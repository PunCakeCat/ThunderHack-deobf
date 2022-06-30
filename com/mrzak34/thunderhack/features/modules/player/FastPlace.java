//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

public class FastPlace extends Module
{
    private boolean shouldMend;
    public static boolean isMending;
    private float prevHealth;
    public Setting<Boolean> attackCheck;
    private Setting<Integer> threshold;
    private Setting<Float> crystalRange;
    public Setting<Boolean> lookdown;
    public Setting<Boolean> xp;
    public Setting<Boolean> autoSwitch;
    public Setting<Boolean> armor;
    
    public FastPlace() {
        super("AutoMend", "\u043a\u043e\u043d\u0430\u0441 \u0430\u0432\u0442\u043e\u043c\u0435\u043d\u0434", Module.Category.PLAYER, true, false, false);
        this.shouldMend = false;
        this.prevHealth = 0.0f;
        this.attackCheck = (Setting<Boolean>)this.register(new Setting("AttackCheck", (T)true));
        this.threshold = (Setting<Integer>)this.register(new Setting("Threshold", (T)60, (T)0, (T)100));
        this.crystalRange = new Setting<Float>("CrystalRange", 6.0f, 10.0f, 0.0f, v -> this.attackCheck.getValue());
        this.lookdown = (Setting<Boolean>)this.register(new Setting("Lookdown", (T)true));
        this.xp = (Setting<Boolean>)this.register(new Setting("AutoXP", (T)false));
        this.autoSwitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", (T)false));
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", (T)false));
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateWalkingPlayerEvent event) {
        if (FastPlace.mc.player == null || FastPlace.mc.world == null) {
            return;
        }
        final EntityEnderCrystal crystal = (EntityEnderCrystal)FastPlace.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal && FastPlace.mc.player.getDistance(e) <= this.crystalRange.getValue()).map(entity -> entity).min(Comparator.comparing(c -> FastPlace.mc.player.getDistance(c))).orElse(null);
        if ((crystal != null || FastPlace.mc.player.getHealth() + FastPlace.mc.player.getAbsorptionAmount() < this.prevHealth) && this.attackCheck.getValue()) {
            FastPlace.isMending = false;
            this.shouldMend = false;
            this.toggle();
            return;
        }
        this.prevHealth = FastPlace.mc.player.getHealth() + FastPlace.mc.player.getAbsorptionAmount();
        if (FastPlace.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE || (this.autoSwitch.getValue() && this.getXpSlot() != -1)) {
            if (event.getStage() == 0) {
                if (event.isCanceled()) {
                    return;
                }
                this.shouldMend = false;
                if (this.lookdown.getValue()) {
                    FastPlace.mc.player.rotationPitch = 90.0f;
                }
                if (this.armor.getValue()) {
                    final ItemStack[] armorStacks = { FastPlace.mc.player.inventory.getStackInSlot(39), FastPlace.mc.player.inventory.getStackInSlot(38), FastPlace.mc.player.inventory.getStackInSlot(37), FastPlace.mc.player.inventory.getStackInSlot(36) };
                    for (int i = 0; i < 4; ++i) {
                        final ItemStack stack = armorStacks[i];
                        if (stack.getItem() instanceof ItemArmor) {
                            if (ArmorUtils.calculatePercentage(stack) >= this.threshold.getValue()) {
                                for (int s = 0; s < 36; ++s) {
                                    final ItemStack emptyStack = FastPlace.mc.player.inventory.getStackInSlot(s);
                                    if (emptyStack.isEmpty() && emptyStack.getItem() == Items.AIR) {
                                        FastPlace.isMending = true;
                                        FastPlace.mc.playerController.windowClick(FastPlace.mc.player.inventoryContainer.windowId, i + 5, 0, ClickType.PICKUP, (EntityPlayer)FastPlace.mc.player);
                                        FastPlace.mc.playerController.windowClick(FastPlace.mc.player.inventoryContainer.windowId, (s < 9) ? (s + 36) : s, 0, ClickType.PICKUP, (EntityPlayer)FastPlace.mc.player);
                                        FastPlace.mc.playerController.windowClick(FastPlace.mc.player.inventoryContainer.windowId, i + 5, 0, ClickType.PICKUP, (EntityPlayer)FastPlace.mc.player);
                                        FastPlace.mc.playerController.updateController();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 4; ++i) {
                        final ItemStack stack = armorStacks[i];
                        if (stack.getItem() instanceof ItemArmor) {
                            if (ArmorUtils.calculatePercentage(stack) < this.threshold.getValue()) {
                                this.shouldMend = true;
                            }
                        }
                    }
                    if (!this.shouldMend) {
                        FastPlace.isMending = false;
                        this.toggle();
                    }
                }
            }
            else if (this.xp.getValue() && (!this.armor.getValue() || this.shouldMend)) {
                final int itemSlot = this.getXpSlot();
                final boolean changeItem = this.autoSwitch.getValue() && FastPlace.mc.player.inventory.currentItem != itemSlot && itemSlot != -1;
                final int startingItem = FastPlace.mc.player.inventory.currentItem;
                if (changeItem) {
                    FastPlace.mc.player.inventory.currentItem = itemSlot;
                    FastPlace.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(itemSlot));
                }
                if (FastPlace.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle) {
                    FastPlace.mc.playerController.processRightClick((EntityPlayer)FastPlace.mc.player, (World)FastPlace.mc.world, EnumHand.MAIN_HAND);
                }
                if (changeItem) {
                    FastPlace.mc.player.inventory.currentItem = startingItem;
                    FastPlace.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(startingItem));
                }
            }
        }
    }
    
    private int getXpSlot() {
        ItemStack stack = FastPlace.mc.player.getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemExpBottle) {
            return FastPlace.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            stack = FastPlace.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemExpBottle) {
                return i;
            }
        }
        return -1;
    }
    
    public void onEnable() {
        this.prevHealth = 0.0f;
    }
    
    public void onDisable() {
        FastPlace.isMending = false;
    }
    
    static {
        FastPlace.isMending = false;
    }
}
