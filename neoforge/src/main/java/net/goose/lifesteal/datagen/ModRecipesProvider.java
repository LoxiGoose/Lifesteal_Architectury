package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEART_CORE.get())
                .pattern("fff")
                .pattern("fgf")
                .pattern("fff")
                .define('f', ModItems.HEART_FRAGMENT.get())
                .define('g', Items.GOLDEN_APPLE)
                .unlockedBy("has_fragment", RecipeProvider.has(ModItems.HEART_FRAGMENT.get()))
                .save(consumer, new ResourceLocation(LifeSteal.MOD_ID, "heart_core"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.HEART_CORE_BLOCK.get().asItem())
                .pattern("cfc")
                .pattern("fdf")
                .pattern("cfc")
                .define('c', ModItems.HEART_CORE.get())
                .define('f', ModItems.HEART_FRAGMENT.get())
                .define('d', Items.DIAMOND)
                .unlockedBy("has_heart_core", RecipeProvider.has(ModItems.HEART_CORE.get()))
                .save(consumer, new ResourceLocation(LifeSteal.MOD_ID, "heart_core_block"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEART_CRYSTAL.get())
                .pattern("bhb")
                .pattern("dcd")
                .pattern("rbg")
                .define('b', ModBlocks.HEART_CORE_BLOCK.get())
                .define('d', Items.DIAMOND)
                .define('c', ModItems.HEART_CORE.get())
                .define('r', Items.BLAZE_ROD)
                .define('g', Items.GOLDEN_APPLE)
                .define('h', Items.HEART_OF_THE_SEA)
                .unlockedBy("has_heart_of_the_sea", RecipeProvider.has(Items.HEART_OF_THE_SEA))
                .save(consumer, new ResourceLocation(LifeSteal.MOD_ID, "heart_crystal"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.REVIVE_CRYSTAL.get())
                .pattern("gcg")
                .pattern("nhn")
                .pattern("ctc")
                .define('g', Items.GHAST_TEAR)
                .define('n', Items.NETHERITE_INGOT)
                .define('c', ModItems.HEART_CORE.get())
                .define('t', Items.TOTEM_OF_UNDYING)
                .define('h', ModItems.HEART_CRYSTAL.get())
                .unlockedBy("has_totem", RecipeProvider.has(Items.TOTEM_OF_UNDYING))
                .save(consumer, new ResourceLocation(LifeSteal.MOD_ID, "revive_crystal"));
    }
}
