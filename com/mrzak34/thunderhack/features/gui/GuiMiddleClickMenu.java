//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui;

import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import java.awt.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.util.math.*;
import javax.vecmath.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.gui.font.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.entity.*;

public class GuiMiddleClickMenu extends GuiScreen
{
    private final EntityPlayer player;
    private final Timer timer;
    public double posX;
    public double posY;
    private final int black;
    
    public GuiMiddleClickMenu(final EntityPlayer player) {
        this.timer = new Timer();
        this.black = Color.BLACK.getRGB();
        this.player = player;
        this.timer.reset();
    }
    
    private Vector3d project2D(final Float scaleFactor, final double x, final double y, final double z) {
        final float xPos = (float)x;
        final float yPos = (float)y;
        final float zPos = (float)z;
        final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector)) {
            return new Vector3d((double)(vector.get(0) / scaleFactor), (double)((Display.getHeight() - vector.get(1)) / scaleFactor), (double)vector.get(2));
        }
        return null;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final Float scaleFactor = MiddleClick.getInstance().scalefactor.getValue();
        final double scaling = scaleFactor / Math.pow(scaleFactor, 2.0);
        GlStateManager.scale(scaling, scaling, scaling);
        final Color c = new Color(255, 255, 255);
        int color = 0;
        color = c.getRGB();
        final float scale = 1.0f;
        final EntityPlayer entityPlayer = this.player;
        if (entityPlayer != ImageESP.mc.player) {
            final double x = this.player.lastTickPosX + (this.player.posX - this.player.lastTickPosX) * this.mc.getRenderPartialTicks();
            final double y = this.player.lastTickPosY + (this.player.posY - this.player.lastTickPosY) * this.mc.getRenderPartialTicks();
            final double z = this.player.lastTickPosZ + (this.player.posZ - this.player.lastTickPosZ) * this.mc.getRenderPartialTicks();
            final AxisAlignedBB axisAlignedBB2 = this.player.getEntityBoundingBox();
            final AxisAlignedBB axisAlignedBB3 = new AxisAlignedBB(axisAlignedBB2.minX - this.player.posX + x - 0.05, axisAlignedBB2.minY - this.player.posY + y, axisAlignedBB2.minZ - this.player.posZ + z - 0.05, axisAlignedBB2.maxX - this.player.posX + x + 0.05, axisAlignedBB2.maxY - this.player.posY + y + 0.15, axisAlignedBB2.maxZ - this.player.posZ + z + 0.05);
            final Vector3d[] vectors = { new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ) };
            this.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
            Vector4d position = null;
            for (Vector3d vector : vectors) {
                vector = this.project2D(scaleFactor, vector.x - this.mc.getRenderManager().renderPosX, vector.y - this.mc.getRenderManager().renderPosY, vector.z - this.mc.getRenderManager().renderPosZ);
                if (vector != null && vector.z > 0.0 && vector.z < 1.0) {
                    if (position == null) {
                        position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                    }
                    position.x = Math.min(vector.x, position.x);
                    position.y = Math.min(vector.y, position.y);
                    position.z = Math.max(vector.x, position.z);
                    position.w = Math.max(vector.y, position.w);
                }
            }
            if (position != null) {
                this.mc.entityRenderer.setupOverlayRendering();
                this.posX = position.x;
                this.posY = position.y;
                final double endPosX = position.z;
                final double endPosY = position.w;
                RectHelper.drawRect(this.posX - 1.0, this.posY, this.posX + 0.5, endPosY + 0.5, this.black);
                RectHelper.drawRect(this.posX - 1.0, this.posY - 0.5, endPosX + 0.5, this.posY + 0.5 + 0.5, this.black);
                RectHelper.drawRect(endPosX - 0.5 - 0.5, this.posY, endPosX + 0.5, endPosY + 0.5, this.black);
                RectHelper.drawRect(this.posX - 1.0, endPosY - 0.5 - 0.5, endPosX + 0.5, endPosY + 0.5, this.black);
                RectHelper.drawRect(this.posX - 0.5, this.posY, this.posX + 0.5 - 0.5, endPosY, color);
                RectHelper.drawRect(this.posX, endPosY - 0.5, endPosX, endPosY, color);
                RectHelper.drawRect(this.posX - 0.5, this.posY, endPosX, this.posY + 0.5, color);
                RectHelper.drawRect(endPosX - 0.5, this.posY, endPosX, endPosY, color);
                RectHelper.drawRect(this.posX, this.posY, this.posX, this.posY, color);
            }
        }
        GL11.glEnable(2929);
        this.mc.entityRenderer.setupOverlayRendering();
        super.drawScreen(mouseX, mouseY, partialTicks);
        final boolean friended = Thunderhack.friendManager.isFriend(this.player.getName());
        boolean practice = false;
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
            practice = true;
        }
        final float width = Math.max(FontRendererWrapper.getStringWidth(friended ? "Unfriend" : "Friend"), FontRendererWrapper.getStringWidth((String)MiddleClick.getInstance().commandtext.getValue()));
        float height = 8.0f + FontRendererWrapper.getStringHeight(friended ? "Unfriend" : "Friend") + FontRendererWrapper.getStringHeight((String)MiddleClick.getInstance().commandtext.getValue());
        if (practice) {
            height += 4.0f + FontRendererWrapper.getStringHeight("Duel");
        }
        GuiRenderHelper.drawRect((float)this.posX - width / 2.0f - 2.0f, (float)this.posY - 2.0f, width + 4.0f, height, Integer.MIN_VALUE);
        GuiRenderHelper.drawOutlineRect((float)this.posX - width / 2.0f - 2.0f, (float)this.posY - 2.0f, width + 4.0f, height, 1.0f, -805306368);
        FontRendererWrapper.drawString(friended ? "Unfriend" : "Friend", (float)this.posX - FontRendererWrapper.getStringWidth(friended ? "Unfriend" : "Friend") / 2.0f, (float)this.posY, -1);
        FontRendererWrapper.drawString((String)MiddleClick.getInstance().commandtext.getValue(), (float)this.posX - FontRendererWrapper.getStringWidth((String)MiddleClick.getInstance().commandtext.getValue()) / 2.0f, (float)this.posY + 4.0f + FontRendererWrapper.getStringHeight(friended ? "Unfriend" : "Friend"), -1);
        if (practice) {
            FontRendererWrapper.drawString("Duel", (float)this.posX - FontRendererWrapper.getStringWidth("Duel") / 2.0f, (float)this.posY + 4.0f + FontRendererWrapper.getStringHeight(friended ? "Unfriend" : "Friend") + 4.0f + FontRendererWrapper.getStringHeight((String)MiddleClick.getInstance().commandtext.getValue()), -1);
        }
        if (this.timer.passedMs(5000L)) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean friended = Thunderhack.friendManager.isFriend(this.player.getName());
        boolean practice = false;
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
            practice = true;
        }
        if (mouseButton == 0) {
            if (mouseWithinBounds(mouseX, mouseY, (float)this.posX - FontRendererWrapper.getStringWidth(friended ? "Unfriend" : "Friend") / 2.0f, (float)this.posY, FontRendererWrapper.getStringWidth(friended ? "Unfriend" : "Friend"), FontRendererWrapper.getStringHeight(friended ? "Unfriend" : "Friend"))) {
                if (friended) {
                    Thunderhack.friendManager.removeFriend(this.player.getName());
                }
                else {
                    Thunderhack.friendManager.addFriend(this.player.getName());
                }
                this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (mouseWithinBounds(mouseX, mouseY, (float)this.posX - FontRendererWrapper.getStringWidth((String)MiddleClick.getInstance().commandtext.getValue()) / 2.0f, (float)this.posY + 4.0f + FontRendererWrapper.getStringHeight(friended ? "Unfriend" : "Friend"), FontRendererWrapper.getStringWidth((String)MiddleClick.getInstance().commandtext.getValue()), FontRendererWrapper.getStringHeight((String)MiddleClick.getInstance().commandtext.getValue()))) {
                this.mc.player.sendChatMessage(MiddleClick.getInstance().commandname.getValue() + " " + this.player.getName());
                this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            else if (practice && mouseWithinBounds(mouseX, mouseY, (float)this.posX - FontRendererWrapper.getStringWidth((String)MiddleClick.getInstance().commandtext.getValue()) / 2.0f, (float)this.posY + 4.0f + FontRendererWrapper.getStringHeight(friended ? "Unfriend" : "Friend") + 4.0f + FontRendererWrapper.getStringHeight((String)MiddleClick.getInstance().commandtext.getValue()), FontRendererWrapper.getStringWidth("Duel"), FontRendererWrapper.getStringHeight("Duel"))) {
                this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/duel " + this.player.getName()));
            }
        }
    }
    
    public static boolean mouseWithinBounds(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    private boolean isValid(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
}
