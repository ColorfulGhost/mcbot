package cc.vimc.mcbot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@MapperScan("cc.vimc.mcbot.mapper")
public class McBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(McBotApplication.class, args);
    }

}
