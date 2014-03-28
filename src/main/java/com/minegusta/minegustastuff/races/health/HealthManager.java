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
                case "enderborn":
                    setHealthToEnderborn(p);
                    break;
                default:
                    setHealthToNormal(p);
                    break;
            }
        }
    }


    //Health setters

    private static void setHealthToEnderborn(Player p) {
        p.setHealthScale(24.0);
        p.setMaxHealth(24);
    }

    private static void setHealthToElf(Player p) {
        p.setHealthScale(24.0);
        p.setMaxHealth(24);
    }

    private static void setHealthToDwarf(Player p) {
        p.setHealthScale(28.0);
        p.setMaxHealth(28);
    }

    private static void setHealthToNormal(Player p) {
        p.setHealthScale(20.0);
        p.setMaxHealth(20.0);
    }


}
