package com.github.sniffity.sniffityscreatures.entity.creature;

import com.github.sniffity.sniffityscreatures.entity.creature.utils.SMBossEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

public abstract class SCEntity extends PathfinderMob implements IAnimatable {

    protected static int IDLE_ANIMATION_VARIANTS;
    private final SMBossEvent bossEvent = new SMBossEvent(this);

    public SCEntity(EntityType<? extends SCEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // =====================
    //      Animation Logic
    // =====================


    private static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(SCEntity.class, EntityDataSerializers.STRING);
    /**
     * ANIMATION_TYPE:
     * Case 1: LOOP
     * Case 2: PLAY_ONCE
     * Case 3: HOLD_ON_LAST_FRAME
     */
    private static final EntityDataAccessor<Integer> ANIMATION_TYPE = SynchedEntityData.defineId(SCEntity.class, EntityDataSerializers.INT);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controllerBasicLocomotion", 0, this::predicateBasicLocomotion));
    }

    public <E extends IAnimatable> PlayState predicateBasicLocomotion(AnimationEvent<E> event) {
        //BoneType: regular Bones
        //Basic Locomotion: Death
        if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("death", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        //Basic Locomotion: Movement
        //These moving animations play whenever the entity is moving
        //By using regular bones for these abilities as opposed to invisible ones, we get both animations to overlay...
        //We select between slow or fast movement based on whether the entity is aggressive or not
        if (event.isMoving() && !this.isAggressive()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (event.isMoving() && this.isAggressive()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }


        //Basic Locomotion: Default cases
        //If the entity is on ground and it is not doing anything else that warrants an animation, it will just stand ("naturally") in place
        if (this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("base_ground", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        //Default case
        //If nothing else was triggered, reset the entity to its base animation
        event.getController().  setAnimation(new AnimationBuilder().  addAnimation("base", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    public <E extends IAnimatable> PlayState predicateAbility(AnimationEvent<E> event)
    {
        //BoneType: invisible Bones
        //All Ability Animations to be played are stored as DataParameters Strings
        //We begin by getting the animation that should be played.
        //This string may have been set as part of an AnimatedGoal that requires a one-shot ability animation to play..
        String animation = this.getAnimation();
        //If we do have an one-shot ability animation, we will play that.
        //"base" is the null value for getAnimation
        if (!animation.equals("base")) {
            //If we do have an ability animation, we get the type (Loop, Play once, Hold on last frame)
            int animationType = this.getAnimationType();
            ILoopType loopType;
            switch (animationType) {
                case 1 -> loopType = ILoopType.EDefaultLoopTypes.LOOP;
                case 2 -> loopType = ILoopType.EDefaultLoopTypes.PLAY_ONCE;
                case 3 -> loopType = ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME;
                default -> {return PlayState.STOP;}
            }
            //We proceed to play the corresponding animation...
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, loopType));
            return PlayState.CONTINUE;
        }
        //If we do not have an ability animation, we will proceed to try and perform Idle:
        //Idle:
        //If the entity is onGround and not doing anything else, have a chance for it to perform an idle animation
        if (this.getRandom().nextDouble() < 0.001 && this.isOnGround() && !this.isAggressive()) {
            int idleVariant = this.random.nextInt(IDLE_ANIMATION_VARIANTS)+1;
            event.getController().setAnimation(new AnimationBuilder().  addAnimation("idle"+idleVariant, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        //Else, just return base:
        //This will not cause a transition to a stiff pose, as walking animations will be running concurrently...
        //We are only resetting the position of the iBones/Bones here
        event.getController().  setAnimation(new AnimationBuilder().  addAnimation("base", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;

    }

    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(ANIMATION, "base");
        this.entityData.define(ANIMATION_TYPE, 1);
        super.defineSynchedData();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }


    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }

    }

    public String getAnimation()
    {
        return entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        entityData.set(ANIMATION, animation);
    }

    public int getAnimationType()
    {
        return entityData.get(ANIMATION_TYPE);
    }

    public void setAnimationType(int animation)
    {
        entityData.set(ANIMATION_TYPE, animation);
    }

    public boolean hasBossBar() {
        return false;
    }

    public BossEvent.BossBarColor bossBarColor() {
        return BossEvent.BossBarColor.PURPLE;
    }

    @Override
    public void tick(){
        if (tickCount % 5 == 0) {
            bossEvent.update();
        }

        super.tick();
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (!this.isRemoved()) {
            bossEvent.update();
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.bossEvent.setName(this.getDisplayName());
    }
}


