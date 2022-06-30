//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.features.command.*;

public class AutoRespawn extends Module
{
    public Setting<Boolean> deathcoords;
    public Setting<Boolean> autokit;
    public Setting<String> kit;
    private final TimerUtil timer;
    
    public AutoRespawn() {
        super("AutoRespawn", "\u0430\u0432\u0442\u043e\u0440\u0435\u0441\u043f\u0430\u0432\u043d \u0441 \u0430\u0432\u0442\u043e\u043a\u0438\u0442\u043e\u043c", Category.PLAYER, true, false, false);
        this.deathcoords = (Setting<Boolean>)this.register(new Setting("deathcoords", (T)true));
        this.autokit = (Setting<Boolean>)this.register(new Setting("Auto Kit", (T)false));
        this.kit = (Setting<String>)this.register(new Setting("kit name", (T)"kitname", v -> this.autokit.getValue()));
        this.timer = new TimerUtil();
    }
    
    @Override
    public void onTick() {
        if (nullCheck()) {
            return;
        }
        if (this.timer.getPassedTimeMs() > 2100L) {
            this.timer.reset();
        }
        if (AutoRespawn.mc.currentScreen instanceof GuiGameOver) {
            AutoRespawn.mc.player.respawnPlayer();
            AutoRespawn.mc.displayGuiScreen((GuiScreen)null);
        }
        if (AutoRespawn.mc.currentScreen instanceof GuiGameOver && this.timer.getPassedTimeMs() > 200L) {
            if (this.autokit.getValue()) {
                AutoRespawn.mc.player.sendChatMessage("/kit " + this.kit.getValue());
            }
            if (this.deathcoords.getValue()) {
                Command.sendMessage(ChatFormatting.GOLD + "[PlayerDeath] " + ChatFormatting.YELLOW + (int)AutoRespawn.mc.player.posX + " " + (int)AutoRespawn.mc.player.posY + " " + (int)AutoRespawn.mc.player.posZ);
            }
            this.timer.reset();
        }
    }
}
