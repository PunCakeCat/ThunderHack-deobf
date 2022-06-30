//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.components.items.buttons;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.gui.components.items.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.features.gui.components.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;

public class ModuleButton extends Button
{
    private final Module module;
    private final ResourceLocation logo;
    private List<Item> items;
    private boolean subOpen;
    private int progress;
    private int animscaling;
    private int animopen;
    
    public ModuleButton(final Module module) {
        super(module.getName());
        this.logo = new ResourceLocation("textures/lightning.png");
        this.items = new ArrayList<Item>();
        this.module = module;
        this.initSettings();
    }
    
    public void initSettings() {
        final ArrayList<Item> newItems = new ArrayList<Item>();
        if (!this.module.getSettings().isEmpty()) {
            for (final Setting setting : this.module.getSettings()) {
                if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                    newItems.add((Item)new BooleanButton(setting));
                }
                if (setting.getValue() instanceof Parent) {
                    newItems.add((Item)new ParentSettingButton(setting));
                }
                if (setting.getValue() instanceof ColorSetting) {
                    newItems.add((Item)new ColorSettingComponent(setting));
                    newItems.add((Item)new ColorShit(setting));
                    newItems.add((Item)new ColorShit(setting));
                    newItems.add((Item)new ColorShit(setting));
                }
                if (setting.getValue() instanceof Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                    newItems.add((Item)new BindButton(setting));
                }
                if (setting.getValue() instanceof SubBind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                    newItems.add((Item)new SubBindButton(setting));
                }
                if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
                    newItems.add((Item)new StringButton(setting));
                }
                if (setting.isNumberSetting() && setting.hasRestriction()) {
                    newItems.add((Item)new Slider(setting));
                }
                else {
                    if (!setting.isEnumSetting()) {
                        continue;
                    }
                    if (setting.getValue() instanceof Parent) {
                        continue;
                    }
                    newItems.add((Item)new EnumButton(setting));
                }
            }
        }
        newItems.add((Item)new BindButton(this.module.getSettingByName("Keybind")));
        this.items = newItems;
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
    
    public static void drawModalRect(final int var0, final int var1, final float var2, final float var3, final int var4, final int var5, final int var6, final int var7, final float var8, final float var9) {
        Gui.drawScaledCustomSizeModalRect(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
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
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!this.items.isEmpty()) {
            ModuleButton.mc.getTextureManager().bindTexture(this.logo);
            drawCompleteImage(this.x - 1.5f + this.width - 12.8f, this.y - 5.8f - OyVeyGui.getClickGui().getTextOffset(), 16, 16);
            if (this.subOpen) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                final Minecraft mc = Util.mc;
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/suka.png"));
                GlStateManager.translate(this.getX() + this.getWidth() - 6.7f, this.getY() + 7.7f - 0.3f, 0.0f);
                GlStateManager.rotate(calculateRotation((float)this.progress), 0.0f, 0.0f, 1.0f);
                drawModalRect(-5, -5, 0.0f, 0.0f, this.animscaling, this.animscaling, this.animscaling, this.animscaling, 10.0f, 10.0f);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                if (this.animscaling != 10) {
                    ++this.animscaling;
                }
                if (this.animopen != 15) {
                    ++this.animopen;
                }
                this.progress += 5;
                float height = 1.0f;
                for (final Item item : this.items) {
                    ++Component.counter1[0];
                    if (!item.isHidden()) {
                        item.setLocation(this.x + 2.0f, this.y + (height += 15.0f));
                        item.setHeight(this.animopen);
                        item.setWidth(this.width - 12);
                        item.drawScreen(mouseX, mouseY, partialTicks);
                    }
                    item.update();
                }
                RenderUtil.drawRect(this.x, this.y + 14.0f, this.x + 0.5f, this.y + height, ClickGui.getInstance().mainColor.getValue().getRawColor());
            }
            else {
                this.animscaling = 0;
                this.animopen = 0;
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.items.isEmpty()) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.subOpen = !this.subOpen;
                ModuleButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
            if (this.subOpen) {
                for (final Item item : this.items) {
                    if (item.isHidden()) {
                        continue;
                    }
                    item.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        super.onKeyTyped(typedChar, keyCode);
        if (!this.items.isEmpty() && this.subOpen) {
            for (final Item item : this.items) {
                if (item.isHidden()) {
                    continue;
                }
                item.onKeyTyped(typedChar, keyCode);
            }
        }
    }
    
    public int getHeight() {
        if (this.subOpen) {
            int height = 14;
            for (final Item item : this.items) {
                if (item.isHidden()) {
                    continue;
                }
                height += item.getHeight() + 1;
            }
            return height + 2;
        }
        return 14;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void toggle() {
        this.module.toggle();
    }
    
    public boolean getState() {
        return this.module.isEnabled();
    }
}
