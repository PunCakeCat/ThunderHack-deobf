//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;

public class AutoTPaccept extends Module
{
    public Setting<Boolean> onlyFriends;
    
    public AutoTPaccept() {
        super("AutoTPaccept", "\u041f\u0440\u0438\u043d\u0438\u043c\u0430\u0435\u0442 \u0442\u043f \u0430\u0432\u0442\u043e\u043c\u0430\u0442\u043e\u043c", Category.FUNNYGAME, true, false, false);
        this.onlyFriends = (Setting<Boolean>)this.register(new Setting("onlyFriends", (T)Boolean.TRUE));
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getType() == ChatType.GAME_INFO || this.tryProcessChat(packet.getChatComponent().getFormattedText(), packet.getChatComponent().getUnformattedText())) {}
        }
    }
    
    private boolean tryProcessChat(final String message, final String unformatted) {
        String out = message;
        out = message;
        if (Util.mc.player == null) {
            return false;
        }
        if (out.contains("\u043f\u0440\u043e\u0441\u0438\u0442 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u043a \u0412\u0430\u043c.")) {
            if (this.onlyFriends.getValue()) {
                if (Thunderhack.friendManager.isFriend(ThunderUtils.solvename(out))) {
                    AutoTPaccept.mc.player.sendChatMessage("/tpaccept");
                }
            }
            else {
                AutoTPaccept.mc.player.sendChatMessage("/tpaccept");
            }
        }
        return true;
    }
}
