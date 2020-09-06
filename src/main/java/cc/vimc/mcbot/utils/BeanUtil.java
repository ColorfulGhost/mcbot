package cc.vimc.mcbot.utils;


import cc.vimc.mcbot.mapper.CoolQSoulMapper;
import cc.vimc.mcbot.mapper.CoolQStatusMapper;
import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.pojo.CoolQUser;
import cc.vimc.mcbot.pojo.FlexBleLoginUser;

public class BeanUtil {
    public static CoolQUserMapper coolQUserMapper = SpringContextUtil.getBean(CoolQUserMapper.class);
    public static  UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);
    public static CoolQSoulMapper coolQSoulMapper = SpringContextUtil.getBean(CoolQSoulMapper.class);
    public static CoolQStatusMapper coolQStatusMapper = SpringContextUtil.getBean(CoolQStatusMapper.class);
    public static RedisUtil redisUtil = SpringContextUtil.getBean(RedisUtil.class);

    /**
     * @Description 验证是否绑定QQ
     * @author wlwang3
     * @param qq
     * @return boolean
     * @date 2020/9/5
     */
    public static boolean verifyNoBindQQ(Long qq) {
        CoolQUser coolQUser = coolQUserMapper.selectQQExist(qq);
        return coolQUser == null;

    }
    /**
     * @Description 查询 FlexBleLoginUser
     * @author wlwang3
     * @param flexbleloginId
     * @return cc.vimc.mcbot.pojo.FlexBleLoginUser
     * @date 2020/9/5
     */
    public static FlexBleLoginUser  getUserBaseInfoByUserId(int flexbleloginId){
        return userMapper.getUserBaseInfoByUserId(flexbleloginId);

    }

    public static String getSoul(){
        return coolQSoulMapper.getSoul().getTitle();
    }


}
