//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class FreecamCamera extends EntityPlayerSP
{
    private final Minecraft mc;
    private boolean copyInventory;
    private boolean follow;
    private float hSpeed;
    private float vSpeed;
    
    public FreecamCamera(final World world) {
        super(Minecraft.getMinecraft(), world, Minecraft.getMinecraft().getConnection(), Minecraft.getMinecraft().player.getStatFileWriter(), Minecraft.getMinecraft().player.getRecipeBook());
        this.mc = Minecraft.getMinecraft();
    }
    
    public FreecamCamera(final boolean copyInventory, final boolean follow, final float hSpeed, final float vSpeed) {
        super(Minecraft.getMinecraft(), (World)Minecraft.getMinecraft().world, Minecraft.getMinecraft().getConnection(), Minecraft.getMinecraft().player.getStatFileWriter(), Minecraft.getMinecraft().player.getRecipeBook());
        this.mc = Minecraft.getMinecraft();
        this.copyInventory = copyInventory;
        this.follow = follow;
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
        this.noClip = true;
        this.setHealth(this.mc.player.getHealth());
        this.posX = this.mc.player.posX;
        this.posY = this.mc.player.posY;
        this.posZ = this.mc.player.posZ;
        this.prevPosX = this.mc.player.prevPosX;
        this.prevPosY = this.mc.player.prevPosY;
        this.prevPosZ = this.mc.player.prevPosZ;
        this.lastTickPosX = this.mc.player.lastTickPosX;
        this.lastTickPosY = this.mc.player.lastTickPosY;
        this.lastTickPosZ = this.mc.player.lastTickPosZ;
        this.rotationYaw = this.mc.player.rotationYaw;
        this.rotationPitch = this.mc.player.rotationPitch;
        this.rotationYawHead = this.mc.player.rotationYawHead;
        this.prevRotationYaw = this.mc.player.prevRotationYaw;
        this.prevRotationPitch = this.mc.player.prevRotationPitch;
        this.prevRotationYawHead = this.mc.player.prevRotationYawHead;
        if (this.copyInventory) {
            this.inventory = this.mc.player.inventory;
            this.inventoryContainer = this.mc.player.inventoryContainer;
            this.setHeldItem(EnumHand.MAIN_HAND, this.mc.player.getHeldItemMainhand());
            this.setHeldItem(EnumHand.OFF_HAND, this.mc.player.getHeldItemOffhand());
        }
        final NBTTagCompound compound = new NBTTagCompound();
        this.mc.player.capabilities.writeCapabilitiesToNBT(compound);
        this.capabilities.readCapabilitiesFromNBT(compound);
        this.capabilities.isFlying = true;
        this.attackedAtYaw = this.mc.player.attackedAtYaw;
        this.movementInput = (MovementInput)new MovementInputFromOptions(this.mc.gameSettings);
    }
    
    public void writeEntityToNBT(final NBTTagCompound compound) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound compound) {
    }
    
    public boolean isInsideOfMaterial(final Material material) {
        return this.mc.player.isInsideOfMaterial(material);
    }
    
    public Map<Potion, PotionEffect> getActivePotionMap() {
        return (Map<Potion, PotionEffect>)this.mc.player.getActivePotionMap();
    }
    
    public Collection<PotionEffect> getActivePotionEffects() {
        return (Collection<PotionEffect>)this.mc.player.getActivePotionEffects();
    }
    
    public int getTotalArmorValue() {
        return this.mc.player.getTotalArmorValue();
    }
    
    public float getAbsorptionAmount() {
        return this.mc.player.getAbsorptionAmount();
    }
    
    public boolean isPotionActive(final Potion potion) {
        return this.mc.player.isPotionActive(potion);
    }
    
    public PotionEffect getActivePotionEffect(final Potion potion) {
        return this.mc.player.getActivePotionEffect(potion);
    }
    
    public FoodStats getFoodStats() {
        return this.mc.player.getFoodStats();
    }
    
    public boolean canTriggerWalking() {
        return false;
    }
    
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return null;
    }
    
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }
    
    public AxisAlignedBB getEntityBoundingBox() {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    public void applyEntityCollision(final Entity entity) {
    }
    
    public boolean attackEntityFrom(final DamageSource source, final float amount) {
        return false;
    }
    
    public boolean canBeAttackedWithItem() {
        return false;
    }
    
    public boolean canBeCollidedWith() {
        return false;
    }
    
    public boolean canBeRidden(final Entity entity) {
        return false;
    }
    
    public boolean canRenderOnFire() {
        return false;
    }
    
    public boolean canTrample(final World world, final Block block, final BlockPos pos, final float fallDistance) {
        return false;
    }
    
    public void doBlockCollisions() {
    }
    
    public void updateFallState(final double y, final boolean onGroundIn, final IBlockState state, final BlockPos pos) {
    }
    
    public boolean getIsInvulnerable() {
        return true;
    }
    
    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.IGNORE;
    }
    
    public boolean hasNoGravity() {
        return true;
    }
    
    public void onLivingUpdate() {
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.movementInput.updatePlayerMoveState();
        final float up = this.movementInput.jump ? 1.0f : (this.movementInput.sneak ? -1.0f : 0.0f);
        this.setMotion(this.movementInput.moveStrafe, up, this.movementInput.moveForward);
        if (this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.motionX *= 2.0;
            this.motionY *= 2.0;
            this.motionZ *= 2.0;
            this.setSprinting(true);
        }
        else {
            this.setSprinting(false);
        }
        if (this.follow) {
            if (Math.abs(this.motionX) <= 9.99999993922529E-9) {
                this.posX += this.mc.player.posX - this.mc.player.prevPosX;
            }
            if (Math.abs(this.motionY) <= 9.99999993922529E-9) {
                this.motionY += this.mc.player.posY - this.mc.player.prevPosY;
            }
            if (Math.abs(this.motionZ) <= 9.99999993922529E-9) {
                this.motionZ += this.mc.player.posZ - this.mc.player.prevPosZ;
            }
        }
        this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    }
    
    public void setMotion(float strafe, float up, float forward) {
        float f = strafe * strafe + up * up + forward * forward;
        if (f >= 1.0E-4f) {
            f = MathHelper.sqrt(f);
            if (f < 1.0f) {
                f = 1.0f;
            }
            f /= 2.0f;
            strafe *= f;
            up *= f;
            forward *= f;
            final float f2 = MathHelper.sin(this.rotationYaw * 0.017453292f);
            final float f3 = MathHelper.cos(this.rotationYaw * 0.017453292f);
            this.motionX = (strafe * f3 - forward * f2) * this.hSpeed;
            this.motionY = up * (double)this.vSpeed;
            this.motionZ = (forward * f3 + strafe * f2) * this.hSpeed;
        }
    }
    
    public boolean isCopyInventory() {
        return this.copyInventory;
    }
    
    public void setCopyInventory(final boolean copyInventory) {
        this.copyInventory = copyInventory;
    }
    
    public boolean isFollow() {
        return this.follow;
    }
    
    public void setFollow(final boolean follow) {
        this.follow = follow;
    }
    
    public float gethSpeed() {
        return this.hSpeed;
    }
    
    public void sethSpeed(final float hSpeed) {
        this.hSpeed = hSpeed;
    }
    
    public float getvSpeed() {
        return this.vSpeed;
    }
    
    public void setvSpeed(final float vSpeed) {
        this.vSpeed = vSpeed;
    }
}
