//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

public class SilentRotaionUtil
{
    private float yawi;
    
    public void setYaw(final float yawi) {
        this.yawi = yawi;
        final Minecraft mc = Util.mc;
        Minecraft.getMinecraft();
        Util.mc.player.rotationYawHead = yawi;
        final Minecraft mc2 = Util.mc;
        Minecraft.getMinecraft();
        Util.mc.player.rotationYawHead = yawi;
    }
    
    public static void lookAtVector(final Vec3d vec) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), vec);
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void lookAtEntity(final Entity entity) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), entity.getPositionEyes(Util.mc.getRenderPartialTicks()));
        lookAtAngles(angle[0], angle[1]);
    }
    
    public static void lookAtXYZ(final float X, final float Y, final float Z) {
        final Vec3d vec = new Vec3d((double)X, (double)Y, (double)Z);
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), vec);
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void lookAtAngles(final float yaw, final float pitch) {
        setPlayerRotations(yaw, pitch);
        Util.mc.player.rotationYawHead = yaw;
    }
    
    public static void lookAtBlock(final BlockPos blockPos) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), new Vec3d((Vec3i)blockPos));
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.renderYawOffset = angle[0];
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        Util.mc.player.rotationYaw = yaw;
        Util.mc.player.rotationYawHead = yaw;
        Util.mc.player.rotationPitch = pitch;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float[] calculateAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        final float yD = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0);
        float pD = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)));
        if (pD > 90.0f) {
            pD = 90.0f;
        }
        else if (pD < -90.0f) {
            pD = -90.0f;
        }
        return new float[] { yD, pD };
    }
    
    public static void update(final float yaw, final float pitch) {
        final boolean flag = Util.mc.player.isSprinting();
        if (flag != ((IEntityPlayerSP)Util.mc.player).getServerSprintState()) {
            if (flag) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Util.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                Util.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Util.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            ((IEntityPlayerSP)Util.mc.player).setServerSprintState(flag);
        }
        final boolean flag2 = Util.mc.player.isSneaking();
        if (flag2 != ((IEntityPlayerSP)Util.mc.player).getServerSneakState()) {
            if (flag2) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Util.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                Util.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Util.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            ((IEntityPlayerSP)Util.mc.player).setServerSneakState(flag2);
        }
        if (Util.mc.player == Util.mc.getRenderViewEntity()) {
            final AxisAlignedBB axisalignedbb = Util.mc.player.getEntityBoundingBox();
            final double dX = Util.mc.player.posX - ((IEntityPlayerSP)Util.mc.player).getLastReportedPosX();
            final double dY = axisalignedbb.minY - ((IEntityPlayerSP)Util.mc.player).getLastReportedPosY();
            final double dZ = Util.mc.player.posZ - ((IEntityPlayerSP)Util.mc.player).getLastReportedPosZ();
            final double dYaw = yaw - ((IEntityPlayerSP)Util.mc.player).getLastReportedYaw();
            final double dPitch = pitch - ((IEntityPlayerSP)Util.mc.player).getLastReportedPitch();
            ((IEntityPlayerSP)Util.mc.player).setPositionUpdateTicks(((IEntityPlayerSP)Util.mc.player).getPositionUpdateTicks() + 1);
            boolean positionChanged = dX * dX + dY * dY + dZ * dZ > 9.0E-4 || ((IEntityPlayerSP)Util.mc.player).getPositionUpdateTicks() >= 20;
            final boolean rotationChanged = dYaw != 0.0 || dPitch != 0.0;
            if (Util.mc.player.isRiding()) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Util.mc.player.motionX, -999.0, Util.mc.player.motionZ, yaw, pitch, Util.mc.player.onGround));
                positionChanged = false;
            }
            else if (positionChanged && rotationChanged) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Util.mc.player.posX, axisalignedbb.minY, Util.mc.player.posZ, yaw, pitch, Util.mc.player.onGround));
            }
            else if (positionChanged) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Util.mc.player.posX, axisalignedbb.minY, Util.mc.player.posZ, Util.mc.player.onGround));
            }
            else if (rotationChanged) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, Util.mc.player.onGround));
            }
            else if (((IEntityPlayerSP)Util.mc.player).getPrevOnGround() != Util.mc.player.onGround) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketPlayer(Util.mc.player.onGround));
            }
            if (positionChanged) {
                ((IEntityPlayerSP)Util.mc.player).setLastReportedPosX(Util.mc.player.posX);
                ((IEntityPlayerSP)Util.mc.player).setLastReportedPosY(axisalignedbb.minY);
                ((IEntityPlayerSP)Util.mc.player).setLastReportedPosZ(Util.mc.player.posZ);
                ((IEntityPlayerSP)Util.mc.player).setPositionUpdateTicks(0);
            }
            if (rotationChanged) {
                ((IEntityPlayerSP)Util.mc.player).setLastReportedYaw(yaw);
                ((IEntityPlayerSP)Util.mc.player).setLastReportedPitch(pitch);
            }
            ((IEntityPlayerSP)Util.mc.player).setPrevOnGround(Util.mc.player.onGround);
            ((IEntityPlayerSP)Util.mc.player).setAutoJumpEnabled(Util.mc.gameSettings.autoJump);
        }
    }
}
