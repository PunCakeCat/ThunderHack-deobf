//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;

public class PlayerTweaks extends Module
{
    boolean lastTickOG;
    public Setting<Boolean> noPush;
    public Setting<Boolean> pistonPush;
    public Setting<Boolean> akbM;
    public Setting<Boolean> antiKnockBack;
    public Setting<Boolean> noFall;
    public Setting<Boolean> noFallDC;
    public Setting<Boolean> noPushWater;
    public Setting<Boolean> portalChat;
    public Setting<Boolean> noPushBlock;
    public Setting<Boolean> webT;
    public Setting<Boolean> ice;
    public Setting<Boolean> noSlow;
    public Setting<Boolean> strict;
    public Setting<Boolean> guiMove;
    public Setting<Integer> postSecure;
    public Setting<Float> iceSpeed;
    public Setting<Float> veloXZ;
    public Setting<Float> veloY;
    private Setting<NofallMode> noFallMode;
    private Setting<CatchMode> catchM;
    public boolean pauseNoFallPacket;
    Vec3d pos;
    int ticksBef;
    BlockPos n1;
    
    public PlayerTweaks() {
        super("PlayerTweaks", "\u043d\u0430\u0431\u043e\u0440 \u0442\u0432\u0438\u043a\u043e\u0432 \u0434\u043b\u044f \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f-\u0438\u0433\u0440\u043e\u043a\u0430", Module.Category.MOVEMENT, true, false, false);
        this.noPush = (Setting<Boolean>)this.register(new Setting("No Push", (T)false));
        this.pistonPush = (Setting<Boolean>)this.register(new Setting("Anti Piston Push", (T)false));
        this.akbM = (Setting<Boolean>)this.register(new Setting("Non 0 value", (T)false));
        this.antiKnockBack = (Setting<Boolean>)this.register(new Setting("Velocity", (T)false));
        this.noFall = (Setting<Boolean>)this.register(new Setting("No Fall", (T)false));
        this.noFallDC = (Setting<Boolean>)this.register(new Setting("Disconnect", (T)false, v -> this.noFall.getValue()));
        this.noPushWater = (Setting<Boolean>)this.register(new Setting("No Push Liquid", (T)false));
        this.portalChat = (Setting<Boolean>)this.register(new Setting("Portal Chat", (T)false));
        this.noPushBlock = (Setting<Boolean>)this.register(new Setting("No Push Block", (T)false));
        this.webT = (Setting<Boolean>)this.register(new Setting("No Slow Web", (T)false));
        this.ice = (Setting<Boolean>)this.register(new Setting("Ice Speed", (T)false));
        this.noSlow = (Setting<Boolean>)this.register(new Setting("No Slow", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("No Slow Strict", (T)false, v -> this.noSlow.getValue()));
        this.guiMove = (Setting<Boolean>)this.register(new Setting("Gui Move", (T)false));
        this.postSecure = (Setting<Integer>)this.register(new Setting("Post Secure", (T)15, (T)1, (T)40, v -> this.pistonPush.getValue()));
        this.iceSpeed = (Setting<Float>)this.register(new Setting("XZ Multiplier", (T)0.4, (T)0, (T)1, v -> this.ice.getValue()));
        this.veloXZ = (Setting<Float>)this.register(new Setting("XZ Multiplier", (T)0, (T)(-5.0), (T)5.0, v -> this.antiKnockBack.getValue() && this.akbM.getValue()));
        this.veloY = (Setting<Float>)this.register(new Setting("Y Multiplier", (T)0, (T)(-5.0), (T)5.0, v -> this.antiKnockBack.getValue() && this.akbM.getValue()));
        this.noFallMode = (Setting<NofallMode>)this.register(new Setting("No Fall Mode", (T)NofallMode.Packet, v -> this.noFall.getValue()));
        this.catchM = (Setting<CatchMode>)this.register(new Setting("Catch Material", (T)CatchMode.Water, v -> this.noFallMode.getValue() == NofallMode.Catch));
        this.pos = new Vec3d(0.0, 0.0, 0.0);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent e) {
        if (PlayerTweaks.mc.player.isHandActive() && !PlayerTweaks.mc.player.isRiding()) {
            if (this.strict.getValue() && PlayerTweaks.mc.player.inventory.getCurrentItem().item instanceof ItemFood) {
                PlayerTweaks.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(PlayerTweaks.mc.player.inventory.currentItem));
            }
            final MovementInput movementInput = PlayerTweaks.mc.player.movementInput;
            movementInput.moveForward /= (float)0.2;
            final MovementInput movementInput2 = PlayerTweaks.mc.player.movementInput;
            movementInput2.moveStrafe /= (float)0.2;
        }
        this.lastTickOG = PlayerTweaks.mc.player.onGround;
    }
    
