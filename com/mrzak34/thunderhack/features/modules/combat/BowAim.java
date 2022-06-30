//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.math.*;
import javax.vecmath.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.glu.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class BowAim extends Module
{
    public static EntityLivingBase target;
    private float yawi;
    private float pitchi;
    public boolean rotate;
    public Setting<Float> range;
    public float raytrace;
    public Setting<Float> fov;
    private Setting<mode> Mode;
    public Setting<Float> scalefactor;
    public Setting<Integer> ccr;
    public Setting<Integer> ccb;
    public Setting<Integer> ccg;
    private final int black;
    private Setting<ImageESP.mode2> Mode2;
    public Setting<Boolean> wtf;
    public Setting<Float> scalefactor1;
    public Setting<Float> scalefactor2;
    private Setting<rmode> rMode;
    
    public BowAim() {
        super("BowAim", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0446\u0435\u043b\u0438\u0442\u0441\u044f-\u0432 \u043d\u0435\u0433\u0440\u043e\u0432", Category.COMBAT, true, false, false);
        this.rotate = true;
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)0.1f, (T)70.0f));
        this.raytrace = 6.0f;
        this.fov = (Setting<Float>)this.register(new Setting("FOV", (T)40.0f, (T)1.0f, (T)360.0f));
        this.Mode = (Setting<mode>)this.register(new Setting("ESP Mode", (T)mode.D2));
        this.scalefactor = (Setting<Float>)this.register(new Setting("Raytrace", (T)2.0f, (T)0.1f, (T)4.0f, "Wall Range."));
        this.ccr = (Setting<Integer>)this.register(new Setting("CustomR", (T)200, (T)0, (T)255));
        this.ccb = (Setting<Integer>)this.register(new Setting("CustomG", (T)0, (T)0, (T)255));
        this.ccg = (Setting<Integer>)this.register(new Setting("CustomB", (T)100, (T)0, (T)255));
        this.black = Color.BLACK.getRGB();
        this.Mode2 = (Setting<ImageESP.mode2>)this.register(new Setting("Color Mode", (T)ImageESP.mode2.Rainbow));
        this.wtf = (Setting<Boolean>)this.register(new Setting("CS:GO", (T)false));
        this.scalefactor1 = (Setting<Float>)this.register(new Setting("X", (T)0.0f, (T)(-30.0f), (T)30.0f, "Wall Range."));
        this.scalefactor2 = (Setting<Float>)this.register(new Setting("Y", (T)0.0f, (T)(-30.0f), (T)30.0f, "Wall Range."));
        this.rMode = (Setting<rmode>)this.register(new Setting("Rotation Mode", (T)rmode.FunnyGame));
    }
    
    @Override
    public void onDisable() {
        BowAim.target = null;
    }
    
    public void setYaw(final float yawi) {
        this.yawi = yawi;
        final Minecraft mc = Util.mc;
        Minecraft.getMinecraft();
        Util.mc.player.rotationYawHead = yawi;
        final Minecraft mc2 = Util.mc;
        Minecraft.getMinecraft();
        Util.mc.player.renderYawOffset = yawi;
        Util.mc.player.rotationYawHead = yawi;
    }
    
    public static boolean canSeeEntityAtFov(final Entity entityLiving, final float scope) {
        final double diffX = entityLiving.posX - BowAim.mc.player.posX;
        final double diffZ = entityLiving.posZ - BowAim.mc.player.posZ;
        final float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        final double difference = angleDifference(yaw, BowAim.mc.player.rotationYaw);
        return difference <= scope;
    }
    
    public static double angleDifference(final float oldYaw, final float newYaw) {
        float yaw = Math.abs(oldYaw - newYaw) % 360.0f;
        if (yaw > 180.0f) {
            yaw = 360.0f - yaw;
        }
        return yaw;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0) {
            BowAim.target = (EntityLivingBase)this.getTarget();
            if (BowAim.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && BowAim.target != null && canSeeEntityAtFov((Entity)BowAim.target, this.fov.getValue()) && this.rMode.getValue() == rmode.FunnyGame) {
                lookAtEntity((Entity)BowAim.target);
            }
        }
    }
    
    public static void lookAtEntity(final Entity entity) {
        final float[] angle = calcAngle(BowAim.mc.player.getPositionEyes(BowAim.mc.getRenderPartialTicks()), entity.getPositionEyes(BowAim.mc.getRenderPartialTicks()));
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.renderYawOffset = angle[0];
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        BowAim.mc.player.rotationYaw = yaw;
        BowAim.mc.player.rotationYawHead = yaw;
        BowAim.mc.player.rotationPitch = pitch;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    private Entity getTarget() {
        Entity target = null;
        double distance = this.range.getValue();
        for (final Entity entity : BowAim.mc.world.playerEntities) {
            if (entity instanceof EntityPlayer) {
                if (entity instanceof EntityLivingBase && EntityUtil.isntValid(entity, distance)) {
                    continue;
                }
                if (!BowAim.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && BowAim.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace)) {
                    continue;
                }
                if (!canSeeEntityAtFov(entity, this.fov.getValue())) {
                    continue;
                }
                if (target == null) {
                    target = entity;
                    distance = BowAim.mc.player.getDistanceSq(entity);
                }
                else {
                    if (BowAim.mc.player.getDistanceSq(entity) < distance) {
                        target = entity;
                        distance = BowAim.mc.player.getDistanceSq(entity);
                    }
                    if (((EntityPlayer)entity).isCreative()) {
                        return null;
                    }
                    continue;
                }
            }
        }
        return target;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        BowAim.target = (EntityLivingBase)this.getTarget();
        if (BowAim.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.Mode.getValue() == mode.D3 && BowAim.target != null && canSeeEntityAtFov((Entity)BowAim.target, this.fov.getValue())) {
            RenderHelper.drawEntityBox((Entity)BowAim.target, new Color(PaletteHelper.astolfo(false, 12).getRGB()), false, 255.0f);
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (BowAim.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.Mode.getValue() == mode.D2 && BowAim.target != null && canSeeEntityAtFov((Entity)BowAim.target, this.fov.getValue())) {
            final float partialTicks = BowAim.mc.timer.renderPartialTicks;
            final Float scaleFactor = this.scalefactor.getValue();
            final double scaling = scaleFactor / Math.pow(scaleFactor, 2.0);
            GlStateManager.scale(scaling, scaling, scaling);
            final Color c = new Color(this.ccr.getValue(), this.ccg.getValue(), this.ccb.getValue());
            int color = 0;
            if (this.Mode2.getValue() == ImageESP.mode2.Custom) {
                color = c.getRGB();
            }
            if (this.Mode2.getValue() == ImageESP.mode2.Astolfo) {
                color = PaletteHelper.astolfo(false, 1).getRGB();
            }
            if (this.Mode2.getValue() == ImageESP.mode2.Rainbow) {
                color = PaletteHelper.rainbow(300, 1.0f, 1.0f).getRGB();
            }
            final float scale = 1.0f;
            for (final Entity entity : BowAim.mc.world.loadedEntityList) {
                if (entity == BowAim.target && RenderHelper.isInViewFrustum(entity)) {
                    final EntityPlayer entityPlayer = (EntityPlayer)entity;
                    if (entityPlayer == ImageESP.mc.player) {
                        continue;
                    }
                    final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * BowAim.mc.getRenderPartialTicks();
                    final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * BowAim.mc.getRenderPartialTicks();
                    final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * BowAim.mc.getRenderPartialTicks();
                    final AxisAlignedBB axisAlignedBB2 = entity.getEntityBoundingBox();
                    final AxisAlignedBB axisAlignedBB3 = new AxisAlignedBB(axisAlignedBB2.minX - entity.posX + x - 0.05, axisAlignedBB2.minY - entity.posY + y, axisAlignedBB2.minZ - entity.posZ + z - 0.05, axisAlignedBB2.maxX - entity.posX + x + 0.05, axisAlignedBB2.maxY - entity.posY + y + 0.15, axisAlignedBB2.maxZ - entity.posZ + z + 0.05);
                    final Vector3d[] vectors = { new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.minZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.minX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.minY, axisAlignedBB3.maxZ), new Vector3d(axisAlignedBB3.maxX, axisAlignedBB3.maxY, axisAlignedBB3.maxZ) };
                    BowAim.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
                    Vector4d position = null;
                    for (Vector3d vector : vectors) {
                        vector = this.project2D(scaleFactor, vector.x - BowAim.mc.getRenderManager().renderPosX, vector.y - BowAim.mc.getRenderManager().renderPosY, vector.z - BowAim.mc.getRenderManager().renderPosZ);
                        if (vector != null && vector.z > 0.0 && vector.z < 1.0) {
                            if (position == null) {
                                position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                            }
                            position.x = Math.min(vector.x, position.x);
                            position.y = Math.min(vector.y, position.y);
                            position.z = Math.max(vector.x, position.z);
                            position.w = Math.max(vector.y, position.w);
                        }
                    }
                    if (position == null) {
                        continue;
                    }
                    BowAim.mc.entityRenderer.setupOverlayRendering();
                    final double posX = position.x;
                    final double posY = position.y;
                    final double endPosX = position.z;
                    final double endPosY = position.w;
                    if (this.wtf.getValue()) {
                        RectHelper.drawRect(posX - 1.0, posY, posX + 0.5, endPosY + 0.5, this.black);
                        RectHelper.drawRect(posX - 1.0, posY - 0.5, endPosX + 0.5, posY + 0.5 + 0.5, this.black);
                        RectHelper.drawRect(endPosX - 0.5 - 0.5, posY, endPosX + 0.5, endPosY + 0.5, this.black);
                        RectHelper.drawRect(posX - 1.0, endPosY - 0.5 - 0.5, endPosX + 0.5, endPosY + 0.5, this.black);
                        RectHelper.drawRect(posX - 0.5, posY, posX + 0.5 - 0.5, endPosY, color);
                        RectHelper.drawRect(posX, endPosY - 0.5, endPosX, endPosY, color);
                        RectHelper.drawRect(posX - 0.5, posY, endPosX, posY + 0.5, color);
                        RectHelper.drawRect(endPosX - 0.5, posY, endPosX, endPosY, color);
                    }
                    RectHelper.drawRect(posX, posY, posX, posY, color);
                    final Minecraft mc = Util.mc;
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/target.png"));
                    ModuleButton.drawCompleteImage((float)posX + this.scalefactor1.getValue(), (float)posY + this.scalefactor2.getValue(), (int)((int)endPosX - posX), (int)((int)endPosY - posY));
                }
            }
            GL11.glEnable(2929);
            BowAim.mc.entityRenderer.setupOverlayRendering();
        }
    }
    
    private Vector3d project2D(final Float scaleFactor, final double x, final double y, final double z) {
        final float xPos = (float)x;
        final float yPos = (float)y;
        final float zPos = (float)z;
        final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector)) {
            return new Vector3d((double)(vector.get(0) / scaleFactor), (double)((Display.getHeight() - vector.get(1)) / scaleFactor), (double)vector.get(2));
        }
        return null;
    }
    
    private boolean isValid(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public enum mode
    {
        D2, 
        D3;
    }
    
    public enum rmode
    {
        FunnyGame, 
        Client, 
        Wint;
    }
}
