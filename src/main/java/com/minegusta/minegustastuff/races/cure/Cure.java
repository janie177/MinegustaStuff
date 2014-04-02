package com.minegusta.minegustastuff.races.cure;

import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Cure
{

	private static FileConfiguration getConfig()
	{
		return ConfigFile.getDefaultConfig();
	}

	private static ItemStack diamonds = new ItemStack(Material.DIAMOND);
	private static ItemStack emeralds = new ItemStack(Material.EMERALD);

	public static void curePlayer(Player p)
	{
		String uuid = p.getUniqueId().toString();

		if(!canCure(uuid))
		{
			p.sendMessage(deniedMessage());
			return;
		}
		if(!hasItems(p))
		{
			p.sendMessage(deniedMessage());
			return;
		}
		removeItems(p);
		p.sendMessage(cureMessage());
		Data.setRace(uuid, "human");
		p.getWorld().spigot().playEffect(p.getLocation(), Effect.CLOUD, 0, 0, 5, 5, 5, 1, 50, 25);
		p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1, 1);
		RaceManager.updateRace(p);
	}

	private static boolean canCure(String uuid)
	{
		return !Data.getRace(uuid).equalsIgnoreCase("human");
	}

	private static boolean hasItems(Player p)
	{
		Inventory inv = p.getInventory();
		return inv.containsAtLeast(diamonds, 30) && inv.containsAtLeast(emeralds, 20);
	}

	private static void removeItems(Player p)
	{
		Inventory inv = p.getInventory();
		inv.removeItem(new ItemStack(Material.DIAMOND, 30));
		inv.removeItem(new ItemStack(Material.EMERALD, 20));

		p.updateInventory();
	}

	private static String cureMessage()
	{
		return ChatColor.GREEN + getConfig().getString("cure_message");
	}

	private static String deniedMessage()
	{
		return ChatColor.DARK_RED + getConfig().getString("cure_denied_message");
	}


}
