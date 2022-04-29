package com.seailz.speedrunevent.game;

import com.seailz.speedrunevent.SpeedrunEvent;
import com.seailz.speedrunevent.core.locale.Locale;
import lombok.Getter;
import lombok.Setter;
import net.agentlv.namemanager.api.NameManagerAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Game {
    private final ArrayList<Player> players;
    private final ArrayList<Player> speedruners;
    private final ArrayList<Player> hunters;
    private final UUID id = UUID.randomUUID();
    private final SpeedrunEvent main = SpeedrunEvent.INSTANCE;

    private boolean attackOn = false;
    private final int headstart;

    public Game(ArrayList<Player> players, ArrayList<Player> speedruners, ArrayList<Player> hunters, int headstart) {
        this.players = players;
        this.speedruners = speedruners;
        this.hunters = hunters;
        this.headstart = headstart;

        for (Player o : players) {
            main.getGames().put(o, this);
        }
    }

    public void start() {
        Locale.STARTED.broadcast();
        SpeedrunEvent.INSTANCE.getServer().getScheduler().runTaskLater(
                SpeedrunEvent.INSTANCE,
                () -> {
                    attackOn = true;
                    Locale.FIGHT_ON.broadcast();
                }, this.headstart
        );

        for (Player o : speedruners) {
            NameManagerAPI.setNametagPrefix(o, ChatColor.translateAlternateColorCodes('&', "&a"));
        }
        for (Player o : hunters) {
            NameManagerAPI.setNametagPrefix(o, ChatColor.translateAlternateColorCodes('&', "&c"));
        }
    }


}
