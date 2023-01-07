package net.goose.lifesteal.mixin;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.data.HealthData;
import net.goose.lifesteal.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public void increaseHearts(HealthData data, int health, LivingEntity killedPlayer){
        if(LifeSteal.config.playerdropsHeartCrystalWhenKilled.get()){
            ItemStack itemStack = new ItemStack(ModItems.HEART_CRYSTAL.get());
            CompoundTag compoundTag = itemStack.getOrCreateTagElement("lifesteal");
            compoundTag.putBoolean("dropped", true);
            compoundTag.putBoolean("Unfresh", true);
            itemStack.setHoverName(Component.literal(killedPlayer.getName().getString() + "'s Heart"));

            ServerPlayer serverPlayer = (ServerPlayer) data.getLivingEntity();

            if (serverPlayer.getInventory().getFreeSlot() == -1) {
                serverPlayer.drop(itemStack, true);
            } else {
                serverPlayer.getInventory().add(itemStack);
            }
        }else {
            data.setHeartDifference(data.getHeartDifference() + health);
            data.refreshHearts(false);
        }
    }
    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void onDeath(final CallbackInfo info) {

        final int maximumheartsGainable = LifeSteal.config.maximumamountofheartsGainable.get();
        final int maximumheartsLoseable = LifeSteal.config.maximumamountofheartsLoseable.get();
        final int startingHitPointDifference = LifeSteal.config.startingHeartDifference.get();
        final int amountOfHealthLostUponLossConfig = LifeSteal.config.amountOfHealthLostUponLoss.get();
        final boolean playersGainHeartsifKillednoHeart = LifeSteal.config.playersGainHeartsifKillednoHeart.get();
        final boolean disableLifesteal = LifeSteal.config.disableLifesteal.get();
        final boolean loseHeartsWhenKilledByPlayer = LifeSteal.config.loseHeartsWhenKilledByPlayer.get();
        final boolean loseHeartsWhenKilledByMob = LifeSteal.config.loseHeartsWhenKilledByMob.get();
        final boolean loseHeartsWhenKilledByEnvironment = LifeSteal.config.loseHeartsWhenKilledByEnvironment.get();

        LivingEntity killedEntity = this;

        HealthData.get(killedEntity).ifPresent(healthData -> {
            if (killedEntity instanceof ServerPlayer) {
                if (!killedEntity.isAlive()) {
                    int HeartDifference = healthData.getHeartDifference();

                    LivingEntity killerEntity = killedEntity.getLastHurtByMob();
                    boolean killerEntityIsPlayer = killerEntity instanceof ServerPlayer;
                    ServerPlayer serverPlayer;
                    if (killerEntityIsPlayer) {
                        serverPlayer = (ServerPlayer) killerEntity;
                    } else {
                        serverPlayer = null;
                    }

                    int amountOfHealthLostUponLoss;

                    if (maximumheartsLoseable < 0) {
                        if (20 + HeartDifference - amountOfHealthLostUponLossConfig >= 0 || playersGainHeartsifKillednoHeart) {
                            amountOfHealthLostUponLoss = amountOfHealthLostUponLossConfig;
                        } else {
                            amountOfHealthLostUponLoss = 20 + HeartDifference;
                        }
                    } else {
                        if (20 + HeartDifference - amountOfHealthLostUponLossConfig >= (20 + startingHitPointDifference) - maximumheartsLoseable || playersGainHeartsifKillednoHeart) {
                            amountOfHealthLostUponLoss = amountOfHealthLostUponLossConfig;
                        } else {
                            amountOfHealthLostUponLoss = HeartDifference + maximumheartsLoseable;
                        }
                    }

                    // THE CODE BELOW IS FOR INCREASING THE KILLER ENTITY HITPOINTDIFFERENCE IF THEY EXIST
                    if (killerEntity != null) { // IF THERE IS A KILLER ENTITY
                        if (killerEntity != killedEntity) { // IF IT'S NOT THEMSELVES (Shooting themselves with an arrow lol)
                            // EVERYTHING BELOW THIS COMMENT IS CODE FOR MAKING THE KILLER PERSON'S HEART DIFFERENCE GO UP.
                            if (killerEntityIsPlayer && !disableLifesteal) {
                                HealthData.get(killerEntity).ifPresent(killerData -> {
                                    if (playersGainHeartsifKillednoHeart) {
                                        increaseHearts(killerData, amountOfHealthLostUponLoss, killedEntity);
                                    } else {
                                        if (maximumheartsLoseable > -1) {
                                            if (startingHitPointDifference + HeartDifference > -maximumheartsLoseable) {
                                                increaseHearts(killerData, amountOfHealthLostUponLoss, killedEntity);
                                            } else {
                                                serverPlayer.sendSystemMessage(Component.translatable("chat.message.lifesteal.no_more_hearts_to_steal"));
                                            }

                                        } else {
                                            increaseHearts(killerData, amountOfHealthLostUponLoss, killedEntity);
                                        }
                                    }

                                });
                            }
                        }
                    }

                    // THE CODE BELOW IS FOR REDUCING THE KILLED ENTITY'S HITPOINTDIFFERENCE

                    if (loseHeartsWhenKilledByPlayer || loseHeartsWhenKilledByMob || loseHeartsWhenKilledByEnvironment) {
                        if (killerEntity != null) { // IF KILLER ENTITY EXISTS
                            if (killedEntity != killerEntity) { // IF KILLER ENTITY ISNT SELF/ IF A KILLER KILLED OUR GUY
                                if (killerEntityIsPlayer) { // IF THEY ARE A PLAYER
                                    if(!loseHeartsWhenKilledByPlayer){
                                        return;
                                    }
                                } else if (!loseHeartsWhenKilledByMob) {
                                    return;
                                }
                            } else if (!loseHeartsWhenKilledByPlayer) {
                                return;
                            }
                        } else if (!loseHeartsWhenKilledByEnvironment) {
                            return;
                        }
                    } else {
                        return;
                    }

                    healthData.setHeartDifference(healthData.getHeartDifference() - amountOfHealthLostUponLoss);
                    healthData.refreshHearts(false);
                }
            }
        });
    }
}
