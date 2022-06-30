//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Criticals extends Module
{
    public Setting<Boolean> critCrystal;
    public Setting<Boolean> onlyAura;
    public Setting<Boolean> allowWater;
    
    public Criticals() {
        super("Criticals", "\u041a\u0430\u0436\u0434\u044b\u0439 \u0443\u0434\u0430\u0440 \u0441\u0442\u0430\u043d\u0435\u0442-\u043a\u0440\u0438\u0442\u043e\u043c", Category.COMBAT, true, false, false);
        this.critCrystal = (Setting<Boolean>)this.register(new Setting("Crit Crystals", (T)false));
        this.onlyAura = (Setting<Boolean>)this.register(new Setting("Only KillAura Target", (T)false));
        this.allowWater = (Setting<Boolean>)this.register(new Setting("In Water", (T)false));
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity && (!(event.getPacket() instanceof EntityEnderCrystal) || this.critCrystal.getValue()) && (!Criticals.mc.player.isInWater() || !this.allowWater.getValue())) {
            if (this.onlyAura.getValue() && KillauraOld.target == null) {
                return;
            }
            if (((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && Criticals.mc.player.onGround) {
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.11, Criticals.mc.player.posZ, false));
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1100013579, Criticals.mc.player.posZ, false));
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 1.3579E-6, Criticals.mc.player.posZ, false));
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer());
            }
        }
    }
}
