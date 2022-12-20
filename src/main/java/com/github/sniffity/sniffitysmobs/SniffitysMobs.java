package com.github.sniffity.sniffitysmobs;

import com.github.sniffity.sniffitysmobs.config.SMServerConfig;
import com.github.sniffity.sniffitysmobs.events.ClientEvents;
import com.github.sniffity.sniffitysmobs.events.ServerEvents;
import com.github.sniffity.sniffitysmobs.registry.SMBlockEntities;
import com.github.sniffity.sniffitysmobs.registry.SMBlocks;
import com.github.sniffity.sniffitysmobs.registry.SMEffects;
import com.github.sniffity.sniffitysmobs.registry.SMItems;
import com.github.sniffity.sniffitysmobs.worldgen.WolfShrineWorldgen;
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

@Mod(SniffitysMobs.MODID)
public class SniffitysMobs
{
    //ToDo: Logo + packmcmeta
    //ToDo: Wolf Shrine Structures
        //ToDo: set NBT to true in structure file
        //ToDo: Loot? Books, etc?


    //ToDo: Bloodthirst effect, add to Villagers
    //ToDo: Silver poker item
        //ToDo: Tooltip
    //ToDo: Event to add bloodthirst counter
        //ToDo: Villager damage +1, Villager Poke +2, Villager death +4
        //ToDo
    //ToDo: Message on bloodthirst reaching a threshold + sound

    //ToDo: transform conditions
        //Bloodthirst > 10 stacks && moon
        //Health < 25%
        //Hit with silver arrow
        //Hit with silver poker

    //ToDo: Boss bar, displays Bloodthirst stacks
    public static final String MODID = "sniffitysmobs";
    private static final Logger LOGGER = LogUtils.getLogger();
    public SniffitysMobs()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ClientEvents.setup();
        ServerEvents.setup();

        modBus.addListener(this::commonSetup);

        SMItems.ITEMS.register(modBus);
        SMBlockEntities.BLOCK_ENTITIES.register(modBus);
        SMBlocks.BLOCKS.register(modBus);
        SMEffects.EFFECTS.register(modBus);

        GeckoLib.initialize();
        forgeBus.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SMServerConfig.SERVER_CONFIG, "sniffitysmobs-server-config.toml");

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WolfShrineWorldgen.setupVillageWorldGen();
        });
    }


}
