//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class TurnEvent extends EventStage
{
    private final float yaw;
    private final float pitch;
    
    public TurnEvent(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
}
