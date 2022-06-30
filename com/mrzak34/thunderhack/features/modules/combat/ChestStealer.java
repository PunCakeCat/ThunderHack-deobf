//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChestStealer extends Module
{
    TimerHelper timer;
    public Setting<Integer> delayed;
    
    public ChestStealer() {
        super("ChestStealer", "\u0421\u0442\u0438\u043b\u0438\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b-\u0438\u0437 \u0441\u0443\u043d\u0434\u0443\u043a\u0430", Category.MISC, true, false, false);
        this.timer = new TimerHelper();
        this.delayed = (Setting<Integer>)this.register(new Setting("Delay", (T)100, (T)0, (T)1000));
    }
    
    @SubscribeEvent
    public void onMotionUpdateEventPre(final MotionUpdateEvent event) {
        if (Util.mc.player.openContainer != null && Util.mc.player.openContainer instanceof ContainerChest) {
            final ContainerChest container = (ContainerChest)Util.mc.player.openContainer;
            for (int i = 0; i < container.inventorySlots.size(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i).getItem() != Item.getItemById(0) && this.timer.hasReached(this.delayed.getValue())) {
                    ChestStealer.mc.playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)Util.mc.player);
                    this.timer.reset();
                }
                else if (this.empty((Container)container)) {
                    Util.mc.player.closeScreen();
                }
            }
        }
    }
    
    public boolean empty(final Container container) {
        boolean voll = true;
        int n;
        for (int slotAmount = n = ((container.inventorySlots.size() == 90) ? 54 : 27), i = 0; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                voll = false;
            }
        }
        return voll;
    }
}
