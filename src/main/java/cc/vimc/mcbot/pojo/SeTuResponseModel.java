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
    private List count;
}
