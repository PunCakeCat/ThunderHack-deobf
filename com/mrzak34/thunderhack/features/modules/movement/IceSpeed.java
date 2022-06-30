//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;

public class IceSpeed extends Module
{
    private static IceSpeed INSTANCE;
    private final Setting<Float> speed;
    
    public IceSpeed() {
        super("IceSpeed", "+\u0441\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u0435\u0441\u043b\u0438 \u043d\u0430 \u043b\u044c\u0434\u0443", Module.Category.MOVEMENT, false, false, false);
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.4f, (T)0.1f, (T)1.5f));
        IceSpeed.INSTANCE = this;
    }
    
    public static IceSpeed getINSTANCE() {
        if (IceSpeed.INSTANCE == null) {
            IceSpeed.INSTANCE = new IceSpeed();
        }
        return IceSpeed.INSTANCE;
    }
    
    public void onUpdate() {
        Blocks.ICE.slipperiness = this.speed.getValue();
        Blocks.PACKED_ICE.slipperiness = this.speed.getValue();
        Blocks.FROSTED_ICE.slipperiness = this.speed.getValue();
    }
    
    public void onDisable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
    
    static {
        IceSpeed.INSTANCE = new IceSpeed();
    }
}
