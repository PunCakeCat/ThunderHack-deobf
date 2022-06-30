//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.ffpshit;

import net.minecraftforge.fml.relauncher.*;
import java.util.concurrent.locks.*;
import io.netty.buffer.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.network.*;
import java.util.*;
import io.netty.channel.*;
import net.minecraftforge.fml.common.eventhandler.*;

@SideOnly(Side.CLIENT)
public class NetworkHandler
{
    private boolean isConnected;
    private NetworkManager networkManager;
    private ReadWriteLock[] outbound_lock;
    private ReadWriteLock[] inbound_lock;
    private List<PacketListener>[] outbound_listeners;
    private List<PacketListener>[] inbound_listeners;
    
    public NetworkHandler() {
        this.isConnected = false;
        this.networkManager = null;
        this.outbound_listeners = (List<PacketListener>[])new List[33];
        this.outbound_lock = new ReadWriteLock[33];
        for (int i = 0; i < 33; ++i) {
            this.outbound_lock[i] = new ReentrantReadWriteLock();
        }
        this.inbound_listeners = (List<PacketListener>[])new List[80];
        this.inbound_lock = new ReadWriteLock[80];
        for (int i = 0; i < 80; ++i) {
            this.inbound_lock[i] = new ReentrantReadWriteLock();
        }
    }
    
    public Packet<?> packetReceived(final EnumPacketDirection direction, final int id, Packet<?> packet, final ByteBuf buf) {
        List<PacketListener> listeners;
        ReadWriteLock lock;
        if (direction == EnumPacketDirection.CLIENTBOUND) {
            listeners = this.inbound_listeners[id];
            lock = this.inbound_lock[id];
        }
        else {
            listeners = this.outbound_listeners[id];
            lock = this.outbound_lock[id];
        }
        if (listeners != null) {
            int buff_start = 0;
            if (buf != null) {
                buff_start = buf.readerIndex();
            }
            lock.readLock().lock();
            final int size = listeners.size();
            lock.readLock().unlock();
            for (int i = 0; i < size; ++i) {
                lock.readLock().lock();
                final PacketListener l = listeners.get(i - (size - listeners.size()));
                lock.readLock().unlock();
                if (buf != null) {
                    buf.readerIndex(buff_start);
                }
                if ((packet = l.packetReceived(direction, id, packet, buf)) == null) {
                    return null;
                }
            }
        }
        return packet;
    }
    
    public void registerListener(final EnumPacketDirection direction, final PacketListener listener, final int... ids) {
        List<PacketListener>[] listeners;
        ReadWriteLock[] locks;
        if (direction == EnumPacketDirection.CLIENTBOUND) {
            listeners = this.inbound_listeners;
            locks = this.inbound_lock;
        }
        else {
            listeners = this.outbound_listeners;
            locks = this.outbound_lock;
        }
        for (final int id : ids) {
            try {
                locks[id].writeLock().lock();
                if (listeners[id] == null) {
                    listeners[id] = new ArrayList<PacketListener>();
                }
                if (!listeners[id].contains(listener)) {
                    listeners[id].add(listener);
                }
            }
            finally {
                locks[id].writeLock().unlock();
            }
        }
    }
    
    public void unregisterListener(final EnumPacketDirection direction, final PacketListener listener) {
        List<PacketListener>[] listeners;
        ReadWriteLock[] locks;
        if (direction == EnumPacketDirection.CLIENTBOUND) {
            listeners = this.inbound_listeners;
            locks = this.inbound_lock;
        }
        else {
            listeners = this.outbound_listeners;
            locks = this.outbound_lock;
        }
        for (int i = 0; i < listeners.length; ++i) {
            try {
                locks[i].writeLock().lock();
                if (listeners[i] != null) {
                    listeners[i].remove(listener);
                    if (listeners[i].size() == 0) {
                        listeners[i] = null;
                    }
                }
            }
            finally {
                locks[i].writeLock().unlock();
            }
        }
    }
    
    public void unregisterListener(final EnumPacketDirection direction, final PacketListener listener, final int... ids) {
        List<PacketListener>[] listeners;
        ReadWriteLock[] locks;
        if (direction == EnumPacketDirection.CLIENTBOUND) {
            listeners = this.inbound_listeners;
            locks = this.inbound_lock;
        }
        else {
            listeners = this.outbound_listeners;
            locks = this.outbound_lock;
        }
        for (final int id : ids) {
            try {
                locks[id].writeLock().lock();
                if (listeners[id] != null) {
                    listeners[id].remove(listener);
                    if (listeners[id].size() == 0) {
                        listeners[id] = null;
                    }
                }
            }
            finally {
                locks[id].writeLock().unlock();
            }
        }
    }
    
    public void sendPacket(final Packet<?> packet) {
        if (this.networkManager != null) {
            this.networkManager.sendPacket((Packet)packet);
        }
    }
    
    public INetHandler getNetHandler() {
        if (this.networkManager != null) {
            return this.networkManager.getNetHandler();
        }
        return null;
    }
    
    public void disconnect() {
        if (this.networkManager != null) {
            this.networkManager.closeChannel((ITextComponent)new TextComponentString("You have been successfully disconnected from server"));
        }
    }
    
    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (!this.isConnected) {
            final ChannelPipeline pipeline = event.getManager().channel().pipeline();
            try {
                ChannelHandler old = pipeline.get("decoder");
                if (old != null && old instanceof NettyPacketDecoder) {
                    final InboundInterceptor spoof = new InboundInterceptor(this, EnumPacketDirection.CLIENTBOUND);
                    pipeline.replace("decoder", "decoder", (ChannelHandler)spoof);
                }
                old = pipeline.get("encoder");
                if (old != null && old instanceof NettyPacketEncoder) {
                    final OutboundInterceptor spoof2 = new OutboundInterceptor(this, EnumPacketDirection.SERVERBOUND);
                    pipeline.replace("encoder", "encoder", (ChannelHandler)spoof2);
                }
                this.networkManager = event.getManager();
                this.isConnected = true;
            }
            catch (NoSuchElementException ex) {}
        }
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.isConnected = false;
        this.networkManager = null;
    }
    
    public boolean isConnected() {
        return this.isConnected;
    }
}
