//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import com.mrzak34.thunderhack.features.setting.*;

public class TModuleButt extends TItem
{
    private final Module module;
    private int progress;
    public static List<TItem> items;
    public float age;
    public int sintegers;
    public int sfloats;
    public int senums;
    public int scolors;
    public int sbools;
    int stepik;
    boolean biloslider;
    boolean bilocolor;
    boolean bilopos;
    boolean biloparent;
    boolean lastbiloslider;
    boolean lastbilocolor;
    boolean lastbilopos;
    boolean lastparent;
    
    public TModuleButt(final Module module, final int x, final int y) {
        super(module.getName());
        this.age = 0.0f;
        this.sintegers = 0;
        this.sfloats = 0;
        this.senums = 0;
        this.scolors = 0;
        this.sbools = 0;
        this.stepik = 0;
        this.biloslider = false;
        this.bilocolor = false;
        this.bilopos = false;
        this.biloparent = false;
        this.lastbiloslider = false;
        this.lastbilocolor = false;
        this.lastbilopos = false;
        this.lastparent = false;
        this.module = module;
        this.setLocation((float)x, (float)y);
    }
    
    public int desh2() {
        int returnedval = 37;
        if (!this.module.getSettings().isEmpty()) {
            for (final Setting setting : this.module.getSettings()) {
                if (setting.getValue() instanceof ColorSetting) {
                    returnedval = 100;
                }
                if (setting.isPositionSetting()) {
                    returnedval = 100;
                }
                if (setting.isNumberSetting() && 70 > returnedval) {
                    returnedval = 70;
                }
                if (setting.isEnumSetting() && 47 > returnedval) {
                    returnedval = 47;
                }
            }
        }
        return returnedval;
    }
    
