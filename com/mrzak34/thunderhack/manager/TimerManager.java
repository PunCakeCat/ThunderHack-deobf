//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.features.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import com.mrzak34.thunderhack.*;

public class TimerManager extends Feature
{
    private float timer;
    private Timer module;
    
    public TimerManager() {
        this.timer = 1.0f;
    }
    
    public void init() {
        this.module = (Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class);
    }
    
    public void unload() {
        this.timer = 1.0f;
        TimerManager.mc.timer.tickLength = 50.0f;
    }
    
    public void update() {
        if (this.module == null || this.module.isEnabled()) {}
        TimerManager.mc.timer.tickLength = 50.0f / ((this.timer <= 0.0f) ? 0.1f : this.timer);
    }
    
    public float getTimer() {
        return this.timer;
    }
    
    public void setTimer(final float timer) {
        if (timer > 0.0f) {
            this.timer = timer;
        }
    }
    
    public void reset() {
        this.timer = 1.0f;
    }
}
