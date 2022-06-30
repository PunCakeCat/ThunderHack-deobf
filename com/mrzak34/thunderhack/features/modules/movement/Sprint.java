//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Sprint extends Module
{
    private Setting<mode> Mode;
    
    public Sprint() {
        super("Sprint", "\u0430\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438-\u0441\u043f\u0440\u0438\u043d\u0442\u0438\u0442\u0441\u044f", Module.Category.MOVEMENT, true, false, false);
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.legit));
    }
    
    @SubscribeEvent
    public void onMove(final EventMove event) {
        if (event.getStage() == 1 && this.Mode.getValue() == mode.Rage && (Sprint.mc.player.movementInput.moveForward != 0.0f || Sprint.mc.player.moveStrafing != 0.0f)) {
            event.setCanceled(true);
        }
    }
    
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        if (this.Mode.getValue() == mode.legit) {
            if (Sprint.mc.gameSettings.keyBindForward.isKeyDown()) {
                Sprint.mc.player.setSprinting(true);
            }
        }
        else {
            Sprint.mc.player.setSprinting(true);
        }
    }
    
    public String getDisplayInfo() {
        return "[" + this.Mode.getValue().toString() + "]";
    }
    
    public enum mode
    {
        legit, 
        Rage;
    }
}