    public static void drawModalRect(final int var0, final int var1, final float var2, final float var3, final int var4, final int var5, final int var6, final int var7, final float var8, final float var9) {
        Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y < ThunderGui.thunderguiY || this.y > ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY + 43) {
            return;
        }
        RenderUtil.drawRect(this.x, this.y, this.x + 127.0f, this.y + 40.0f, ThunderHackGui.getInstance().buttsColor.getValue().getColorObject().getRGB());
        this.age += 0.5f;
        if (this.module.isSetting()) {
            GlStateManager.pushMatrix();
            RenderUtil.drawSmoothRect(this.x + 114.0f, this.y + 27.0f, this.x + 124.0f, this.y + 37.0f, RenderUtil.TwoColoreffect(new Color(10790052), new Color(2894892), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0f * (this.age / 16.0f) / 60.0f).getRGB());
            ++this.progress;
            final Minecraft mc = Util.mc;
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/modulegear.png"));
            GlStateManager.translate(this.getX() + 119.0f, this.getY() + 32.0f, 0.0f);
            GlStateManager.rotate(calculateRotation((float)this.progress), 0.0f, 0.0f, 1.0f);
            drawModalRect(-5, -5, 0.0f, 0.0f, 10, 10, 10, 10, 10.0f, 10.0f);
            GlStateManager.popMatrix();
        }
        else {
            this.age = 0.0f;
            GlStateManager.pushMatrix();
            RenderUtil.drawSmoothRect(this.x + 114.0f, this.y + 27.0f, this.x + 124.0f, this.y + 37.0f, new Color(10790052).getRGB());
            final Minecraft mc2 = Util.mc;
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/modulegear.png"));
            GlStateManager.translate(this.getX() + 119.0f, this.getY() + 32.0f, 0.0f);
            drawModalRect(-5, -5, 0.0f, 0.0f, 10, 10, 10, 10, 10.0f, 10.0f);
            GlStateManager.popMatrix();
        }
        if (!this.module.isEnabled()) {
            FontRender.drawString(this.module.getName(), this.x + 3.0f, this.y + 6.0f, new Color(14932441).getRGB());
        }
        else {
            FontRender.drawString(this.module.getName(), this.x + 3.0f, this.y + 6.0f, ThunderGui.getCatColor().getRGB());
        }
        if (!this.module.getBind().isEmpty() && this.module.isValidBind(this.module.getBind().toString())) {
            FontRender.drawString(this.module.getBind().toString(), this.x + 115.0f, this.y + 6.0f, new Color(14932441).getRGB());
        }
        final String myString = this.module.getDescription();
        final String[] splitString = myString.split("-");
        if (splitString[0] != null && !splitString[0].equals("")) {
            Util.fr.drawString(splitString[0], (int)this.x + 5, (int)(this.y + 17.0f), new Color(6645093).getRGB());
        }
        if (splitString.length > 1 && splitString[1] != null && !splitString[1].equals("")) {
            Util.fr.drawString(splitString[1], (int)this.x + 5, (int)(this.y + 25.0f), new Color(6645093).getRGB());
        }
        if (splitString.length == 3 && splitString[2] != null && !splitString[2].equals("")) {
            Util.fr.drawString(splitString[2], (int)this.x + 5, (int)(this.y + 32.0f), new Color(6645093).getRGB());
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && mouseX >= this.x + 114.0f && mouseX <= this.x + 124.0f && mouseY >= this.y + 27.0f && mouseY <= this.y + 37.0f) {
            ThunderGui.getInstance().components.forEach(component -> component.setSetting(false));
            ThunderGui.getInstance().components.forEach(component -> TModuleButt.items.clear());
            this.module.setSetting(true);
            this.initSettings();
            return;
        }
        if (mouseButton == 0 && mouseX >= this.x && mouseX <= this.x + 127.0f && mouseY >= this.y && mouseY <= this.y + 40.0f) {
            this.module.toggle();
        }
    }
    
    public void setSetting(final boolean b) {
        this.module.setSetting(b);
    }
    
    public static float calculateRotation(float var0) {
        if ((var0 %= 360.0f) >= 180.0f) {
            var0 -= 360.0f;
        }
        if (var0 < -180.0f) {
            var0 += 360.0f;
        }
        return var0;
    }
    
    public int chooseSide(final int a) {
        if (a == 0) {
            return (int)(this.x + 150.0f);
        }
        if (a == 1) {
            return (int)(this.x + 250.0f);
        }
        if (a == 2) {
            return (int)(this.x + 350.0f);
        }
        return (int)(this.x + 150.0f);
    }
    
    public void initSettings() {
        this.sintegers = 0;
        this.sfloats = 0;
        this.senums = 0;
        this.scolors = 0;
        this.sbools = 0;
        final AtomicInteger index = new AtomicInteger();
        this.stepik = 0;
        final ArrayList<TItem> newItems = new ArrayList<TItem>();
        if (!this.module.getSettings().isEmpty()) {
            for (final Setting setting : this.module.getSettings()) {
                if (this.stepik == 3) {
                    index.set(index.get() + 1);
                    this.stepik = 0;
                }
                if (setting.getValue() instanceof Parent) {
                    this.stepik = 3;
                    newItems.add((TItem)new ModuleSettingCategory(setting, this.chooseSide(0), ThunderGui.thunderguiY + 129 + this.desh2() * index.get()));
                    this.biloparent = true;
                }
                if (setting.isPositionSetting()) {
                    newItems.add(new TPositionSelector(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                    ++this.stepik;
                }
                if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                    newItems.add((TItem)new TBooleanButt(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                    ++this.sbools;
                    ++this.stepik;
                }
                if (setting.isEnumSetting()) {
                    newItems.add((TItem)new TModeButt(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                    ++this.stepik;
                    ++this.senums;
                }
                if (setting.isColorSetting()) {
                    newItems.add((TItem)new TColorPicker(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                    ++this.stepik;
                    ++this.scolors;
                }
                if (setting.isNumberSetting() && setting.hasRestriction()) {
                    newItems.add(new TSlider(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                    if (setting.isFloat()) {
                        ++this.sfloats;
                    }
                    if (setting.isInteger()) {
                        ++this.sintegers;
                    }
                    ++this.stepik;
                }
                else {
                    if (setting.getValue() instanceof SubBind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                        newItems.add(new TSubBindButt(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                        ++this.stepik;
                    }
                    if ((!(setting.getValue() instanceof String) && !(setting.getValue() instanceof Character)) || setting.getName().equalsIgnoreCase("displayName")) {
                        continue;
                    }
                    newItems.add(new TStringButt(setting, this.chooseSide(this.stepik), ThunderGui.thunderguiY + 77 + this.desh2() * index.get()));
                    ++this.stepik;
                }
            }
        }
        newItems.add((TItem)new TBindButt(this.module.getSettingByName("Keybind"), this.chooseSide(0), ThunderGui.thunderguiY + 200 + this.desh2() * index.get()));
        TModuleButt.items = newItems;
    }
    
    public boolean isSetting() {
        return this.module.isSetting();
    }
    
    static {
        TModuleButt.items = new ArrayList<TItem>();
    }
}
