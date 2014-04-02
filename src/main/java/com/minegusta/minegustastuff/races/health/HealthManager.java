package com.minegusta.minegustastuff.races.health;

import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.util.MojangIdProvider;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HealthManager
{
	//Checks

	public static void checkPlayerHealth(Player p, World w)
	{
		if(!Minegusta.getConfig().getList("worlds").contains(w.getName())) setHealthToNormal(p);
		else
		{
			String race = Data.getRace(MojangIdProvider.getId(p));

			switch(race)
			{
				case "dwarf":
					setHealthToDwarf(p);
					break;
				case "elf":
					setHealthToElf(p);
					break;
				case "enderborn":
					setHealthToEnderborn(p);
					break;
				default:
					setHealthToNormal(p);
					break;
			}
		}
	}

	//Health setters

	private static void setHealthToEnderborn(Player p)
	{
		p.setHealthScale(24.0);
		p.setMaxHealth(24);
	}

	private static void setHealthToElf(Player p)
	{
		p.setHealthScale(24.0);
		p.setMaxHealth(24);
	}

	private static void setHealthToDwarf(Player p)
	{
		p.setHealthScale(28.0);
		p.setMaxHealth(28);
	}

	private static void setHealthToNormal(Player p)
	{
		p.setHealthScale(20.0);
		p.setMaxHealth(20.0);
	}
}
