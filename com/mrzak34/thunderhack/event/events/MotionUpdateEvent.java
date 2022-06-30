//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;

public class MotionUpdateEvent extends EventStage
{
    public float rotationYaw;
    public float rotationPitch;
    public double posX;
    public double posY;
    public double posZ;
    public boolean onGround;
    public boolean noClip;
    public int stage;
    
    public MotionUpdateEvent(final float rotationYaw, final float rotationPitch, final double posX, final double posY, final double posZ, final boolean onGround, final boolean noClip, final int stage) {
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.onGround = onGround;
        this.noClip = noClip;
        this.stage = stage;
    }
}
