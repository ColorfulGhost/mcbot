package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class RetModel<T> {
    @JSONField(name="retcode")
    private int retCode;
    private String message;
    private T data;
}
