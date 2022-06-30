//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraft.entity.*;

public class EntityRemovedEvent extends EventStage
{
    public Entity entity;
    
    public EntityRemovedEvent(final Entity entity) {
        this.entity = entity;
    }
}
