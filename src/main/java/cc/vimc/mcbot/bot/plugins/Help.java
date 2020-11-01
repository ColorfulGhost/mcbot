package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;

import java.util.ArrayList;

public class Help implements EverywhereCommand {


    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        for (Commands commands : Commands.values()) {
            messageBuilder.add("/").add(commands.getCommand()).add("   ").add(commands.getDescription()).newLine();
        }
        return messageBuilder.toString();
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties("help");
    }
}
