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
                .anyMatch(pieceName -> pieceName.contains("sniffitysmobs:") && pieceName.contains("/wolf_shrine_piece"));

        boolean hasWerewolfVillager = pieces.stream()
                .map(piece -> ((PoolElementStructurePiece) piece).getElement().toString())
                .anyMatch(pieceName -> pieceName.contains("sniffitysmobs:") && pieceName.contains("/werewolf_villager"));

        //If the Village already has a Wolf Shrine, remove the Wolf Shrine from the pool, ensuring a maximum of one Wolf Shrine per village
        if (hasWolfShrine) {
            List<StructurePoolElement> adjustedPool = pool.getShuffledTemplates(rand).stream().filter(piece -> {
                String pieceName = piece.toString();
                return !pieceName.contains("sniffitysmobs:") || !pieceName.contains("/wolf_shrine_piece");
            }).collect(Collectors.toList());
            //adjustedPool: Pool without Wolf Shrine

            //We have a Wolf Shrine, now proceed to check if we have a Werewolf Villager..

            //If we do not have a Werewolf Villager yet...
            if (!hasWerewolfVillager) {
                //Since we already have one Wolf Shrine, we proceed to ensure at least one Werewolf Villager gets added...
                //We do so by finding the Werewolf Villager piece and placing it at the front of the line...
                //Instantiate the werewolfVillager element...
                StructurePoolElement werewolfVillager = null;
                //Instantiate the resultingPool
                List<StructurePoolElement> resultPool = new ArrayList<>();
                //The original Pool will be the pool with the wolfShrine removed..
                for (StructurePoolElement piece : adjustedPool) {
                    String pieceName = piece.toString();
                    if (pieceName.contains("sniffitysmobs:") && pieceName.contains("/werewolf_villager")) {
                        werewolfVillager = piece;
                    } else {
                        resultPool.add(piece);
                    }
                }
                if (werewolfVillager != null) {
                    resultPool.add(0,werewolfVillager);
                }
                return resultPool;
            } else {
                //We already have a Werewolf Villager, adjust the initial pool, removing all Werewolf Villager
                adjustedPool = pool.getShuffledTemplates(rand).stream().filter(piece -> {
                    String pieceName = piece.toString();
                    return !pieceName.contains("sniffitysmobs:") || !pieceName.contains("/werewolf_villager");
                }).collect(Collectors.toList());
                return adjustedPool;
            }
        }  else {
            //We have no WOlf Shrine, remove all werewolf Villagers
            return pool.getShuffledTemplates(rand).stream().filter(piece -> {
                String pieceName = piece.toString();
                return !pieceName.contains("sniffitysmobs:") || !pieceName.contains("/werewolf_villager");
            }).collect(Collectors.toList());
        }
    }
}