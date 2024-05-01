package net.goose.lifesteal.common.tab.neoforge;

import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.common.tab.ModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ModTabsImpl {
    public static CreativeModeTab createTab(){
        return CreativeModeTab.builder().title(Component.translatable("itemGroup.lifesteal"))
                        .icon(ModTabs::makeIcon).displayItems((itemDisplayParameters, output) ->
                                ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).build();
    }
}