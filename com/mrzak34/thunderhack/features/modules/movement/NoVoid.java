//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class NoVoid extends Module
{
    boolean aboveVoid;
    private Timer voidTimer;
    
    public NoVoid() {
        super("NoVoid", "\u0440\u0443\u0431\u0435\u0440\u0431\u0435\u043d\u0434\u0438\u0442 \u0435\u0441\u043b\u0438 \u0442\u044b-\u0443\u043f\u0430\u043b \u0432 \u043f\u0443\u0441\u0442\u043e\u0442\u0443", Module.Category.MOVEMENT, false, false, false);
        this.aboveVoid = true;
        this.voidTimer = new Timer();
    }
    
    public void onUpdate() {
        if (NoVoid.mc.player == null || NoVoid.mc.world == null) {
            return;
        }
        if (PlayerUtils.isPlayerAboveVoid() && NoVoid.mc.player.posY <= 1.0) {
            if (this.aboveVoid && this.voidTimer.passedMs(1000L)) {
                this.aboveVoid = false;
                NoVoid.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(NoVoid.mc.player.posX, NoVoid.mc.player.posY + 0.1, NoVoid.mc.player.posZ, false));
            }
        }
        else {
            this.aboveVoid = true;
        }
    }
    
    @SubscribeEvent
    public void onPlayerMove(final PlayerMoveEvent event) {
    }
    
    @SubscribeEvent
    public void onReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && !(NoVoid.mc.currentScreen instanceof GuiDownloadTerrain)) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            ((ISPacketPlayerPosLook)packet).setYaw(NoVoid.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)packet).setPitch(NoVoid.mc.player.rotationPitch);
            packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        }
    }
}
