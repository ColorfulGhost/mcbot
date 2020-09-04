package cc.vimc.mcbot.utils;

import cc.moecraft.icq.command.interfaces.IcqCommand;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RGroup;
import cc.vimc.mcbot.bot.Bot;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@Log4j2
public class BotUtils {

    private static IcqHttpApi icqHttpApi = Bot.bot.getAccountManager().getNonAccountSpecifiedApi();


    public static Long getRandomGroupId() {
        ReturnListData<RGroup> groupList = icqHttpApi.getGroupList();
        if (groupList == null || CollectionUtil.isEmpty(groupList.getData())) {
            return -1L;
        }
        List<RGroup> groupListData = groupList.getData();
        //随机组
        Random randomGroup = new Random();
        RGroup rGroup = groupListData.get(randomGroup.nextInt(groupListData.size()));
        return rGroup.getGroupId();
    }

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

    public static int getRandomSum(int count,int bound) {
        //随机发送
        Random r = new Random();
        int randomSum = 0;
        //100范围内随机循环3次累计
        for (int i = 0; i < count; i++) {
            randomSum += r.nextInt(bound);
        }
        return randomSum;
    }
}
