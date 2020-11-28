package cc.vimc.mcbot.utils;


import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.vimc.mcbot.bot.Bot;
import cc.vimc.mcbot.bot.plugins.Hitokoto;
import cc.vimc.mcbot.bot.plugins.MiBand;
import cc.vimc.mcbot.bot.plugins.SeTu;
import cc.vimc.mcbot.bot.plugins.YuanShen;
import cc.vimc.mcbot.entity.CoolQStatus;
import cc.vimc.mcbot.entity.CoolQUser;
import cc.vimc.mcbot.entity.NewHonorPlayer;
import cc.vimc.mcbot.mapper.CoolQStatusMapper;
import cc.vimc.mcbot.entity.FlexBleLoginUser;
import cc.vimc.mcbot.mapper.NewHonorPlayerMapper;
import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.pojo.*;
import cc.vimc.mcbot.rcon.RconCommand;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Log4j2
public class ScheduledSendMessage {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewHonorPlayerMapper newHonorPlayerMapper;

    @Autowired
    private CoolQStatusMapper coolQStatusMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${minecraft.qq.group}")
    private Long minecraftQQGroup;


    private final static IcqHttpApi icqHttpApi = Bot.bot.getAccountManager().getNonAccountSpecifiedApi();

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

    @Scheduled(cron = "00 12 6 * * ?")
    @Async
    public void autoYuanShenSign() {
        List<CoolQUser> coolQUser = BeanUtil.coolQUserMapper.selectYuanShenCookieNotNull();
        if (CollectionUtil.isEmpty(coolQUser)) {
            return;
        }
        YuanShen yuanShen = new YuanShen();
        for (CoolQUser qUser : coolQUser) {
            String yuanshenCookie = qUser.getYuanshenCookie();
            String result = yuanShen.sendYuanShenSign(yuanshenCookie);
            ThreadUtil.sleep(BotUtils.getRandomSum(2, 2000));
            icqHttpApi.sendPrivateMsg(qUser.getQq(), result);

        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void SendMessageFor1ms() {
        //每秒番剧检测
        sendBangumiUpdateTime();
        //循环3次范围100累加
        int randomSum = BotUtils.getRandomSum(3, 100);
        //随机组
        Long randomGroupId = BotUtils.getRandomGroupId();
        MessageBuilder messageBuilder = new MessageBuilder();

        //累计3次总和随机 数值越小调用到的每秒概率略大
        if (randomSum > 260) {
            //随机涩图
            if (randomSeTu(messageBuilder)) {
                return;
            }
        } else if (randomSum > 240) {
            //随机一言
//            if (randomKiToKoTo(messageBuilder)) {
//                return;
//            }
        }


        icqHttpApi.sendGroupMsg(randomGroupId, messageBuilder.toString());

    }

    private boolean randomSeTu(MessageBuilder messageBuilder) {
        SeTuResponseModel seTuResponseModel = new SeTu().seTuApi("", 0, 1);
        if (seTuResponseModel.getCode() != 0) {
            return true;
        }
        SeTuResponseModel.Setu setu = seTuResponseModel.getData().get(0);
        messageBuilder.add(new ComponentImage(setu.getUrl()));
        return false;
    }


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
                        .add("放送地址：\n");
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
        result.add("通知下面的小伙伴开饭啦，如果不喜欢这个番剧可以：/bangumi rm ").add(bgmId).add("\n");
        int finalBgmId = bgmId;
        //过滤不喜欢的番剧
        qqByQQGroup.forEach((group, qqs) -> {
            StringBuilder atMember = new StringBuilder();
            qqs.forEach(qq -> {
                if (!MapUtil.isEmpty(excludeBangumi) &&
                        !(excludeBangumi.containsKey(qq) && excludeBangumi.get(qq)
                                .contains(String.valueOf(finalBgmId)))) {
                    atMember.append(new ComponentAt(qq));
                }
            });
            if (StringUtils.isEmpty(atMember.toString())) {
                return;
            }
            result.add(atMember);
            icqHttpApi.sendGroupMsg(group, result.toString());
        });


    }

    @Scheduled(cron = "00 58 23 * * ?")
    public void sendQQZoneMessage() {

        MessageBuilder messageBuilder = new MessageBuilder();

        MiBand.getMiBandData(messageBuilder);

        messageBuilder.add("\n今日涩图API调用次数：").add(redisUtil.get(RedisUtil.BOT_SETU_COUNT));
        messageBuilder.add("\n一言：");
        Hitokoto.randomKiToKoTo(messageBuilder);
        messageBuilder.add("\n毒鸡汤：");
        messageBuilder.add(BeanUtil.getSoul());
        messageBuilder.add("\n");


        messageBuilder.add("\n本条说说驱动：https://github.com/ColorfulGhost/QzoneHuhuRobot");

        Map<String, Object> param = new HashMap<>();
        param.put("msg", messageBuilder.toString());
        HttpUtil.post("http://192.168.1.220:89/sendMsgToQQZone", param);
    }

    @Scheduled(cron = "59 59 23 * * ?")
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

        ThreadUtil.sleep(1000);
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
