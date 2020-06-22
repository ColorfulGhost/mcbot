package cc.vimc.mcbot.utils;


import cc.moecraft.icq.sender.IcqHttpApi;
import cc.vimc.mcbot.bot.Bot;
import cc.vimc.mcbot.mapper.NewHonorPlayerMapper;
import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.pojo.FlexBleLoginUser;
import cc.vimc.mcbot.pojo.NewHonorPlayer;
import cc.vimc.mcbot.rcon.RconCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScheduledMessage {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewHonorPlayerMapper newHonorPlayerMapper;

    @Scheduled(cron = "30 59 23 * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void sendPlayerStatistics() {

        IcqHttpApi icqHttpApi = Bot.bot.getAccountManager().getNonAccountSpecifiedApi();

        List<FlexBleLoginUser> todayLoginPlayers = userMapper.getTodayLoginPlayers();

        if (CollectionUtils.isEmpty(todayLoginPlayers)) {
            icqHttpApi.sendGroupMsg(320510479, "今日没有玩家在线OAO");
            return;
        }
        StringBuilder retMessage = new StringBuilder();
        retMessage.append("今日在线").append(todayLoginPlayers.size()).append("个玩家的时间统计,注意时间健康生活哈！");

        for (FlexBleLoginUser todayLoginPlayer : todayLoginPlayers) {
            String rconData = RconCommand.send("/playtime " + todayLoginPlayer.getUserName());
            String[] splitPlayTime = rconData.split("\n");

            String total = splitPlayTime[1];
            String totalTime = total.split("\\|")[0].split(": ")[1];

            if (total.contains("1w")) {
                String uuid = UUIDUtil.getGuidFromByteArray(todayLoginPlayer.getUuid());
                NewHonorPlayer newHonorPlayer = newHonorPlayerMapper.selectPlayerHonorForUUID(uuid);
                String honor[] = newHonorPlayer.getHonors().split(",");
                Set<String> tmpHonor = new HashSet(Arrays.asList(honor));
                tmpHonor.add("gandi");

                StringBuilder retHonorString = new StringBuilder();

                for (String singeHonor : tmpHonor) {
                    retHonorString.append(",").append(singeHonor);
                }
                newHonorPlayerMapper.updatePlayerHonorForUUID( retHonorString.toString(),uuid);
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

        icqHttpApi.sendGroupMsg(320510479, retMessage.toString());

    }

    private static String convertChineseTime(String source) {
        return source.replace("s", "秒 ")
                .replace("m", "分 ")
                .replace("h", "小时 ")
                .replace("d", "天 ")
                .replace("w", "周 ");
    }
}
