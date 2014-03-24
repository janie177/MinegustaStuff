package com.minegusta.minegustastuff.races.werepire;

import com.dogonfire.werewolf.API;
import com.minegusta.minegustastuff.MinegustaStuff;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.util.ConsoleUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SupernaturalCheck {


    public static void supernaturalCheck() {

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!pass(p.getWorld())) return;
            String name = p.getName();

            if (API.isWerewolf(name) && !RaceManager.humanMap.contains(p)) {
                ConsoleUtil.command("werewolf toggle " + name);
            }
            if (isVampire(p) && !RaceManager.humanMap.contains(p)) {
                ConsoleUtil.command("v set v false " + name);
            }
        }
    }

    private static boolean isVampire(Player p) {
        return p.hasPermission("vampire.is.vampire");
    }

    private static boolean pass(World w) {
        return MinegustaStuff.worldCheck(w);
    }
}
