//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

public class RotationUtils
{
    public static Minecraft mc;
    
    public static void update(final float yaw, final float pitch) {
        final boolean flag = RotationUtils.mc.player.isSprinting();
        if (flag != ((IEntityPlayerSP)RotationUtils.mc.player).getServerSprintState()) {
            if (flag) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            ((IEntityPlayerSP)RotationUtils.mc.player).setServerSprintState(flag);
        }
        final boolean flag2 = RotationUtils.mc.player.isSneaking();
        if (flag2 != ((IEntityPlayerSP)RotationUtils.mc.player).getServerSneakState()) {
            if (flag2) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)RotationUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            ((IEntityPlayerSP)RotationUtils.mc.player).setServerSneakState(flag2);
        }
        if (RotationUtils.mc.player == RotationUtils.mc.getRenderViewEntity()) {
            final AxisAlignedBB axisalignedbb = RotationUtils.mc.player.getEntityBoundingBox();
            final double dX = RotationUtils.mc.player.posX - ((IEntityPlayerSP)RotationUtils.mc.player).getLastReportedPosX();
            final double dY = axisalignedbb.minY - ((IEntityPlayerSP)RotationUtils.mc.player).getLastReportedPosY();
            final double dZ = RotationUtils.mc.player.posZ - ((IEntityPlayerSP)RotationUtils.mc.player).getLastReportedPosZ();
            final double dYaw = yaw - ((IEntityPlayerSP)RotationUtils.mc.player).getLastReportedYaw();
            final double dPitch = pitch - ((IEntityPlayerSP)RotationUtils.mc.player).getLastReportedPitch();
            ((IEntityPlayerSP)RotationUtils.mc.player).setPositionUpdateTicks(((IEntityPlayerSP)RotationUtils.mc.player).getPositionUpdateTicks() + 1);
            boolean positionChanged = dX * dX + dY * dY + dZ * dZ > 9.0E-4 || ((IEntityPlayerSP)RotationUtils.mc.player).getPositionUpdateTicks() >= 20;
            final boolean rotationChanged = dYaw != 0.0 || dPitch != 0.0;
            if (RotationUtils.mc.player.isRiding()) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(RotationUtils.mc.player.motionX, -999.0, RotationUtils.mc.player.motionZ, yaw, pitch, RotationUtils.mc.player.onGround));
                positionChanged = false;
            }
            else if (positionChanged && rotationChanged) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(RotationUtils.mc.player.posX, axisalignedbb.minY, RotationUtils.mc.player.posZ, yaw, pitch, RotationUtils.mc.player.onGround));
            }
            else if (positionChanged) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(RotationUtils.mc.player.posX, axisalignedbb.minY, RotationUtils.mc.player.posZ, RotationUtils.mc.player.onGround));
            }
            else if (rotationChanged) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, RotationUtils.mc.player.onGround));
            }
            else if (((IEntityPlayerSP)RotationUtils.mc.player).getPrevOnGround() != RotationUtils.mc.player.onGround) {
                RotationUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer(RotationUtils.mc.player.onGround));
            }
            if (positionChanged) {
                ((IEntityPlayerSP)RotationUtils.mc.player).setLastReportedPosX(RotationUtils.mc.player.posX);
                ((IEntityPlayerSP)RotationUtils.mc.player).setLastReportedPosY(axisalignedbb.minY);
                ((IEntityPlayerSP)RotationUtils.mc.player).setLastReportedPosZ(RotationUtils.mc.player.posZ);
                ((IEntityPlayerSP)RotationUtils.mc.player).setPositionUpdateTicks(0);
            }
            if (rotationChanged) {
                ((IEntityPlayerSP)RotationUtils.mc.player).setLastReportedYaw(yaw);
                ((IEntityPlayerSP)RotationUtils.mc.player).setLastReportedPitch(pitch);
            }
            ((IEntityPlayerSP)RotationUtils.mc.player).setPrevOnGround(RotationUtils.mc.player.onGround);
            ((IEntityPlayerSP)RotationUtils.mc.player).setAutoJumpEnabled(RotationUtils.mc.gameSettings.autoJump);
        }
    }
    
    static {
        RotationUtils.mc = Minecraft.getMinecraft();
    }
}
