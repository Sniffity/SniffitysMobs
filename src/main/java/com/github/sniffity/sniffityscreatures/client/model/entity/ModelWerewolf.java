package com.github.sniffity.sniffityscreatures.client.model.entity;

import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import com.github.sniffity.sniffityscreatures.entity.creature.EntityWerewolf;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelWerewolf extends AnimatedGeoModel<EntityWerewolf> {
    @Override
    public ResourceLocation getModelLocation(EntityWerewolf object) {
        return new ResourceLocation(SniffitysCreatures.MODID,"geo/creature/werewolf/werewolf.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityWerewolf object) {
        object.getTextureVariant();
        return new ResourceLocation(SniffitysCreatures.MODID,"textures/creature/werewolf/werewolf"+object.getTextureVariant()+".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityWerewolf animatable) {
        return new ResourceLocation(SniffitysCreatures.MODID,"animations/creature/werewolf/werewolf.json");
    }
}