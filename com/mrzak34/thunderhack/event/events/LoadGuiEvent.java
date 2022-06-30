//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;

@Cancelable
public class LoadGuiEvent extends EventStage
{
    private GuiScreen gui;
    
    public LoadGuiEvent(final GuiScreen gui) {
        this.gui = gui;
    }
    
    public GuiScreen getGui() {
        return this.gui;
    }
    
    public void setGui(final GuiScreen gui) {
        this.gui = gui;
    }
}
