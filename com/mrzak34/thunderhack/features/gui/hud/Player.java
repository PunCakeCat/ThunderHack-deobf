//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;

public class Player extends Module
{
    public Setting<Integer> scale;
    public Setting<Boolean> yw;
    public Setting<Boolean> pch;
    private final Setting<PositionSetting> pos;
    float x1;
    float y1;
    
    public Player() {
        super("PlayerView", "Player", Category.HUD, true, false, false);
        this.scale = (Setting<Integer>)this.register(new Setting("Scale", (T)50, (T)0, (T)200));
        this.yw = (Setting<Boolean>)this.register(new Setting("Yaw", (T)true));
        this.pch = (Setting<Boolean>)this.register(new Setting("Pitch", (T)true));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(Player.mc);
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        drawPlayerOnScreen((int)this.x1, (int)this.y1, this.scale.getValue(), -30.0f, 0.0f, (EntityPlayer)Minecraft.getMinecraft().player, this.yw.getValue(), this.pch.getValue());
    }
    
    public static void drawPlayerOnScreen(final int x, final int y, final int scale, float mouseX, float mouseY, final EntityPlayer player, final boolean yaw, final boolean pitch) {
        GlStateManager.pushMatrix();
        GlStateManager.enableDepth();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, 50.0f);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float f = player.renderYawOffset;
        final float f2 = player.rotationYaw;
        final float f3 = player.rotationPitch;
        final float f4 = player.prevRotationYawHead;
        final float f5 = player.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        mouseX = (yaw ? (player.rotationYaw * -1.0f) : mouseX);
        mouseY = (pitch ? (player.rotationPitch * -1.0f) : mouseY);
        GlStateManager.rotate(-(float)Math.atan(mouseY / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        if (!yaw) {
            player.renderYawOffset = (float)Math.atan(mouseX / 40.0f) * 20.0f;
            player.rotationYaw = (float)Math.atan(mouseX / 40.0f) * 40.0f;
            player.rotationYawHead = player.rotationYaw;
            player.prevRotationYawHead = player.rotationYaw;
        }
        if (!pitch) {
            player.rotationPitch = -(float)Math.atan(mouseY / 40.0f) * 20.0f;
        }
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0f);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity((Entity)player, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        rendermanager.setRenderShadow(true);
        if (!yaw) {
            player.renderYawOffset = f;
            player.rotationYaw = f2;
            player.prevRotationYawHead = f4;
            player.rotationYawHead = f5;
        }
        if (!pitch) {
            player.rotationPitch = f3;
        }
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
    }
}
