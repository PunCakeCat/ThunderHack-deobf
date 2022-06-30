//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.potion.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;

public class EffectsRemover extends Module
{
    public Setting<Boolean> jumpBoost;
    public Timer timer;
    
    public EffectsRemover() {
        super("PowderTweaks", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0434\u0436\u0430\u043c\u043f\u0431\u0443\u0441\u0442 \u043e\u0442-\u043f\u043e\u0440\u043e\u0445\u0430 \u0438 \u044e\u0437\u0430\u0435\u0442 \u0435\u0433\u043e \u0430\u0432\u0442\u043e\u043c\u0430\u0442\u043e\u043c", Category.FUNNYGAME, true, false, false);
        this.jumpBoost = (Setting<Boolean>)this.register(new Setting("JumpBoostRemove", (T)true));
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(500L) && !EffectsRemover.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionFromResourceLocation("strength")))) {
            final int hotbarslot = EffectsRemover.mc.player.inventory.currentItem;
            final ItemStack itemStack = Util.mc.player.inventory.getStackInSlot(InventoryUtil.getPowderAtHotbar());
            if (!itemStack.getItem().getItemStackDisplayName(itemStack).equals("\u041f\u043e\u0440\u043e\u0445")) {
                return;
            }
            EffectsRemover.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getPowderAtHotbar()));
            EffectsRemover.mc.playerController.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            EffectsRemover.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(hotbarslot));
            this.timer.reset();
        }
        if (this.jumpBoost.getValue() && EffectsRemover.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionFromResourceLocation("jump_boost")))) {
            EffectsRemover.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("jump_boost"));
        }
    }
}
