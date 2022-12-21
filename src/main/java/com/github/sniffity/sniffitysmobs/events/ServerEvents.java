package com.github.sniffity.sniffitysmobs.events;

import com.github.sniffity.sniffitysmobs.entity.ai.goal.GoalAvoidWerewolfVillager;
import com.github.sniffity.sniffitysmobs.entity.ai.goal.GoalLookAtWerewolfVillager;
import com.github.sniffity.sniffitysmobs.registry.SMEffects;
import com.github.sniffity.sniffitysmobs.registry.SMItems;
import com.github.sniffity.sniffitysmobs.registry.SMSoundEvents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;


public class ServerEvents {
    public static void setup() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(ServerEvents::onJoinWorld);
        forgeBus.addListener(ServerEvents::onLivingHurt);

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
            ((Mob) entity).goalSelector.addGoal(5, new GoalLookAtWerewolfVillager((Mob) entity, Villager.class, 4.0F));

        }
        //Get Cats to avoid Werewolf Villagers
        if (entity instanceof Cat) {
            ((Mob) entity).goalSelector.addGoal(5, new GoalAvoidWerewolfVillager((PathfinderMob) entity, Villager.class, 3.0F, 1.0F, 1.0F));
        }
    }

    public static void onLivingHurt(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        Entity source = event.getSource().getEntity();
        //Whenever a non Werewolf Villager is hurt by a player, we will add a Bloodthirst Effect to it.
        //This Bloodthirst Effect effectively counts how many stacks of Bloodthirst the Werewolf will have when it does spawn
        if (entity instanceof Villager && source instanceof Player) {
            if (!entity.getTags().contains("Werewolf")) {
                //Get all nearby Villagers and check if we have a Werewolf Villager...
                List<Villager> villagerList = entity.level.getEntitiesOfClass(Villager.class, entity.getBoundingBox().inflate(30));
                if (!villagerList.isEmpty()) {
                    for (Villager villager : villagerList) {
                        if (villager.getTags().contains("Werewolf")) {
                            //If we do find a Werewolf Villager...
                            int bloodthirstStacksOriginal;
                            int bloodthirstStacksNew;
                            MobEffectInstance bloodthirst = villager.getEffect(SMEffects.BLOODTHIRST.get());
                            ///Get its Bloodthirst stacks...
                            if (bloodthirst != null) {
                                bloodthirstStacksOriginal = bloodthirst.getAmplifier();
                            } else {
                                bloodthirstStacksOriginal = 0;
                            }

                            //Define the new bloodthirstStacks:
                            //If a Villager was killed by a Silver Needle, add 4..
                            //Else if a Villager was damaged by a Silver Needle, add 2..
                            // Else, regular damage, add 1...
                            if (((Villager) entity).getHealth() < event.getAmount()) {
                                bloodthirstStacksNew = bloodthirstStacksOriginal + 4;
                            } else if (((Player) source).getMainHandItem().is(SMItems.SILVER_NEEDLE.get())) {
                                bloodthirstStacksNew = bloodthirstStacksOriginal + 2;
                            } else {
                                bloodthirstStacksNew = bloodthirstStacksOriginal + 1;
                            }
                            //Remove the previous Bloodthirst and add the new bloodthirst effect...
                            villager.removeAllEffects();
                            villager.addEffect(new MobEffectInstance(SMEffects.BLOODTHIRST.get(), 2147483647, bloodthirstStacksNew, false, false));
                            //Send a message to player if Bloodthirst just exceeded a certain threshold...
                            String sendMessage = null;
                            Entity updatedSource = event.getSource().getEntity();
                            if (bloodthirstStacksOriginal < 3 && bloodthirstStacksNew >= 3) {
                                sendMessage = "sniffitysmobs.bloodthirst.message.3";
                                updatedSource.level.playSound(null, updatedSource.position().x, updatedSource.position().y, updatedSource.position().z, SMSoundEvents.ENTITY_WEREWOLF_VILLAGER1.get(), SoundSource.HOSTILE, 2, 1);
                            }
                            if (bloodthirstStacksOriginal < 7 && bloodthirstStacksNew >= 7) {
                                sendMessage = "sniffitysmobs.bloodthirst.message.7";
                                updatedSource.level.playSound(null, updatedSource.position().x, updatedSource.position().y, updatedSource.position().z, SMSoundEvents.ENTITY_WEREWOLF_VILLAGER2.get(), SoundSource.HOSTILE, 2, 1);
                            }
                            //Only send this warning message if it's not night yet, else directly transform and send corresponding message...
                            if (bloodthirstStacksOriginal < 10 && bloodthirstStacksNew >= 10) {
                                if (!(villager.level.getDayTime() > 13000 && villager.level.getDayTime() < 23500)) {
                                    sendMessage = "sniffitysmobs.bloodthirst.message.10";
                                    updatedSource.level.playSound(null, updatedSource.position().x, updatedSource.position().y, updatedSource.position().z, SMSoundEvents.ENTITY_WEREWOLF_VILLAGER3.get(), SoundSource.HOSTILE, 2, 1);
                                } else {
                                    //ToDo: Handle Werewolf transform sound elsewhere
                                    sendMessage = "sniffitysmobs.werewolf.transform";
                                }
                            }
                            if (sendMessage != null) {
                                List<Player> playerList = entity.level.getEntitiesOfClass(Player.class, entity.getBoundingBox().inflate(20));
                                if (!playerList.isEmpty()) {
                                    for (Player player : playerList) {
                                        player.sendMessage(new TranslatableComponent(sendMessage), entity.getUUID());
                                    }
                                }
                            }
                            System.out.println("Set bloodthirst stacks to: " + bloodthirstStacksNew +
                                    " for Werewolf Villager at:"
                                    + " X: " + villager.position().x
                                    + " Y: " + villager.position().y
                                    + " Z: " + villager.position().z);
                            break;
                        }
                    }
                }

            }
        }
    }
}
