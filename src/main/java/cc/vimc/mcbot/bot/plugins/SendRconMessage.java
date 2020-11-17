package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.enums.ConstantMessages;
import cc.vimc.mcbot.rcon.RconCommand;
import cc.vimc.mcbot.utils.BotUtils;

import java.util.ArrayList;

public class SendRconMessage implements EverywhereCommand {
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        if (BotUtils.ADMINS.contains(sender.getId())) {
            messageBuilder.add(RconCommand.send(BotUtils.removeCommandPrefix(command, event.getMessage())));
        } else {
            messageBuilder.add(ConstantMessages.PERMISSION_NOT_FUNOD.getMessage());
        }
        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.RCON.getCommand());
    }
}
