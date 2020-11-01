package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.utils.BotUtils;
import cn.hutool.core.collection.CollectionUtil;
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
                messageBuilder.add("预计").add(formatDate).add("恢复到").add(spList.get(1)).add("体力，咱会提前5分钟通知@您");
                return messageBuilder.toString();
            case STATUS:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.YUANSHEN.getCommand());
    }

}
