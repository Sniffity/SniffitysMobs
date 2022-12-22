package com.github.sniffity.sniffityscreatures.entity.creature.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;

public class GoalAvoidWerewolfVillager extends AvoidEntityGoal {
    public GoalAvoidWerewolfVillager(PathfinderMob pMob, Class pEntityClassToAvoid, float pMaxDistance, double pWalkSpeedModifier, double pSprintSpeedModifier) {
        super(pMob, pEntityClassToAvoid, pMaxDistance, pWalkSpeedModifier, pSprintSpeedModifier);
    }

    @Override
    public boolean canUse() {
        List<Villager> list = this.mob.level.getEntitiesOfClass(Villager.class, this.mob.getBoundingBox().inflate(this.maxDist));
        if (!list.isEmpty()) {
            for(Mob mob : list) {
                Set<String> tags = mob.getTags();
                if (!tags.isEmpty()){
                    if (tags.contains("Werewolf")) {
                        toAvoid = mob;
                    }
                }
            }
        }

        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            if (vec3 == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
    }
}
