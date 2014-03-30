package com.minegusta.minegustastuff.races.powers;

import com.minegusta.minegustastuff.MinegustaStuff;
import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.util.WorldGuardManager;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ConcurrentMap;

public class PermanentBoosts {


    private static ConcurrentMap<Player, Boolean> getElfMap() {
        return RaceManager.elfMap;
    }

    private static ConcurrentMap<Player, Boolean> getEnderbornMap() {
        return RaceManager.enderbornMap;
    }

    public static void elfBoost() {
        for (Player p : getElfMap().keySet()) {
            if (!pass(p.getWorld())) return;
            updatePotionEffect(PotionEffectType.SPEED, p, 3 * 20, 0);
            if (isInWater(p)) {
                updatePotionEffect(PotionEffectType.REGENERATION, p, 3 * 20, 0);
                p.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART, 0, 0, 1, 2, 1, 3, 4, 25);
            }
        }
    }

    public static void enderbornBoost() {
        for (Player p : getEnderbornMap().keySet()) {
            World w = p.getWorld();
            if (!pass(w)) return;
            if (isInWater(p) && canGetDamage(p)) {
                p.damage(ConfigFile.getDefaultConfig().getDouble("enderborn_water_damage"));
                updatePotionEffect(PotionEffectType.WEAKNESS, p, 3 * 20, 1);
                updatePotionEffect(PotionEffectType.SLOW, p, 3 * 20, 1);
            }
            updatePotionEffect(PotionEffectType.JUMP, p, 3 * 20, 2);

            final Location loc = p.getLocation();
            final int x = loc.getBlockX();
            final int z = loc.getBlockZ();

            if (isRaining(w) && canGetDamage(p) & inInRain(w, x, z, loc) && isNotInDesert(x, z, w)) {
                p.damage(ConfigFile.getDefaultConfig().getDouble("enderborn_water_damage"));
                updatePotionEffect(PotionEffectType.WEAKNESS, p, 3 * 20, 1);
                updatePotionEffect(PotionEffectType.SLOW, p, 3 * 20, 1);
            }

            if (p.isSneaking()) {
                updatePotionEffect(PotionEffectType.INVISIBILITY, p, 3 * 20, 0);
                p.getWorld().spigot().playEffect(p.getLocation(), Effect.PARTICLE_SMOKE, 0, 0, 1, 0, 1, 0, 50, 25);
            }
        }
    }

    private static boolean isInWater(Player p) {
        Material m = p.getLocation().getBlock().getType();
        return m.equals(Material.STATIONARY_WATER) || m.equals(Material.WATER);
    }

    private static void updatePotionEffect(PotionEffectType effect, Player p, int duration, int amplifier) {
        for (PotionEffect pe : p.getActivePotionEffects()) {
            if (pe.getType().equals(effect)) {
                p.removePotionEffect(effect);
            }
        }
        p.addPotionEffect(new PotionEffect(effect, duration, amplifier));
    }

    private static boolean pass(World w) {
        return MinegustaStuff.worldCheck(w);
    }

    private static boolean isRaining(World w) {
        return w.hasStorm();
    }

    private static boolean inInRain(World w, int x, int z, Location loc) {

        return !(w.getHighestBlockYAt(x, z) > loc.getBlockY());
    }

    private static boolean isNotInDesert(int x, int z, World w) {
        Biome b = w.getBiome(x, z);
        return !b.equals(Biome.DESERT) && !b.equals(Biome.DESERT_HILLS) && !b.equals(Biome.DESERT_MOUNTAINS);
    }

    private static boolean canGetDamage(Player p) {
        return WorldGuardManager.canGetDamage(p);
    }
}

