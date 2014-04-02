package com.minegusta.minegustastuff;

import org.bukkit.World;

public class MinegustaServer
{
	MinegustaServer()
	{
	}

	public boolean containsWorld(World w)
	{
		return Minegusta.getConfig().getList("worlds").contains(w.getName());
	}

	public void saveConfig()
	{
		Minegusta.getConfig().options().copyDefaults(true);
		MinegustaPlugin.getInst().saveConfig();
	}

	public void reloadConfig()
	{
		MinegustaPlugin.getInst().reloadConfig();
	}
}
