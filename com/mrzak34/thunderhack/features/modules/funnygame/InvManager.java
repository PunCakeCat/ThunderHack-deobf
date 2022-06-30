//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import java.util.*;

public class InvManager extends Module
{
    public static int weaponSlot;
    public static int pickaxeSlot;
    public static int axeSlot;
    public static int shovelSlot;
    public static List<Block> invalidBlocks;
    private final TimerHelper timer;
    public Setting<Integer> cap;
    public final Setting<Float> delay1;
    public Setting<Boolean> archer;
    public Setting<Boolean> food;
    public Setting<Boolean> sword;
    public Setting<Boolean> cleaner;
    public Setting<Boolean> openinv;
    public Setting<Boolean> nomoveswap;
    
    public InvManager() {
        super("InvManager", "\u043e\u0447\u0438\u0449\u0430\u0435\u0442 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c-\u043e\u0442 \u0445\u043b\u0430\u043c\u0430", Category.FUNNYGAME, true, false, false);
        this.timer = new TimerHelper();
        this.cap = (Setting<Integer>)this.register(new Setting("Block Cap", (T)128, (T)8, (T)256));
        this.delay1 = (Setting<Float>)this.register(new Setting("Sort Delay", (T)1.0f, (T)0.0f, (T)10.0f));
        this.archer = (Setting<Boolean>)this.register(new Setting("Archer", (T)false));
        this.food = (Setting<Boolean>)this.register(new Setting("Food", (T)false));
        this.sword = (Setting<Boolean>)this.register(new Setting("Sword", (T)true));
        this.cleaner = (Setting<Boolean>)this.register(new Setting("Inv Cleaner", (T)true));
        this.openinv = (Setting<Boolean>)this.register(new Setting("Open Inv", (T)true));
        this.nomoveswap = (Setting<Boolean>)this.register(new Setting("No Moving Swap", (T)false));
    }
    
