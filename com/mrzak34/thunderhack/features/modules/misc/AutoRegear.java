//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.command.commands.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class AutoRegear extends Module
{
    private HashMap<Integer, String> planInventory;
    private final HashMap<Integer, String> containerInv;
    private ArrayList<Integer> sortItems;
    private int delayTimeTicks;
    private int stepNow;
    private boolean openedBefore;
    private boolean finishSort;
    private boolean doneBefore;
    public Setting<Integer> tickDelay;
    public Setting<Integer> switchForTick;
    public Setting<Boolean> debugMode;
    public Setting<Boolean> infoMsgs;
    public Setting<Boolean> closeAfter;
    public Setting<Boolean> invasive;
    public Setting<Boolean> confirmSort;
    public Setting<Boolean> enderChest;
    
    public AutoRegear() {
        super("AutoRegear", "\u0440\u0435\u0433\u0438\u0440\u0438\u0442 \u0442\u0435\u0431\u044f \u0438\u0437-\u0448\u0430\u043b\u043a\u0435\u0440\u0430", Category.MISC, true, false, false);
        this.planInventory = new HashMap<Integer, String>();
        this.containerInv = new HashMap<Integer, String>();
        this.sortItems = new ArrayList<Integer>();
        this.tickDelay = (Setting<Integer>)this.register(new Setting("Tick Delay", (T)50, (T)0, (T)20));
        this.switchForTick = (Setting<Integer>)this.register(new Setting("Switch Per Tick", (T)1, (T)1, (T)100));
        this.debugMode = (Setting<Boolean>)this.register(new Setting("Debug Mode", (T)false));
        this.infoMsgs = (Setting<Boolean>)this.register(new Setting("Info Msgs", (T)false));
        this.closeAfter = (Setting<Boolean>)this.register(new Setting("Close After", (T)false));
        this.invasive = (Setting<Boolean>)this.register(new Setting("saInvasive", (T)false));
        this.confirmSort = (Setting<Boolean>)this.register(new Setting("Confirm Sort", (T)false));
        this.enderChest = (Setting<Boolean>)this.register(new Setting("enderChest", (T)false));
    }
    
    @Override
    public void onEnable() {
        final String curConfigName = KitCommand.getCurrentSet();
        if (curConfigName.equals("")) {
            this.disable();
            return;
        }
        if (this.infoMsgs.getValue()) {
            Command.sendMessage("Config " + curConfigName + " actived");
        }
        final String inventoryConfig = KitCommand.getInventoryKit(curConfigName);
        if (inventoryConfig.equals("")) {
            this.disable();
            return;
        }
        final String[] inventoryDivided = inventoryConfig.split(" ");
        this.planInventory = new HashMap<Integer, String>();
        final HashMap<String, Integer> nItems = new HashMap<String, Integer>();
        for (int i = 0; i < inventoryDivided.length; ++i) {
            if (!inventoryDivided[i].contains("air")) {
                this.planInventory.put(i, inventoryDivided[i]);
                if (nItems.containsKey(inventoryDivided[i])) {
                    nItems.put(inventoryDivided[i], nItems.get(inventoryDivided[i]) + 1);
                }
                else {
                    nItems.put(inventoryDivided[i], 1);
                }
            }
        }
        this.delayTimeTicks = 0;
        final boolean b = false;
        this.doneBefore = b;
        this.openedBefore = b;
    }
    
    @Override
    public void onDisable() {
        if (this.infoMsgs.getValue() && this.planInventory.size() > 0) {
            Command.sendMessage("AutoSort Turned Off!");
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.delayTimeTicks < this.tickDelay.getValue()) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        if (this.planInventory.size() == 0) {
            this.disable();
        }
        if ((AutoRegear.mc.player.openContainer instanceof ContainerChest && (this.enderChest.getValue() || !((ContainerChest)AutoRegear.mc.player.openContainer).getLowerChestInventory().getDisplayName().getUnformattedText().equals("Ender Chest"))) || AutoRegear.mc.player.openContainer instanceof ContainerShulkerBox) {
            this.sortInventoryAlgo();
        }
        else {
            this.openedBefore = false;
        }
    }
    
    private void sortInventoryAlgo() {
        if (!this.openedBefore) {
            if (this.infoMsgs.getValue() && !this.doneBefore) {
                Command.sendMessage("Start sorting inventory...");
            }
            final int maxValue = (AutoRegear.mc.player.openContainer instanceof ContainerChest) ? ((ContainerChest)AutoRegear.mc.player.openContainer).getLowerChestInventory().getSizeInventory() : 27;
            for (int i = 0; i < maxValue; ++i) {
                final ItemStack item = (ItemStack)AutoRegear.mc.player.openContainer.getInventory().get(i);
                this.containerInv.put(i, Objects.requireNonNull(item.getItem().getRegistryName()).toString() + item.getMetadata());
            }
            this.openedBefore = true;
            final HashMap<Integer, String> inventoryCopy = this.getInventoryCopy(maxValue);
            final HashMap<Integer, String> aimInventory = this.getInventoryCopy(maxValue, this.planInventory);
            this.sortItems = this.getInventorySort(inventoryCopy, aimInventory, maxValue);
            if (this.sortItems.size() == 0 && !this.doneBefore) {
                this.finishSort = false;
                if (this.infoMsgs.getValue()) {
                    Command.sendMessage("Inventory already sorted...");
                }
                if (this.closeAfter.getValue()) {
                    AutoRegear.mc.player.closeScreen();
                }
            }
            else {
                this.finishSort = true;
                this.stepNow = 0;
            }
            this.openedBefore = true;
        }
        else if (this.finishSort) {
            int j = 0;
            while (j < this.switchForTick.getValue()) {
                if (this.sortItems.size() != 0) {
                    final int slotChange = this.sortItems.get(this.stepNow++);
                    AutoRegear.mc.playerController.windowClick(AutoRegear.mc.player.openContainer.windowId, slotChange, 0, ClickType.PICKUP, (EntityPlayer)AutoRegear.mc.player);
                }
                if (this.stepNow == this.sortItems.size()) {
                    if (this.confirmSort.getValue() && !this.doneBefore) {
                        this.openedBefore = false;
                        this.finishSort = false;
                        this.doneBefore = true;
                        this.checkLastItem();
                        return;
                    }
                    this.finishSort = false;
                    if (this.infoMsgs.getValue()) {
                        Command.sendMessage("Inventory sorted");
                    }
                    this.checkLastItem();
                    this.doneBefore = false;
                    if (this.closeAfter.getValue()) {
                        AutoRegear.mc.player.closeScreen();
                    }
                }
                else {
                    ++j;
                }
            }
        }
    }
    
    private void checkLastItem() {
        if (this.sortItems.size() != 0) {
            final int slotChange = this.sortItems.get(this.sortItems.size() - 1);
            if (((ItemStack)AutoRegear.mc.player.openContainer.getInventory().get(slotChange)).isEmpty()) {
                AutoRegear.mc.playerController.windowClick(0, slotChange, 0, ClickType.PICKUP, (EntityPlayer)AutoRegear.mc.player);
            }
        }
    }
    
    private ArrayList<Integer> getInventorySort(final HashMap<Integer, String> copyInventory, final HashMap<Integer, String> planInventoryCopy, final int startValues) {
        final ArrayList<Integer> planMove = new ArrayList<Integer>();
        final HashMap<String, Integer> nItemsCopy = new HashMap<String, Integer>();
        for (final String value : planInventoryCopy.values()) {
            if (nItemsCopy.containsKey(value)) {
                nItemsCopy.put(value, nItemsCopy.get(value) + 1);
            }
            else {
                nItemsCopy.put(value, 1);
            }
        }
        final ArrayList<Integer> ignoreValues = new ArrayList<Integer>();
        final int[] listValue = new int[planInventoryCopy.size()];
        int id = 0;
        for (final int idx : planInventoryCopy.keySet()) {
            listValue[id++] = idx;
        }
        for (final int item : listValue) {
            if (copyInventory.get(item).equals(planInventoryCopy.get(item))) {
                ignoreValues.add(item);
                nItemsCopy.put(planInventoryCopy.get(item), nItemsCopy.get(planInventoryCopy.get(item)) - 1);
                if (nItemsCopy.get(planInventoryCopy.get(item)) == 0) {
                    nItemsCopy.remove(planInventoryCopy.get(item));
                }
                planInventoryCopy.remove(item);
            }
        }
        String pickedItem = null;
        for (int i = startValues; i < startValues + copyInventory.size(); ++i) {
            if (!ignoreValues.contains(i)) {
                final String itemCheck = copyInventory.get(i);
                final Optional<Map.Entry<Integer, String>> momentAim = planInventoryCopy.entrySet().stream().filter(x -> x.getValue().equals(itemCheck)).findFirst();
                if (momentAim.isPresent()) {
                    if (pickedItem == null) {
                        planMove.add(i);
                    }
                    final int aimKey = momentAim.get().getKey();
                    planMove.add(aimKey);
                    if (pickedItem == null || !pickedItem.equals(itemCheck)) {
                        ignoreValues.add(aimKey);
                    }
                    nItemsCopy.put(itemCheck, nItemsCopy.get(itemCheck) - 1);
                    if (nItemsCopy.get(itemCheck) == 0) {
                        nItemsCopy.remove(itemCheck);
                    }
                    copyInventory.put(i, copyInventory.get(aimKey));
                    copyInventory.put(aimKey, itemCheck);
                    if (!copyInventory.get(aimKey).equals("minecraft:air0")) {
                        if (i >= startValues + copyInventory.size()) {
                            continue;
                        }
                        pickedItem = copyInventory.get(i);
                        --i;
                    }
                    else {
                        pickedItem = null;
                    }
                    planInventoryCopy.remove(aimKey);
                }
                else if (pickedItem != null) {
                    planMove.add(i);
                    copyInventory.put(i, pickedItem);
                    pickedItem = null;
                }
            }
        }
        if (planMove.size() != 0 && planMove.get(planMove.size() - 1).equals(planMove.get(planMove.size() - 2))) {
            planMove.remove(planMove.size() - 1);
        }
        final Object[] keyList = this.containerInv.keySet().toArray();
        for (int values = 0; values < keyList.length; ++values) {
            final int itemC = (int)keyList[values];
            if (nItemsCopy.containsKey(this.containerInv.get(itemC))) {
                final int start = planInventoryCopy.entrySet().stream().filter(x -> x.getValue().equals(this.containerInv.get(itemC))).findFirst().get().getKey();
                if (this.invasive.getValue() || ((ItemStack)AutoRegear.mc.player.openContainer.getInventory().get(start)).isEmpty()) {
                    planMove.add(start);
                    planMove.add(itemC);
                    planMove.add(start);
                    nItemsCopy.put(planInventoryCopy.get(start), nItemsCopy.get(planInventoryCopy.get(start)) - 1);
                    if (nItemsCopy.get(planInventoryCopy.get(start)) == 0) {
                        nItemsCopy.remove(planInventoryCopy.get(start));
                    }
                    planInventoryCopy.remove(start);
                }
            }
        }
        if (this.debugMode.getValue()) {
            for (final int valuePath : planMove) {
                Command.sendMessage(Integer.toString(valuePath));
            }
        }
        return planMove;
    }
    
    private HashMap<Integer, String> getInventoryCopy(final int startPoint) {
        final HashMap<Integer, String> output = new HashMap<Integer, String>();
        for (int sizeInventory = AutoRegear.mc.player.inventory.mainInventory.size(), i = 0; i < sizeInventory; ++i) {
            final int value = i + startPoint + ((i < 9) ? (sizeInventory - 9) : -9);
            final ItemStack item = (ItemStack)AutoRegear.mc.player.openContainer.getInventory().get(value);
            output.put(value, Objects.requireNonNull(item.getItem().getRegistryName()).toString() + item.getMetadata());
        }
        return output;
    }
    
    private HashMap<Integer, String> getInventoryCopy(final int startPoint, final HashMap<Integer, String> inventory) {
        final HashMap<Integer, String> output = new HashMap<Integer, String>();
        final int sizeInventory = AutoRegear.mc.player.inventory.mainInventory.size();
        for (final int val : inventory.keySet()) {
            output.put(val + startPoint + ((val < 9) ? (sizeInventory - 9) : -9), inventory.get(val));
        }
        return output;
    }
}
