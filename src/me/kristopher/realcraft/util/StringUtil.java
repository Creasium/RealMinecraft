package me.kristopher.realcraft.util;

import org.bukkit.ChatColor;

public class StringUtil {
	public static String inColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
