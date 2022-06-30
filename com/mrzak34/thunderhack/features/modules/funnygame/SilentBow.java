//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

public class SilentBow extends Module
{
    public SilentBow() {
        super("SilentBow", "SilentBow", Category.FUNNYGAME, true, false, false);
    }
    
    @Override
    public void onEnable() {
        final int oldslot = SilentBow.mc.player.inventory.currentItem;
        final int bowslot = InventoryUtil.getBowAtHotbar();
        InventoryUtil.switchToHotbarSlot(bowslot, false);
        BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, BowSpam.mc.player.getHorizontalFacing()));
        BowSpam.mc.player.stopActiveHand();
        InventoryUtil.switchToHotbarSlot(oldslot, false);
        this.toggle();
    }
}
