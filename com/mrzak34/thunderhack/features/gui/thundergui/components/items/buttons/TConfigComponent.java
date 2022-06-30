//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import com.mrzak34.thunderhack.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class TConfigComponent extends TItem
{
    private final String configname;
    ResourceLocation configpng;
    ResourceLocation loadpng;
    ResourceLocation bin;
    
    public TConfigComponent(final String configname, final int x, final int y) {
        super(configname);
        this.configpng = new ResourceLocation("textures/configpng.png");
        this.loadpng = new ResourceLocation("textures/loadpng.png");
        this.bin = new ResourceLocation("textures/trashbinnigga.png");
        this.configname = configname;
        this.setLocation((float)x, (float)y);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y + 26.0f < 81 + ThunderGui.thunderguiY) {
            return;
        }
        RenderUtil.drawRect2(this.x, this.y, this.x + 432.0f, 26.0f + this.y, ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
        FontRender.drawString(this.configname, this.x + 30.0f, this.y + 3.0f, new Color(12961221).getRGB());
        if (this.isHoveringItem(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            RenderUtil.drawSmoothRect(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, new Color(16600142).getRGB());
        }
        else {
            RenderUtil.drawSmoothRect(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, new Color(16515843).getRGB());
        }
        if (this.isHoveringItem(this.x + 380.0f, this.y + 5.0f, this.x + 416.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            RenderUtil.drawSmoothRect(this.x + 380.0f, this.y + 5.0f, this.x + 396.0f, this.y + 21.0f, new Color(14276824).getRGB());
        }
        else {
            RenderUtil.drawSmoothRect(this.x + 380.0f, this.y + 5.0f, this.x + 396.0f, this.y + 21.0f, new Color(10460830).getRGB());
        }
        drawImage(this.configpng, this.x + 2.0f, this.y + 2.0f, 22.0f, 22.0f, new Color(6316128));
        if (this.isHoveringItem(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            drawImage(this.bin, this.x + 411.0f, this.y + 6.0f, 13.0f, 13.0f, new Color(6316128));
        }
        else {
            drawImage(this.bin, this.x + 411.0f, this.y + 6.0f, 13.0f, 13.0f, new Color(16579836));
        }
        if (this.isHoveringItem(this.x + 380.0f, this.y + 5.0f, this.x + 396.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            drawImage(this.loadpng, this.x + 381.0f, this.y + 6.0f, 13.0f, 13.0f, new Color(16777215));
        }
        else {
            drawImage(this.loadpng, this.x + 381.0f, this.y + 6.0f, 13.0f, 13.0f, new Color(9868950));
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.y + 26.0f < 81 + ThunderGui.thunderguiY) {
            return;
        }
        if (this.isHoveringItem(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            Thunderhack.configManager.deleteConfig(this.configname);
        }
        if (this.isHoveringItem(this.x + 380.0f, this.y + 5.0f, this.x + 396.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            Thunderhack.configManager.loadConfig(this.configname);
        }
    }
    
    public boolean isHoveringItem(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final float x, final float y, final float width, final float height, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        setColor(color.getRGB());
        Util.mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, 0.0f, 0.0f, (int)width, (int)height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }
    
    public static void setColor(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    public static void setColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
}
