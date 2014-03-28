package com.minegusta.minegustastuff.util;

import org.bukkit.Bukkit;

public class ConsoleUtil {

    private ConsoleUtil() {
    }

    public static boolean command(String command) {
        return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
