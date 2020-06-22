package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.enums.ConstantMessages;
import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.pojo.CoolQUser;
import cc.vimc.mcbot.rcon.RconCommand;
import cc.vimc.mcbot.utils.SpringContextUtil;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;

public class PlayerStatistic  implements GroupCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.STATISTIC.getCommand());
    }

    @Override
    @Scheduled(cron = "16 14 * * *")
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {

        MessageBuilder messageBuilder = new MessageBuilder();
        CoolQUserMapper coolQUserMapper = SpringContextUtil.getBean(CoolQUserMapper.class);
        CoolQUser coolQUser = coolQUserMapper.selectQQExist(sender.getId());
        if (coolQUser==null){
            messageBuilder.add(ConstantMessages.PLAYER_NOT_EXIST);
            return messageBuilder.toString();
        }
        messageBuilder.add(RconCommand.send(Commands.LIST.getCommand()));
        return messageBuilder.toString();
    }
}
