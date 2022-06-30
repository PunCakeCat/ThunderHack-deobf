//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class NewRotationManager
{
    public void setPlayerRotations(final float yaw, final float pitch) {
        Util.mc.player.rotationYaw = yaw;
        Util.mc.player.rotationYawHead = yaw;
        Util.mc.player.rotationPitch = pitch;
    }
    
    public void lookAtEntity(final Entity entity) {
        final float[] angle = calcAngle(Util.mc.player.getPositionEyes(Util.mc.getRenderPartialTicks()), entity.getPositionEyes(Util.mc.getRenderPartialTicks()));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
}
