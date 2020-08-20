package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.enums.ConstantMessages;
import cc.vimc.mcbot.utils.MCUtils;
import cc.vimc.mcbot.utils.MessageUtil;
import cc.vimc.mcbot.utils.SpringContextUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

@Log4j2
public class StartServer implements EverywhereCommand {


    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();

        if (MCUtils.verifyNoBindQQ(sender.getId())) {
            messageBuilder.add(ConstantMessages.PLAYER_NOT_EXIST.getMessage());
            return messageBuilder.toString();
        }
        String apiKey = SpringContextUtil.getEnvProperty("MCSM.api.admin.key");
        String url = SpringContextUtil.getEnvProperty("MCSM.api.url");
        String serverName = MessageUtil.removeCommandPrefix(command, event.getMessage());

        String data;
        try {
            data = HttpUtil.get(url + "start_server/" + serverName + "?apikey=" + apiKey);
        } catch (Exception e) {
            messageBuilder.add(e.getMessage());
            return messageBuilder.toString();

        }

        JSONObject jsonData = JSON.parseObject(data);
        int status = (int) jsonData.get("status");
        String errorMessage = (String) jsonData.getOrDefault("error", "未知错误");

        if (status == HttpStatus.HTTP_OK) {
            messageBuilder.add("服务器启动成功啦！");
        } else {
            messageBuilder.add(errorMessage);
        }
        return messageBuilder.toString();

    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.START_SERVER.getCommand());
    }
}