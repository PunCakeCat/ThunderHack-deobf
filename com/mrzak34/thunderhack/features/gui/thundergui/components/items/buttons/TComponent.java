//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import com.mrzak34.thunderhack.features.*;
import com.mrzak34.thunderhack.features.gui.components.items.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import java.util.*;
import com.mrzak34.thunderhack.features.gui.components.*;

public class TComponent extends Feature
{
    public static int[] counter1;
    private final ArrayList<Item> items;
    public boolean drag;
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private boolean open;
    private boolean hidden;
    
    public TComponent(final String name, final int x, final int y, final boolean open) {
        super(name);
        this.items = new ArrayList<Item>();
        this.hidden = false;
        this.x = x;
        this.y = y;
        this.width = 88;
        this.height = 18;
        this.open = open;
        this.setupItems();
    }
    
    public void setupItems() {
    }
    
    private void drag(final int mouseX, final int mouseY) {
        if (!this.drag) {
            return;
        }
        this.x = this.x2 + mouseX;
        this.y = this.y2 + mouseY;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drag(mouseX, mouseY);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            OyVeyGui.getClickGui().getComponents().forEach(component -> {
                if (component.drag) {
                    component.drag = false;
                }
                return;
            });
            this.drag = true;
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            TComponent.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            return;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        if (releaseButton == 0) {
            this.drag = false;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.onKeyTyped(typedChar, keyCode));
    }
    
    public void addButton(final Button button) {
        this.items.add((Item)button);
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public final ArrayList<Item> getItems() {
        return this.items;
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight() - (this.open ? 2 : 0);
    }
    
    private float getTotalItemHeight() {
        float height = 0.0f;
        for (final Item item : this.getItems()) {
            height += item.getHeight() + 1.5f;
        }
        return height;
    }
    
    static {
        TComponent.counter1 = new int[] { 1 };
    }
}
