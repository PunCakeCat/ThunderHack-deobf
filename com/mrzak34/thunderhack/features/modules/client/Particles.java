//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.gui.particles.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;

public class Particles extends Module
{
    public Setting<Integer> delta;
    public Setting<Integer> amount;
    public Setting<Float> scale1;
    private final Setting<ColorSetting> color1;
    private final Setting<ColorSetting> color4;
    public Setting<Boolean> lines;
    public Setting<Integer> dist;
    public Setting<Float> scale12;
    private ParticleSystem particleSystem;
    boolean started;
    boolean render;
    
    public Particles() {
        super("Particles", "\u0440\u0438\u0441\u0443\u0435\u0442 \u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b \u0432 \u0433\u0443\u0438", Category.CLIENT, true, false, false);
        this.delta = (Setting<Integer>)this.register(new Setting("Delta ", (T)1, (T)0, (T)60));
        this.amount = (Setting<Integer>)this.register(new Setting("Amount ", (T)150, (T)0, (T)666));
        this.scale1 = (Setting<Float>)this.register(new Setting("duplicateOutline", (T)5.0f, (T)0.1f, (T)10.0f));
        this.color1 = (Setting<ColorSetting>)this.register(new Setting("MainColor", (T)new ColorSetting(-2013200640)));
        this.color4 = (Setting<ColorSetting>)this.register(new Setting("LineColor", (T)new ColorSetting(-2013200640)));
        this.lines = (Setting<Boolean>)this.register(new Setting("Lines", (T)false));
        this.dist = (Setting<Integer>)this.register(new Setting("Dist ", (T)150, (T)100, (T)500, v -> this.lines.getValue()));
        this.scale12 = (Setting<Float>)this.register(new Setting("LineT", (T)0.5f, (T)0.1f, (T)1.0f));
    }
    
    @Override
    public void onEnable() {
        this.particleSystem = new ParticleSystem((int)this.amount.getValue(), (float)this.scale1.getValue(), (ColorSetting)this.color1.getValue(), (ColorSetting)this.color4.getValue(), (boolean)this.lines.getValue(), (float)this.scale12.getValue(), (int)this.dist.getValue());
    }
    
    @Override
    public void onUpdate() {
        if (!this.started) {
            this.particleSystem = new ParticleSystem((int)this.amount.getValue(), (float)this.scale1.getValue(), (ColorSetting)this.color1.getValue(), (ColorSetting)this.color4.getValue(), (boolean)this.lines.getValue(), (float)this.scale12.getValue(), (int)this.dist.getValue());
            this.started = true;
        }
        if (Particles.mc.player == null && Particles.mc.world == null) {
            return;
        }
        if (!this.render) {
            return;
        }
        try {
            this.particleSystem.tick((int)this.delta.getValue());
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onGuiOpened(final GuiOpenEvent event) {
        if (event.getGui() != null) {
            this.render = true;
        }
        else {
            this.render = false;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        if (!this.render) {
            return;
        }
        try {
            this.particleSystem.render();
        }
        catch (Exception ex) {}
    }
}
