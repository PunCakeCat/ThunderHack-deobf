//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.font;

public class WindowAttributes
{
    private boolean closable;
    private boolean resizable;
    
    public WindowAttributes(final boolean closable, final boolean resizable) {
        this.closable = closable;
        this.resizable = resizable;
    }
    
    public boolean isClosable() {
        return this.closable;
    }
    
    public boolean isResizable() {
        return this.resizable;
    }
    
    public void setClosable(final boolean closable) {
        this.closable = closable;
    }
    
    public void setResizable(final boolean resizable) {
        this.resizable = resizable;
    }
}
