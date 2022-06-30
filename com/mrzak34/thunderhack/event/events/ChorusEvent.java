//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;

public class ChorusEvent extends EventStage
{
    private final double chorusX;
    private final double chorusY;
    private final double chorusZ;
    
    public ChorusEvent(final double x, final double y, final double z) {
        this.chorusX = x;
        this.chorusY = y;
        this.chorusZ = z;
    }
    
    public double getChorusX() {
        return this.chorusX;
    }
    
    public double getChorusY() {
        return this.chorusY;
    }
    
    public double getChorusZ() {
        return this.chorusZ;
    }
}
