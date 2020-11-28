package cc.vimc.mcbot.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CoolQKeyWord {
    private Integer id;
    private String keyWord;
    private String answer;
    private Integer matchType;
    private Long qq;
    private Date createTime;
}