    @SubscribeEvent
    public void onPush(final PushEvent event) {
        if (event.getStage() == 0 && this.noPush.getValue() && event.entity.equals((Object)PlayerTweaks.mc.player)) {
            if (this.noPush.getValue()) {
                event.setCanceled(true);
            }
            if (this.noPushWater.getValue()) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (this.antiKnockBack.getValue() && this.akbM.getValue()) {
            if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == PlayerTweaks.mc.player.getEntityId()) {
                final SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity)event.getPacket();
                sPacketEntityVelocity.motionX *= (int)(Object)this.veloXZ.getValue();
                final SPacketEntityVelocity sPacketEntityVelocity2 = (SPacketEntityVelocity)event.getPacket();
                sPacketEntityVelocity2.motionY *= (int)(Object)this.veloY.getValue();
                final SPacketEntityVelocity sPacketEntityVelocity3 = (SPacketEntityVelocity)event.getPacket();
                sPacketEntityVelocity3.motionZ *= (int)(Object)this.veloXZ.getValue();
            }
            if (event.getPacket() instanceof SPacketExplosion) {
                final SPacketExplosion sPacketExplosion = (SPacketExplosion)event.getPacket();
                sPacketExplosion.motionX *= this.veloXZ.getValue();
                final SPacketExplosion sPacketExplosion2 = (SPacketExplosion)event.getPacket();
                sPacketExplosion2.motionY *= this.veloY.getValue();
                final SPacketExplosion sPacketExplosion3 = (SPacketExplosion)event.getPacket();
                sPacketExplosion3.motionZ *= this.veloXZ.getValue();
            }
        }
        else if (this.antiKnockBack.getValue() && !this.akbM.getValue()) {
            if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == PlayerTweaks.mc.player.getEntityId()) {
                event.setCanceled(true);
            }
            if (event.getPacket() instanceof SPacketExplosion) {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if ((event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.PositionRotation) && HoleUtil.isHole(PlayerTweaks.mc.player.getPosition(), true, true).getType() != HoleUtil.HoleType.NONE) {
            final boolean found = this.isPushable(PlayerTweaks.mc.player.posX, PlayerTweaks.mc.player.posY, PlayerTweaks.mc.player.posZ);
            if (found) {
                event.setCanceled(true);
                this.ticksBef = this.postSecure.getValue();
            }
            else if (--this.ticksBef > 0) {
                event.setCanceled(true);
            }
        }
        if (PlayerTweaks.mc.player != null && PlayerTweaks.mc.world != null) {
            if (this.noFall.getValue() && event.getPacket() instanceof CPacketPlayer && !PlayerTweaks.mc.player.isElytraFlying()) {
                try {
                    PlayerTweaks.mc.player.connection.getNetworkManager().handleDisconnection();
                    final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
                    if (this.noFallMode.getValue() == NofallMode.Packet) {
                        if (PlayerTweaks.mc.player.onGround) {
                            return;
                        }
                        if (!this.pauseNoFallPacket) {
                            packet.onGround = true;
                            PlayerTweaks.mc.player.fallDistance = 0.0f;
                        }
                        else {
                            this.pauseNoFallPacket = false;
                        }
                    }
                    else if (this.noFallMode.getValue() == NofallMode.OldFag) {
                        if (this.predict(new BlockPos(PlayerTweaks.mc.player.posX, PlayerTweaks.mc.player.posY, PlayerTweaks.mc.player.posZ)) && PlayerTweaks.mc.player.fallDistance >= 3.0f) {
                            PlayerTweaks.mc.player.motionY = 0.0;
                            packet.y = this.n1.getY();
                            PlayerTweaks.mc.player.fallDistance = 0.0f;
                        }
                    }
                    else if (this.noFallMode.getValue() == NofallMode.Catch) {
                        if (PlayerTweaks.mc.player.fallDistance >= 3.0f) {
                            final int oldSlot = PlayerTweaks.mc.player.inventory.currentItem;
                            final int slot = (this.catchM.getValue() == CatchMode.Web) ? getSlot(Blocks.WEB) : getSlot(Items.WATER_BUCKET);
                            if (slot != -1) {
                                PlayerTweaks.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
                                if (this.catchM.getValue() == CatchMode.Web) {
                                    try {
                                        PlayerTweaks.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.getDownPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                                    }
                                    catch (NullPointerException ex) {}
                                }
                                else {
                                    PlayerTweaks.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.getDownPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                                }
                                PlayerTweaks.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
                            }
                        }
                    }
                    else if (this.noFallMode.getValue() == NofallMode.Glitch) {
                        if (PlayerTweaks.mc.player.fallDistance > 2.0f) {
                            PlayerTweaks.mc.player.setPosition(this.pos.x, this.pos.y, this.pos.z);
                        }
                        else if (PlayerTweaks.mc.player.onGround) {
                            this.pos = PlayerTweaks.mc.player.getPositionVector();
                        }
                    }
                }
                catch (Exception e) {
                    try {
                        Command.sendMessage(e.getMessage());
                        for (final StackTraceElement p : e.getStackTrace()) {
                            System.out.println(p.toString());
                        }
                    }
                    catch (Exception ex2) {}
                }
            }
            if (this.noFallDC.getValue() && PlayerTweaks.mc.player.fallDistance - 2.1 >= PlayerTweaks.mc.player.getHealth()) {
                PlayerTweaks.mc.player.connection.getNetworkManager().closeChannel((ITextComponent)new TextComponentString(ChatFormatting.GOLD + "Player would have taken fall damage"));
            }
        }
    }
    
