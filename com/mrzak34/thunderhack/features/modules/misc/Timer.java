//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.*;

public class Timer extends Module
{
    public Setting<Float> timerSpeed;
    
    public Timer() {
        super("Timer", "\u0438\u0433\u0440\u043e\u0432\u043e\u0439 \u0442\u0430\u0439\u043c\u0435\u0440", Category.PLAYER, false, false, false);
        this.timerSpeed = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)0.01f, (T)2.0f));
    }
    
    @Override
    public void onUpdate() {
        Thunderhack.TICK_TIMER = this.timerSpeed.getValue();
    }
    
    @Override
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
    }
    
    @Override
    public String getDisplayInfo() {
        return this.timerSpeed.getValueAsString();
    }
}
