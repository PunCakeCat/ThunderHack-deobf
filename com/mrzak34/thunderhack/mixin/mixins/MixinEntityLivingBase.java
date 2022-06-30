//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.event.events.*;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends Entity
{
    public MixinEntityLivingBase(final World worldIn) {
        super(worldIn);
    }
    
    @Inject(method = { "travel" }, at = { @At("HEAD") }, cancellable = true)
    public void onTravel(final float strafe, final float vertical, final float forward, final CallbackInfo ci) {
        final ElytraEvent event = new ElytraEvent((Entity)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "onEntityUpdate" }, at = { @At("TAIL") })
    public void onEntityUpdate(final CallbackInfo callbackInfo) {
        final EventLivingUpdate event = new EventLivingUpdate((Entity)this);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
