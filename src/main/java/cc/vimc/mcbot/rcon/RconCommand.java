package cc.vimc.mcbot.rcon;

import cc.vimc.mcbot.exception.AuthenticationException;

import java.io.IOException;

public class RconCommand {

    public static String send(String command) {
        String result;
        try {
            Rcon rcon = new Rcon("192.168.1.220", 25575, "sdfg84RCON".getBytes());
            rcon.command(command);
            result = rcon.command(command);

        } catch (IOException | AuthenticationException e) {
            result = "Rcon连接出错！" + e.getMessage();
        }
        return result;

    }
}
