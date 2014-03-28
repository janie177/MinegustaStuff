package com.minegusta.minegustastuff.races.infection;

import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ElfInfect {

    private ItemStack i;
    private Player p;

    private ElfInfect(PlayerItemConsumeEvent e) {
        i = e.getItem();
        p = e.getPlayer();
    }

    public static ElfInfect elfInfect(PlayerItemConsumeEvent e) {
        return new ElfInfect(e);
    }

    public boolean isHuman() {
        return RaceManager.humanMap.containsKey(p);
    }

    public boolean hasLore() {
        return i.getItemMeta().hasLore();
    }

    public boolean isVeganStew() {
        return i.getItemMeta().getLore().toString().contains("Vegan Stew");
    }

    public void makeElf() {
        p.sendMessage(ChatColor.GREEN + "You are now an elf!");
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.HAPPY_VILLAGER, 0, 0, 3, 3, 3, 1, 30, 25);
        Data.setRace(p.getUniqueId().toString(), "elf");
        p.playSound(p.getLocation(), Sound.ARROW_HIT, 1, 1);
        RaceManager.updateRace(p);
    }


}
