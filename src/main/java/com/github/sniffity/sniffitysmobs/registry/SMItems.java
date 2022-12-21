package com.github.sniffity.sniffitysmobs.registry;

import com.github.sniffity.sniffitysmobs.SniffitysMobs;
import com.github.sniffity.sniffitysmobs.item.ItemSilverNeedle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SniffitysMobs.MODID);

    public static final RegistryObject<Item> SM_ICON = ITEMS.register("sm_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_NEEDLE = ITEMS.register("silver_needle",
            () -> new ItemSilverNeedle(new Item.Properties().tab(SMItemGroup.GROUP)));
}