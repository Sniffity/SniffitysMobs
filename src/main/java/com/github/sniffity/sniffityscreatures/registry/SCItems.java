package com.github.sniffity.sniffityscreatures.registry;

import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import com.github.sniffity.sniffityscreatures.item.ItemSilverNeedle;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SniffitysCreatures.MODID);

    public static final RegistryObject<Item> SC_ICON = ITEMS.register("sc_icon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVER_NEEDLE = ITEMS.register("silver_needle",
            () -> new ItemSilverNeedle(new Item.Properties().tab(SCItemGroup.GROUP)));
    public static final RegistryObject<Item> WEREWOLF_SPAWN_EGG = ITEMS.register("werewolf_spawn_egg",
            () -> new ForgeSpawnEggItem(
                    SCEntityTypes.WEREWOLF,
                    0x878996,
                    0x38393b,
                    (new Item.Properties().tab(SCItemGroup.GROUP))));
}