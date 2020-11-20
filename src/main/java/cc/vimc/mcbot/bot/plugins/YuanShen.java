package cc.vimc.mcbot.bot.plugins;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.sender.message.MessageBuilder;
import cc.moecraft.icq.user.User;
import cc.vimc.mcbot.enums.Commands;
import cc.vimc.mcbot.pojo.*;
import cc.vimc.mcbot.utils.BeanUtil;
import cc.vimc.mcbot.utils.BotUtils;
import cc.vimc.mcbot.utils.RegxUtils;
import cc.vimc.mcbot.utils.UUIDUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class YuanShen implements EverywhereCommand {
    /**
     * 绑定账号
     */
    private static final String BIND = "bind";
    /**
     * 体力设置
     */
    private static final String SP = "sp";
    /**
     * 角色状态
     */
    private static final String STATUS = "status";

    private static final String APP_VERSION = "2.1.0";

    private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 9; Unspecified Device) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Mobile Safari/537.36 miHoYoBBS/" + APP_VERSION;

    private static final String INDEX_URL = "https://webstatic.mihoyo.com/bbs/event/signin-ys/index.html";

    private static final String ACCEPT_ENCODING = "gzip, deflate, br";
    /**
     * 签到
     */
    private static final String ACTID = "e202009291139501";

    private static final String REFERER = INDEX_URL + "?bbs_auth_required=true&act_id=" + ACTID + "&utm_source=bbs&utm_medium=mys&utm_campaign=icon";


    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        String preCommand = BotUtils.removeCommandPrefix(Commands.YUANSHEN.getCommand(), event.getMessage());
        List<String> preCommandList = Arrays.asList(preCommand.split(" "));
        MessageBuilder messageBuilder = new MessageBuilder();
        if (CollectionUtil.isEmpty(preCommandList)) {
            return null;
        }
        switch (preCommandList.get(0)) {
            case BIND:
                if (BeanUtil.verifyNoBindQQ(sender.getId())) {
                    BeanUtil.coolQUserMapper.insertYuanshenUser(sender.getId(), preCommandList.get(1));
                    return "绑定成功";
                } else {
                    BeanUtil.coolQUserMapper.updateYuanShenUID(preCommandList.get(1), sender.getId());
                    return "更新成功";
                }
            case SP:
                return SP(preCommandList, messageBuilder);
            case STATUS:
                if (preCommandList.size() < 2) {
                    CoolQUser coolQUser = BeanUtil.coolQUserMapper.selectForQQ(sender.getId());

                    if (coolQUser == null || StringUtils.isEmpty(coolQUser.getYuanshenUid())) {
                        return "您没有绑定无法直接查询";
                    }
                    return getStatus(coolQUser.getYuanshenUid());
                } else {
                    String qq = new RegxUtils(preCommandList.get(1)).getQQ();
                    if (!StringUtils.isEmpty(qq)) {
                        CoolQUser coolQUser = BeanUtil.coolQUserMapper.selectForQQ(Long.valueOf(qq));
                        return getStatus(coolQUser.getYuanshenUid());
                    }
                    return getStatus(preCommandList.get(1));
                }
            default:
                break;
        }
        return null;
    }

    private String SP(List<String> preCommandList, MessageBuilder messageBuilder) {
        List<String> spList = Arrays.asList(preCommandList.get(1).split("-"));
        if (CollectionUtil.isEmpty(spList)) {
            return null;
        }
        Integer nowSp = Integer.parseInt(spList.get(0));
        Integer afterSp = Integer.valueOf(spList.get(1));
        int preSp = afterSp - nowSp;
        if (preSp <= 0) {
            return null;
        }
        //1分钟8体力*预计恢复体力 = 总恢复时间
        int preSpRestoreMin = preSp * 8;

        if (preSpRestoreMin > Integer.MAX_VALUE) {
            return messageBuilder.add("你在测试我的MAX_VALUE?").toString();
        }
        ;
        Date date = new Date(System.currentTimeMillis() + 60000 * preSpRestoreMin);
        String formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        messageBuilder.add("预计").add(formatDate).add("恢复到").add(spList.get(1)).add("体力.");
        return messageBuilder.toString();
    }

    /**
     * @return java.lang.String
     * @Description DS算法
     * @author Ghost
     * @date 2020/11/19
     */
    private String DSGet() {
        String randomStr = "abcdefghijklmnopqrstuvwxyz0123456789";
        String n = MD5.create().digestHex(APP_VERSION);
        String i = NumberUtil.roundStr(System.currentTimeMillis() / 1000.0, 0);
        String r = "";
        for (int i1 = 0; i1 < 6; i1++) {
            r += Character.toString(RandomUtil.randomChar(randomStr));
        }
        String c = MD5.create().digestHex("salt=" + n + "&t=" + i + "&r=" + r);
        return i + "," + r + "," + c;
    }

    /**
     * @param UID
     * @return java.lang.String
     * @Description 获取原神个人信息接口
     * @author Ghost
     * @date 2020/11/19
     */
    private String getYuanShenInfo(String UID) {
        HttpRequest request = HttpUtil.createGet("https://api-takumi.mihoyo.com/game_record/genshin/api/index?server=cn_gf01&role_id=" + UID);
        request.header("Accept", "application/json, text/plain, */*");
        request.header("DS", DSGet());
        request.header("Origin", "https://webstatic.mihoyo.com");
        request.header("x-rpc-app_version", APP_VERSION);
        request.header("User-Agent", USER_AGENT);
        request.header("x-rpc-client_type", "4");
        request.header("Referer", "https://webstatic.mihoyo.com/app/community-game-records/index.html?v=6");
        request.header("Accept-Encoding", ACCEPT_ENCODING);
        request.header("Accept-Language", "zh-CN,en-US;q=0.8");
        request.header("X-Requested-With", "com.mihoyo.hyperion");
        HttpResponse execute = request.execute();
        return execute.body();
    }


    private String[] proxyIpAndPort() {
        String proxyDataStr = HttpUtil.get("http://vimc.cc:5010/get/");
        ProxyData proxyData = JSONObject.parseObject(proxyDataStr, ProxyData.class);
        String proxy = proxyData.getProxy();
        return proxy.split(":");


    }

    private RetModel<UserGameRoles> getUserGameRolesByCookie(String cookie) {
        HttpRequest get = HttpUtil.createGet("https://api-takumi.mihoyo.com/binding/api/getUserGameRolesByCookie?game_biz=hk4e_cn");
        get.header("User-Agent", USER_AGENT);
        get.header("Referer", USER_AGENT);
        get.header("Accept-Encoding", ACCEPT_ENCODING);
        get.header("Referer", REFERER);
        get.header("Cookie", cookie);
        get.header("DS", DSGet());
        HttpResponse httpResponse = get.executeAsync();
        String result = httpResponse.body();
        return JSONObject.parseObject(result, new TypeReference<RetModel<UserGameRoles>>() {
        });
    }

    private void sendYuanShenSign() {
        String cookie = "";
        RetModel<UserGameRoles> userGameRolesByCookie = getUserGameRolesByCookie(cookie);
        if (userGameRolesByCookie.getRetCode() != 0) {
            return;
        }
        ListItem listItem = userGameRolesByCookie.getData().getList().stream().findFirst().get();
        HttpRequest post = HttpRequest.post("https://api-takumi.mihoyo.com/event/bbs_sign_reward/sign");

        post.header("x-rpc-device_id", UUIDUtil.uuid3(UUIDUtil.NAMESPACE_URL, cookie).toString().replace("-", "").toUpperCase());
        post.header("x-rpc-client_type", "5");
        post.header("Accept-Encoding", ACCEPT_ENCODING);
        post.header("User-Agent", USER_AGENT);
        post.header("Referer", REFERER);
        post.header("x-rpc-app_version", APP_VERSION);
        post.header("DS", DSGet());
        post.header("Cookie", cookie);

        Map<String, Object> args = new HashMap<>();
        args.put("act_id", ACTID);
        args.put("region", listItem.getRegion());
        args.put("uid", listItem.getGameUid());
        post.body(JSON.toJSONString(args));

        HttpResponse execute = post.execute();
        String body = execute.body();

    }


    /**
     * @param UID 原神uid
     * @return java.lang.String
     * @Description 组装数据
     * @author wlwang3
     * @date 2020/11/19
     */
    private String getStatus(String UID) {
//        String stringJSON = HttpUtil.get("https://service-joam13r8-1252025612.gz.apigw.tencentcs.com/uid/" + UID);
        RetModel<YuanShenUserInfo> yuanShenUserInfoRetModel = JSONObject.parseObject(getYuanShenInfo(UID), new TypeReference<RetModel<YuanShenUserInfo>>() {
        });
        if (!"OK".equals(yuanShenUserInfoRetModel.getMessage())) {
            return "奇怪...查不到该玩家信息。是不是没有启用米游社权限?或绑定了错误的UID?";
        }
        YuanShenUserInfo yuanShenUserInfo = yuanShenUserInfoRetModel.getData();

        StringBuilder avatarsResult = new StringBuilder();
        StringBuilder statusResult = new StringBuilder();
        StringBuilder cityExplorationsResult = new StringBuilder();

        Stats stats = yuanShenUserInfo.getStats();
        statusResult.append("活跃天数：").append(stats.getActiveDayNumber()).append(" | ");
        statusResult.append("成就达成数：").append(stats.getAchievementNumber()).append("\n");
        statusResult.append("风神瞳：").append(stats.getAnemoculusNumber()).append(" | ");
        statusResult.append("岩神瞳：").append(stats.getGeoculusNumber()).append("\n");

        statusResult.append("获得角色数：").append(stats.getAvatarNumber()).append(" | ");
        statusResult.append("解锁传送点：").append(stats.getWayPointNumber()).append("\n");
        statusResult.append("解锁秘境：").append(stats.getDomainNumber()).append(" | ");
        statusResult.append("深境螺旋：").append(stats.getSpiralAbyss()).append("\n");

        statusResult.append("华丽宝箱数：").append(stats.getLuxuriousChestNumber()).append(" | ");
        statusResult.append("珍贵宝箱数：").append(stats.getPreciousChestNumber()).append("\n");
        statusResult.append("精致宝箱数：").append(stats.getExquisiteChestNumber()).append(" | ");
        statusResult.append("普通宝箱数：").append(stats.getCommonChestNumber()).append("\n");

        statusResult.append("-----------------\n");

        List<AvatarsItem> avatars = yuanShenUserInfo.getAvatars();
        boolean tempBoolean = false;
        for (AvatarsItem avatar : avatars) {
            avatarsResult
                    .append(avatar.getRarity() == 5 ? "⭐" : "")
                    .append(avatar.getName()).append(" ")
                    .append("等级：").append(avatar.getLevel()).append(" | ")
                    .append("好感度：").append(avatar.getFetter())
                    .append("\n");
//                    .append(tempBoolean ? "\n" : " | ");
            tempBoolean = !tempBoolean;
        }

        avatarsResult.append("-----------------\n");

        for (CityExplorationsItem cityExploration : yuanShenUserInfo.getCityExplorations()) {
            cityExplorationsResult.append("城市：").append(cityExploration.getName())
                    .append("声望等级：").append(cityExploration.getLevel())
                    .append("探索度：").append(cityExploration.getExplorationPercentage() / 10.0)
                    .append("%").append("\n");
        }

        return avatarsResult.toString() + statusResult.toString() + cityExplorationsResult.toString();
    }

    @Override

    public CommandProperties properties() {
        return new CommandProperties(Commands.YUANSHEN.getCommand());
    }

}
