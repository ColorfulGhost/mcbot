package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.PrivateCommand;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.pojo.FlexBleLoginUser;
import cc.vimc.mcbot.utils.BcryptHasher;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.SpringContextUtil;
import cc.vimc.mcbot.utils.UUIDUtil;
import cn.hutool.core.text.StrSpliter;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class BindMCAuth implements PrivateCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.BIND.getCommand());
    }

    @Override
    public String privateMessage(EventPrivateMessage event, User sender, String command, ArrayList<String> args) {
        UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);
        MessageBuilder messageBuilder = new MessageBuilder();
        String userNameAndPassword = BotUtils.removeCommandPrefix(command, event.getMessage());

        List<String> splitUserNameAndPassword = StrSpliter.split(userNameAndPassword, " ", 2, true, true);
        if (splitUserNameAndPassword.size() != 2) {
            messageBuilder.add("输入格式错误，请检查后确认后再发送给我=w=");
            return messageBuilder.toString();
        }

        String userName = splitUserNameAndPassword.get(0);
        String password = splitUserNameAndPassword.get(1);
        FlexBleLoginUser userBaseInfo = userMapper.getUserBaseInfoByUserName(userName);

        if (userBaseInfo==null){
            messageBuilder.add("用户不存在，请检查后再绑定哈w");
            return messageBuilder.toString();
        }
        CoolQUserMapper coolQUserMapper = SpringContextUtil.getBean(CoolQUserMapper.class);
        if (!StringUtils.isEmpty(coolQUserMapper.selectUserExist(userName))){
            messageBuilder.add("用户已经绑定过啦，请不要重复绑定哈w");
            return messageBuilder.toString();
        }

        if (BcryptHasher.checkPassword(userBaseInfo.getPassword(), password)) {
            String uuid = UUIDUtil.getGuidFromByteArray(userBaseInfo.getUuid());
            try {
                coolQUserMapper.insertUser(String.valueOf(sender.getId()), userBaseInfo.getUserId(), uuid,userName);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            messageBuilder.add("密码验证通过并绑定成功！");
        } else {
            messageBuilder.add("密码验证不通过，绑定失败！");
        }
        return messageBuilder.toString();

    }
}
