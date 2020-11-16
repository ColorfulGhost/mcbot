package cc.vimc.mcbot;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


@Log4j2
class McBotApplicationTests {
    @Test
    void contextLoads() throws Exception {
        String cmd[] = {"python", "D:/Python/YuanShen_User_Info/ys_UserInfoGet.py", "108288915"};


        Process exec = Runtime.getRuntime().exec(cmd);
        BufferedReader in = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String s = "";
        while( in.readLine()!= null){
            s += in.readLine();
        }
        log.error(s);
    }


    @Test
    void getYuanShenInfo(){
//        def md5(text):
//        md5 = hashlib.md5()
//        md5.update(text.encode())
//        return md5.hexdigest()

        
//        def DSGet():
//        mhyVersion = "2.1.0"
//        n = md5(mhyVersion)
//        i = str(int(time.time()))
//        r = ''.join(random.sample(string.ascii_lowercase + string.digits, 6))
//        c = md5("salt=" + n + "&t="+ i + "&r=" + r)
//        return i + "," + r + "," + c


    }

}
