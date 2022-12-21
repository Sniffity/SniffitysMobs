package com.github.sniffity.sniffitysmobs.entity.creature;

import com.github.sniffity.sniffitysmobs.config.SMServerConfig;
import com.github.sniffity.sniffitysmobs.registry.SMEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public class EntityWerewolf extends SMEntity {
    protected static final EntityDataAccessor<Integer> BLOODTHIRST_STACKS = SynchedEntityData.defineId(EntityWerewolf.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> TEXTURE_VARIANT = SynchedEntityData.defineId(EntityWerewolf.class, EntityDataSerializers.INT);


    public EntityWerewolf(EntityType<? extends SMEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntityWerewolf(EntityType<? extends SMEntity> type, Level worldIn, int bloodthirstStacks, int textureVariant) {
        super(type, worldIn);
        setBloodthirstStacks(bloodthirstStacks);
        setTextureVariant(textureVariant);
    }

    // ====================================
    //      A) Entity Data
    // ====================================


    @Override
    protected void defineSynchedData() {
        this.entityData.define(BLOODTHIRST_STACKS, 0);
        this.entityData.define(TEXTURE_VARIANT, 0);
        super.defineSynchedData();
    }


    public int getBloodthirstStacks() {
        return entityData.get(BLOODTHIRST_STACKS);
    }

    public void setBloodthirstStacks(int stacks) {
        entityData.set(BLOODTHIRST_STACKS, stacks);
    }


    public int getTextureVariant() {
        return entityData.get(TEXTURE_VARIANT);
    }

    public void setTextureVariant(int stacks) {
        entityData.set(TEXTURE_VARIANT, stacks);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData data, @javax.annotation.Nullable CompoundTag dataTag)
    {
        if (getBloodthirstStacks() > 0) {
            this.addEffect(new MobEffectInstance(SMEffects.BLOODTHIRST.get(),2147483647, getBloodthirstStacks(),false,false));
        }
        return super.finalizeSpawn(level, difficulty, reason, data, dataTag);
    }

    public static AttributeSupplier.Builder werewolfAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, SMServerConfig.SERVER.ENTITIES.WEREWOLF.attributes.entityAttack.get())
                .add(Attributes.ATTACK_KNOCKBACK, 2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 4)
                .add(Attributes.FOLLOW_RANGE, 50)
                .add(Attributes.MAX_HEALTH, SMServerConfig.SERVER.ENTITIES.WEREWOLF.attributes.entityHealth.get())
                .add(Attributes.MOVEMENT_SPEED, 1.5F);
    }
}



