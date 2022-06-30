//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class DamageUtil2 implements Util
{
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece == null) {
                return true;
            }
            if (getItemDamage(piece) >= durability) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static boolean isNaked(final EntityPlayer player) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece != null) {
                if (piece.isEmpty()) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static boolean canBreakWeakness(final EntityPlayer player) {
        int strengthAmp = 0;
        final PotionEffect effect = DamageUtil.mc.player.getActivePotionEffect(MobEffects.STRENGTH);
        if (effect != null) {
            strengthAmp = effect.getAmplifier();
        }
        return !DamageUtil.mc.player.isPotionActive(MobEffects.WEAKNESS) || strengthAmp >= 1 || DamageUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || DamageUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe || DamageUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe || DamageUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemSpade;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)DamageUtil.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity, final boolean ignoreTerrain) {
        float finalDamage = 1.0f;
        try {
            final float doubleExplosionSize = 12.0f;
            final double distancedSize = entity.getDistance(posX, posY, posZ) / doubleExplosionSize;
            final double blockDensity = ignoreTerrain ? ignoreTerrainDecntiy(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox(), (World)DamageUtil2.mc.world) : ((double)entity.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox()));
            final double v = (1.0 - distancedSize) * blockDensity;
            final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * doubleExplosionSize + 1.0);
            if (entity instanceof EntityLivingBase) {
                finalDamage = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)DamageUtil2.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
            }
        }
        catch (NullPointerException ex) {}
        return finalDamage;
    }
    
    public static float ignoreTerrainDecntiy(final Vec3d vec, final AxisAlignedBB bb, final World world) {
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
                        final RayTraceResult result;
                        if ((result = world.rayTraceBlocks(new Vec3d(d6 + d4, d7, d8 + d5), vec)) == null) {
                            ++j2;
                        }
                        else {
                            final Block blockHit = BlockUtil.getBlock(result.getBlockPos());
                            if (blockHit.blockResistance < 600.0f) {
                                ++j2;
                            }
                        }
                        ++k2;
                    }
                }
            }
            return j2 / (float)k2;
        }
        return 0.0f;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
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
        final int diff = DamageUtil.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final Entity crystal, final Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
    }
    
    public static boolean canTakeDamage(final boolean suicide) {
        return !DamageUtil.mc.player.capabilities.isCreativeMode && !suicide;
    }
    
    public static int getCooldownByWeapon(final EntityPlayer player) {
        final Item item = player.getHeldItemMainhand().getItem();
        if (item instanceof ItemSword) {
            return 600;
        }
        if (item instanceof ItemPickaxe) {
            return 850;
        }
        if (item == Items.IRON_AXE) {
            return 1100;
        }
        if (item == Items.STONE_HOE) {
            return 500;
        }
        if (item == Items.IRON_HOE) {
            return 350;
        }
        if (item == Items.WOODEN_AXE || item == Items.STONE_AXE) {
            return 1250;
        }
        if (item instanceof ItemSpade || item == Items.GOLDEN_AXE || item == Items.DIAMOND_AXE || item == Items.WOODEN_HOE || item == Items.GOLDEN_HOE) {
            return 1000;
        }
        return 250;
    }
}
