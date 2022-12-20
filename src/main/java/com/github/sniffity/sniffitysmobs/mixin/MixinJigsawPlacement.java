package com.github.sniffity.sniffitysmobs.mixin;

import com.github.sniffity.sniffitysmobs.config.SMServerConfig;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@Mixin(targets = "net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$Placer")
public class MixinJigsawPlacement {

    @Final
    @Shadow
    private List<? super PoolElementStructurePiece> pieces;

    @Redirect(method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool;getShuffledTemplates(Ljava/util/Random;)Ljava/util/List;"))
    private List<StructurePoolElement> getShuffledTemplates(StructureTemplatePool pool, Random rand) {
        boolean hasWolfShrine = pieces.stream()
                .map(piece -> ((PoolElementStructurePiece) piece).getElement().toString())
                .anyMatch(pieceName -> pieceName.contains("sniffitysmobs:") && pieceName.contains("/wolf_shrine"));
        if (hasWolfShrine && SMServerConfig.SERVER.ENTITIES.WEREWOLF.forceMaxWolfShrine.get()) {
            return pool.getShuffledTemplates(rand).stream().filter(piece -> {
                String pieceName = piece.toString();
                return !pieceName.contains("sniffitysmobs:") || !pieceName.contains("/wolf_shrine");
            }).collect(Collectors.toList());
        } else if (SMServerConfig.SERVER.ENTITIES.WEREWOLF.forceMinWolfShrine.get()) {
            StructurePoolElement wolfsbane = null;
            List<StructurePoolElement> original = pool.getShuffledTemplates(rand);
            List<StructurePoolElement> result = new ArrayList<>();
            for (StructurePoolElement piece : original) {
                String pieceName = piece.toString();
                if (pieceName.contains("sniffitysmobs:") && pieceName.contains("/wolf_shrine")) {
                    wolfsbane = piece;
                } else {
                    result.add(piece);
                }
            }
            if (wolfsbane != null) {
                result.add(0, wolfsbane);
            }
            return result;
        }
        return pool.getShuffledTemplates(rand);
    }
}