package com.seailz.speedrunevent.core.config;

import com.seailz.speedrunevent.SpeedrunEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.checkerframework.checker.units.qual.Speed;

public class Options {
    public static String TOKEN = SpeedrunEvent.CONFIG.getString("discord.token");
    public static int SPEEDRUN_COUNT = SpeedrunEvent.CONFIG.getInt("game.speedrunerSlots");
    public static TextChannel ADMIN_WHITELIST_CHANNEL;
    public static TextChannel WHITELIST_REQUEST_CHANNEL;
    public static Guild GUILD;
}
