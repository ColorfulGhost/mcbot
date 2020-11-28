package cc.vimc.mcbot.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ghost
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("coolq_group")
public class CoolQGroup {
    private Long groupId;
    private boolean setuRandom;
    private boolean bangumi;
    private boolean setu;
    /**
     * 操作人QQ
     */
    private Long lastOperate;

}
