//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.client.network.*;
import net.minecraft.stats.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.modules.movement.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.event.events.*;

@Mixin(value = { EntityPlayerSP.class }, priority = 9998)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer
{
    public MixinEntityPlayerSP(final Minecraft p_i47378_1_, final World p_i47378_2_, final NetHandlerPlayClient p_i47378_3_, final StatisticsManager p_i47378_4_, final RecipeBook p_i47378_5_) {
        super(p_i47378_2_, p_i47378_3_.getGameProfile());
    }
    
    @Inject(method = { "sendChatMessage" }, at = { @At("HEAD") }, cancellable = true)
    public void sendChatMessage(final String message, final CallbackInfo callback) {
        final ChatEvent chatEvent = new ChatEvent(message);
        MinecraftForge.EVENT_BUS.post((Event)chatEvent);
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
    private boolean redirectIsCurrentViewEntity(final EntityPlayerSP entityPlayerSP) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return entityPlayerSP == mc.player;
        }
        return mc.getRenderViewEntity() == entityPlayerSP;
    }
    
    @Redirect(method = { "updateEntityActionState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isCurrentViewEntity()Z"))
    private boolean redirectIsCurrentViewEntity2(final EntityPlayerSP entityPlayerSP) {
        final Minecraft mc = Minecraft.getMinecraft();
        final FreecamEvent event = new FreecamEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            return entityPlayerSP == mc.player;
        }
        return mc.getRenderViewEntity() == entityPlayerSP;
    }
    
    @Inject(method = { "onUpdate" }, at = { @At("HEAD") })
    public void onPlayerUpdate(final CallbackInfo info) {
        final PlayerUpdateEvent playerUpdateEvent = new PlayerUpdateEvent();
        MinecraftForge.EVENT_BUS.post((Event)playerUpdateEvent);
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") })
    private void preMotion(final CallbackInfo info) {
        final UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(0);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void postMotion(final CallbackInfo info) {
        final UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(1);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.AFTER) })
    public void onPostMotionUpdate(final CallbackInfo info) {
        final MotionUpdateEvent event = new MotionUpdateEvent(Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch, Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround, Minecraft.getMinecraft().player.noClip, 1);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MovementInput;updatePlayerMoveState()V"))
    public void updatePlayerMoveState(final MovementInput input) {
        input.updatePlayerMoveState();
        final UpdatePlayerMoveStateEvent event = new UpdatePlayerMoveStateEvent(input);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;closeScreen()V"))
    public void closeScreen(final EntityPlayerSP player) {
        if (!((PlayerTweaks)Thunderhack.moduleManager.getModuleByClass((Class)PlayerTweaks.class)).isOn() && (boolean)((PlayerTweaks)Thunderhack.moduleManager.getModuleByClass((Class)PlayerTweaks.class)).portalChat.getValue()) {
            player.closeScreen();
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V"))
    public void closeScreen(final Minecraft minecraft, final GuiScreen screen) {
        if (!((PlayerTweaks)Thunderhack.moduleManager.getModuleByClass((Class)PlayerTweaks.class)).isOn() && (boolean)((PlayerTweaks)Thunderhack.moduleManager.getModuleByClass((Class)PlayerTweaks.class)).portalChat.getValue()) {
            Util.mc.displayGuiScreen(screen);
        }
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    private void pushOutOfBlocksHook(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> info) {
        final PushEvent event = new PushEvent(1);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled() || (((PlayerTweaks)Thunderhack.moduleManager.getModuleByClass((Class)PlayerTweaks.class)).isOn() && (boolean)((PlayerTweaks)Thunderhack.moduleManager.getModuleByClass((Class)PlayerTweaks.class)).noPushBlock.getValue())) {
            info.setReturnValue((Object)false);
        }
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onUpdateWalkingPlayer()V", shift = At.Shift.BEFORE) })
    public void onPreMotionUpdate(final CallbackInfo info) {
        final MotionUpdateEvent event = new MotionUpdateEvent(Minecraft.getMinecraft().player.rotationYaw, Minecraft.getMinecraft().player.rotationPitch, Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, Minecraft.getMinecraft().player.onGround, Minecraft.getMinecraft().player.noClip, 0);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(final AbstractClientPlayer player, final MoverType type, final double x, final double y, final double z) {
        final RhMoveEvent event = new RhMoveEvent(x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
        super.move(type, event.motionX, event.motionY, event.motionZ);
    }
    
    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    private void move(final MoverType type, final double x, final double y, final double z, final CallbackInfo info) {
        final EventMove event = new EventMove(type, x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            super.move(type, event.get_x(), event.get_y(), event.get_z());
            info.cancel();
        }
    }
}
