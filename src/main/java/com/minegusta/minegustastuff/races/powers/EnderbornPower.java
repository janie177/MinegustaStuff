package com.minegusta.minegustastuff.races.powers;

import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.util.MojangIdProvider;
import com.minegusta.minegustastuff.util.WorldGuardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class EnderbornPower
{
	//Variables. ------------------------------------------------------------------------------------------------------------------------------------------------------------------

	Entity entity;
	Player p;
	String uuid;
	Entity victim;
	Entity damager;
	EntityDamageByEntityEvent event;
	EntityDamageEvent damageEvent;

	//Constructors. ---------------------------------------------------------------------------------------------------------------------------------------------------------------

	private EnderbornPower(EntityDamageByEntityEvent e)
	{
		entity = e.getEntity();
		damager = e.getDamager();
		event = e;
		victim = entity;
		uuid = entity.getUniqueId().toString();
	}

	private EnderbornPower(EntityDamageEvent e)
	{
		entity = e.getEntity();
		damageEvent = e;
		uuid = entity.getUniqueId().toString();
	}
	//getting the class. ----------------------------------------------------------------------------------------------------------------------------------------------------------

	public static EnderbornPower enderPearlDamage(EntityDamageByEntityEvent e)
	{
		return new EnderbornPower(e);
	}

	public static EnderbornPower bleedBoost(EntityDamageByEntityEvent e)
	{
		return new EnderbornPower(e);
	}

	public static EnderbornPower fallDamageBoost(EntityDamageEvent e)
	{
		return new EnderbornPower(e);
	}

	//Boolean Methods -------------------------------------------------------------------------------------------------------------------------------------------------------------

	public boolean isPlayer()
	{
		return entity instanceof Player;
	}

	public boolean isPearl()
	{
		return damager instanceof EnderPearl;
	}

	public boolean entityIsEnderBorn()
	{
		if(entity instanceof Player)
		{
			p = (Player) entity;
			return "enderborn".equals(Data.getRace(MojangIdProvider.getId(p)));
		}
		return false;
	}

	public boolean damagerIsEndeborn()
	{
		if(damager instanceof Player)
		{
			p = (Player) damager;
			return "enderborn".equals(Data.getRace(MojangIdProvider.getId(p)));
		}
		return false;
	}

	public boolean victimIsLiving()
	{
		return victim instanceof LivingEntity;
	}

	public boolean canPVP()
	{
		return WorldGuardManager.canPVP(victim);
	}

	public boolean isFallDamage()
	{
		return damageEvent.getCause().equals(EntityDamageEvent.DamageCause.FALL);
	}

	//Applying boosts. ------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void cancelPearlDamage()
	{
		event.setDamage(0);
	}

	public void cancelFallDamae()
	{
		damageEvent.setDamage(0);
	}

	public void applyBleedBoost()
	{
		bleedEntity((LivingEntity) victim);
	}

	//Applying weaknesses. --------------------------------------------------------------------------------------------------------------------------------------------------------

	//Other

	private void bleedEntity(LivingEntity bleedingEntity)
	{
		Random rand = new Random();
		int chance = rand.nextInt(100);
		if(chance < 85) return;
		final LivingEntity e = bleedingEntity;
		for(int i = 0; i < 60; i++)
		{
			final int k = i;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Minegusta.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					if(e instanceof Player)
					{
						Player p = (Player) e;
						if(k == 1) p.sendMessage(ChatColor.RED + "You are bleeding!");
					}
					double d = e.getMaxHealth() / 15;
					e.getWorld().spigot().playEffect(e.getLocation(), Effect.CRIT);
					if(k == 20) e.damage(d);
					if(k == 40) e.damage(d);
					if(k == 59) e.damage(d);
				}
			}, i);
		}
	}
}
