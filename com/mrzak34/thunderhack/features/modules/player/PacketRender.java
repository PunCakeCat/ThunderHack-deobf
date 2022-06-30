//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class PacketRender extends Module
{
    private static float yaw;
    private static float pitch;
    
    public PacketRender() {
        super("PacketRender", "\u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u043f\u0430\u043a\u0435\u0442\u044b-CPacketPlayerRotation", Module.Category.PLAYER, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
    }
    
    public static float getYaw() {
        return PacketRender.yaw;
    }
    
    public static float getPitch() {
        return PacketRender.pitch;
    }
    
    public static void setYaw(final float yaw) {
        PacketRender.yaw = yaw;
    }
    
    public static void setPitch(final float pitch) {
        PacketRender.pitch = pitch;
    }
    
    static {
        PacketRender.yaw = 0.0f;
        PacketRender.pitch = 0.0f;
    }
}
