package fr.shurisko;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import fr.shurisko.module.voicesystem.loop.TimerTask;
import fr.shurisko.module.voicesystem.VoiceGetter;
import fr.shurisko.module.youtube.BYoutube;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Timer;

public class VoiceMaster extends ListenerAdapter {

    public static VoiceMaster getInstance;
    public JDA botInstance;
    public TimerTask loopTask;
    public BYoutube youtubeQuery;

    public static long GUILD_ID = 793156829672243232L;
    public static long OWNER_ID = 459094461276880897L;
    public static long CHANNEL_ID = 858024943542468618L;
    public static long VOICE_CHANNEL_ID = 921137902627459072L;

    public VoiceMaster() throws LoginException, IOException {
        getInstance = this;
        botInstance = JDABuilder.createDefault("OTg3OTU0NDYwMjU5MTQzNzUw.GWI31e.TgrLfO7R4sBByMZrUDCFtmITK-oHraUyZ996UA",
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_MESSAGES)
                .disableCache(CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();
        botInstance.addEventListener(new VoiceGetter(botInstance, this),
                this);
        youtubeQuery = new BYoutube();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        loopTask = new TimerTask(botInstance);
        new Timer().schedule(loopTask, 0, 1000);
    }

    public static void main(String[] args) throws LoginException, IOException {
                new VoiceMaster();
    }
}
