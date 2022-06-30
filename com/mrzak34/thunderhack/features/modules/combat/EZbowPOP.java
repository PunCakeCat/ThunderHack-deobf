//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;

public class EZbowPOP extends Module
{
    public Setting<Float> fov;
    public Setting<Boolean> cancelRotate;
    public Setting<Boolean> move;
    public Setting<Boolean> blink;
    public Setting<Boolean> staticS;
    public Setting<Boolean> always;
    public Setting<Boolean> rotate;
    public Setting<Boolean> prioEnemies;
    public Setting<Boolean> silent;
    public Setting<Boolean> visCheck;
    public Setting<Boolean> oppSpotted;
    public Setting<Integer> runs;
    public Setting<Integer> buffer;
    public Setting<Integer> teleports;
    public Setting<Integer> interval;
    public Setting<Integer> armor;
    public Setting<Float> range;
    public Setting<Float> wallRange;
    public Setting<Float> targetRange;
    public Setting<Float> height;
    public Setting<Float> soft;
    private Setting<AuraTarget> targetMode;
    protected int packetsSent;
    protected boolean cancelling;
    protected boolean needsMessage;
    protected boolean blockUnder;
    protected Entity target;
    protected final ArrayList<EntityData> entityDataArrayList;
    int teleportID;
    
