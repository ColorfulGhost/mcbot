package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.pojo.MiBandDataModel;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class MiBand implements EverywhereCommand {
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {

        MessageBuilder messageBuilder = new MessageBuilder();
        getMiBandData(messageBuilder);

        return messageBuilder.toString();
    }

    public static void  getMiBandData(MessageBuilder messageBuilder) {
        HttpRequest httpRequest = HttpUtil.createGet("http://192.168.1.202:90/getMiBandStat");
        HttpResponse httpResponse = httpRequest.execute();

        if (httpResponse.getStatus() == HttpStatus.HTTP_INTERNAL_ERROR) {
            messageBuilder.add(httpResponse.body());
        } else if (httpResponse.getStatus() == HttpStatus.HTTP_OK) {
            MiBandDataModel miBandDataModel = JSONObject.parseObject(httpResponse.body(), MiBandDataModel.class);
            messageBuilder.add("截止当前运动：").add(miBandDataModel.getSteps()).add("步\n");
            messageBuilder.add("消耗：").add(miBandDataModel.getCalories()).add("Cal\n");
            messageBuilder.add("今日移动距离: ").add(miBandDataModel.getMeters()).add("M\n");
        } else {
            messageBuilder.add("网络请求错误，请检查服务器是否正常");
        }
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.MIBAND.getCommand());
    }
}
