package com.minegusta.minegustastuff.races.powers;

import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PermanentBoosts {

    public static void elfBoost() {
        for (Player p : RaceManager.elfMap) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 3, 0, false));
            if (isInWater(p)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, 0, false));
                p.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART, 0, 0, 1, 2, 1, 3, 4, 25);
            }
        }
    }

    public static void enderbornBoost() {
        for (Player p : RaceManager.enderbornMap) {
            if (isInWater(p)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 0, false));
                p.damage(ConfigFile.getDefaultConfig().getDouble("enderborn_water_damage"));
                p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 20, 1, false));
            }

            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 3, 1, false));

            if (!p.isSneaking()) return;
            p.getWorld().spigot().playEffect(p.getLocation(), Effect.PARTICLE_SMOKE, 0, 0, 1, 0, 1, 0, 25, 25);
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3 * 20, 0, false));
        }
    }

    private static boolean isInWater(Player p) {
        Material m = p.getLocation().getBlock().getType();
        return m.equals(Material.STATIONARY_WATER) || m.equals(Material.WATER);
    }
}
