//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.components.items.buttons;

import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import org.lwjgl.input.*;
import java.awt.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class ColorSettingComponent extends Button
{
    private final Setting colorSetting;
    public int alpha;
    public int red;
    public int green;
    public int blue;
    float sliderWidth;
    float steps;
    float sliderX;
    float sliderY;
    float sliderHeight;
    float pickerBoxX;
    float pickerBoxY;
    float pickerBoxHeight;
    float pickerBoxWidth;
    int niggerh;
    private final Timer timer;
    int color;
    public static int clipboard;
    
    public ColorSettingComponent(final Setting colorSetting) {
        super(colorSetting.getName());
        this.sliderWidth = 45.0f;
        this.steps = this.sliderWidth / 10.0f;
        this.sliderX = this.getX() + 1.0f;
        this.sliderY = this.getY() + 48.0f;
        this.sliderHeight = 10.0f;
        this.pickerBoxX = this.getX() + 1.0f;
        this.pickerBoxY = this.getY() + 18.0f;
        this.pickerBoxHeight = 26.0f;
        this.pickerBoxWidth = 44.0f;
        this.niggerh = 15;
        this.timer = new Timer();
        this.color = ColorUtil.toARGB(ClickGui.getInstance().mainColor.getValue().getRed(), ClickGui.getInstance().mainColor.getValue().getGreen(), ClickGui.getInstance().mainColor.getValue().getBlue(), 170);
        this.colorSetting = colorSetting;
        this.alpha = (this.getColorSetting().getRawColor() >> 24 & 0xFF);
        this.red = (this.getColorSetting().getRawColor() >> 16 & 0xFF);
        this.green = (this.getColorSetting().getRawColor() >> 8 & 0xFF);
        this.blue = (this.getColorSetting().getRawColor() & 0xFF);
        this.height = this.niggerh;
    }
    
    public void refresh() {
        this.alpha = (this.getColorSetting().getRawColor() >> 24 & 0xFF);
        this.red = (this.getColorSetting().getRawColor() >> 16 & 0xFF);
        this.green = (this.getColorSetting().getRawColor() >> 8 & 0xFF);
        this.blue = (this.getColorSetting().getRawColor() & 0xFF);
    }
    
    public boolean isOpen() {
        return true;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.color = ColorUtil.toARGB(ClickGui.getInstance().mainColor.getValue().getRed(), ClickGui.getInstance().mainColor.getValue().getGreen(), ClickGui.getInstance().mainColor.getValue().getBlue(), 170);
        if (Mouse.isButtonDown(0)) {
            this.dragSetting(mouseX, mouseY);
        }
        final float[] hsb = Color.RGBtoHSB(this.red, this.green, this.blue, null);
        GuiRenderHelper.drawRect(this.getX(), this.getY(), 15.0f, 12.0f, this.color);
        this.color = new Color(96, 96, 96, 100).hashCode();
        GuiRenderHelper.drawRect(this.getX(), this.getY(), (float)this.getWidth(), 12.0f, this.color);
        ClickGUIFontRenderWrapper.drawStringWithShadow(this.getName(), (float)(int)(this.getX() + 5.0f), (float)(int)(this.getY() + 6.0f - ClickGUIFontRenderWrapper.getFontHeight() / 2 - 0.5f), -1);
        GuiRenderHelper.drawRect(this.getX(), this.getY() + 17.0f, 46.0f, 28.0f, this.color);
        this.pickerBoxX = this.getX() + 1.0f;
        this.pickerBoxY = this.getY() + 18.0f;
        RenderUtil.draw2DGradientRect(this.pickerBoxX, this.pickerBoxY, this.pickerBoxX + this.pickerBoxWidth, this.pickerBoxY + this.pickerBoxHeight, Color.getHSBColor(hsb[0], 0.0f, 0.0f).getRGB(), Color.getHSBColor(hsb[0], 0.0f, 1.0f).getRGB(), Color.getHSBColor(hsb[0], 1.0f, 0.0f).getRGB(), Color.getHSBColor(hsb[0], 1.0f, 1.0f).getRGB());
        GuiRenderHelper.drawRect(this.getX(), this.getY() + 47.0f, 46.0f, 12.0f, this.color);
        this.sliderX = this.getX() + 1.0f;
        this.sliderY = this.getY() + 48.0f;
        for (float i = 0.0f; i + this.steps <= this.sliderWidth; i += this.steps) {
            RenderUtil.draw1DGradientRect(this.sliderX + i, this.sliderY, this.sliderX + this.steps + i, this.sliderY + this.sliderHeight, Color.getHSBColor(i / this.sliderWidth, 1.0f, 1.0f).getRGB(), Color.getHSBColor((i + this.steps) / this.sliderWidth, 1.0f, 1.0f).getRGB());
        }
        GuiRenderHelper.drawRect(this.getX() + 45.0f, this.getY() + 47.0f, 1.0f, 12.0f, this.color);
        final int maxAlpha = 0xFF000000 | (this.red & 0xFF) << 16 | (this.green & 0xFF) << 8 | (this.blue & 0xFF);
        final int minAlpha = 0x0 | (this.red & 0xFF) << 16 | (this.green & 0xFF) << 8 | (this.blue & 0xFF);
        final float alphaX = this.getX() + this.getWidth() - 1.0f;
        final float alphaY = this.getY() + 17.0f;
        final float alphaWidth = 10.0f;
        final float alphaHeight = 42.0f;
        RenderUtil.draw2DGradientRect(alphaX, alphaY, this.getX() + this.getWidth() + 9.0f, this.getY() + 60.0f - 2.0f, minAlpha, maxAlpha, minAlpha, maxAlpha);
        ClickGUIFontRenderWrapper.drawString("R" + this.red, (float)((int)this.getX() + 3 + (int)this.sliderWidth), (float)((int)this.getY() + 16), 16777215);
        ClickGUIFontRenderWrapper.drawString("G" + this.green, (float)((int)this.getX() + 3 + (int)this.sliderWidth), (int)this.getY() + 18 + ClickGUIFontRenderWrapper.getStringHeight("RGB0:1234567890") - 0.5f, 16777215);
        ClickGUIFontRenderWrapper.drawString("B" + this.blue, (float)((int)this.getX() + 3 + (int)this.sliderWidth), (int)this.getY() + 20 + ClickGUIFontRenderWrapper.getStringHeight("RGB0:1234567890") * 2.0f - 0.5f, 16777215);
        if (this.getColorSetting().isCycle()) {
            GuiRenderHelper.drawOutlineRect(this.getX() + 3.0f + this.sliderWidth, (float)((int)this.getY() + 22 + ClickGUIFontRenderWrapper.getFontHeight() * 3), 10.0f, 10.0f, 2.0f, this.getColorSetting().getColor());
        }
        GuiRenderHelper.drawRect(this.getX() + 4.0f + this.sliderWidth, (float)((int)this.getY() + 23 + ClickGUIFontRenderWrapper.getFontHeight() * 3), 8.0f, 8.0f, this.getColorSetting().getColor());
        ClickGUIFontRenderWrapper.drawString("RB", (float)(int)(this.getX() + 15.0f + this.sliderWidth), (float)((int)this.getY() + 22 + ClickGUIFontRenderWrapper.getFontHeight() * 3), 16777215);
        final int indicatorColor = new Color(255, 255, 255, 140).hashCode();
        final int indicatorHue = (int)(this.sliderX + (int)(hsb[0] * this.sliderWidth));
        GuiRenderHelper.drawRect((float)indicatorHue, this.sliderY, 2.0f, this.sliderHeight, indicatorColor);
        final int indicatorAlpha = (int)(alphaHeight + alphaY - (int)(this.alpha / 255.0f * alphaHeight));
        GuiRenderHelper.drawRect(alphaX, MathHelper.clamp(indicatorAlpha - 1.0f, alphaY, alphaY + alphaHeight), alphaWidth, 2.0f, indicatorColor);
        final int indicatorX = (int)(this.pickerBoxX + (int)(hsb[1] * this.pickerBoxWidth));
        final int indicatorY = (int)(this.pickerBoxHeight + this.pickerBoxY - (int)(hsb[2] * this.pickerBoxHeight));
        GuiRenderHelper.drawRect(indicatorX - 1.0f, indicatorY - 1.0f, 2.0f, 2.0f, indicatorColor);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), 12.0)) {
            if (mouseButton == 2) {
                if (ColorSettingComponent.clipboard == -1) {
                    ColorSettingComponent.clipboard = this.getColorSetting().getRawColor();
                }
                else {
                    this.getColorSetting().setColor(ColorSettingComponent.clipboard);
                    ColorSettingComponent.clipboard = -1;
                    this.refresh();
                }
            }
            else {
                Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }
        this.handleMouseClick(mouseX, mouseY, mouseButton);
    }
    
    private void dragSetting(final int mouseX, final int mouseY) {
        this.handleMouseClick(mouseX, mouseY, 0);
    }
    
    private boolean handleMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
        if (!GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, this.getX(), this.getY(), this.getWidth() + 20, 60.0)) {
            return false;
        }
        final float alphaX = this.getX() + this.getWidth() - 1.0f;
        if (mouseButton != 0) {
            return false;
        }
        final float alphaY = this.getY() + 17.0f;
        if (this.isOpen()) {
            if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, this.pickerBoxX, this.pickerBoxY, this.pickerBoxWidth, this.pickerBoxHeight - 1.0f)) {
                this.getRGBfromClick((float)mouseX, (float)mouseY);
            }
            else if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, this.sliderX, this.sliderY, this.sliderWidth - 2.0f, this.sliderHeight)) {
                this.getHueFromClick((float)mouseX);
            }
            else if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, alphaX, alphaY, 8.0, 42.0)) {
                this.getAlphaFromClick((float)mouseY);
            }
            else if (GuiMiddleClickMenu.mouseWithinBounds(mouseX, mouseY, this.getX() + 4.0f + this.sliderWidth, this.getY() + 25.0f + ClickGUIFontRenderWrapper.getFontHeight() * 3, 8.0, 8.0) && this.timer.passedMs(500L)) {
                this.getColorSetting().toggleCycle();
                this.timer.reset();
            }
        }
        return true;
    }
    
    private void getRGBfromClick(float mouseX, float mouseY) {
        mouseX -= this.getX();
        mouseY -= this.getY();
        final float sat = (mouseX - 1.0f) / this.pickerBoxWidth;
        final float bri = 1.0f - (mouseY - 18.0f) / this.pickerBoxHeight;
        final float[] hsb = Color.RGBtoHSB(this.red, this.green, this.blue, null);
        final int rgb = Color.HSBtoRGB(hsb[0], sat, bri);
        this.red = (rgb >> 16 & 0xFF);
        this.green = (rgb >> 8 & 0xFF);
        this.blue = (rgb & 0xFF);
        this.updateColor();
    }
    
    private void getHueFromClick(float mouseX) {
        mouseX -= this.getX();
        final float hue = (mouseX - 1.0f) / this.sliderWidth;
        final float[] hsb = Color.RGBtoHSB(this.red, this.green, this.blue, null);
        final int rgb = Color.HSBtoRGB(hue, hsb[1], hsb[2]);
        this.red = (rgb >> 16 & 0xFF);
        this.green = (rgb >> 8 & 0xFF);
        this.blue = (rgb & 0xFF);
        this.updateColor();
    }
    
    private void getAlphaFromClick(float mouseY) {
        mouseY -= this.getY();
        this.alpha = 255 - (int)((mouseY - 17.0f) / 42.0f * 255.0f);
        this.updateColor();
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
    
    static {
        ColorSettingComponent.clipboard = -1;
    }
}
