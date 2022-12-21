package com.github.sniffity.sniffitysmobs.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSilverNeedle extends Item {
    public ItemSilverNeedle(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent(getDescriptionId() + ".text.1").setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
        tooltip.add(new TranslatableComponent(getDescriptionId() + ".text.2").setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));

    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

}