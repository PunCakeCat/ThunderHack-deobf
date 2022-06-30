//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui;

import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.features.gui.components.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import com.mrzak34.thunderhack.features.*;
import java.util.function.*;
import java.util.*;
import com.mrzak34.thunderhack.features.gui.components.items.*;
import org.lwjgl.input.*;

public class HudEditorGui extends GuiScreen
{
    private static HudEditorGui hudGui;
    private static HudEditorGui INSTANCE;
    private final ArrayList<Component> components;
    public String search;
    public float animopenY;
    int color;
    
    public HudEditorGui() {
        this.components = new ArrayList<Component>();
        this.search = "";
        this.animopenY = 5.0f;
        this.color = ColorUtil.toARGB(ClickGui.getInstance().topColor.getValue().getRed(), ClickGui.getInstance().topColor.getValue().getGreen(), ClickGui.getInstance().topColor.getValue().getBlue(), 25);
        this.setInstance();
        this.load();
    }
    
    public static HudEditorGui getInstance() {
        if (HudEditorGui.INSTANCE == null) {
            HudEditorGui.INSTANCE = new HudEditorGui();
        }
        return HudEditorGui.INSTANCE;
    }
    
    public static HudEditorGui getHudGui() {
        return getInstance();
    }
    
    private void setInstance() {
        HudEditorGui.INSTANCE = this;
    }
    
    private void load() {
        for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
            if (Objects.equals(category.getName(), "HUD")) {
                this.components.add(new Component(category.getName(), 100, 40, true) {
                    public void setupItems() {
                        HudEditorGui$1.counter1 = new int[] { 1 };
                        Thunderhack.moduleManager.getModulesByCategory(category).forEach(module -> {
                            if (!module.hidden) {
                                this.addButton((Button)new ModuleButton(module));
                            }
                        });
                    }
                });
            }
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
        this.checkMouseWheel();
        if (ClickGui.getInstance().darkBackGround.getValue()) {
            this.drawDefaultBackground();
        }
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
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
    
    static {
        HudEditorGui.INSTANCE = new HudEditorGui();
    }
}
