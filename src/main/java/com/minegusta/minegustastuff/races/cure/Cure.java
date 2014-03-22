package com.minegusta.minegustastuff.races.cure;

import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cure {

    private static FileConfiguration getConfig() {
        return ConfigFile.getDefaultConfig();
    }

    private static ItemStack i = new ItemStack(getConfig().getItemStack("cure_item"));
    private static int cureAmount = getConfig().getInt("cure_amount");
    private static Material mat = i.getType();

    public static void curePlayer(Player p) {
        String uuid = p.getUniqueId().toString();

        if (!canCure(uuid)) {
            p.sendMessage(deniedMessage());
            return;
        }
        if (!hasItems(p)) {
            p.sendMessage(deniedMessage());
            return;
        }
        removeItems(p);
        p.sendMessage(cureMessage());
        Data.setRace(uuid, "human");
    }

    private static boolean canCure(String uuid) {
        return !Data.getRace(uuid).equalsIgnoreCase("human");
    }

    private static boolean hasItems(Player p) {
        return p.getInventory().contains(mat, cureAmount);
    }

    private static void removeItems(Player p) {
        p.getInventory().remove(new ItemStack(mat, cureAmount));
        p.updateInventory();
    }

    private static String cureMessage() {
        return ChatColor.GREEN + getConfig().getString("cure_message");
    }

    private static String deniedMessage() {
        return ChatColor.DARK_RED + getConfig().getString("cure_denied_message");
    }


}
