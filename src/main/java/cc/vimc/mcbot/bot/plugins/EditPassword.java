package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.enums.ConstantMessages;
import cc.vimc.mcbot.entity.CoolQUser;
import cc.vimc.mcbot.entity.FlexBleLoginUser;
import cc.vimc.mcbot.utils.BcryptHasher;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.BeanUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class EditPassword implements PrivateCommand {
    @Override
    public String privateMessage(EventPrivateMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        CoolQUser coolQUser = BeanUtil.coolQUserMapper.selectForQQ(sender.getId());
        if (coolQUser == null) {
            messageBuilder.add(ConstantMessages.PLAYER_NOT_EXIST);
            return messageBuilder.toString();

        }
        String password = BotUtils.removeCommandPrefix(command, event.getMessage());
        if (StringUtils.isEmpty(password)) {
            messageBuilder.add("密码不能为空！");
            return messageBuilder.toString();
        }
        if (password.length() < 6) {
            messageBuilder.add("密码不能少于6位数！");
            return messageBuilder.toString();
        }
        FlexBleLoginUser userBaseInfoByUserId = BeanUtil.getUserBaseInfoByUserId(coolQUser.getFlexbleloginId());
        BeanUtil.userMapper.editPassword(userBaseInfoByUserId.getUserName(), BcryptHasher.hash(password));
        messageBuilder.add("修改密码成功！");

        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        {
            return new CommandProperties(Commands.EDIT_PASSWORD.getCommand());
        }
    }
}
