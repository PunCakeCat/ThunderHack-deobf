//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.gui.inventory.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class OffhandRewrite extends Module
{
    private final Setting<Integer> health;
    private final Setting<Integer> faldistance;
    public Setting<Mode> mode;
    public Setting<Boolean> crapple;
    public Setting<Boolean> rightClickGapple;
    
    public OffhandRewrite() {
        super("OffHand", "\u0410\u0432\u0442\u043e \u0432\u0442\u043e\u0440\u0430\u044f \u0440\u0443\u043a\u0430", Category.COMBAT, true, false, false);
        this.health = (Setting<Integer>)this.register(new Setting("RangeY", (T)16, (T)0, (T)36));
        this.faldistance = (Setting<Integer>)this.register(new Setting("Fall Distance", (T)16, (T)0, (T)20));
        this.mode = (Setting<Mode>)this.register(new Setting("Confirm", (T)Mode.TOTEM));
        this.crapple = (Setting<Boolean>)this.register(new Setting("Crapple", (T)false));
        this.rightClickGapple = (Setting<Boolean>)this.register(new Setting("Right Click Gapple", (T)true));
    }
    
    @Override
    public void onUpdate() {
        if (nullCheck() || OffhandRewrite.mc.currentScreen instanceof GuiInventory) {
            return;
        }
        final float hp = OffhandRewrite.mc.player.getHealth() + OffhandRewrite.mc.player.getAbsorptionAmount();
        if (hp > this.health.getValue() && OffhandRewrite.mc.player.fallDistance < this.faldistance.getValue()) {
            final Item heldItem = OffhandRewrite.mc.player.getHeldItemMainhand().getItem();
            if (this.rightClickGapple.getValue() && OffhandRewrite.mc.gameSettings.keyBindUseItem.isKeyDown() && (heldItem instanceof ItemSword || heldItem instanceof ItemAxe) && OffhandRewrite.mc.currentScreen == null) {
                if (this.mode.getValue() != Mode.GAPPLE) {
                    ItemUtil.swapToOffhandSlot(this.getSlot(Mode.GAPPLE));
                }
            }
            else {
                ItemUtil.swapToOffhandSlot(this.getSlot(this.mode.getValue()));
            }
        }
        else {
            ItemUtil.swapToOffhandSlot(ItemUtil.getItemSlot(Items.TOTEM_OF_UNDYING));
        }
    }
    
    private int getSlot(final Mode mode) {
        switch (mode) {
            case CRYSTAL: {
                return ItemUtil.getItemSlot(Items.END_CRYSTAL);
            }
            case GAPPLE: {
                return ItemUtil.getGappleSlot(this.crapple.getValue());
            }
            default: {
                return ItemUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
            }
        }
    }
    
    public enum Mode
    {
        TOTEM, 
        GAPPLE, 
        CRYSTAL;
    }
}
