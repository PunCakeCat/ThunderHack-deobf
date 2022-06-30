//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.command.*;

public class ShiftInterp extends Module
{
    private static ShiftInterp INSTANCE;
    public Setting<Boolean> animatic;
    public Setting<Boolean> sleep;
    public final Setting<Integer> animtimer;
    public boolean anim;
    public Timer timr;
    
    public ShiftInterp() {
        super("ShiftInterp", "ShiftInterp", Module.Category.RENDER, true, false, false);
        this.animatic = (Setting<Boolean>)this.register(new Setting("Animatic", (T)true));
        this.sleep = (Setting<Boolean>)this.register(new Setting("Sleep", (T)false));
        this.animtimer = (Setting<Integer>)this.register(new Setting("Delay", (T)1000, (T)50, (T)5000));
        this.anim = false;
        this.timr = new Timer();
        this.setInstance();
    }
    
    public static ShiftInterp getInstance() {
        if (ShiftInterp.INSTANCE == null) {
            ShiftInterp.INSTANCE = new ShiftInterp();
        }
        return ShiftInterp.INSTANCE;
    }
    
    private void setInstance() {
        ShiftInterp.INSTANCE = this;
    }
    
    public void onUpdate() {
        if (this.timr.passed(this.animtimer.getValue())) {
            Command.sendMessage(String.valueOf(this.anim));
            this.anim = !this.anim;
            this.timr.reset();
        }
    }
    
    static {
        ShiftInterp.INSTANCE = new ShiftInterp();
    }
}
