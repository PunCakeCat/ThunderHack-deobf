//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PushAttack extends Module
{
    public Setting<Float> clickCoolDown;
    
    public PushAttack() {
        super("PushAttack", "\u0413\u0440\u044b\u0437\u0442\u044c \u0433\u0435\u043f\u043b\u044b-\u0438 \u0431\u0438\u0442\u044c \u043e\u0434\u043d\u043e\u0432\u0440\u0435\u043c\u0435\u043d\u043d\u043e", Category.COMBAT, true, false, false);
        this.clickCoolDown = (Setting<Float>)this.register(new Setting("clickCoolDown", (T)1.0f, (T)0.5f, (T)1.0f, "Wall Range."));
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateWalkingPlayerEvent event) {
        if (PushAttack.mc.player.getCooledAttackStrength(0.0f) == this.clickCoolDown.getValue() && PushAttack.mc.gameSettings.keyBindAttack.pressed) {
            PushAttack.mc.clickMouse();
        }
    }
}
