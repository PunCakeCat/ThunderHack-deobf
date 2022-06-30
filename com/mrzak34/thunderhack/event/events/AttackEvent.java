//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class AttackEvent extends EventStage
{
    Entity entity;
    
    public AttackEvent(final Entity attack) {
        this.entity = attack;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
