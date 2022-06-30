//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.concurrent.atomic.*;
import net.minecraftforge.fml.common.gameevent.*;
import com.mrzak34.thunderhack.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;

public class TickShift extends Module
{
    public Setting<Float> timer;
    public Setting<Integer> packets;
    public Setting<Integer> lagTime;
    public Setting<Boolean> sneaking;
    public Setting<Boolean> cancelGround;
    public Setting<Boolean> cancelRotations;
    private final AtomicLong lagTimer;
    private int ticks;
    
    public TickShift() {
        super("TickShift", "\u0442\u0438\u043a\u0448\u0438\u0444\u0442 \u044d\u043a\u0441\u043f\u043b\u043e\u0438\u0442", Category.MISC, true, false, false);
        this.timer = (Setting<Float>)this.register(new Setting("Timer", (T)2.0f, (T)0.1f, (T)100.0f));
        this.packets = (Setting<Integer>)this.register(new Setting("Packets", (T)20, (T)0, (T)1000));
        this.lagTime = (Setting<Integer>)this.register(new Setting("LagTime", (T)1000, (T)0, (T)10000));
        this.sneaking = (Setting<Boolean>)this.register(new Setting("Sneaking", (T)false));
        this.cancelGround = (Setting<Boolean>)this.register(new Setting("v", (T)false));
        this.cancelRotations = (Setting<Boolean>)this.register(new Setting("CancelRotation", (T)false));
        this.lagTimer = new AtomicLong();
    }
    
    public boolean passed(final int ms) {
        return System.currentTimeMillis() - this.lagTimer.get() >= ms;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent e) {
        if (TickShift.mc.player == null || TickShift.mc.world == null || !this.passed(this.lagTime.getValue())) {
            this.rozetked();
        }
        else if (this.ticks <= 0 || noMovementKeys() || (!this.sneaking.getValue() && TickShift.mc.player.isSneaking())) {
            Thunderhack.TICK_TIMER = 1.0f;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
    }
    
    @SubscribeEvent
    public void onEventMove(final EventMove e) {
        Thunderhack.TICK_TIMER = 1.0f;
        final int maxPackets = this.packets.getValue();
        this.ticks = ((this.ticks >= maxPackets) ? maxPackets : (this.ticks + 1));
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.lagTimer.set(System.currentTimeMillis());
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer.PositionRotation) {
            this.hth(e, true);
        }
        if (e.getPacket() instanceof CPacketPlayer.Rotation) {
            if (this.cancelRotations.getValue() && (this.cancelGround.getValue() || ((CPacketPlayer.Rotation)e.getPacket()).isOnGround() == TickShift.mc.player.onGround)) {
                e.setCanceled(true);
            }
            else {
                this.hth(e, false);
            }
        }
        if (e.getPacket() instanceof CPacketPlayer.Position) {
            this.hth(e, true);
        }
        if (e.getPacket() instanceof CPacketPlayer) {
            if (this.cancelGround.getValue()) {
                e.setCanceled(true);
            }
            else {
                this.hth(e, false);
            }
        }
    }
    
    public static boolean noMovementKeys() {
        return !TickShift.mc.player.movementInput.forwardKeyDown && !TickShift.mc.player.movementInput.backKeyDown && !TickShift.mc.player.movementInput.rightKeyDown && !TickShift.mc.player.movementInput.leftKeyDown;
    }
    
    @Override
    public String getDisplayInfo() {
        return this.ticks + "";
    }
    
    @Override
    public void onEnable() {
        this.rozetked();
    }
    
    @Override
    public void onDisable() {
        this.rozetked();
    }
    
    private void hth(final PacketEvent.Send event, final boolean moving) {
        if (event.isCanceled()) {
            return;
        }
        if (moving && !noMovementKeys() && (this.sneaking.getValue() || !TickShift.mc.player.isSneaking())) {
            Thunderhack.TICK_TIMER = this.timer.getValue();
        }
        this.ticks = ((this.ticks <= 0) ? 0 : (this.ticks - 1));
    }
    
    public void rozetked() {
        Thunderhack.TICK_TIMER = 1.0f;
        this.ticks = 0;
    }
}
