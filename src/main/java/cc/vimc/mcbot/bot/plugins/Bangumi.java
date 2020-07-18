package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.mapper.CoolQStatusMapper;
import cc.vimc.mcbot.pojo.CoolQStatus;
import cc.vimc.mcbot.utils.SpringContextUtil;

import java.util.ArrayList;

public class Bangumi implements GroupCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.BANGUMI.getCommand());
    }

    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        long qq = sender.getId();
        long groupId = group.getId();
        CoolQStatusMapper coolQStatusMapper = SpringContextUtil.getBean(CoolQStatusMapper.class);
        CoolQStatus checkExist = coolQStatusMapper.checkExist(qq, groupId);


        messageBuilder.add(new ComponentAt(qq));
        if (checkExist==null){
            coolQStatusMapper.insertBangumiStatus(qq,groupId,true);
            messageBuilder.add("你已经成功订阅新番更新提醒，新番更新的时候会@你哈");
            return messageBuilder.toString();

        }

        if (checkExist.isBangumiFlag()) {
            coolQStatusMapper.updateBangumiStatus(qq,groupId,false);
            messageBuilder.add("你已经取消订阅新番更新提醒");
        }else {
            coolQStatusMapper.updateBangumiStatus(qq,groupId,true);
            messageBuilder.add("再次订阅了新番更新提醒，新番更新的时候会@你");
        }

        return messageBuilder.toString();
    }
}
