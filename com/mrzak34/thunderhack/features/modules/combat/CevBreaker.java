//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;

public class CevBreaker extends Module
{
    public Setting<Boolean> miscSection;
    public Setting<Boolean> delaySection;
    public Setting<Boolean> rotationSection;
    public Setting<Boolean> breakPlaceSection;
    public Setting<Boolean> targetSection;
    public Setting<Boolean> switchSword;
    public Setting<Boolean> antiWeakness;
    public Setting<Boolean> rotate;
    public Setting<Boolean> forceBreaker;
    public Setting<Boolean> forceRotation;
    public Setting<Boolean> confirmPlace;
    public Setting<Boolean> confirmBreak;
    public Setting<Boolean> placeCrystal;
    public Setting<Boolean> antiStep;
    public Setting<Boolean> trapPlayer;
    public Setting<Boolean> fastBreak;
    public Setting<Boolean> fastPlace;
    public Setting<Integer> preRotationDelay;
    public Setting<Integer> afterRotationDelay;
    public Setting<Integer> supDelay;
    public Setting<Integer> crystalDelay;
    public Setting<Integer> blocksPerTick;
    public Setting<Integer> hitDelay;
    public Setting<Integer> midHitDelay;
    public Setting<Integer> endDelay;
    public Setting<Integer> pickSwitchTick;
    public Setting<Float> enemyRange;
    private Setting<targetmode> target;
    private Setting<breakCrystalmode> breakCrystal;
    private Setting<breakBlockmode> breakBlock;
    public static int cur_item;
    public static boolean isActive;
    public static boolean forceBrk;
    private boolean noMaterials;
    private boolean hasMoved;
    private boolean isSneaking;
    private boolean isHole;
    private boolean enoughSpace;
    private boolean broken;
    private boolean stoppedCa;
    private boolean deadPl;
    private boolean rotationPlayerMoved;
    private boolean prevBreak;
    private boolean preRotationBol;
    private int oldSlot;
    private int stage;
    private int delayTimeTicks;
    private int hitTryTick;
    private int tickPick;
    private int afterRotationTick;
    private int preRotationTick;
    private final int[][] model;
    public static boolean isPossible;
    private int[] slot_mat;
    private int[] delayTable;
    private int[] enemyCoordsInt;
    private double[] enemyCoordsDouble;
    private structureTemp toPlace;
    Double[][] sur_block;
    private EntityPlayer aimTarget;
    Vec3d lastHitVec;
    
