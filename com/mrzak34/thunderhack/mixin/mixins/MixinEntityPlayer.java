//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer extends EntityLivingBase
{
    public MixinEntityPlayer(final World worldIn, final GameProfile gameProfileIn) {
        super(worldIn);
    }
    
    @Inject(method = { "getCooldownPeriod" }, at = { @At("HEAD") }, cancellable = true)
    private void getCooldownPeriodHook(final CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (TpsSync.getInstance().isOn() && (boolean)TpsSync.getInstance().attack.getValue()) {
            callbackInfoReturnable.setReturnValue((Object)(float)(1.0 / EntityPlayer.class.cast(this).getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue() * 20.0 * Thunderhack.serverManager.getTpsFactor()));
        }
    }
}
