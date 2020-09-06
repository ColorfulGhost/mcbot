package cc.vimc.mcbot.bot;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberApprove;
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
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class MessageListener extends IcqListener {

    @EventHandler
    public void eventNoticeGroupMemberApprove(EventNoticeGroupMemberApprove event) {
        //阳炎科技
        if (320510479 == event.getGroupId()) {
            event.getGroupMethods().respond("欢迎新成员~查看置顶群公告，看看如何申请白名单吧~");
        }
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
    public void eventGroupMessage(EventGroupMessage event) {

        for (String sexKeyword : BotUtils.SEX_KEYWORD) {
            if (event.getMessage().contains(sexKeyword)) {
                SeTuResponseModel.Setu setu = new SeTu().seTuAPI("", 2, 1).getData().get(0);
                event.getBotAccount().getHttpApi().sendGroupMsg(event.getGroupId(), new MessageBuilder().add(new ComponentImage(setu.getUrl())).toString());
                return;
            }
        }
        if (event.getMessage().contains("CQ:image")) {
            RegxUtils cqCodeImageRegx = new RegxUtils(event.getMessage());
            String url = cqCodeImageRegx.getCQCodeImageURL();
            File image = new File(cqCodeImageRegx.getCQCodeImageFileName());

            HttpUtil.downloadFile(url, image);
            byte[] byteType = new byte[3];
            BufferedInputStream fileInputStream = FileUtil.getInputStream(image);
            try {
                fileInputStream.read(byteType, 0, byteType.length);
            } catch (IOException e) {
                log.error(e);
            }
            String hex = bytesToHexString(byteType).toUpperCase();
            String suffix = FileType.getSuffix(hex);
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
                for (String sexKeyword : BotUtils.SEX_KEYWORD) {
                    for (String content : contents) {
                        if (content.contains(sexKeyword)) {
                            SeTuResponseModel.Setu setu = new SeTu().seTuAPI("", 2, 1).getData().get(0);
                            event.getBotAccount().getHttpApi().sendGroupMsg(event.getGroupId(), new MessageBuilder().add(new ComponentImage(setu.getUrl())).toString());
                            return;
                        }
                    }
                }
            }

        }

        if (event.getMessage().contains("机器人")) {
            event.getBotAccount().getHttpApi().sendGroupMsg(event.getGroupId(), event.getMessage());

        }


    }
}
