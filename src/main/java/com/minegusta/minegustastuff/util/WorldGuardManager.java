package com.minegusta.minegustastuff.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldGuardManager {
    public static boolean isInRegion(Location location, final String regionName) {
        return Iterables.any(WorldGuardPlugin.inst().getRegionManager(location.getWorld()).getApplicableRegions(location), new Predicate<ProtectedRegion>() {
            @Override
            public boolean apply(ProtectedRegion protectedRegion) {
                return protectedRegion.getId().toLowerCase().contains(regionName);
            }
        });
    }

    public static boolean canSpawnMob(Location location, final String regionName) {
        return Iterables.any(WorldGuardPlugin.inst().getRegionManager(location.getWorld()).getApplicableRegions(location), new Predicate<ProtectedRegion>() {
            @Override
            public boolean apply(ProtectedRegion protectedRegion) {
                return protectedRegion.getId().contains(regionName);
            }
        });
    }

    public static boolean canBuild(Player p) {
        Location loc = p.getLocation();
        return WorldGuardPlugin.inst().canBuild(p, loc);
    }

    public static ArrayList<String> getRegionNames(Location location) {
        ApplicableRegionSet regions = WorldGuardPlugin.inst().getGlobalRegionManager().get(location.getWorld()).getApplicableRegions(location);
        String regionName = "nothing";
        for (ProtectedRegion r : regions) {
            regionName = r.getId();
        }
        ArrayList<String> regionNames = new ArrayList<String>();
        regionNames.add(regionName);
        return regionNames;
    }
}
