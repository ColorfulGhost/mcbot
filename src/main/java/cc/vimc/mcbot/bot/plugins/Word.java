package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.ConstantMessages;
import cc.vimc.mcbot.mapper.CoolQKeyWordMapper;
import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.pojo.CoolQUser;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.SpringContextUtil;
import cn.hutool.core.text.StrSpliter;

import java.util.ArrayList;
import java.util.List;

public class Word implements EverywhereCommand {
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        CoolQUserMapper coolQUserMapper = SpringContextUtil.getBean(CoolQUserMapper.class);
        CoolQUser coolQUser = coolQUserMapper.selectQQExist(sender.getId());
        if (coolQUser == null) {
            messageBuilder.add(ConstantMessages.PLAYER_NOT_EXIST);
            return messageBuilder.toString();
        }
        CoolQKeyWordMapper coolQKeyWordMapper = SpringContextUtil.getBean(CoolQKeyWordMapper.class);
        String typeAndContent = BotUtils.removeCommandPrefix(command, event.getMessage());
        List<String> typeAndContentSplit = StrSpliter.split(typeAndContent, " ", 3, true, true);
        //类型 Q A  = 3
        if (typeAndContentSplit.size() != 3) {
            messageBuilder.add("输入格式错误，请检查后确认后再发给我哈~");
            return messageBuilder.toString();
        }

        int matchType = 0;
        switch (typeAndContentSplit.get(0)) {
            case "exact":
                matchType = 0;
                break;
            case "fuzzy":
                matchType = 1;
                break;
            case "regex":
                matchType = 2;
                break;
            default:
                break;
        }


        coolQKeyWordMapper.insert(typeAndContentSplit.get(1), typeAndContentSplit.get(2), matchType, sender.getId());

//        messageBuilder.add(RconCommand.send(Commands.LIST.getCommand()));
        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("");
    }

}
