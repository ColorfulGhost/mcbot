package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.GroupCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.vimc.mcbot.entity.CoolQGroup;
import cc.vimc.mcbot.mapper.CoolQGroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;

public class Switch extends ServiceImpl<CoolQGroupMapper, CoolQGroup> implements GroupCommand {

    public CoolQGroupMapper coolQGroupMapper() {
        return this.baseMapper;
    }

    @Override
    public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {
        return null;
    }

    @Override
    public CommandProperties properties() {
        return null;
    }
}
