//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.client.entity.*;

public class NoFall extends Module
{
    public NoFall() {
        super("NoFall", "\u0440\u0443\u0431\u0435\u0440\u0431\u0435\u043d\u0434\u0438\u0442 \u0435\u0441\u043b\u0438 \u0442\u044b-\u0443\u043f\u0430\u043b", Module.Category.MOVEMENT, false, false, false);
    }
    
    public void onUpdate() {
        if (NoFall.mc.player.fallDistance > 3.0f && !NoFall.mc.player.isSneaking()) {
            final EntityPlayerSP player = NoFall.mc.player;
            player.motionY -= 0.1;
            NoFall.mc.player.onGround = true;
            NoFall.mc.player.capabilities.disableDamage = true;
        }
    }
}
