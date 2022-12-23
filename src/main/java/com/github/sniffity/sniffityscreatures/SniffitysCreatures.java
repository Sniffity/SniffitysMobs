package com.github.sniffity.sniffityscreatures;

import com.github.sniffity.sniffityscreatures.config.SMServerConfig;
import com.github.sniffity.sniffityscreatures.events.ClientEvents;
import com.github.sniffity.sniffityscreatures.events.ServerEvents;
import com.github.sniffity.sniffityscreatures.registry.*;
import com.github.sniffity.sniffityscreatures.worldgen.WolfShrineWorldgen;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(SniffitysCreatures.MODID)
public class SniffitysCreatures
{
    //ToDo: Logo + packmcmeta


    //ToDo: Wolf Shrine Structures
        //ToDo: Advancement on Wolf Statue

    //ToDo: silver arrows


    //ToDo: transform conditions --
    //Hit with silver needle
    //Hit with silver arrow
    //Health < 25% or dying
    //Bloodthirst > 10 stacks && moon
        //Pass in biomes, set texture variants

    //ToDo: Cancel iron golem aggro if hit with silver needle


    //ToDo: Transform - hide with particle effect
            //ToDo: transform if bloodthirst stacks = null, pass in -1

    //ToDo: If Werewolf is transformed, handle stacks on Werewolf Entity....
        //ToDo: handle transform sound on FinalizeSpawn
        //ToDo: AttackGoal + whole werewolf logic


    //ToDo: test config options

    //ToDo: Debug statements - remove

    //ToDo: Gargoyles?


    public static final String MODID = "sniffityscreatures";
    private static final Logger LOGGER = LogUtils.getLogger();
    public SniffitysCreatures()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ClientEvents.setup();
        ServerEvents.setup();

        modBus.addListener(this::commonSetup);

        SCItems.ITEMS.register(modBus);
        SCBlockEntities.BLOCK_ENTITIES.register(modBus);
        SCBlocks.BLOCKS.register(modBus);
        SCEvents.EFFECTS.register(modBus);
        SCSoundEvents.SOUND_EVENTS.register(modBus);
        SCEntityTypes.ENTITY_TYPES.register(modBus);

        GeckoLib.initialize();
        forgeBus.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SMServerConfig.SERVER_CONFIG, "sniffityscreatures-server-config.toml");

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WolfShrineWorldgen.setupVillageWorldGen();
        });
    }


}
