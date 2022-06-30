//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.gui.thundergui.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.*;

public class TColorPicker extends TItem
{
    private final Setting colorSetting;
    public int alpha;
    public int red;
    public int green;
    public int blue;
    private final Timer timer;
    
    public TColorPicker(final Setting colorSetting, final int x, final int y) {
        super(colorSetting.getName());
        this.timer = new Timer();
        this.colorSetting = colorSetting;
        this.alpha = (this.getColorSetting().getRawColor() >> 24 & 0xFF);
        this.red = (this.getColorSetting().getRawColor() >> 16 & 0xFF);
        this.green = (this.getColorSetting().getRawColor() >> 8 & 0xFF);
        this.blue = (this.getColorSetting().getRawColor() & 0xFF);
        this.setLocation((float)x, (float)y);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        if (Mouse.isButtonDown(0)) {
            this.dragSetting(mouseX, mouseY);
        }
        RenderUtil.drawRect2(this.x, this.y, this.x + 94.0f, this.y + 90.0f, ThunderHackGui.getInstance().buttsColor.getValue().getRawColor());
        final float[] hsb = Color.RGBtoHSB(this.red, this.green, this.blue, null);
        FontRender.drawString3(this.colorSetting.getName(), (int)this.x + 3, (int)this.y + 2, new Color(this.red, this.green, this.blue).getRGB());
        RenderUtil.draw2DGradientRect(this.x + 3.0f, this.y + 12.0f, this.x + 91.0f, this.y + 52.0f, Color.getHSBColor(hsb[0], 0.0f, 0.0f).getRGB(), Color.getHSBColor(hsb[0], 0.0f, 1.0f).getRGB(), Color.getHSBColor(hsb[0], 1.0f, 0.0f).getRGB(), Color.getHSBColor(hsb[0], 1.0f, 1.0f).getRGB());
        for (float steps = 8.8f, i = 0.0f; i + steps <= 96.8; i += steps) {
            RenderUtil.draw1DGradientRect(this.x + 3.0f + i, this.y + 57.0f, this.x + 3.0f + steps + i, this.y + 62.0f, Color.getHSBColor(i / 88.0f, 1.0f, 1.0f).getRGB(), Color.getHSBColor((i + steps) / 88.0f, 1.0f, 1.0f).getRGB());
        }
        final int maxAlpha = 0xFF000000 | (this.red & 0xFF) << 16 | (this.green & 0xFF) << 8 | (this.blue & 0xFF);
        final int minAlpha = 0x0 | (this.red & 0xFF) << 16 | (this.green & 0xFF) << 8 | (this.blue & 0xFF);
        RenderUtil.draw2DGradientRect(this.x + 3.0f, this.y + 70.0f, this.x + 91.0f, this.y + 75.0f, minAlpha, minAlpha, maxAlpha, maxAlpha);
        final int indicatorColor = new Color(255, 255, 255, 140).hashCode();
        final int indicatorAlpha = (int)(this.x + 3.0f + (int)(this.alpha / 255.0f * 88.0f));
        RenderUtil.drawRect((float)indicatorAlpha, this.y + 69.0f, (float)(2 + indicatorAlpha), 6.0f + this.y + 70.0f, indicatorColor);
        final int indicatorHue = (int)(this.x + 3.0f + (int)(hsb[0] * 88.0f));
        RenderUtil.drawRect((float)indicatorHue, this.y + 56.0f, (float)(2 + indicatorHue), 3.0f + this.y + 61.0f, indicatorColor);
        final int indicatorX = (int)(this.x + 3.0f + (int)(hsb[1] * 88.0f));
        final int indicatorY = (int)(40.0f + this.y + 12.0f - (int)(hsb[2] * 40.0f));
        GuiRenderHelper.drawRect(indicatorX - 1.0f, indicatorY - 1.0f, 2.0f, 2.0f, indicatorColor);
        RenderUtil.drawSmoothRect(this.x + 3.0f, this.y + 80.0f, this.x + 91.0f, this.y + 88.0f, new Color(this.red, this.green, this.blue).getRGB());
        final String values = "R" + this.red + "/G" + this.green + "/B" + this.blue + "/A" + this.alpha;
        Util.fr.drawString(values, (int)this.x + 5, (int)this.y + 80, getContrastColor(new Color(this.red, this.green, this.blue)).getRGB());
        this.refresh();
    }
    
    public static Color getContrastColor(final Color color) {
        final double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return (y >= 128.0) ? Color.black : Color.white;
    }
    
    public void refresh() {
        this.alpha = (this.getColorSetting().getRawColor() >> 24 & 0xFF);
        this.red = (this.getColorSetting().getRawColor() >> 16 & 0xFF);
        this.green = (this.getColorSetting().getRawColor() >> 8 & 0xFF);
        this.blue = (this.getColorSetting().getRawColor() & 0xFF);
    }
    
    private void updateColor() {
        final int rgb = (this.alpha & 0xFF) << 24 | (this.red & 0xFF) << 16 | (this.green & 0xFF) << 8 | (this.blue & 0xFF);
        this.getColorSetting().setColor(rgb);
    }
    
    public ColorSetting getColorSetting() {
        return this.colorSetting.getValue();
    }
    
    public Setting getSetting() {
        return this.colorSetting;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.handleMouseClick(mouseX, mouseY, mouseButton);
    }
    
    private boolean handleMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
        if (!GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, (double)this.x, (double)this.y, 94.0, 90.0)) {
            return false;
        }
        if (mouseButton != 0) {
            return false;
        }
        if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, (double)(this.x + 3.0f), (double)(this.y + 12.0f), 91.0, 40.0)) {
            this.getRGBfromClick((float)mouseX, (float)mouseY);
        }
        else if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, (double)(this.x + 3.0f), (double)(this.y + 57.0f), 88.0, 5.0)) {
            this.getHueFromClick((float)mouseX);
        }
        else if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, (double)(this.x + 3.0f), (double)(this.y + 70.0f), 88.0, 5.0)) {
            this.getAlphaFromClick((float)mouseX);
        }
        return true;
    }
    
    private void getRGBfromClick(final float mouseX, final float mouseY) {
        final float saturation = (mouseX - this.x) / 91.0f;
        final float brightness = 1.0f - (mouseY - (this.y + 12.0f)) / 40.0f;
        final float[] hsb = Color.RGBtoHSB(this.red, this.green, this.blue, null);
        final int rgb = Color.HSBtoRGB(hsb[0], saturation, brightness);
        this.red = (rgb >> 16 & 0xFF);
        this.green = (rgb >> 8 & 0xFF);
        this.blue = (rgb & 0xFF);
        this.updateColor();
    }
    
    private void getHueFromClick(final float mouseX) {
        final float hue = (mouseX - 3.0f - this.x) / 88.0f;
        final float[] hsb = Color.RGBtoHSB(this.red, this.green, this.blue, null);
        final int rgb = Color.HSBtoRGB(hue, hsb[1], hsb[2]);
        this.red = (rgb >> 16 & 0xFF);
        this.green = (rgb >> 8 & 0xFF);
        this.blue = (rgb & 0xFF);
        this.updateColor();
    }
    
    private void dragSetting(final int mouseX, final int mouseY) {
        this.handleMouseClick(mouseX, mouseY, 0);
    }
    
    private void getAlphaFromClick(final float mouseX) {
        this.alpha = (int)((mouseX - 3.0f - this.x) / 88.0f * 255.0f);
        this.updateColor();
    }
}
