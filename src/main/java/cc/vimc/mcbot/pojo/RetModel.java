package cc.vimc.mcbot.pojo;

import lombok.Data;

@Data
public class RetModel<T> {
    private int retCode;
    private String message;
    private T data;
}
