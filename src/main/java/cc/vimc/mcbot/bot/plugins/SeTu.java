package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.pojo.SeTuResponseModel;
import cc.vimc.mcbot.utils.BeanUtil;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.RedisUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.*;

@Log4j2
public class SeTu implements EverywhereCommand {
    private int tmpNextKeyIndex = 0;


    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        MessageBuilder messageBuilder = new MessageBuilder();
        String keyword = BotUtils.removeCommandPrefix(command, event.getMessage());


        SeTuResponseModel seTuResponseModel = seTuApi(keyword, 2, 1, event);

        if (seTuResponseModel == null) {
            messageBuilder.add("调用色图服务出错");
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
        SeTuResponseModel.Setu firstSeTu = seTuResponseModel.getData().stream().findFirst().get();
        messageBuilder.add(new ComponentImage(firstSeTu.getUrl()));
        messageBuilder.add("作品名称：").add(firstSeTu.getTitle()).add("\n画师：").add(firstSeTu.getAuthor());


        return messageBuilder.toString();
    }


    /**
     * 文档：https://api.lolicon.app/
     *
     * @param keyword 若指定关键字，将会返回从插画标题、作者、标签中模糊搜索的结果
     * @param r18     0为非 R18，1为 R18，2为混合
     * @param num     一次返回的结果数量，范围为1到10，不提供 APIKEY 时固定为1；在指定关键字的情况下，结果数量可能会不足指定的数量
     * @return
     */

    public SeTuResponseModel seTuApi(String keyword, int r18, int num) {
        return seTuApi(keyword, r18, num, null);
    }

    public SeTuResponseModel seTuApi(String keyword, int r18, int num, EventMessage event) {
        Map<String, Object> requestData = new HashMap<>();
        while (true) {
            if (tmpNextKeyIndex < BeanUtil.seTuAPIKeys.size()) {
                requestData.put("apikey", BeanUtil.seTuAPIKeys.get(tmpNextKeyIndex));
                tmpNextKeyIndex++;
                break;
            } else {
                tmpNextKeyIndex = 0;
            }
        }
        if (!StringUtils.isEmpty(keyword)) {
            requestData.put("keyword", keyword);
        }
        requestData.put("r18", r18);
        requestData.put("num", num);
        requestData.put("proxy", "i.pixiv.cat");
        requestData.put("size1200", true);
        String jsonData;
        SeTuResponseModel seTuResponseModel;
        try {
            jsonData = HttpUtil.get("https://api.lolicon.app/setu/", requestData);
            seTuResponseModel = JSON.parseObject(jsonData, SeTuResponseModel.class);
        } catch (Exception e) {
            log.error("调用色图服务出错", e);
            return null;
        }
        Optional<SeTuResponseModel.Setu> first = seTuResponseModel.getData().stream().findFirst();
        first.ifPresent(setu -> ThreadUtil.execAsync(() -> {
            Long groupId = ((EventGroupMessage) event).getGroupId();
            //藤原拓海豆腐店
            if (groupId != null && !groupId.equals(BeanUtil.setuQQGroup)) {
                ThreadUtil.sleep(110000);
                BotUtils.delMassage(event.getHttpApi(), event.getMessageId());
//                event.getHttpApi().sendGroupMsg(groupId, "免撤回QQ群：" + BeanUtil.setuQQGroup + "图片地址：\n" + setu.getUrl());
            }
        }));

        BeanUtil.redisUtil.incrBy(RedisUtil.BOT_SETU_COUNT, 1);

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 58);
        BeanUtil.redisUtil.expireAt(RedisUtil.BOT_SETU_COUNT, instance.getTime());


        return seTuResponseModel;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties(Commands.SETU.getCommand());
    }
}
