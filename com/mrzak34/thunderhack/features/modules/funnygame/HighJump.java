//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.entity.*;

public class HighJump extends Module
{
    private Setting<Float> jh;
    public Setting<Boolean> display;
    
    public HighJump() {
        super("HighJump", "HighJump", Category.FUNNYGAME, true, false, false);
        this.jh = (Setting<Float>)this.register(new Setting("jumphjk", (T)1.5f, (T)0.0f, (T)5.0f));
        this.display = (Setting<Boolean>)this.register(new Setting("Display", (T)false));
    }
    
    @Override
    public void onUpdate() {
        if (HighJump.mc.gameSettings.keyBindJump.pressed) {
            if (this.display.getValue()) {
                final EntityPlayerSP player = HighJump.mc.player;
                player.motionY += 0.9736375212669373;
            }
            else {
                final EntityPlayerSP player2 = HighJump.mc.player;
                player2.motionY += this.jh.getValue();
            }
        }
    }
}
