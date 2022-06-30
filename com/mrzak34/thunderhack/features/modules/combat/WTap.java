//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WTap extends Module
{
    public WTap() {
        super("WTap", "\u041f\u0440\u043e\u0436\u0438\u043c\u0430\u0435\u0442 W-\u043f\u043e\u0441\u043b\u0435 \u0443\u0434\u0430\u0440\u0430", Category.COMBAT, true, false, false);
    }
    
    @SubscribeEvent
    public void onSendPacket(final PacketEvent event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                WTap.mc.player.setSprinting(false);
                WTap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WTap.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                WTap.mc.player.setSprinting(true);
                WTap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WTap.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
        }
    }
}
