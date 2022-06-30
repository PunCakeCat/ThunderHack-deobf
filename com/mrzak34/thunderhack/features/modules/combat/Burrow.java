//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import java.util.*;
import java.util.concurrent.*;
import com.google.common.collect.*;

public class Burrow extends Module
{
    public boolean vis;
    private static final Map<EntityPlayer, PopCounter> pops;
    private volatile int teleportID;
    private volatile double last_x;
    private volatile double last_y;
    private volatile double last_z;
    private volatile boolean onGround2;
    private Setting<OffsetMode> offsetMode;
    public static final Set<Block> BAD_BLOCKS;
    public static final Set<Block> SHULKERS;
    public Setting<Float> vClip;
    public Setting<Float> minDown;
    public Setting<Float> maxDown;
    public Setting<Float> minUp;
    public Setting<Float> maxUp;
    public Setting<Float> scaleFactor;
    public Setting<Integer> scaleDelay;
    public Setting<Integer> cooldown;
    public Setting<Integer> delay;
    public Setting<Boolean> scaleDown;
    public Setting<Boolean> scaleVelocity;
    public Setting<Boolean> scaleExplosion;
    public Setting<Boolean> attackBefore;
    public Setting<Boolean> antiWeakness;
    public Setting<Boolean> attack;
    public Setting<Boolean> deltaY;
    public Setting<Boolean> placeDisable;
    public Setting<Boolean> wait;
    public Setting<Boolean> highBlock;
    public Setting<Boolean> evade;
    public Setting<Boolean> noVoid;
    public Setting<Boolean> conflict;
    public Setting<Boolean> onGround;
    public Setting<Boolean> allowUp;
    public Setting<Boolean> beacon;
    public Setting<Boolean> echest;
    public Setting<Boolean> anvil;
    public Setting<Boolean> rotate;
    public Setting<Boolean> discrete;
    public Setting<Boolean> air;
    public Setting<Boolean> fallback;
    public Setting<Boolean> skipZero;
    protected final Timer scaleTimer;
    protected final Timer timer;
    protected double motionY;
    protected BlockPos startPos;
    
