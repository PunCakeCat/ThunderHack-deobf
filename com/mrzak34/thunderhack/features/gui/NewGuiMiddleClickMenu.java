//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui;

import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.command.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.math.*;

public class NewGuiMiddleClickMenu extends GuiScreen
{
    private final EntityPlayer player;
    private final Timer timer;
    int x1;
    int y1;
    int selectedItem;
    private static final float PRECISION = 5.0f;
    
    public NewGuiMiddleClickMenu(final EntityPlayer player) {
        this.timer = new Timer();
        this.player = player;
        this.timer.reset();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        final ScaledResolution sr = new ScaledResolution(this.mc);
        drawPlayerOnScreen(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 + 50, 45, -30.0f, 0.0f, this.player, true, true);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < 3; ++i) {
            final float s = (float)(i * 120);
            final float e = (float)(i * 120 + 120);
            if (this.selectedItem == i) {
                this.drawSlice(buffer, (float)(sr.getScaledWidth() / 2), (float)(sr.getScaledHeight() / 2), 1.0f, MiddleClick.getInstance().circus.getValue(), MiddleClick.getInstance().circus.getValue() * 2.0f, s, e, 255, 255, 255, 64);
            }
            else {
                this.drawSlice(buffer, (float)(sr.getScaledWidth() / 2), (float)(sr.getScaledHeight() / 2), 1.0f, MiddleClick.getInstance().circus.getValue(), MiddleClick.getInstance().circus.getValue() * 2.0f, s, e, 0, 0, 0, 64);
            }
        }
        tessellator.draw();
        GlStateManager.popMatrix();
        if (mouseX <= sr.getScaledWidth() / 2 && mouseY <= sr.getScaledHeight() * 0.75) {
            this.selectedItem = 1;
        }
        if (mouseX >= sr.getScaledWidth() / 2 && mouseY <= sr.getScaledHeight() / 2) {
            this.selectedItem = 2;
        }
        if (mouseX >= sr.getScaledWidth() / 2 && mouseY >= sr.getScaledHeight() / 2) {
            this.selectedItem = 0;
        }
        RenderHelper.drawCircle((float)(sr.getScaledWidth() / 2), (float)(sr.getScaledHeight() / 2), MiddleClick.getInstance().circus.getValue() * 2.0f, false, PaletteHelper.astolfo(false, 1));
        Util.fr.drawStringWithShadow(Thunderhack.friendManager.isFriend(this.player.getName()) ? "Del Friend" : "Add Friend", (float)(sr.getScaledWidth() / 2 - 100), (float)(sr.getScaledHeight() / 2 - 8), PaletteHelper.astolfo(false, 1).getRGB());
        Util.fr.drawStringWithShadow(Thunderhack.enemyManager.isEnemy(this.player.getName()) ? "Del Enemy" : "Add Enemy", (float)(sr.getScaledWidth() / 2 + 30), (float)(sr.getScaledHeight() / 2 + 55), PaletteHelper.astolfo(false, 1).getRGB());
        Util.fr.drawStringWithShadow((String)MiddleClick.getInstance().commandtext.getValue(), (float)(sr.getScaledWidth() / 2 + 30), (float)(sr.getScaledHeight() / 2 - 60), PaletteHelper.astolfo(false, 1).getRGB());
        Util.fr.drawStringWithShadow(this.player.getName(), (float)(sr.getScaledWidth() / 2 - 15), (float)(sr.getScaledHeight() / 2 - 40), -1);
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean friended = Thunderhack.friendManager.isFriend(this.player.getName());
        boolean practice = false;
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
            practice = true;
        }
        if (mouseButton == 0) {
            if (this.selectedItem == 0) {
                if (Thunderhack.enemyManager.isEnemy(this.player.getName())) {
                    Thunderhack.enemyManager.removeEnemy(this.player.getName());
                    Command.sendMessage("\u0412\u0440\u0430\u0433 " + this.player.getName() + " \u0443\u0434\u0430\u043b\u0435\u043d!");
                }
                else {
                    Thunderhack.enemyManager.addEnemy(this.player.getName());
                    Command.sendMessage("\u0412\u0440\u0430\u0433 " + this.player.getName() + " \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d!");
                }
            }
            if (this.selectedItem == 1) {
                if (Thunderhack.friendManager.isFriend(this.player.getName())) {
                    Thunderhack.friendManager.removeFriend(this.player.getName());
                    Command.sendMessage("\u0414\u0440\u0443\u0433 " + this.player.getName() + " \u0443\u0434\u0430\u043b\u0435\u043d!");
                }
                else {
                    Thunderhack.friendManager.addFriend(this.player.getName());
                    Command.sendMessage("\u0414\u0440\u0443\u0433 " + this.player.getName() + " \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d!");
                    if (MiddleClick.getInstance().fm.getValue()) {
                        this.mc.player.sendChatMessage("/w " + this.player.getName() + " I friended u at Thunderhack plus!");
                    }
                }
            }
            if (this.selectedItem == 2) {
                this.mc.player.sendChatMessage(MiddleClick.getInstance().commandname.getValue() + " " + this.player.getName());
            }
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
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
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
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.disableDepth();
        GlStateManager.popMatrix();
    }
    
    public static boolean mouseWithinBounds(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    private void drawSlice(final BufferBuilder buffer, final float x, final float y, final float z, final float radiusIn, final float radiusOut, float startAngle, float endAngle, final int r, final int g, final int b, final int a) {
        float angle = endAngle - startAngle;
        final int sections = Math.max(1, MathHelper.ceil(angle / 5.0f));
        startAngle = (float)Math.toRadians(startAngle);
        endAngle = (float)Math.toRadians(endAngle);
        angle = endAngle - startAngle;
        for (int i = 0; i < sections; ++i) {
            final float angle2 = startAngle + i / (float)sections * angle;
            final float angle3 = startAngle + (i + 1) / (float)sections * angle;
            final float pos1InX = x + radiusIn * (float)Math.cos(angle2);
            final float pos1InY = y + radiusIn * (float)Math.sin(angle2);
            final float pos1OutX = x + radiusOut * (float)Math.cos(angle2);
            final float pos1OutY = y + radiusOut * (float)Math.sin(angle2);
            final float pos2OutX = x + radiusOut * (float)Math.cos(angle3);
            final float pos2OutY = y + radiusOut * (float)Math.sin(angle3);
            final float pos2InX = x + radiusIn * (float)Math.cos(angle3);
            final float pos2InY = y + radiusIn * (float)Math.sin(angle3);
            buffer.pos((double)pos1OutX, (double)pos1OutY, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)pos1InX, (double)pos1InY, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)pos2InX, (double)pos2InY, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)pos2OutX, (double)pos2OutY, (double)z).color(r, g, b, a).endVertex();
        }
    }
}
