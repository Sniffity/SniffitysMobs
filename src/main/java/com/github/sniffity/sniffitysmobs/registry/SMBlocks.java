package com.github.sniffity.sniffitysmobs.registry;


import com.github.sniffity.sniffitysmobs.SniffitysMobs;
import com.github.sniffity.sniffitysmobs.block.BlockWolfStatue;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SMBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SniffitysMobs.MODID);


    public static final RegistryObject<Block> WOLF_STATUE = register("wolf_statue",
            BlockWolfStatue::new, new Item.Properties().tab(SMItemGroup.GROUP));


    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        SMItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
