//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import java.util.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.util.*;

public class ChorusDelay extends Module
{
    boolean startCancel;
    ArrayList<Packet<?>> packets;
    public final Setting<ColorSetting> Color;
    boolean isSendingPackets;
    int x;
    int y;
    int z;
    
    public ChorusDelay() {
        super("ChorusDelay", "\u0445\u043e\u0440\u0443\u0441 \u044d\u043a\u0441\u043f\u043b\u043e\u0438\u0442", Category.RENDER, true, false, false);
        this.startCancel = false;
        this.packets = new ArrayList<Packet<?>>();
        this.Color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.isSendingPackets = false;
    }
    
    @Override
    public void onUpdate() {
        if (ChorusDelay.mc.world == null || ChorusDelay.mc.player == null) {
            this.packets.clear();
        }
        else if (ChorusDelay.mc.gameSettings.keyBindSneak.isPressed()) {
            this.isSendingPackets = true;
            for (int i = 0; i < this.packets.size(); ++i) {
                ChorusDelay.mc.player.connection.sendPacket((Packet)this.packets.get(i));
            }
            this.isSendingPackets = false;
            this.packets.clear();
            this.startCancel = false;
        }
    }
    
    @Override
    public void onEnable() {
        final boolean b = false;
        this.isSendingPackets = b;
        this.startCancel = b;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        this.startCancel = true;
        this.x = (int)ChorusDelay.mc.player.posX;
        this.y = (int)ChorusDelay.mc.player.posY;
        this.z = (int)ChorusDelay.mc.player.posZ;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (ChorusDelay.mc.world == null || ChorusDelay.mc.player == null || !this.startCancel || this.isSendingPackets) {
            return;
        }
        final Packet<?> pack = (Packet<?>)event.getPacket();
        if (pack instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook pl = (SPacketPlayerPosLook)pack;
            if (this.x != (int)pl.x || this.y != pl.y || this.z != pl.z) {
                ChorusDelay.mc.player.setPosition(pl.x, pl.y, pl.z);
            }
            event.setCanceled(true);
        }
        else if (event.getPacket() instanceof CPacketPlayer) {
            this.packets.add(pack);
            event.setCanceled(true);
        }
        else if (event.getPacket() instanceof CPacketConfirmTeleport) {
            this.packets.add(pack);
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.startCancel) {
            RenderUtil.drawBoundingBox(ChorusDelay.mc.player.getEntityBoundingBox(), 2.0f, this.Color.getValue().getRawColor());
        }
    }
}
