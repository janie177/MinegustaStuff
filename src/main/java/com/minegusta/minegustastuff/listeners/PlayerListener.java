package com.minegusta.minegustastuff.listeners;

import com.minegusta.minegustastuff.MinegustaStuff;
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
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import org.spigotmc.event.entity.EntityMountEvent;

public class PlayerListener implements Listener {


    @EventHandler
    public void playerEatEvent(PlayerItemConsumeEvent e) {
        World w = e.getPlayer().getWorld();
        if (!pass(w)) return;

        ElfPower fruit = ElfPower.consumeFruit(e);

        ElfInfect food = ElfInfect.elfInfect(e);

        if (fruit.isElf() && fruit.isFruit()) {
            fruit.applyFoodRegenBoost();
        }

        if (food.isHuman() && food.isStew() && food.hasLore() && food.isVeganStew()) {
            food.makeElf();
        }


    }

    @EventHandler
    public void playerBreakBlock(BlockBreakEvent e) {
        World w = e.getPlayer().getWorld();
        if (!pass(w)) return;

        DwarfPower mine = DwarfPower.miningBoost(e);
        if (mine.isDwarf()) {
            mine.applyMiningBoost();
        }

    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        HealthManager.checkPlayerHealth(p, w);

        RaceManager.addPlayerToRaceMap(p);

    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        HealthManager.checkPlayerHealth(p, w);

        RaceManager.removePlayerFromRaceMap(p);

    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent e) {
        World w = e.getEntity().getWorld();
        if (!pass(w)) return;
        if (e.isCancelled()) return;

        ElfPower arrow = ElfPower.arrowDamage(e);
        DwarfPower axe = DwarfPower.axeBoost(e);
        EnderbornPower bleed = EnderbornPower.bleedBoost(e);
        EnderbornPower pearl = EnderbornPower.enderPearlDamage(e);

        if (arrow.isBowDamage() && arrow.arrowIsFiredByElf() && arrow.victimIsLiving()) {
            arrow.applyBowDamage();
        }

        if (axe.isDwarf() && axe.hasAxe()) {
            axe.applyAxeBoost();
        }

        if (bleed.damagerIsEndeborn() && bleed.victimIsLiving()) {
            bleed.applyBleedBoost();
        }

        if (pearl.isPearl() && pearl.entityIsEnderBorn()) {
            pearl.cancelPearlDamage();
        }

    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent e) {
        World w = e.getEntity().getWorld();
        if (!pass(w)) return;
        if (e.isCancelled()) return;

        DwarfPower arrowWeakness = DwarfPower.arrowWeaknesBoost(e);
        ElfPower fireWeakness = ElfPower.fireDamage(e);

        if (arrowWeakness.isProjectile() && arrowWeakness.isDwarf()) {
            arrowWeakness.applyProjectileWeakness();
        }
        if (fireWeakness.isFireDamage() && fireWeakness.isElf()) {
            fireWeakness.applyFireDamage();
        }
    }

    @EventHandler
    public void entityDeathEvent(EntityDeathEvent e) {
        World w = e.getEntity().getWorld();
        if (!pass(w)) return;

        DwarfPower strengthBoost = DwarfPower.strengthBoost(e);

        DwarfInfect dwarfInfect = DwarfInfect.dwarfInfect(e);

        if (strengthBoost.isPlayer() && strengthBoost.killerIsPlayer() && strengthBoost.isDwarf()) {
            strengthBoost.applyStrengthBoost();
        }

        if (dwarfInfect.isByLava() && dwarfInfect.isHuman() && dwarfInfect.hasShinyGem()) {
            dwarfInfect.makeDwarf();
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        if (!pass(w)) return;

        DwarfPower rage = DwarfPower.battleCryBoost(e);
        Altar altar = Altar.altarCheck(e);

        if (rage.isRightClick() && rage.isDwarf() && rage.hasAxe()) {
            rage.applyBattleCryBoost();
        }

        if (altar.isRightClick() && altar.isAltar() && altar.hasDiamondBlocks() && altar.isNotHuman()) {
            Cure.curePlayer(p);
        }


    }

    @EventHandler
    public void playerInteractEntityEvent(PlayerInteractEntityEvent e) {
        World w = e.getPlayer().getWorld();
        if (!pass(w)) return;

        DwarfPower rage = DwarfPower.battleCryBoost(e);

        if (rage.isRightClick() && rage.isDwarf() && rage.hasAxe()) {
            rage.applyAxeBoost();
        }

    }

    @EventHandler
    public void entityMountEvent(EntityMountEvent e) {
        World w = e.getEntity().getWorld();
        if (!pass(w)) return;

        ElfPower mount = ElfPower.horseMount(e);

        if (mount.isElf() && mount.isHorse()) {
            mount.applyTameBoost();
        }
    }

    @EventHandler
    public void onPlayerWorldSwitch(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        HealthManager.checkPlayerHealth(p, w);
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        World w = e.getPlayer().getWorld();
        if (!pass(w)) return;

        EnderbornInfect enderbornInfect = EnderbornInfect.enderbornInfect(e);

        if (enderbornInfect.isSpell() && enderbornInfect.isHuman() && enderbornInfect.hasEyesOfEnder()) {
            enderbornInfect.makeEnderborn();
        }
    }

    private boolean pass(World w) {
        return MinegustaStuff.worldCheck(w);
    }

}
