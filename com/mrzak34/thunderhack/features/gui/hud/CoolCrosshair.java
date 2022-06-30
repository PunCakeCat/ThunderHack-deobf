//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.helper.*;
import java.awt.*;

public class CoolCrosshair extends Module
{
    public final Setting<ColorSetting> color;
    public Setting<Float> car;
    private final Setting<Boolean> smt;
    public Setting<Float> lwid;
    public Setting<Float> rounded2;
    int status;
    int santi;
    
    public CoolCrosshair() {
        super("CoolCrosshair", "CoolCrosshair", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.car = (Setting<Float>)this.register(new Setting("otstup", (T)0.0f, (T)0.1f, (T)1.0f));
        this.smt = (Setting<Boolean>)this.register(new Setting("smooth", (T)Boolean.FALSE));
        this.lwid = (Setting<Float>)this.register(new Setting("otstup", (T)0.0f, (T)0.1f, (T)1.0f));
        this.rounded2 = (Setting<Float>)this.register(new Setting("Round2", (T)0.0f, (T)0.5f, (T)20.0f));
        this.status = 0;
        this.santi = 0;
    }
    
    @SubscribeEvent
    public void onRenderAttackIndicator(final RenderAttackIndicatorEvent event) {
        event.setCanceled(true);
    }
    
    @Override
    public void onUpdate() {
        if (this.santi < this.status) {
            this.santi += 60;
        }
        if (this.status < this.santi) {
            this.santi -= 360;
        }
        if (this.santi < 0) {
            this.santi = 0;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        final ScaledResolution sr = new ScaledResolution(CoolCrosshair.mc);
        final float x1 = (float)(sr.getScaledWidth_double() / 2.0 + 0.5);
        final float y1 = (float)(sr.getScaledHeight_double() / 2.0 + 0.5);
        GL11.glEnable(3042);
        this.status = (int)(360.0f / (1.0f / CoolCrosshair.mc.itemRenderer.equippedProgressMainHand));
        drawPartialCircle(x1, y1, this.rounded2.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
        drawPartialCircle(x1, y1, this.rounded2.getValue() - this.car.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
        drawPartialCircle(x1, y1, this.rounded2.getValue() + this.car.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
        drawPartialCircle(x1, y1, this.rounded2.getValue(), 0, this.status, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
        drawPartialCircle(x1, y1, this.rounded2.getValue() - this.car.getValue(), 0, this.status, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
        drawPartialCircle(x1, y1, this.rounded2.getValue() + this.car.getValue(), 0, this.status, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
    }
    
    public static void drawPartialCircle(final float x, final float y, final float radius, int startAngle, int endAngle, final float thickness, final Color colour, final boolean smooth) {
        preRender();
        if (startAngle > endAngle) {
            final int temp = startAngle;
            startAngle = endAngle;
            endAngle = temp;
        }
        if (startAngle < 0) {
            startAngle = 0;
        }
        if (endAngle > 360) {
            endAngle = 360;
        }
        if (smooth) {
            GL11.glEnable(2848);
        }
        else {
            GL11.glDisable(2848);
        }
        GL11.glLineWidth(thickness);
        GL11.glColor4f(colour.getRed() / 255.0f, colour.getGreen() / 255.0f, colour.getBlue() / 255.0f, colour.getAlpha() / 255.0f);
        GL11.glBegin(3);
        final float ratio = 0.01745328f;
        for (int i = startAngle; i <= endAngle; ++i) {
            final float radians = (i - 90) * ratio;
            GL11.glVertex2f(x + (float)Math.cos(radians) * radius, y + (float)Math.sin(radians) * radius);
        }
        GL11.glEnd();
        postRender();
    }
    
    public static void preRender() {
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
    }
    
    public static void postRender() {
        GL11.glEnable(3553);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
