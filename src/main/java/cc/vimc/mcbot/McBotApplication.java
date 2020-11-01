package cc.vimc.mcbot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableJpaRepositories("cc.vimc.mcbot.utils")
@EntityScan("cc.vimc.mcbot.utils")
@MapperScan("cc.vimc.mcbot.mapper")
public class McBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(McBotApplication.class, args);
    }

}
