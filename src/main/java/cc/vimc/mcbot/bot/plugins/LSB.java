package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.utils.BeanUtil;
import cc.vimc.mcbot.utils.RedisUtil;

import java.util.ArrayList;

public class LSB implements EverywhereCommand {

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.add("今日涩图API调用次数：");
        messageBuilder.add(BeanUtil.redisUtil.get(RedisUtil.BOT_SETU_COUNT));
        return messageBuilder.toString();
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.LSB.getCommand());
    }
}
