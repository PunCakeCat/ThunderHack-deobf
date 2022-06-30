//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.client.entity.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.helper.*;

public class RBandESP extends Module
{
    EntityPlayerSP fakeEntity;
    public Timer timer;
    
    public RBandESP() {
        super("RBandESP", "RBandESP", Module.Category.RENDER, true, true, false);
        this.fakeEntity = null;
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        Command.sendMessage(e.getPacket().toString());
        if (e.getPacket() instanceof SPacketPlayerPosLook && RBandESP.mc.player != null && RBandESP.mc.world != null) {
            Command.sendMessage("Rband");
            for (final Entity entity : RBandESP.mc.world.loadedEntityList) {
                if (entity instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)entity;
                    this.fakeEntity = RBandESP.mc.player;
                    this.timer.reset();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent e) {
        if (this.fakeEntity != null && this.timer.getPassedTimeMs() < 2000L) {
            RenderHelper.drawEntityBox((Entity)this.fakeEntity, new Color(PaletteHelper.astolfo(false, 12).getRGB()), false, 255.0f);
        }
    }
}
