package com.seailz.speedrunevent.core.locale;

import games.negative.framework.message.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Locale {

    QUEUE_CHANGE("queue.change", Collections.singletonList("&b&lQUEUE &fYou have been &b%status% the queue.")),
    ENTER_GAME("game.enter", Collections.singletonList("&b&lGAME &fYou have joined a game with the UUID &b%uuid%")),
    SETUP_ENTER("setup.enter", Collections.singletonList("&b&lADMIN &fYou have entered setup mode!")),
    INVALID_PLAYER("core.invalid-player", Collections.singletonList("&cThat player cannot be found"));


    private final String id;
    private final List<String> defaultMessage;
    private Message message;

    /**
     * Register the messages.yml file
     *
     * @param plugin The main class
     */
    @SneakyThrows
    public static void init(JavaPlugin plugin) {
        File configFile = new File(plugin.getDataFolder(), "messages.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            Arrays.stream(values()).forEach(locale -> {
                String id = locale.getId();
                List<String> defaultMessage = locale.getDefaultMessage();

                config.set(id, defaultMessage);
            });

        } else {
            Arrays.stream(values()).filter(locale -> {
                String id = locale.getId();
                return (config.get(id, null) == null);
            }).forEach(locale -> config.set(locale.getId(), locale.getDefaultMessage()));

        }
        config.save(configFile);

        // Creates the message objects
        Arrays.stream(values()).forEach(locale ->
                locale.message = new Message(config.getStringList(locale.getId())
                        .toArray(new String[0])));
    }

    public void send(CommandSender sender) {
        message.send(sender);
    }

    public void send(List<Player> players) {
        message.send((CommandSender) players);
    }

    public void broadcast() {
        message.broadcast();
    }

    public Message replace(Object o1, Object o2) {
        return message.replace((String) o1, (String) o2);
    }
}
