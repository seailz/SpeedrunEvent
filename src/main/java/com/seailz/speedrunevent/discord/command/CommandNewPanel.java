package com.seailz.speedrunevent.discord.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class CommandNewPanel implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (!(event instanceof SlashCommandInteractionEvent)) return;
        SlashCommandInteractionEvent e = (SlashCommandInteractionEvent) event;
        if (!e.getName().equals("newHub")) return;
        if (!e.getMember().hasPermission(Permission.MANAGE_CHANNEL)) return;


    }
}
