//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.features.gui.font.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.awt.*;

public class WexsideTPS extends Module
{
    public final Setting<ColorSetting> color;
    public Setting<Float> sx;
    public Setting<Float> sy;
    public Setting<Float> car;
    private final Setting<Boolean> wex;
    private final Setting<Boolean> smt;
    public Setting<Float> rounded;
    public Setting<Integer> todown;
    public Setting<Integer> lwid;
    public Setting<Float> rounded2;
    private final Setting<PositionSetting> pos;
    public Setting<Float> xrounded;
    public Setting<Float> yrounded;
    float x1;
    float y1;
    int santi;
    int status;
    
    public WexsideTPS() {
        super("WexsideTPS", "WexsideTPS", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.sx = (Setting<Float>)this.register(new Setting("ScaleX", (T)0.0f, (T)0.1f, (T)1.0f));
        this.sy = (Setting<Float>)this.register(new Setting("ScaleY", (T)0.0f, (T)0.1f, (T)1.0f));
        this.car = (Setting<Float>)this.register(new Setting("otstup", (T)0.0f, (T)0.1f, (T)1.0f));
        this.wex = (Setting<Boolean>)this.register(new Setting("test", (T)Boolean.FALSE));
        this.smt = (Setting<Boolean>)this.register(new Setting("smooth", (T)Boolean.FALSE));
        this.rounded = (Setting<Float>)this.register(new Setting("Round", (T)0.0f, (T)0.5f, (T)20.0f));
        this.todown = (Setting<Integer>)this.register(new Setting("tdown", (T)0, (T)0, (T)45));
        this.lwid = (Setting<Integer>)this.register(new Setting("lwid", (T)0, (T)0, (T)180));
        this.rounded2 = (Setting<Float>)this.register(new Setting("Round2", (T)0.0f, (T)0.5f, (T)20.0f));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.xrounded = (Setting<Float>)this.register(new Setting("xRound", (T)0.0f, (T)(-30.5f), (T)30.0f));
        this.yrounded = (Setting<Float>)this.register(new Setting("yRound", (T)0.0f, (T)(-30.5f), (T)30.0f));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.santi = 0;
        this.status = 0;
    }
    
    @Override
    public void onUpdate() {
        if (this.santi < this.status) {
            this.santi += 5;
        }
        if (this.santi > this.status) {
            this.santi -= 5;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(WexsideTPS.mc);
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        GL11.glPushMatrix();
        GL11.glScalef((float)this.sx.getValue(), (float)this.sy.getValue(), 1.0f);
        if (this.wex.getValue()) {
            RectHelper.drawRoundedRect(this.x1, this.y1, 35.0, 40.0, this.rounded.getValue(), this.color.getValue().getColorObject());
        }
        else {
            RenderUtil.drawSmoothRect(this.x1, this.y1, this.x1 + 35.0f, this.y1 + 45.0f, this.color.getValue().getRawColor());
        }
        GL11.glEnable(3042);
        if (Thunderhack.serverManager.getDoubleTPS() != 0) {
            this.status = 360 / (20 / Thunderhack.serverManager.getDoubleTPS());
        }
        else {
            this.status = 0;
        }
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue() - this.car.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue() + this.car.getValue(), 0, 360, this.lwid.getValue(), this.color.getValue().withAlpha((this.color.getValue().getAlpha() > 210) ? this.color.getValue().getAlpha() : (this.color.getValue().getAlpha() + 40)).getColorObject(), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue(), 0, this.santi, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue() - this.car.getValue(), 0, this.santi, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue() + this.car.getValue(), 0, this.santi, this.lwid.getValue(), PaletteHelper.astolfo(false, 1), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue() - this.car.getValue() * 1.5f, 0, this.santi, this.lwid.getValue(), PaletteHelper.astolfo2(), this.smt.getValue());
        drawPartialCircle(this.x1 + 17.5f, this.y1 + this.todown.getValue(), this.rounded2.getValue() + this.car.getValue() * 1.5f, 0, this.santi, this.lwid.getValue(), PaletteHelper.astolfo2(), this.smt.getValue());
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        FontRendererWrapper.drawCenteredString(String.valueOf(Thunderhack.serverManager.getDoubleTPS()), (this.x1 + this.xrounded.getValue()) / 2.0f, (this.y1 + this.yrounded.getValue()) / 2.0f, -1);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
        FontRendererWrapper.drawCenteredString("TPS", this.x1 + 17.5f, this.y1 + 3.0f, -1);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
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
