package com.minegusta.minegustastuff.races.recipes;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;

public class Recipes {

    public static void registerRecipes() {
        elfRecipe();
        dwarfRecipe();
    }

    public static ItemStack shinyGem = new ItemStack(Material.NETHER_STAR, 1)
    {
        {
            List<String> lore = getItemMeta().getLore();
            lore.add(ChatColor.WHITE + "It's so shiny!!");
            getItemMeta().setLore(lore);
            getItemMeta().setDisplayName(ChatColor.GOLD + "Shiny Gem");
        }
    };

    private static void elfRecipe() {
        ItemStack i = new ItemStack(Material.MUSHROOM_SOUP, 1);
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GREEN + "Vegan Stew");
        i.getItemMeta().setLore(lore);
        i.getItemMeta().setDisplayName(ChatColor.DARK_GREEN + "Stew..?");


        Recipe elfRecipe = new ShapelessRecipe(i)
        {
            {
                addIngredient(3, Material.YELLOW_FLOWER);
                addIngredient(1, Material.MUSHROOM_SOUP);
                addIngredient(2, Material.POTATO_ITEM);
                addIngredient(2, Material.CARROT_ITEM);
                addIngredient(1, Material.LEAVES);
            }
        };

        Bukkit.getServer().addRecipe(elfRecipe);
    }

    private static void dwarfRecipe()
    {
        Recipe dwarfRecipe = new ShapelessRecipe(shinyGem)
        {
            {
                addIngredient(1, Material.NETHER_STAR);
                addIngredient(3, Material.DIAMOND);
                addIngredient(3, Material.EMERALD);
                addIngredient(2, Material.LAPIS_BLOCK);
            }
        };
    }

}
