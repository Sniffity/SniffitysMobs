package com.github.sniffity.sniffityscreatures.registry;

import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import com.github.sniffity.sniffityscreatures.effect.EffectBloodthirst;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCEvents {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SniffitysCreatures.MODID);

    public static final RegistryObject<MobEffect> BLOODTHIRST = EFFECTS.register("bloodthirst", EffectBloodthirst::new);
}
