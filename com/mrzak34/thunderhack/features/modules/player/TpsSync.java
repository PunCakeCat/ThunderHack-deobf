//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.*;

public class TpsSync extends Module
{
    private static TpsSync INSTANCE;
    public Setting<Boolean> attack;
    public Setting<Boolean> mining;
    
    public TpsSync() {
        super("TpsSync", "\u0441\u0438\u043d\u0445\u0440\u043e\u043d\u0438\u0437\u0438\u0440\u0443\u0435\u0442 \u0438\u0433\u0440\u0443-\u0441 \u0442\u043f\u0441", Module.Category.PLAYER, true, false, false);
        this.attack = (Setting<Boolean>)this.register(new Setting("Attack", (T)Boolean.FALSE));
        this.mining = (Setting<Boolean>)this.register(new Setting("Mine", (T)Boolean.TRUE));
        this.setInstance();
    }
    
    public static TpsSync getInstance() {
        if (TpsSync.INSTANCE == null) {
            TpsSync.INSTANCE = new TpsSync();
        }
        return TpsSync.INSTANCE;
    }
    
    private void setInstance() {
        TpsSync.INSTANCE = this;
    }
    
    public void onUpdate() {
        Thunderhack.TICK_TIMER = Thunderhack.serverManager.getTPS() / 20.0f;
    }
    
    static {
        TpsSync.INSTANCE = new TpsSync();
    }
}
