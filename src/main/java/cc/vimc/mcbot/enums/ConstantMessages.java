package cc.vimc.mcbot.enums;

public enum ConstantMessages {


    PLAYER_NOT_EXIST("您还没有进行过绑定，无法使用功能。【私聊我】如：/bind yjx4 password");

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
