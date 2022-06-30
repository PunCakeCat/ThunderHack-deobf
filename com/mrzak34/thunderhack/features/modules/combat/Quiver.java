//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class Quiver extends Module
{
    public final Setting<Boolean> speed;
    public final Setting<Boolean> strength;
    public final Setting<Boolean> toggelable;
    public final Setting<Boolean> autoSwitch;
    public final Setting<Boolean> rearrange;
    public final Setting<Boolean> noGapSwitch;
    public final Setting<Integer> health;
    private Timer timer;
    private boolean cancelStopUsingItem;
    
    public Quiver() {
        super("Quiver", "\u041d\u0430\u043a\u043b\u0430\u0434\u044b\u0432\u0430\u0442\u044c \u044d\u0444\u0444\u0435\u043a\u0442\u044b-\u043d\u0430 \u0441\u0435\u0431\u044f \u0441 \u043b\u0443\u043a\u0430 ", Category.COMBAT, true, false, false);
        this.speed = (Setting<Boolean>)this.register(new Setting("Swiftness", (T)false));
        this.strength = (Setting<Boolean>)this.register(new Setting("Strength", (T)false));
        this.toggelable = (Setting<Boolean>)this.register(new Setting("Toggelable", (T)false));
        this.autoSwitch = (Setting<Boolean>)this.register(new Setting("AutoSwitch", (T)false));
        this.rearrange = (Setting<Boolean>)this.register(new Setting("Rearrange", (T)false));
        this.noGapSwitch = (Setting<Boolean>)this.register(new Setting("NoGapSwitch", (T)false));
        this.health = (Setting<Integer>)this.register(new Setting("MinHealth", (T)20, (T)0, (T)36));
        this.timer = new Timer();
        this.cancelStopUsingItem = false;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() != 0) {
            return;
        }
        if (Quiver.mc.player == null || Quiver.mc.world == null) {
            return;
        }
        if (event.isCanceled() || !InteractionUtil.canPlaceNormally()) {
            return;
        }
        if (!this.timer.passedMs(2500L)) {
            return;
        }
        if (Quiver.mc.player.getHealth() + Quiver.mc.player.getAbsorptionAmount() < this.health.getValue()) {
            return;
        }
        if (this.noGapSwitch.getValue() && Quiver.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        if (this.strength.getValue() && !Quiver.mc.player.isPotionActive(MobEffects.STRENGTH)) {
            if (this.isFirstAmmoValid("Arrow of Strength")) {
                this.shootBow(event);
            }
            else if (this.toggelable.getValue()) {
                this.toggle();
            }
        }
        if (this.speed.getValue() && !Quiver.mc.player.isPotionActive(MobEffects.SPEED)) {
            if (this.isFirstAmmoValid("Arrow of Swiftness")) {
                this.shootBow(event);
            }
            else if (this.toggelable.getValue()) {
                this.toggle();
            }
        }
    }
    
    @SubscribeEvent
    public void onStopUsingItem(final StopUsingItemEvent event) {
        if (this.cancelStopUsingItem) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onEnable() {
        this.cancelStopUsingItem = false;
    }
    
    private void shootBow(final UpdateWalkingPlayerEvent event) {
        if (Quiver.mc.player.inventory.getCurrentItem().getItem() == Items.BOW) {
            Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, -90.0f, Quiver.mc.player.onGround));
            ((IEntityPlayerSP)Quiver.mc.player).setLastReportedYaw(0.0f);
            ((IEntityPlayerSP)Quiver.mc.player).setLastReportedPitch(-90.0f);
            if (Quiver.mc.player.getItemInUseMaxCount() >= 3) {
                this.cancelStopUsingItem = false;
                Quiver.mc.playerController.onStoppedUsingItem((EntityPlayer)Quiver.mc.player);
                if (this.toggelable.getValue()) {
                    this.toggle();
                }
                this.timer.reset();
            }
            else if (Quiver.mc.player.getItemInUseMaxCount() == 0) {
                Quiver.mc.playerController.processRightClick((EntityPlayer)Quiver.mc.player, (World)Quiver.mc.world, EnumHand.MAIN_HAND);
                this.cancelStopUsingItem = true;
            }
        }
        else if (this.autoSwitch.getValue()) {
            final int bowSlot = this.getBowSlot();
            if (bowSlot != -1 && bowSlot != Quiver.mc.player.inventory.currentItem) {
                Quiver.mc.player.inventory.currentItem = bowSlot;
                Quiver.mc.playerController.updateController();
            }
        }
    }
    
    public int getBowSlot() {
        int bowSlot = -1;
        if (Quiver.mc.player.getHeldItemMainhand().getItem() == Items.BOW) {
            bowSlot = Module.mc.player.inventory.currentItem;
        }
        if (bowSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (Quiver.mc.player.inventory.getStackInSlot(l).getItem() == Items.BOW) {
                    bowSlot = l;
                    break;
                }
            }
        }
        return bowSlot;
    }
    
    private boolean isFirstAmmoValid(final String type) {
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = Quiver.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Items.TIPPED_ARROW) {
                final boolean matches = itemStack.getDisplayName().equalsIgnoreCase(type);
                return matches || (this.rearrange.getValue() && this.rearrangeArrow(i, type));
            }
        }
        return false;
    }
    
    private boolean rearrangeArrow(final int fakeSlot, final String type) {
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = Quiver.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Items.TIPPED_ARROW && itemStack.getDisplayName().equalsIgnoreCase(type)) {
                Quiver.mc.playerController.windowClick(0, fakeSlot, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
                Quiver.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
                Quiver.mc.playerController.windowClick(0, fakeSlot, 0, ClickType.PICKUP, (EntityPlayer)Quiver.mc.player);
                return true;
            }
        }
        return false;
    }
}
