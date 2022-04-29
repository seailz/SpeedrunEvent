package com.seailz.speedrunevent;

import com.seailz.speedrunevent.command.CommandLink;
import com.seailz.speedrunevent.core.command.CommandDetailedReport;
import com.seailz.speedrunevent.core.config.Options;
import com.seailz.speedrunevent.core.log.Logger;
import com.seailz.speedrunevent.discord.command.CommandNewPanel;
import com.seailz.speedrunevent.game.Game;
import games.negative.framework.BasePlugin;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description 1.0 Main Class
 * @author Seailz
 */
public final class SpeedrunEvent extends BasePlugin {

    @Getter
    @Setter
    public static SpeedrunEvent INSTANCE;
    @Getter
    @Setter
    public static JDA CLIENT;
    @Getter
    @Setter
    public static FileConfiguration CONFIG;
    @Getter
    boolean debug;
    @Getter
    private int minorErrors;
    @Getter
    private int severeErrors;
    @Getter
    private ArrayList<String> debugLog;
    @Getter
    @Setter
    private HashMap<Player, Game> games = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        saveDefaultConfig();
        INSTANCE = this;
        CONFIG = this.getConfig();
        try {
            CLIENT = JDABuilder.createDefault(Options.TOKEN).build();
            CLIENT.upsertCommand("newpanel", "Create a new panel").queue();
            CLIENT.addEventListener(new CommandNewPanel());
        } catch (LoginException e) {
            Logger.log(Logger.LogLevel.ERROR, "The token is invalid!");
            Logger.log(Logger.LogLevel.ERROR, "The token is invalid!");
            Logger.log(Logger.LogLevel.ERROR, "The token is invalid!");
        }

        registerCommands(
                new CommandDetailedReport(this),
                new CommandLink()
        );
    }

    /**
     * Add an error to the debug log.
     *
     * @param severe If the error is severe
     * @author Seailz
     */
    public void addError(boolean severe) {
        if (severe) {
            severeErrors++;
        } else {
            minorErrors++;
        }
    }
}
