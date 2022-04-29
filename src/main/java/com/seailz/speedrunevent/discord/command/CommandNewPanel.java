package com.seailz.speedrunevent.discord.command;

import com.seailz.speedrunevent.SpeedrunEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CommandNewPanel implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (!(event instanceof SlashCommandInteractionEvent)) return;
        SlashCommandInteractionEvent e = (SlashCommandInteractionEvent) event;
        if (!e.getName().equals("newpanel")) return;
        if (!e.getMember().hasPermission(Permission.MANAGE_CHANNEL)) return;

        e.getChannel().sendMessageEmbeds(new EmbedBuilder()
                        .setTitle("**Slots**")
                        .setDescription("Please choose a slot, speedruner or hunter! \n" +
                                "Speedunner slots left: " + SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") + "" +
                                "\nHunter slots left: " + "Infinite")
                        .setColor(Color.GREEN)
                        .build()
        )
                .setActionRow(Button.success("speedrun", "Speedrun (" + SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") + ")"),
                        Button.danger("hunter", "Hunter (Infinite)")
                        )
                .queue();
    }
}
