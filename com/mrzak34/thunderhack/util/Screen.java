//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.util.text.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.event.world.*;

public class Screen
{
    protected static Minecraft mc;
    public boolean visible;
    
    public Screen() {
        this.visible = true;
    }
    
    public TextFormatting ColorfromInt(final int i) {
        if (i < 16) {
            return TextFormatting.fromColorIndex(i);
        }
        switch (i) {
            case 16: {
                return TextFormatting.OBFUSCATED;
            }
            case 17: {
                return TextFormatting.BOLD;
            }
            case 18: {
                return TextFormatting.STRIKETHROUGH;
            }
            case 19: {
                return TextFormatting.UNDERLINE;
            }
            case 20: {
                return TextFormatting.ITALIC;
            }
            case 21: {
                return TextFormatting.RESET;
            }
            default: {
                return null;
            }
        }
    }
    
    public void setup() {
    }
    
    public void GuiOpen(final GuiOpenEvent event) {
    }
    
    public void GuiScreenEventPost(final GuiScreenEvent.ActionPerformedEvent.Post event) {
    }
    
    public void GuiScreenEventInit(final GuiScreenEvent.InitGuiEvent.Post event) {
    }
    
    public void GuiScreenEventPre(final GuiScreenEvent.ActionPerformedEvent.Pre event) {
    }
    
    public void onClientTick(final TickEvent.ClientTickEvent event) {
    }
    
    public void DrawScreenEvent(final GuiScreenEvent.DrawScreenEvent event) {
    }
    
    public void onWorldUnload(final WorldEvent.Unload event) {
    }
    
    static {
        Screen.mc = Minecraft.getMinecraft();
    }
}
