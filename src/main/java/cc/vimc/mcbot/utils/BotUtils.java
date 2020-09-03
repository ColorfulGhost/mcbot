package cc.vimc.mcbot.utils;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.sender.IcqHttpApi;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Log4j2
public class BotUtils {

    public static String removeCommandPrefix(String command, String content) {
        return content.replace("/" + command, "").trim();
    }

    public static void delMassageForMs(IcqHttpApi icqHttpApi, Long messageId, int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error(e);
        }
        icqHttpApi.deleteMsg(messageId);
    }

    public static IcqCommand[] getAllPlugins() {
        Set<Class<?>> classes = ClassUtil.scanPackage("cc.vimc.mcbot.bot.plugins");
        Set<IcqCommand> pluginsClazz = new HashSet<>();
        for (Class<?> aClass : classes) {
            try {
                IcqCommand icqCommand = (IcqCommand) aClass.newInstance();
                pluginsClazz.add(icqCommand);
            } catch (InstantiationException e) {
                log.error(e);
            } catch (IllegalAccessException e) {
                log.error(e);
            }
        }
        IcqCommand[] icqCommands = new IcqCommand[pluginsClazz.size()];
        return pluginsClazz.toArray(icqCommands);

    }
}
