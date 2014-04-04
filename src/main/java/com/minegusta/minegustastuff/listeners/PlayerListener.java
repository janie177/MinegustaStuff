package com.minegusta.minegustastuff.listeners;

import com.minegusta.minegustastuff.Minegusta;
import com.minegusta.minegustastuff.races.RaceManager;
import com.minegusta.minegustastuff.races.cure.Altar;
import com.minegusta.minegustastuff.races.cure.Cure;
import com.minegusta.minegustastuff.races.health.HealthManager;
import com.minegusta.minegustastuff.races.infection.DwarfInfect;
import com.minegusta.minegustastuff.races.infection.ElfInfect;
import com.minegusta.minegustastuff.races.infection.EnderbornInfect;
import com.minegusta.minegustastuff.races.powers.DwarfPower;
import com.minegusta.minegustastuff.races.powers.ElfPower;
import com.minegusta.minegustastuff.races.powers.EnderbornPower;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import org.spigotmc.event.entity.EntityMountEvent;

public class PlayerListener implements Listener
{
	@EventHandler
	public void playerEatEvent(PlayerItemConsumeEvent e)
	{
		if(!Minegusta.getServer().containsWorld(e.getPlayer().getWorld())) return;

		ElfPower fruit = ElfPower.consumeFruit(e);

		ElfInfect food = ElfInfect.elfInfect(e);

		if(fruit.isPlayer() && fruit.isElf() && fruit.isFruit())
		{
			fruit.applyFoodRegenBoost();
		}

		if(food.isHuman() && food.hasLore() && food.isVeganStew())
		{
			food.makeElf();
		}
	}

	@EventHandler
	public void playerBreakBlock(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		if(!Minegusta.getServer().containsWorld(e.getPlayer().getWorld())) return;

		DwarfPower mine = DwarfPower.miningBoost(e);
		if(mine.isPlayer() && mine.isDwarf())
		{
			mine.applyMiningBoost();
		}
	}

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();

		HealthManager.checkPlayerHealth(p, p.getWorld());

		RaceManager.addPlayerToRaceMap(p);
	}

	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();

		HealthManager.checkPlayerHealth(p, p.getWorld());

		RaceManager.removePlayerFromRaceMap(p);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void entityDamageByEntity(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		if(!Minegusta.getServer().containsWorld(e.getEntity().getWorld())) return;

		ElfPower arrow = ElfPower.arrowDamage(e);
		DwarfPower axe = DwarfPower.axeBoost(e);
		EnderbornPower bleed = EnderbornPower.bleedBoost(e);
		EnderbornPower pearl = EnderbornPower.enderPearlDamage(e);

		if(arrow.isBowDamage() && arrow.isPlayer() && arrow.arrowIsFiredByElf() && arrow.victimIsLiving() && arrow.canPVP())
		{
			arrow.applyBowDamage();
		}

		if(axe.isPlayer() && axe.isDwarf() && axe.hasAxe() && axe.canPVP())
		{
			axe.applyAxeBoost();
		}

		if(bleed.isPlayer() && bleed.damagerIsEndeborn() && bleed.victimIsLiving() && bleed.canPVP())
		{
			bleed.applyBleedBoost();
		}

		if(pearl.isPearl() && pearl.isPlayer() && pearl.entityIsEnderBorn())
		{
			pearl.cancelPearlDamage();
		}
	}

	@EventHandler
	public void entityDamageEvent(EntityDamageEvent e)
	{
		if(e.isCancelled()) return;
		if(!Minegusta.getServer().containsWorld(e.getEntity().getWorld())) return;

		DwarfPower arrowWeakness = DwarfPower.arrowWeaknesBoost(e);
		ElfPower fireWeakness = ElfPower.fireDamage(e);
		EnderbornPower fallDamage = EnderbornPower.fallDamageBoost(e);

		if(arrowWeakness.isProjectile() && arrowWeakness.isPlayer() && arrowWeakness.isDwarf() && arrowWeakness.canPVP())
		{
			arrowWeakness.applyProjectileWeakness();
		}
		if(fireWeakness.isFireDamage() && fireWeakness.isPlayer() && fireWeakness.isElf())
		{
			fireWeakness.applyFireDamage();
		}

		if(fallDamage.isPlayer() && fallDamage.entityIsEnderBorn() && fallDamage.isFallDamage())
		{
			fallDamage.cancelFallDamae();
		}
	}

	@EventHandler
	public void entityDeathEvent(EntityDeathEvent e)
	{
		if(!Minegusta.getServer().containsWorld(e.getEntity().getWorld())) return;

		DwarfPower strengthBoost = DwarfPower.strengthBoost(e);

		DwarfInfect dwarfInfect = DwarfInfect.dwarfInfect(e);

		if(strengthBoost.isPlayer() && strengthBoost.killerIsPlayer() && strengthBoost.isDwarf())
		{
			strengthBoost.applyStrengthBoost();
		}

		if(dwarfInfect.isByLava() && dwarfInfect.isHuman() && dwarfInfect.hasShinyGem())
		{
			dwarfInfect.makeDwarf();
		}
	}

	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent e)
	{
		if(!Minegusta.getServer().containsWorld(e.getPlayer().getWorld())) return;

		DwarfPower rage = DwarfPower.battleCryBoost(e);
		Altar altar = Altar.altarCheck(e);

		if(rage.isRightClick() && rage.isPlayer() && rage.isDwarf() && rage.hasAxe())
		{
			rage.applyBattleCryBoost();
		}

		if(altar.isRightClick() && altar.isAltar() && altar.hasDiamondBlocks() && altar.isNotHuman())
		{
			Cure.curePlayer(e.getPlayer());
		}
	}

	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent e)
	{
		if(!Minegusta.getServer().containsWorld(e.getPlayer().getWorld())) return;

		DwarfPower rage = DwarfPower.battleCryBoost(e);

		if(rage.isPlayer() && rage.isDwarf() && rage.hasAxe())
		{
			rage.applyBattleCryBoost();
		}
	}

	@EventHandler
	public void entityMountEvent(EntityMountEvent e)
	{
		if(!Minegusta.getServer().containsWorld(e.getEntity().getWorld())) return;

		ElfPower mount = ElfPower.horseMount(e);

		if(mount.isPlayer() && mount.isElf() && mount.isHorse())
		{
			mount.applyTameBoost();
		}
	}

	@EventHandler
	public void onPlayerWorldSwitch(PlayerChangedWorldEvent e)
	{
		Player p = e.getPlayer();
		World w = p.getWorld();

		HealthManager.checkPlayerHealth(p, w);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatEvent(AsyncPlayerChatEvent e)
	{
		if(e.isCancelled()) return;
		if(!Minegusta.getServer().containsWorld(e.getPlayer().getWorld())) return;

		EnderbornInfect enderbornInfect = EnderbornInfect.enderbornInfect(e);

		if(enderbornInfect.isSpell() && enderbornInfect.isHuman() && enderbornInfect.hasEyesOfEnder())
		{
			enderbornInfect.makeEnderborn();
		}
	}
}
