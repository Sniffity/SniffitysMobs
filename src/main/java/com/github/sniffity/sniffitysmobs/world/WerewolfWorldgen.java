package com.github.sniffity.sniffitysmobs.world;

import com.github.sniffity.sniffitysmobs.config.SMServerConfig;
import com.github.sniffity.sniffitysmobs.mixin.MixinSingleJigsawAccess;
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
import java.util.stream.Collectors;
import java.util.Random;

/**
 * Sniffity's Mobs Mod - Class: WolfsbaneWorldgen <br></br?>
 *
 *
 * Acknowledgements: The following class was developed after studying how Waystone, Immersive Engineering and Ice and Fire implement their own Village Worldgen
 */

public class WerewolfWorldgen {
    private static final ResourceLocation wolfShrineStructure = new ResourceLocation("sniffitysmobs", "village/wolf_shrine");
    private static final ResourceLocation werewolfVillager = new ResourceLocation("sniffitysmobs", "village/werwolf_villager_");
    private static final String[] VILLAGE_TYPES = new String[]{"plains", "savanna", "taiga","snowy","desert"};

    public static void setupVillageWorldGen() {
        if (SMServerConfig.SERVER.MOBS.WEREWOLF.enableWerewolf.get()) {
            for (String type : VILLAGE_TYPES) {
                addToPool(new ResourceLocation("village/" + type + "/houses"),wolfShrineStructure, SMServerConfig.SERVER.MOBS.WEREWOLF.werewolfSpawnWeight.get());
                addToPool(new ResourceLocation("village/" + type + "/villagers"),new ResourceLocation(werewolfVillager+type), 1);
            }
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
                Either.left(toAdd), ProcessorLists.EMPTY, StructureTemplatePool.Projection.RIGID
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
