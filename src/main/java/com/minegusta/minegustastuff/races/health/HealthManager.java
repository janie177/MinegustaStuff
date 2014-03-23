package com.minegusta.minegustastuff.races.health;

import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.Data;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HealthManager {


    //Checks

    public static void checkPlayerHealth(Player p, World w) {
        if (!ConfigFile.getDefaultConfig().getList("worlds").contains(w.getName())) setHealthToNormal(p);
        else {
            String race = Data.getRace(p.getUniqueId().toString());

            switch (race) {
                case "dwarf":
                    setHealthToDwarf(p);
                    break;
                case "elf":
                    setHealthToElf(p);
                    break;
                default:
                    setHealthToNormal(p);
                    break;
            }
        }
    }


    //Health setters

    private static void setHealthToElf(Player p) {
        p.setHealthScale(10.0);
        p.setMaxHealth(16);
    }

    private static void setHealthToDwarf(Player p) {
        p.setHealthScale(10.0);
        p.setMaxHealth(24);
    }

    private static void setHealthToNormal(Player p) {
        p.setHealthScale(10.0);
        p.setMaxHealth(20.0);
    }


}
