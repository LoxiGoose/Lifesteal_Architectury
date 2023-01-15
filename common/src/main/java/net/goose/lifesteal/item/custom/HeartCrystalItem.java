package net.goose.lifesteal.item.custom;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.concurrent.atomic.AtomicBoolean;

public class HeartCrystalItem extends Item {

    public static final FoodProperties HeartCrystal = (new FoodProperties.Builder()).alwaysEat().build();

    public HeartCrystalItem(Properties pProperties) {
        super(pProperties);
    }

    public void applyCrystalEffect(LivingEntity entity) {
        // Formula, for every hit point, increase duration of the regeneration by 50 ticks: TickDuration = MaxHealth * 50
        int tickTime = (int) (entity.getMaxHealth() * 50) / 4;
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, tickTime, 3));
    }

    public void canceled(ServerPlayer serverPlayer, ItemStack item) {
        item.grow(1);
        serverPlayer.containerMenu.broadcastChanges();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {

        if (!level.isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            CompoundTag compoundTag = item.getTagElement("lifesteal");
            boolean compoundTagExists;
            boolean droppedHeartCrystal;
            boolean unnaturalHeartCrystal;
            AtomicBoolean success = new AtomicBoolean(true);

            if (compoundTag != null) {
                compoundTagExists = true;
                droppedHeartCrystal = compoundTag.getBoolean("dropped");

                unnaturalHeartCrystal = compoundTag.getBoolean("Unfresh");
            } else {
                unnaturalHeartCrystal = false;
                droppedHeartCrystal = false;
                compoundTagExists = false;
            }

            if (!droppedHeartCrystal) {
                if (unnaturalHeartCrystal) {
                    if (LifeSteal.config.disableUnnaturalHeartCrystals.get()) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.unnatural_heart_crystal_disabled"), true);
                        canceled(serverPlayer, item);
                        success.set(false);
                    }
                } else {
                    if (LifeSteal.config.disableHeartCrystals.get()) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_crystal_disabled"), true);
                        canceled(serverPlayer, item);
                        success.set(false);
                    }
                }
            }

            HealthData.get(entity).ifPresent(IHeartCap -> {
                if (LifeSteal.config.maximumamountofhitpointsGainable.get() > -1 && LifeSteal.config.preventFromUsingCrystalIfMax.get()) {
                    int maximumheartDifference = LifeSteal.config.startingHeartDifference.get() + LifeSteal.config.maximumamountofhitpointsGainable.get();
                    if (IHeartCap.getHeartDifference() == maximumheartDifference) {
                        serverPlayer.displayClientMessage(Component.translatable("gui.lifesteal.heart_crystal_reaching_max"), true);
                        canceled(serverPlayer, item);
                        success.set(false);
                    }
                }

                if(success.get()){
                    int newheartDifference = IHeartCap.getHeartDifference() + LifeSteal.config.heartCrystalAmountGain.get();

                    IHeartCap.setHeartDifference(newheartDifference);
                    IHeartCap.refreshHearts(false);

                    // Formula, for every hit point, increase duration of the regeneration by 50 ticks: TickDuration = MaxHealth * 50
                    if (!compoundTagExists) {
                        applyCrystalEffect(entity);
                    } else if (!unnaturalHeartCrystal) {
                        applyCrystalEffect(entity);
                    }
                }
            });
        }
        return super.finishUsingItem(item, level, entity);
    }
}