    public EZbowPOP() {
        super("EZbowPOP", "\u0428\u043e\u0442\u0430\u0435\u0442 \u0441 \u043b\u0443\u043a\u0430", Category.COMBAT, true, false, false);
        this.fov = (Setting<Float>)this.register(new Setting("FOV", (T)360.0f, (T)5.0f, (T)360.0f));
        this.cancelRotate = (Setting<Boolean>)this.register(new Setting("CancelRotate", (T)false));
        this.move = (Setting<Boolean>)this.register(new Setting("Move", (T)false));
        this.blink = (Setting<Boolean>)this.register(new Setting("Blink", (T)true));
        this.staticS = (Setting<Boolean>)this.register(new Setting("Static", (T)true));
        this.always = (Setting<Boolean>)this.register(new Setting("Always", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.prioEnemies = (Setting<Boolean>)this.register(new Setting("Prio-Enemies", (T)true));
        this.silent = (Setting<Boolean>)this.register(new Setting("Silent", (T)true));
        this.visCheck = (Setting<Boolean>)this.register(new Setting("VisCheck", (T)true));
        this.oppSpotted = (Setting<Boolean>)this.register(new Setting("Opp-Spotted", (T)false));
        this.runs = (Setting<Integer>)this.register(new Setting("Runs", (T)8, (T)1, (T)200));
        this.buffer = (Setting<Integer>)this.register(new Setting("Buffer", (T)10, (T)0, (T)200));
        this.teleports = (Setting<Integer>)this.register(new Setting("Teleports", (T)0, (T)0, (T)200));
        this.interval = (Setting<Integer>)this.register(new Setting("Interval", (T)25, (T)0, (T)100));
        this.armor = (Setting<Integer>)this.register(new Setting("Armor", (T)0, (T)0, (T)100));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)15.0f, (T)0.0f, (T)30.0f));
        this.wallRange = (Setting<Float>)this.register(new Setting("WallRange", (T)10.0f, (T)0.0f, (T)30.0f));
        this.targetRange = (Setting<Float>)this.register(new Setting("Target-Range", (T)30.0f, (T)0.0f, (T)50.0f));
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)1.0f, (T)0.0f, (T)1.0f));
        this.soft = (Setting<Float>)this.register(new Setting("Soft", (T)180.0f, (T)0.1f, (T)180.0f));
        this.targetMode = (Setting<AuraTarget>)this.register(new Setting("Rotation Mode", (T)AuraTarget.Closest));
        this.packetsSent = 0;
        this.blockUnder = false;
        this.entityDataArrayList = new ArrayList<EntityData>();
        this.teleportID = 0;
    }
    
    @Override
    public void onEnable() {
        this.packetsSent = 0;
        this.cancelling = false;
        this.needsMessage = true;
    }
    
    @SubscribeEvent
    protected void onPacketSend(final PacketEvent.Send event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayerDigging) {
            if (!EZbowPOP.mc.player.collidedVertically) {
                return;
            }
            if (((CPacketPlayerDigging)event.getPacket()).getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && EZbowPOP.mc.player.getActiveItemStack().getItem() == Items.BOW && this.blockUnder) {
                this.cancelling = false;
                if (this.packetsSent >= this.runs.getValue() * 2 || this.always.getValue()) {
                    sendAction(CPacketEntityAction.Action.START_SPRINTING);
                    for (int i = 0; i < this.runs.getValue() + this.buffer.getValue(); ++i) {
                        if (i != 0 && i % this.interval.getValue() == 0) {
                            int id = this.teleportID;
                            for (int j = 0; j < this.teleports.getValue(); ++j) {
                                EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(++id));
                            }
                        }
                        final double[] dir = MovementUtil.strafe(0.001);
                        if (this.rotate.getValue()) {
                            this.target = this.findTarget();
                            if (this.target != null) {
                                final float[] rotations = this.getRotations((Entity)RotationUtil.getRotationPlayer(), this.target, this.height.getValue(), this.soft.getValue());
                                if (rotations != null) {
                                    doPosRotNoEvent(EZbowPOP.mc.player.posX + (this.move.getValue() ? dir[0] : 0.0), EZbowPOP.mc.player.posY + 1.3E-13, EZbowPOP.mc.player.posZ + (this.move.getValue() ? dir[1] : 0.0), rotations[0], rotations[1], true);
                                    doPosRotNoEvent(EZbowPOP.mc.player.posX + (this.move.getValue() ? (dir[0] * 2.0) : 0.0), EZbowPOP.mc.player.posY + 2.7E-13, EZbowPOP.mc.player.posZ + (this.move.getValue() ? (dir[1] * 2.0) : 0.0), rotations[0], rotations[1], false);
                                }
                            }
                            else {
                                doPosRotNoEvent(EZbowPOP.mc.player.posX + (this.move.getValue() ? dir[0] : 0.0), EZbowPOP.mc.player.posY + 1.3E-13, EZbowPOP.mc.player.posZ + (this.move.getValue() ? dir[1] : 0.0), EZbowPOP.mc.player.rotationYaw, EZbowPOP.mc.player.rotationPitch, true);
                                doPosRotNoEvent(EZbowPOP.mc.player.posX + (this.move.getValue() ? (dir[0] * 2.0) : 0.0), EZbowPOP.mc.player.posY + 2.7E-13, EZbowPOP.mc.player.posZ + (this.move.getValue() ? (dir[1] * 2.0) : 0.0), EZbowPOP.mc.player.rotationYaw, EZbowPOP.mc.player.rotationPitch, false);
                            }
                        }
                        else {
                            doPosRotNoEvent(EZbowPOP.mc.player.posX + (this.move.getValue() ? dir[0] : 0.0), EZbowPOP.mc.player.posY + 1.3E-13, EZbowPOP.mc.player.posZ + (this.move.getValue() ? dir[1] : 0.0), EZbowPOP.mc.player.rotationYaw, EZbowPOP.mc.player.rotationPitch, true);
                            doPosRotNoEvent(EZbowPOP.mc.player.posX + (this.move.getValue() ? (dir[0] * 2.0) : 0.0), EZbowPOP.mc.player.posY + 2.7E-13, EZbowPOP.mc.player.posZ + (this.move.getValue() ? (dir[1] * 2.0) : 0.0), EZbowPOP.mc.player.rotationYaw, EZbowPOP.mc.player.rotationPitch, false);
                        }
                    }
                }
                this.packetsSent = 0;
            }
        }
        if (!this.cancelRotate.getValue() && event.getPacket() instanceof CPacketPlayer.Rotation) {
            return;
        }
        if (!EZbowPOP.mc.player.onGround) {
            return;
        }
        if (this.blink.getValue() && this.cancelling) {
            event.setCanceled(true);
        }
    }
    
    protected Entity findTarget() {
        for (final Entity ent : EZbowPOP.mc.world.loadedEntityList) {
            if (ent instanceof EntityPlayer && canSeeEntityAtFov(ent, this.fov.getValue())) {
                return ent;
            }
        }
        return null;
    }
    
    public static double angleDifference(final double a, final double b) {
        float yaw360 = (float)(Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }
    
    public static boolean canSeeEntityAtFov(final Entity entityLiving, final float scope) {
        final double diffX = entityLiving.posX - Minecraft.getMinecraft().player.posX;
        final double diffZ = entityLiving.posZ - Minecraft.getMinecraft().player.posZ;
        final float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        final double difference = angleDifference(newYaw, Minecraft.getMinecraft().player.rotationYaw);
        return difference <= scope;
    }
    
    public boolean isInRange(final Entity from, final Entity target) {
        return this.isInRange(from.getPositionVector(), target);
    }
    
    public boolean isInRange(final Vec3d from, final Entity target) {
        final double distance = from.squareDistanceTo(target.getPositionVector());
        return distance < MathUtil.square(this.range.getValue()) && (distance < MathUtil.square(this.wallRange.getValue()) || EZbowPOP.mc.world.rayTraceBlocks(new Vec3d(from.x, from.y + EZbowPOP.mc.player.getEyeHeight(), from.z), new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ), false, true, false) == null);
    }
    
    public boolean isValid(final Entity entity) {
        return entity != null && !EntityUtil.isDead(entity) && !entity.equals((Object)EZbowPOP.mc.player) && !entity.equals((Object)EZbowPOP.mc.player.getRidingEntity()) && (!(entity instanceof EntityPlayer) || !Thunderhack.friendManager.isFriend((EntityPlayer)entity)) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityItem) && !(entity instanceof EntityArrow) && !(entity instanceof EntityEnderCrystal);
    }
    
    public boolean hasEntity(final String id) {
        return this.entityDataArrayList.stream().anyMatch(entityData -> Objects.equals(entityData.id, id));
    }
    
    @SubscribeEvent
    public void onEntityAdded(final EntityAddedEvent event) {
        if (!this.oppSpotted.getValue()) {
            return;
        }
        if (event.entity != null && this.isValid(event.entity)) {
            Command.sendMessage(ChatFormatting.RED + "Opp detected I repeat opp detected!");
            if (!this.hasEntity(event.entity.getUniqueID().toString())) {
                this.entityDataArrayList.add(new EntityData(event.entity.getUniqueID().toString(), System.currentTimeMillis()));
            }
        }
    }
    
    @SubscribeEvent
    public void onMotionUpdate(final MotionUpdateEvent event) {
        this.entityDataArrayList.removeIf(e -> e.getTime() + 60000L < System.currentTimeMillis());
        if (!EZbowPOP.mc.player.collidedVertically) {
            return;
        }
        if (event.getStage() == 0) {
            this.blockUnder = this.isBlockUnder();
            if (this.rotate.getValue() && EZbowPOP.mc.player.getActiveItemStack().getItem() == Items.BOW && EZbowPOP.mc.gameSettings.keyBindUseItem.isKeyDown() && this.blockUnder) {
                this.target = this.findTarget();
                if (this.target != null) {
                    final float[] rotations = this.getRotations((Entity)RotationUtil.getRotationPlayer(), this.target, this.height.getValue(), this.soft.getValue());
                    if (rotations != null) {
                        if (this.silent.getValue()) {
                            SilentRotaionUtil.lookAtAngles(rotations[0], rotations[1]);
                        }
                        else {
                            EZbowPOP.mc.player.rotationYaw = rotations[0];
                            EZbowPOP.mc.player.rotationPitch = rotations[1];
                        }
                    }
                }
            }
            if (EZbowPOP.mc.player.getActiveItemStack().getItem() == Items.BOW && EZbowPOP.mc.player.isHandActive() && !this.blockUnder) {
                final int newSlot = this.findBlockInHotbar();
                if (newSlot != -1) {
                    final int oldSlot = EZbowPOP.mc.player.inventory.currentItem;
                    EZbowPOP.mc.player.inventory.currentItem = newSlot;
                    this.placeBlock(PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).down(1), event);
                    EZbowPOP.mc.player.inventory.currentItem = oldSlot;
                }
            }
        }
        else if (EZbowPOP.mc.player.getActiveItemStack().getItem() != Items.BOW) {
            this.cancelling = false;
            this.packetsSent = 0;
        }
        else if (EZbowPOP.mc.player.getActiveItemStack().getItem() == Items.BOW && EZbowPOP.mc.player.isHandActive() && this.cancelling && this.blockUnder) {
            ++this.packetsSent;
            if (this.packetsSent > this.runs.getValue() * 2 && !this.always.getValue() && this.needsMessage) {
                Command.sendMessage("Charged!");
            }
        }
    }
    
    private int findBlockInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = EZbowPOP.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private boolean canBeClicked(final BlockPos pos) {
        return EZbowPOP.mc.world.getBlockState(pos).getBlock().canCollideCheck(EZbowPOP.mc.world.getBlockState(pos), false);
    }
    
    private void placeBlock(final BlockPos pos, final MotionUpdateEvent event) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (this.canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(new Vec3d(0.5, 0.5, 0.5)).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                SilentRotaionUtil.lookAtVector(hitVec);
                EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)EZbowPOP.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                EZbowPOP.mc.playerController.processRightClickBlock(EZbowPOP.mc.player, EZbowPOP.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                EZbowPOP.mc.player.swingArm(EnumHand.MAIN_HAND);
                EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)EZbowPOP.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return;
            }
        }
    }
    
    private boolean isBlockUnder() {
        return !(EZbowPOP.mc.world.getBlockState(PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).down(1)).getBlock() instanceof BlockAir);
    }
    
    public float[] getRotations(final Entity from, final Entity entity, final double height, final double maxAngle) {
        return this.getRotations(entity, from.posX, from.posY, from.posZ, from.getEyeHeight(), height, maxAngle);
    }
    
    public float[] getRotations(final Entity entity, final double fromX, final double fromY, final double fromZ, final float eyeHeight, final double height, final double maxAngle) {
        final float[] rotations = RotationUtil.getRotations(entity.posX, entity.posY + entity.getEyeHeight() * height, entity.posZ, fromX, fromY, fromZ, eyeHeight);
        return this.smoothen(rotations, maxAngle);
    }
    
    public float[] smoothen(final float[] rotations, final double maxAngle) {
        final float[] server = { EZbowPOP.mc.player.rotationYaw, EZbowPOP.mc.player.rotationPitch };
        return this.smoothen(server, rotations, maxAngle);
    }
    
    public float[] smoothen(final float[] server, final float[] rotations, final double maxAngle) {
        if (maxAngle >= 180.0 || maxAngle <= 0.0 || RotationUtil.angle(server, rotations) <= maxAngle) {
            return rotations;
        }
        return faceSmoothly(server[0], server[1], rotations[0], rotations[1], maxAngle, maxAngle);
    }
    
    public static float[] faceSmoothly(final double curYaw, final double curPitch, final double intendedYaw, final double intendedPitch, final double yawSpeed, final double pitchSpeed) {
        final float yaw = updateRotation((float)curYaw, (float)intendedYaw, (float)yawSpeed);
        final float pitch = updateRotation((float)curPitch, (float)intendedPitch, (float)pitchSpeed);
        return new float[] { yaw, pitch };
    }
    
    public static float updateRotation(final float current, final float intended, final float factor) {
        float updated = MathHelper.wrapDegrees(intended - current);
        if (updated > factor) {
            updated = factor;
        }
        if (updated < -factor) {
            updated = -factor;
        }
        return current + updated;
    }
    
    @SubscribeEvent
    public void onMove(final EventMove event) {
        if (!EZbowPOP.mc.player.collidedVertically) {
            return;
        }
        if (this.staticS.getValue() && EZbowPOP.mc.player.getActiveItemStack().getItem() instanceof ItemBow && this.blockUnder) {
            EZbowPOP.mc.player.setVelocity(0.0, 0.0, 0.0);
            event.set_x(0.0);
            event.set_y(0.0);
            event.set_z(0.0);
        }
    }
    
    @SubscribeEvent
    public void invoke(final RightClickItemEvent event) {
        if (!EZbowPOP.mc.player.collidedVertically) {
            return;
        }
        if (EZbowPOP.mc.player.getHeldItem(event.getHand()).getItem() == Items.BOW && this.blockUnder) {
            this.cancelling = true;
            this.needsMessage = true;
        }
    }
    
    public static void sendAction(final CPacketEntityAction.Action action) {
        EZbowPOP.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)EZbowPOP.mc.player, action));
    }
    
    public static void doPosRotNoEvent(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        EZbowPOP.mc.player.connection.sendPacket((Packet)positionRotation(x, y, z, yaw, pitch, onGround));
    }
    
    public static CPacketPlayer positionRotation(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, onGround);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
            this.teleportID = packet.getTeleportId();
        }
    }
    
    public enum AuraTarget
    {
        Closest, 
        Smart, 
        Angle;
    }
    
    public static class EntityData
    {
        private final String id;
        private final long time;
        
        public EntityData(final String id, final long time) {
            this.id = id;
            this.time = time;
        }
        
        public String getId() {
            return this.id;
        }
        
        public long getTime() {
            return this.time;
        }
    }
}
