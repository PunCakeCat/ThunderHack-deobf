//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;
import com.mojang.realmsclient.gui.*;
import java.awt.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AFKtimer extends Module
{
    public static AFKtimer INSTANCE;
    public Setting<Integer> time;
    public Setting<Boolean> afkrpc;
    private final Timer timer;
    public boolean afk;
    
    public AFKtimer() {
        super("AFKtimer", "\u0434\u043b\u044f \u0440\u043f\u0441", Category.MISC, true, false, false);
        this.time = (Setting<Integer>)this.register(new Setting("AFKtime", (T)30, (T)10, (T)600));
        this.afkrpc = (Setting<Boolean>)this.register(new Setting("toRPC", (T)Boolean.TRUE, "qdickpick"));
        this.timer = new Timer();
        this.afk = false;
        AFKtimer.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (AFKtimer.mc.player == null || AFKtimer.mc.world == null) {
            this.timer.reset();
            return;
        }
        if (Thunderhack.speedManager.getSpeedKpH() > 0.0) {
            this.timer.reset();
            this.afk = false;
        }
        if (this.timer.passedMs(this.time.getValue() * 1000)) {
            this.afk = true;
        }
    }
    
    @Override
    public void onEnable() {
        this.timer.reset();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        final ScaledResolution sr = new ScaledResolution(AFKtimer.mc);
        if (this.afk) {
            GL11.glPushMatrix();
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            final int mins = (int)(this.timer.getPassedTimeMs() / 1000L / 60L);
            final String text = "\u0422\u044b \u0430\u0444\u043a " + mins + " \u043c\u0438\u043d\u0443\u0442";
            Util.fr.drawStringWithShadow((this.timer.getPassedTimeMs() >= this.time.getValue() * 1000) ? (ChatFormatting.RED + text) : text, (float)(int)(sr.getScaledWidth() / 2.0f - AFKtimer.mc.fontRenderer.getStringWidth(text)), (float)(int)(sr.getScaledHeight() / 2.0f + 30.0f), new Color(170, 170, 170).getRGB());
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
}
