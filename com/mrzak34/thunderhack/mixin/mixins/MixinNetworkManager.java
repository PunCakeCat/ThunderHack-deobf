//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacketPre(final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Send event = new PacketEvent.Send(0, (Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
        else if (event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            PacketRender.setYaw(((CPacketPlayer)event.getPacket()).getYaw(0.0f));
            PacketRender.setPitch(((CPacketPlayer)event.getPacket()).getPitch(0.0f));
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadPre(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Receive event = new PacketEvent.Receive(0, (Packet)packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
}
