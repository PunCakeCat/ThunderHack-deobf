//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import com.mrzak34.thunderhack.helper.*;

public class PyroRadar extends Module
{
    public Setting<Float> scalex;
    public Setting<Float> scaley;
    public Setting<Float> scalez;
    public Setting<Float> scale;
    public Setting<Float> changeradius;
    public Setting<Float> distance;
    public Setting<Boolean> comps;
    public Setting<Boolean> unlockTilt;
    public Setting<Integer> tilt;
    public Setting<Boolean> items;
    public Setting<Boolean> players;
    public Setting<Boolean> hidefrustum;
    public Setting<Boolean> other;
    public Setting<Boolean> bosses;
    public Setting<Boolean> hostiles;
    public Setting<Boolean> friends;
    private Setting<mode2> Mode2;
    private Setting<mode> Mode;
    public Setting<Integer> redP;
    public Setting<Integer> greenP;
    public Setting<Integer> blueP;
    public Setting<Integer> redF;
    public Setting<Integer> greenF;
    public Setting<Integer> blueF;
    private static final double half_pi = 1.5707963267948966;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean dock;
    public static TextManager renderer;
    public Setting<Integer> compassScale;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Integer> redci;
    public Setting<Integer> greenci;
    public Setting<Integer> blueci;
    public Setting<Integer> alphaci;
    public Setting<Integer> correctx;
    public Setting<Integer> correcty;
    public Setting<Integer> correctz;
    public Setting<Integer> angle2;
    public Setting<Integer> lockPitch;
    public Setting<Integer> lockPitch2;
    public Setting<Float> radius;
    
