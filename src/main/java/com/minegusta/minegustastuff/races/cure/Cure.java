package com.minegusta.minegustastuff.races.cure;

import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.util.MojangIdProvider;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Cure
{
	private static ItemStack diamonds = new ItemStack(Material.DIAMOND);
	private static ItemStack emeralds = new ItemStack(Material.EMERALD);

	public static void curePlayer(Player p)
	{
		String uuid = MojangIdProvider.getId(p);

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

	@SuppressWarnings("deprecation")
	private static void removeItems(Player p)
	{
		Inventory inv = p.getInventory();
		inv.removeItem(new ItemStack(Material.DIAMOND, 30));
		inv.removeItem(new ItemStack(Material.EMERALD, 20));

		p.updateInventory();
	}

	private static String cureMessage()
	{
		return ChatColor.GREEN + Minegusta.getConfig().getString("cure_message");
	}

	private static String deniedMessage()
	{
		return ChatColor.DARK_RED + Minegusta.getConfig().getString("cure_denied_message");
	}
}
