package cc.vimc.mcbot.pojo;


import lombok.Data;

import java.util.Date;


@Data
public class FlexBleLoginUser {
    private int userId;
    private byte[] uuid;
    private String userName;
    private String password;
    private Date lastLogin;
    private String email;
    private int loggedIn;
}
