package com.minegusta.minegustastuff.races.cure;

import com.minegusta.minegustastuff.races.Data;
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
    private PlayerInteractEvent event;


    private Altar(PlayerInteractEvent e) {
        p = e.getPlayer();
        race = Data.getRace(p.getUniqueId().toString());
        b = e.getClickedBlock();
        a = e.getAction();
        event = e;
    }

    public static Altar altarCheck(PlayerInteractEvent e) {
        return new Altar(e);
    }

    public boolean isAltar() {
        return mat.equals(Material.EMERALD_BLOCK);
    }

    public boolean isRightClick() {
        if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
            mat = event.getClickedBlock().getType();
            return true;
        }
        return false;
    }

    public boolean hasDiamondBlocks() {
        return (b.getRelative(BlockFace.DOWN, 1).getType().equals(Material.DIAMOND_BLOCK) && b.getRelative(BlockFace.UP, 1).getType().equals(Material.DIAMOND_BLOCK));
    }

    public boolean isNotHuman() {
        return !race.equalsIgnoreCase("human");
    }
}