    public PyroRadar() {
        super("Radar", "\u0440\u0430\u0434\u0430\u0440 \u0438\u0437 \u043f\u0430\u0439\u0440\u043e", Module.Category.HUD, true, false, false);
        this.scalex = (Setting<Float>)this.register(new Setting("XXX", (T)1.0f, (T)(-5.0f), (T)5.08f));
        this.scaley = (Setting<Float>)this.register(new Setting("YYY", (T)1.0f, (T)(-5.0f), (T)5.08f));
        this.scalez = (Setting<Float>)this.register(new Setting("ZZZ", (T)1.0f, (T)(-5.0f), (T)5.08f));
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)1.0f, (T)0.0f, (T)10.08f));
        this.changeradius = (Setting<Float>)this.register(new Setting("changeradius", (T)75.0f, (T)0.0f, (T)100.0f));
        this.distance = (Setting<Float>)this.register(new Setting("distance", (T)1.0f, (T)0.0f, (T)10.0f));
        this.comps = (Setting<Boolean>)this.register(new Setting("CompAss", (T)false));
        this.unlockTilt = (Setting<Boolean>)this.register(new Setting("Unlock Tilt", (T)false));
        this.tilt = (Setting<Integer>)this.register(new Setting("Tilt", (T)50, (T)(-90), (T)90));
        this.items = (Setting<Boolean>)this.register(new Setting("items", (T)false));
        this.players = (Setting<Boolean>)this.register(new Setting("players", (T)false));
        this.hidefrustum = (Setting<Boolean>)this.register(new Setting("HideInFrustrum", (T)false));
        this.other = (Setting<Boolean>)this.register(new Setting("other", (T)false));
        this.bosses = (Setting<Boolean>)this.register(new Setting("bosses", (T)false));
        this.hostiles = (Setting<Boolean>)this.register(new Setting("hostiles", (T)false));
        this.friends = (Setting<Boolean>)this.register(new Setting("friends", (T)false));
        this.Mode2 = (Setting<mode2>)this.register(new Setting("Circle CMode", (T)mode2.Rainbow));
        this.Mode = (Setting<mode>)this.register(new Setting("Compass Mode", (T)mode.XY));
        this.redP = (Setting<Integer>)this.register(new Setting("RedFRIENDS", (T)0, (T)0, (T)255));
        this.greenP = (Setting<Integer>)this.register(new Setting("GreenFRIENDS", (T)200, (T)0, (T)255));
        this.blueP = (Setting<Integer>)this.register(new Setting("BlueFRIENDS", (T)60, (T)0, (T)255));
        this.redF = (Setting<Integer>)this.register(new Setting("Red", (T)200, (T)0, (T)255));
        this.greenF = (Setting<Integer>)this.register(new Setting("Green", (T)120, (T)0, (T)255));
        this.blueF = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255));
        this.dock = true;
        this.compassScale = (Setting<Integer>)this.register(new Setting("Compass Scale", (T)16, (T)1, (T)60));
        this.red = (Setting<Integer>)this.register(new Setting("redCompass", (T)160, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("greenCompass", (T)160, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("blueCompass", (T)160, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("alphaCompass", (T)255, (T)0, (T)255));
        this.redci = (Setting<Integer>)this.register(new Setting("redCirc", (T)160, (T)0, (T)255));
        this.greenci = (Setting<Integer>)this.register(new Setting("greenCirc", (T)160, (T)0, (T)255));
        this.blueci = (Setting<Integer>)this.register(new Setting("blueCirc", (T)160, (T)0, (T)255));
        this.alphaci = (Setting<Integer>)this.register(new Setting("alphaCirc", (T)255, (T)0, (T)255));
        this.correctx = (Setting<Integer>)this.register(new Setting("XCompass", (T)16, (T)(-30), (T)30));
        this.correcty = (Setting<Integer>)this.register(new Setting("YCompass", (T)16, (T)(-30), (T)30));
        this.correctz = (Setting<Integer>)this.register(new Setting("PitchCompass", (T)30, (T)(-60), (T)60));
        this.angle2 = (Setting<Integer>)this.register(new Setting("AngleCompass", (T)(-1), (T)(-180), (T)(-1)));
        this.lockPitch = (Setting<Integer>)this.register(new Setting("LockPitchCompassA", (T)30, (T)(-60), (T)60));
        this.lockPitch2 = (Setting<Integer>)this.register(new Setting("LockPitchCompassB", (T)30, (T)(-60), (T)60));
        this.radius = (Setting<Float>)this.register(new Setting("radiusCompass", (T)1.8f, (T)0.1f, (T)40.0f));
    }
    
    public Rotation getRotation(final Vec3d vec3d, final Vec3d vec3d2) {
        final double d = vec3d2.x - vec3d.x;
        final double d2 = vec3d2.y - vec3d.y;
        final double d3 = vec3d2.z - vec3d.z;
        final double d4 = MathHelper.sqrt(d * d + d3 * d3);
        return new Rotation(MathHelper.wrapDegrees((float)Math.toDegrees(MathHelper.atan2(d3, d)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(MathHelper.atan2(d2, d4)))));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        GL11.glPushMatrix();
        GlStateManager.translate((float)this.scalex.getValue(), (float)this.scaley.getValue(), (float)this.scalez.getValue());
        if (PyroRadar.mc.player != null) {
            final Entity entity = PyroRadar.mc.getRenderViewEntity();
            if (entity == null) {}
            final Entity entity2 = PyroRadar.mc.getRenderViewEntity();
            if (entity2 == null) {}
            final Entity entity3 = PyroRadar.mc.getRenderViewEntity();
            if (entity3 == null) {}
            RenderUtil.camera.setPosition(entity.posX, entity2.posY, entity3.posZ);
            GlStateManager.pushMatrix();
            final WorldClient worldClient = Util.mc.world;
            if (worldClient == null) {}
            for (final Entity entity4 : worldClient.loadedEntityList) {
                if (entity4 == PyroRadar.mc.getRenderViewEntity()) {
                    continue;
                }
                if (this.hidefrustum.getValue() && RenderUtil.camera.isBoundingBoxInFrustum(entity4.getEntityBoundingBox())) {
                    continue;
                }
                if (entity4 instanceof EntityPlayer) {
                    if (this.friends.getValue() && Thunderhack.friendManager.isFriend((EntityPlayer)entity4)) {
                        this.drawFriendArrow(entity4);
                    }
                    else {
                        if (!this.players.getValue() && Thunderhack.friendManager.isFriend((EntityPlayer)entity4)) {
                            continue;
                        }
                        this.drawArrow(entity4);
                    }
                }
                else {
                    if (!(entity4 instanceof EntityDragon)) {
                        if (!(entity4 instanceof EntityWither)) {
                            if (entity4.isCreatureType(EnumCreatureType.MONSTER, false)) {
                                if (!this.hostiles.getValue()) {
                                    continue;
                                }
                                this.drawArrow(entity4);
                                continue;
                            }
                            else if (entity4 instanceof EntityItem) {
                                if (!this.items.getValue()) {
                                    continue;
                                }
                                this.drawArrow(entity4);
                                continue;
                            }
                            else {
                                if (!this.other.getValue()) {
                                    continue;
                                }
                                this.drawArrow(entity4);
                                continue;
                            }
                        }
                    }
                    if (!this.bosses.getValue()) {
                        continue;
                    }
                    this.drawArrow(entity4);
                }
            }
            GlStateManager.popMatrix();
            GlStateManager.translate(1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
    
    public Vec3d getEntityVector(final Entity entity) {
        final RenderManager renderManager = PyroRadar.mc.getRenderManager();
        final double d = this.c(entity.posX, entity.lastTickPosX) - PyroRadar.mc.player.getPosition().getX();
        final RenderManager renderManager2 = PyroRadar.mc.getRenderManager();
        final double d2 = this.c(entity.posY, entity.lastTickPosY) - PyroRadar.mc.player.getPosition().getY();
        final RenderManager renderManager3 = PyroRadar.mc.getRenderManager();
        final double d3 = this.c(entity.posZ, entity.lastTickPosZ) - PyroRadar.mc.player.getPosition().getZ();
        return new Vec3d(d, d2, d3);
    }
    
    public void arrow(final float f, final float f2, final float f3, final float f4) {
        GlStateManager.glBegin(6);
        GlStateManager.glVertex3f(f, f2, f3);
        GlStateManager.glVertex3f(f + 0.1f * f4, f2, f3 - 0.2f * f4);
        GlStateManager.glVertex3f(f, f2, f3 - 0.12f * f4);
        GlStateManager.glVertex3f(f - 0.1f * f4, f2, f3 - 0.2f * f4);
        GlStateManager.glEnd();
    }
    
    public void drawArrow(final Entity var1x) {
        if (PyroRadar.mc.entityRenderer != null) {
            final Rotation var8 = this.getRotation(Vec3d.ZERO, this.getEntityVector(var1x));
            float var9 = var8.meth2();
            float var10 = 180.0f - var9;
            Entity var11 = PyroRadar.mc.getRenderViewEntity();
            var9 = var10 + var11.rotationYaw;
            final Vec3d var12 = new Vec3d(0.0, 0.0, 1.0).rotateYaw((float)Math.toRadians(var9)).rotatePitch((float)Math.toRadians(180.0));
            GlStateManager.blendFunc(770, 771);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            final float var13 = (float)(var1x.getDistance(PyroRadar.mc.getRenderViewEntity()) / this.changeradius.getValue().doubleValue());
            GlStateManager.color((float)this.redF.getValue(), (float)this.greenF.getValue(), (float)this.blueF.getValue());
            GlStateManager.disableLighting();
            GlStateManager.loadIdentity();
            final EntityRenderer var14 = PyroRadar.mc.entityRenderer;
            if (var14 == null) {
                System.out.println("null cannot be cast to non-null type dev.nuker.pyro.mixin.EntityRendererAccessor");
            }
            else {
                ((IEntityRenderer)var14).orientCam(PyroRadar.mc.getRenderPartialTicks());
                final float var15 = (float)this.scale.getValue().doubleValue() * 0.2f;
                final float var16 = (float)this.distance.getValue().doubleValue() * 0.2f;
                var11 = PyroRadar.mc.getRenderViewEntity();
                GlStateManager.translate(0.0f, var11.getEyeHeight(), 0.0f);
                Entity var17 = PyroRadar.mc.getRenderViewEntity();
                GlStateManager.rotate(-var17.rotationYaw, 0.0f, 1.0f, 0.0f);
                var17 = PyroRadar.mc.getRenderViewEntity();
                GlStateManager.rotate(var17.rotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, 1.0f);
                float var18 = (float)this.tilt.getValue().intValue();
                if (this.unlockTilt.getValue()) {
                    var10 = 90.0f;
                    var11 = PyroRadar.mc.getRenderViewEntity();
                    if (var10 - var11.rotationPitch < var18) {
                        var10 = 90.0f;
                        var11 = PyroRadar.mc.getRenderViewEntity();
                        var18 = var10 - var11.rotationPitch;
                    }
                }
                GlStateManager.rotate(var18, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(var9, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, var16);
                final float var19 = Float.valueOf(String.valueOf(this.scale.getValue())) * var15 * 2.0f;
                this.arrow((float)var12.x, (float)var12.y, (float)var12.z, var19);
                GlStateManager.enableTexture2D();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableLighting();
            }
        }
    }
    
    public void drawFriendArrow(final Entity var1x) {
        if (PyroRadar.mc.entityRenderer != null) {
            final Rotation var8 = this.getRotation(Vec3d.ZERO, this.getEntityVector(var1x));
            float var9 = var8.meth2();
            float var10 = 180.0f - var9;
            Entity var11 = PyroRadar.mc.getRenderViewEntity();
            var9 = var10 + var11.rotationYaw;
            final Vec3d var12 = new Vec3d(0.0, 0.0, 1.0).rotateYaw((float)Math.toRadians(var9)).rotatePitch((float)Math.toRadians(180.0));
            GlStateManager.blendFunc(770, 771);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            final float var13 = (float)(var1x.getDistance(PyroRadar.mc.getRenderViewEntity()) / this.changeradius.getValue().doubleValue());
            GlStateManager.color((float)this.redP.getValue(), (float)this.greenP.getValue(), (float)this.blueP.getValue());
            GlStateManager.disableLighting();
            GlStateManager.loadIdentity();
            final EntityRenderer var14 = PyroRadar.mc.entityRenderer;
            if (var14 == null) {
                System.out.println("null cannot be cast to non-null type dev.nuker.pyro.mixin.EntityRendererAccessor");
            }
            else {
                ((IEntityRenderer)var14).orientCam(PyroRadar.mc.getRenderPartialTicks());
                final float var15 = (float)this.scale.getValue().doubleValue() * 0.2f;
                final float var16 = (float)this.distance.getValue().doubleValue() * 0.2f;
                var11 = PyroRadar.mc.getRenderViewEntity();
                GlStateManager.translate(0.0f, var11.getEyeHeight(), 0.0f);
                Entity var17 = PyroRadar.mc.getRenderViewEntity();
                GlStateManager.rotate(-var17.rotationYaw, 0.0f, 1.0f, 0.0f);
                var17 = PyroRadar.mc.getRenderViewEntity();
                GlStateManager.rotate(var17.rotationPitch, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, 1.0f);
                float var18 = (float)this.tilt.getValue().intValue();
                if (this.unlockTilt.getValue()) {
                    var10 = 90.0f;
                    var11 = PyroRadar.mc.getRenderViewEntity();
                    if (var10 - var11.rotationPitch < var18) {
                        var10 = 90.0f;
                        var11 = PyroRadar.mc.getRenderViewEntity();
                        var18 = var10 - var11.rotationPitch;
                    }
                }
                GlStateManager.rotate(var18, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(var9, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, var16);
                final float var19 = Float.valueOf(String.valueOf(this.scale.getValue())) * var15 * 2.0f;
                this.arrow((float)var12.x, (float)var12.y, (float)var12.z, var19);
                GlStateManager.enableTexture2D();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableLighting();
            }
        }
    }
    
    public double c(final double d, final double d2) {
        return d2 + (d - d2) * PyroRadar.mc.getRenderPartialTicks();
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        if (this.comps.getValue()) {
            final int xyi = Display.getWidth() / 2 / ((PyroRadar.mc.gameSettings.guiScale == 0) ? 1 : PyroRadar.mc.gameSettings.guiScale);
            final int yi = Display.getHeight() / 2 / ((PyroRadar.mc.gameSettings.guiScale == 0) ? 1 : PyroRadar.mc.gameSettings.guiScale);
            final int r = this.red.getValue();
            final int g = this.green.getValue();
            final int b = this.blue.getValue();
            final int a = this.alpha.getValue();
            final int x = Display.getWidth() / 2 / ((PyroRadar.mc.gameSettings.guiScale == 0) ? 1 : PyroRadar.mc.gameSettings.guiScale);
            final int y = Display.getHeight() / 2 / ((PyroRadar.mc.gameSettings.guiScale == 0) ? 1 : PyroRadar.mc.gameSettings.guiScale);
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            GL11.glRotatef(clamp2(PyroRadar.mc.player.rotationPitch + this.angle2.getValue(), -this.lockPitch2.getValue(), 90.0f), 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef((float)(-x), (float)(-y), 0.0f);
            RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue(), false, new Color(r, g, b, a));
            if (this.Mode2.getValue() == mode2.Custom) {
                RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue() - 2.0f, false, new Color(this.redci.getValue(), this.greenci.getValue(), this.blueci.getValue(), this.alphaci.getValue()));
                RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue() - 2.5f, false, new Color(this.redci.getValue(), this.greenci.getValue(), this.blueci.getValue(), this.alphaci.getValue()));
            }
            if (this.Mode2.getValue() == mode2.Rainbow) {
                RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue() - 2.0f, false, PaletteHelper.rainbow(300, 1.0f, 1.0f));
                RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue() - 2.5f, false, PaletteHelper.rainbow(300, 1.0f, 1.0f));
            }
            if (this.Mode2.getValue() == mode2.Astolfo) {
                RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue() - 2.0f, false, PaletteHelper.astolfo(false, 1));
                RenderHelper.drawCircleCompass(-(int)PyroRadar.mc.player.rotationYaw, (float)(x + this.correctx.getValue() + 4), (float)(y + this.correcty.getValue() + 5), this.radius.getValue() - 2.5f, false, PaletteHelper.astolfo(false, 1));
            }
            GL11.glTranslatef((float)x, (float)y, 0.0f);
            GL11.glRotatef(-clamp2(PyroRadar.mc.player.rotationPitch + this.angle2.getValue(), -this.lockPitch2.getValue(), 90.0f), 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef((float)(-x), (float)(-y), 0.0f);
            for (final Direction dir : Direction.values()) {
                final double rad = this.get_pos_on_compass(dir);
                if (this.Mode.getValue() == mode.XY) {
                    if (dir.name().equals("N")) {
                        this.create_line("-Z ", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                    if (dir.name().equals("W")) {
                        this.create_line("-X ", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                    if (dir.name().equals("S")) {
                        this.create_line(" Z ", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                    if (dir.name().equals("E")) {
                        this.create_line(" X ", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                }
                else {
                    if (dir.name().equals("N")) {
                        this.create_line("N", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                    if (dir.name().equals("W")) {
                        this.create_line("W", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                    if (dir.name().equals("S")) {
                        this.create_line("S", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                    if (dir.name().equals("E")) {
                        this.create_line("E", (int)(this.docking(xyi + this.correctx.getValue(), dir.name()) + this.get_x(rad)), (int)this.get_y(rad) + yi + this.correcty.getValue(), r, g, b, a);
                    }
                }
            }
            this.set_width(50);
            this.set_height(50);
        }
    }
    
    public void set_width(final int width) {
        this.width = width;
    }
    
    public int docking(final int position_x, final String string) {
        if (this.dock) {
            return position_x;
        }
        return this.width - this.get(string, "width") - position_x;
    }
    
    public void set_height(final int height) {
        this.height = height;
    }
    
    protected int get(final String string, final String type) {
        int value_to_request = 0;
        if (type.equals("width")) {
            value_to_request = Util.fr.getStringWidth(string);
        }
        return value_to_request;
    }
    
    protected void create_line(final String string, final int pos_x, final int pos_y) {
        this.draw_string(string, this.x + pos_x, this.y + pos_y, 255, 255, 255, 255);
    }
    
    protected void create_line(final String string, final int pos_x, final int pos_y, final int r, final int g, final int b, final int a) {
        this.draw_string(string, this.x + pos_x, this.y + pos_y, r, g, b, a);
    }
    
    public void draw_string(final String string, final int x, final int y, final int r, final int g, final int b, final int a) {
        PyroRadar.renderer.drawStringForCompass(string, (float)x, (float)y, new Color(r, g, b, a).getRGB(), false);
    }
    
    private double get_pos_on_compass(final Direction dir) {
        final double yaw = Math.toRadians(wrap(PyroRadar.mc.player.rotationYaw));
        final int index = dir.ordinal();
        return yaw + index * 1.5707963267948966;
    }
    
    public static float wrap(final float valI) {
        float val = valI % 360.0f;
        if (val >= 180.0f) {
            val -= 360.0f;
        }
        if (val < -180.0f) {
            val += 360.0f;
        }
        return val;
    }
    
    private double get_x(final double rad) {
        return Math.sin(rad) * this.compassScale.getValue();
    }
    
    public static float clamp2(final float num, final float min, final float max) {
        if (num < min) {
            return min;
        }
        return (num > max) ? max : num;
    }
    
    private double get_y(final double rad) {
        final double epic_pitch = clamp2(PyroRadar.mc.player.rotationPitch + this.correctz.getValue(), -this.lockPitch.getValue(), 90.0f);
        final double pitch_radians = Math.toRadians(epic_pitch);
        return Math.cos(rad) * Math.sin(pitch_radians) * this.compassScale.getValue();
    }
    
    static {
        PyroRadar.renderer = Thunderhack.textManager;
    }
    
    public enum mode2
    {
        Custom, 
        Rainbow, 
        Astolfo;
    }
    
    public enum mode
    {
        XY, 
        NEWS;
    }
    
    private enum Direction
    {
        N, 
        W, 
        S, 
        E;
    }
}