    public static int getSlot(final Block blockToFind) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PlayerTweaks.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block.equals(blockToFind)) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int getSlot(final Item blockToFind) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PlayerTweaks.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    if (stack.getItem() == blockToFind) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    boolean isPushable(final double x, double y, final double z) {
        final BlockPos bps = new BlockPos(x, ++y, z);
        if (PlayerTweaks.mc.world.getBlockState(bps).getBlock().equals(Blocks.PISTON_HEAD) || PlayerTweaks.mc.world.getBlockState(bps).getBlock().equals(Blocks.PISTON_EXTENSION)) {
            return true;
        }
        for (final TileEntity entity : PlayerTweaks.mc.world.loadedTileEntityList) {
            final TileEntityShulkerBox tempShulker;
            final AxisAlignedBB tempAxis;
            if (entity instanceof TileEntityShulkerBox && (tempShulker = (TileEntityShulkerBox)entity).getProgress(PlayerTweaks.mc.getRenderPartialTicks()) > 0.0f && (((tempAxis = tempShulker.getRenderBoundingBox()).minY <= y && tempAxis.maxY >= y && (int)tempAxis.minX <= x && tempAxis.maxX >= x) || ((int)tempAxis.minZ <= z && tempAxis.maxZ >= z))) {
                return true;
            }
        }
        return false;
    }
    
    public void onDisable() {
        Blocks.ICE.setDefaultSlipperiness(0.4f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.4f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.4f);
    }
    
    public void onUpdate() {
        if (this.ice.getValue()) {
            Blocks.ICE.setDefaultSlipperiness((float)this.iceSpeed.getValue());
            Blocks.PACKED_ICE.setDefaultSlipperiness((float)this.iceSpeed.getValue());
            Blocks.FROSTED_ICE.setDefaultSlipperiness((float)this.iceSpeed.getValue());
        }
        else {
            Blocks.ICE.setDefaultSlipperiness(0.4f);
            Blocks.PACKED_ICE.setDefaultSlipperiness(0.4f);
            Blocks.FROSTED_ICE.setDefaultSlipperiness(0.4f);
        }
        if (!Thunderhack.moduleManager.getModuleByClass(Timer.class).isOn()) {
            if (PlayerTweaks.mc.player.isInWeb && !PlayerTweaks.mc.player.onGround && this.webT.getValue()) {
                PlayerTweaks.mc.timer.tickLength = 1.0f;
                PlayerTweaks.mc.player.moveForward = 0.0f;
                PlayerTweaks.mc.player.moveStrafing = 0.0f;
            }
            else {
                PlayerTweaks.mc.timer.tickLength = 50.0f;
            }
        }
        if (this.guiMove.getValue() && PlayerTweaks.mc.currentScreen != null && !(PlayerTweaks.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = PlayerTweaks.mc.player;
                player.rotationPitch -= 5.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = PlayerTweaks.mc.player;
                player2.rotationPitch += 5.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player3 = PlayerTweaks.mc.player;
                player3.rotationYaw += 5.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player4 = PlayerTweaks.mc.player;
                player4.rotationYaw -= 5.0f;
            }
            if (PlayerTweaks.mc.player.rotationPitch > 90.0f) {
                PlayerTweaks.mc.player.rotationPitch = 90.0f;
            }
            if (PlayerTweaks.mc.player.rotationPitch < -90.0f) {
                PlayerTweaks.mc.player.rotationPitch = -90.0f;
            }
        }
    }
    
    private boolean predict(final BlockPos blockPos) {
        this.n1 = blockPos.add(0, -3, 0);
        return PlayerTweaks.mc.world.getBlockState(this.n1).getBlock() != Blocks.AIR;
    }
    
    BlockPos getDownPos() {
        BlockPos e = null;
        for (int i = 0; i < 5; ++i) {
            if (!PlayerTweaks.mc.world.isAirBlock(new BlockPos(PlayerTweaks.mc.player.getPositionVector()).add(0, -i, 0))) {
                e = new BlockPos(PlayerTweaks.mc.player.getPositionVector()).add(0, -i + 1, 0);
                break;
            }
        }
        return e;
    }
    
    public enum NofallMode
    {
        Packet, 
        OldFag, 
        Catch, 
        Glitch;
    }
    
    public enum CatchMode
    {
        Web, 
        Water;
    }
}
