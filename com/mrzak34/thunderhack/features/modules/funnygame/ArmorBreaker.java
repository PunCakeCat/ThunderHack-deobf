//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;

public class ArmorBreaker extends Module
{
    public Setting<SubBind> button;
    public final Setting<Boolean> packet;
    int newtarget;
    int delay;
    public static float yaw;
    public static float pitch;
    public static boolean active;
    
    public ArmorBreaker() {
        super("ArmorBreaker", "\u041d\u0415 \u0412\u041a\u041b\u042e\u0427\u0410\u0422\u042c!!!-\u0422\u0415\u0421\u0422!!!!", Category.FUNNYGAME, true, false, false);
        this.button = (Setting<SubBind>)this.register(new Setting("BindButt", (T)new SubBind(36)));
        this.packet = (Setting<Boolean>)this.register(new Setting("silent", (T)true));
        this.newtarget = 0;
    }
    
    @Override
    public void onEnable() {
        ArmorBreaker.active = true;
        this.delay = 0;
    }
    
    @Override
    public void onUpdate() {
        ++this.delay;
        for (final Entity ebt : ArmorBreaker.mc.world.playerEntities) {
            if (!(ebt instanceof EntityPlayer)) {
                return;
            }
            if (this.newtarget >= 2 || ((EntityPlayer)ebt).getName() == ArmorBreaker.mc.player.getName()) {
                continue;
            }
            EntityPlayer entityplayer = (EntityPlayer)ebt;
            if (ArmorBreaker.mc.player.getDistance((Entity)entityplayer) > ArmorBreaker.mc.player.getDistance(ebt)) {
                entityplayer = (EntityPlayer)ebt;
                this.newtarget = 0;
            }
            final float f = ArmorBreaker.mc.player.getDistance((Entity)entityplayer);
            if (f >= 6.1 || entityplayer.isInvisible() || !entityplayer.isEntityAlive()) {
                continue;
            }
            if (entityplayer.isInvisible()) {
                continue;
            }
            if (this.delay <= 0) {
                continue;
            }
            if (this.packet.getValue()) {
                SilentRotaionUtil.lookAtEntity((Entity)entityplayer);
            }
            else {
                KillauraOld.lookAtEntity((Entity)entityplayer);
            }
            if (ArmorBreaker.mc.player.isActiveItemStackBlocking() && ArmorBreaker.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
                ArmorBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
            }
            ArmorBreaker.mc.playerController.attackEntity((EntityPlayer)ArmorBreaker.mc.player, (Entity)entityplayer);
            ArmorBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
            this.delay = 0;
        }
    }
    
    @Override
    public void onDisable() {
        ArmorBreaker.active = false;
        super.onDisable();
    }
}
