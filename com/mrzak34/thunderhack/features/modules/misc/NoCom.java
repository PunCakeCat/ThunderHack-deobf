//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.chunk.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.notification.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoCom extends Module
{
    public Setting<Integer> delay;
    public Setting<Integer> loop;
    public Setting<Integer> startX;
    public Setting<Integer> startZ;
    private BlockPos playerPos;
    private int renderDistanceDiameter;
    private long time;
    private int count;
    private int x;
    private int z;
    
    public NoCom() {
        super("NoCom", "\u044d\u043a\u0441\u043f\u043b\u043e\u0438\u0442 \u0434\u043b\u044f \u043f\u043e\u0438\u0441\u043a\u0430-\u0438\u0433\u0440\u043e\u043a\u043e\u0432", Category.MISC, true, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)200, (T)0, (T)1000));
        this.loop = (Setting<Integer>)this.register(new Setting("LoopPerTick", (T)1, (T)1, (T)100));
        this.startX = (Setting<Integer>)this.register(new Setting("StartX", (T)0, (T)0, (T)1000000));
        this.startZ = (Setting<Integer>)this.register(new Setting("StartZ", (T)0, (T)0, (T)1000000));
        this.playerPos = null;
        this.renderDistanceDiameter = 0;
        this.time = 0L;
        this.count = 0;
    }
    
    @Override
    public void onUpdate() {
        this.playerPos = new BlockPos(NoCom.mc.player.posX, NoCom.mc.player.posY - 1.0, NoCom.mc.player.posZ);
        if (this.renderDistanceDiameter == 0) {
            final IChunkProviderClient chunkProviderClient = (IChunkProviderClient)NoCom.mc.world.getChunkProvider();
            this.renderDistanceDiameter = (int)Math.sqrt(chunkProviderClient.getLoadedChunks().size());
        }
        if (this.time == 0L) {
            this.time = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.time > this.delay.getValue()) {
            for (int i = 0; i < this.loop.getValue(); ++i) {
                final int x = this.getSpiralCoords(this.count)[0] * this.renderDistanceDiameter * 16 + this.startX.getValue();
                final int z = this.getSpiralCoords(this.count)[1] * this.renderDistanceDiameter * 16 + this.startZ.getValue();
                final BlockPos position = new BlockPos(x, 0, z);
                this.x = x;
                this.z = z;
                NoCom.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.playerPos, EnumFacing.EAST));
                NoCom.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, position, EnumFacing.EAST));
                this.playerPos = new BlockPos(NoCom.mc.player.posX, NoCom.mc.player.posY - 1.0, NoCom.mc.player.posZ);
                this.time = System.currentTimeMillis();
                ++this.count;
            }
        }
    }
    
    @SubscribeEvent
    public final void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketBlockChange) {
            final int x = ((SPacketBlockChange)event.getPacket()).getBlockPosition().getX();
            final int z = ((SPacketBlockChange)event.getPacket()).getBlockPosition().getZ();
            final IChunkProviderClient chunkProviderClient = (IChunkProviderClient)NoCom.mc.world.getChunkProvider();
            for (final Chunk chunk : chunkProviderClient.getLoadedChunks().values()) {
                if (chunk.x == x / 16 || chunk.z == z / 16) {
                    return;
                }
            }
            final String shittytext = "\u0418\u0433\u0440\u043e\u043a \u043d\u0430\u0439\u0434\u0435\u043d \u043d\u0430 X: " + ChatFormatting.GREEN + x + ChatFormatting.RESET + " Z: " + ChatFormatting.GREEN + z;
            Command.sendMessage(shittytext);
            if (Thunderhack.moduleManager.getModuleByClass(NotificationManager.class).isEnabled()) {
                NotificationManager.publicity("NoCom", shittytext, 3, NotificationType.SUCCESS);
            }
        }
    }
    
    private int[] getSpiralCoords(int n) {
        int x = 0;
        int z = 0;
        int d = 1;
        int lineNumber = 1;
        int[] coords = { 0, 0 };
        for (int i = 0; i < n; ++i) {
            if (2 * x * d < lineNumber) {
                x += d;
                coords = new int[] { x, z };
            }
            else if (2 * z * d < lineNumber) {
                z += d;
                coords = new int[] { x, z };
            }
            else {
                d *= -1;
                ++lineNumber;
                ++n;
            }
        }
        return coords;
    }
    
    @Override
    public void onEnable() {
        this.playerPos = null;
        this.count = 0;
        this.time = 0L;
    }
    
    @Override
    public void onDisable() {
        this.playerPos = null;
        this.count = 0;
        this.time = 0L;
    }
    
    @Override
    public String getDisplayInfo() {
        return this.x + " , " + this.z;
    }
}
