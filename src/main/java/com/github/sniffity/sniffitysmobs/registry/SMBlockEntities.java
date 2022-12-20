package com.github.sniffity.sniffitysmobs.registry;

import com.github.sniffity.sniffitysmobs.SniffitysMobs;
import com.github.sniffity.sniffitysmobs.block.BlockEntityWolfStatue;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SMBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, SniffitysMobs.MODID);
    public static final RegistryObject<BlockEntityType<BlockEntityWolfStatue>> WOLF_STATUE_BE = BLOCK_ENTITIES.register(
            "wolf_statue", () -> BlockEntityType.Builder.of(BlockEntityWolfStatue::new, SMBlocks.WOLF_STATUE.get()).build(null));
}