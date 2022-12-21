package com.github.sniffity.sniffitysmobs.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SMItemGroup {

    public static final CreativeModeTab GROUP = new CreativeModeTab("group_sm") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SMItems.SM_ICON.get());
        }
    };
}