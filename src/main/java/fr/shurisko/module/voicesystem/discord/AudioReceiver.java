package fr.shurisko.module.voicesystem.discord;

import fr.shurisko.VoiceMaster;
import net.dv8tion.jda.api.audio.AudioReceiveHandler;
import net.dv8tion.jda.api.audio.UserAudio;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioReceiver implements AudioReceiveHandler {

    public final List<byte[]> list = new ArrayList<>();

    @Override
    public boolean canReceiveUser() {
        if (this.list.size() >= 50*100) this.list.clear();
        return true;
    }

    @Override
    public void handleUserAudio(@NotNull UserAudio userAudio) {
        if (userAudio.getUser().getIdLong() != VoiceMaster.OWNER_ID) return;
        list.add(userAudio.getAudioData(1.0f));
    }

    public List<byte[]> getList() {
        final List<byte[]> copy = new ArrayList<>(list);
        this.list.clear();
        return copy;
    }
}
