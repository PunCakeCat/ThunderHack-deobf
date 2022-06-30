//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld
{
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    private void updateLightmapHook(final EnumSkyBlock lightType, final BlockPos pos, final CallbackInfoReturnable<Boolean> info) {
        if (lightType == EnumSkyBlock.SKY && NoRender.getInstance().isOn() && (NoRender.getInstance().skylight.getValue() == NoRender.Skylight.WORLD || NoRender.getInstance().skylight.getValue() == NoRender.Skylight.ALL)) {
            info.setReturnValue((Object)true);
            info.cancel();
        }
    }
    
    @Inject(method = { "onEntityAdded" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityAdded(final Entity entity, final CallbackInfo callbackInfo) {
        final EntityAddedEvent event = new EntityAddedEvent(entity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onEntityRemoved" }, at = { @At("HEAD") }, cancellable = true)
    public void onEntityRemoved(final Entity entity, final CallbackInfo callbackInfo) {
        final EntityRemovedEvent event = new EntityRemovedEvent(entity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "handleMaterialAcceleration" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
    public boolean isPushedbyWaterHook(final Entity entity) {
        final PushEvent event = new PushEvent(2, entity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return entity.isPushedByWater() && !event.isCanceled();
    }
}
