package com.minegusta.minegustastuff.races.commands;

import com.google.common.collect.Lists;
import com.minegusta.minegustastuff.data.ConfigFile;
import com.minegusta.minegustastuff.races.Data;
import com.minegusta.minegustastuff.races.Races;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RaceCommands implements CommandExecutor {

    List<String> help = Lists.newArrayList("/Race List" + ChatColor.GRAY + " - List all races.", "/Race Info <RaceName>" + ChatColor.GRAY + " - Gives information about a specific race.", "/Race Info" + ChatColor.GRAY + " - Shows your current race.", "/Race Infection <Race>" + ChatColor.GRAY + " - Help on becoming a race.");

    List<String> races = Races.RacesList.getRaces();

    List<String> dwarfInfect = Lists.newArrayList(ChatColor.RED + "- - - Dwarf Infection - - -", "To become a dwarf:", ChatColor.GRAY + "  - Die in lava.", ChatColor.GRAY + "  - Hold a Shiny Gem.", ChatColor.GRAY + "  - This has a 50% chance to make you dwarf.", "To make a Shiny Gem:", ChatColor.GRAY + "  - 1 Netherstar.", ChatColor.GRAY + "  - 3 Diamonds.", ChatColor.GRAY + "  - 3 Emeralds.", ChatColor.GRAY + "  - 2 LapisLazuliBlocks.");
    List<String> enderbornInfect = Lists.newArrayList(ChatColor.RED + "- - - Enderborn Infection - - -", "To become enderborn:", ChatColor.GRAY + "  - Say the spell: 'In fine autem omnes morimur' ", ChatColor.GRAY + "  - Hold 10 eyes of ender in your hand.");
    List<String> elfInfect = Lists.newArrayList(ChatColor.RED + "- - - Elf Infection - - -", "To become an elf:", ChatColor.GRAY + "  - Eat Vegan Stew.", "To make a Vegan Stew:", ChatColor.GRAY + "  - 3 Flowers.", ChatColor.GRAY + "  - 2 Carrots.", ChatColor.GRAY + "  - 2 Potatoes.", ChatColor.GRAY + "  - 1 Mushroom Soup.", ChatColor.GRAY + "  - 1 Leaves.");
    List<String> humanInfect = Lists.newArrayList(ChatColor.RED + "- - - Cure - - -", "To become a human again:", ChatColor.GRAY + "  - Right click an altar.", ChatColor.GRAY + "  - Pay 30 diamonds. (Hold them in your hand).", "To make an altar:", ChatColor.GRAY + "  - 1 Emerald Block", ChatColor.GRAY + "  - 2 Diamond Blocks under it.");

    List<String> reloaded = Lists.newArrayList("Sucessfully reloaded config.");

    private List<String> getRaceInfo(String raceName) {
        return Races.RacesList.getRaceInfo(raceName);
    }


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (cmd.getName().equalsIgnoreCase("race")) {
            if (args.length == 0) {
                sendText(help, p);
                return true;
            } else if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload") && p.isOp()) {
                    ConfigFile.reloadConfig();
                    sendText(reloaded, p);
                    return true;
                } else if (args[0].equalsIgnoreCase("infection") && args.length > 1) {
                    switch (args[1].toLowerCase()) {
                        case "human":
                            sendText(humanInfect, p);
                            break;
                        case "elf":
                            sendText(elfInfect, p);
                            break;
                        case "dwarf":
                            sendText(dwarfInfect, p);
                            break;
                        case "enderborn":
                            sendText(enderbornInfect, p);
                            break;
                        default:
                            sendText(help, p);
                            break;
                    }

                } else if (args[0].equalsIgnoreCase("List")) {
                    sendText(races, p);
                    return true;
                } else if (args[0].equalsIgnoreCase("Info")) {
                    if (args.length == 1) {
                        sendText(getRaceInfo(Data.getRace(p.getUniqueId().toString())), p);
                        return true;
                    } else if (listContainsString(args[1], races)) {
                        sendText(getRaceInfo(args[1].toLowerCase()), p);
                        return true;
                    } else {
                        sendText(help, p);
                        return true;
                    }
                }
            } else {
                sendText(help, p);
                return true;
            }
        }
        return true;
    }

    private void sendText(List<String> list, Player p) {
        p.sendMessage(ChatColor.GOLD + "- - - - - - - " + ChatColor.RED + "RACES HELP" + ChatColor.GOLD + " - - - - - - -");
        for (String s : list) {
            p.sendMessage(ChatColor.YELLOW + s);
        }
    }

    private boolean listContainsString(String s, List<String> l) {
        for (String string : l) {
            if (string.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}
