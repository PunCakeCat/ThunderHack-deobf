//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraft.entity.player.*;

public class BlockPushOutEvent extends EventStage
{
    private static BlockPushOutEvent INSTANCE;
    private EntityPlayer player;
    
    public static BlockPushOutEvent get(final EntityPlayer player) {
        BlockPushOutEvent.INSTANCE.player = player;
        return BlockPushOutEvent.INSTANCE;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    static {
        BlockPushOutEvent.INSTANCE = new BlockPushOutEvent();
    }
}
