package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentAt;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.pojo.AnimeModel;
import cc.vimc.mcbot.pojo.DocsItem;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.RegxUtils;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Log4j2
public class Anime implements EverywhereCommand {
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();

        String coolQImageCode = BotUtils.removeCommandPrefix(command, event.getMessage());
        String imageURL = new RegxUtils(coolQImageCode).getCQCodeImageURL();
        String result = HttpUtil.get("https://trace.moe/api/search?url=" + imageURL);
        messageBuilder.add(new ComponentAt(sender.getId())).add("\n");
        if ("\"Error reading imagenull\"".equals(result)) {
            messageBuilder.add("你发送的图片过大或是GIF导致搜索失败:P");
            return messageBuilder.toString();
        }
        AnimeModel animeModel = null;
        try {
            animeModel = JSONObject.parseObject(result, AnimeModel.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            messageBuilder.add("查询失败，请稍后再试=_=||");
            return messageBuilder.toString();
        }

        if (CollectionUtils.isEmpty(animeModel.getDocs())) {
            messageBuilder.add("没有找到该番剧信息OAO");
            return messageBuilder.toString();
        }
        DocsItem docsItem = animeModel.getDocs().stream().findFirst().get();
        messageBuilder.add(docsItem.getAnime()).add("\n");
        if (docsItem.getEpisode() != 0) {
            messageBuilder.add("第" + docsItem.getEpisode() + "集\n");

        }
        messageBuilder.add("相似度：").add((int) (docsItem.getSimilarity() * 100)).add("%\n")
                .add("场景片段：")
                .add((int) docsItem.getFrom() / 60).add(":").add((int) docsItem.getFrom() % 60)
                .add(" - ")
                .add((int) docsItem.getTo() / 60).add(":").add((int) docsItem.getTo() % 60);
        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.ANIME.getCommand());
    }
}
