//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

@Cancelable
public class DamageBlockEvent extends EventStage
{
    private BlockPos blockPos;
    private EnumFacing enumFacing;
    
    public DamageBlockEvent(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
    
    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
    
    public EnumFacing getEnumFacing() {
        return this.enumFacing;
    }
    
    public void setEnumFacing(final EnumFacing enumFacing) {
        this.enumFacing = enumFacing;
    }
}
