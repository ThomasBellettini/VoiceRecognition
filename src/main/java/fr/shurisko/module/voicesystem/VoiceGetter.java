package fr.shurisko.module.voicesystem;

import fr.shurisko.VoiceMaster;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VoiceGetter extends ListenerAdapter {

    private JDA botInstance;
    private VoiceMaster mainProgram;

    public VoiceGetter(JDA botInstance, VoiceMaster mainProgram) {
        this.botInstance = botInstance;
        this.mainProgram = mainProgram;
    }

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        if (event.getChannelLeft() != null && event.getChannelJoined() == null)
            mainProgram.loopTask.userVoiced.remove(event.getMember().getIdLong());
        else if (!mainProgram.loopTask.userVoiced.contains(event.getMember().getIdLong()))
           mainProgram.loopTask.userVoiced.add(event.getMember().getIdLong());
    }
}
