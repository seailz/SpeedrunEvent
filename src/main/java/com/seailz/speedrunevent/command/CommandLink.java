package com.seailz.speedrunevent.command;

import com.seailz.speedrunevent.SpeedrunEvent;
import com.seailz.speedrunevent.core.config.Options;
import com.seailz.speedrunevent.core.locale.Locale;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "link",
        playerOnly = true,
        args = {"discordid"}
)
public class CommandLink extends Command {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (SpeedrunEvent.CLIENT.getUserById(args[0]) == null) {
            Locale.INVALID_ID.send(sender);
            return;
        }
        Player p = (Player) sender;
        SpeedrunEvent.CLIENT.getUserById(args[0]).openPrivateChannel().flatMap(channel ->
                channel.sendMessage("Please confirm that `" + sender.getName() + "` is you. " +
                        "If it isn't, please click deny!").setActionRow(Button.success("" +
                        "confirm-" + p, "Confirm"), Button.danger("deny", "Deny"))).queue();

    }
}
