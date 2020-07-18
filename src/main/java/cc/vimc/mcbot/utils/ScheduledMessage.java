package cc.vimc.mcbot.utils;


import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.vimc.mcbot.bot.Bot;
import cc.vimc.mcbot.mapper.CoolQStatusMapper;
import cc.vimc.mcbot.mapper.NewHonorPlayerMapper;
import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.pojo.BangumiList;
import cc.vimc.mcbot.pojo.CoolQStatus;
import cc.vimc.mcbot.pojo.FlexBleLoginUser;
import cc.vimc.mcbot.pojo.NewHonorPlayer;
import cc.vimc.mcbot.rcon.RconCommand;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ScheduledMessage {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewHonorPlayerMapper newHonorPlayerMapper;

    @Autowired
    private CoolQStatusMapper coolQStatusMapper;

    @Value("${minecraft.qq.group}")
    private Long minecraftQQGroup;


    private IcqHttpApi icqHttpApi = Bot.bot.getAccountManager().getNonAccountSpecifiedApi();

    private String lastQuarterBangumi;

    @Scheduled(cron = "0 0 5 * * ?")
    @PostConstruct
    public void getBangumiJSON() {
        String bangumiLayerYearJSON = HttpUtil.get("https://bgmlist.com/tempapi/archive.json");
        Map data = JSON.parseObject(bangumiLayerYearJSON).getJSONObject("data").toJavaObject(Map.class);
        Collection yearURLCollect = data.values();
        Map<String, String> lastYear = (Map<String, String>) yearURLCollect.toArray()[yearURLCollect.size() - 1];
        Collection<String> quarterCollect = lastYear.values();
        Map<String, String> lastQuarter = (Map<String, String>) quarterCollect.toArray()[quarterCollect.size() - 1];
        String lastQuarterBangumiURL = lastQuarter.get("path");

        this.lastQuarterBangumi = HttpUtil.get(lastQuarterBangumiURL);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendBangumiUpdateTime() {
        Collection<Object> bgList = JSON.parseObject(this.lastQuarterBangumi).values();
        List<BangumiList> bangumiList = Convert.convert(new TypeReference<List<BangumiList>>() {
        }, bgList);

        //周-1 ，当天番剧
        Map<String, List<BangumiList>> tmpBGMList = new HashMap<>();

        for (BangumiList bgm : bangumiList) {
            if (bgm.isNewBgm()) {
                tmpBGMList.computeIfAbsent(bgm.getWeekDayCN(), k -> new ArrayList<>()).add(bgm);
            }
        }
        Date date = new Date();

        SimpleDateFormat hHmm = new SimpleDateFormat("HHmm");
        String hhmmString = hHmm.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        MessageBuilder result = new MessageBuilder();


        List<BangumiList> todayBGM = tmpBGMList.get(String.valueOf(dayOfWeek - 1));
        int bgmId = 0;
        for (BangumiList bgm : todayBGM) {
            String timeCN = bgm.getTimeCN();
            if (!StringUtils.isEmpty(timeCN) && timeCN.equals(hhmmString)) {
                result.add("《").add(bgm.getTitleCN()).add("》").add("更新了！\n")
                        .add("\n放送地址：\n");
                for (String url : bgm.getOnAirSite()) {
                    result.add(url + "\n");
                }
                bgmId = bgm.getBgmId();
                break;
            }
        }
        if (StringUtils.isEmpty(result.toString())) {
            return;
        }
        List<CoolQStatus> coolQStatusList = coolQStatusMapper.getCoolQStatusList();
        //过滤不喜欢的番剧
        Map<Long, Set<String>> excludeBangumi = new HashMap<>();
        //组与QQ关系
        Map<Long, List<Long>> qqByQQGroup = new HashMap<>();

        for (CoolQStatus coolQStatus : coolQStatusList) {
            qqByQQGroup.computeIfAbsent(coolQStatus.getQqGroup(), x -> new ArrayList<>()).add(coolQStatus.getQq());
            if (!StringUtils.isEmpty(coolQStatus.getBangumiExclude())) {
                List<String> splitBangumiExclude = StrSpliter.split(coolQStatus.getBangumiExclude(), ",", true, true);
                excludeBangumi.computeIfAbsent(coolQStatus.getQq(), x -> new HashSet<>()).addAll(splitBangumiExclude);
            }
        }
        result.add("通知下面的小伙伴开饭啦，如果不喜欢这个番剧可以：/bangumi rm ").add(bgmId);


        int finalBgmId = bgmId;
        //过滤不喜欢的番剧
        qqByQQGroup.forEach((group, qqs) -> {
            qqs.forEach(qq -> {
                if (!MapUtil.isEmpty(excludeBangumi)&& !excludeBangumi.get(qq).contains(finalBgmId)) {
                    result.add(new ComponentAt(qq));
                }
            });
            icqHttpApi.sendGroupMsg(group, result.toString());
        });


    }

    @Scheduled(cron = "50 59 23 * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void sendPlayerStatistics() {


        List<FlexBleLoginUser> todayLoginPlayers = userMapper.getTodayLoginPlayers();

        if (CollectionUtils.isEmpty(todayLoginPlayers)) {
            icqHttpApi.sendGroupMsg(minecraftQQGroup, "今日没有玩家在线OAO");
            return;
        }
        StringBuilder retMessage = new StringBuilder();
        retMessage.append("今日在线").append(todayLoginPlayers.size()).append("个玩家的时间统计,注意时间健康生活哈！");

        for (FlexBleLoginUser todayLoginPlayer : todayLoginPlayers) {
            String rconData = RconCommand.send("/playtime " + todayLoginPlayer.getUserName());
            String[] splitPlayTime = rconData.split("\n");

            String total = splitPlayTime[1];
            String totalTime = total.split("\\|")[0].split(": ")[1];

            if (totalTime.contains("1w")) {
                String uuid = UUIDUtil.getGuidFromByteArray(todayLoginPlayer.getUuid());
                NewHonorPlayer newHonorPlayer = newHonorPlayerMapper.selectPlayerHonorForUUID(uuid);
                String honor[] = newHonorPlayer.getHonors().split(",");
                Set<String> tmpHonor = new HashSet(Arrays.asList(honor));
                tmpHonor.add("gandi");

                StringBuilder retHonorString = new StringBuilder();

                for (String singeHonor : tmpHonor) {
                    retHonorString.append(",").append(singeHonor);
                }
                newHonorPlayerMapper.updatePlayerHonorForUUID(retHonorString.toString(), uuid);
            }
            String toDay = splitPlayTime[2];
            retMessage.append("\n=========")
                    .append(todayLoginPlayer.getUserName())
                    .append("=========")
                    .append("\n今日游戏总时长：")
                    .append(convertChineseTime(toDay.split("\\|")[0].split(":\\s")[1]))
                    .append("\n今日挂机：")
                    .append(convertChineseTime(toDay.split("\\|")[1].split(" - ")[0]))
                    .append("\n总游戏时长：")
                    .append(convertChineseTime(totalTime))
                    .append("\n挂机总时长：")
                    .append(convertChineseTime(total.split("\\|")[1].split(" - ")[0]));
        }
        icqHttpApi.sendGroupMsg(minecraftQQGroup, retMessage.toString());
    }

    private static String convertChineseTime(String source) {
        return source.replace("s", "秒 ")
                .replace("m", "分 ")
                .replace("h", "小时 ")
                .replace("d", "天 ")
                .replace("w", "周 ");
    }
}
