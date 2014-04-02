package com.minegusta.minegustastuff.races;

import org.bukkit.configuration.file.FileConfiguration;

public class Data
{
	private static String defaultRace = Races.getDefaultRace();

	private static FileConfiguration getConfig()
	{
		return FileManager.conf;
	}

	//Getting data

	public static String getRace(String mojangID)
	{
		if(getConfig().getString(mojangID + ".race") != null)
		{
			return getConfig().getString(mojangID + ".race");
		}
		else return defaultRace.toLowerCase();
	}

	//Setting data

	public static void setRace(String mojangID, String race)
	{
		getConfig().set(mojangID + ".race", race);
	}

}