    public CevBreaker() {
        super("CevBreaker", "\u0413\u0430\u0437\u043e\u0432\u0430\u044f \u043a\u0430\u043c\u0435\u0440\u0430", Category.COMBAT, true, true, false);
        this.miscSection = (Setting<Boolean>)this.register(new Setting("Misc Section", (T)true));
        this.delaySection = (Setting<Boolean>)this.register(new Setting("Delay Section", (T)true));
        this.rotationSection = (Setting<Boolean>)this.register(new Setting("Rotation Section", (T)true));
        this.breakPlaceSection = (Setting<Boolean>)this.register(new Setting("Break Place Section", (T)true));
        this.targetSection = (Setting<Boolean>)this.register(new Setting("targetSection", (T)true));
        this.switchSword = (Setting<Boolean>)this.register(new Setting("Switch Sword", (T)false, v -> this.miscSection.getValue()));
        this.antiWeakness = (Setting<Boolean>)this.register(new Setting("Anti Weakness", (T)false, v -> this.miscSection.getValue()));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false, v -> this.rotationSection.getValue()));
        this.forceBreaker = (Setting<Boolean>)this.register(new Setting("Force Breaker", (T)false, v -> this.rotationSection.getValue()));
        this.forceRotation = (Setting<Boolean>)this.register(new Setting("Force Rotation", (T)false, v -> this.rotationSection.getValue()));
        this.confirmPlace = (Setting<Boolean>)this.register(new Setting("No Glitch Place", (T)true, v -> this.breakPlaceSection.getValue()));
        this.confirmBreak = (Setting<Boolean>)this.register(new Setting("No Glitch Break", (T)true, v -> this.breakPlaceSection.getValue()));
        this.placeCrystal = (Setting<Boolean>)this.register(new Setting("Place Crystal", (T)true, v -> this.breakPlaceSection.getValue()));
        this.antiStep = (Setting<Boolean>)this.register(new Setting("Trap Player", (T)true, v -> this.breakPlaceSection.getValue()));
        this.trapPlayer = (Setting<Boolean>)this.register(new Setting("Trap Player", (T)true, v -> this.breakPlaceSection.getValue()));
        this.fastBreak = (Setting<Boolean>)this.register(new Setting("Fast Plac", (T)true, v -> this.breakPlaceSection.getValue()));
        this.fastPlace = (Setting<Boolean>)this.register(new Setting("Fast Plac", (T)false, v -> this.breakPlaceSection.getValue()));
        this.preRotationDelay = (Setting<Integer>)this.register(new Setting("Pre Rotation Delay", (T)0, (T)0, (T)20, v -> this.rotationSection.getValue()));
        this.afterRotationDelay = (Setting<Integer>)this.register(new Setting("After Rotation Delay", (T)0, (T)0, (T)20, v -> this.rotationSection.getValue()));
        this.supDelay = (Setting<Integer>)this.register(new Setting("Support Delay", (T)1, (T)0, (T)4, v -> this.delaySection.getValue()));
        this.crystalDelay = (Setting<Integer>)this.register(new Setting("Crystal Delay", (T)2, (T)0, (T)20, v -> this.delaySection.getValue()));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("Blocks Per Tick", (T)4, (T)2, (T)6, v -> this.delaySection.getValue()));
        this.hitDelay = (Setting<Integer>)this.register(new Setting("Hit Delay", (T)2, (T)0, (T)20, v -> this.delaySection.getValue()));
        this.midHitDelay = (Setting<Integer>)this.register(new Setting("Mid Hit Delay", (T)1, (T)0, (T)20, v -> this.delaySection.getValue()));
        this.endDelay = (Setting<Integer>)this.register(new Setting("End Delay", (T)1, (T)0, (T)20, v -> this.delaySection.getValue()));
        this.pickSwitchTick = (Setting<Integer>)this.register(new Setting("Pick Switch Tick", (T)100, (T)0, (T)500, v -> this.miscSection.getValue()));
        this.enemyRange = (Setting<Float>)this.register(new Setting("Range", (T)4.9f, (T)0.0f, (T)6.0f, v -> this.targetSection.getValue()));
        this.target = (Setting<targetmode>)this.register(new Setting("Target", (T)targetmode.Nearest, v -> this.targetSection.getValue()));
        this.breakCrystal = (Setting<breakCrystalmode>)this.register(new Setting("Break Crystal", (T)breakCrystalmode.Packet, v -> this.breakPlaceSection.getValue()));
        this.breakBlock = (Setting<breakBlockmode>)this.register(new Setting("Break Block", (T)breakBlockmode.Packet, v -> this.breakPlaceSection.getValue()));
        this.noMaterials = false;
        this.hasMoved = false;
        this.isSneaking = false;
        this.isHole = true;
        this.enoughSpace = true;
        this.oldSlot = -1;
        this.model = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 0, 1, 1 }, { 0, 1, -1 } };
        this.sur_block = new Double[4][3];
    }
    
    @SubscribeEvent
    public void onDestroyBlock(final DestroyBlockEvent event) {
        if (this.enemyCoordsInt != null && event.getBlockPos().x + ((event.getBlockPos().x < 0) ? 1 : 0) == this.enemyCoordsInt[0] && event.getBlockPos().z + ((event.getBlockPos().z < 0) ? 1 : 0) == this.enemyCoordsInt[2]) {
            this.destroyCrystalAlgo();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && (int)packet.getX() == this.enemyCoordsInt[0] && (int)packet.getZ() == this.enemyCoordsInt[2]) {
                this.stage = 1;
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final UpdatePlayerMoveStateEvent event) {
        if (event.getStage() != 0 || !this.rotate.getValue() || this.lastHitVec == null || !this.forceRotation.getValue()) {
            return;
        }
        final Vec2f rotation = RotationUtil.getRotationTo(this.lastHitVec);
        SilentRotaionUtil.lookAtVector(Vec3d.fromPitchYaw(rotation));
    }
    
    @Override
    public void onEnable() {
        this.initValues();
        if (this.getAimTarget()) {
            return;
        }
        this.playerChecks();
    }
    
    private boolean getAimTarget() {
        if (this.target.getValue() == targetmode.Nearest) {
            this.aimTarget = PlayerUtil.findClosestTarget(this.enemyRange.getValue(), this.aimTarget);
        }
        else {
            this.aimTarget = PlayerUtil.findLookingPlayer(this.enemyRange.getValue());
        }
        if (this.aimTarget == null || this.target.getValue() != targetmode.Looking) {
            if (this.target.getValue() != targetmode.Looking && this.aimTarget == null) {
                this.disable();
            }
            return this.aimTarget == null;
        }
        return false;
    }
    
    private void playerChecks() {
        if (this.getMaterialsSlot()) {
            if (this.is_in_hole()) {
                this.enemyCoordsDouble = new double[] { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ };
                this.enemyCoordsInt = new int[] { (int)this.enemyCoordsDouble[0], (int)this.enemyCoordsDouble[1], (int)this.enemyCoordsDouble[2] };
                this.enoughSpace = this.createStructure();
            }
            else {
                this.isHole = false;
            }
        }
        else {
            this.noMaterials = true;
        }
    }
    
    private void initValues() {
        this.preRotationBol = false;
        final int n = 0;
        this.preRotationTick = n;
        this.afterRotationTick = n;
        CevBreaker.isPossible = false;
        this.aimTarget = null;
        this.lastHitVec = null;
        this.delayTable = new int[] { this.supDelay.getValue(), this.crystalDelay.getValue(), this.hitDelay.getValue(), this.endDelay.getValue() };
        this.toPlace = new structureTemp(0.0, 0, new ArrayList<Vec3d>());
        this.isHole = (CevBreaker.isActive = true);
        final boolean b = false;
        this.broken = b;
        this.deadPl = b;
        this.rotationPlayerMoved = b;
        this.hasMoved = b;
        this.slot_mat = new int[] { -1, -1, -1, -1 };
        final int n2 = 0;
        this.delayTimeTicks = n2;
        this.stage = n2;
        if (CevBreaker.mc.player == null) {
            this.disable();
            return;
        }
        this.oldSlot = CevBreaker.mc.player.inventory.currentItem;
        this.stoppedCa = false;
        CevBreaker.cur_item = -1;
        CevBreaker.forceBrk = this.forceBreaker.getValue();
    }
    
    @Override
    public void onDisable() {
        if (CevBreaker.mc.player == null) {
            return;
        }
        String output = "";
        String materialsNeeded = "";
        if (this.aimTarget == null) {
            output = "No target found...";
        }
        else if (this.noMaterials) {
            output = "No Materials Detected...";
            materialsNeeded = this.getMissingMaterials();
        }
        else if (!this.isHole) {
            output = "The enemy is not in a hole...";
        }
        else if (!this.enoughSpace) {
            output = "Not enough space...";
        }
        else if (this.hasMoved) {
            output = "Out of range...";
        }
        else if (this.deadPl) {
            output = "Enemy is dead, gg! ";
        }
        Command.sendMessage(output + "CevBreaker turned OFF!");
        if (!materialsNeeded.equals("")) {
            Command.sendMessage("Materials missing:" + materialsNeeded);
        }
        if (this.isSneaking) {
            CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)CevBreaker.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        if (this.oldSlot != CevBreaker.mc.player.inventory.currentItem && this.oldSlot != -1) {
            CevBreaker.mc.player.inventory.currentItem = this.oldSlot;
            this.oldSlot = -1;
        }
        this.noMaterials = (CevBreaker.isPossible = (CevBreaker.isActive = (CevBreaker.forceBrk = false)));
    }
    
    private String getMissingMaterials() {
        final StringBuilder output = new StringBuilder();
        if (this.slot_mat[0] == -1) {
            output.append(" Obsidian");
        }
        if (this.slot_mat[1] == -1) {
            output.append(" Crystal");
        }
        if ((this.antiWeakness.getValue() || this.switchSword.getValue()) && this.slot_mat[3] == -1) {
            output.append(" Sword");
        }
        if (this.slot_mat[2] == -1) {
            output.append(" Pick");
        }
        return output.toString();
    }
    
    @Override
    public void onUpdate() {
        if (CevBreaker.mc.player == null || CevBreaker.mc.player.isDead) {
            this.disable();
            return;
        }
        if (this.delayTimeTicks < this.delayTable[this.stage]) {
            ++this.delayTimeTicks;
            return;
        }
        this.delayTimeTicks = 0;
        if (this.enemyCoordsDouble == null || this.aimTarget == null) {
            if (this.aimTarget == null) {
                this.aimTarget = PlayerUtil.findLookingPlayer(this.enemyRange.getValue());
                if (this.aimTarget != null) {
                    this.playerChecks();
                }
            }
            else {
                this.checkVariable();
            }
            return;
        }
        if (this.aimTarget.isDead) {
            this.deadPl = true;
        }
        if ((int)this.aimTarget.posX != (int)this.enemyCoordsDouble[0] || (int)this.aimTarget.posZ != (int)this.enemyCoordsDouble[2]) {
            this.hasMoved = true;
        }
        if (this.checkVariable()) {
            return;
        }
        if (this.placeSupport()) {
            switch (this.stage) {
                case 1: {
                    if (this.getCrystal() != null) {
                        this.stage = 3;
                        return;
                    }
                    if (this.afterRotationDelay.getValue() != 0 && this.afterRotationTick != this.afterRotationDelay.getValue()) {
                        ++this.afterRotationTick;
                        return;
                    }
                    if (this.preRotationDelay.getValue() != 0 && !this.preRotationBol) {
                        this.placeBlockThings(this.stage, true, false);
                        if (this.preRotationTick != this.preRotationDelay.getValue()) {
                            ++this.preRotationTick;
                            break;
                        }
                        this.preRotationBol = true;
                        this.preRotationTick = 0;
                    }
                    this.placeBlockThings(this.stage, false, false);
                    if (this.fastPlace.getValue()) {
                        this.placeCrystal(false);
                    }
                    this.prevBreak = false;
                    this.tickPick = 0;
                    break;
                }
                case 2: {
                    if (this.afterRotationDelay.getValue() != 0 && this.afterRotationTick != this.afterRotationDelay.getValue()) {
                        ++this.afterRotationTick;
                        return;
                    }
                    if (this.preRotationDelay.getValue() != 0 && !this.preRotationBol) {
                        this.placeCrystal(true);
                        if (this.preRotationTick != this.preRotationDelay.getValue()) {
                            ++this.preRotationTick;
                            break;
                        }
                        this.preRotationBol = true;
                        this.preRotationTick = 0;
                    }
                    if (this.confirmPlace.getValue() && !(BlockUtil.getBlock(this.compactBlockPos(0)) instanceof BlockObsidian)) {
                        --this.stage;
                        return;
                    }
                    this.placeCrystal(false);
                    break;
                }
                case 3: {
                    if (this.confirmPlace.getValue() && this.getCrystal() == null) {
                        this.stage = 1;
                        return;
                    }
                    int switchValue = 3;
                    if (!this.switchSword.getValue() || this.tickPick == this.pickSwitchTick.getValue() || this.tickPick++ == 0) {
                        switchValue = 2;
                    }
                    this.switchPick(switchValue);
                    final BlockPos obbyBreak = new BlockPos(this.enemyCoordsDouble[0], (double)(this.enemyCoordsInt[1] + 2), this.enemyCoordsDouble[2]);
                    if (BlockUtil.getBlock(obbyBreak) instanceof BlockObsidian) {
                        final EnumFacing sideBreak = BlockUtil.getPlaceableSide(obbyBreak);
                        if (sideBreak != null) {
                            switch (this.breakBlock.getValue()) {
                                case Packet: {
                                    if (!this.prevBreak) {
                                        CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                                        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, obbyBreak, sideBreak));
                                        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, obbyBreak, sideBreak));
                                        this.prevBreak = true;
                                        break;
                                    }
                                    break;
                                }
                                case Normal: {
                                    CevBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                                    CevBreaker.mc.playerController.onPlayerDamageBlock(obbyBreak, sideBreak);
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    this.destroyCrystalAlgo();
                    break;
                }
            }
        }
    }
    
    private void switchPick(final int switchValue) {
        if (CevBreaker.cur_item != this.slot_mat[switchValue]) {
            if (this.slot_mat[switchValue] == -1) {
                this.noMaterials = true;
                return;
            }
            CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(CevBreaker.cur_item = this.slot_mat[switchValue]));
            CevBreaker.mc.player.inventory.currentItem = CevBreaker.cur_item;
        }
    }
    
    private void placeCrystal(final boolean onlyRotate) {
        this.placeBlockThings(this.stage, onlyRotate, true);
        if (this.fastBreak.getValue() && !onlyRotate) {
            this.fastBreakFun();
        }
    }
    
    private void fastBreakFun() {
        this.switchPick(2);
        CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(this.enemyCoordsInt[0], this.enemyCoordsInt[1] + 2, this.enemyCoordsInt[2]), EnumFacing.UP));
        CevBreaker.isPossible = true;
    }
    
    private Entity getCrystal() {
        for (final Entity t : CevBreaker.mc.world.loadedEntityList) {
            if (t instanceof EntityEnderCrystal && (int)t.posX == this.enemyCoordsInt[0] && (int)t.posZ == this.enemyCoordsInt[2] && t.posY - this.enemyCoordsInt[1] == 3.0) {
                return t;
            }
        }
        return null;
    }
    
    public void destroyCrystalAlgo() {
        CevBreaker.isPossible = false;
        final Entity crystal = this.getCrystal();
        if (this.confirmBreak.getValue() && this.broken && crystal == null) {
            this.stage = 1;
            this.broken = false;
        }
        if (crystal != null) {
            this.breakCrystalPiston(crystal);
            if (this.confirmBreak.getValue()) {
                this.broken = true;
            }
            else {
                this.stage = 1;
            }
        }
        else {
            this.stage = 1;
        }
    }
    
    private void breakCrystalPiston(final Entity crystal) {
        if (this.hitTryTick++ < this.midHitDelay.getValue()) {
            return;
        }
        this.hitTryTick = 0;
        if (this.antiWeakness.getValue() && CevBreaker.mc.player.isPotionActive(MobEffects.WEAKNESS) && CevBreaker.mc.player.getActivePotionEffects().stream().noneMatch(e -> e.getEffectName().contains("damageBoost") && e.getAmplifier() > 0)) {
            CevBreaker.mc.player.inventory.currentItem = this.slot_mat[3];
        }
        final Vec3d vecCrystal = crystal.getPositionVector().add(0.5, 0.5, 0.5);
        if (this.breakCrystal.getValue() != breakCrystalmode.None && this.rotate.getValue()) {
            SilentRotaionUtil.lookAtXYZ((float)vecCrystal.x, (float)vecCrystal.y, (float)vecCrystal.z);
            if (this.forceRotation.getValue()) {
                this.lastHitVec = vecCrystal;
            }
        }
        try {
            switch (this.breakCrystal.getValue()) {
                case Vanilla: {
                    CrystalUtils.breakCrystal(crystal);
                    break;
                }
                case Packet: {
                    CrystalUtils.breakCrystalPacket(crystal);
                    break;
                }
            }
        }
        catch (NullPointerException ex) {}
        if (this.rotate.getValue()) {}
    }
    
    private boolean placeSupport() {
        int checksDone = 0;
        int blockDone = 0;
        if (this.toPlace.supportBlock > 0) {
            do {
                final BlockPos targetPos = this.getTargetPos(checksDone);
                if (BlockUtil.getBlock(targetPos) instanceof BlockAir) {
                    if (this.preRotationDelay.getValue() != 0 && !this.preRotationBol) {
                        if (this.preRotationTick == 0) {
                            this.placeBlock(targetPos, 0, true);
                        }
                        if (this.preRotationTick != this.preRotationDelay.getValue()) {
                            ++this.preRotationTick;
                            return false;
                        }
                        this.preRotationBol = true;
                        this.preRotationTick = 0;
                    }
                    if (!this.placeBlock(targetPos, 0, false)) {
                        continue;
                    }
                    this.preRotationBol = false;
                    if (++blockDone == this.blocksPerTick.getValue()) {
                        return false;
                    }
                    continue;
                }
            } while (++checksDone != this.toPlace.supportBlock);
        }
        this.stage = ((this.stage == 0) ? 1 : this.stage);
        return true;
    }
    
    private boolean changeItem(final int step) {
        if (this.slot_mat[step] == 11 || CevBreaker.mc.player.inventory.getStackInSlot(this.slot_mat[step]) != ItemStack.EMPTY) {
            if (CevBreaker.cur_item != this.slot_mat[step]) {
                if (this.slot_mat[step] == -1) {
                    return this.noMaterials = true;
                }
                if (this.slot_mat[step] != 11) {
                    CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(CevBreaker.cur_item = this.slot_mat[step]));
                    CevBreaker.mc.player.inventory.currentItem = CevBreaker.cur_item;
                }
            }
            return false;
        }
        return this.noMaterials = true;
    }
    
    private boolean placeBlock(final BlockPos pos, final int step, final boolean onlyRotate) {
        if (this.changeItem(step)) {
            return false;
        }
        if (!onlyRotate) {
            EnumHand handSwing = EnumHand.MAIN_HAND;
            if (this.slot_mat[step] == 11) {
                handSwing = EnumHand.OFF_HAND;
            }
            PlacementUtil.place(pos, handSwing, this.rotate.getValue() && !this.forceRotation.getValue(), false);
            return true;
        }
        final EnumFacing side = BlockUtil.getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockUtil.canBeClicked(neighbour)) {
            return false;
        }
        final double add = (step == 1 && (int)CevBreaker.mc.player.posY == this.enemyCoordsInt[1]) ? -0.5 : 0.0;
        this.lastHitVec = new Vec3d((Vec3i)neighbour).add(0.5, 0.5 + add, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        return false;
    }
    
    public void placeBlockThings(int step, final boolean onlyRotate, final boolean isCrystal) {
        if (step != 1 || this.placeCrystal.getValue()) {
            --step;
            final BlockPos targetPos = this.compactBlockPos(step);
            if (!isCrystal) {
                this.placeBlock(targetPos, step, onlyRotate);
            }
            else {
                if (this.changeItem(step)) {
                    return;
                }
                EnumHand handSwing = EnumHand.MAIN_HAND;
                if (this.slot_mat[step] == 11) {
                    handSwing = EnumHand.OFF_HAND;
                }
                CevBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(targetPos.add(0.5, 0.5, 0.5), EnumFacing.getDirectionFromEntityLiving(targetPos, (EntityLivingBase)CevBreaker.mc.player), handSwing, 0.0f, 0.0f, 0.0f));
                CevBreaker.mc.player.swingArm(handSwing);
            }
        }
        if (!onlyRotate) {
            ++this.stage;
            this.afterRotationTick = 0;
            this.preRotationBol = false;
        }
    }
    
    public BlockPos compactBlockPos(final int step) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(this.toPlace.supportBlock + step));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    private BlockPos getTargetPos(final int idx) {
        final BlockPos offsetPos = new BlockPos((Vec3d)this.toPlace.to_place.get(idx));
        return new BlockPos(this.enemyCoordsDouble[0] + offsetPos.getX(), this.enemyCoordsDouble[1] + offsetPos.getY(), this.enemyCoordsDouble[2] + offsetPos.getZ());
    }
    
    private boolean checkVariable() {
        if (this.noMaterials || !this.isHole || !this.enoughSpace || this.hasMoved || this.deadPl || this.rotationPlayerMoved) {
            this.disable();
            return true;
        }
        return false;
    }
    
    public static Block getBlock(final double x, final double y, final double z) {
        return CevBreaker.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    private boolean createStructure() {
        if (Objects.requireNonNull(getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 2.0, this.enemyCoordsDouble[2]).getRegistryName()).toString().toLowerCase().contains("bedrock") || !(getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 3.0, this.enemyCoordsDouble[2]) instanceof BlockAir) || !(getBlock(this.enemyCoordsDouble[0], this.enemyCoordsDouble[1] + 4.0, this.enemyCoordsDouble[2]) instanceof BlockAir)) {
            return false;
        }
        double max_found = Double.MIN_VALUE;
        int cor = 0;
        int i = 0;
        for (final Double[] cord_b : this.sur_block) {
            final double distance_now;
            if ((distance_now = CevBreaker.mc.player.getDistanceSq(new BlockPos((double)cord_b[0], (double)cord_b[1], (double)cord_b[2]))) > max_found) {
                max_found = distance_now;
                cor = i;
            }
            ++i;
        }
        this.toPlace.to_place.add(new Vec3d((double)this.model[cor][0], 1.0, (double)this.model[cor][2]));
        this.toPlace.to_place.add(new Vec3d((double)this.model[cor][0], 2.0, (double)this.model[cor][2]));
        this.toPlace.supportBlock = 2;
        if (this.trapPlayer.getValue() || this.antiStep.getValue()) {
            for (int high = 1; high < 3; ++high) {
                if (high != 2 || this.antiStep.getValue()) {
                    for (final int[] modelBas : this.model) {
                        final Vec3d toAdd = new Vec3d((double)modelBas[0], (double)high, (double)modelBas[2]);
                        if (!this.toPlace.to_place.contains(toAdd)) {
                            this.toPlace.to_place.add(toAdd);
                            final structureTemp toPlace = this.toPlace;
                            ++toPlace.supportBlock;
                        }
                    }
                }
            }
        }
        this.toPlace.to_place.add(new Vec3d(0.0, 2.0, 0.0));
        this.toPlace.to_place.add(new Vec3d(0.0, 2.0, 0.0));
        return true;
    }
    
    private boolean getMaterialsSlot() {
        if (CevBreaker.mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            this.slot_mat[1] = 11;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = CevBreaker.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (this.slot_mat[1] == -1 && stack.getItem() instanceof ItemEndCrystal) {
                    this.slot_mat[1] = i;
                }
                else if ((this.antiWeakness.getValue() || this.switchSword.getValue()) && stack.getItem() instanceof ItemSword) {
                    this.slot_mat[3] = i;
                }
                else if (stack.getItem() instanceof ItemPickaxe) {
                    this.slot_mat[2] = i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block instanceof BlockObsidian) {
                        this.slot_mat[0] = i;
                    }
                }
            }
        }
        int count = 0;
        for (final int val : this.slot_mat) {
            if (val != -1) {
                ++count;
            }
        }
        return count >= 3 + ((this.antiWeakness.getValue() || this.switchSword.getValue()) ? 1 : 0);
    }
    
    private boolean is_in_hole() {
        this.sur_block = new Double[][] { { this.aimTarget.posX + 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX - 1.0, this.aimTarget.posY, this.aimTarget.posZ }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ + 1.0 }, { this.aimTarget.posX, this.aimTarget.posY, this.aimTarget.posZ - 1.0 } };
        return true;
    }
    
    static {
        CevBreaker.cur_item = -1;
        CevBreaker.isActive = false;
        CevBreaker.forceBrk = false;
        CevBreaker.isPossible = false;
    }
    
    public enum targetmode
    {
        Nearest, 
        Looking;
    }
    
    public enum breakCrystalmode
    {
        Vanilla, 
        Packet, 
        None;
    }
    
    public enum breakBlockmode
    {
        Normal, 
        Packet;
    }
    
    private static class structureTemp
    {
        public double distance;
        public int supportBlock;
        public ArrayList<Vec3d> to_place;
        public int direction;
        
        public structureTemp(final double distance, final int supportBlock, final ArrayList<Vec3d> to_place) {
            this.distance = distance;
            this.supportBlock = supportBlock;
            this.to_place = to_place;
            this.direction = -1;
        }
    }
}
