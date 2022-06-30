//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class PushEvent extends EventStage
{
    public Entity entity;
    public double x;
    public double y;
    public double z;
    public boolean airbone;
    
    public PushEvent(final Entity entity, final double x, final double y, final double z, final boolean airbone) {
        super(0);
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.z = z;
        this.airbone = airbone;
    }
    
    public PushEvent(final int stage) {
        super(stage);
    }
    
    public PushEvent(final int stage, final Entity entity) {
        super(stage);
        this.entity = entity;
    }
}
