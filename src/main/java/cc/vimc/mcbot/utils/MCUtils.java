package cc.vimc.mcbot.utils;


import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.pojo.CoolQUser;


public class MCUtils {

    public static boolean verifyNoBindQQ(Long qq) {
        CoolQUserMapper coolQUserMapper = SpringContextUtil.getBean(CoolQUserMapper.class);
        CoolQUser coolQUser = coolQUserMapper.selectQQExist(qq);
        return coolQUser == null;

    }

}
