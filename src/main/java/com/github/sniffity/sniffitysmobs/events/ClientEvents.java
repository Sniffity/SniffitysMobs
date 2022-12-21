package com.github.sniffity.sniffitysmobs.events;


import com.github.sniffity.sniffitysmobs.client.render.entity.RenderWerewolf;
import com.github.sniffity.sniffitysmobs.entity.creature.SMEntity;
import com.github.sniffity.sniffitysmobs.registry.SMBlocks;
import com.github.sniffity.sniffitysmobs.registry.SMEntityTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientEvents {

    public static void setup() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        modBus.addListener(ClientEvents::clientSetup);
        modBus.addListener(ClientEvents::registerEntityRenderers);
    }

    // ====================
    //       Mod Bus
    // ====================
    private static void clientSetup(final FMLClientSetupEvent event)
    {
        ItemBlockRenderTypes.setRenderLayer(SMBlocks.WOLFSBANE.get(),RenderType.cutout());
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(SMEntityTypes.WEREWOLF.get(), RenderWerewolf::new);
    }

    // =====================
    //      Forge Bus
    // =====================
}
