package com.minegusta.minegustastuff.data;

import com.minegusta.minegustastuff.MinegustaStuff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigFile {
    private static JavaPlugin p = MinegustaStuff.PLUGIN;

    public static void saveDefaultConfigFile() {
        p.saveDefaultConfig();
    }

    public static void save() {
        p.saveConfig();
    }

    public static FileConfiguration getDefaultConfig() {
        return p.getConfig();
    }

    public static void reloadConfig() {
        p.reloadConfig();
    }
}
