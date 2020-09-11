package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.pojo.HitokotoDataModel;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

public class Hitokoto implements EverywhereCommand {


    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        randomKiToKoTo(messageBuilder);
        return messageBuilder.toString();
    }


    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.HITOKOTO.getCommand());
    }


    public static void randomKiToKoTo(MessageBuilder messageBuilder) {
        String result = HttpUtil.get("https://v1.hitokoto.cn/?c=a&c=b&c=c&c=d&c=e&c=f&c=h&c=j&c=k&c=l");
        HitokotoDataModel hitokotoDataModel;
        hitokotoDataModel = JSON.parseObject(result, HitokotoDataModel.class);
        messageBuilder.add("『").add("" + hitokotoDataModel.getHitokoto()).add("』").add(" ———— ").add(hitokotoDataModel.getFrom());
//        messageBuilder.add("" + hitokoto.getHitokoto());

    }

}
