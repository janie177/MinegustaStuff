package com.minegusta.minegustastuff;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

public class Minegusta
{
	private static final MinegustaServer server = new MinegustaServer();

	private Minegusta()
	{
	}

	public static MinegustaPlugin getPlugin()
	{
		return MinegustaPlugin.getInst();
	}

	public static MinegustaServer getServer()
	{
		return server;
	}

	public static FileConfiguration getConfig()
	{
		return MinegustaPlugin.getInst().getConfig();
	}

	public static Logger getLogger()
	{
		return MinegustaPlugin.getInst().getLogger();
	}
}
