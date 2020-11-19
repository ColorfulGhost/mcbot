package cc.vimc.mcbot;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Log4j2
class McBotApplicationTests {
    @Test
    void contextLoads() throws Exception {
        String cmd[] = {"python", "D,/Python/YuanShen_User_Info/ys_UserInfoGet.py", "108288915"};


        Process exec = Runtime.getRuntime().exec(cmd);
        BufferedReader in = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String s = "";
        while (in.readLine() != null) {
            s += in.readLine();
        }
        log.error(s);
    }



}
