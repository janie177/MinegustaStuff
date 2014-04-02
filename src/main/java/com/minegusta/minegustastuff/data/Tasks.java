package com.minegusta.minegustastuff.data;

import com.minegusta.minegustastuff.MinegustaStuff;
import com.minegusta.minegustastuff.races.FileManager;
import com.minegusta.minegustastuff.races.powers.PermanentBoosts;
import com.minegusta.minegustastuff.races.werepire.SupernaturalCheck;
import org.bukkit.Bukkit;

public class Tasks
{

	public static int startSaveTask()
	{
		return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinegustaStuff.PLUGIN, new Runnable()
		{
			@Override
			public void run()
			{
				FileManager.saveFile();
				ConfigFile.save();
				SupernaturalCheck.supernaturalCheck();
			}
		}, 0, 20 * 60);
	}

	public static int startBoostCheck()
	{
		return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MinegustaStuff.PLUGIN, new Runnable()
		{
			@Override
			public void run()
			{

				PermanentBoosts.elfBoost();
				PermanentBoosts.enderbornBoost();

			}
		}, 0, 20);
	}
}
