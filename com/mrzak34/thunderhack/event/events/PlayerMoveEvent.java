//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class PlayerMoveEvent extends EventStage
{
    private static PlayerMoveEvent INSTANCE;
    private MoverType type;
    private double x;
    private double y;
    private double z;
    
    public static PlayerMoveEvent get(final MoverType type, final double x, final double y, final double z) {
        PlayerMoveEvent.INSTANCE.type = type;
        PlayerMoveEvent.INSTANCE.x = x;
        PlayerMoveEvent.INSTANCE.y = y;
        PlayerMoveEvent.INSTANCE.z = z;
        return PlayerMoveEvent.INSTANCE;
    }
    
    public MoverType getType() {
        return this.type;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setType(final MoverType type) {
        this.type = type;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    static {
        PlayerMoveEvent.INSTANCE = new PlayerMoveEvent();
    }
}
