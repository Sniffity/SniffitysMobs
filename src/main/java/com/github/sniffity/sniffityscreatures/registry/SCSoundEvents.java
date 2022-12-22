package com.github.sniffity.sniffityscreatures.registry;

import com.github.sniffity.sniffityscreatures.SniffitysCreatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SniffitysCreatures.MODID);

    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_VILLAGER1 = SOUND_EVENTS.register("werewolf.villager1",
            () -> new SoundEvent(new ResourceLocation(SniffitysCreatures.MODID,"werewolf.villager1")));
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_VILLAGER2 = SOUND_EVENTS.register("werewolf.villager2",
            () -> new SoundEvent(new ResourceLocation(SniffitysCreatures.MODID,"werewolf.villager2")));
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_VILLAGER3 = SOUND_EVENTS.register("werewolf.villager3",
            () -> new SoundEvent(new ResourceLocation(SniffitysCreatures.MODID,"werewolf.villager3")));

}
