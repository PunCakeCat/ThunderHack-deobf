//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.util.math.*;
import java.util.*;

public class CrystalUtils
{
    public static Minecraft mc;
    private static List<Block> valid;
    
    public static void breakCrystal(final Entity crystal) {
        CrystalUtils.mc.playerController.attackEntity((EntityPlayer)CrystalUtils.mc.player, crystal);
        CrystalUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    public static void breakCrystalPacket(final Entity crystal) {
        CrystalUtils.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
        CrystalUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
    public static boolean rayTraceBreak(final double x, final double y, final double z) {
        return CrystalUtils.mc.world.rayTraceBlocks(new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.posY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ), new Vec3d(x, y + 1.8, z), false, true, false) == null || CrystalUtils.mc.world.rayTraceBlocks(new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.posY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ), new Vec3d(x, y + 1.5, z), false, true, false) == null || CrystalUtils.mc.world.rayTraceBlocks(new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.posY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ), new Vec3d(x, y, z), false, true, false) == null;
    }
    
    public static boolean isVisible(final Vec3d vec3d) {
        final Vec3d eyesPos = new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.getEntityBoundingBox().minY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ);
        return CrystalUtils.mc.world.rayTraceBlocks(eyesPos, vec3d) == null;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageInput, final Explosion explosion) {
        float damage = damageInput;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = CrystalUtils.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static Vec3d getEntityPosVec(final Entity entity, final int ticks) {
        return entity.getPositionVector().add(getMotionVec(entity, ticks));
    }
    
    public static Vec3d getMotionVec(final Entity entity, final int ticks) {
        final double dX = entity.posX - entity.prevPosX;
        final double dZ = entity.posZ - entity.prevPosZ;
        double entityMotionPosX = 0.0;
        double entityMotionPosZ = 0.0;
        entityMotionPosX = dX * ticks;
        entityMotionPosZ = dZ * ticks;
        return new Vec3d(entityMotionPosX, 0.0, entityMotionPosZ);
    }
    
    public static int ping() {
        if (CrystalUtils.mc.getConnection() == null) {
            return 50;
        }
        if (CrystalUtils.mc.player == null) {
            return 50;
        }
        try {
            return CrystalUtils.mc.getConnection().getPlayerInfo(CrystalUtils.mc.player.getUniqueID()).getResponseTime();
        }
        catch (NullPointerException ex) {
            return 50;
        }
    }
    
    public static int getCrystalSlot() {
        int crystalSlot = -1;
        if (Util.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            crystalSlot = Util.mc.player.inventory.currentItem;
        }
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (Util.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        return crystalSlot;
    }
    
    public static int getSwordSlot() {
        int swordSlot = -1;
        if (Util.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
            swordSlot = Util.mc.player.inventory.currentItem;
        }
        if (swordSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (Util.mc.player.inventory.getStackInSlot(l).getItem() == Items.DIAMOND_SWORD) {
                    swordSlot = l;
                    break;
                }
            }
        }
        return swordSlot;
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            if (CrystalUtils.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && CrystalUtils.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (CrystalUtils.mc.world.getBlockState(boost).getBlock() != Blocks.AIR || CrystalUtils.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
                return false;
            }
            for (final Entity entity : CrystalUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
            for (final Entity entity : CrystalUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return true;
    }
    
    public static float getBlockDensity(final Vec3d vec, final AxisAlignedBB bb) {
        final double d0 = 1.0 / ((bb.maxX - bb.minX) * 2.0 + 1.0);
        final double d2 = 1.0 / ((bb.maxY - bb.minY) * 2.0 + 1.0);
        final double d3 = 1.0 / ((bb.maxZ - bb.minZ) * 2.0 + 1.0);
        final double d4 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
        final double d5 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        if (d0 >= 0.0 && d2 >= 0.0 && d3 >= 0.0) {
            int j2 = 0;
            int k2 = 0;
            for (float f = 0.0f; f <= 1.0f; f += (float)d0) {
                for (float f2 = 0.0f; f2 <= 1.0f; f2 += (float)d2) {
                    for (float f3 = 0.0f; f3 <= 1.0f; f3 += (float)d3) {
                        final double d6 = bb.minX + (bb.maxX - bb.minX) * f;
                        final double d7 = bb.minY + (bb.maxY - bb.minY) * f2;
                        final double d8 = bb.minZ + (bb.maxZ - bb.minZ) * f3;
                        if (rayTraceBlocks(new Vec3d(d6 + d4, d7, d8 + d5), vec) == null) {
                            ++j2;
                        }
                        ++k2;
                    }
                }
            }
            return j2 / (float)k2;
        }
        return 0.0f;
    }
    
    @Nullable
    public static RayTraceResult rayTraceBlocks(final Vec3d start, final Vec3d end) {
        return rayTraceBlocks(start, end, false, false, false);
    }
    
    @Nullable
    public static RayTraceResult rayTraceBlocks(Vec3d vec31, final Vec3d vec32, final boolean stopOnLiquid, final boolean ignoreBlockWithoutBoundingBox, final boolean returnLastUncollidableBlock) {
        if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z)) {
            return null;
        }
        if (!Double.isNaN(vec32.x) && !Double.isNaN(vec32.y) && !Double.isNaN(vec32.z)) {
            final int i = MathHelper.floor(vec32.x);
            final int j = MathHelper.floor(vec32.y);
            final int k = MathHelper.floor(vec32.z);
            int l = MathHelper.floor(vec31.x);
            int i2 = MathHelper.floor(vec31.y);
            int j2 = MathHelper.floor(vec31.z);
            BlockPos blockpos = new BlockPos(l, i2, j2);
            IBlockState iblockstate = CrystalUtils.mc.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (!CrystalUtils.valid.contains(block)) {
                block = Blocks.AIR;
                iblockstate = Blocks.AIR.getBlockState().getBaseState();
            }
            if ((!ignoreBlockWithoutBoundingBox || iblockstate.getCollisionBoundingBox((IBlockAccess)CrystalUtils.mc.world, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, stopOnLiquid)) {
                final RayTraceResult raytraceresult = iblockstate.collisionRayTrace((World)CrystalUtils.mc.world, blockpos, vec31, vec32);
                if (raytraceresult != null) {
                    return raytraceresult;
                }
            }
            RayTraceResult raytraceresult2 = null;
            int k2 = 200;
            while (k2-- >= 0) {
                if (Double.isNaN(vec31.x) || Double.isNaN(vec31.y) || Double.isNaN(vec31.z)) {
                    return null;
                }
                if (l == i && i2 == j && j2 == k) {
                    return returnLastUncollidableBlock ? raytraceresult2 : null;
                }
                boolean flag2 = true;
                boolean flag3 = true;
                boolean flag4 = true;
                double d0 = 999.0;
                double d2 = 999.0;
                double d3 = 999.0;
                if (i > l) {
                    d0 = l + 1.0;
                }
                else if (i < l) {
                    d0 = l + 0.0;
                }
                else {
                    flag2 = false;
                }
                if (j > i2) {
                    d2 = i2 + 1.0;
                }
                else if (j < i2) {
                    d2 = i2 + 0.0;
                }
                else {
                    flag3 = false;
                }
                if (k > j2) {
                    d3 = j2 + 1.0;
                }
                else if (k < j2) {
                    d3 = j2 + 0.0;
                }
                else {
                    flag4 = false;
                }
                double d4 = 999.0;
                double d5 = 999.0;
                double d6 = 999.0;
                final double d7 = vec32.x - vec31.x;
                final double d8 = vec32.y - vec31.y;
                final double d9 = vec32.z - vec31.z;
                if (flag2) {
                    d4 = (d0 - vec31.x) / d7;
                }
                if (flag3) {
                    d5 = (d2 - vec31.y) / d8;
                }
                if (flag4) {
                    d6 = (d3 - vec31.z) / d9;
                }
                if (d4 == -0.0) {
                    d4 = -1.0E-4;
                }
                if (d5 == -0.0) {
                    d5 = -1.0E-4;
                }
                if (d6 == -0.0) {
                    d6 = -1.0E-4;
                }
                EnumFacing enumfacing;
                if (d4 < d5 && d4 < d6) {
                    enumfacing = ((i > l) ? EnumFacing.WEST : EnumFacing.EAST);
                    vec31 = new Vec3d(d0, vec31.y + d8 * d4, vec31.z + d9 * d4);
                }
                else if (d5 < d6) {
                    enumfacing = ((j > i2) ? EnumFacing.DOWN : EnumFacing.UP);
                    vec31 = new Vec3d(vec31.x + d7 * d5, d2, vec31.z + d9 * d5);
                }
                else {
                    enumfacing = ((k > j2) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                    vec31 = new Vec3d(vec31.x + d7 * d6, vec31.y + d8 * d6, d3);
                }
                l = MathHelper.floor(vec31.x) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
                i2 = MathHelper.floor(vec31.y) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
                j2 = MathHelper.floor(vec31.z) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
                blockpos = new BlockPos(l, i2, j2);
                IBlockState iblockstate2 = CrystalUtils.mc.world.getBlockState(blockpos);
                Block block2 = iblockstate2.getBlock();
                if (!CrystalUtils.valid.contains(block2)) {
                    block2 = Blocks.AIR;
                    iblockstate2 = Blocks.AIR.getBlockState().getBaseState();
                }
                if (ignoreBlockWithoutBoundingBox && iblockstate2.getMaterial() != Material.PORTAL && iblockstate2.getCollisionBoundingBox((IBlockAccess)CrystalUtils.mc.world, blockpos) == Block.NULL_AABB) {
                    continue;
                }
                if (block2.canCollideCheck(iblockstate2, stopOnLiquid)) {
                    final RayTraceResult raytraceresult3 = iblockstate2.collisionRayTrace((World)CrystalUtils.mc.world, blockpos, vec31, vec32);
                    if (raytraceresult3 != null) {
                        return raytraceresult3;
                    }
                    continue;
                }
                else {
                    raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
                }
            }
            return returnLastUncollidableBlock ? raytraceresult2 : null;
        }
        return null;
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final Vec3d entityPosVec = getEntityPosVec(entity, ((int)NewAC.getInstance().predictTicks.getValue() > 0) ? ((int)NewAC.getInstance().predictTicks.getValue()) : 0);
        final double distancedsize = entityPosVec.distanceTo(new Vec3d(posX, posY, posZ)) / doubleExplosionSize;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            if (NewAC.getInstance().terrainIgnore.getValue()) {
                blockDensity = getBlockDensity(vec3d, ((int)NewAC.getInstance().predictTicks.getValue() > 0) ? entity.getEntityBoundingBox().offset(getMotionVec(entity, (int)NewAC.getInstance().predictTicks.getValue())) : entity.getEntityBoundingBox());
            }
            else {
                blockDensity = entity.world.getBlockDensity(vec3d, ((int)NewAC.getInstance().predictTicks.getValue() > 0) ? entity.getEntityBoundingBox().offset(getMotionVec(entity, (int)NewAC.getInstance().predictTicks.getValue())) : entity.getEntityBoundingBox());
            }
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)CrystalUtils.mc.world, (Entity)CrystalUtils.mc.player, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float calculateDamage(final EntityEnderCrystal crystal, final Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }
    
    public static boolean rayTracePlace(final BlockPos pos) {
        if (NewAC.getInstance().directionMode.getValue() != NewAC.DirectionMode.VANILLA) {
            final double increment = 0.45;
            final double start = 0.05;
            final double end = 0.95;
            final Vec3d eyesPos = new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.getEntityBoundingBox().minY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ);
            for (double xS = start; xS <= end; xS += increment) {
                for (double yS = start; yS <= end; yS += increment) {
                    for (double zS = start; zS <= end; zS += increment) {
                        final Vec3d posVec = new Vec3d((Vec3i)pos).add(xS, yS, zS);
                        final double distToPosVec = eyesPos.distanceTo(posVec);
                        if (!(boolean)NewAC.getInstance().strictDirection.getValue() || distToPosVec <= (float)NewAC.getInstance().placeRange.getValue()) {
                            final double diffX = posVec.x - eyesPos.x;
                            final double diffY = posVec.y - eyesPos.y;
                            final double diffZ = posVec.z - eyesPos.z;
                            final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                            final double[] tempPlaceRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))) };
                            final float yawCos = MathHelper.cos((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                            final float yawSin = MathHelper.sin((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                            final float pitchCos = -MathHelper.cos((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                            final float pitchSin = MathHelper.sin((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                            final Vec3d rotationVec = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
                            final Vec3d eyesRotationVec = eyesPos.add(rotationVec.x * distToPosVec, rotationVec.y * distToPosVec, rotationVec.z * distToPosVec);
                            final RayTraceResult rayTraceResult = CrystalUtils.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, false, false);
                            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        for (final EnumFacing facing : EnumFacing.values()) {
            final Vec3d cVector = new Vec3d(pos.getX() + 0.5 + facing.getDirectionVec().getX() * 0.5, pos.getY() + 0.5 + facing.getDirectionVec().getY() * 0.5, pos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 0.5);
            if (!(boolean)NewAC.getInstance().strictDirection.getValue() || CrystalUtils.mc.player.getPositionVector().add(0.0, (double)CrystalUtils.mc.player.getEyeHeight(), 0.0).distanceTo(cVector) <= (float)NewAC.getInstance().placeRange.getValue()) {
                final RayTraceResult rayTraceResult2 = CrystalUtils.mc.world.rayTraceBlocks(new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.posY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ), cVector, false, true, false);
                if (rayTraceResult2 != null && rayTraceResult2.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK) && rayTraceResult2.getBlockPos().equals((Object)pos)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        CrystalUtils.mc = Minecraft.getMinecraft();
        CrystalUtils.valid = Arrays.asList(Blocks.OBSIDIAN, Blocks.BEDROCK, Blocks.ENDER_CHEST, Blocks.ANVIL);
    }
}
