package com.minegusta.minegustastuff;

import com.minegusta.minegustastuff.data.SaveFile;
import com.minegusta.minegustastuff.data.Tasks;
import com.minegusta.minegustastuff.listeners.PlayerListener;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.races.commands.RaceCommands;
import com.minegusta.minegustastuff.races.recipes.Recipes;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MinegustaPlugin extends JavaPlugin
{
	private static MinegustaPlugin plugin;
	public static boolean WORLD_GUARD_ENABLED;

	public static MinegustaPlugin getInst()
	{
		return plugin;
	}

	@Override
	public void onEnable()
	{
		// Plugin Static Access
		plugin = this;

		// Load Files
		Minegusta.getServer().saveConfig();

		// Race File
		SaveFile.createIfAbsent("races.yml").load();

		// Reload safety
		RaceManager.onReloadAddRacesToMap();

		// Listeners
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

		// Tasks
		Tasks.startSaveTask();
		Tasks.startBoostCheck();

		// Depends
		WORLD_GUARD_ENABLED = Bukkit.getPluginManager().isPluginEnabled("WorldGuard") && Bukkit.getPluginManager().getPlugin("WorldGuard") instanceof WorldGuardPlugin;

		// Register recipes
		Recipes.registerRecipes();

		// Commands
		getCommand("Race").setExecutor(new RaceCommands());

	}

	@Override
	public void onDisable()
	{
		// Cancel tasks
		Bukkit.getServer().getScheduler().cancelTasks(this);

		// Unregister Handlers
		HandlerList.unregisterAll(this);
	}
}
