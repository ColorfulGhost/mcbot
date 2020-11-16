package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.pojo.*;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.utils.BeanUtil;
import cc.vimc.mcbot.utils.BotUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Log4j2
public class YuanShen implements EverywhereCommand {
    /**
     * 绑定账号
     */
    private static final String BIND = "bind";
    /**
     * 体力设置
     */
    private static final String SP = "sp";
    /**
     * 角色状态
     */
    private static final String STATUS = "status";



    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        String preCommand = BotUtils.removeCommandPrefix(Commands.YUANSHEN.getCommand(), event.getMessage());
        List<String> preCommandList = Arrays.asList(preCommand.split(" "));
        MessageBuilder messageBuilder = new MessageBuilder();
        if (CollectionUtil.isEmpty(preCommandList)) {
            return null;
        }
        switch (preCommandList.get(0)) {
            case BIND:
                if (BeanUtil.verifyNoBindQQ(sender.getId())){
                    CoolQUser coolQUser = new CoolQUser();
                    coolQUser.setQq(sender.getId());
                    coolQUser.setYuanShenUID(preCommandList.get(1));
                    BeanUtil.coolQUserMapper.insertUser(coolQUser);
                }else {

                }
                break;
            case SP:
                List<String> spList = Arrays.asList(preCommandList.get(1).split("-"));
                if (CollectionUtil.isEmpty(spList)) {
                    return null;
                }
                Integer nowSp = Integer.valueOf(spList.get(0));
                Integer afterSp = Integer.valueOf(spList.get(1));
                int preSp = afterSp - nowSp;
                if (preSp <= 0) {
                    return null;
                }
                //1分钟8体力*预计恢复体力 = 总恢复时间
                int preSpRestoreMin = preSp * 8;

                Date date = new Date(System.currentTimeMillis() + 60000 * preSpRestoreMin);
                String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                messageBuilder.add("预计").add(formatDate).add("恢复到").add(spList.get(1)).add("体力.");
                return messageBuilder.toString();
            case STATUS:


                break;
            default:
                break;
        }
        return null;
    }

    private String getStatus(String UID) {
        String stringJSON = HttpUtil.get("https://service-joam13r8-1252025612.gz.apigw.tencentcs.com/uid/" + UID);
        RetModel<YuanShenUserInfo> yuanShenUserInfoRetModel = JSONObject.parseObject(stringJSON, new TypeReference<RetModel<YuanShenUserInfo>>() {
        });
        if (!"OK".equals(yuanShenUserInfoRetModel.getMessage())) {
            return "奇怪...查询信息错误，联系主人排查w";
        }
        YuanShenUserInfo yuanShenUserInfo = yuanShenUserInfoRetModel.getData();

        StringBuilder avatarsResult = new StringBuilder();
        StringBuilder statusResult = new StringBuilder();
        StringBuilder cityExplorationsResult = new StringBuilder();

        Stats stats = yuanShenUserInfo.getStats();
        statusResult.append("活跃天数").append(stats.getActiveDayNumber()).append("\n");
        statusResult.append("成就达成数").append(stats.getAchievementNumber()).append("\n");
        statusResult.append("风神瞳").append(stats.getAnemoculusNumber()).append("\n");
        statusResult.append("岩神瞳").append(stats.getGeoculusNumber()).append("\n");

        statusResult.append("获得角色数").append(stats.getAvatarNumber()).append("\n");
        statusResult.append("解锁传送点").append(stats.getWayPointNumber()).append("\n");
        statusResult.append("解锁秘境").append(stats.getDomainNumber()).append("\n");
        statusResult.append("深境螺旋").append(stats.getSpiralAbyss()).append("\n");

        statusResult.append("华丽宝箱数").append(stats.getLuxuriousChestNumber()).append("\n");
        statusResult.append("珍贵宝箱数").append(stats.getPreciousChestNumber()).append("\n");
        statusResult.append("精致宝箱数").append(stats.getExquisiteChestNumber()).append("\n");
        statusResult.append("普通宝箱数").append(stats.getCommonChestNumber()).append("\n");

        List<AvatarsItem> avatars = yuanShenUserInfo.getAvatars();
        for (AvatarsItem avatar : avatars) {
            avatarsResult.append(avatar.getName()).append(avatar.getRarity()).append("⭐").append("|")
                    .append("等级：").append(avatar.getLevel())
                    .append("好感度：").append(avatar.getFetter()).append("\n");
        }
        avatarsResult.append("----------");
        for (CityExplorationsItem cityExploration : yuanShenUserInfo.getCityExplorations()) {
            cityExplorationsResult.append("城市：").append(cityExploration.getName())
                    .append("声望等级：").append(cityExploration.getLevel())
                    .append("探索度：").append(cityExploration.getExplorationPercentage() / 10.0)
                    .append("%");
        }

        return avatarsResult.toString()+statusResult.toString()+cityExplorationsResult.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.YUANSHEN.getCommand());
    }

}
