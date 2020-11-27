package cc.vimc.mcbot.bot;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.notice.groupmember.EventNoticeGroupMemberChange;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberApprove;
import cc.moecraft.icq.event.events.request.EventGroupInviteRequest;
import cc.moecraft.icq.event.events.request.EventRequest;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.vimc.mcbot.bot.plugins.SeTu;
import cc.vimc.mcbot.enums.FileType;
import cc.vimc.mcbot.pojo.SeTuResponseModel;
import cc.vimc.mcbot.utils.BeanUtil;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.RedisUtil;
import cc.vimc.mcbot.utils.RegxUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class MessageListener extends IcqListener {
    //复读群和消息
    private Map<Long, String> repeater = new ConcurrentHashMap<>();
    //准备复读状态
    private Map<Long, Boolean> readyRepeaterStatus = new ConcurrentHashMap<>();

    @EventHandler
    public void eventNoticeGroupMemberApprove(EventRequest event) {
        event.accept();
        ThreadUtil.sleep(1000);
        event.getHttpApi().getBot().getAccountManager().refreshCache();
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @EventHandler
    @Async
    public void eventGroupMessage(EventGroupMessage event) {
        IcqHttpApi httpApi = event.getBotAccount().getHttpApi();
        Long groupId = event.getGroupId();
        String message = event.getMessage();
//            if (message.contains("机器人")) {
//                httpApi.sendGroupMsg(groupId, message);

//            } else {
        //复读状态里有没有这个组
//                if (repeater.containsKey(groupId) && repeater.get(groupId).equals(message)) {
//                    //拿取上次保存的message和本次message对比 和之前有没有复读过
//                    if (readyRepeaterStatus.getOrDefault(groupId, false)) {
//                        httpApi.sendGroupMsg(groupId, message);
//                        //关闭复读状态
//                        readyRepeaterStatus.put(groupId, false);
//                        return;
//                    }
//                } else {
//                    //如果不是复读 则缓存并且随时准备复读
//                    repeater.put(groupId, message);
//                    readyRepeaterStatus.put(groupId, true);
//
//                }
        for (String sexKeyword : BotUtils.LSB_KEYWORD) {
            if (message.contains(sexKeyword)) {
                SeTuResponseModel.Setu setu = new SeTu().seTuApi("", 2, 1, event).getData().get(0);
                httpApi.sendGroupMsg(groupId, new MessageBuilder().add(new ComponentImage(setu.getUrl())).toString());
                return;
            }
        }
//            }


        RegxUtils cqCodeImageRegx = new RegxUtils(message);
        String url = cqCodeImageRegx.getCQCodeImageURL();

        if (StringUtils.isEmpty(url) && message.contains("CQ:image")) {
            return;
        }

        File image = FileUtil.file(cqCodeImageRegx.getCQCodeImageFileName());
        HttpUtil.downloadFile(url, image);
        //图片>200kb不处理
        long imageKB = FileUtil.size(image) / 1024;
        if (imageKB > 200) {
            return;
        }

        byte[] byteType = new byte[3];
        BufferedInputStream fileInputStream = FileUtil.getInputStream(image);
        try {
            fileInputStream.read(byteType, 0, byteType.length);
        } catch (IOException e) {
            log.error(e);
        }

        String hex = bytesToHexString(byteType).toUpperCase();
        String suffix = FileType.getSuffix(hex);
        try {
            fileInputStream.close();
        } catch (IOException e) {
            log.error(e);
        }
        //ocr内容
        List<String> contents;
        if (suffix.equals(FileType.JPG.getSuffix()) || suffix.equals(FileType.PNG.getSuffix())) {

            //拿取文件md5
            String imageMD5 = SecureUtil.md5(image);
            //从redis获取缓存
            String imageContentList = BeanUtil.redisUtil.get(RedisUtil.BOT_IMAGE_MD5 + imageMD5);
            String imageContentJSON;
            //拿不到则OCR
            if (StringUtils.isEmpty(imageContentList)) {
                HashMap<String, Object> args = new HashMap<>();
                args.put("image", image);
                imageContentJSON = HttpUtil.post("http://192.168.1.220:88/doOCR", args);
                Map<String, List<String>> md5ForContent = JSONObject.parseObject(imageContentJSON, Map.class);

                //删除文件
                FileUtil.del(cqCodeImageRegx.getCQCodeImageFileName());
                if (MapUtil.isEmpty(md5ForContent)) {
                    return;
                }
                //拿到存入缓存
                contents = md5ForContent.get(imageMD5);
                if (CollectionUtil.isEmpty(contents)) {
                    return;
                }
                BeanUtil.redisUtil.set(RedisUtil.BOT_IMAGE_MD5 + imageMD5, JSON.toJSONString(contents));
            } else {
                contents = JSON.parseArray(imageContentList, String.class);
            }
            //有交集则发涩图
            for (String keyword : BotUtils.LSB_KEYWORD) {
                for (String content : contents) {
                    if (content.contains(keyword)) {
                        SeTuResponseModel.Setu setu = new SeTu().seTuApi("", 2, 1, event).getData().get(0);
                        httpApi.sendGroupMsg(groupId, new MessageBuilder().add(new ComponentImage(setu.getUrl())).toString());
                        return;
                    }
                }
            }
        }
    }
}
