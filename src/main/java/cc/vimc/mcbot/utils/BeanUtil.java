package cc.vimc.mcbot.utils;


import cc.vimc.mcbot.api.MqttGateway;
import cc.vimc.mcbot.mapper.CoolQSoulMapper;
import cc.vimc.mcbot.mapper.CoolQStatusMapper;
import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.entity.CoolQUser;
import cc.vimc.mcbot.entity.FlexBleLoginUser;

import java.util.Arrays;
import java.util.List;

public class BeanUtil {
    public static CoolQUserMapper coolQUserMapper = SpringContextUtil.getBean(CoolQUserMapper.class);
    public static  UserMapper userMapper = SpringContextUtil.getBean(UserMapper.class);
    public static CoolQSoulMapper coolQSoulMapper = SpringContextUtil.getBean(CoolQSoulMapper.class);
    public static CoolQStatusMapper coolQStatusMapper = SpringContextUtil.getBean(CoolQStatusMapper.class);
    public static RedisUtil redisUtil = SpringContextUtil.getBean(RedisUtil.class);
    public static Long setuQQGroup = Long.valueOf( SpringContextUtil.getEnvProperty("setu.qq.group"));
    public static List<String> seTuAPIKeys = Arrays.asList(SpringContextUtil.getEnvProperty("setu.api.key").split(","));
    public static String apiKey = SpringContextUtil.getEnvProperty("MCSM.api.admin.key");
    public static  String url = SpringContextUtil.getEnvProperty("MCSM.api.url");

    public static MqttGateway mqttGateway = SpringContextUtil.getBean(MqttGateway.class);


    /**
     * @Description 验证是否绑定QQ
     * @author wlwang3
     * @param qq
     * @return boolean
     * @date 2020/9/5
     */
    public static boolean verifyNoBindQQ(Long qq) {
        CoolQUser coolQUser = coolQUserMapper.selectForQQ(qq);
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
