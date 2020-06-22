package cc.vimc.mcbot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class Test {

    @RequestMapping("/1")
    public void test() {

        FileReader fileReader = new FileReader("5.0.13-5.0.14");
        String json = fileReader.readString();
        JSONObject jsonObject = JSON.parseObject(json);
    }
}
