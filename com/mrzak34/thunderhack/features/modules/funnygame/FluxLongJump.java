//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.math.*;

public class FluxLongJump extends Module
{
    boolean hasHurt;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    private double boost;
    private boolean jumped;
    int i;
    int slotId;
    int ticks;
    
    public FluxLongJump() {
        super("FluxLongJump", "FluxLongJump", Category.FUNNYGAME, true, false, false);
        this.hasHurt = false;
        this.boost = 4.0;
    }
    
    @Override
    public void onEnable() {
        this.stage = 1;
        this.moveSpeed = 0.1873;
        this.hasHurt = false;
        if (InventoryUtil.getArrow() == -1) {
            Command.sendMessage("\u043d\u0435\u0442 \u0441\u0442\u0440\u0435\u043b!");
            this.setEnabled(false);
            return;
        }
        ItemStack itemStack = null;
        this.i = 0;
        while (this.i < 9) {
            itemStack = Util.mc.player.inventory.getStackInSlot(this.i);
            if (itemStack != null && itemStack.getItem() instanceof ItemBow) {
                break;
            }
            ++this.i;
        }
        if (this.i == 9) {
            Command.sendMessage("Did not find a bow in your hotbar.");
            this.setEnabled(false);
            return;
        }
        this.slotId = FluxLongJump.mc.player.inventory.currentItem;
        if (this.i != this.slotId) {
            Command.sendMessage("Switching slot from " + this.slotId + " to " + this.i);
            FluxLongJump.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.i));
        }
        this.ticks = FluxLongJump.mc.player.ticksExisted;
        FluxLongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Thunderhack.TICK_TIMER = 1.0f;
        this.lastDist = 0.0;
        this.stage = 4;
        this.jumped = false;
        super.onDisable();
    }
    
    @Override
    public void onUpdate() {
        final double xDist = FluxLongJump.mc.player.posX - FluxLongJump.mc.player.prevPosX;
        final double zDist = FluxLongJump.mc.player.posZ - FluxLongJump.mc.player.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @SubscribeEvent
    public void onMove(final EventMove e) {
        if (FluxLongJump.mc.player.hurtTime == 9) {
            this.hasHurt = true;
        }
        if (!this.hasHurt) {
            if (FluxLongJump.mc.player.ticksExisted - this.ticks == 3) {
                FluxLongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(FluxLongJump.mc.player.rotationYaw, -89.5f, true));
                FluxLongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
                if (this.i != this.slotId) {
                    Command.sendMessage("Switching back from " + this.i + " to " + this.slotId);
                    FluxLongJump.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.slotId));
                }
            }
            e.set_x(0.0);
            if (e.get_y() > 0.0) {
                e.set_y(0.0);
            }
            e.set_z(0.0);
        }
        else {
            if (roundToPlace(FluxLongJump.mc.player.posY - (int)FluxLongJump.mc.player.posY, 3) == roundToPlace(0.41, 3)) {
                FluxLongJump.mc.player.motionY = 0.0;
            }
            if (FluxLongJump.mc.player.moveStrafing < 0.0f && FluxLongJump.mc.player.moveForward < 0.0f) {
                this.stage = 1;
            }
            if (round(FluxLongJump.mc.player.posY - (int)FluxLongJump.mc.player.posY, 3) == round(0.943, 3)) {
                FluxLongJump.mc.player.motionY = 0.0;
            }
            if (this.stage == 1 && (FluxLongJump.mc.player.moveForward != 0.0f || FluxLongJump.mc.player.moveStrafing != 0.0f) && FluxLongJump.mc.player.collidedVertically) {
                this.stage = 2;
                this.moveSpeed = this.boost * this.getBaseMoveSpeed() - 0.01;
            }
            else if (this.stage == 2) {
                this.stage = 3;
                FluxLongJump.mc.player.motionY = 0.64;
                e.y = 0.64;
                this.moveSpeed *= 2.149802;
                this.jumped = true;
            }
            else if (this.stage == 3) {
                this.stage = 4;
                final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else if (this.stage == 4) {
                this.moveSpeed = this.lastDist - this.lastDist / 25.0;
                if (FluxLongJump.mc.world.getCollisionBoxes((Entity)FluxLongJump.mc.player, FluxLongJump.mc.player.getEntityBoundingBox().offset(0.0, FluxLongJump.mc.player.motionY, 0.0)).size() > 0 || FluxLongJump.mc.player.collidedVertically) {
                    this.stage = 1;
                    if (this.jumped) {
                        this.toggle();
                    }
                }
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            float forward = FluxLongJump.mc.player.movementInput.moveForward;
            float strafe = FluxLongJump.mc.player.movementInput.moveStrafe;
            float yaw = FluxLongJump.mc.player.rotationYaw;
            if (forward == 0.0f && strafe == 0.0f) {
                e.x = 0.0;
                e.z = 0.0;
            }
            else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45 : 45);
                    strafe = 0.0f;
                }
                else if (strafe <= -1.0f) {
                    yaw += ((forward > 0.0f) ? 45 : -45);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
            e.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
            e.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
            if (forward == 0.0f && strafe == 0.0f) {
                e.x = 0.0;
                e.z = 0.0;
            }
        }
    }
    
    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private double getBaseMoveSpeed() {
        return 0.1873;
    }
}
