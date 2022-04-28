package com.seailz.speedrunevent.discord.listener;

import com.seailz.speedrunevent.SpeedrunEvent;
import games.negative.framework.message.Message;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ButtonClick implements EventListener {
    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if (!(event instanceof ButtonInteractionEvent)) return;
        ButtonInteractionEvent e = (ButtonInteractionEvent) event;
        if (e.getButton().getId().startsWith("confirm-")) {
            OfflinePlayer linked = Bukkit.getPlayer(e.getButton().getId().replaceAll("confirm-", ""));
            if (linked.isOnline()) {
                Player p = (Player) linked;
                new Message("Your account has been linked!").send(p);
            }
            SpeedrunEvent.CONFIG.set("data.linked." + linked.getUniqueId() + ".discord", e.getUser().getId());
            SpeedrunEvent.INSTANCE.saveConfig();
            ((ButtonInteractionEvent) event).reply("Confirmed!").setEphemeral(true).queue();
        }
        else if (e.getButton().getId().equals("deny")) {
            ((ButtonInteractionEvent) event).reply("Noted!").queue();
        }
    }
}
