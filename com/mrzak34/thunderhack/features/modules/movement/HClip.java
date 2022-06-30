//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.command.*;

public class HClip extends Module
{
    public Setting<Integer> dst;
    
    public HClip() {
        super("HClip", "\u0422\u0435\u043f\u0430\u0435\u0442 \u0432 \u0432\u0435\u0440\u0445", Module.Category.MOVEMENT, true, false, false);
        this.dst = (Setting<Integer>)this.register(new Setting("Distance", (T)100, (T)(-200), (T)200));
    }
    
    public void onEnable() {
        try {
            final double[] direction = PlayerUtils.getDirectionFromYaw(HClip.mc.player.rotationYaw);
            if (HClip.mc.player.getRidingEntity() != null) {
                HClip.mc.player.getRidingEntity().setPosition(HClip.mc.player.getRidingEntity().posX + direction[0] * this.dst.getValue(), HClip.mc.player.getRidingEntity().posY, HClip.mc.player.getRidingEntity().posZ + direction[1] * this.dst.getValue());
            }
            else {
                HClip.mc.player.setPosition(HClip.mc.player.posX + direction[0] * this.dst.getValue(), HClip.mc.player.posY, HClip.mc.player.posZ + direction[1] * this.dst.getValue());
            }
            Command.sendMessage("\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0441\u044f " + ((this.dst.getValue() > 0) ? "\u0432\u043f\u0435\u0440\u0435\u0434 \u043d\u0430 " : "\u043d\u0430\u0437\u0430\u0434 \u043d\u0430 ") + Math.abs(this.dst.getValue()) + " \u0431\u043b\u043e\u043a\u043e\u0432.");
        }
        catch (NumberFormatException exception) {
            Command.sendMessage("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f \u043d\u0435 \u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u0430\u044f!");
        }
    }
}
