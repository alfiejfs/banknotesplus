package com.github.avroovulcan.banknotesplus.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author AvroVulcan
 */
public final class Chat {

    public static String color(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(CommandSender sender, String message)
    {
        sender.sendMessage(color(message));
    }
}
