package com.minegusta.minegustastuff.races.powers;

import com.minegusta.minegustastuff.MinegustaStuff;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class EnderbornPower {


//Variables. ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    Entity entity;
    String uuid = entity.getUniqueId().toString();
    Entity victim;
    Entity damager;
    EntityDamageByEntityEvent event;

//Constructors. ---------------------------------------------------------------------------------------------------------------------------------------------------------------


    private EnderbornPower(EntityDamageByEntityEvent e) {
        entity = e.getEntity();
        damager = e.getDamager();
        event = e;
        victim = entity;
    }
//getting the class. ----------------------------------------------------------------------------------------------------------------------------------------------------------

    public static EnderbornPower enderPearlDamage(EntityDamageByEntityEvent e) {
        return new EnderbornPower(e);
    }

    public static EnderbornPower bleedBoost(EntityDamageByEntityEvent e) {
        return new EnderbornPower(e);
    }

//Boolean Methods -------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean isPlayer() {
        return entity instanceof Player;
    }

    public boolean isPearl() {
        return damager instanceof EnderPearl;
    }

    public boolean entityIsEnderBorn() {
        return RaceManager.pRaces.containsKey(uuid) && RaceManager.pRaces.get(uuid).equals("enderborn");
    }

    public boolean entityIsLiving() {
        return entity instanceof LivingEntity;
    }

    public boolean damagerIsEndeborn() {
        return RaceManager.pRaces.containsKey(damager.getUniqueId().toString()) && RaceManager.pRaces.get(damager.getUniqueId().toString()).equals("enderborn");
    }

    public boolean victimIsLiving() {
        return victim instanceof LivingEntity;
    }

//Applying boosts. ------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void cancelPearlDamage() {
        event.setDamage(0);
    }

    public void applyBleedBoost() {
        bleedEntity((LivingEntity) victim);
    }

//Applying weaknesses. --------------------------------------------------------------------------------------------------------------------------------------------------------


//Other

    private void bleedEntity(LivingEntity bleedingEntity) {
        Random rand = new Random();
        int chance = rand.nextInt(100);

        if (chance < 85) return;
        final LivingEntity e = bleedingEntity;
        for (int i = 0; i < 60; i++) {
            final int k = i;
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(MinegustaStuff.PLUGIN, new Runnable() {
                @Override
                public void run() {
                    if (e instanceof Player) {
                        Player p = (Player) e;
                        if (k == 1) p.sendMessage(ChatColor.RED + "You are bleeding!");
                    }
                    double d = e.getMaxHealth() / 15;
                    e.getWorld().spigot().playEffect(e.getLocation(), Effect.CRIT);
                    if (k == 20) e.damage(d);
                    if (k == 40) e.damage(d);
                    if (k == 59) e.damage(d);


                }
            }, i);
        }
    }
}


