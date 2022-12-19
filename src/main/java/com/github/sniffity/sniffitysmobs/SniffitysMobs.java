package com.github.sniffity.sniffitysmobs;

import com.github.sniffity.sniffitysmobs.config.SMServerConfig;
import com.github.sniffity.sniffitysmobs.registry.SMBlocks;
import com.github.sniffity.sniffitysmobs.registry.SMItems;
import com.github.sniffity.sniffitysmobs.world.WerewolfWorldgen;
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
    public static final String MODID = "sniffitysmobs";
    private static final Logger LOGGER = LogUtils.getLogger();
    public SniffitysMobs()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modBus.addListener(this::commonSetup);

        SMItems.ITEMS.register(modBus);
        SMBlocks.BLOCKS.register(modBus);

        GeckoLib.initialize();
        forgeBus.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SMServerConfig.SERVER_CONFIG, "sniffitysmobs-server-config.toml");

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WerewolfWorldgen.setupVillageWorldGen();
        });
    }


}
