package com.minegusta.minegustastuff.races.powers;

import com.google.common.collect.Lists;
import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.util.MojangIdProvider;
import com.minegusta.minegustastuff.util.WorldGuardManager;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.List;

public class ElfPower
{

	//Lists
	private List<ItemStack> fruits = Lists.newArrayList(new ItemStack(Material.APPLE), new ItemStack(Material.MELON), new ItemStack(Material.GOLDEN_APPLE), new ItemStack(Material.MUSHROOM_SOUP), new ItemStack(Material.POTATO_ITEM), new ItemStack(Material.CARROT_ITEM));

	//Variables. ------------------------------------------------------------------------------------------------------------------------------------------------------------------

	Entity entity;
	EntityDamageEvent.DamageCause cause;
	Entity victim;
	Player player;
	double damage;
	LivingEntity livingVictim;
	String uuid;
	Arrow arrow;
	ItemStack fruit;
	Entity mount;

	//Constructors. ---------------------------------------------------------------------------------------------------------------------------------------------------------------

	private ElfPower(EntityDamageEvent e)
	{
		entity = e.getEntity();
		cause = e.getCause();
		uuid = entity.getUniqueId().toString();
	}

	private ElfPower(EntityDamageByEntityEvent e)
	{
		entity = e.getDamager();
		victim = e.getEntity();
		uuid = entity.getUniqueId().toString();
	}

	private ElfPower(PlayerItemConsumeEvent e)
	{
		entity = e.getPlayer();
		player = e.getPlayer();
		fruit = e.getItem();
		uuid = entity.getUniqueId().toString();
	}

	private ElfPower(EntityMountEvent e)
	{
		entity = e.getEntity();
		mount = e.getMount();
		uuid = entity.getUniqueId().toString();
	}

	//getting the class. ----------------------------------------------------------------------------------------------------------------------------------------------------------

	public static ElfPower fireDamage(EntityDamageEvent e)
	{
		return new ElfPower(e);
	}

	public static ElfPower arrowDamage(EntityDamageByEntityEvent e)
	{
		return new ElfPower(e);
	}

	public static ElfPower consumeFruit(PlayerItemConsumeEvent e)
	{
		return new ElfPower(e);
	}

	public static ElfPower horseMount(EntityMountEvent e)
	{
		return new ElfPower(e);
	}

	//Boolean Methods -------------------------------------------------------------------------------------------------------------------------------------------------------------

	public boolean isElf()
	{
		return RaceManager.pRaces.containsKey(uuid) && RaceManager.pRaces.get(uuid).equalsIgnoreCase("elf");
	}

	public boolean isFireDamage()
	{
		return cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK);
	}

	public boolean isHorse()
	{
		return mount instanceof Horse;
	}

	public boolean isPlayer()
	{
		if(entity instanceof Player)
		{
			uuid = MojangIdProvider.getId((Player) entity);
			return true;
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

	public boolean isBowDamage()
	{
		return entity instanceof Arrow;
	}

	public boolean arrowIsFiredByElf()
	{
		arrow = (Arrow) entity;
		if(arrow.getShooter() instanceof Player)
		{
			Player p = (Player) arrow.getShooter();
			return "elf".equals(Data.getRace(MojangIdProvider.getId(p)));
		}
		return false;
	}

	public boolean isFruit()
	{
		return fruits.contains(fruit);
	}

	//Applying boosts. ------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void applyTameBoost()
	{
		Horse h = (Horse) mount;
		player = (Player) entity;
		if(!h.isTamed())
		{
			h.setOwner(player);
		}
	}

	public void applyBowDamage()
	{
		livingVictim = (LivingEntity) victim;
		livingVictim.damage(Minegusta.getConfig().getDouble("elf_bonus_damage_bow"));
	}

	public void applyFoodRegenBoost()
	{
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, 0, false));
	}

	//Applying weaknesses. --------------------------------------------------------------------------------------------------------------------------------------------------------

	public void applyFireDamage()
	{
		player = (Player) entity;
		player.damage(damage);
	}
}
