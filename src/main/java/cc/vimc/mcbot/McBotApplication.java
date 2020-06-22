package cc.vimc.mcbot;


import cc.vimc.mcbot.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("cc.vimc.mcbot.mapper")
public class McBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(McBotApplication.class, args);
    }

}
