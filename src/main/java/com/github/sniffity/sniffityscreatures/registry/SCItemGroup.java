package com.github.sniffity.sniffityscreatures.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SCItemGroup {

    public static final CreativeModeTab GROUP = new CreativeModeTab("group_sniffitys_creatures") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SCItems.SC_ICON.get());
        }
    };
}