//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class EventMove extends EventStage
{
    private MoverType move_type;
    public double x;
    public double y;
    public double z;
    
    public EventMove(final MoverType type, final double x, final double y, final double z) {
        this.move_type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void set_move_type(final MoverType type) {
        this.move_type = type;
    }
    
    public void set_x(final double x) {
        this.x = x;
    }
    
    public void set_y(final double y) {
        this.y = y;
    }
    
    public void set_z(final double z) {
        this.z = z;
    }
    
    public MoverType get_move_type() {
        return this.move_type;
    }
    
    public double get_x() {
        return this.x;
    }
    
    public double get_y() {
        return this.y;
    }
    
    public double get_z() {
        return this.z;
    }
}
