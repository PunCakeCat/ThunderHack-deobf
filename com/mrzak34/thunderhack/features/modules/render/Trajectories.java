//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import org.lwjgl.util.glu.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Trajectories extends Module
{
    public Setting<Boolean> landed;
    private final Setting<ColorSetting> ncolor;
    
    public Trajectories() {
        super("Trajectories", "Draws trajectories.", Module.Category.RENDER, true, false, false);
        this.landed = (Setting<Boolean>)this.register(new Setting("Landed", (T)true));
        this.ncolor = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
    }
    
    protected boolean isThrowable(final Item item) {
        return item instanceof ItemEnderPearl || item instanceof ItemExpBottle || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion;
    }
    
    protected float getDistance(final Item item) {
        return (item instanceof ItemBow) ? 1.0f : 0.4f;
    }
    
    protected float getThrowVelocity(final Item item) {
        if (item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion) {
            return 0.5f;
        }
        if (item instanceof ItemExpBottle) {
            return 0.59f;
        }
        return 1.5f;
    }
    
    protected int getThrowPitch(final Item item) {
        if (item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemExpBottle) {
            return 20;
        }
        return 0;
    }
    
    protected float getGravity(final Item item) {
        if (item instanceof ItemBow || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemExpBottle) {
            return 0.05f;
        }
        return 0.03f;
    }
    
    protected List<Entity> getEntitiesWithinAABB(final AxisAlignedBB bb) {
        final ArrayList<Entity> list = new ArrayList<Entity>();
        final int chunkMinX = MathHelper.floor((bb.minX - 2.0) / 16.0);
        final int chunkMaxX = MathHelper.floor((bb.maxX + 2.0) / 16.0);
        final int chunkMinZ = MathHelper.floor((bb.minZ - 2.0) / 16.0);
        final int chunkMaxZ = MathHelper.floor((bb.maxZ + 2.0) / 16.0);
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (Trajectories.mc.world.getChunkProvider().getLoadedChunk(x, z) != null) {
                    Trajectories.mc.world.getChunk(x, z).getEntitiesWithinAABBForEntity((Entity)Trajectories.mc.player, bb, (List)list, EntitySelectors.NOT_SPECTATING);
                }
            }
        }
        return list;
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (Trajectories.mc.player == null || Trajectories.mc.world == null || Trajectories.mc.gameSettings.thirdPersonView != 0) {
            return;
        }
        if ((Trajectories.mc.player.getHeldItemMainhand() == ItemStack.EMPTY || !(Trajectories.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow)) && (Trajectories.mc.player.getHeldItemMainhand() == ItemStack.EMPTY || !this.isThrowable(Trajectories.mc.player.getHeldItemMainhand().getItem())) && (Trajectories.mc.player.getHeldItemOffhand() == ItemStack.EMPTY || !this.isThrowable(Trajectories.mc.player.getHeldItemOffhand().getItem()))) {
            return;
        }
        final double renderPosX = getRenderPosX();
        final double renderPosY = getRenderPosY();
        final double renderPosZ = getRenderPosZ();
        Item item = null;
        if (Trajectories.mc.player.getHeldItemMainhand() != ItemStack.EMPTY && (Trajectories.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow || this.isThrowable(Trajectories.mc.player.getHeldItemMainhand().getItem()))) {
            item = Trajectories.mc.player.getHeldItemMainhand().getItem();
        }
        else if (Trajectories.mc.player.getHeldItemOffhand() != ItemStack.EMPTY && this.isThrowable(Trajectories.mc.player.getHeldItemOffhand().getItem())) {
            item = Trajectories.mc.player.getHeldItemOffhand().getItem();
        }
        if (item == null) {
            return;
        }
        startRender();
        double posX = renderPosX - MathHelper.cos(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        double posY = renderPosY + Trajectories.mc.player.getEyeHeight() - 0.1000000014901161;
        double posZ = renderPosZ - MathHelper.sin(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        final float maxDist = this.getDistance(item);
        double motionX = -MathHelper.sin(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(Trajectories.mc.player.rotationPitch / 180.0f * 3.1415927f) * maxDist;
        double motionY = -MathHelper.sin((Trajectories.mc.player.rotationPitch - this.getThrowPitch(item)) / 180.0f * 3.141593f) * maxDist;
        double motionZ = MathHelper.cos(Trajectories.mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(Trajectories.mc.player.rotationPitch / 180.0f * 3.1415927f) * maxDist;
        final int var6 = 72000 - Trajectories.mc.player.getItemInUseCount();
        float power = var6 / 20.0f;
        power = (power * power + power * 2.0f) / 3.0f;
        if (power > 1.0f) {
            power = 1.0f;
        }
        final float distance = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;
        final float pow = ((item instanceof ItemBow) ? (power * 2.0f) : 1.0f) * this.getThrowVelocity(item);
        motionX *= pow;
        motionY *= pow;
        motionZ *= pow;
        if (!Trajectories.mc.player.onGround) {
            motionY += Trajectories.mc.player.motionY;
        }
        GlStateManager.color(this.ncolor.getValue().getRed() / 255.0f, this.ncolor.getValue().getGreen() / 255.0f, this.ncolor.getValue().getBlue() / 255.0f, this.ncolor.getValue().getAlpha() / 255.0f);
        GL11.glEnable(2848);
        final float size = (float)((item instanceof ItemBow) ? 0.3 : 0.25);
        boolean hasLanded = false;
        Entity landingOnEntity = null;
        RayTraceResult landingPosition = null;
        GL11.glBegin(3);
        while (!hasLanded && posY > 0.0) {
            final Vec3d present = new Vec3d(posX, posY, posZ);
            final Vec3d future = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
            final RayTraceResult possibleLandingStrip = Trajectories.mc.world.rayTraceBlocks(present, future, false, true, false);
            if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != RayTraceResult.Type.MISS) {
                landingPosition = possibleLandingStrip;
                hasLanded = true;
            }
            final AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
            final List<Entity> entities = this.getEntitiesWithinAABB(arrowBox.offset(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            for (final Object entity : entities) {
                final Entity boundingBox = (Entity)entity;
                if (boundingBox.canBeCollidedWith() && boundingBox != Trajectories.mc.player) {
                    final float var7 = 0.3f;
                    final AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().expand((double)var7, (double)var7, (double)var7);
                    final RayTraceResult possibleEntityLanding = var8.calculateIntercept(present, future);
                    if (possibleEntityLanding == null) {
                        continue;
                    }
                    hasLanded = true;
                    landingOnEntity = boundingBox;
                    landingPosition = possibleEntityLanding;
                }
            }
            if (landingOnEntity != null) {
                GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            }
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            final float motionAdjustment = 0.99f;
            motionX *= 0.9900000095367432;
            motionY *= 0.9900000095367432;
            motionZ *= 0.9900000095367432;
            motionY -= this.getGravity(item);
            this.drawLine3D(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
        }
        GL11.glEnd();
        if (this.landed.getValue() && landingPosition != null && landingPosition.typeOfHit == RayTraceResult.Type.BLOCK) {
            GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
            final int side = landingPosition.sideHit.getIndex();
            if (side == 2) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (side == 3) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (side == 4) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            }
            else if (side == 5) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            }
            final Cylinder c = new Cylinder();
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            c.setDrawStyle(100013);
            if (landingOnEntity != null) {
                GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glLineWidth(2.5f);
                c.draw(0.5f, 0.15f, 0.0f, 8, 1);
                GL11.glLineWidth(0.1f);
                GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            }
            c.draw(0.5f, 0.15f, 0.0f, 8, 1);
        }
        endRender();
    }
    
    public static double getRenderPosX() {
        return ((IRenderManager)Trajectories.mc.getRenderManager()).getRenderPosX();
    }
    
    public static double getRenderPosY() {
        return ((IRenderManager)Trajectories.mc.getRenderManager()).getRenderPosY();
    }
    
    public static void startRender() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void endRender() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static double getRenderPosZ() {
        return ((IRenderManager)Trajectories.mc.getRenderManager()).getRenderPosZ();
    }
    
    public void drawLine3D(final double var1, final double var2, final double var3) {
        GL11.glVertex3d(var1, var2, var3);
    }
}
