package com.github.sniffity.sniffityscreatures.entity.creature.utils;

import com.github.sniffity.sniffityscreatures.entity.creature.SCEntity;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SMBossEvent extends ServerBossEvent {
    private final SCEntity entity;

    private final Set<ServerPlayer> unseen = new HashSet<>();

    public SMBossEvent(SCEntity entity) {
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

