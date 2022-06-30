//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;

public class NoInterp extends Module
{
    private static NoInterp INSTANCE;
    public Setting<Boolean> lowIQ;
    
    public NoInterp() {
        super("NoInterp", "Renders when some1 nigger", Module.Category.RENDER, true, false, false);
        this.lowIQ = (Setting<Boolean>)this.register(new Setting("LowIQ", (T)true));
        this.setInstance();
    }
    
    public static NoInterp getInstance() {
        if (NoInterp.INSTANCE == null) {
            NoInterp.INSTANCE = new NoInterp();
        }
        return NoInterp.INSTANCE;
    }
    
    private void setInstance() {
        NoInterp.INSTANCE = this;
    }
    
    static {
        NoInterp.INSTANCE = new NoInterp();
    }
}
