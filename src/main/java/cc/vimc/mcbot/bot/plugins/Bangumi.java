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
import cc.vimc.mcbot.utils.MessageUtil;
import cc.vimc.mcbot.utils.SpringContextUtil;
import cn.hutool.core.text.StrSpliter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
        String acceptMessage = MessageUtil.removeCommandPrefix(command, event.getMessage());

        messageBuilder.add(new ComponentAt(qq));

        if (StringUtils.isEmpty(acceptMessage)) {
            if (checkExist == null) {
                coolQStatusMapper.insertBangumiStatus(qq, groupId, true);
                messageBuilder.add("你已经成功订阅新番更新提醒，新番更新的时候会@你哈");
                return messageBuilder.toString();
            }
            if (checkExist.isBangumiFlag()) {
                coolQStatusMapper.updateBangumiStatus(qq, groupId, false);
                messageBuilder.add("你已经取消订阅新番更新提醒");
            } else {
                coolQStatusMapper.updateBangumiStatus(qq, groupId, true);
                messageBuilder.add("再次订阅了新番更新提醒，新番更新的时候会@你");
            }
            return messageBuilder.toString();
        }

        if (acceptMessage.startsWith("rm ")) {
            List<String> bgmIds = StrSpliter.split(acceptMessage, " ", true, true);
            StringBuilder excludeBGMIds = new StringBuilder();

            for (int i = 1; i < bgmIds.size(); i++) {
                excludeBGMIds.append(bgmIds.get(i)).append(",");
            }
            String bangumiExclude = checkExist.getBangumiExclude();
            if (StringUtils.isEmpty(bangumiExclude)) {
                coolQStatusMapper.updateBangumiExclude(qq, groupId, excludeBGMIds.toString());
            } else {
                coolQStatusMapper.updateBangumiExclude(qq, groupId, bangumiExclude + excludeBGMIds.toString());
            }
            messageBuilder.add("已经移除番号：" + excludeBGMIds.toString() + "订阅。");
            return messageBuilder.toString();
        }
        messageBuilder.add("你没有订阅或错误使用命令无法进行通知番剧过滤");
        return messageBuilder.toString();
    }
}
