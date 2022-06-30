//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class ClickBlockEvent extends EventStage
{
    private final BlockPos pos;
    private final EnumFacing facing;
    
    public ClickBlockEvent(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public static class Right extends ClickBlockEvent
    {
        private final Vec3d vec;
        private final EnumHand hand;
        
        public Right(final BlockPos pos, final EnumFacing facing, final Vec3d vec, final EnumHand hand) {
            super(pos, facing);
            this.vec = vec;
            this.hand = hand;
        }
        
        public EnumHand getHand() {
            return this.hand;
        }
        
        public Vec3d getVec() {
            return this.vec;
        }
    }
}
