package fr.shurisko.module.voicesystem.loop;

import fr.shurisko.VoiceMaster;
import fr.shurisko.module.voicesystem.discord.AudioReceiver;
import fr.shurisko.utils.VoiceTranslate;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimerTask extends java.util.TimerTask {

    private JDA botInstance;
    public List<Long> userVoiced;
    public AudioReceiver receiver;
    public int sizeLoop = 0;
    public int loopSize = 0;
    boolean isEnable = false;

    public TimerTask(JDA botInstance) {
        this.botInstance = botInstance;
        userVoiced = new ArrayList<>();
        receiver = new AudioReceiver();
    }

    @Override
    public void run() {
        VoiceChannel voiceChannel = botInstance.getVoiceChannelById(VoiceMaster.VOICE_CHANNEL_ID);
        Guild manager = botInstance.getGuildById(VoiceMaster.GUILD_ID);
        AudioManager audioManager = manager.getAudioManager();
        if (isEnable) loopSize ++;
        if (loopSize >= 10) isEnable = false;

        if (!audioManager.isConnected()) {
            audioManager.setReceivingHandler(receiver);
            audioManager.openAudioConnection(voiceChannel);
        }
        if (receiver.list.isEmpty()) return;
        if (receiver.list.size() != sizeLoop) {
            sizeLoop = receiver.list.size();
        } else {
            List<byte[]> content = receiver.getList();
            TextChannel textChannel = manager.getTextChannelById(VoiceMaster.CHANNEL_ID);
            if (!isEnable) {
                if (content.size() >= 50*3) return;
                byte[] voiceContent;
                try {
                    voiceContent = VoiceTranslate.getByteTranslatable(new File("tmp.wav"), content);
                } catch (IOException e) {
                    System.out.printf("Error: [Data convert] %s", e.getMessage());
                    return;
                }
                String wordList = VoiceTranslate.getTranslateFromByteArray(voiceContent);
                for (String str : wordList.split(" ")) {
                    if (str.equalsIgnoreCase("pomme")) {
                        textChannel.sendMessage("Waiting request ...").queue();
                        isEnable = true;
                    }
                }
                loopSize = 0;
            } else {
                if (content.size() >= 50*70) return;
                byte[] voiceContent;
                try {
                    voiceContent = VoiceTranslate.getByteTranslatable(new File("tmp.wav"), content);
                } catch (IOException e) {
                    System.out.printf("Error: [Data convert] %s", e.getMessage());
                    return;
                }
                String conversion = VoiceTranslate.getTranslateFromByteArray(voiceContent);
                if (conversion.isEmpty() || conversion.equalsIgnoreCase(" ")) return;
                textChannel.sendMessage("Request From voice: " + conversion).queue();
                isEnable = false;
            }
        }
    }
}
