package com.minegusta.minegustastuff.data;

import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.MinegustaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SaveFile
{
	private static final ConcurrentMap<String, SaveFile> SAVE_FILES = new ConcurrentHashMap<>();

	String name;
	File file;
	FileConfiguration conf;

	private SaveFile(String name)
	{
		this.name = name;
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void load()
	{
		try
		{
			file = new File(MinegustaPlugin.getInst().getDataFolder(), name);

			if(!file.exists())
			{
				file.createNewFile();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		conf = YamlConfiguration.loadConfiguration(file);

		SAVE_FILES.put(name, this);
	}

	public void recache()
	{
		SAVE_FILES.put(name, this);
	}

	public void save()
	{
		try
		{
			conf.save(file);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Minegusta.getLogger().severe("Could not save to file: " + name);
		}
	}

	public FileConfiguration getConf()
	{
		return conf;
	}

	public static Collection<SaveFile> files()
	{
		return SAVE_FILES.values();
	}

	public static SaveFile createIfAbsent(String name)
	{
		SAVE_FILES.putIfAbsent(name, new SaveFile(name));
		return SAVE_FILES.get(name);
	}

	public static SaveFile get(String name)
	{
		if(!SAVE_FILES.containsKey(name)) return null;
		return SAVE_FILES.get(name);
	}
}
