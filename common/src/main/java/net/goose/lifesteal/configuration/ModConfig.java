package net.goose.lifesteal.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public final ForgeConfigSpec.IntValue startingHeartDifference;
    public final ForgeConfigSpec.BooleanValue shouldAllMobsGiveHearts;
    public final ForgeConfigSpec.BooleanValue loseHeartsWhenKilledByPlayer;
    public final ForgeConfigSpec.BooleanValue loseHeartsWhenKilledByMob;
    public final ForgeConfigSpec.BooleanValue loseHeartsWhenKilledByEnvironment;
    public final ForgeConfigSpec.IntValue amountOfHealthLostUponLoss;
    public final ForgeConfigSpec.IntValue maximumamountofhitpointsGainable;
    public final ForgeConfigSpec.IntValue maximumamountofhitpointsLoseable;
    public final ForgeConfigSpec.BooleanValue disableLifesteal;
    public final ForgeConfigSpec.BooleanValue preventFromUsingCrystalIfMax;
    public final ForgeConfigSpec.BooleanValue preventFromUsingCoreIfMax;
    public final ForgeConfigSpec.BooleanValue disableHeartCrystals;
    public final ForgeConfigSpec.BooleanValue disableUnnaturalHeartCrystals;
    public final ForgeConfigSpec.BooleanValue disableHeartCores;
    public final ForgeConfigSpec.BooleanValue disableReviveCrystals;
    public final ForgeConfigSpec.BooleanValue playersGainHeartsifKillednoHeart;
    public final ForgeConfigSpec.IntValue heartCrystalAmountGain;
    public final ForgeConfigSpec.DoubleValue heartCoreHeal;
    public final ForgeConfigSpec.BooleanValue disableStatusEffects;
    public final ForgeConfigSpec.BooleanValue disableLightningEffect;
    public final ForgeConfigSpec.BooleanValue customHeartDifferenceWhenRevived;
    public final ForgeConfigSpec.IntValue startingHeartDifferenceFromCrystal;
    public final ForgeConfigSpec.BooleanValue tellPlayersIfHitPointChanged;
    public final ForgeConfigSpec.BooleanValue disableWithdrawing;
    public final ForgeConfigSpec.ConfigValue advancementUsedForWithdrawing;
    public final ForgeConfigSpec.ConfigValue textUsedForRequirementOnWithdrawing;
    public final ForgeConfigSpec.BooleanValue tellPlayersIfReachedMaxHearts;
    public final ForgeConfigSpec.BooleanValue silentlyRevivePlayer;
    public final ForgeConfigSpec.BooleanValue playersSpawnHeadUponDeath;
    public final ForgeConfigSpec.BooleanValue playerDropsHeartCrystalWhenKilled;
    public final ForgeConfigSpec.BooleanValue playerDropsHeartCrystalOnlyWhenKillerHasMax;
    public final ForgeConfigSpec.BooleanValue uponDeathBanned;


    public ModConfig(final ForgeConfigSpec.Builder builder) {
        builder.comment("It's recommended to edit the config BEFORE you make/play a world. While editing the config in an already generated world can work sometimes, there may be visual bugs or just bugs in general.");
        builder.comment("This category holds general values that will mostly be customized by most.");
        builder.push("Starting Configurations");
        this.startingHeartDifference = buildInt(builder, "Starting HitPoint Difference:", 0, -19, Integer.MAX_VALUE, "This value modifies how many hearts you'll start at in a world. 2 would mean 1 extra heart, -2 would mean 1 less heart. If you have lives enabled, you'll gain a life when you get max hearts double your starting hearts. EX: If 3 hearts is your starting value, you'll gain a life if you get 3 more hearts. ");
        this.amountOfHealthLostUponLoss = buildInt(builder, "Amount of HitPoints/Health Lost/Given Upon Death/Kill:", 2, 1, Integer.MAX_VALUE, "This values modifies the amount of hit points that should be lost when you die. The same also applies when you gain max health from lifestealing. 2 hit points = 1 health.");
        this.playersSpawnHeadUponDeath = buildBoolean(builder, "Players Spawn Their Head Upon Death:", true, "In multiplayer, this value determines whether heads spawn or not when a player dies. NOTE: In singleplayer, this value is always false.");
        this.uponDeathBanned = buildBoolean(builder, "Players get Banned Upon Losing All Hearts:", true, "In multiplayer, this value decides whether or not a player gets banned when they lose all hearts. If this value is false, they will go into spectator mode. This value does NOT affect singleplayer, you'll always go into spectator.");

        builder.pop();

        builder.comment("This category is for how players should lose hearts. If you want players to not be able to lose hearts at all, disable all the config options below.");
        builder.push("Losing Hearts");
        this.loseHeartsWhenKilledByPlayer = buildBoolean(builder, "Lose Hearts When Killed By a Player:", true, "When this is true, you will lose hearts when killed by a player.");
        this.loseHeartsWhenKilledByMob = buildBoolean(builder, "Lose Hearts When Killed By a Mob:", true, "When this is true, you will lose hearts when killed by a mob.");
        this.loseHeartsWhenKilledByEnvironment = buildBoolean(builder, "Lose Hearts When Killed By the Environment:", true, "When this is true, you will lose hearts when killed by the environment.");
        builder.pop();

        builder.comment("This category is the configuration for items and enchantments in this mod");
        builder.push("Items and Enchantments");
        builder.push("Heart Cores");
        this.disableHeartCores = buildBoolean(builder, "Disable Heart Cores:", false, "Heart Cores can heal on default 25% of your health if right clicked. This value determines if they should be disabled.");
        this.heartCoreHeal = buildDouble(builder, "Percentage of max Health Heart Core Heals", 0.33, 0.01, 1, "The percentage of max health a heart core should heal when used.");
        this.preventFromUsingCoreIfMax = buildBoolean(builder, "Prevent Players From Using Heart Cores If At Max Health:", true, "If this option is true, a player cannot eat heart cores if they are already at their max health.");

        builder.pop();
        builder.push("Heart Crystals");
        this.disableHeartCrystals = buildBoolean(builder, "Disable Heart Crystals:", false, "Heart Crystals increase the max amount of hearts you get when eaten, this value determines whether they are enabled or not. This will NOT affect Unnatural Heart Crystals.");
        this.disableUnnaturalHeartCrystals = buildBoolean(builder, "Disable Unnatural Heart Crystals:", false, "Disables Unnatural Heart Crystals. Unnatural Heart Crystals are achieved by using the withdraw command.");
        this.heartCrystalAmountGain = buildInt(builder, "Amount of HitPoints Heart Crystal Permanently Gives:", 2, 1, Integer.MAX_VALUE, "This is the amount of hit points a Heart Crystal should give when used. 2 HitPoints = 1 Heart, 3 = 1.5 Heart.");
        this.preventFromUsingCrystalIfMax = buildBoolean(builder, "Prevent Players From Using Heart Crystals If At Max Hearts:", true, "If a max is set for the amount of hearts you can get, this option when true, makes it so players can't eat heart crystals if they're already at the max.");

        builder.pop();
        builder.push("Revive Crystals");
        this.disableReviveCrystals = buildBoolean(builder, "Disable Revive Crystals:", false, "This value determines whether or not revive crystals are disabled. If you're in singleplayer, this value is always true.");
        this.silentlyRevivePlayer = buildBoolean(builder, "Silently Revive Players:", false, "When a player is revived, this value determines whether or not a chat message will be sent indicating they have been brought back from the dead.");
        this.disableLightningEffect = buildBoolean(builder, "Disable Lightning Effect:", false, "When a player is revived, lightning will strike the head from above for dramatic effect. This value can be used to disable this effect.");
        this.disableStatusEffects = buildBoolean(builder, "Disable Status Effects:", false, "When a player is revived, they get bonus effects such as regeneration and resistance. This value determines if they are disabled or not. It's recommended to keep this on if you are keeping the lightning effect above.");
        this.customHeartDifferenceWhenRevived = buildBoolean(builder, "Use a Custom Heart Difference for Revived Players:", false, "If you want revived players to start off with a different heart difference, enable this value and configure the amount you'd like below");
        this.startingHeartDifferenceFromCrystal = buildInt(builder, "Amount of Hitpoints Given When Revived:", 0, -19, Integer.MAX_VALUE, "This is applied the same way StartingHeartDifference works, this is used instead of StartingHeartDifference if the configuration to use this value is enabled.");

        builder.pop();
        builder.pop();
        builder.comment("This category is everything related to life stealing from someone.");
        builder.push("Lifesteal Related");
        this.disableLifesteal = buildBoolean(builder, "Disable Lifesteal:", false, "This makes it so you can't gain hearts from killing other players. THIS DOESN'T AFFECT LOSING HEARTS.");
        this.playersGainHeartsifKillednoHeart = buildBoolean(builder, "Players Gain Hearts From No Heart Players:", false, "This value determines if a player should still earn hearts from a player they killed even if the player doesn't have hearts to spare. EX: MinimumHeartHave");
        this.playerDropsHeartCrystalWhenKilled = buildBoolean(builder, "Players Drop Heart Crystals When Killed:", false, "This value determines whether the killer will automatically gain hearts from a player or if the player drops a heart crystal instead that can be eaten. The config that disables Unnatural Heart Crystals will not affect this.");
        this.playerDropsHeartCrystalOnlyWhenKillerHasMax = buildBoolean(builder, "Players Drop Heart Crystals Only When Killer has Max", false, "This value determines if the above value should only apply if the killer has received the max amount of hearts. This requires both the above value and MaximumHearts config to be enabled.");

        builder.pop();
        builder.comment("This category will hold the maximums for certain values");
        builder.push("Maximums");
        this.maximumamountofhitpointsGainable = buildInt(builder, "Maximum Amount of Hitpoints a Player can get:", -1, -1, Integer.MAX_VALUE, "This value makes a limit SET after your Starting HitPoint Difference for how many hit points/hearts a player can get. 2 hit points = 1 heart. Set this to less than 0 to disable the feature.");
        this.maximumamountofhitpointsLoseable = buildInt(builder, "Maximum Amount Of Hitpoints a Player can Lose:", -1, -1, Integer.MAX_VALUE, "This value makes a limit set on how many hit points/hearts a player can lose, this value is actually set depending on the Starting Health Difference. EX: Starting Health Difference - MinimumHeartHave. Set this to less than 0 to disable the feature.");
        this.tellPlayersIfReachedMaxHearts = buildBoolean(builder, "Tell Players if They Have Reached max Hearts:", true, "When a player has reached max hearts or attempt to go higher than the max, if this value is true, a message will let them know indicating they cannot go higher.");

        builder.pop();
        builder.comment("This category holds values related to commands.");
        builder.push("Commands");
        this.tellPlayersIfHitPointChanged = buildBoolean(builder, "Tell Players if Their HitPoint Difference Changed:", true, "This just makes it so when an admin changed a person's hitpoints, this value would determine if the game should tell the person in chat that their hitpoints was changed.");
        builder.push("Withdrawing");
        this.disableWithdrawing = buildBoolean(builder, "Disable Withdrawing", false, "This value determines if withdrawing hearts should be disabled or not.");
        this.advancementUsedForWithdrawing = buildString(builder, "The Advancement Needed to Unlock Withdrawing:", "lifesteal:story/get_heart_crystal", "This value determines the advancement used to unlock withdrawing. You would find the advancement you want to use by using the ID of the advancement which is found with the /advancement command. If the value is empty, withdraw will be unlocked automatically.");
        this.textUsedForRequirementOnWithdrawing = buildString(builder, "The Text Shown When Withdrawing Isn't Unlocked:", "You need to at least have gotten one heart crystal in this world to withdraw", "This value determines what text will pop up when a player hasn't unlocked withdrawing. If this value is empty, no text will pop up.");

        builder.pop();
        builder.pop();
        builder.comment("This category holds values that don't fit in other categories OR are not made for gameplay usage.");
        builder.push("Misc/Fun");
        this.shouldAllMobsGiveHearts = buildBoolean(builder, "Killing any Mobs Gives Hearts:", false, "When this is false, you can only gain hearts from killing players. Otherwise, any mob will give you hearts.");
    }

    private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, int defaultValue, int min, int max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ForgeConfigSpec.DoubleValue buildDouble(ForgeConfigSpec.Builder builder, String name, double defaultValue, double min, double max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }

    private static ForgeConfigSpec.ConfigValue buildString(ForgeConfigSpec.Builder builder, String name, String defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

    private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }

}