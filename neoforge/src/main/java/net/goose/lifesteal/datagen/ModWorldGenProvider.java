package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.datagen.level.BiomeModifiersProvider;
import net.goose.lifesteal.datagen.level.ConfiguredFeaturesProvider;
import net.goose.lifesteal.datagen.level.PlacedFeaturesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeaturesProvider::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeaturesProvider::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiersProvider::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(LifeSteal.MOD_ID));
    }

}
