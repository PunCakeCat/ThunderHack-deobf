//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.util.*;

public class Surround extends Module
{
    private final Timer delayTimer;
    public Setting<Boolean> alertPlayerClip;
    public Setting<Boolean> destroyAboveCrystal;
    public Setting<Boolean> destroyCrystal;
    public Setting<Boolean> rotate;
    public Setting<Boolean> predict;
    public Setting<Boolean> onlyOnSneak;
    public Setting<Boolean> disableOnJump;
    public Setting<Boolean> onlyOnStop;
    public Setting<Boolean> allowNon1x1;
    public Setting<Boolean> centre;
    public Setting<Integer> afterRotate;
    public Setting<Integer> blocksPerTick;
    public Setting<Integer> delayTicks;
    public Setting<Boolean> protate;
    ArrayList<BlockPos> blockChanged;
    int y;
    Timer alertDelay;
    boolean hasPlaced;
    int lookDown;
    int tickAC;
    
    public Surround() {
        super("Surround", "\u0417\u0430\u0449\u0438\u0449\u0430\u0435\u0442 \u0442\u0435\u0431\u044f \u043e\u0442-\u043a\u0440\u0438\u0441\u0442\u0430\u043b\u043b\u043e\u0432", Category.COMBAT, true, false, false);
        this.delayTimer = new Timer();
        this.alertPlayerClip = (Setting<Boolean>)this.register(new Setting("Alert Player Clip", (T)false));
        this.destroyAboveCrystal = (Setting<Boolean>)this.register(new Setting("Destroy Above Crystal", (T)false));
        this.destroyCrystal = (Setting<Boolean>)this.register(new Setting("Destroy Stuck Crystal", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.predict = (Setting<Boolean>)this.register(new Setting("Predict", (T)false));
        this.onlyOnSneak = (Setting<Boolean>)this.register(new Setting("Only on Sneak", (T)false));
        this.disableOnJump = (Setting<Boolean>)this.register(new Setting("Disable On Jump", (T)false));
        this.onlyOnStop = (Setting<Boolean>)this.register(new Setting("OnStop", (T)false));
        this.allowNon1x1 = (Setting<Boolean>)this.register(new Setting("Allow non 1x1", (T)true));
        this.centre = (Setting<Boolean>)this.register(new Setting("Centre", (T)false, v -> !this.allowNon1x1.getValue()));
        this.afterRotate = (Setting<Integer>)this.register(new Setting("Post Secure", (T)3, (T)0, (T)5, v -> this.rotate.getValue()));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("Blocks Per Tick", (T)4, (T)1, (T)20));
        this.delayTicks = (Setting<Integer>)this.register(new Setting("Tick Delay", (T)3, (T)0, (T)10));
        this.protate = (Setting<Boolean>)this.register(new Setting("PacketRotate", (T)true));
        this.blockChanged = new ArrayList<BlockPos>();
        this.alertDelay = new Timer();
        this.lookDown = -1;
        this.tickAC = -1;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketBlockChange && this.predict.getValue()) {
            final SPacketBlockChange packet = (SPacketBlockChange)event.getPacket();
            if (!this.blockChanged.contains(packet.getBlockPosition())) {
                for (final BlockPos pos : this.getOffsets()) {
                    if (pos.equals((Object)packet.getBlockPosition())) {
                        if (packet.getBlockState().getBlock() != Blocks.AIR) {
                            continue;
                        }
                        final int blockSlot = this.getSlot();
                        if (blockSlot == -1) {
                            return;
                        }
                        if (blockSlot != Surround.mc.player.inventory.currentItem) {
                            Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(blockSlot));
                        }
                        PlacementUtil.place(pos, EnumHand.MAIN_HAND, false, false);
                        if (blockSlot != Surround.mc.player.inventory.currentItem) {
                            Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Surround.mc.player.inventory.currentItem));
                            Surround.mc.playerController.updateController();
                        }
                        Command.sendMessage("Predict Place");
                        this.blockChanged.add(pos);
                        break;
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (Surround.mc.player == null || Surround.mc.world == null || this.lookDown == -1) {
            return;
        }
        if (this.protate.getValue()) {
            Surround.mc.playerController.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, true));
        }
        else {
            SilentRotaionUtil.lookAtAngles(0.0f, 90.0f);
        }
        --this.lookDown;
    }
    
    int getSlot() {
        int slot = InventoryUtil.findFirstBlockSlot(Blocks.OBSIDIAN.getClass(), 0, 8);
        if (slot == -1) {
            slot = InventoryUtil.findFirstBlockSlot(Blocks.ENDER_CHEST.getClass(), 0, 8);
        }
        return slot;
    }
    
    @Override
    public void onEnable() {
        this.alertDelay.reset();
        this.y = (int)Math.floor(Surround.mc.player.posY);
    }
    
    @Override
    public void onUpdate() {
        if (Surround.mc.player == null || Surround.mc.world == null) {
            return;
        }
        if (this.onlyOnStop.getValue() && (Surround.mc.player.motionX != 0.0 || Surround.mc.player.motionY != 0.0 || Surround.mc.player.motionZ != 0.0)) {
            return;
        }
        if (this.onlyOnSneak.getValue() && !Surround.mc.gameSettings.keyBindSneak.isPressed()) {
            return;
        }
        if (this.disableOnJump.getValue() && Math.abs(Math.abs(this.y) - Math.abs(Surround.mc.player.posY)) >= 0.3) {
            this.disable();
            return;
        }
        if (this.tickAC > -1) {}
        if (this.delayTimer.getPassedTimeMs() / 50L >= this.delayTicks.getValue()) {
            this.delayTimer.reset();
            int blocksPlaced = 0;
            this.hasPlaced = false;
            final List<BlockPos> offsetPattern = this.getOffsets();
            final int maxSteps = offsetPattern.size();
            boolean hasSilentSwitched = false;
            final int blockSlot = this.getSlot();
            if (blockSlot == -1) {
                return;
            }
            int offsetSteps = 0;
            if (this.centre.getValue() && !this.allowNon1x1.getValue()) {
                PlayerUtil.centerPlayer(Surround.mc.player.getPositionVector());
            }
            while (blocksPlaced <= this.blocksPerTick.getValue() && offsetSteps < maxSteps) {
                final BlockPos targetPos = offsetPattern.get(offsetSteps++);
                if (this.blockChanged.contains(targetPos)) {
                    continue;
                }
                Surround.mc.world.getEntitiesInAABBexcluding((Entity)null, new AxisAlignedBB(targetPos), (Predicate)null);
                boolean foundSomeone = false;
                for (final Entity entity : Surround.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityPlayer) {
                        foundSomeone = true;
                        if (this.alertPlayerClip.getValue() && entity != Surround.mc.player && this.alertDelay.passedMs(1000L)) {
                            Command.sendMessage("Player " + entity.getName() + " is clipping in your surround");
                            this.alertDelay.reset();
                            break;
                        }
                        break;
                    }
                    else {
                        if (!(entity instanceof EntityEnderCrystal) || !this.destroyCrystal.getValue()) {
                            continue;
                        }
                        if (this.rotate.getValue()) {
                            BlockUtil.faceVectorPacketInstant(new Vec3d((Vec3i)entity.getPosition()).add(0.5, 0.0, 0.5), true);
                        }
                        Surround.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                        Surround.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    }
                }
                if (this.destroyAboveCrystal.getValue()) {
                    for (final Entity entity : new ArrayList<Entity>(Surround.mc.world.loadedEntityList)) {
                        if (entity instanceof EntityEnderCrystal && this.sameBlockPos(entity.getPosition(), targetPos)) {
                            if (this.rotate.getValue()) {
                                BlockUtil.faceVectorPacketInstant(new Vec3d((Vec3i)entity.getPosition()).add(0.5, 0.0, 0.5), true);
                            }
                            Surround.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                            Surround.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        }
                    }
                }
                if (foundSomeone) {
                    continue;
                }
                if (!Surround.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    continue;
                }
                if (!hasSilentSwitched && blockSlot != Surround.mc.player.inventory.currentItem) {
                    Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(blockSlot));
                    hasSilentSwitched = true;
                }
                if (!PlacementUtil.place(targetPos, EnumHand.MAIN_HAND, this.rotate.getValue(), false)) {
                    continue;
                }
                if (this.centre.getValue()) {
                    PlayerUtil.centerPlayer(Surround.mc.player.getPositionVector());
                }
                this.y = (int)Math.floor(Surround.mc.player.posY);
                Command.sendMessage("Normal Place");
                ++blocksPlaced;
                if (!this.rotate.getValue() || this.afterRotate.getValue() == 0) {
                    continue;
                }
                this.lookDown = this.afterRotate.getValue();
            }
            if (hasSilentSwitched) {
                Surround.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Surround.mc.player.inventory.currentItem));
                Surround.mc.playerController.updateController();
            }
        }
        PlacementUtil.stopSneaking();
        this.blockChanged.clear();
    }
    
    boolean sameBlockPos(final BlockPos first, final BlockPos second) {
        return first != null && second != null && first.getX() == second.getX() && first.getY() == second.getY() + 2 && first.getZ() == second.getZ();
    }
    
    List<BlockPos> getOffsets() {
        final BlockPos playerPos = this.getPlayerPos();
        final ArrayList<BlockPos> offsets = new ArrayList<BlockPos>();
        if (this.allowNon1x1.getValue()) {
            final double decimalX = Math.abs(Surround.mc.player.posX) - Math.floor(Math.abs(Surround.mc.player.posX));
            final double decimalZ = Math.abs(Surround.mc.player.posZ) - Math.floor(Math.abs(Surround.mc.player.posZ));
            final int lengthXPos = this.calcLength(decimalX, false);
            final int lengthXNeg = this.calcLength(decimalX, true);
            final int lengthZPos = this.calcLength(decimalZ, false);
            final int lengthZNeg = this.calcLength(decimalZ, true);
            final ArrayList<BlockPos> tempOffsets = new ArrayList<BlockPos>();
            offsets.addAll(this.getOverlapPos());
            for (int x = 1; x < lengthXPos + 1; ++x) {
                tempOffsets.add(this.addToPlayer(playerPos, x, 0.0, 1 + lengthZPos));
                tempOffsets.add(this.addToPlayer(playerPos, x, 0.0, -(1 + lengthZNeg)));
            }
            for (int x = 0; x <= lengthXNeg; ++x) {
                tempOffsets.add(this.addToPlayer(playerPos, -x, 0.0, 1 + lengthZPos));
                tempOffsets.add(this.addToPlayer(playerPos, -x, 0.0, -(1 + lengthZNeg)));
            }
            for (int z = 1; z < lengthZPos + 1; ++z) {
                tempOffsets.add(this.addToPlayer(playerPos, 1 + lengthXPos, 0.0, z));
                tempOffsets.add(this.addToPlayer(playerPos, -(1 + lengthXNeg), 0.0, z));
            }
            for (int z = 0; z <= lengthZNeg; ++z) {
                tempOffsets.add(this.addToPlayer(playerPos, 1 + lengthXPos, 0.0, -z));
                tempOffsets.add(this.addToPlayer(playerPos, -(1 + lengthXNeg), 0.0, -z));
            }
            for (final BlockPos pos : tempOffsets) {
                if (getDown(pos)) {
                    offsets.add(pos.add(0, -1, 0));
                }
                offsets.add(pos);
            }
        }
        else {
            offsets.add(playerPos.add(0, -1, 0));
            for (final int[] surround : new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } }) {
                if (getDown(playerPos.add(surround[0], 0, surround[1]))) {
                    offsets.add(playerPos.add(surround[0], -1, surround[1]));
                }
                offsets.add(playerPos.add(surround[0], 0, surround[1]));
            }
        }
        return offsets;
    }
    
    public static boolean getDown(final BlockPos pos) {
        for (final EnumFacing e : EnumFacing.values()) {
            if (!Surround.mc.world.isAirBlock(pos.add(e.getDirectionVec()))) {
                return false;
            }
        }
        return true;
    }
    
    int calcOffset(final double dec) {
        return (dec >= 0.7) ? 1 : ((dec <= 0.3) ? -1 : 0);
    }
    
    BlockPos addToPlayer(final BlockPos playerPos, double x, double y, double z) {
        if (playerPos.getX() < 0) {
            x = -x;
        }
        if (playerPos.getY() < 0) {
            y = -y;
        }
        if (playerPos.getZ() < 0) {
            z = -z;
        }
        return playerPos.add(x, y, z);
    }
    
    List<BlockPos> getOverlapPos() {
        final ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        final double decimalX = Surround.mc.player.posX - Math.floor(Surround.mc.player.posX);
        final double decimalZ = Surround.mc.player.posZ - Math.floor(Surround.mc.player.posZ);
        final int offX = this.calcOffset(decimalX);
        final int offZ = this.calcOffset(decimalZ);
        positions.add(this.getPlayerPos());
        for (int x = 0; x <= Math.abs(offX); ++x) {
            for (int z = 0; z <= Math.abs(offZ); ++z) {
                final int properX = x * offX;
                final int properZ = z * offZ;
                positions.add(this.getPlayerPos().add(properX, -1, properZ));
            }
        }
        return positions;
    }
    
    int calcLength(final double decimal, final boolean negative) {
        if (negative) {
            return (decimal <= 0.3) ? 1 : 0;
        }
        return (decimal >= 0.7) ? 1 : 0;
    }
    
    BlockPos getPlayerPos() {
        final double decimalPoint = Surround.mc.player.posY - Math.floor(Surround.mc.player.posY);
        return new BlockPos(Surround.mc.player.posX, (decimalPoint > 0.8) ? (Math.floor(Surround.mc.player.posY) + 1.0) : Math.floor(Surround.mc.player.posY), Surround.mc.player.posZ);
    }
}
