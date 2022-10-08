package fr.shurisko.module.command;

import fr.shurisko.VoiceMaster;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinCommand extends ListenerAdapter {

    public JDA botInstance;

    public JoinCommand(JDA botInstance) {
        this.botInstance = botInstance;

        botInstance.getGuildById(VoiceMaster.GUILD_ID)
                .upsertCommand("join", "Ask bot ot join the channel").queue();
    }
}
