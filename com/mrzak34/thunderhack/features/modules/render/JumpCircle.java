//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import java.util.*;

public class JumpCircle extends Module
{
    public final Setting<ColorSetting> color;
    public Timer timer;
    static List circles;
    private Setting<rmode> rMode;
    
    public JumpCircle() {
        super("JumpCircle", "JumpCircle", Module.Category.RENDER, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.timer = new Timer();
        this.rMode = (Setting<rmode>)this.register(new Setting("Mode", (T)rmode.Default));
    }
    
    public void onUpdate() {
        if (JumpCircle.mc.player.motionY == 0.33319999363422365) {
            this.handleEntityJump((Entity)JumpCircle.mc.player);
        }
        this.onLocalPlayerUpdate(JumpCircle.mc.player);
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        final EntityPlayerSP client = Minecraft.getMinecraft().player;
        final Minecraft mc = Minecraft.getMinecraft();
        final double ix = -(client.lastTickPosX + (client.posX - client.lastTickPosX) * mc.getRenderPartialTicks());
        final double iy = -(client.lastTickPosY + (client.posY - client.lastTickPosY) * mc.getRenderPartialTicks());
        final double iz = -(client.lastTickPosZ + (client.posZ - client.lastTickPosZ) * mc.getRenderPartialTicks());
        if (this.rMode.getValue() == rmode.Disc) {
            GL11.glPushMatrix();
            GL11.glTranslated(ix, iy, iz);
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(3008);
            GL11.glDisable(2929);
            GL11.glBlendFunc(770, 771);
            GL11.glShadeModel(7425);
            Collections.reverse(JumpCircle.circles);
            try {
                for (final Circle c : JumpCircle.circles) {
                    final float k = Circle.existed / 20.0f;
                    final double x = c.position().x;
                    final double y = c.position().y - k * 0.5;
                    final double z = c.position().z;
                    final float start = k;
                    final float end = k + 1.0f - k;
                    GL11.glBegin(8);
                    for (int i = 0; i <= 360; i += 5) {
                        GL11.glColor4f((float)c.color().x, (float)c.color().y, (float)c.color().z, 0.2f * (1.0f - Circle.existed / 20.0f));
                        GL11.glVertex3d(x + Math.cos(Math.toRadians(i * 4)) * start, y, z + Math.sin(Math.toRadians(i * 4)) * start);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.01f * (1.0f - Circle.existed / 20.0f));
                        GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * end, y + Math.sin(k * 8.0f) * 0.5, z + Math.sin(Math.toRadians(i) * end));
                    }
                    GL11.glEnd();
                }
            }
            catch (Exception ex) {}
            Collections.reverse(JumpCircle.circles);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glShadeModel(7424);
            GL11.glEnable(2884);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            GL11.glPopMatrix();
        }
        else if (this.rMode.getValue() == rmode.Default) {
            GL11.glPushMatrix();
            GL11.glTranslated(ix, iy, iz);
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glShadeModel(7425);
            Collections.reverse(JumpCircle.circles);
            for (final Circle c : JumpCircle.circles) {
                final double x2 = c.position().x;
                final double y2 = c.position().y;
                final double z2 = c.position().z;
                final float j = Circle.existed / 20.0f;
                final float start = j * 2.5f;
                final float end = start + 1.0f - j;
                GL11.glBegin(8);
                for (int i = 0; i <= 360; i += 5) {
                    GL11.glColor4f((float)c.color().x, (float)c.color().y, (float)c.color().z, 0.7f * (1.0f - Circle.existed / 20.0f));
                    switch (false) {
                        case 0: {
                            GL11.glVertex3d(x2 + Math.cos(Math.toRadians(i)) * start, y2, z2 + Math.sin(Math.toRadians(i)) * start);
                            break;
                        }
                        case 1: {
                            GL11.glVertex3d(x2 + Math.cos(Math.toRadians(i * 2)) * start, y2, z2 + Math.sin(Math.toRadians(i * 2)) * start);
                            break;
                        }
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.01f * (1.0f - Circle.existed / 20.0f));
                    switch (false) {
                        case 0: {
                            GL11.glVertex3d(x2 + Math.cos(Math.toRadians(i)) * end, y2, z2 + Math.sin(Math.toRadians(i)) * end);
                            break;
                        }
                        case 1: {
                            GL11.glVertex3d(x2 + Math.cos(Math.toRadians(-i)) * end, y2, z2 + Math.sin(Math.toRadians(-i)) * end);
                            break;
                        }
                    }
                }
                GL11.glEnd();
            }
            Collections.reverse(JumpCircle.circles);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glShadeModel(7424);
            GL11.glEnable(2884);
            GL11.glEnable(3008);
            GL11.glPopMatrix();
        }
    }
    
    public void onLocalPlayerUpdate(final EntityPlayerSP instance) {
        JumpCircle.circles.removeIf(Circle::update);
    }
    
    public void handleEntityJump(final Entity entity) {
        final int red = (int)((this.color.getValue().getColorObject().getRGB() >> 16 & 0xFF) / 100.0f);
        final int green = (int)((this.color.getValue().getColorObject().getRGB() >> 8 & 0xFF) / 100.0f);
        final int blue = (int)((this.color.getValue().getColorObject().getRGB() & 0xFF) / 100.0f);
        final Vec3d color = new Vec3d((double)red, (double)green, (double)blue);
        JumpCircle.circles.add(new Circle(entity.getPositionVector(), color));
        Circle.existed = 0;
    }
    
    static {
        JumpCircle.circles = new ArrayList();
    }
    
    public enum rmode
    {
        Disc, 
        Default;
    }
    
    static class Circle
    {
        private final Vec3d vec;
        private final Vec3d color;
        static byte existed;
        
        Circle(final Vec3d vec, final Vec3d color) {
            this.vec = vec;
            this.color = color;
        }
        
        Vec3d position() {
            return this.vec;
        }
        
        Vec3d color() {
            return this.color;
        }
        
        public static boolean update(final Object o) {
            return ++Circle.existed > 20;
        }
    }
}
