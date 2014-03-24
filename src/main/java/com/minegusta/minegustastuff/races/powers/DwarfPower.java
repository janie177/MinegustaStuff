package com.minegusta.minegustastuff.races.powers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.minegustastuff.MinegustaStuff;
import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.RaceManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class DwarfPower {

//Lists and Maps. -------------------------------------------------------------------------------------------------------------------------------------------------------------

    List<ItemStack> itemStackList = Lists.newArrayList(new ItemStack(Material.WOOD_AXE), new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.GOLD_AXE), new ItemStack(Material.IRON_AXE), new ItemStack(Material.STONE_AXE));
    ConcurrentMap<String, Long> battleCryCooldown = Maps.newConcurrentMap();


//Variables. ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //essential
    Entity entity;
    EntityDamageEvent.DamageCause cause;
    Entity victim;
    World world;
    Player player;
    double damage;
    LivingEntity livingVictim;
    String uuid;
    Action action;
    PlayerInteractEvent event;
    PlayerInteractEntityEvent entityEvent;

//Constructors. ---------------------------------------------------------------------------------------------------------------------------------------------------------------

    private DwarfPower(EntityDamageByEntityEvent e) {
        entity = e.getDamager();
        victim = e.getEntity();
        damage = e.getDamage();
        uuid = entity.getUniqueId().toString();
    }

    private DwarfPower(EntityDamageEvent e) {
        entity = e.getEntity();
        cause = e.getCause();
        uuid = entity.getUniqueId().toString();
    }

    private DwarfPower(EntityDeathEvent e) {
        victim = e.getEntity();
        entity = e.getEntity().getKiller();
        world = victim.getWorld();
    }

    private DwarfPower(BlockBreakEvent e) {
        player = e.getPlayer();
        entity = player;
        uuid = entity.getUniqueId().toString();
    }

    private DwarfPower(PlayerInteractEvent e) {
        player = e.getPlayer();
        entity = player;
        action = e.getAction();
        event = e;
        uuid = entity.getUniqueId().toString();
    }

    private DwarfPower(PlayerInteractEntityEvent e) {
        player = e.getPlayer();
        entity = player;
        entityEvent = e;
        uuid = entity.getUniqueId().toString();
    }


//getting the class. ----------------------------------------------------------------------------------------------------------------------------------------------------------

    public static DwarfPower arrowWeaknesBoost(EntityDamageEvent e) {
        return new DwarfPower(e);
    }

    public static DwarfPower strengthBoost(EntityDeathEvent e) {
        return new DwarfPower(e);
    }

    public static DwarfPower miningBoost(BlockBreakEvent event) {
        return new DwarfPower(event);
    }

    public static DwarfPower axeBoost(EntityDamageByEntityEvent e) {
        return new DwarfPower(e);
    }

    public static DwarfPower battleCryBoost(PlayerInteractEvent e) {
        return new DwarfPower(e);
    }

    public static DwarfPower battleCryBoost(PlayerInteractEntityEvent e) {
        return new DwarfPower(e);
    }

//Boolean Methods -------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean hasAxe() {
        player = (Player) entity;
        return itemStackList.contains(player.getItemInHand());
    }

    public boolean isPlayer() {
        return entity instanceof Player;
    }

    public boolean isDwarf() {
        return RaceManager.pRaces.containsKey(uuid) && RaceManager.pRaces.get(uuid).equalsIgnoreCase("dwarf");
    }

    public boolean victimIsLiving() {
        return victim instanceof LivingEntity;
    }

    public boolean killerIsPlayer() {
        if (entity instanceof Player) {
            uuid = entity.getUniqueId().toString();
            return true;
        }
        return false;
    }

    public boolean isRightClick() {
        return ((action.equals(Action.RIGHT_CLICK_AIR)) || (action.equals(Action.RIGHT_CLICK_BLOCK)));
    }

    public boolean isProjectile() {
        return cause.equals(EntityDamageEvent.DamageCause.PROJECTILE);
    }

    private boolean isInBattleCryMap() {
        return battleCryCooldown.containsKey(player.getName());
    }

//Applying boosts. ------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void applyStrengthBoost() {
        Player p = (Player) entity;
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 4 * 20, 0, true));
        world.spigot().playEffect(entity.getLocation(), Effect.VILLAGER_THUNDERCLOUD, 0, 0, 1, 1, 1, 1, 6, 15);
        world.playSound(entity.getLocation(), Sound.BLAZE_BREATH, 1, 1);
    }

    public void applyMiningBoost() {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 2, 3, true));
    }

    public void applyAxeBoost() {
        livingVictim = (LivingEntity) victim;
        livingVictim.damage(ConfigFile.getDefaultConfig().getDouble("dwarf_bonus_damage_axe"));
    }

    public void applyBattleCryBoost() {
        if (isInBattleCryMap()) {
            player = (Player) entity;
            long coolDownTime = TimeUnit.SECONDS.toMillis(ConfigFile.getDefaultConfig().getInt("battlecry_cooldown"));
            if (System.currentTimeMillis() - battleCryCooldown.get(player.getName()) >= coolDownTime) {
                runBattleCry(player);
                if (event != null) event.setCancelled(true);
                if (entityEvent != null) entityEvent.setCancelled(true);
            } else {
                player.sendMessage(ChatColor.RED + "You gotta wait another " + getRemainingCooldown(coolDownTime - (System.currentTimeMillis() - battleCryCooldown.get(player))) + " before you can use battlecry again.");
            }
        }
        if (event != null) event.setCancelled(true);
        if (entityEvent != null) entityEvent.setCancelled(true);
        battleCryCooldown.put(player.getName(), System.currentTimeMillis());
        runBattleCry(player);
    }

//Applying weaknesses. --------------------------------------------------------------------------------------------------------------------------------------------------------

    public void applyProjectileWeakness() {
        player = (Player) entity;
        player.damage(MinegustaStuff.PLUGIN.getConfig().getDouble("dwarf_weakness_arrows"));
    }

//Methods.

    private void runBattleCry(Player p) {
        p.getWorld().spigot().playEffect(p.getLocation(), Effect.VILLAGER_THUNDERCLOUD, 0, 0, 1, 1, 1, 1, 15, 20);
        p.sendMessage(ChatColor.RED + "You knock back your enemies!");
        for (Entity e : p.getNearbyEntities(3.0, 3.0, 3.0)) {
            if (!(e instanceof LivingEntity)) return;
            LivingEntity le = (LivingEntity) e;
            le.getWorld().spigot().playEffect(le.getLocation(), Effect.CRIT, 0, 0, 1, 1, 1, 1, 15, 20);
            le.getWorld().playSound(le.getLocation(), Sound.ANVIL_USE, 1, 1);
            le.setVelocity(le.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(MinegustaStuff.PLUGIN.getConfig().getDouble("battlecry_power")));
        }
    }

    private static String getRemainingCooldown(long millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder();
        if (minutes != 0L) {
            sb.append(minutes);
            sb.append(" minutes ");
        }

        if (seconds != 0L) {
            sb.append(seconds);
            sb.append(" seconds.");
        }

        return sb.toString();
    }

}
