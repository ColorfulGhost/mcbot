package cc.vimc.mcbot;

import cc.vimc.mcbot.bot.Bot;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class McBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(McBotApplication.class, args);
    }

}
