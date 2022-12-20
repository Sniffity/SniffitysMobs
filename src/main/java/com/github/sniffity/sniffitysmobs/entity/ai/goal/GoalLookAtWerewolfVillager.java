package com.github.sniffity.sniffitysmobs.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Set;

public class GoalLookAtWerewolfVillager extends LookAtPlayerGoal {
    public GoalLookAtWerewolfVillager(Mob pMob, Class<? extends LivingEntity> pLookAtType, float pLookDistance) {
        super(pMob, pLookAtType, pLookDistance);
    }

    @Override
    public boolean canUse(){
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.lookAt = this.mob.getTarget();
            } else {
                List<Villager> list = this.mob.level.getEntitiesOfClass(Villager.class, this.mob.getBoundingBox().inflate(this.lookDistance));
                if (!list.isEmpty()) {
                    for(Mob mob : list) {
                        Set<String> tags = mob.getTags();
                        if (!tags.isEmpty()){
                            if (tags.contains("Werewolf")) {
                                lookAt = mob;
                            }
                        }
                    }
                }
            }
            return this.lookAt != null;
        }
    }
}