    @SubscribeEvent
    public void onPreMotion(final MotionUpdateEvent eventPre) {
        final long delay = (long)(this.delay1.getValue() * 50.0f);
        if (!(InvManager.mc.currentScreen instanceof GuiInventory) && this.openinv.getValue()) {
            return;
        }
        if (InvManager.mc.currentScreen == null || InvManager.mc.currentScreen instanceof GuiInventory || InvManager.mc.currentScreen instanceof GuiChat) {
            if (this.timer.hasReached((float)delay) && InvManager.weaponSlot >= 36) {
                if (!InvManager.mc.player.inventoryContainer.getSlot(InvManager.weaponSlot).getHasStack()) {
                    this.getBestWeapon(InvManager.weaponSlot);
                }
                else if (!this.isBestWeapon(InvManager.mc.player.inventoryContainer.getSlot(InvManager.weaponSlot).getStack())) {
                    this.getBestWeapon(InvManager.weaponSlot);
                }
            }
            if (this.timer.hasReached((float)delay) && InvManager.pickaxeSlot >= 36) {
                this.getBestPickaxe();
            }
            if (this.timer.hasReached((float)delay) && InvManager.shovelSlot >= 36) {
                this.getBestShovel();
            }
            if (this.timer.hasReached((float)delay) && InvManager.axeSlot >= 36) {
                this.getBestAxe();
            }
            if (this.timer.hasReached((float)delay) && this.cleaner.getValue()) {
                for (int i = 9; i < 45; ++i) {
                    if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                        final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                        if (this.shouldDrop(is, i)) {
                            this.drop(i);
                            if (delay == 0L) {
                                InvManager.mc.player.closeScreen();
                            }
                            this.timer.reset();
                            if (delay > 0L) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void swap(final int slot, final int hotbarSlot) {
        InvManager.mc.playerController.windowClick(InvManager.mc.player.inventoryContainer.windowId, slot, hotbarSlot, ClickType.SWAP, (EntityPlayer)InvManager.mc.player);
    }
    
    public void drop(final int slot) {
        InvManager.mc.playerController.windowClick(InvManager.mc.player.inventoryContainer.windowId, slot, 1, ClickType.THROW, (EntityPlayer)InvManager.mc.player);
    }
    
    public boolean isBestWeapon(final ItemStack stack) {
        final float damage = this.getDamage(stack);
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.getDamage(is) > damage && (is.getItem() instanceof ItemSword || !this.sword.getValue())) {
                    return false;
                }
            }
        }
        return stack.getItem() instanceof ItemSword || !this.sword.getValue();
    }
    
    public void getBestWeapon(final int slot) {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.isBestWeapon(is) && this.getDamage(is) > 0.0f && (is.getItem() instanceof ItemSword || !this.sword.getValue())) {
                    this.swap(i, slot - 36);
                    this.timer.reset();
                    break;
                }
            }
        }
    }
    
    private float getDamage(final ItemStack stack) {
        float damage = 0.0f;
        final Item item = stack.getItem();
        if (item instanceof ItemTool) {
            final ItemTool tool = (ItemTool)item;
            damage += tool.getMaxDamage();
        }
        if (item instanceof ItemSword) {
            final ItemSword sword = (ItemSword)item;
            damage += sword.getMaxDamage();
        }
        damage += EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(16)), stack) * 1.25f + EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(20)), stack) * 0.01f;
        return damage;
    }
    
    public boolean shouldDrop(final ItemStack stack, final int slot) {
        if (stack.getDisplayName().toLowerCase().contains("/")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("\u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("§k||")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("kit")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("wool")) {
            return false;
        }
        if (stack.getDisplayName().toLowerCase().contains("\u043b\u043e\u0431\u0431\u0438")) {
            return false;
        }
        if ((slot == InvManager.weaponSlot && this.isBestWeapon(InvManager.mc.player.inventoryContainer.getSlot(InvManager.weaponSlot).getStack())) || (slot == InvManager.pickaxeSlot && this.isBestPickaxe(InvManager.mc.player.inventoryContainer.getSlot(InvManager.pickaxeSlot).getStack()) && InvManager.pickaxeSlot >= 0) || (slot == InvManager.axeSlot && this.isBestAxe(InvManager.mc.player.inventoryContainer.getSlot(InvManager.axeSlot).getStack()) && InvManager.axeSlot >= 0) || (slot == InvManager.shovelSlot && this.isBestShovel(InvManager.mc.player.inventoryContainer.getSlot(InvManager.shovelSlot).getStack()) && InvManager.shovelSlot >= 0)) {
            return false;
        }
        if (stack.getItem() instanceof ItemArmor) {
            for (int type = 1; type < 5; ++type) {
                if (InvManager.mc.player.inventoryContainer.getSlot(4 + type).getHasStack()) {
                    final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(4 + type).getStack();
                    if (isBestArmor(is, type)) {
                        continue;
                    }
                }
                if (isBestArmor(stack, type)) {
                    return false;
                }
            }
        }
        return (stack.getItem() instanceof ItemBlock && (this.getBlockCount() > this.cap.getValue() || InvManager.invalidBlocks.contains(((ItemBlock)stack.getItem()).getBlock()))) || (stack.getItem() instanceof ItemPotion && this.isBadPotion(stack)) || (stack.getItem() instanceof ItemFood && this.food.getValue() && !(stack.getItem() instanceof ItemAppleGold)) || (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor) || ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("arrow")) && this.archer.getValue()) || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("tnt") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("stick") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("egg") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("string") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("cake") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("mushroom") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("flint") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("dyePowder") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("feather") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("bucket") || (stack.getItem().getUnlocalizedNameInefficiently(stack).contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect")) || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("snow") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("fish") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("enchant") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("exp") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("shears") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("anvil") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("torch") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("seeds") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("leather") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("reeds") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("skull") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("wool") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("record") || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("snowball") || stack.getItem() instanceof ItemGlassBottle || stack.getItem().getUnlocalizedNameInefficiently(stack).contains("piston");
    }
    
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && !InvManager.invalidBlocks.contains(((ItemBlock)item).getBlock())) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
    
    public static boolean isBestArmor(final ItemStack stack, final int type) {
        String armorType = "";
        if (type == 1) {
            armorType = "helmet";
        }
        else if (type == 2) {
            armorType = "chestplate";
        }
        else if (type == 3) {
            armorType = "leggings";
        }
        else if (type == 4) {
            armorType = "boots";
        }
        if (!stack.getItem().getUnlocalizedNameInefficiently(stack).contains(armorType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
            }
        }
        return true;
    }
    
    private void getBestPickaxe() {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.isBestPickaxe(is) && InvManager.pickaxeSlot != i && !this.isBestWeapon(is)) {
                    if (!InvManager.mc.player.inventoryContainer.getSlot(InvManager.pickaxeSlot).getHasStack()) {
                        this.swap(i, InvManager.pickaxeSlot - 36);
                        this.timer.reset();
                        if (this.delay1.getValue() > 0.0f) {
                            return;
                        }
                    }
                    else if (!this.isBestPickaxe(InvManager.mc.player.inventoryContainer.getSlot(InvManager.pickaxeSlot).getStack())) {
                        this.swap(i, InvManager.pickaxeSlot - 36);
                        this.timer.reset();
                        if (this.delay1.getValue() > 0.0f) {
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private void getBestShovel() {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.isBestShovel(is) && InvManager.shovelSlot != i && !this.isBestWeapon(is)) {
                    if (!InvManager.mc.player.inventoryContainer.getSlot(InvManager.shovelSlot).getHasStack()) {
                        this.swap(i, InvManager.shovelSlot - 36);
                        this.timer.reset();
                        if (this.delay1.getValue() > 0.0f) {
                            return;
                        }
                    }
                    else if (!this.isBestShovel(InvManager.mc.player.inventoryContainer.getSlot(InvManager.shovelSlot).getStack())) {
                        this.swap(i, InvManager.shovelSlot - 36);
                        this.timer.reset();
                        if (this.delay1.getValue() > 0.0f) {
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private void getBestAxe() {
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.isBestAxe(is) && InvManager.axeSlot != i && !this.isBestWeapon(is)) {
                    if (!InvManager.mc.player.inventoryContainer.getSlot(InvManager.axeSlot).getHasStack()) {
                        this.swap(i, InvManager.axeSlot - 36);
                        this.timer.reset();
                        if (this.delay1.getValue() > 0.0f) {
                            return;
                        }
                    }
                    else if (!this.isBestAxe(InvManager.mc.player.inventoryContainer.getSlot(InvManager.axeSlot).getStack())) {
                        this.swap(i, InvManager.axeSlot - 36);
                        this.timer.reset();
                        if (this.delay1.getValue() > 0.0f) {
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private boolean isBestPickaxe(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBestShovel(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isBestAxe(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        }
        final float value = this.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            if (InvManager.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = InvManager.mc.player.inventoryContainer.getSlot(i).getStack();
                if (this.getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !this.isBestWeapon(stack)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private float getToolEffect(final ItemStack stack) {
        final Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        final ItemTool tool = (ItemTool)item;
        float value;
        if (item instanceof ItemPickaxe) {
            value = tool.getDestroySpeed(stack, Blocks.STONE.getDefaultState());
        }
        else if (item instanceof ItemSpade) {
            value = tool.getDestroySpeed(stack, Blocks.DIRT.getDefaultState());
        }
        else {
            if (!(item instanceof ItemAxe)) {
                return 1.0f;
            }
            value = tool.getDestroySpeed(stack, Blocks.LOG.getDefaultState());
        }
        value += (float)(EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(32)), stack) * 0.0075);
        value += (float)(EnchantmentHelper.getEnchantmentLevel((Enchantment)Objects.requireNonNull(Enchantment.getEnchantmentByID(34)), stack) / 100.0);
        return value;
    }
    
    private boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            for (final PotionEffect o : PotionUtils.getEffectsFromStack(stack)) {
                if (o.getPotion() == Potion.getPotionById(19) || o.getPotion() == Potion.getPotionById(7) || o.getPotion() == Potion.getPotionById(2) || o.getPotion() == Potion.getPotionById(18)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        InvManager.weaponSlot = 36;
        InvManager.pickaxeSlot = 37;
        InvManager.axeSlot = 38;
        InvManager.shovelSlot = 39;
        InvManager.invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, (Block)Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, (Block)Blocks.WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, (Block)Blocks.STONE_SLAB, (Block)Blocks.WOODEN_SLAB, (Block)Blocks.STONE_SLAB2, (Block)Blocks.RED_MUSHROOM, (Block)Blocks.BROWN_MUSHROOM, (Block)Blocks.YELLOW_FLOWER, (Block)Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, (Block)Blocks.STAINED_GLASS_PANE, Blocks.IRON_BARS, (Block)Blocks.CACTUS, Blocks.LADDER, Blocks.WEB);
    }
}
