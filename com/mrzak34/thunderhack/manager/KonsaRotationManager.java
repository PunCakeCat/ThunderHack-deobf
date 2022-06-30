//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import net.minecraft.client.*;
import net.minecraft.util.math.*;

public class KonsaRotationManager
{
    private Minecraft mc;
    private float yaw;
    private float pitch;
    private boolean rotationsSet;
    
    public KonsaRotationManager() {
        this.mc = Minecraft.getMinecraft();
        this.rotationsSet = false;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean isRotationsSet() {
        return this.rotationsSet;
    }
    
    public void reset() {
        this.yaw = this.mc.player.rotationYaw;
        this.pitch = this.mc.player.rotationPitch;
        this.rotationsSet = false;
    }
    
    public void setRotations(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.rotationsSet = true;
    }
    
    public boolean safeSetRotations(final float yaw, final float pitch) {
        if (this.rotationsSet) {
            return false;
        }
        this.setRotations(yaw, pitch);
        return true;
    }
    
    public void lookAtPos(final BlockPos pos) {
        final float[] angle = calculateAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)));
        this.setRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final Vec3d vec3d) {
        final float[] angle = calculateAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        this.setRotations(angle[0], angle[1]);
    }
    
    public void lookAtXYZ(final double x, final double y, final double z) {
        final Vec3d vec3d = new Vec3d(x, y, z);
        this.lookAtVec3d(vec3d);
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
}
