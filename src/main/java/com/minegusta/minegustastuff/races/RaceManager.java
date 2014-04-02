package com.minegusta.minegustastuff.races;

import com.google.common.collect.Maps;
import com.minegusta.minegustastuff.races.health.HealthManager;
import com.minegusta.minegustastuff.util.MojangIdProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class RaceManager
{
	public static ConcurrentMap<String, Long> battleCryCooldown = Maps.newConcurrentMap();
	public static ConcurrentMap<String, String> pRaces = Maps.newConcurrentMap();

	public static void addPlayerToRaceMap(Player p)
	{
		String mojangID = MojangIdProvider.getId(p);

		pRaces.put(mojangID, Data.getRace(mojangID));
	}

	public static void removePlayerFromRaceMap(Player p)
	{
		String mojangID = MojangIdProvider.getId(p);

		if(isInMap(mojangID))
		{
			pRaces.remove(mojangID);
		}
	}

	public static void updateRace(Player p)
	{
		addPlayerToRaceMap(p);
		HealthManager.checkPlayerHealth(p, p.getWorld());
	}

	private static boolean isInMap(String mojangID)
	{
		return pRaces.containsKey(mojangID);
	}

	public static void onReloadAddRacesToMap()
	{
		for(Player p : Bukkit.getOnlinePlayers())
			addPlayerToRaceMap(p);
	}
}
