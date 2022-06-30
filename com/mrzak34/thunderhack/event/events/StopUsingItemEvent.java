//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class StopUsingItemEvent extends EventStage
{
    private boolean packet;
    
    public StopUsingItemEvent() {
        this.packet = false;
    }
    
    public boolean isPacket() {
        return this.packet;
    }
    
    public void setPacket(final boolean packet) {
        this.packet = packet;
    }
}
