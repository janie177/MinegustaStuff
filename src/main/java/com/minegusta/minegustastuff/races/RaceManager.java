package com.minegusta.minegustastuff.races;

import com.google.common.collect.Maps;
import com.minegusta.minegustastuff.races.health.HealthManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class RaceManager {

    public static ConcurrentMap<String, String> pRaces = Maps.newConcurrentMap();

    public static ConcurrentMap<Player, Boolean> elfMap = Maps.newConcurrentMap();
    public static ConcurrentMap<Player, Boolean> humanMap = Maps.newConcurrentMap();
    public static ConcurrentMap<Player, Boolean> dwarfMap = Maps.newConcurrentMap();
    public static ConcurrentMap<Player, Boolean> enderbornMap = Maps.newConcurrentMap();
    public static ConcurrentMap<UUID, Long> battleCryCooldown = Maps.newConcurrentMap();


    public static void addPlayerToRaceMap(Player p) {

        String mojangID = p.getUniqueId().toString();

        pRaces.put(mojangID, Data.getRace(mojangID));

        switch (Data.getRace(mojangID).toLowerCase()) {
            case "human":
                humanMap.put(p, false);
                break;
            case "elf":
                elfMap.put(p, false);
                ;
                break;
            case "dwarf":
                dwarfMap.put(p, false);
                break;
            case "enderborn":
                enderbornMap.put(p, false);
                break;
            default:
                humanMap.put(p, false);
                break;
        }
    }

    public static void removePlayerFromRaceMap(Player p) {

        String mojangID = getMojangID(p);

        if (isInMap(mojangID)) {

            pRaces.remove(mojangID);

            switch (Data.getRace(mojangID)) {

                case "human":
                    humanMap.remove(p);
                    break;
                case "elf":
                    elfMap.remove(p);
                    break;
                case "dwarf":
                    dwarfMap.remove(p);
                    break;
                case "enderborn":
                    enderbornMap.remove(p);
                    break;
                default:
                    humanMap.remove(p);
                    break;
            }
        }
    }

    public static void updateRace(Player p) {
        if (humanMap.containsKey(p)) humanMap.remove(p);
        if (elfMap.containsKey(p)) elfMap.remove(p);
        if (dwarfMap.containsKey(p)) dwarfMap.remove(p);
        if (enderbornMap.containsKey(p)) enderbornMap.remove(p);
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
