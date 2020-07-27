package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.mapper.CoolQSoulMapper;
import cc.vimc.mcbot.utils.SpringContextUtil;

import java.util.ArrayList;

public class AntiMotivationalQuotes implements EverywhereCommand {
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();

        CoolQSoulMapper coolQSoulMapper = SpringContextUtil.getBean(CoolQSoulMapper.class);
        messageBuilder.add(coolQSoulMapper.getSoul().getTitle());
        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.AMQ.getCommand());
    }
}
