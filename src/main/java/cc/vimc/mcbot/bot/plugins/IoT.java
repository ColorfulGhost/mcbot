package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.utils.BeanUtil;
import cc.vimc.mcbot.utils.BotUtils;
import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IoT implements EverywhereCommand {

    private static final String LIGHT = "light";
    private static final String DOOR = "door";

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        String preCommand = BotUtils.removeCommandPrefix(Commands.IOT.getCommand(), event.getMessage());
        List<String> preCommandList = Arrays.asList(preCommand.split(" "));
        MessageBuilder messageBuilder = new MessageBuilder();
        if (CollectionUtil.isEmpty(preCommandList)) {
            return null;
        }
        switch (preCommandList.get(0)) {
            case DOOR:
                String lightSwitch = preCommandList.get(1);
                BeanUtil.mqttGateway.sendToMqtt(lightSwitch, "inTopic-" + DOOR);
                return messageBuilder.add("success").toString();
            case LIGHT:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.IOT.getCommand());
    }
}
