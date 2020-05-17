package cc.vimc.mcbot.utils;

public class MessageUtil {

    public static String removeCommandPrefix(String command, String content) {
        return content.replace("/" + command, "").trim();
    }
}
