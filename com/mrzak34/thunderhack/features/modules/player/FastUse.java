//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;

public class FastUse extends Module
{
    private Setting<Boolean> fastXP;
    private Setting<Boolean> ghostFix;
    private Setting<Boolean> strict;
    private Setting<Boolean> packetEat;
    private Setting<Boolean> fastBow;
    private Setting<Boolean> bowBomb;
    private Setting<Boolean> fastPlace;
    private Setting<Boolean> noCrystalPlace;
    private Setting<Boolean> fastPlaceWhitelist;
    public Setting<BlockListSetting> whitelist;
    public static boolean ignore;
    
    public FastUse() {
        super("FastUse", "\u0431\u044b\u0441\u0442\u0440\u043e\u043a\u043b\u0438\u043a", Module.Category.PLAYER, true, false, false);
        this.fastXP = (Setting<Boolean>)this.register(new Setting("FastXP", (T)true));
        this.ghostFix = (Setting<Boolean>)this.register(new Setting("GhostFix", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.packetEat = (Setting<Boolean>)this.register(new Setting("PacketEat", (T)false));
        this.fastBow = (Setting<Boolean>)this.register(new Setting("FastBow", (T)false));
        this.bowBomb = (Setting<Boolean>)this.register(new Setting("BowBomb", (T)false, v -> this.fastBow.getValue()));
        this.fastPlace = (Setting<Boolean>)this.register(new Setting("FastPlace", (T)false));
        this.noCrystalPlace = (Setting<Boolean>)this.register(new Setting("NoCrystalPlace", (T)false));
        this.fastPlaceWhitelist = (Setting<Boolean>)this.register(new Setting("PlaceWhitelist", (T)false));
        this.whitelist = new Setting<BlockListSetting>("Whitelist", new BlockListSetting(new String[0]));
    }
    
    @SubscribeEvent
    public void onStopUsingItem(final StopUsingItemEvent event) {
        if (FastUse.mc.player.getHeldItem(FastUse.mc.player.getActiveHand()).getItem() instanceof ItemFood && this.packetEat.getValue()) {
            event.setCanceled(true);
        }
    }
    
    public void onUpdate() {
        if (FastUse.mc.world == null || FastUse.mc.player == null) {
            return;
        }
        if (this.strict.getValue() && FastUse.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        if (this.fastBow.getValue() && FastUse.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && FastUse.mc.player.isHandActive() && FastUse.mc.player.getItemInUseMaxCount() >= 3) {
            FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, FastUse.mc.player.getHorizontalFacing()));
            if (this.bowBomb.getValue() && FastUse.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(FastUse.mc.player.posX, FastUse.mc.player.posY - 0.1, FastUse.mc.player.posZ, FastUse.mc.player.rotationYaw, FastUse.mc.player.rotationPitch, false));
                FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(FastUse.mc.player.posX, FastUse.mc.player.posY - 999.0, FastUse.mc.player.posZ, FastUse.mc.player.rotationYaw, FastUse.mc.player.rotationPitch, true));
            }
            FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(FastUse.mc.player.getActiveHand()));
            FastUse.mc.player.stopActiveHand();
            return;
        }
        if (this.shouldFastUse() && FastUse.mc.rightClickDelayTimer != 0) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (FastUse.mc.player == null || FastUse.mc.world == null) {
            return;
        }
        if (this.ghostFix.getValue() && FastUse.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                event.setCanceled(true);
            }
        }
        else if (this.noCrystalPlace.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && FastUse.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)event.getPacket()).getHand()).getItem() instanceof ItemEndCrystal) {
            if (FastUse.ignore) {
                FastUse.ignore = false;
            }
            else {
                event.setCanceled(true);
            }
        }
    }
    
    private boolean shouldFastUse() {
        final Item main = FastUse.mc.player.getHeldItemMainhand().getItem();
        final Item off = FastUse.mc.player.getHeldItemOffhand().getItem();
        if (this.fastXP.getValue() && (main instanceof ItemExpBottle || off instanceof ItemExpBottle)) {
            return true;
        }
        if (this.fastPlace.getValue()) {
            if (main instanceof ItemBlock && (this.whitelist.getValue().getBlocks().contains(((ItemBlock)main).getBlock()) || !this.fastPlaceWhitelist.getValue())) {
                FastUse.mc.rightClickDelayTimer = 0;
                return true;
            }
            if (off instanceof ItemBlock && (this.whitelist.getValue().getBlocks().contains(((ItemBlock)off).getBlock()) || !this.fastPlaceWhitelist.getValue())) {
                FastUse.mc.rightClickDelayTimer = 0;
                return true;
            }
        }
        if (main instanceof ItemFood) {
            FastUse.mc.rightClickDelayTimer = 0;
            return true;
        }
        return false;
    }
    
    static {
        FastUse.ignore = false;
    }
}
