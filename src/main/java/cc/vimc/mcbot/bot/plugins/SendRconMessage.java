package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.rcon.RconCommand;
import cc.vimc.mcbot.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SendRconMessage implements EverywhereCommand {

    private List<Long> admins = Arrays.asList(815666528L);

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {

        MessageBuilder messageBuilder = new MessageBuilder();
        if (admins.contains(sender.getId())) {
            messageBuilder.add(RconCommand.send(MessageUtil.removeCommandPrefix(command, event.getMessage())));
        } else {
            messageBuilder.add("您不是管理员，无权执行命令");
        }
        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.RCON.getCommand());
    }
}
