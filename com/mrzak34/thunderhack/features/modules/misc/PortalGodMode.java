//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PortalGodMode extends Module
{
    public PortalGodMode() {
        super("PortalGodMode", "\u0431\u0435\u0441\u0441\u043c\u0435\u0440\u0442\u0438\u0435 \u043f\u043e\u043a\u0430 \u0442\u044b \u0432 -\u043f\u043e\u0440\u0442\u0430\u043b\u0435", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketConfirmTeleport) {
            event.setCanceled(true);
        }
    }
}
