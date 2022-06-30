//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Interactions extends Module
{
    public Setting<Parent> breaking;
    private Setting<BreakRenderMode> bRenderMode;
    private Setting<Float> bRange;
    private Setting<Boolean> bOutline;
    private Setting<Boolean> bWireframe;
    private Setting<Float> bWidth;
    private Setting<ColorSetting> bOutlineColor;
    private Setting<ColorSetting> bCrossOutlineColor;
    private Setting<Boolean> naame;
    private Setting<Boolean> bFill;
    private Setting<ColorSetting> bFillColor;
    private Setting<ColorSetting> bCrossFillColor;
    private Setting<Boolean> bTracer;
    private Setting<ColorSetting> bTracerColor;
    public Setting<Parent> placing;
    private Setting<Boolean> pOutline;
    private Setting<Boolean> pWireframe;
    private Setting<Float> pWidth;
    private Setting<ColorSetting> pOutlineColor;
    private Setting<Boolean> pFill;
    private Setting<ColorSetting> pFillColor;
    
    public Interactions() {
        super("BreakHighLight", "\u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u043b\u043e\u043c\u0430\u043d\u0438\u044f-\u0431\u043b\u043e\u043a\u043e\u0432", Module.Category.RENDER, true, false, false);
        this.breaking = (Setting<Parent>)this.register(new Setting("Breaking", (T)new Parent(false)));
        this.bRenderMode = (Setting<BreakRenderMode>)this.register((Setting)new Setting<BreakRenderMode>("BRenderMove", BreakRenderMode.GROW).withParent(this.breaking));
        this.bRange = (Setting<Float>)this.register((Setting)new Setting<Float>("BRange", 15.0f, 5.0f, 255.0f).withParent(this.breaking));
        this.bOutline = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("BOutline", true).withParent(this.breaking));
        this.bWireframe = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("BWireframe", false).withParent(this.breaking));
        this.bWidth = (Setting<Float>)this.register((Setting)new Setting<Float>("BWidth", 1.5f, 1.0f, 10.0f).withParent(this.breaking));
        this.bOutlineColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("BOutlineColor", new ColorSetting(-65536)).withParent(this.breaking));
        this.bCrossOutlineColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("BCrossOutlineColor", new ColorSetting(-65536)).withParent(this.breaking));
        this.naame = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("Name", true).withParent(this.breaking));
        this.bFill = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("BFill", true).withParent(this.breaking));
        this.bFillColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("BFillColor", new ColorSetting(1727987712)).withParent(this.breaking));
        this.bCrossFillColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("BCrossFillColor", new ColorSetting(1727987712)).withParent(this.breaking));
        this.bTracer = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("BTracer", false).withParent(this.breaking));
        this.bTracerColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("BTracerColor", new ColorSetting(-65536)).withParent(this.breaking));
        this.placing = (Setting<Parent>)this.register(new Setting("Placing", (T)new Parent(false)));
        this.pOutline = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("POutline", true).withParent(this.placing));
        this.pWireframe = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("PWireframe", false).withParent(this.placing));
        this.pWidth = (Setting<Float>)this.register((Setting)new Setting<Float>("PWidth", 1.5f, 1.0f, 10.0f).withParent(this.placing));
        this.pOutlineColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("POutlineColor", new ColorSetting(-16776961)).withParent(this.placing));
        this.pFill = (Setting<Boolean>)this.register((Setting)new Setting<Boolean>("PFill", true).withParent(this.placing));
        this.pFillColor = (Setting<ColorSetting>)this.register((Setting)new Setting<ColorSetting>("PFillColor", new ColorSetting(1711276287)).withParent(this.placing));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (Interactions.mc.player == null || Interactions.mc.world == null) {
            return;
        }
        if (Interactions.mc.playerController.getIsHittingBlock()) {
            final float progress = ((IPlayerControllerMP)Interactions.mc.playerController).getCurBlockDamageMP();
            final BlockPos pos = ((IPlayerControllerMP)Interactions.mc.playerController).getCurrentBlock();
            final AxisAlignedBB bb = Interactions.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)Interactions.mc.world, pos).offset(pos);
            switch (this.bRenderMode.getValue()) {
                case GROW: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case SHRINK: {
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case CROSS: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bCrossFillColor.getValue(), this.bCrossOutlineColor.getValue());
                    break;
                }
                default: {
                    this.renderBreakingBB(bb, this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
            }
            if (this.bTracer.getValue()) {
                final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Interactions.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Interactions.mc.player.rotationYaw));
                this.renderTracer(eyes.x, eyes.y + Interactions.mc.player.getEyeHeight(), eyes.z, pos.getX() - ((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosX() + 0.5, pos.getY() - ((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosY() + 0.5, pos.getZ() - ((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosZ() + 0.5, this.bTracerColor.getValue().getColor());
            }
        }
        final Entity object;
        BlockPos pos2;
        String name;
        ((IRenderGlobal)Interactions.mc.renderGlobal).getDamagedBlocks().forEach((integer, destroyBlockProgress) -> {
            this.renderGlobalBreakage(destroyBlockProgress);
            object = Interactions.mc.world.getEntityByID((int)integer);
            if (object != null && this.naame.getValue() && !object.getName().equals(Interactions.mc.player.getName())) {
                GlStateManager.pushMatrix();
                pos2 = destroyBlockProgress.getPosition();
                try {
                    NewAC.glBillboardDistanceScaled(pos2.getX() + 0.5f, pos2.getY() + 0.5f, pos2.getZ() + 0.5f, (EntityPlayer)Interactions.mc.player, 1.0f);
                }
                catch (Exception ex) {}
                name = object.getName();
                FontRender.drawString3(name, (int)(-(FontRender.getStringWidth(name) / 2.0)), -4, -1);
                GlStateManager.popMatrix();
            }
        });
    }
    
    private void renderGlobalBreakage(final DestroyBlockProgress destroyBlockProgress) {
        if (destroyBlockProgress != null) {
            final BlockPos pos = destroyBlockProgress.getPosition();
            if (Interactions.mc.playerController.getIsHittingBlock() && ((IPlayerControllerMP)Interactions.mc.playerController).getCurrentBlock().equals((Object)pos)) {
                return;
            }
            if (Interactions.mc.player.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > this.bRange.getValue()) {
                return;
            }
            final float progress = Math.min(1.0f, destroyBlockProgress.getPartialBlockDamage() / 8.0f);
            final AxisAlignedBB bb = Interactions.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)Interactions.mc.world, pos).offset(pos);
            switch (this.bRenderMode.getValue()) {
                case GROW: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case SHRINK: {
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
                case CROSS: {
                    this.renderBreakingBB(bb.shrink(0.5 - progress * 0.5), this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    this.renderBreakingBB(bb.shrink(progress * 0.5), this.bCrossFillColor.getValue(), this.bCrossOutlineColor.getValue());
                    break;
                }
                default: {
                    this.renderBreakingBB(bb, this.bFillColor.getValue(), this.bOutlineColor.getValue());
                    break;
                }
            }
            if (this.bTracer.getValue()) {
                final Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Interactions.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Interactions.mc.player.rotationYaw));
                this.renderTracer(eyes.x, eyes.y + Interactions.mc.player.getEyeHeight(), eyes.z, pos.getX() - ((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosX() + 0.5, pos.getY() - ((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosY() + 0.5, pos.getZ() - ((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosZ() + 0.5, this.bTracerColor.getValue().getColor());
            }
        }
    }
    
    public void renderPlacingBB(final AxisAlignedBB bb) {
        if (this.pFill.getValue()) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBox(bb, this.pFillColor.getValue().getColorObject());
            BlockRenderUtil.releaseGL();
        }
        if (this.pOutline.getValue()) {
            BlockRenderUtil.prepareGL();
            if (this.pWireframe.getValue()) {
                BlockRenderUtil.drawWireframe(bb.offset(-((IRenderManager)Module.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)Module.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)Module.mc.getRenderManager()).getRenderPosZ()), this.pOutlineColor.getValue().getColor(), this.pWidth.getValue());
            }
            else {
                TessellatorUtil.drawBoundingBox(bb, this.pWidth.getValue(), this.pOutlineColor.getValue().getColorObject());
            }
            BlockRenderUtil.releaseGL();
        }
    }
    
    private void renderBreakingBB(final AxisAlignedBB bb, final ColorSetting fill, final ColorSetting outline) {
        if (this.bFill.getValue()) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBox(bb, fill.getColorObject());
            BlockRenderUtil.releaseGL();
        }
        if (this.bOutline.getValue()) {
            BlockRenderUtil.prepareGL();
            if (this.bWireframe.getValue()) {
                BlockRenderUtil.drawWireframe(bb.offset(-((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)Interactions.mc.getRenderManager()).getRenderPosZ()), outline.getColor(), this.bWidth.getValue());
            }
            else {
                TessellatorUtil.drawBoundingBox(bb, this.bWidth.getValue(), outline.getColorObject());
            }
            BlockRenderUtil.releaseGL();
        }
    }
    
    private void renderTracer(final double x, final double y, final double z, final double x2, final double y2, final double z2, final int color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f, (color >> 24 & 0xFF) / 255.0f);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ((IEntityRenderer)Interactions.mc.entityRenderer).orientCam(Interactions.mc.getRenderPartialTicks());
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GlStateManager.enableLighting();
    }
    
    private enum BreakRenderMode
    {
        GROW, 
        SHRINK, 
        CROSS, 
        STATIC;
    }
}
