package com.github.sniffity.sniffitysmobs.events;


import com.github.sniffity.sniffitysmobs.registry.SMBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientEvents {

    public static void setup() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        modBus.addListener(ClientEvents::clientSetup);
    }

    // ====================
    //       Mod Bus
    // ====================
    private static void clientSetup(final FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(SMBlocks.WOLFSBANE.get(),RenderType.cutout());
    }

    // =====================
    //      Forge Bus
    // =====================
}
