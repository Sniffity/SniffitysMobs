package com.github.sniffity.sniffityscreatures.registry;


import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import com.github.sniffity.sniffityscreatures.block.BlockWolfStatue;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SniffitysCreatures.MODID);


    public static final RegistryObject<Block> WOLF_STATUE = register("wolf_statue",
            BlockWolfStatue::new, new Item.Properties().tab(SCItemGroup.GROUP));

    public static final RegistryObject<Block> WOLFSBANE = register("wolfsbane",
            () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS)),new Item.Properties().tab(SCItemGroup.GROUP));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        SCItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
