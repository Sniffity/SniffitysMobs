package com.github.sniffity.sniffitysmobs.events;

import com.github.sniffity.sniffitysmobs.entity.ai.goal.GoalAvoidWerewolfVillager;
import com.github.sniffity.sniffitysmobs.entity.ai.goal.GoalLookAtWerewolfVillager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


public class ServerEvents {
    public static void setup() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(ServerEvents::onJoinWorld);
    }
    // ====================
    //       Mod Bus
    // ====================

    // =====================
    //      Forge Bus
    // =====================

    public static void onJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        //Get Wolves to look at Werewolf Villagers
        if (entity instanceof Wolf) {
            //((Mob) entity).goalSelector.addGoal(5,new GoalFollowWerewolfVillager((Mob)entity,1.0F,5.0F,2.0F));
            ((Mob) entity).goalSelector.addGoal(5,new GoalLookAtWerewolfVillager((Mob)entity,Villager.class,4.0F));

        }
        //Get Cats to avoid Werewolf Villagers
        if (entity instanceof Cat){
            ((Mob) entity).goalSelector.addGoal(5,new GoalAvoidWerewolfVillager((PathfinderMob) entity,Villager.class,3.0F,1.0F,1.0F));
        }
    }
}
