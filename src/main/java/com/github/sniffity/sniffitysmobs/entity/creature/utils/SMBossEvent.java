package com.github.sniffity.sniffitysmobs.entity.creature.utils;

import com.github.sniffity.sniffitysmobs.entity.creature.EntityWerewolf;
import com.github.sniffity.sniffitysmobs.entity.creature.SMEntity;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SMBossEvent extends ServerBossEvent {
    private final SMEntity entity;

    private final Set<ServerPlayer> unseen = new HashSet<>();

    public SMBossEvent(SMEntity entity) {
        super(entity.getDisplayName(), entity.bossBarColor(), BossBarOverlay.PROGRESS);
        this.setVisible(entity.hasBossBar());
        this.entity = entity;
    }

    public void update() {
        this.setProgress(this.entity.getHealth() / this.entity.getMaxHealth());
        Iterator<ServerPlayer> iterator = this.unseen.iterator();
        while (iterator.hasNext()) {
            ServerPlayer player = iterator.next();
            if (this.entity.getSensing().hasLineOfSight(player)) {
                super.addPlayer(player);
                iterator.remove();
            }
        }
    }

    @Override
    public void addPlayer(ServerPlayer player) {
        if (this.entity.getSensing().hasLineOfSight(player)) {
            super.addPlayer(player);
        } else {
            this.unseen.add(player);
        }
    }

    @Override
    public void removePlayer(ServerPlayer player) {
        super.removePlayer(player);
        this.unseen.remove(player);
    }
}

