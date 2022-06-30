//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.features.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.function.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;

public class EnemyManager extends Feature
{
    public List<Enemy> enemies;
    
    public EnemyManager() {
        super("Enemies");
        this.enemies = new ArrayList<Enemy>();
    }
    
    public boolean isEnemy(final String name) {
        this.cleanEnemies();
        return this.enemies.stream().anyMatch(enemy -> enemy.username.equalsIgnoreCase(name));
    }
    
    public boolean isEnemy(final EntityPlayer player) {
        return this.isEnemy(player.getName());
    }
    
    public void addEnemy(final String name) {
        final Enemy enemy = this.getEnemyByName(name);
        if (enemy != null) {
            this.enemies.add(enemy);
        }
        this.cleanEnemies();
    }
    
    public void removeEnemy(final String name) {
        this.cleanEnemies();
        for (final Enemy enemy : this.enemies) {
            if (!enemy.getUsername().equalsIgnoreCase(name)) {
                continue;
            }
            this.enemies.remove(enemy);
            break;
        }
    }
    
    public void onLoad() {
        this.enemies = new ArrayList<Enemy>();
        this.clearSettings();
    }
    
    public void saveEnemies() {
        this.clearSettings();
        this.cleanEnemies();
        for (final Enemy enemy : this.enemies) {
            this.register(new Setting(enemy.getUuid().toString(), (Object)enemy.getUsername()));
        }
    }
    
    public void cleanEnemies() {
        this.enemies.stream().filter(Objects::nonNull).filter(enemy -> enemy.getUsername() != null);
    }
    
    public List<Enemy> getEnemies() {
        this.cleanEnemies();
        return this.enemies;
    }
    
    public Enemy getEnemyByName(final String input) {
        final UUID uuid = PlayerUtil.getUUIDFromName(input);
        if (uuid != null) {
            final Enemy enemy = new Enemy(input, uuid);
            return enemy;
        }
        return null;
    }
    
    public void addEnemy(final Enemy enemy) {
        this.enemies.add(enemy);
    }
    
    public static class Enemy
    {
        private final String username;
        private final UUID uuid;
        
        public Enemy(final String username, final UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }
        
        public String getUsername() {
            return this.username;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
    }
}
