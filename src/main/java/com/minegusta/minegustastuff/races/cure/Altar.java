package com.minegusta.minegustastuff.races.cure;

import com.minegusta.minegustastuff.races.Data;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Altar {

    private static Material mat;
    private static Player p;
    private static String race;
    private static Block b;
    private static Action a;


    private Altar(PlayerInteractEvent e) {
        mat = e.getClickedBlock().getType();
        p = e.getPlayer();
        race = Data.getRace(p.getUniqueId().toString());
        b = e.getClickedBlock();
        a = e.getAction();
    }

    public static Altar altarCheck(PlayerInteractEvent e) {
        return new Altar(e);
    }

    public boolean isAltar() {
        return mat.equals(Material.EMERALD_BLOCK);
    }

    public boolean isRightClick() {
        return a.equals(Action.RIGHT_CLICK_BLOCK);
    }

    public boolean hasDiamondBlocks() {
        if (b.getRelative(BlockFace.DOWN, 1).getType().equals(Material.DIAMOND_BLOCK) && b.getRelative(BlockFace.DOWN, 2).getType().equals(Material.DIAMOND_BLOCK)) {
            return true;
        }
        return false;
    }

    public boolean isNotHuman() {
        return !race.equalsIgnoreCase("human");
    }

    public void playEffect() {
        b.getWorld().spigot().playEffect(b.getLocation(), Effect.CLOUD, 0, 0, 5, 5, 5, 1, 50, 25);
    }
}
