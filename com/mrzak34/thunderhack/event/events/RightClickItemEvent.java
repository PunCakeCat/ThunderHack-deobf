//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

@Cancelable
public class RightClickItemEvent extends EventStage
{
    private final EntityPlayer player;
    private final World worldIn;
    private final EnumHand hand;
    
    public RightClickItemEvent(final EntityPlayer player, final World worldIn, final EnumHand hand) {
        this.player = player;
        this.worldIn = worldIn;
        this.hand = hand;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public World getWorldIn() {
        return this.worldIn;
    }
    
    public EnumHand getHand() {
        return this.hand;
    }
}
