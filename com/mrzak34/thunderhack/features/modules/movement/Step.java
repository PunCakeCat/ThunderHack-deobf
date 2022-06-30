//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

public class Step extends Module
{
    public Setting<Boolean> normal;
    public Setting<Boolean> reverse;
    
    public Step() {
        super("Step", "\u0445\u043e\u0434\u0438\u0442\u044c \u043f\u043e \u0431\u043b\u043e\u043a\u0430\u043c-\u043a\u0430\u043a \u043f\u043e \u043b\u0435\u0441\u0442\u043d\u0438\u0446\u0435", Module.Category.MOVEMENT, true, false, false);
        this.normal = (Setting<Boolean>)this.register(new Setting("Normal", (T)false));
        this.reverse = (Setting<Boolean>)this.register(new Setting("Reverse", (T)false));
    }
    
    public void onUpdate() {
        if (!Step.mc.player.collidedHorizontally && this.normal.getValue()) {
            return;
        }
        if (!Step.mc.player.onGround || Step.mc.player.isOnLadder() || Step.mc.player.isInWater() || Step.mc.player.isInLava() || Step.mc.player.movementInput.jump || Step.mc.player.noClip) {
            return;
        }
        if (Step.mc.player.moveForward == 0.0f && Step.mc.player.moveStrafing == 0.0f) {
            return;
        }
        final double n = this.get_n_normal();
        if (this.normal.getValue()) {
            if (n < 0.0 || n > 2.0) {
                return;
            }
            if (n == 2.0) {
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.42, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.78, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.63, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.51, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.9, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.21, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.45, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.43, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0, Step.mc.player.posZ);
            }
            if (n == 1.5) {
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.41999998688698, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.7531999805212, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.00133597911214, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.16610926093821, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.24918707874468, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 1.1707870772188, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
            }
            if (n == 1.0) {
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.41999998688698, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + 0.7531999805212, Step.mc.player.posZ, Step.mc.player.onGround));
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
            }
        }
        if (this.reverse.getValue()) {
            Step.mc.player.motionY = -1.0;
        }
    }
    
    public double get_n_normal() {
        Step.mc.player.stepHeight = 0.5f;
        double max_y = -1.0;
        final AxisAlignedBB grow = Step.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        if (!Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, grow.offset(0.0, 2.0, 0.0)).isEmpty()) {
            return 100.0;
        }
        for (final AxisAlignedBB aabb : Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, grow)) {
            if (aabb.maxY > max_y) {
                max_y = aabb.maxY;
            }
        }
        return max_y - Step.mc.player.posY;
    }
}
