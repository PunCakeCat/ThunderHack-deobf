//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class SpeedNom extends Module
{
    public Setting<Integer> nomSpeed;
    public Setting<Integer> spoofs;
    int ticks;
    
    public SpeedNom() {
        super("SpeedNom", "\u0431\u044b\u0441\u0442\u0440\u043e \u0445\u0430\u0432\u0430\u0435\u0442", Module.Category.PLAYER, true, false, false);
        this.nomSpeed = (Setting<Integer>)this.register(new Setting("Nom Delay", (T)16, (T)1, (T)20));
        this.spoofs = (Setting<Integer>)this.register(new Setting("Nom Speed", (T)30, (T)1, (T)100));
    }
    
    public void onUpdate() {
        if (SpeedNom.mc.player.isHandActive() && SpeedNom.mc.player.inventory.getCurrentItem().item instanceof ItemFood && this.ticks > this.nomSpeed.getValue()) {
            for (int i = 0; i < this.spoofs.getValue(); ++i) {
                SpeedNom.mc.player.connection.sendPacket((Packet)new CPacketPlayer(true));
            }
            SpeedNom.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            SpeedNom.mc.player.stopActiveHand();
            this.ticks = 0;
            return;
        }
        ++this.ticks;
    }
}
