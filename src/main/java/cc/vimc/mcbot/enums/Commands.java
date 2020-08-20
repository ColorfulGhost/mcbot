package cc.vimc.mcbot.enums;

public enum Commands {
    /**
     * 命令全部用小写
     */
    HELP("help", "查看小叽帮助(<ゝω·)☆"),
    BANGUMI("bangumi", "开启新番更新提醒(大陆未购买版权则不会提醒)，排除你不喜欢的新番如:/bangumi rm 240835"),
    SETU("setu", "涩图！可加关键字，r18五五开检索。如：/setu yjx4"),
    AMQ("amq","到点了，网抑云开启 (AMQ <anti-motivational quotes>)"),
    START_SERVER("startserver","开启服务端，现在支持服务端：AcademyCraft 如：/startserver AcademyCraft"),
    ANIME("anime","以图搜番 食用方法：/anime [图片]"),
    LIST("list", "查看在线玩家列表w"),
    RCON("rcon", "Minecraft服务端RCON命令（管理员权限）"),
//    STATISTIC("statistic", "获取玩家在线统计数据"),
    BIND("bind", "QQ绑定Minecraft账号。私聊我，如：/bind yjx4 password"),
    EDIT_PASSWORD("password", "修改Minecraft账户密码，私聊我，如：/password yjx4prprpr");
//    WORD("word", "填充语料库，正则测试网站：https://regexr.com/ ，正则表达式匹配(Java正则)。" +
//            "\n如精准匹配：/word exact yjx4のドレス何色なの？ 可愛いの色 " +
//            "\n如模糊匹配：/world fuzzy ドレス何色なの 可愛いの色" +
//            "\n如正则匹配：/world regex .*yjx4.* 萌え！！" +
//            "\n精准匹配>模糊匹配>正则匹配，如：Q: yjx4 A:萌え！！; Q:ドレス何色なの A:可愛いの色");
//    REPEAT("repeat", "人类的本质是复读机，所以我变的像人类了嘛？"),
//    TG("tg", "与Telegram上主人对话，如：/tg 主人，好梦~"),
//    LIKE("like", "小叽给您点赞啦~");


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
