package com.minegusta.minegustastuff.armour;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ArmourManager {

    public static ConcurrentHashMap<UUID, String> armourMap = new ConcurrentHashMap<>();

    public static boolean hasSet(Player p, String lore) {
        ItemStack[] armour = p.getInventory().getArmorContents();
        String itemLore = lore.toLowerCase();

        for (ItemStack i : armour) {
            if (i.getType().equals(Material.AIR)) return false;
            if (!i.getItemMeta().hasLore()) return false;
            if (!i.getItemMeta().getLore().toString().toLowerCase().contains(itemLore)) return false;
        }
        return true;
    }
}
