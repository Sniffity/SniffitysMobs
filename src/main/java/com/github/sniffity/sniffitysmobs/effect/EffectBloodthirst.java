package com.github.sniffity.sniffitysmobs.effect;

import com.github.sniffity.sniffitysmobs.registry.SMEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class EffectBloodthirst extends MobEffect {

    public EffectBloodthirst() {
        super(MobEffectCategory.BENEFICIAL, 1041313);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "7aade2e8-80b5-11ed-a1eb-0242ac120002",
                0.0F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return this == SMEffects.BLOODTHIRST.get();
    }

    //For each stack of Bloodthirst, increase attack damage by 0.75F
    @Override
    public double getAttributeModifierValue(int pAmplifier, @NotNull AttributeModifier pModifier) {
        return 0.75*(double)(pAmplifier + 1);
    }
}
