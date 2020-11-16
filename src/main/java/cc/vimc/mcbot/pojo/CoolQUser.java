package cc.vimc.mcbot.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class CoolQUser {
    private Long qq;
    private String userName;
    private Integer flexbleloginId;
    private String skin;
    private Date createTime;
    private String uuid;
    private String yuanShenUID;
}
