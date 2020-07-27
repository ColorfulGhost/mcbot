package cc.vimc.mcbot.bot;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.vimc.mcbot.bot.plugins.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Bot {

    @Value("${coolq.port}")
    private int cqPort;
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


        this.bot = new PicqBotX(config);

        this.bot.addAccount(botName, postURL, cqPort);
        this.bot.enableCommandManager("/");
        this.bot.getEventManager().registerListeners(new MessageListener());
        this.bot.getCommandManager().registerCommands(
                new Help(),
                new ListPlayer(),
                new BindMCAuth(),
                new EditPassword(),
                new Anime(),
                new AntiMotivationalQuotes(),
                new SendRconMessage(),
                new Word(),
                new Bangumi()
        );
        bot.startBot();
    }


}
