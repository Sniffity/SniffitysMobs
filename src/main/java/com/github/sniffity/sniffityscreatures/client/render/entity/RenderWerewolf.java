package com.github.sniffity.sniffityscreatures.client.render.entity;

import com.github.sniffity.sniffityscreatures.client.model.entity.ModelWerewolf;
import com.github.sniffity.sniffityscreatures.entity.creature.EntityWerewolf;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderWerewolf extends GeoEntityRenderer<EntityWerewolf> {

    public RenderWerewolf(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelWerewolf());
        this.shadowRadius = 1.0F;
    }

    @Override
    public void renderEarly(EntityWerewolf animatable, PoseStack stackIn, float ticks,
                            MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn,
                            float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
                red, green, blue, partialTicks);

        stackIn.scale(1.0F,1.0F,1.0F);
    }
}