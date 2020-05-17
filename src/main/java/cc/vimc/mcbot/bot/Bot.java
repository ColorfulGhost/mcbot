package cc.vimc.mcbot.bot;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.vimc.mcbot.bot.plugins.BindMCAuth;
import cc.vimc.mcbot.bot.plugins.Help;
import cc.vimc.mcbot.bot.plugins.ListPlayer;
import cc.vimc.mcbot.bot.plugins.SendRconMessage;
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

    @PostConstruct
    public void initBot() {
        PicqConfig config = new PicqConfig(postPort);
        config.setApiAsync(true);
        config.setNoVerify(true);
        config.setDebug(true);

        PicqBotX bot = new PicqBotX(config);

        bot.addAccount(botName, postURL, cqPort);
        bot.enableCommandManager("/");
        bot.getEventManager().registerListeners(new MessageListener());
        bot.getCommandManager().registerCommands(
                new Help(),
                new ListPlayer(),
                new BindMCAuth(),
                new SendRconMessage()
        );
        bot.startBot();
    }


}
