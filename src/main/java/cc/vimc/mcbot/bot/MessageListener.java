package cc.vimc.mcbot.bot;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberApprove;
import cc.vimc.mcbot.mapper.CoolQKeyWordMapper;
import cc.vimc.mcbot.pojo.CoolQKeyWord;
import cc.vimc.mcbot.utils.SpringContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageListener extends IcqListener {

    @EventHandler
    public void eventNoticeGroupMemberApprove(EventNoticeGroupMemberApprove event) {
        //阳炎科技
        if (320510479 == event.getGroupId()) {
            event.getGroupMethods().respond("欢迎新成员~查看置顶群公告，看看如何申请白名单吧~");
        }
    }

    @EventHandler
    public void eventGroupMessage(EventGroupMessage event) {


    }
}
