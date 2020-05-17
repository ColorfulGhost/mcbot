package cc.vimc.mcbot.api;

import cc.vimc.mcbot.mapper.UserMapper;
import cc.vimc.mcbot.pojo.FlexBleLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class Skin {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/test")
    public void test(){
        List<FlexBleLoginUser> allUser = userMapper.getAllUser();
    }
}
