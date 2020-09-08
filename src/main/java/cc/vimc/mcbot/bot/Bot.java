package cc.vimc.mcbot.bot;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Bot {

    @Value("${coolq.port}")
    private int cqPort;
    @Value("${coolq.port.test}")
    private int cqPortTest;
    @Value("${coolq.bot.name}")
    private String botName;
    @Value("${coolq.post.port}")
    private int postPort;
    @Value("${coolq.post.url}")
    private String postURL;

    public static PicqBotX bot;

    @PostConstruct
    public void initBot() {
        PicqConfig config = new PicqConfig(postPort);
        config.setApiAsync(true);
        config.setNoVerify(true);
        config.setDebug(true);


        bot = new PicqBotX(config);

        bot.addAccount(botName, postURL, cqPort);
//        bot.addAccount(botName, postURL, cqPortTest);
        bot.enableCommandManager("/");
        bot.getEventManager().registerListeners(new MessageListener());
        bot.getCommandManager().registerAllCommands("cc.vimc.mcbot.bot.plugins");
        bot.startBot();
    }


}
