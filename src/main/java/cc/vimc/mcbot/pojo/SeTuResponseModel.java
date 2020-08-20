package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class SeTuResponseModel {
    //https://api.lolicon.app/#/setu?id=telegram-bot
    private int code;
    private String msg;
    private int quota;
    @JSONField(name = "quota_min_ttl")
    private int quotaMinTTL;
    private int count;
    private List<Setu> data;

    @Data
    public static class Setu {
        private int pid;
        private int p;
        private int uid;
        private String title;
        private String author;
        private String url;
        private boolean r18;
        private int width;
        private int height;
        private List<String> tags;

    }
}
