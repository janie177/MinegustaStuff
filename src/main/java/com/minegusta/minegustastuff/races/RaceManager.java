package com.minegusta.minegustastuff.races;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class RaceManager {

    public static ConcurrentMap<String, String> pRaces = Maps.newConcurrentMap();

    public static Set<Player> elfMap = Sets.newHashSet();
    public static Set<Player> humanMap = Sets.newHashSet();
    public static Set<Player> dwarfMap = Sets.newHashSet();
    public static Set<Player> enderbornMap = Sets.newHashSet();


    public static void addPlayerToRaceMap(Player p) {

        String mojangID = p.getUniqueId().toString();

        pRaces.put(mojangID, Data.getRace(mojangID));

        switch (Data.getRace(mojangID)) {
            case "human":
                humanMap.add(p);
                break;
            case "elf":
                elfMap.add(p);
                break;
            case "dwarf":
                dwarfMap.add(p);
                break;
            case "enderborn":
                enderbornMap.add(p);
                break;
            default:
                humanMap.add(p);
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
        if (humanMap.contains(p)) humanMap.remove(p);
        if (elfMap.contains(p)) elfMap.remove(p);
        if (dwarfMap.contains(p)) dwarfMap.remove(p);
        if (enderbornMap.contains(p)) enderbornMap.remove(p);
        addPlayerToRaceMap(p);
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
