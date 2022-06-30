//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;

public class AntiBowBomb extends Module
{
    public final Setting<Boolean> stopa;
    public final Setting<Boolean> el;
    public final Setting<Boolean> packetLook;
    EntityPlayer target;
    public Setting<Integer> range;
    public Setting<Integer> maxUse;
    int old;
    boolean b;
    
    public AntiBowBomb() {
        super("AntiBowBomb", "\u0421\u0442\u0430\u0432\u0438\u0442 \u0449\u0438\u0442 \u0435\u0441\u043b\u0438-\u0432 \u0442\u0435\u0431\u044f \u0446\u0435\u043b\u0438\u0442\u0441\u044f-\u0438\u0433\u0440\u043e\u043a", Category.COMBAT, true, false, false);
        this.stopa = (Setting<Boolean>)this.register(new Setting("StopAura", (T)true));
        this.el = (Setting<Boolean>)this.register(new Setting("EnemyList", (T)false));
        this.packetLook = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)40, (T)0, (T)60));
        this.maxUse = (Setting<Integer>)this.register(new Setting("MaxUse", (T)0, (T)0, (T)20));
    }
    
    @Override
    public void onToggle() {
        this.b = false;
        this.old = -1;
        this.target = null;
    }
    
    public EntityPlayer getTarget(final float range) {
        EntityPlayer currentTarget = null;
        for (int size = AntiBowBomb.mc.world.playerEntities.size(), i = 0; i < size; ++i) {
            final EntityPlayer player = AntiBowBomb.mc.world.playerEntities.get(i);
            if (!this.isntValid((Entity)player, range)) {
                if (currentTarget == null) {
                    currentTarget = player;
                }
                else if (AntiBowBomb.mc.player.getDistanceSq((Entity)player) < AntiBowBomb.mc.player.getDistanceSq((Entity)currentTarget)) {
                    currentTarget = player;
                }
            }
        }
        return currentTarget;
    }
    
    @Override
    public void onTick() {
        this.target = this.getTarget(this.range.getValue());
        if (this.target == null) {
            if (this.b) {
                ((AccessorKeyBinding)AntiBowBomb.mc.gameSettings.keyBindUseItem).setPressed(false);
                if (this.old != -1) {
                    ItemUtil.swapToHotbarSlot(this.old, false);
                }
                this.target = null;
                this.b = false;
            }
            return;
        }
        this.old = AntiBowBomb.mc.player.inventory.currentItem;
        final int shield = ItemUtil.findItem(ItemShield.class);
        if (shield == -1) {
            this.target = null;
            return;
        }
        if (Thunderhack.friendManager.isFriend(this.target.getName())) {
            return;
        }
        if (this.target.getItemInUseMaxCount() <= this.maxUse.getValue()) {
            return;
        }
        if (!(this.target.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow)) {
            return;
        }
        if (this.stopa.getValue() && Thunderhack.moduleManager.getModuleByClass(KillauraOld.class).isEnabled()) {
            Thunderhack.moduleManager.getModuleByClass(KillauraOld.class).toggle();
        }
        if (this.stopa.getValue() && Thunderhack.moduleManager.getModuleByClass(TargetStrafe.class).isEnabled()) {
            Thunderhack.moduleManager.getModuleByClass(TargetStrafe.class).toggle();
        }
        InventoryUtil.switchToHotbarSlot(shield, false);
        if (AntiBowBomb.mc.player.getHeldItemMainhand().getItem() instanceof ItemShield) {
            ((AccessorKeyBinding)AntiBowBomb.mc.gameSettings.keyBindUseItem).setPressed(true);
            ItemUtil.swapToHotbarSlot(shield, false);
            if (this.packetLook.getValue()) {
                SilentRotaionUtil.lookAtEntity((Entity)this.target);
            }
            else {
                KillauraOld.lookAtEntity((Entity)this.target);
            }
            this.b = true;
        }
    }
    
    public boolean isntValid(final Entity entity, final double range) {
        return entity == null || EntityUtil.isDead(entity) || entity.equals((Object)AntiBowBomb.mc.player) || (entity instanceof EntityPlayer && Thunderhack.friendManager.isFriend(entity.getName())) || AntiBowBomb.mc.player.getDistanceSq(entity) > MathUtil.square(range) || (this.el.getValue() && !Thunderhack.enemyManager.isEnemy(entity.getName()));
    }
}
