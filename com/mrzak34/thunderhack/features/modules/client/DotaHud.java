//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.helper.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import org.lwjgl.opengl.*;

public class DotaHud extends Module
{
    public Setting<Integer> imageScaleXp;
    public Setting<Integer> imageScaleYP;
    public Setting<Integer> imageScaleX;
    public Setting<Integer> imageScaleY;
    public Setting<Integer> trans;
    public Setting<Integer> cringe;
    private final ResourceLocation hud_pic;
    public Setting<Integer> imageScaleX22;
    public Setting<Integer> imageScaleY22;
    public Setting<Integer> imageScaleX2;
    public Setting<Integer> imageScaleY2;
    public Setting<Integer> imageScaleX12;
    public Setting<Integer> imageScaleY12;
    public Setting<Integer> width2;
    public Setting<Integer> test;
    public Setting<Boolean> yw;
    public Setting<Boolean> pch;
    
    public DotaHud() {
        super("DotaHud", "Kills aura.", Category.CLIENT, true, true, false);
        this.imageScaleXp = (Setting<Integer>)this.register(new Setting("ImageScaleXp", (T)160, (T)0, (T)512));
        this.imageScaleYP = (Setting<Integer>)this.register(new Setting("ImageScaleYp", (T)160, (T)0, (T)512));
        this.imageScaleX = (Setting<Integer>)this.register(new Setting("X", (T)160, (T)0, (T)400));
        this.imageScaleY = (Setting<Integer>)this.register(new Setting("Y", (T)160, (T)0, (T)200));
        this.trans = (Setting<Integer>)this.register(new Setting("Trans", (T)160, (T)0, (T)255));
        this.cringe = (Setting<Integer>)this.register(new Setting("xxx", (T)16, (T)0, (T)40));
        this.hud_pic = new ResourceLocation("textures/dotahud.png");
        this.imageScaleX22 = (Setting<Integer>)this.register(new Setting("X22", (T)160, (T)(-200), (T)100));
        this.imageScaleY22 = (Setting<Integer>)this.register(new Setting("X22", (T)160, (T)(-200), (T)1000));
        this.imageScaleX2 = (Setting<Integer>)this.register(new Setting("X2", (T)160, (T)(-200), (T)100));
        this.imageScaleY2 = (Setting<Integer>)this.register(new Setting("Y2", (T)160, (T)0, (T)1000));
        this.imageScaleX12 = (Setting<Integer>)this.register(new Setting("X12", (T)160, (T)0, (T)512));
        this.imageScaleY12 = (Setting<Integer>)this.register(new Setting("Y12", (T)160, (T)0, (T)400));
        this.width2 = (Setting<Integer>)this.register(new Setting("width", (T)160, (T)0, (T)512));
        this.test = (Setting<Integer>)this.register(new Setting("width", (T)1, (T)0, (T)20));
        this.yw = (Setting<Boolean>)this.register(new Setting("yaw", (T)true));
        this.pch = (Setting<Boolean>)this.register(new Setting("pitch", (T)true));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(DotaHud.mc);
        Gui.drawRect(400, 400, 400, 400, new Color(252, 252, 252, this.trans.getValue()).getRGB());
        Util.mc.getTextureManager().bindTexture(this.hud_pic);
        drawCompleteImage(this.imageScaleX.getValue(), this.imageScaleY.getValue(), this.imageScaleXp.getValue(), this.imageScaleYP.getValue());
        final int i = this.imageScaleX.getValue() + this.imageScaleX22.getValue();
        int iteration = 0;
        final int y = this.imageScaleY.getValue() + this.imageScaleY22.getValue();
        for (final ItemStack is : DotaHud.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * this.cringe.getValue() + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(DotaHud.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            this.renderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.renderer.getStringWidth(s)), (float)(y + 9), 16777215);
        }
        if (DotaHud.mc.player != null && DotaHud.mc.world != null) {
            drawPlayerOnScreen(this.imageScaleX.getValue() + this.imageScaleX2.getValue(), this.imageScaleY.getValue() + this.imageScaleY2.getValue(), 50, -30.0f, 0.0f, (EntityPlayer)Minecraft.getMinecraft().player, this.yw.getValue(), this.pch.getValue());
        }
        if (DotaHud.mc.player != null && DotaHud.mc.player.getTotalArmorValue() != 0) {
            RenderUtil.drawSmoothRect((float)(this.imageScaleX.getValue() + this.imageScaleX12.getValue()), (float)(this.imageScaleY.getValue() + this.imageScaleY12.getValue()), (float)(this.imageScaleX.getValue() + this.imageScaleX12.getValue() + this.width2.getValue()), this.imageScaleY.getValue() + this.imageScaleY12.getValue() + 10.0f, new Color(25, 25, 35, 255).getRGB());
            RenderUtil.drawSmoothRect((float)(this.imageScaleX.getValue() + this.imageScaleX12.getValue()), (float)(this.imageScaleY.getValue() + this.imageScaleY12.getValue()), this.imageScaleX.getValue() + this.imageScaleX12.getValue() + DotaHud.mc.player.getTotalArmorValue() / 20.0f * this.width2.getValue(), this.imageScaleY.getValue() + this.imageScaleY12.getValue() + 10.0f, new Color(77, 128, 255, 255).getRGB());
        }
        if (DotaHud.mc.player != null) {
            RenderUtil.drawSmoothRect((float)(this.imageScaleX.getValue() + this.imageScaleX12.getValue()), this.imageScaleY.getValue() + this.imageScaleY12.getValue() + 15.0f, (float)(this.imageScaleX.getValue() + this.imageScaleX12.getValue() + this.width2.getValue()), this.imageScaleY.getValue() + this.imageScaleY12.getValue() + 10.0f + 15.0f, new Color(25, 25, 35, 255).getRGB());
            RenderUtil.drawSmoothRect((float)(this.imageScaleX.getValue() + this.imageScaleX12.getValue()), this.imageScaleY.getValue() + this.imageScaleY12.getValue() + 15.0f, this.imageScaleX.getValue() + this.imageScaleX12.getValue() + DotaHud.mc.player.getHealth() / 36.0f * this.width2.getValue(), this.imageScaleY.getValue() + this.imageScaleY12.getValue() + 10.0f + 15.0f, PaletteHelper.fadeColor(new Color(255, 2, 2, 255).getRGB(), new Color(2, 255, 23, 255).getRGB(), DotaHud.mc.player.getHealth() / 36.0f));
        }
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
    
    public static void drawCompleteImage(final float posX, final float posY, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f((float)width, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f((float)width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}
