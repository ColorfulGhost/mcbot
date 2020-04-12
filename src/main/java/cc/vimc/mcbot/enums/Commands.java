package cc.vimc.mcbot.enums;

import lombok.Data;

public enum Commands {
    HELP("help", "查看小叽帮助(<ゝω·)☆"),
    WHITELIST("whitelist", "Minecraft服务器白名单申请"),
    REPEAT("repeat", "人类的本质是复读机，所以我变的像人类了嘛？"),
    RCON("rcon", "Minecraft服务端RCON命令（管理员权限）"),
    TG("tg", "与Telegram上主人对话，如：/tg 主人，好梦~"),
    LIKE("like", "小叽给您点赞啦~");


    private String command;
    private String description;

    Commands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
