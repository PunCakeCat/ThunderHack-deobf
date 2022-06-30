//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;

public class PlayerJoinEvent extends EventStage
{
    private final String name;
    
    public PlayerJoinEvent(final String n) {
        this.name = n;
    }
    
    public String getName() {
        return this.name;
    }
}
