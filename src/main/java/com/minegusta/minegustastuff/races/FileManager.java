package com.minegusta.minegustastuff.races;

import com.minegusta.minegustastuff.MinegustaStuff;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FileManager {

    static Plugin p = MinegustaStuff.PLUGIN;
    static File file;
    static FileConfiguration conf;

    public static void loadFile() {
        file = new File(p.getDataFolder() + "/data/" + "races.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        conf = YamlConfiguration.loadConfiguration(file);

    }

    public static void saveFile() {
        try {
            conf.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
