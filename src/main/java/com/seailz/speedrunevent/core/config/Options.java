package com.seailz.speedrunevent.core.config;

import com.seailz.speedrunevent.SpeedrunEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.checkerframework.checker.units.qual.Speed;

public class Options {
    public static String TOKEN = SpeedrunEvent.CONFIG.getString("discord.token");
    public static int SPEEDRUN_COUNT = SpeedrunEvent.CONFIG.getInt("game.speedrunerSlots");
    public static TextChannel ADMIN_WHITELIST_CHANNEL = SpeedrunEvent.CLIENT.getTextChannelById(SpeedrunEvent.CONFIG.getString("whitelist-request-recieve-channel-id"));
    public static TextChannel WHITELIST_REQUEST_CHANNEL = SpeedrunEvent.CLIENT.getTextChannelById(SpeedrunEvent.CONFIG.getString("whitelist-request-channel-id"));
    public static Guild GUILD = SpeedrunEvent.CLIENT.getGuildById(SpeedrunEvent.CONFIG.getString("discord.guild-id"));
}
