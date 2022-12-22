package com.github.sniffity.sniffityscreatures.registry;

import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import com.github.sniffity.sniffityscreatures.block.BlockEntityWolfStatue;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SniffitysCreatures.MODID);
    public static final RegistryObject<BlockEntityType<BlockEntityWolfStatue>> WOLF_STATUE_BE = BLOCK_ENTITIES.register(
            "wolf_statue", () -> BlockEntityType.Builder.of(BlockEntityWolfStatue::new, SCBlocks.WOLF_STATUE.get()).build(null));
}