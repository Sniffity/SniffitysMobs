package com.github.sniffity.sniffityscreatures.worldgen;

import com.github.sniffity.sniffityscreatures.config.SMServerConfig;
import com.github.sniffity.sniffityscreatures.mixin.MixinSingleJigsawAccess;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Sniffity's Creatures - Class: WolfsbaneWorldgen <br></br?>
 *
 *
 * Acknowledgements: The following class was developed after studying how Waystones, Immersive Engineering and Ice and Fire implement their own Village Worldgen
 */

public class WolfShrineWorldgen {

    public static void setupVillageWorldGen() {
        if (SMServerConfig.SERVER.ENTITIES.WEREWOLF.enableWerewolf.get()) {
            addToPool(new ResourceLocation("village/plains/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_plains"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightPlains.get());
            addToPool(new ResourceLocation("village/taiga/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_taiga"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightTaiga.get());
            addToPool(new ResourceLocation("village/savanna/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_savanna"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightSavanna.get());
            addToPool(new ResourceLocation("village/desert/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_desert"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightDesert.get());
            addToPool(new ResourceLocation("village/snowy/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_snowy"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightSnowy.get());

        }
    }

    private static void addToPool(ResourceLocation pool, ResourceLocation toAdd, int weight) {
        StructureTemplatePool old = BuiltinRegistries.TEMPLATE_POOL.get(pool);
        int id = BuiltinRegistries.TEMPLATE_POOL.getId(old);

        List<StructurePoolElement> shuffled;
        if (old != null) {
            shuffled = old.getShuffledTemplates(new Random());
        }
        else {
            shuffled = ImmutableList.of();
        }
        Object2IntMap<StructurePoolElement> newPieces = new Object2IntLinkedOpenHashMap<>();
        for(StructurePoolElement p : shuffled)
            newPieces.computeInt(p, (StructurePoolElement pTemp, Integer i) -> (i==null?0: i)+1);
        newPieces.put(MixinSingleJigsawAccess.construct(
                Either.left(toAdd), ProcessorLists.EMPTY, StructureTemplatePool.Projection.TERRAIN_MATCHING
        ), weight);
        List<Pair<StructurePoolElement, Integer>> newPieceList = newPieces.object2IntEntrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getIntValue()))
                .collect(Collectors.toList());

        ResourceLocation name = old.getName();
        ((WritableRegistry<StructureTemplatePool>)BuiltinRegistries.TEMPLATE_POOL).registerOrOverride(
                OptionalInt.of(id),
                ResourceKey.create(BuiltinRegistries.TEMPLATE_POOL.key(), name),
                new StructureTemplatePool(pool, name, newPieceList),
                Lifecycle.stable()
        );
    }
}