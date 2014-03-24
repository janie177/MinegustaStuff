package com.minegusta.minegustastuff.races.powers;

import com.minegusta.minegustastuff.MinegustaStuff;
import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PermanentBoosts {

    public static void elfBoost() {
        for (Player p : RaceManager.elfMap) {
            if (!pass(p.getWorld())) return;
            updatePotionEffect(PotionEffectType.SPEED, p, 3 * 20, 0);
            if (isInWater(p)) {
                updatePotionEffect(PotionEffectType.REGENERATION, p, 3 * 20, 0);
                p.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART, 0, 0, 1, 2, 1, 3, 4, 25);
            }
        }
    }

    public static void enderbornBoost() {
        for (Player p : RaceManager.enderbornMap) {
            if (!pass(p.getWorld())) return;
            if (isInWater(p)) {
                p.damage(ConfigFile.getDefaultConfig().getDouble("enderborn_water_damage"));
                updatePotionEffect(PotionEffectType.WEAKNESS, p, 3 * 20, 1);
                updatePotionEffect(PotionEffectType.SLOW, p, 3 * 20, 1);
            }
            updatePotionEffect(PotionEffectType.JUMP, p, 3 * 20, 2);

            if (!p.isSneaking()) return;
            updatePotionEffect(PotionEffectType.INVISIBILITY, p, 3 * 20, 0);
            p.getWorld().spigot().playEffect(p.getLocation(), Effect.PARTICLE_SMOKE, 0, 0, 1, 0, 1, 0, 50, 25);
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
}
