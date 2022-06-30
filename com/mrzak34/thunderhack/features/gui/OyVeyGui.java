//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui;

import com.mrzak34.thunderhack.features.gui.components.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import com.mrzak34.thunderhack.features.*;
import java.util.function.*;
import java.util.*;
import com.mrzak34.thunderhack.features.gui.components.items.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.io.*;

public class OyVeyGui extends GuiScreen
{
    private static OyVeyGui oyveyGui;
    private static OyVeyGui INSTANCE;
    public boolean hudeditor;
    private final ArrayList<Component> components;
    public String search;
    public float animopenY;
    int color;
    public boolean searching;
    public boolean animopen;
    
    public OyVeyGui() {
        this.hudeditor = false;
        this.components = new ArrayList<Component>();
        this.search = "";
        this.animopenY = 5.0f;
        this.color = ColorUtil.toARGB(ClickGui.getInstance().topColor.getValue().getRed(), ClickGui.getInstance().topColor.getValue().getGreen(), ClickGui.getInstance().topColor.getValue().getBlue(), 25);
        this.setInstance();
        this.load();
    }
    
    public static OyVeyGui getInstance() {
        if (OyVeyGui.INSTANCE == null) {
            OyVeyGui.INSTANCE = new OyVeyGui();
        }
        return OyVeyGui.INSTANCE;
    }
    
    public static OyVeyGui getClickGui() {
        return getInstance();
    }
    
    private void setInstance() {
        OyVeyGui.INSTANCE = this;
    }
    
    private void load() {
        int x = -84;
        for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
            if (Objects.equals(category.getName(), "HUD") && !this.hudeditor) {
                return;
            }
            if (!Objects.equals(category.getName(), "HUD") && this.hudeditor) {
                return;
            }
            final ArrayList<Component> components2 = this.components;
            final String name = category.getName();
            x += 90;
            components2.add(new Component(name, x, 40, true) {
                public void setupItems() {
                    Thunderhack.moduleManager.getModulesByCategory(category).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton((Button)new ModuleButton(module));
                        }
                    });
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort(Comparator.comparing((Function<? super E, ? extends Comparable>)Feature::getName)));
    }
    
    public void updateModule(final Module module) {
        for (final Component component : this.components) {
            for (final Item item : component.getItems()) {
                if (!(item instanceof ModuleButton)) {
                    continue;
                }
                final ModuleButton button = (ModuleButton)item;
                final Module mod = button.getModule();
                if (module == null) {
                    continue;
                }
                if (!module.equals(mod)) {
                    continue;
                }
                button.initSettings();
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.animopen && this.animopenY < 20.0f) {
            ++this.animopenY;
        }
        if (!this.animopen && this.animopenY > 5.0f && !this.searching && (this.search == null || this.search.equals(""))) {
            --this.animopenY;
        }
        final ScaledResolution sr = new ScaledResolution(this.mc);
        RenderUtil.drawSmoothRect(sr.getScaledWidth() / 4 - 0.3f, this.animopenY - 15.3f, (float)(sr.getScaledWidth() * 0.75) + 0.3f, this.animopenY + 0.3f, this.color);
        RenderUtil.drawSmoothRect((float)(sr.getScaledWidth() / 4), this.animopenY - 15.0f, (float)(sr.getScaledWidth() * 0.75), this.animopenY, new Color(0, 31, 31, 255).getRGB());
        if (this.search != null && !this.search.equals("") && this.searching) {
            Util.fr.drawStringWithShadow(this.search + "_", sr.getScaledWidth() / 4 + 0.7f, 5.4f, -1);
        }
        this.checkMouseWheel();
        if (ClickGui.getInstance().darkBackGround.getValue()) {
            this.drawDefaultBackground();
        }
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
        if (mouseX >= sr.getScaledWidth() / 4 && mouseX <= sr.getScaledWidth() * 0.75 + this.width && mouseY >= 5.0f && mouseY <= 20.0f) {
            this.animopen = true;
        }
        else {
            this.animopen = false;
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (mouseX >= sr.getScaledWidth() / 4 && mouseX <= sr.getScaledWidth() * 0.75 + this.width && mouseY >= 5.0f && mouseY <= 20.0f) {
            this.searching = true;
            this.color = ColorUtil.toARGB(ClickGui.getInstance().topColor.getValue().getRed(), ClickGui.getInstance().topColor.getValue().getGreen(), ClickGui.getInstance().topColor.getValue().getBlue(), 180);
        }
        else {
            this.searching = false;
            this.color = ColorUtil.toARGB(ClickGui.getInstance().topColor.getValue().getRed(), ClickGui.getInstance().topColor.getValue().getGreen(), ClickGui.getInstance().topColor.getValue().getBlue(), 75);
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public final ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        }
        else if (dWheel > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }
    
    public int getTextOffset() {
        return -6;
    }
    
    public Component getComponentByName(final String name) {
        for (final Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return component;
        }
        return null;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
        if (this.searching) {
            final Bind bind = new Bind(keyCode);
            if (bind.toString().equalsIgnoreCase("RShift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("LShift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("R_Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("L_Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Back")) {
                if (removeLastChar(this.search) != null) {
                    this.search = removeLastChar(this.search);
                }
                else {
                    this.search = "";
                }
            }
            if (bind.toString().equalsIgnoreCase("Escape")) {
                this.search = "";
            }
            else if (!bind.toString().equalsIgnoreCase("Back")) {
                this.search += typedChar;
            }
        }
        else {
            this.search = "";
        }
    }
    
    public static String removeLastChar(final String s) {
        return (s == null || s.length() == 0) ? null : s.substring(0, s.length() - 1);
    }
    
    static {
        OyVeyGui.INSTANCE = new OyVeyGui();
    }
}
