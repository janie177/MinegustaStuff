package com.minegusta.minegustastuff.races;

import com.minegusta.minegustastuff.data.SaveFile;
import org.bukkit.configuration.file.FileConfiguration;

public class Data
{
	private static String defaultRace = Races.getDefaultRace();

	//Getting data

	public static String getRace(String mojangID)
	{
		FileConfiguration raceFile = SaveFile.get("races.yml").getConf();
		if(raceFile.getString(mojangID + ".race") != null)
		{
			return raceFile.getString(mojangID + ".race");
		}
		else return defaultRace.toLowerCase();
	}

	//Setting data

	public static void setRace(String mojangID, String race)
	{
		SaveFile raceFile = SaveFile.get("races.yml");
		raceFile.getConf().set(mojangID + ".race", race);
		raceFile.recache();
	}
}
