package com.minegusta.minegustastuff.races.infection;

import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnderbornInfect {

    private Player p;
    private String message;
    private Inventory inv;

    private EnderbornInfect(AsyncPlayerChatEvent e) {
        p = e.getPlayer();
        message = e.getMessage();
        inv = p.getInventory();
    }

    public static EnderbornInfect enderbornInfect(AsyncPlayerChatEvent e) {
        return new EnderbornInfect(e);
    }

    public boolean isHuman() {
        return RaceManager.humanMap.contains(p);
    }

    public boolean hasEyesOfEnder() {
        return inv.contains(new ItemStack(Material.EYE_OF_ENDER, 10));
    }

    public boolean isSpell() {
        return message.equalsIgnoreCase("In fine autem omnes morimur");
    }

    public void makeEnderborn() {
        Data.setRace(p.getUniqueId().toString(), "enderborn");
        sendMessage();
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0, 0, 3, 3, 3, 1, 30, 25);
        p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
        RaceManager.updateRace(p);
        removeEyesOfEnder();

    }

    private void removeEyesOfEnder() {
        p.getInventory().remove(new ItemStack(Material.EYE_OF_ENDER, 10));
    }

    private void sendMessage() {
        p.sendMessage(ChatColor.DARK_PURPLE + "You are now enderborn!");
    }
}