    public Burrow() {
        super("Burrow", "\u0421\u0442\u0430\u0432\u0438\u0442 \u0432 \u0442\u0435\u0431\u044f \u0431\u043b\u043e\u043a", Category.COMBAT, true, false, false);
        this.vis = false;
        this.offsetMode = (Setting<OffsetMode>)this.register(new Setting("Mode", (T)OffsetMode.Smart));
        this.vClip = (Setting<Float>)this.register(new Setting("V-Clip", (T)(-9.0f), (T)(-256.0f), (T)256.0f));
        this.minDown = (Setting<Float>)this.register(new Setting("Min-Down", (T)3.0f, (T)0.0f, (T)1337.0f));
        this.maxDown = (Setting<Float>)this.register(new Setting("Max-Down", (T)10.0f, (T)0.0f, (T)1337.0f));
        this.minUp = (Setting<Float>)this.register(new Setting("Min-Up", (T)3.0f, (T)0.0f, (T)1337.0f));
        this.maxUp = (Setting<Float>)this.register(new Setting("Max-Up", (T)10.0f, (T)0.0f, (T)1337.0f));
        this.scaleFactor = (Setting<Float>)this.register(new Setting("Scale-Factor", (T)1.0f, (T)0.1f, (T)10.0f));
        this.scaleDelay = (Setting<Integer>)this.register(new Setting("Scale-Delay", (T)250, (T)0, (T)1000));
        this.cooldown = (Setting<Integer>)this.register(new Setting("Cooldown", (T)500, (T)0, (T)500));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)100, (T)0, (T)1000));
        this.scaleDown = (Setting<Boolean>)this.register(new Setting("Scale-Down", (T)false));
        this.scaleVelocity = (Setting<Boolean>)this.register(new Setting("Scale-Velocity", (T)false));
        this.scaleExplosion = (Setting<Boolean>)this.register(new Setting("Scale-Explosion", (T)false));
        this.attackBefore = (Setting<Boolean>)this.register(new Setting("Attack-Before", (T)false));
        this.antiWeakness = (Setting<Boolean>)this.register(new Setting("antiWeakness", (T)false));
        this.attack = (Setting<Boolean>)this.register(new Setting("Attack", (T)false));
        this.deltaY = (Setting<Boolean>)this.register(new Setting("Delta-Y", (T)true));
        this.placeDisable = (Setting<Boolean>)this.register(new Setting("PlaceDisable", (T)false));
        this.wait = (Setting<Boolean>)this.register(new Setting("Wait", (T)true));
        this.highBlock = (Setting<Boolean>)this.register(new Setting("HighBlock", (T)false));
        this.evade = (Setting<Boolean>)this.register(new Setting("Evade", (T)false));
        this.noVoid = (Setting<Boolean>)this.register(new Setting("NoVoid", (T)false));
        this.conflict = (Setting<Boolean>)this.register(new Setting("Conflict", (T)true));
        this.onGround = (Setting<Boolean>)this.register(new Setting("OnGround", (T)true));
        this.allowUp = (Setting<Boolean>)this.register(new Setting("Allow-Up", (T)false));
        this.beacon = (Setting<Boolean>)this.register(new Setting("Beacon", (T)false));
        this.echest = (Setting<Boolean>)this.register(new Setting("E-Chest", (T)false));
        this.anvil = (Setting<Boolean>)this.register(new Setting("Anvil", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.discrete = (Setting<Boolean>)this.register(new Setting("Discrete", (T)true));
        this.air = (Setting<Boolean>)this.register(new Setting("Air", (T)false));
        this.fallback = (Setting<Boolean>)this.register(new Setting("Fallback", (T)true));
        this.skipZero = (Setting<Boolean>)this.register(new Setting("SkipZero", (T)true));
        this.scaleTimer = new Timer();
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        this.timer.reset();
        if (Burrow.mc.world == null || Burrow.mc.player == null) {
            return;
        }
        this.startPos = this.getPlayerPos();
    }
    
    protected void attack(final Packet<?> attacking, final int slot) {
        if (slot != -1) {
            switchToHotbarSlot(slot, true);
        }
        send(attacking);
        this.swing(EnumHand.MAIN_HAND);
    }
    
    public static void send(final Packet<?> packet) {
        final NetHandlerPlayClient connection = Burrow.mc.getConnection();
        if (connection != null) {
            connection.sendPacket((Packet)packet);
        }
    }
    
    public void swing(final EnumHand hand) {
        swingPacket(hand);
    }
    
    public static void swingPacket(final EnumHand hand) {
        Objects.requireNonNull(Burrow.mc.getConnection()).sendPacket((Packet)new CPacketAnimation(hand));
    }
    
    public static void switchToHotbarSlot(final int slot, final boolean silent) {
        if (Burrow.mc.player.inventory.currentItem == slot || slot < 0) {
            return;
        }
        if (silent) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            Burrow.mc.playerController.updateController();
        }
        else {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            Burrow.mc.player.inventory.currentItem = slot;
            Burrow.mc.playerController.updateController();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() != 0) {
            return;
        }
        if (event.getPacket() instanceof SPacketExplosion && this.scaleExplosion.getValue()) {
            this.scaleTimer.reset();
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            if (this.scaleVelocity.getValue()) {
                return;
            }
            final EntityPlayerSP playerSP = Burrow.mc.player;
            if (playerSP != null) {
                this.scaleTimer.reset();
            }
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
                x += Burrow.mc.player.posX;
            }
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
                y += Burrow.mc.player.posY;
            }
            if (packet.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
                z += Burrow.mc.player.posZ;
            }
            this.last_x = MathHelper.clamp(x, -3.0E7, 3.0E7);
            this.last_y = y;
            this.last_z = MathHelper.clamp(z, -3.0E7, 3.0E7);
            this.onGround2 = false;
            this.teleportID = packet.getTeleportId();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.wait.getValue()) {
            final BlockPos currentPos = this.getPlayerPos();
            if (!currentPos.equals((Object)this.startPos)) {
                this.disable();
                return;
            }
        }
        if (this.isInsideBlock()) {
            return;
        }
        final EntityPlayer rEntity = (EntityPlayer)Burrow.mc.player;
        final BlockPos pos = getPosition((Entity)rEntity);
        if (!Burrow.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            if (!this.wait.getValue()) {
                this.disable();
            }
            return;
        }
        final BlockPos posHead = getPosition((Entity)rEntity).up().up();
        if (!Burrow.mc.world.getBlockState(posHead).getMaterial().isReplaceable() && this.wait.getValue()) {
            return;
        }
        final CPacketUseEntity attacking = null;
        boolean crystals = false;
        final float currentDmg = Float.MAX_VALUE;
        for (final Entity entity : Burrow.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity != null && !Burrow.mc.player.equals((Object)entity) && entity.preventEntitySpawning) {
                if (!(entity instanceof EntityEnderCrystal) || !this.attack.getValue()) {
                    if (!this.wait.getValue()) {
                        this.disable();
                    }
                    return;
                }
                System.out.println("\u0430\u0442\u0442\u0430\u043a\u0443\u0435\u043c \u043a\u0440\u0438\u0441\u0442\u0430\u043b \u0435\u0431\u0443\u0447\u0438\u0439");
                EntityUtil.attackEntity(entity, true, true);
                crystals = true;
            }
        }
        int weaknessSlot = -1;
        if (crystals) {
            if (attacking == null) {
                if (!this.wait.getValue()) {
                    this.disable();
                }
                return;
            }
            if (!canBreakWeakness(true) && (!this.antiWeakness.getValue() || this.cooldown.getValue() != 0 || (weaknessSlot = findAntiWeakness()) == -1)) {
                if (!this.wait.getValue()) {
                    this.disable();
                }
                return;
            }
        }
        if (!this.allowUp.getValue()) {
            final BlockPos upUp = pos.up(2);
            final IBlockState upState = Burrow.mc.world.getBlockState(upUp);
            if (upState.getMaterial().blocksMovement()) {
                if (!this.wait.getValue()) {
                    this.disable();
                }
                return;
            }
        }
        final int slot = this.anvil.getValue() ? InventoryUtil.findHotbarBlock(Blocks.ANVIL) : (this.beacon.getValue() ? InventoryUtil.findHotbarBlock((Block)Blocks.BEACON) : ((this.echest.getValue() || Burrow.mc.world.getBlockState(pos.down()).getBlock() == Blocks.ENDER_CHEST) ? InventoryUtil.findHotbarBlock(Blocks.ENDER_CHEST) : InventoryUtil.findHotbarBlock(BlockObsidian.class)));
        if (slot == -1) {
            Command.sendMessage("No Block found!");
            return;
        }
        final EnumFacing f = BlockUtil.getFacing(pos);
        if (f == null) {
            if (!this.wait.getValue()) {
                this.disable();
            }
            return;
        }
        final double y = this.applyScale(this.getY((Entity)rEntity, this.offsetMode.getValue()));
        if (Double.isNaN(y)) {
            return;
        }
        final BlockPos on = pos.offset(f);
        final float[] r = getRotations(on, f.getOpposite(), (Entity)rEntity);
        final RayTraceResult result = getRayTraceResultWithEntity(r[0], r[1], (Entity)rEntity);
        final float[] vec = hitVecToPlaceVec(on, result.hitVec);
        final boolean sneaking = !shouldSneak(on, true);
        final EntityPlayer finalREntity = rEntity;
        final int finalWeaknessSlot = weaknessSlot;
        final CPacketUseEntity finalAttacking = attacking;
        if (this.singlePlayerCheck(pos)) {
            if (!this.wait.getValue() || this.placeDisable.getValue()) {
                this.disable();
            }
            return;
        }
        final int lastSlot = Burrow.mc.player.inventory.currentItem;
        if (this.attackBefore.getValue() && finalAttacking != null) {
            this.attack((Packet<?>)finalAttacking, finalWeaknessSlot);
        }
        if (this.conflict.getValue() || this.rotate.getValue()) {
            if (this.rotate.getValue()) {
                if (finalREntity.getPositionVector().equals((Object)this.getVec())) {
                    doRotation(r[0], r[1], true);
                }
                else {
                    doPosRot(finalREntity.posX, finalREntity.posY, finalREntity.posZ, r[0], r[1], true);
                }
            }
            else {
                doPosition(finalREntity.posX, finalREntity.posY, finalREntity.posZ, true);
            }
        }
        doY((Entity)finalREntity, finalREntity.posY + 0.42, this.onGround.getValue());
        doY((Entity)finalREntity, finalREntity.posY + 0.75, this.onGround.getValue());
        doY((Entity)finalREntity, finalREntity.posY + 1.01, this.onGround.getValue());
        doY((Entity)finalREntity, finalREntity.posY + 1.16, this.onGround.getValue());
        InventoryUtil.switchToHotbarSlot(slot, false);
        if (!sneaking) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        place(on, f.getOpposite(), slot, vec[0], vec[1], vec[2]);
        if (this.highBlock.getValue()) {
            doY((Entity)finalREntity, finalREntity.posY + 1.67, this.onGround.getValue());
            doY((Entity)finalREntity, finalREntity.posY + 2.01, this.onGround.getValue());
            doY((Entity)finalREntity, finalREntity.posY + 2.42, this.onGround.getValue());
            final BlockPos highPos = pos.up();
            final EnumFacing face = EnumFacing.DOWN;
            place(highPos.offset(face), face.getOpposite(), slot, vec[0], vec[1], vec[2]);
        }
        swing(slot);
        InventoryUtil.switchToHotbarSlot(lastSlot, false);
        if (!sneaking) {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        doY((Entity)rEntity, y, false);
        this.timer.reset();
        if (!this.wait.getValue() || this.placeDisable.getValue()) {
            this.disable();
        }
    }
    
    public static void swing(final int slot) {
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketAnimation(getHand(slot)));
    }
    
    public Vec3d getVec() {
        return new Vec3d(this.last_x, this.last_y, this.last_z);
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from) {
        return getRotations(pos, facing, from, (IBlockAccess)Burrow.mc.world, Burrow.mc.world.getBlockState(pos));
    }
    
    public static float[] getRotations(final BlockPos pos, final EnumFacing facing, final Entity from, final IBlockAccess world, final IBlockState state) {
        final AxisAlignedBB bb = state.getBoundingBox(world, pos);
        double x = pos.getX() + (bb.minX + bb.maxX) / 2.0;
        double y = pos.getY() + (bb.minY + bb.maxY) / 2.0;
        double z = pos.getZ() + (bb.minZ + bb.maxZ) / 2.0;
        if (facing != null) {
            x += facing.getDirectionVec().getX() * ((bb.minX + bb.maxX) / 2.0);
            y += facing.getDirectionVec().getY() * ((bb.minY + bb.maxY) / 2.0);
            z += facing.getDirectionVec().getZ() * ((bb.minZ + bb.maxZ) / 2.0);
        }
        return getRotations(x, y, z, from);
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final Entity f) {
        return getRotations(x, y, z, f.posX, f.posY, f.posZ, f.getEyeHeight());
    }
    
    public static float[] getRotations(final double x, final double y, final double z, final double fromX, final double fromY, final double fromZ, final float fromHeight) {
        final double xDiff = x - fromX;
        final double yDiff = y - (fromY + fromHeight);
        final double zDiff = z - fromZ;
        final double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        final float prevYaw = Burrow.mc.player.rotationYaw;
        float diff = yaw - prevYaw;
        if (diff < -180.0f || diff > 180.0f) {
            final float round = (float)Math.round(Math.abs(diff / 360.0f));
            diff = ((diff < 0.0f) ? (diff + 360.0f * round) : (diff - 360.0f * round));
        }
        return new float[] { prevYaw + diff, pitch };
    }
    
    public static void doRotation(final float yaw, final float pitch, final boolean onGround) {
        Burrow.mc.player.connection.sendPacket((Packet)rotation(yaw, pitch, onGround));
    }
    
    public static CPacketPlayer rotation(final float yaw, final float pitch, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.Rotation(yaw, pitch, onGround);
    }
    
    protected double getY(final Entity entity, final OffsetMode mode) {
        if (mode == OffsetMode.Constant) {
            double y = entity.posY + this.vClip.getValue();
            if (this.evade.getValue() && Math.abs(y) < 1.0) {
                y = -1.0;
            }
            return y;
        }
        double d = this.getY(entity, this.minDown.getValue(), this.maxDown.getValue(), true);
        if (Double.isNaN(d)) {
            d = this.getY(entity, -this.minUp.getValue(), -this.maxUp.getValue(), false);
            if (Double.isNaN(d) && this.fallback.getValue()) {
                return this.getY(entity, OffsetMode.Constant);
            }
        }
        return d;
    }
    
    public static void doY(final Entity entity, final double y, final boolean onGround) {
        doPosition(entity.posX, y, entity.posZ, onGround);
    }
    
    public static void doPosition(final double x, final double y, final double z, final boolean onGround) {
        Burrow.mc.player.connection.sendPacket((Packet)position(x, y, z, onGround));
    }
    
    public static CPacketPlayer position(final double x, final double y, final double z) {
        return position(x, y, z, Burrow.mc.player.onGround);
    }
    
    public static CPacketPlayer position(final double x, final double y, final double z, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.Position(x, y, z, onGround);
    }
    
    public static void doPosRot(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        Burrow.mc.player.connection.sendPacket((Packet)positionRotation(x, y, z, yaw, pitch, onGround));
    }
    
    public static CPacketPlayer positionRotation(final double x, final double y, final double z, final float yaw, final float pitch, final boolean onGround) {
        return (CPacketPlayer)new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, onGround);
    }
    
    public static void place(final BlockPos on, final EnumFacing facing, final int slot, final float x, final float y, final float z) {
        try {
            place(on, facing, getHand(slot), x, y, z);
        }
        catch (Exception e) {
            Command.sendMessage("Failed to place the block");
        }
    }
    
    public static EnumHand getHand(final int slot) {
        return (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }
    
    public static void place(final BlockPos on, final EnumFacing facing, final EnumHand hand, final float x, final float y, final float z) {
        try {
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(on, facing, hand, x, y, z));
        }
        catch (Exception exception) {
            Command.sendMessage("Failed to place the block");
        }
    }
    
    protected double getY(final Entity entity, final double min, final double max, final boolean add) {
        if ((min > max && add) || (max > min && !add)) {
            return Double.NaN;
        }
        final double x = entity.posX;
        final double y = entity.posY;
        final double z = entity.posZ;
        boolean air = false;
        double lastOff = 0.0;
        BlockPos last = null;
        double off = min;
        while (true) {
            if (add) {
                if (off >= max) {
                    break;
                }
            }
            else if (off <= max) {
                break;
            }
            final BlockPos pos = new BlockPos(x, y - off, z);
            if (!this.noVoid.getValue() || pos.getY() >= 0) {
                if (this.skipZero.getValue() && Math.abs(y) < 1.0) {
                    air = false;
                    last = pos;
                    lastOff = y - off;
                }
                else {
                    final IBlockState state = Burrow.mc.world.getBlockState(pos);
                    if ((!this.air.getValue() && !state.getMaterial().blocksMovement()) || state.getBlock() == Blocks.AIR) {
                        if (air) {
                            if (add) {
                                return this.discrete.getValue() ? pos.getY() : (y - off);
                            }
                            return this.discrete.getValue() ? last.getY() : lastOff;
                        }
                        else {
                            air = true;
                        }
                    }
                    else {
                        air = false;
                    }
                    last = pos;
                    lastOff = y - off;
                }
            }
            off = (add ? (++off) : (--off));
        }
        return Double.NaN;
    }
    
    protected double applyScale(double value) {
        if ((value < Burrow.mc.player.posY && !this.scaleDown.getValue()) || (!this.scaleExplosion.getValue() && !this.scaleVelocity.getValue()) || this.scaleTimer.passed(this.scaleDelay.getValue()) || this.motionY == 0.0) {
            return value;
        }
        if (value < Burrow.mc.player.posY) {
            value -= this.motionY * this.scaleFactor.getValue();
        }
        else {
            value += this.motionY * this.scaleFactor.getValue();
        }
        return this.discrete.getValue() ? Math.floor(value) : value;
    }
    
    protected BlockPos getPlayerPos() {
        return (this.deltaY.getValue() && Math.abs(Burrow.mc.player.motionY) > 0.1) ? new BlockPos((Entity)Burrow.mc.player) : getPosition((Entity)Burrow.mc.player);
    }
    
    public static BlockPos getPosition(final Entity entity) {
        return getPosition(entity, 0.0);
    }
    
    public static BlockPos getPosition(final Entity entity, final double yOffset) {
        double y = entity.posY + yOffset;
        if (entity.posY - Math.floor(entity.posY) > 0.5) {
            y = Math.ceil(entity.posY);
        }
        return new BlockPos(entity.posX, y, entity.posZ);
    }
    
    protected boolean isInsideBlock() {
        final double x = Burrow.mc.player.posX;
        final double y = Burrow.mc.player.posY + 0.2;
        final double z = Burrow.mc.player.posZ;
        return Burrow.mc.world.getBlockState(new BlockPos(x, y, z)).getMaterial().blocksMovement() || !Burrow.mc.player.collidedVertically;
    }
    
    protected boolean singlePlayerCheck(final BlockPos pos) {
        if (!Burrow.mc.isSingleplayer()) {
            return false;
        }
        final EntityPlayer player = (EntityPlayer)Burrow.mc.getIntegratedServer().getPlayerList().getPlayerByUUID(Burrow.mc.player.getUniqueID());
        if (player == null) {
            this.disable();
            return true;
        }
        player.getEntityWorld().setBlockState(pos, ((boolean)this.echest.getValue()) ? Blocks.ENDER_CHEST.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
        Burrow.mc.world.setBlockState(pos, ((boolean)this.echest.getValue()) ? Blocks.ENDER_CHEST.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
        return true;
    }
    
    public static boolean canBreakWeakness(final boolean checkStack) {
        if (!Burrow.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            return true;
        }
        int strengthAmp = 0;
        final PotionEffect effect = Burrow.mc.player.getActivePotionEffect(MobEffects.STRENGTH);
        if (effect != null) {
            strengthAmp = effect.getAmplifier();
        }
        return strengthAmp >= 1 || (checkStack && canBreakWeakness(Burrow.mc.player.getHeldItemMainhand()));
    }
    
    public static boolean canBreakWeakness(final ItemStack stack) {
        return stack.getItem() instanceof ItemSword;
    }
    
    public static boolean shouldSneak(final BlockPos pos, final boolean manager) {
        return shouldSneak(pos, (IBlockAccess)Burrow.mc.world, manager);
    }
    
    public static boolean shouldSneak(final BlockPos pos, final IBlockAccess provider, final boolean manager) {
        return shouldSneak(provider.getBlockState(pos).getBlock(), manager);
    }
    
    public static boolean shouldSneak(final Block block, final boolean manager) {
        return (!manager || !Burrow.mc.player.isSneaking()) && (Burrow.BAD_BLOCKS.contains(block) || Burrow.SHULKERS.contains(block));
    }
    
    public static float[] hitVecToPlaceVec(final BlockPos pos, final Vec3d hitVec) {
        final float x = (float)(hitVec.x - pos.getX());
        final float y = (float)(hitVec.y - pos.getY());
        final float z = (float)(hitVec.z - pos.getZ());
        return new float[] { x, y, z };
    }
    
    public static RayTraceResult getRayTraceResultWithEntity(final float yaw, final float pitch, final Entity from) {
        return getRayTraceResult(yaw, pitch, Burrow.mc.playerController.getBlockReachDistance(), from);
    }
    
    public static RayTraceResult getRayTraceResult(final float yaw, final float pitch, final float d, final Entity from) {
        final Vec3d vec3d = getEyePos(from);
        final Vec3d lookVec = getVec3d(yaw, pitch);
        final Vec3d rotations = vec3d.add(lookVec.x * d, lookVec.y * d, lookVec.z * d);
        final RayTraceResult rayTraceResult;
        return Optional.ofNullable(Burrow.mc.world.rayTraceBlocks(vec3d, rotations, false, false, false)).orElseGet(() -> {
            new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, BlockPos.ORIGIN);
            return rayTraceResult;
        });
    }
    
    public static Vec3d getVec3d(final float yaw, final float pitch) {
        final float vx = -MathHelper.sin(rad(yaw)) * MathHelper.cos(rad(pitch));
        final float vz = MathHelper.cos(rad(yaw)) * MathHelper.cos(rad(pitch));
        final float vy = -MathHelper.sin(rad(pitch));
        return new Vec3d((double)vx, (double)vy, (double)vz);
    }
    
    public static float rad(final float angle) {
        return (float)(angle * 3.141592653589793 / 180.0);
    }
    
    public static Vec3d getEyePos(final Entity entity) {
        return new Vec3d(entity.posX, getEyeHeight(entity), entity.posZ);
    }
    
    public static double getEyeHeight() {
        return getEyeHeight((Entity)Burrow.mc.player);
    }
    
    public static double getEyeHeight(final Entity entity) {
        return entity.posY + entity.getEyeHeight();
    }
    
    public static int findAntiWeakness() {
        int slot = -1;
        for (int i = 8; i > -1 && (!canBreakWeakness(Burrow.mc.player.inventory.getStackInSlot(i)) || Burrow.mc.player.inventory.currentItem != (slot = i)); --i) {}
        return slot;
    }
    
    static {
        pops = new ConcurrentHashMap<EntityPlayer, PopCounter>();
        BAD_BLOCKS = Sets.newHashSet((Object[])new Block[] { Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE });
        SHULKERS = Sets.newHashSet((Object[])new Block[] { Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX });
    }
    
    private static class PopCounter
    {
        private final Timer timer;
        private int pops;
        
        private PopCounter() {
            this.timer = new Timer();
        }
        
        public int getPops() {
            return this.pops;
        }
        
        public void pop() {
            this.timer.reset();
            ++this.pops;
        }
        
        public void reset() {
            this.pops = 0;
        }
        
        public long lastPop() {
            return this.timer.getPassedTimeMs();
        }
    }
    
    public enum OffsetMode
    {
        Constant, 
        Smart;
    }
}
