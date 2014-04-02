package com.minegusta.minegustastuff.races.werepire;

import com.dogonfire.werewolf.API;
import com.massivecraft.vampire.entity.UPlayer;
import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.util.ConsoleUtil;
import com.minegusta.minegustastuff.util.MojangIdProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SupernaturalCheck
{
	public static void supernaturalCheck()
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(!pass(p.getWorld())) return;
			String name = p.getName();

			if(API.isWerewolf(name) && !"human".equals(Data.getRace(MojangIdProvider.getId(p))))
			{
				ConsoleUtil.command("werewolf toggle " + name);
			}
			if(isVampire(p) && !"human".equals(Data.getRace(MojangIdProvider.getId(p))))
			{
				ConsoleUtil.command("v set v false " + name);
			}
		}
	}

	private static boolean isVampire(Player p)
	{
		return UPlayer.get(p).isVampire();
	}

	private static boolean pass(World w)
	{
		return Minegusta.getServer().containsWorld(w);
	}
}
