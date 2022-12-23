package com.github.sniffity.sniffityscreatures.worldgen;

import com.github.sniffity.sniffityscreatures.config.SMServerConfig;
import com.github.sniffity.sniffityscreatures.mixin.StructureTemplatePoolAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Sniffity's Creatures - Class: WolfsbaneWorldgen <br></br?>
 *
 *
 * Acknowledgements: The following class was developed after studying how Waystones, Immersive Engineering and Ice and Fire implement their own Village Worldgen
 *
 * Best source for adding to dynamic registry structure pools for best compatibility and less hacks:
 * https://gist.github.com/TelepathicGrunt/4fdbc445ebcbcbeb43ac748f4b18f342
 */

public class WolfShrineWorldgen {

    private static final ResourceKey<StructureProcessorList> STREET_PLAINS_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation("minecraft", "street_plains"));

    public static void setupVillageWorldGen(final ServerAboutToStartEvent event) {
        if (SMServerConfig.SERVER.ENTITIES.WEREWOLF.enableWerewolf.get()) {
            Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
            Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();

            addToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/plains/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_street_plains"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightPlains.get());
            addToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/taiga/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_street_taiga"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightTaiga.get());
            addToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/savanna/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_street_savanna"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightSavanna.get());
            addToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/desert/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_street_desert"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightDesert.get());
            addToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/snowy/streets"),new ResourceLocation("sniffityscreatures", "village/wolf_shrine_street_snowy"), SMServerConfig.SERVER.ENTITIES.WEREWOLF.wolfShrineSpawnWeightSnowy.get());

        }
    }

    private static void addToPool(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry, ResourceLocation poolRL, ResourceLocation toAdd, int weight) {
        // Grabs the processor list we want to use along with our piece.
        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(STREET_PLAINS_PROCESSOR_LIST_KEY);

        // Grab the pool we want to add to
        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;

        // Grabs the nbt piece and creates a SinglePoolElement of it that we can add to a structure's pool.
        // Use .legacy( for villages/outposts and .single( for everything else
        SinglePoolElement piece = SinglePoolElement.legacy(toAdd.toString(), emptyProcessorList).apply(StructureTemplatePool.Projection.TERRAIN_MATCHING);

        // Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's templates field public for us to see.
        // Weight is handled by how many times the entry appears in this list.
        // We do not need to worry about immutability as this field is created using Lists.newArrayList(); which makes a mutable list.
        for (int i = 0; i < weight; i++) {
            ((StructureTemplatePoolAccessor)pool).getTemplates().add(piece);
        }

        // Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's rawTemplates field public for us to see.
        // This list of pairs of pieces and weights is not used by vanilla by default but another mod may need it for efficiency.
        // So lets add to this list for completeness. We need to make a copy of the array as it can be an immutable list.
        //   NOTE: This is a com.mojang.datafixers.util.Pair. It is NOT a fastUtil pair class. Use the mojang class.
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(((StructureTemplatePoolAccessor)pool).getRawTemplates());
        listOfPieceEntries.add(new Pair<>(piece, weight));
        ((StructureTemplatePoolAccessor)pool).setRawTemplates(listOfPieceEntries);
    }
}