package com.github.sniffity.sniffitysmobs.registry;

import com.github.sniffity.sniffitysmobs.SniffitysMobs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SMItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SniffitysMobs.MODID);

    public static final RegistryObject<Item> SILVER_NEEDLE = ITEMS.register("silver_needle",
            () -> new Item(new Item.Properties().tab(SMItemGroup.GROUP)));
}