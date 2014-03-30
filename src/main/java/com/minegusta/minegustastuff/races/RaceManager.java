package com.minegusta.minegustastuff.races;

import com.google.common.collect.Maps;
import com.minegusta.minegustastuff.races.health.HealthManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class RaceManager {

    public static ConcurrentMap<String, String> pRaces = Maps.newConcurrentMap();

    public static ConcurrentMap<String, Boolean> elfMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Boolean> humanMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Boolean> dwarfMap = Maps.newConcurrentMap();
    public static ConcurrentMap<String, Boolean> enderbornMap = Maps.newConcurrentMap();
    public static ConcurrentMap<UUID, Long> battleCryCooldown = Maps.newConcurrentMap();


    public static void addPlayerToRaceMap(Player p) {

        String mojangID = p.getUniqueId().toString();

        pRaces.put(mojangID, Data.getRace(mojangID));

        switch (Data.getRace(mojangID).toLowerCase()) {
            case "human":
                humanMap.put(p.getName(), false);
                break;
            case "elf":
                elfMap.put(p.getName(), false);
                break;
            case "dwarf":
                dwarfMap.put(p.getName(), false);
                break;
            case "enderborn":
                enderbornMap.put(p.getName(), false);
                break;
            default:
                humanMap.put(p.getName(), false);
                break;
        }
    }

    public static void removePlayerFromRaceMap(Player p) {

        String mojangID = getMojangID(p);

        if (isInMap(mojangID)) {

            pRaces.remove(mojangID);

            switch (Data.getRace(mojangID)) {

                case "human":
                    humanMap.remove(p.getName());
                    break;
                case "elf":
                    elfMap.remove(p.getName());
                    break;
                case "dwarf":
                    dwarfMap.remove(p.getName());
                    break;
                case "enderborn":
                    enderbornMap.remove(p.getName());
                    break;
                default:
                    humanMap.remove(p.getName());
                    break;
            }
        }
    }

    public static void updateRace(Player p) {
        if (humanMap.containsKey(p.getName())) humanMap.remove(p.getName());
        if (elfMap.containsKey(p.getName())) elfMap.remove(p.getName());
        if (dwarfMap.containsKey(p.getName())) dwarfMap.remove(p.getName());
        if (enderbornMap.containsKey(p.getName())) enderbornMap.remove(p.getName());
        addPlayerToRaceMap(p);
        HealthManager.checkPlayerHealth(p, p.getWorld());
    }

    private static boolean isInMap(String mojangID) {
        return pRaces.containsKey(mojangID);
    }

    public static void onReloadAddRacesToMap() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            addPlayerToRaceMap(p);

        }
    }

    private static String getMojangID(Player p) {
        return p.getUniqueId().toString();
    }
}
