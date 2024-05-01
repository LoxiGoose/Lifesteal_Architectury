package net.goose.lifesteal.neoforge;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.configuration.ConfigHolder;
import net.goose.lifesteal.data.neoforge.ModCapabilities;
import net.goose.lifesteal.data.neoforge.ModDataAttachments;
import net.goose.lifesteal.datagen.ModDataGenerators;
import net.goose.lifesteal.neoforge.event.EventHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;

import static net.goose.lifesteal.advancement.ModCriteria.*;

@Mod(LifeSteal.MOD_ID)
public class LifestealNeoForge {
    public static IEventBus modEventBus;
    public LifestealNeoForge(IEventBus modEventBusParam) {
        modEventBus = modEventBusParam;
        modEventBus.addListener(this::registerEvent);
        IEventBus eventBus = NeoForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        LifeSteal.config = ConfigHolder.SERVER;
        LifeSteal.init();

        ModDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        eventBus.register(EventHandler.class);
        modEventBus.register(ModCapabilities.EventCapHandler.class);
        modEventBus.register(ModDataGenerators.class);

    }

    public static void registerCriteria(RegisterEvent registerEvent){
        LifeSteal.LOGGER.debug("Initializing ModCriteria for " + LifeSteal.MOD_ID);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), new ResourceLocation("lifesteal:get_10_max_hearts"), () -> GET_10_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), new ResourceLocation("lifesteal:use_totem_while_20_max_hearts"), () -> USE_TOTEM_WHILE_20_MAX_HEARTS);
        registerEvent.register(BuiltInRegistries.TRIGGER_TYPES.key(), new ResourceLocation("lifesteal:revived"), () -> REVIVED);
    }
    @SubscribeEvent
    public void registerEvent(RegisterEvent registerEvent){
        // NeoForge is special so we have to register criteria this way
        registerCriteria(registerEvent);
    }
}