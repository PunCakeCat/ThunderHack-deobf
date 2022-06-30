//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoFish extends Module
{
    private int rodSlot;
    
    public AutoFish() {
        super("AutoFish", "\u043f\u0440\u0438\u0437\u043d\u0430\u0439\u0441\u044f \u0437\u0430\u0445\u043e\u0442\u0435\u043b", Category.MISC, true, false, false);
        this.rodSlot = -1;
    }
    
    @Override
    public void onEnable() {
        if (nullCheck()) {
            this.toggle();
            return;
        }
        this.rodSlot = ItemUtil.findItem(ItemFishingRod.class);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.NEUTRAL && packet.getSound() == SoundEvents.ENTITY_BOBBER_SPLASH) {
                if (this.rodSlot == -1) {
                    this.rodSlot = ItemUtil.findItem(ItemFishingRod.class);
                }
                if (this.rodSlot != -1) {
                    final int startSlot = AutoFish.mc.player.inventory.currentItem;
                    AutoFish.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(this.rodSlot));
                    AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                    AutoFish.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    AutoFish.mc.player.swingArm(EnumHand.MAIN_HAND);
                    if (startSlot != -1) {
                        AutoFish.mc.getConnection().sendPacket((Packet)new CPacketHeldItemChange(startSlot));
                    }
                }
            }
        }
    }
}
