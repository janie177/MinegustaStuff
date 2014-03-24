package com.minegusta.minegustastuff.races.infection;

import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.races.recipes.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Inventory;

public class DwarfInfect {
    private Player p;
    private Entity entity;
    private Inventory i;
    EntityDamageEvent cause;

    private DwarfInfect(EntityDeathEvent e) {
        entity = e.getEntity();
        cause = e.getEntity().getLastDamageCause();
    }

    public static DwarfInfect dwarfInfect(EntityDeathEvent e) {
        return new DwarfInfect(e);
    }

    public boolean isHuman() {
        if (entity instanceof Player && RaceManager.humanMap.contains(entity)) {
            i = p.getInventory();
            return true;
        }
        return false;
    }

    public boolean hasShinyGem() {
        return i.contains(Recipes.shinyGem());
    }

    public boolean isByLava() {
        return cause.getCause().equals(EntityDamageEvent.DamageCause.LAVA);
    }

    public void makeDwarf() {
        Data.setRace(p.getUniqueId().toString(), "dwarf");
        i.remove(Recipes.shinyGem());
        p.updateInventory();
        successMessage();
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.VILLAGER_THUNDERCLOUD, 0, 0, 3, 3, 3, 1, 30, 25);
        p.playSound(p.getLocation(), Sound.ANVIL_USE, 1, 1);
        RaceManager.updateRace(p);
    }

    private void successMessage() {
        p.sendMessage(ChatColor.DARK_GREEN + "You are now a dwarf! Diggy Diggy Hole.");
    }


}
