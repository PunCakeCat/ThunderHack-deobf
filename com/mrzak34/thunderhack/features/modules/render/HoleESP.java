//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import java.awt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class HoleESP extends Module
{
    private final Setting<Integer> rangeXZ;
    private final Setting<Integer> rangeY;
    private final Setting<Float> width;
    private final Setting<Float> height;
    private final Setting<Mode> mode;
    private final Setting<Integer> fadeAlpha;
    private final Setting<Boolean> depth;
    private final Setting<Boolean> noLineDepth;
    private final Setting<Lines> lines;
    private final Setting<Boolean> sides;
    private final Setting<Boolean> notSelf;
    private final Setting<Boolean> twoBlock;
    private final Setting<Boolean> bedrock;
    private final Setting<Boolean> obsidian;
    private final Setting<Boolean> vunerable;
    private final Setting<Boolean> selfVunerable;
    private List<BlockPos> obiHoles;
    private List<BlockPos> bedrockHoles;
    private List<TwoBlockHole> obiHolesTwoBlock;
    private List<TwoBlockHole> bedrockHolesTwoBlock;
    private final Setting<ColorSetting> bRockHoleColor;
    private final Setting<ColorSetting> bRockLineColor;
    private final Setting<ColorSetting> obiHoleColor;
    private final Setting<ColorSetting> obiLineHoleColor;
    private final Setting<ColorSetting> vunerableColor;
    private final Setting<ColorSetting> vunerableLineColor;
    
    public HoleESP() {
        super("HoleESP", "Surrounds you with Obsidian", Module.Category.RENDER, true, false, false);
        this.rangeXZ = (Setting<Integer>)this.register(new Setting("RangeXZ", (T)8, (T)1, (T)25));
        this.rangeY = (Setting<Integer>)this.register(new Setting("RangeY", (T)5, (T)1, (T)25));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)1.5f, (T)0.0f, (T)10.0f));
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)1.0f, (T)(-2.0f), (T)8.0f));
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FULL));
        this.fadeAlpha = (Setting<Integer>)this.register(new Setting("FadeAlpha", (T)0, (T)0, (T)255, v -> this.mode.getValue() == Mode.FADE));
        this.depth = (Setting<Boolean>)this.register(new Setting("Depth", (T)true, v -> this.mode.getValue() == Mode.FADE));
        this.noLineDepth = (Setting<Boolean>)this.register(new Setting("NotLines", (T)true, v -> this.mode.getValue() == Mode.FADE && this.depth.getValue()));
        this.lines = (Setting<Lines>)this.register(new Setting("Lines", (T)Lines.BOTTOM, v -> this.mode.getValue() == Mode.FADE));
        this.sides = (Setting<Boolean>)this.register(new Setting("Sides", (T)false, v -> this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.FADE));
        this.notSelf = (Setting<Boolean>)this.register(new Setting("NotSelf", (T)true, v -> this.mode.getValue() == Mode.FADE));
        this.twoBlock = (Setting<Boolean>)this.register(new Setting("TwoBlock", (T)false));
        this.bedrock = (Setting<Boolean>)this.register(new Setting("Bedrock", (T)true));
        this.obsidian = (Setting<Boolean>)this.register(new Setting("Obsidian", (T)true));
        this.vunerable = (Setting<Boolean>)this.register(new Setting("Vulnerable", (T)false));
        this.selfVunerable = (Setting<Boolean>)this.register(new Setting("Self", (T)false));
        this.obiHoles = new ArrayList<BlockPos>();
        this.bedrockHoles = new ArrayList<BlockPos>();
        this.obiHolesTwoBlock = new ArrayList<TwoBlockHole>();
        this.bedrockHolesTwoBlock = new ArrayList<TwoBlockHole>();
        this.bRockHoleColor = (Setting<ColorSetting>)this.register(new Setting("bRockHoleColor", (T)new ColorSetting(-2013200640)));
        this.bRockLineColor = (Setting<ColorSetting>)this.register(new Setting("bRockLineColor", (T)new ColorSetting(-1996554240)));
        this.obiHoleColor = (Setting<ColorSetting>)this.register(new Setting("obiHoleColor", (T)new ColorSetting(-2013200640)));
        this.obiLineHoleColor = (Setting<ColorSetting>)this.register(new Setting("obiLineHoleColor", (T)new ColorSetting(-65536)));
        this.vunerableColor = (Setting<ColorSetting>)this.register(new Setting("vunerableColor", (T)new ColorSetting(1727987967)));
        this.vunerableLineColor = (Setting<ColorSetting>)this.register(new Setting("vunerableLineColor", (T)new ColorSetting(-65281)));
    }
    
    public void onUpdate() {
        if (HoleESP.mc.world == null || HoleESP.mc.player == null) {
            return;
        }
        this.obiHoles.clear();
        this.bedrockHoles.clear();
        this.obiHolesTwoBlock.clear();
        this.bedrockHolesTwoBlock.clear();
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(HoleESP.mc.player.getPosition().add(-this.rangeXZ.getValue(), -this.rangeY.getValue(), -this.rangeXZ.getValue()), HoleESP.mc.player.getPosition().add((int)this.rangeXZ.getValue(), (int)this.rangeY.getValue(), (int)this.rangeXZ.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleESP.mc.world.getBlockState(pos).getMaterial().blocksMovement() || !HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement() || !HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial().blocksMovement()) {
                if (BlockUtils.validObi(pos) && this.obsidian.getValue()) {
                    this.obiHoles.add(pos);
                }
                else {
                    final BlockPos validTwoBlock = BlockUtils.validTwoBlockObiXZ(pos);
                    if (validTwoBlock != null && this.obsidian.getValue() && this.twoBlock.getValue()) {
                        this.obiHolesTwoBlock.add(new TwoBlockHole(pos, pos.add(validTwoBlock.getX(), validTwoBlock.getY(), validTwoBlock.getZ())));
                    }
                }
                if (BlockUtils.validBedrock(pos) && this.bedrock.getValue()) {
                    this.bedrockHoles.add(pos);
                }
                else {
                    final BlockPos validTwoBlock = BlockUtils.validTwoBlockBedrockXZ(pos);
                    if (validTwoBlock == null || !this.bedrock.getValue() || !this.twoBlock.getValue()) {
                        continue;
                    }
                    this.bedrockHolesTwoBlock.add(new TwoBlockHole(pos, pos.add(validTwoBlock.getX(), validTwoBlock.getY(), validTwoBlock.getZ())));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (HoleESP.mc.world == null || HoleESP.mc.player == null) {
            return;
        }
        if (this.mode.getValue() == Mode.BOTTOM) {
            GlStateManager.pushMatrix();
            RenderUtil.beginRender();
            GlStateManager.enableBlend();
            GlStateManager.glLineWidth(5.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            for (final BlockPos pos : this.bedrockHoles) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)pos.getY(), (double)(pos.getZ() + 1));
                drawBoundingBox(box, this.bRockHoleColor.getValue().getRawColor());
            }
            for (final BlockPos pos : this.obiHoles) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)pos.getY(), (double)(pos.getZ() + 1));
                drawBoundingBox(box, this.obiHoleColor.getValue().getRawColor());
            }
            for (final TwoBlockHole pos2 : this.bedrockHolesTwoBlock) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos2.getOne().getX(), (double)pos2.getOne().getY(), (double)pos2.getOne().getZ(), (double)(pos2.getExtra().getX() + 1), (double)pos2.getExtra().getY(), (double)(pos2.getExtra().getZ() + 1));
                drawBoundingBox(box, this.bRockHoleColor.getValue().getRawColor());
            }
            for (final TwoBlockHole pos2 : this.obiHolesTwoBlock) {
                final AxisAlignedBB box = new AxisAlignedBB((double)pos2.getOne().getX(), (double)pos2.getOne().getY(), (double)pos2.getOne().getZ(), (double)(pos2.getExtra().getX() + 1), (double)pos2.getExtra().getY(), (double)(pos2.getExtra().getZ() + 1));
                drawBoundingBox(box, this.obiHoleColor.getValue().getRawColor());
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            RenderUtil.endRender();
            GlStateManager.popMatrix();
        }
        else {
            for (final BlockPos pos : this.bedrockHoles) {
                this.drawHole(pos, this.bRockHoleColor.getValue().getColorObject(), this.bRockHoleColor.getValue().getColorObject());
            }
            for (final BlockPos pos : this.obiHoles) {
                this.drawHole(pos, this.obiHoleColor.getValue().getColorObject(), this.obiLineHoleColor.getValue().getColorObject());
            }
            for (final TwoBlockHole pos2 : this.bedrockHolesTwoBlock) {
                this.drawHoleTwoBlock(pos2.getOne(), pos2.getExtra(), this.bRockHoleColor.getValue().getColorObject(), this.bRockLineColor.getValue().getColorObject());
            }
            for (final TwoBlockHole pos2 : this.obiHolesTwoBlock) {
                this.drawHoleTwoBlock(pos2.getOne(), pos2.getExtra(), this.obiHoleColor.getValue().getColorObject(), this.obiLineHoleColor.getValue().getColorObject());
            }
        }
        if (this.vunerable.getValue()) {
            final List<Entity> targetsInRange = (List<Entity>)HoleESP.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).filter(e -> e.getDistance((Entity)HoleESP.mc.player) < this.rangeXZ.getValue()).filter(e -> e != HoleESP.mc.player || this.selfVunerable.getValue()).filter(e -> !Thunderhack.friendManager.isFriend(e.getName())).sorted(Comparator.comparing(e -> HoleESP.mc.player.getDistance(e))).collect(Collectors.toList());
            for (final Entity target : targetsInRange) {
                final ArrayList<BlockPos> vuns = getVulnerablePositions(new BlockPos(target));
                for (final BlockPos pos3 : vuns) {
                    final AxisAlignedBB axisAlignedBB = HoleESP.mc.world.getBlockState(pos3).getBoundingBox((IBlockAccess)HoleESP.mc.world, pos3).offset(pos3);
                    BlockRenderUtil.prepareGL();
                    TessellatorUtil.drawBox(axisAlignedBB, true, 1.0, this.vunerableColor.getValue().getColorObject(), this.vunerableColor.getValue().getAlpha(), 63);
                    TessellatorUtil.drawBoundingBox(axisAlignedBB, this.width.getValue(), this.vunerableLineColor.getValue().getColorObject());
                    BlockRenderUtil.releaseGL();
                }
            }
        }
    }
    
    public static ArrayList<BlockPos> getVulnerablePositions(final BlockPos root) {
        final ArrayList<BlockPos> vP = new ArrayList<BlockPos>();
        if (!(HoleESP.mc.world.getBlockState(root).getBlock() instanceof BlockAir)) {
            return vP;
        }
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            if (HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() instanceof BlockAir) {
                return new ArrayList<BlockPos>();
            }
            if (HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() instanceof BlockObsidian) {
                if (CrystalUtils.canPlaceCrystal(root.offset(facing, 2).down()) && HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() != Blocks.AIR) {
                    vP.add(root.offset(facing));
                }
                else if (CrystalUtils.canPlaceCrystal(root.offset(facing)) && HoleESP.mc.world.getBlockState(root.offset(facing)).getBlock() != Blocks.AIR && (HoleESP.mc.world.getBlockState(root.offset(facing).down()).getBlock() == Blocks.BEDROCK || HoleESP.mc.world.getBlockState(root.offset(facing).down()).getBlock() == Blocks.OBSIDIAN)) {
                    vP.add(root.offset(facing));
                }
            }
        }
        return vP;
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final Color color) {
        final AxisAlignedBB boundingBox = bb.offset(-HoleESP.mc.getRenderManager().viewerPosX, -HoleESP.mc.getRenderManager().viewerPosY, -HoleESP.mc.getRenderManager().viewerPosZ);
        drawBoxOutline(boundingBox.grow(0.0020000000949949026), (float)(color.getRed() * 255), (float)(color.getGreen() * 255), (float)(color.getBlue() * 255), (float)(color.getAlpha() * 255));
    }
    
    public static void drawBoxOutline(final AxisAlignedBB box, final float red, final float green, final float blue, final float alpha) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.minX, box.maxY, box.maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.minX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.maxZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.maxZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.maxY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(box.maxX, box.minY, box.minZ).color(red, green, blue, 0.0f).endVertex();
        tessellator.draw();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final int color) {
        drawBoundingBox(bb, new Color(color));
    }
    
    public void drawHole(final BlockPos pos, final Color color, final Color lineColor) {
        AxisAlignedBB axisAlignedBB = HoleESP.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)HoleESP.mc.world, pos).offset(pos);
        axisAlignedBB = axisAlignedBB.setMaxY(axisAlignedBB.minY + this.height.getValue());
        if (this.mode.getValue() == Mode.FULL) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBox(axisAlignedBB, true, 1.0, color, color.getAlpha(), ((boolean)this.sides.getValue()) ? 60 : 63);
            BlockRenderUtil.releaseGL();
        }
        if (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.OUTLINE) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBoundingBox(axisAlignedBB, this.width.getValue(), lineColor);
            BlockRenderUtil.releaseGL();
        }
        if (this.mode.getValue() == Mode.WIREFRAME) {
            BlockRenderUtil.prepareGL();
            BlockRenderUtil.drawWireframe(axisAlignedBB.offset(-((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosZ()), lineColor.getRGB(), this.width.getValue());
            BlockRenderUtil.releaseGL();
        }
        if (this.mode.getValue() == Mode.FADE) {
            AxisAlignedBB tBB = HoleESP.mc.world.getBlockState(pos).getBoundingBox((IBlockAccess)HoleESP.mc.world, pos).offset(pos);
            tBB = tBB.setMaxY(tBB.minY + this.height.getValue());
            if (HoleESP.mc.player.getEntityBoundingBox() != null && tBB.intersects(HoleESP.mc.player.getEntityBoundingBox()) && this.notSelf.getValue()) {
                tBB = tBB.setMaxY(Math.min(tBB.maxY, HoleESP.mc.player.posY + 1.0));
            }
            BlockRenderUtil.prepareGL();
            if (this.depth.getValue()) {
                GlStateManager.enableDepth();
                tBB = tBB.shrink(0.01);
            }
            TessellatorUtil.drawBox(tBB, true, this.height.getValue(), color, this.fadeAlpha.getValue(), ((boolean)this.sides.getValue()) ? 60 : 63);
            if (this.width.getValue() >= 0.1f) {
                if (this.lines.getValue() == Lines.BOTTOM) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.minY, tBB.minZ, tBB.maxX, tBB.minY, tBB.maxZ);
                }
                else if (this.lines.getValue() == Lines.TOP) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.maxY, tBB.minZ, tBB.maxX, tBB.maxY, tBB.maxZ);
                }
                if (this.noLineDepth.getValue()) {
                    GlStateManager.disableDepth();
                }
                TessellatorUtil.drawBoundingBox(tBB, this.width.getValue(), lineColor, this.fadeAlpha.getValue());
            }
            BlockRenderUtil.releaseGL();
        }
    }
    
    public void drawHoleTwoBlock(final BlockPos pos, final BlockPos two, final Color color, final Color lineColor) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(two.getX() + 1), (double)(two.getY() + this.height.getValue()), (double)(two.getZ() + 1));
        if (this.mode.getValue() == Mode.FULL) {
            BlockRenderUtil.releaseGL();
            TessellatorUtil.drawBox(axisAlignedBB, true, 1.0, color, color.getAlpha(), ((boolean)this.sides.getValue()) ? 60 : 63);
            BlockRenderUtil.releaseGL();
        }
        if (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.OUTLINE) {
            BlockRenderUtil.prepareGL();
            TessellatorUtil.drawBoundingBox(axisAlignedBB, this.width.getValue(), lineColor);
            BlockRenderUtil.releaseGL();
        }
        if (this.mode.getValue() == Mode.WIREFRAME) {
            BlockRenderUtil.prepareGL();
            BlockRenderUtil.drawWireframe(axisAlignedBB.offset(-((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)HoleESP.mc.getRenderManager()).getRenderPosZ()), lineColor.getRGB(), this.width.getValue());
            BlockRenderUtil.releaseGL();
        }
        if (this.mode.getValue() == Mode.FADE) {
            AxisAlignedBB tBB = new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(two.getX() + 1), (double)(two.getY() + this.height.getValue()), (double)(two.getZ() + 1));
            if (tBB.intersects(HoleESP.mc.player.getEntityBoundingBox()) && this.notSelf.getValue()) {
                tBB = tBB.setMaxY(Math.min(tBB.maxY, HoleESP.mc.player.posY + 1.0));
            }
            BlockRenderUtil.prepareGL();
            if (this.depth.getValue()) {
                GlStateManager.enableDepth();
                tBB = tBB.shrink(0.01);
            }
            TessellatorUtil.drawBox(tBB, true, this.height.getValue(), color, this.fadeAlpha.getValue(), ((boolean)this.sides.getValue()) ? 60 : 63);
            if (this.width.getValue() >= 0.1f) {
                if (this.lines.getValue() == Lines.BOTTOM) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.minY, tBB.minZ, tBB.maxX, tBB.minY, tBB.maxZ);
                }
                else if (this.lines.getValue() == Lines.TOP) {
                    tBB = new AxisAlignedBB(tBB.minX, tBB.maxY, tBB.minZ, tBB.maxX, tBB.maxY, tBB.maxZ);
                }
                if (this.noLineDepth.getValue()) {
                    GlStateManager.disableDepth();
                }
                TessellatorUtil.drawBoundingBox(tBB, this.width.getValue(), lineColor, this.fadeAlpha.getValue());
            }
            BlockRenderUtil.releaseGL();
        }
    }
    
    private enum Lines
    {
        FULL, 
        BOTTOM, 
        TOP;
    }
    
    private enum Mode
    {
        BOTTOM, 
        OUTLINE, 
        FULL, 
        WIREFRAME, 
        FADE;
    }
    
    private static class TwoBlockHole
    {
        private final BlockPos one;
        private final BlockPos extra;
        
        public TwoBlockHole(final BlockPos one, final BlockPos extra) {
            this.one = one;
            this.extra = extra;
        }
        
        public BlockPos getOne() {
            return this.one;
        }
        
        public BlockPos getExtra() {
            return this.extra;
        }
    }
}
