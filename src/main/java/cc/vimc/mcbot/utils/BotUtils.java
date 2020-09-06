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

import java.util.*;
import java.util.stream.Collectors;

@Component
@Log4j2
public class BotUtils {

    private static IcqHttpApi icqHttpApi = Bot.bot.getAccountManager().getNonAccountSpecifiedApi();


    public static final String[] SEX_KEYWORD = {"开车", "涩图", "色图", "营养", "开冲","够色"};

    /**
     * @param
     * @return java.util.List<java.lang.Long>
     * @Description 获取所有QQ群
     * @author wlwang3
     * @date 2020/9/6
     */
    public static List<Long> getAllGroup() {

        ReturnListData<RGroup> groupList = icqHttpApi.getGroupList();
        if (groupList == null || CollectionUtil.isEmpty(groupList.getData())) {
            return Collections.emptyList();
        }
        return groupList.getData().stream().map(data -> data.getGroupId()).collect(Collectors.toList());
    }

    /**
     * @param
     * @return java.lang.Long
     * @Description 随机拿取QQ群
     * @author wlwang3
     * @date 2020/9/6
     */
    public static Long getRandomGroupId() {

        //随机组
        Random randomGroup = new Random();
        List<Long> allGroup = getAllGroup();
        return allGroup.get(randomGroup.nextInt(allGroup.size()));
    }

    /**
     * @param command
     * @param content
     * @return java.lang.String
     * @Description 删除命令字符
     * @author wlwang3
     * @date 2020/9/6
     */
    public static String removeCommandPrefix(String command, String content) {

        return content.replace("/" + command, "").trim();
    }

    @Deprecated
    public static void delMassageForMs(IcqHttpApi icqHttpApi, Long messageId, int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error(e);
        }
        icqHttpApi.deleteMsg(messageId);
    }

    /**
     * @param
     * @return cc.moecraft.icq.command.interfaces.IcqCommand[]
     * @Description 实例化Plugins下所有插件
     * @author wlwang3
     * @date 2020/9/6
     */
    public static IcqCommand[] getAllPlugins() {

        Set<Class<?>> classes = ClassUtil.scanPackage("cc.vimc.mcbot.bot.plugins");
        Set<IcqCommand> pluginsClazz = new HashSet<>();
        for (Class<?> aClass : classes) {
            try {
                IcqCommand icqCommand = (IcqCommand) aClass.newInstance();
                pluginsClazz.add(icqCommand);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e);
            }
        }
        return pluginsClazz.toArray(new IcqCommand[pluginsClazz.size()]);

    }

    public static int getRandomSum(int count, int bound) {
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
