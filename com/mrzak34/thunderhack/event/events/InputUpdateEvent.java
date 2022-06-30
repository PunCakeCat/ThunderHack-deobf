//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;

@Cancelable
public class InputUpdateEvent extends EventStage
{
    MovementInput movementInput;
    
    public InputUpdateEvent(final MovementInput movementInput) {
        this.movementInput = movementInput;
    }
    
    public MovementInput getMovementInput() {
        return this.movementInput;
    }
}
