package cc.vimc.mcbot.enums;

public enum ConstantMessages {


    PLAYER_NOT_EXIST("您还没有进行过绑定，无法使用功能。【私聊我】如：/bind yjx4 password"),
    PERMISSION_NOT_FUNOD("您没有权限使用此功能，不如与王哥哥进行PY交易吧！");

    ConstantMessages(String message){
        this.message = message;
    }


    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
