//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

public class KRotationUtil
{
    public static Minecraft mc;
    
    public static void update(final float yaw, final float pitch) {
        final boolean flag = KRotationUtil.mc.player.isSprinting();
        if (flag != ((IEntityPlayerSP)KRotationUtil.mc.player).getServerSprintState()) {
            if (flag) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)KRotationUtil.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)KRotationUtil.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            ((IEntityPlayerSP)KRotationUtil.mc.player).setServerSprintState(flag);
        }
        final boolean flag2 = KRotationUtil.mc.player.isSneaking();
        if (flag2 != ((IEntityPlayerSP)KRotationUtil.mc.player).getServerSneakState()) {
            if (flag2) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)KRotationUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)KRotationUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            ((IEntityPlayerSP)KRotationUtil.mc.player).setServerSneakState(flag2);
        }
        if (KRotationUtil.mc.player == KRotationUtil.mc.getRenderViewEntity()) {
            final AxisAlignedBB axisalignedbb = KRotationUtil.mc.player.getEntityBoundingBox();
            final double dX = KRotationUtil.mc.player.posX - ((IEntityPlayerSP)KRotationUtil.mc.player).getLastReportedPosX();
            final double dY = axisalignedbb.minY - ((IEntityPlayerSP)KRotationUtil.mc.player).getLastReportedPosY();
            final double dZ = KRotationUtil.mc.player.posZ - ((IEntityPlayerSP)KRotationUtil.mc.player).getLastReportedPosZ();
            final double dYaw = yaw - ((IEntityPlayerSP)KRotationUtil.mc.player).getLastReportedYaw();
            final double dPitch = pitch - ((IEntityPlayerSP)KRotationUtil.mc.player).getLastReportedPitch();
            ((IEntityPlayerSP)KRotationUtil.mc.player).setPositionUpdateTicks(((IEntityPlayerSP)KRotationUtil.mc.player).getPositionUpdateTicks() + 1);
            boolean positionChanged = dX * dX + dY * dY + dZ * dZ > 9.0E-4 || ((IEntityPlayerSP)KRotationUtil.mc.player).getPositionUpdateTicks() >= 20;
            final boolean rotationChanged = dYaw != 0.0 || dPitch != 0.0;
            if (KRotationUtil.mc.player.isRiding()) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(KRotationUtil.mc.player.motionX, -999.0, KRotationUtil.mc.player.motionZ, yaw, pitch, KRotationUtil.mc.player.onGround));
                positionChanged = false;
            }
            else if (positionChanged && rotationChanged) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(KRotationUtil.mc.player.posX, axisalignedbb.minY, KRotationUtil.mc.player.posZ, yaw, pitch, KRotationUtil.mc.player.onGround));
            }
            else if (positionChanged) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(KRotationUtil.mc.player.posX, axisalignedbb.minY, KRotationUtil.mc.player.posZ, KRotationUtil.mc.player.onGround));
            }
            else if (rotationChanged) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, KRotationUtil.mc.player.onGround));
            }
            else if (((IEntityPlayerSP)KRotationUtil.mc.player).getPrevOnGround() != KRotationUtil.mc.player.onGround) {
                KRotationUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer(KRotationUtil.mc.player.onGround));
            }
            if (positionChanged) {
                ((IEntityPlayerSP)KRotationUtil.mc.player).setLastReportedPosX(KRotationUtil.mc.player.posX);
                ((IEntityPlayerSP)KRotationUtil.mc.player).setLastReportedPosY(axisalignedbb.minY);
                ((IEntityPlayerSP)KRotationUtil.mc.player).setLastReportedPosZ(KRotationUtil.mc.player.posZ);
                ((IEntityPlayerSP)KRotationUtil.mc.player).setPositionUpdateTicks(0);
            }
            if (rotationChanged) {
                ((IEntityPlayerSP)KRotationUtil.mc.player).setLastReportedYaw(yaw);
                ((IEntityPlayerSP)KRotationUtil.mc.player).setLastReportedPitch(pitch);
            }
            ((IEntityPlayerSP)KRotationUtil.mc.player).setPrevOnGround(KRotationUtil.mc.player.onGround);
            ((IEntityPlayerSP)KRotationUtil.mc.player).setAutoJumpEnabled(KRotationUtil.mc.gameSettings.autoJump);
        }
    }
    
    static {
        KRotationUtil.mc = Minecraft.getMinecraft();
    }
}
