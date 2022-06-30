//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.ffpshit;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.*;
import io.netty.buffer.*;

@SideOnly(Side.CLIENT)
public interface PacketListener
{
    Packet<?> packetReceived(final EnumPacketDirection p0, final int p1, final Packet<?> p2, final ByteBuf p3);
}
