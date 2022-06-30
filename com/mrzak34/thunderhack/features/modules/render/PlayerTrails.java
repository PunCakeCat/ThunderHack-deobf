//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PlayerTrails extends Module
{
    ArrayList<Point> points;
    private Setting<colorModeEn> colorMode;
    public final Setting<ColorSetting> onecolor;
    public final Setting<ColorSetting> twocolor;
    public Setting<Integer> removeticks;
    public Setting<Integer> alpha;
    public Setting<Float> saturation;
    public final Setting<ColorSetting> puls1;
    public final Setting<ColorSetting> puls2;
    
    public PlayerTrails() {
        super("PlayerTrails", "\u0420\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u0442\u0440\u0435\u0439\u043b\u044b-\u0441\u0437\u0430\u0434\u0438 \u0438\u0433\u0440\u043e\u043a\u0430", Module.Category.RENDER, true, false, false);
        this.points = new ArrayList<Point>();
        this.colorMode = (Setting<colorModeEn>)this.register(new Setting("Trails Color", (T)colorModeEn.Astolfo));
        this.onecolor = (Setting<ColorSetting>)this.register(new Setting("One Color", (T)new ColorSetting(1354711231), v -> this.colorMode.getValue() == colorModeEn.Static || this.colorMode.getValue() == colorModeEn.Custom));
        this.twocolor = (Setting<ColorSetting>)this.register(new Setting("Two Color", (T)new ColorSetting(1354711231), v -> this.colorMode.getValue() == colorModeEn.Custom));
        this.removeticks = (Setting<Integer>)this.register(new Setting("Remove Ticks", (T)50, (T)50, (T)500));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)1, (T)255));
        this.saturation = (Setting<Float>)this.register(new Setting("Saturation", (T)0.7f, (T)0.1f, (T)1.0f, v -> this.colorMode.getValue() == colorModeEn.Astolfo));
        this.puls1 = (Setting<ColorSetting>)this.register(new Setting("pulse1", (T)new ColorSetting(1024970471)));
        this.puls2 = (Setting<ColorSetting>)this.register(new Setting("pulse1", (T)new ColorSetting(1354711231)));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        this.points.removeIf(p -> p.age >= this.removeticks.getValue());
        final float x = (float)(PlayerTrails.mc.player.lastTickPosX + (PlayerTrails.mc.player.posX - PlayerTrails.mc.player.lastTickPosX) * event.getPartialTicks());
        final float y = (float)(PlayerTrails.mc.player.lastTickPosY + (PlayerTrails.mc.player.posY - PlayerTrails.mc.player.lastTickPosY) * event.getPartialTicks());
        final float z = (float)(PlayerTrails.mc.player.lastTickPosZ + (PlayerTrails.mc.player.posZ - PlayerTrails.mc.player.lastTickPosZ) * event.getPartialTicks());
        this.points.add(new Point(x, y, z));
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2884);
        for (final Point t : this.points) {
            if (this.points.indexOf(t) >= this.points.size() - 1) {
                continue;
            }
            final Point temp = this.points.get(this.points.indexOf(t) + 1);
            Color color = Color.WHITE;
            final Color firstcolor = new Color(this.onecolor.getValue().getColor());
            switch (this.colorMode.getValue()) {
                case Astolfo: {
                    color = RenderUtil.astolfoColors45(t.age - t.age + 1.0f, t.age, this.saturation.getValue(), 10.0f);
                    break;
                }
                case Pulse: {
                    color = RenderUtil.TwoColoreffect(this.puls1.getValue().getColorObject(), this.puls2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0f * (t.age / 16.0f) / 60.0f);
                    break;
                }
                case Custom: {
                    color = RenderUtil.TwoColoreffect(new Color(this.onecolor.getValue().getColor()), new Color(this.twocolor.getValue().getColor()), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0f * (t.age / 16.0f) / 60.0f);
                    break;
                }
                case Static: {
                    color = firstcolor;
                    break;
                }
            }
            final Color c = RenderUtil.setAlpha(color, (t.age < 2.0f) ? ((int)this.alpha.getValue()) : ((int)(this.alpha.getValue() * (1.0f - t.age / this.removeticks.getValue()))));
            GL11.glBegin(8);
            final double x2 = t.x - PlayerTrails.mc.getRenderManager().renderPosX;
            final double y2 = t.y - PlayerTrails.mc.getRenderManager().renderPosY;
            final double z2 = t.z - PlayerTrails.mc.getRenderManager().renderPosZ;
            final double x3 = temp.x - PlayerTrails.mc.getRenderManager().renderPosX;
            final double y3 = temp.y - PlayerTrails.mc.getRenderManager().renderPosY;
            final double z3 = temp.z - PlayerTrails.mc.getRenderManager().renderPosZ;
            RenderUtil.glColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (t.age < 2.0f) ? this.alpha.getValue() : ((int)(this.alpha.getValue() * (1.0f - t.age / this.removeticks.getValue())))));
            GL11.glVertex3d(x2, y2 + PlayerTrails.mc.player.height - 0.1, z2);
            RenderUtil.glColor(c.getRGB());
            GL11.glVertex3d(x2, y2 + 0.2, z2);
            GL11.glVertex3d(x3, y3 + PlayerTrails.mc.player.height - 0.1, z3);
            GL11.glVertex3d(x3, y3 + 0.2, z3);
            GL11.glEnd();
            final Point point = t;
            ++point.age;
        }
        GlStateManager.resetColor();
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public void onDisable() {
        this.points.clear();
        super.onDisable();
    }
    
    public static Color getGradientOffset(final Color color1, final Color color2, double offset, final int alpha) {
        if (offset > 1.0) {
            final double left = offset % 1.0;
            final int off = (int)offset;
            offset = ((off % 2 == 0) ? left : (1.0 - left));
        }
        final double inverse_percent = 1.0 - offset;
        final int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offset);
        final int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        final int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart, alpha);
    }
    
    public enum colorModeEn
    {
        Pulse, 
        Custom, 
        Astolfo, 
        Static;
    }
    
    class Point
    {
        public final float x;
        public final float y;
        public final float z;
        public float age;
        
        public Point(final float x, final float y, final float z) {
            this.age = 0.0f;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
