package com.seailz.speedrunevent.discord.listener;

import com.seailz.speedrunevent.SpeedrunEvent;
import games.negative.framework.message.Message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.Speed;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

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
        else if (e.getButton().getId().equals("speedrun")) {
            boolean linked = false;
            boolean setRole = false;
            String mcName = "";
            for (String s : SpeedrunEvent.CONFIG.getConfigurationSection("data.linked").getKeys(false)) {
                if (SpeedrunEvent.CONFIG.getString("data.linked." + s + ".discord").equals(e.getUser().getId())) {
                    linked = true;
                    mcName = s;
                }
                if (SpeedrunEvent.CONFIG.getString("data.linked." + s + ".type") != null) {
                    setRole = true;
                }
            }
            if (setRole) {
                e.reply("You've already chosen a team!").setEphemeral(true).queue();
                return;
            }
            if (linked) {
                e.reply("You haven't linked your account yet!").setEphemeral(true).queue();
                return;
            }
            if (SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") == 0) {
                e.reply("All the speedrunner slots are taken up! Sorry.").setEphemeral(true).queue();
                return;
            }
            SpeedrunEvent.CONFIG.set("data.speedrunersLeft", SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") -1);
            SpeedrunEvent.CONFIG.set("data.linked." + mcName + ".type", "speedrun");
            SpeedrunEvent.INSTANCE.saveConfig();


            e.getMessage().delete().queue();
            ((ButtonInteractionEvent) event).reply("You are now a hunter!").setEphemeral(true).queue();
            e.getChannel().sendMessageEmbeds(new EmbedBuilder()
                            .setTitle("**Slots**")
                            .setDescription("Please choose a slot, speedruner or hunter! \n" +
                                    "Speedrunner slots left: " + SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") + "" +
                                    "\nHunter slots left: " + "Infinite")
                            .setColor(Color.GREEN)
                            .build()
                    )
                    .setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("speedrun", "Speedrun (" + SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") + ")"),
                            Button.danger("hunter", "Hunter (Infinite)")
                    )
                    .queue();
        }
        else if (e.getButton().getId().equals("hunter")) {
            boolean linked = false;
            boolean setRole = false;
            String mcName = "";
            for (String s : SpeedrunEvent.CONFIG.getConfigurationSection("data.linked").getKeys(false)) {
                if (SpeedrunEvent.CONFIG.getString("data.linked." + s + ".discord").equals(e.getUser().getId())) {
                    linked = true;
                    mcName = s;
                }
                if (SpeedrunEvent.CONFIG.getString("data.linked." + s + ".type") != null) {
                    setRole = true;
                }
            }
            if (setRole) {
                e.reply("You've already chosen a team!").setEphemeral(true).queue();
                return;
            }
            if (linked) {
                e.reply("You haven't linked your account yet!").setEphemeral(true).queue();
                return;
            }
            SpeedrunEvent.CONFIG.set("data.linked." + mcName + ".type", "hunter");
            SpeedrunEvent.INSTANCE.saveConfig();


            e.getMessage().delete().queue();
            e.getChannel().sendMessageEmbeds(new EmbedBuilder()
                            .setTitle("**Slots**")
                            .setDescription("Please choose a slot, speedrunner or hunter! \n" +
                                    "Speedrunner slots left: " + SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") + "" +
                                    "\nHunter slots left: " + "Infinite")
                            .setColor(Color.GREEN)
                            .build()
                    )
                    .setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("speedrun", "Speedrun (" + SpeedrunEvent.CONFIG.getInt("data.speedrunersLeft") + ")"),
                            Button.danger("hunter", "Hunter (Infinite)")
                    )
                    .queue();

            ((ButtonInteractionEvent) event).reply("You are now a hunter!").setEphemeral(true).queue();
        }
    }
}
