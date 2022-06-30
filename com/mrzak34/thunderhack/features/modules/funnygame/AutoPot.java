//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class AutoPot extends Module
{
    public Setting<Integer> triggerhealth;
    private Setting<rmode> rMode;
    public Setting<Integer> delay;
    public Timer timer;
    public Timer alerttimer;
    
    public AutoPot() {
        super("AutoCappuccino", "\u0430\u0432\u0442\u043e\u043a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e \u0434\u043b\u044f-\u0444\u0430\u043d\u0433\u0435\u0439\u043c\u0430", Category.FUNNYGAME, true, false, false);
        this.triggerhealth = (Setting<Integer>)this.register(new Setting("TriggerHealth", (T)10, (T)1, (T)36));
        this.rMode = (Setting<rmode>)this.register(new Setting("Mode", (T)rmode.Inventory));
        this.delay = (Setting<Integer>)this.register(new Setting("delay", (T)200, (T)1, (T)2000));
        this.timer = new Timer();
        this.alerttimer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (AutoPot.mc.player.getHealth() < this.triggerhealth.getValue() && this.timer.passedMs(this.delay.getValue()) && InventoryUtil.getCappuchinoAtHotbar() != -1) {
            final int hotbarslot = AutoPot.mc.player.inventory.currentItem;
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getCappuchinoAtHotbar()));
            AutoPot.mc.playerController.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            AutoPot.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(hotbarslot));
            this.timer.reset();
        }
        if (InventoryUtil.getCappuchinoAtHotbar() == -1 && this.alerttimer.passedMs(1000L)) {
            Command.sendMessage("\u041d\u0435\u043c\u0430 \u0437\u0435\u043b\u0435\u043a!!!!");
            AutoPot.mc.world.playSound(PlayerUtil.getPlayerPos(), SoundEvents.ENTITY_BLAZE_HURT, SoundCategory.AMBIENT, 150.0f, 10.0f, true);
            this.alerttimer.reset();
        }
    }
    
    public enum rmode
    {
        Inventory, 
        OnlyHotbar;
    }
}
