package com.minegusta.minegustastuff;

import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.data.Tasks;
import com.minegusta.minegustastuff.listeners.PlayerListener;
import com.minegusta.minegustastuff.races.FileManager;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.races.commands.RaceCommands;
import com.minegusta.minegustastuff.races.recipes.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class MinegustaStuff extends JavaPlugin {

    public static MinegustaStuff PLUGIN;
    private static int SAVETASK;
    private static int BOOSTTASK;

    @Override
    public void onEnable() {
        PLUGIN = this;
        //Load Files
        ConfigFile.saveDefaultConfigFile();
        FileManager.loadFile();

        //Reload safety
        RaceManager.onReloadAddRacesToMap();

        //Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        //Tasks

        SAVETASK = Tasks.startSaveTask();
        BOOSTTASK = Tasks.startBoostCheck();

        //Register recipes

        Recipes.registerRecipes();

        //commands
        getCommand("Race").setExecutor(new RaceCommands());

    }

    @Override
    public void onDisable() {

        //Cancel tasks
        Bukkit.getServer().getScheduler().cancelTask(SAVETASK);
        Bukkit.getServer().getScheduler().cancelTask(BOOSTTASK);

    }

    public static boolean worldCheck(World w) {
        return ConfigFile.getDefaultConfig().getList("worlds").contains(w.getName());
    }
}
