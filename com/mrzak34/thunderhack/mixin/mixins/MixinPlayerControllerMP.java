//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.event.events.*;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP
{
    @Shadow
    private float curBlockDamageMP;
    @Shadow
    private int blockHitDelay;
    
    @Redirect(method = { "onPlayerDamageBlock" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    public float getPlayerRelativeBlockHardnessHook(final IBlockState state, final EntityPlayer player, final World worldIn, final BlockPos pos) {
        return state.getPlayerRelativeBlockHardness(player, worldIn, pos) * ((TpsSync.getInstance().isOn() && (boolean)TpsSync.getInstance().mining.getValue()) ? (1.0f / Thunderhack.serverManager.getTpsFactor()) : 1.0f);
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final BlockEvent event = new BlockEvent(3, pos, face);
        final ClickBlockEvent event2 = new ClickBlockEvent(pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event);
        MinecraftForge.EVENT_BUS.post((Event)event2);
        if (event2.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "processRightClick" }, at = { @At("HEAD") }, cancellable = true)
    private void processClickHook(final EntityPlayer player, final World worldIn, final EnumHand hand, final CallbackInfoReturnable<EnumActionResult> cir) {
        final RightClickItemEvent event = new RightClickItemEvent(player, worldIn, hand);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            cir.setReturnValue((Object)EnumActionResult.PASS);
        }
    }
    
    @Inject(method = { "attackEntity" }, at = { @At("HEAD") }, cancellable = true)
    public void attackEntity(final EntityPlayer playerIn, final Entity targetEntity, final CallbackInfo info) {
        final AttackEvent event = new AttackEvent(targetEntity);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "processRightClickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final EntityPlayerSP player, final WorldClient worldIn, final BlockPos pos, final EnumFacing direction, final Vec3d vec, final EnumHand hand, final CallbackInfoReturnable<EnumActionResult> info) {
        final ClickBlockEvent.Right event = new ClickBlockEvent.Right(pos, direction, vec, hand);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemovingHook(final CallbackInfo info) {
        final ResetBlockEvent event = new ResetBlockEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "onStoppedUsingItem" }, at = { @At("HEAD") }, cancellable = true)
    private void onStoppedUsingItemInject(final EntityPlayer playerIn, final CallbackInfo ci) {
        if (playerIn.equals((Object)Minecraft.getMinecraft().player)) {
            final StopUsingItemEvent event = new StopUsingItemEvent();
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCanceled()) {
                if (event.isPacket()) {
                    this.syncCurrentPlayItem();
                    playerIn.stopActiveHand();
                }
                ci.cancel();
            }
        }
    }
    
    @Shadow
    public abstract void syncCurrentPlayItem();
    
    @Inject(method = { "syncCurrentPlayItem" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;connection:Lnet/minecraft/client/network/NetHandlerPlayClient;") })
    private void onSyncPlayerItemSendPacket(final CallbackInfo ci) {
        final SyncPlayerItemEvent event = new SyncPlayerItemEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V") }, cancellable = true)
    private void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        MinecraftForge.EVENT_BUS.post((Event)new DestroyBlockEvent(pos));
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlockHook(final BlockPos pos, final EnumFacing face, final CallbackInfoReturnable<Boolean> info) {
        final BlockEvent event = new BlockEvent(4, pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
    
    @Inject(method = { "processRightClickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void processRightClickBlock(final EntityPlayerSP player, final WorldClient worldIn, final BlockPos pos, final EnumFacing direction, final Vec3d vec, final EnumHand hand, final CallbackInfoReturnable<EnumActionResult> cir) {
        final ProcessRightClickBlockEvent event = new ProcessRightClickBlockEvent(pos, hand, Minecraft.instance.player.getHeldItem(hand));
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            cir.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfoReturnable.setReturnValue((Object)false);
        }
    }
}
