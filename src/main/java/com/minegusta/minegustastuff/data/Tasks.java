package com.minegusta.minegustastuff.data;

import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.powers.PermanentBoosts;
import com.minegusta.minegustastuff.races.werepire.SupernaturalCheck;
import org.bukkit.Bukkit;

public class Tasks
{
	public static int startSaveTask()
	{
		return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Minegusta.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				for(SaveFile file : SaveFile.files())
					file.save();
				Minegusta.getServer().saveConfig();
				SupernaturalCheck.supernaturalCheck();
			}
		}, 0, 20 * 60);
	}

	public static int startBoostCheck()
	{
		return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Minegusta.getPlugin(), new Runnable()
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
