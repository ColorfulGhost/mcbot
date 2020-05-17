package cc.vimc.mcbot;


import cc.vimc.mcbot.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cc.vimc.mcbot.mapper")
public class McBotApplication {

    @Autowired
    UserMapper userMapper;
    public static void main(String[] args) {
        SpringApplication.run(McBotApplication.class, args);
    }

}
