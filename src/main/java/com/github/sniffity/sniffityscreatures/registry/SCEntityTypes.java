package com.github.sniffity.sniffityscreatures.registry;


import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import com.github.sniffity.sniffityscreatures.entity.creature.EntityWerewolf;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, SniffitysCreatures.MODID);

    public static final RegistryObject<EntityType<EntityWerewolf>> WEREWOLF = ENTITY_TYPES.register("werewolf", () ->
            EntityType.Builder.<EntityWerewolf>of(EntityWerewolf::new, MobCategory.CREATURE)
                    .sized(1.0F, 2.0F)
                    .canSpawnFarFromPlayer()
                    .build(new ResourceLocation(SniffitysCreatures.MODID, "werewolf").toString()));
}

