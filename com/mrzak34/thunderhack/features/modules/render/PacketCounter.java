//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.notification.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.helper.*;

public class PacketCounter extends Module
{
    private final TimerUtil timer;
    public Setting<Integer> delayms;
    public Setting<Integer> waterMarkZ1;
    public Setting<Integer> waterMarkZ2;
    private Setting<mode> Mode;
    public Setting<Integer> i1;
    public Setting<Integer> i2;
    public Setting<Integer> i3;
    public Setting<Boolean> notif;
    public int i;
    ScaledResolution sr;
    float x1;
    float y1;
    
    public PacketCounter() {
        super("PacketCounter", "PacketCounter", Module.Category.HUD, true, false, false);
        this.delayms = (Setting<Integer>)this.register(new Setting("Delay", (T)500, (T)0, (T)5000));
        this.waterMarkZ1 = (Setting<Integer>)this.register(new Setting("Y", (T)10, (T)0, (T)524));
        this.waterMarkZ2 = (Setting<Integer>)this.register(new Setting("X", (T)20, (T)0, (T)862));
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.FunnyGame));
        this.i1 = (Setting<Integer>)this.register(new Setting("green under", (T)20, (T)0, (T)100, v -> this.Mode.getValue() == mode.Custom));
        this.i2 = (Setting<Integer>)this.register(new Setting("Orange under", (T)50, (T)0, (T)100, v -> this.Mode.getValue() == mode.Custom));
        this.i3 = (Setting<Integer>)this.register(new Setting("Red", (T)50, (T)0, (T)100, v -> this.Mode.getValue() == mode.Custom));
        this.notif = (Setting<Boolean>)this.register(new Setting("Notification", (T)true));
        this.i = 0;
        this.sr = new ScaledResolution(PacketCounter.mc);
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.timer = new TimerUtil();
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        ++this.i;
        if (this.timer.passedMs(this.delayms.getValue())) {
            this.i = 0;
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        this.y1 = this.sr.getScaledHeight() / (1000.0f / this.waterMarkZ1.getValue());
        this.x1 = this.sr.getScaledWidth() / (1000.0f / this.waterMarkZ2.getValue());
        Color color = null;
        RenderUtil.drawSmoothRect(this.waterMarkZ2.getValue(), this.waterMarkZ1.getValue(), (float)(93 + this.waterMarkZ2.getValue()), (float)(20 + this.waterMarkZ1.getValue()), new Color(35, 35, 40, 230).getRGB());
        RenderUtil.drawSmoothRect((float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 12), (float)(90 + this.waterMarkZ2.getValue()), (float)(15 + this.waterMarkZ1.getValue()), new Color(51, 51, 58, 230).getRGB());
        if (this.Mode.getValue() == mode.FunnyGame) {
            if (this.i < 25) {
                color = new Color(54, 250, 0, 255);
            }
            if (this.i > 25 && this.i < 50) {
                color = new Color(255, 178, 0, 255);
            }
            if (this.i > 50) {
                color = new Color(255, 0, 0, 255);
                if (this.notif.getValue()) {
                    NotificationManager.publicity("PacketCounter", "\u0412\u044b\u0440\u0443\u0431\u0430\u0439, \u0449\u0430 \u043a\u0438\u043a\u043d\u0435\u0442!!!", 2, NotificationType.WARNING);
                }
            }
        }
        if (this.Mode.getValue() == mode.TOOBEE) {
            if (this.i < 25) {
                color = new Color(54, 250, 0, 255);
            }
            if (this.i > 25 && this.i < 50) {
                color = new Color(255, 178, 0, 255);
            }
            if (this.i > 50) {
                color = new Color(255, 0, 0, 255);
                NotificationManager.publicity("PacketCounter", "\u0412\u044b\u0440\u0443\u0431\u0430\u0439, \u0449\u0430 \u043a\u0438\u043a\u043d\u0435\u0442!!!", 2, NotificationType.WARNING);
            }
        }
        if (this.Mode.getValue() == mode.Custom) {
            if (this.i < this.i1.getValue()) {
                color = new Color(54, 250, 0, 255);
            }
            if (this.i > this.i2.getValue() && this.i < this.i3.getValue()) {
                color = new Color(255, 178, 0, 255);
            }
            if (this.i > this.i3.getValue()) {
                color = new Color(255, 0, 0, 255);
                NotificationManager.publicity("PacketCounter", "\u0412\u044b\u0440\u0443\u0431\u0430\u0439, \u0449\u0430 \u043a\u0438\u043a\u043d\u0435\u0442!!!", 2, NotificationType.WARNING);
            }
        }
        if (color != null) {
            RenderUtil.drawSmoothRect((float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 12), (float)(Math.min(this.i, 85) + this.waterMarkZ2.getValue() + 5), (float)(15 + this.waterMarkZ1.getValue()), color.getRGB());
        }
        Util.fr.drawStringWithShadow("PacketCounter", (float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
    }
    
    public enum mode
    {
        FunnyGame, 
        TOOBEE, 
        Custom;
    }
}
