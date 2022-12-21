package com.github.sniffity.sniffitysmobs.client.model.entity;

import com.github.sniffity.sniffitysmobs.SniffitysMobs;
import com.github.sniffity.sniffitysmobs.entity.creature.EntityWerewolf;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelWerewolf extends AnimatedGeoModel<EntityWerewolf> {
    @Override
    public ResourceLocation getModelLocation(EntityWerewolf object) {
        return new ResourceLocation(SniffitysMobs.MODID,"geo/creature/werewolf/werewolf.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWerewolf object) {
        object.getTextureVariant();
        return new ResourceLocation(SniffitysMobs.MODID,"textures/creature/werewolf/werewolf"+object.getTextureVariant()+".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWerewolf animatable) {
        return new ResourceLocation(SniffitysMobs.MODID,"animations/creature/werewolf/werewolf.json");
    }
}