//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.ffpshit;

import net.minecraftforge.fml.relauncher.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import java.io.*;

@SideOnly(Side.CLIENT)
public class OutboundInterceptor extends NettyPacketEncoder
{
    private final EnumPacketDirection direction;
    private NetworkHandler handler;
    private boolean isPlay;
    
    public OutboundInterceptor(final NetworkHandler handler, final EnumPacketDirection direction) {
        super(direction);
        this.handler = handler;
        this.direction = direction;
        this.isPlay = false;
    }
    
    protected void encode(final ChannelHandlerContext context, Packet<?> packet, final ByteBuf out) throws IOException, Exception {
        if (!this.isPlay) {
            final EnumConnectionState state = (EnumConnectionState)context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
            this.isPlay = (state == EnumConnectionState.PLAY);
        }
        if (this.isPlay) {
            final int id = ((EnumConnectionState)context.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacketId(this.direction, (Packet)packet);
            packet = (Packet<?>)this.handler.packetReceived(this.direction, id, (Packet)packet, (ByteBuf)null);
            if (packet == null) {
                return;
            }
        }
        super.encode(context, (Packet)packet, out);
    }
}
