package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.pojo.SeTuResponseModel;
import cc.vimc.mcbot.utils.MessageUtil;
import cc.vimc.mcbot.utils.SpringContextUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.util.*;

public class SeTu implements EverywhereCommand {
    private int tmpNextKeyIndex = 0;

    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        String keyword = MessageUtil.removeCommandPrefix(command, event.getMessage());
        Map<String, Object> requestData = new HashMap<>();

        List<String> seTuAPIKeys = Arrays.asList(SpringContextUtil.getEnvProperty("setu.api.key").split(","));
        while (true) {
            if (tmpNextKeyIndex < seTuAPIKeys.size()) {
                requestData.put("apikey", seTuAPIKeys.get(tmpNextKeyIndex));
                tmpNextKeyIndex++;
                break;
            } else {
                tmpNextKeyIndex = 0;
            }
        }
        if (!StringUtils.isEmpty(keyword)) {
            requestData.put("keyword", keyword);
        }

        requestData.put("r18", 2);
        requestData.put("num", 1);
        requestData.put("proxy", "i.pixiv.cat");
        requestData.put("size1200", true);

        String jsonData;
        SeTuResponseModel seTuResponseModel;
        try {
            jsonData = HttpUtil.get("https://api.lolicon.app/setu/", requestData);
            seTuResponseModel = JSON.parseObject(jsonData, SeTuResponseModel.class);
        } catch (Exception e) {
            messageBuilder.add("调用服务出错或JSON转换失败").add(e.getMessage());
            return messageBuilder.toString();
        }
        if (seTuResponseModel.getCode() != 0) {
            if (seTuResponseModel.getCode() == HttpStatus.HTTP_NOT_FOUND) {
                messageBuilder.add("找不到符合关键字的色图");
            }
            if (seTuResponseModel.getCode() == HttpStatus.HTTP_FORBIDDEN) {
                messageBuilder.add("由于不规范的操作而被拒绝调用");
            }
            if (seTuResponseModel.getCode() == HttpStatus.HTTP_UNAUTHORIZED) {
                messageBuilder.add("APIKEY 不存在或被封禁");
            }
            if (seTuResponseModel.getCode() == 429) {
                messageBuilder.add("达到调用额度限制");
            }
            if (seTuResponseModel.getCode() == -1) {
                messageBuilder.add("内部错误，请向 i@loli.best 反馈");
            }
            return messageBuilder.toString();

        }
        Optional<SeTuResponseModel.Setu> firstSeTu = seTuResponseModel.getData().stream().findFirst();

        messageBuilder.add(new ComponentImage(firstSeTu.get().getUrl()));
        messageBuilder.add("作品名称：").add(firstSeTu.get().getTitle()).add("，画师：").add(firstSeTu.get().getAuthor());
        return messageBuilder.toString();
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.SETU.getCommand());
    }
}
