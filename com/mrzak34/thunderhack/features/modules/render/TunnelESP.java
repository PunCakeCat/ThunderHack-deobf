//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;

public class TunnelESP extends Module
{
    public Setting<Boolean> fov;
    private final Setting<Integer> range;
    private final Setting<Integer> rangeY;
    private final Setting<Integer> boxAlpha;
    private final Setting<Float> lineWidth;
    private final Setting<Integer> safeRed;
    private final Setting<Integer> safeGreen;
    private final Setting<Integer> safeBlue;
    private final Setting<Integer> safeAlpha;
    public Setting<Boolean> box;
    public Setting<Boolean> outline;
    private final Setting<Integer> safecRed;
    private final Setting<Integer> safecGreen;
    private final Setting<Integer> safecBlue;
    private final Setting<Integer> safecAlpha;
    
    public TunnelESP() {
        super("TunnelESP", "TunnelESP TunnelESP TunnelESP.", Module.Category.RENDER, false, false, false);
        this.fov = (Setting<Boolean>)this.register(new Setting("InFov", (T)true));
        this.range = (Setting<Integer>)this.register(new Setting("RangeX", (T)30, (T)0, (T)100));
        this.rangeY = (Setting<Integer>)this.register(new Setting("RangeY", (T)30, (T)0, (T)100));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.safeRed = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255));
        this.safeGreen = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.safeBlue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255));
        this.safeAlpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.safecRed = (Setting<Integer>)this.register(new Setting("OL-Red", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecGreen = (Setting<Integer>)this.register(new Setting("OL-Green", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecBlue = (Setting<Integer>)this.register(new Setting("OL-Blue", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.safecAlpha = (Setting<Integer>)this.register(new Setting("OL-Alpha", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
    }
    
    public void onRender3D(final Render3DEvent event) {
        assert TunnelESP.mc.renderViewEntity != null;
        final Vec3i playerPos = new Vec3i(TunnelESP.mc.renderViewEntity.posX, TunnelESP.mc.renderViewEntity.posY, TunnelESP.mc.renderViewEntity.posZ);
        for (int x = playerPos.getX() - this.range.getValue(); x < playerPos.getX() + this.range.getValue(); ++x) {
            for (int z = playerPos.getZ() - this.range.getValue(); z < playerPos.getZ() + this.range.getValue(); ++z) {
                for (int y = playerPos.getY() + this.rangeY.getValue(); y > playerPos.getY() - this.rangeY.getValue(); --y) {
                    final BlockPos pos = new BlockPos(x, y, z);
                    if (TunnelESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && !pos.equals((Object)new BlockPos(TunnelESP.mc.player.posX, TunnelESP.mc.player.posY, TunnelESP.mc.player.posZ))) {
                        if (BlockUtil.isPosInFov(pos) || !this.fov.getValue()) {
                            if (TunnelESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.AIR && TunnelESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.NETHERRACK && TunnelESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.NETHERRACK && TunnelESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.AIR && TunnelESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK && TunnelESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && TunnelESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.NETHERRACK)) {
                                RenderUtil.drawBoxESP(pos, new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.outline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
                                RenderUtil.drawBoxESP(pos.add(0, 1, 0), new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.outline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
                            }
                            else if (TunnelESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.NETHERRACK && TunnelESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.AIR && TunnelESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.AIR && TunnelESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.NETHERRACK && TunnelESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK && TunnelESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && TunnelESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.NETHERRACK)) {
                                RenderUtil.drawBoxESP(pos, new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.outline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
                                RenderUtil.drawBoxESP(pos.add(0, 1, 0), new Color(this.safeRed.getValue(), this.safeGreen.getValue(), this.safeBlue.getValue(), this.safeAlpha.getValue()), this.outline.getValue(), new Color(this.safecRed.getValue(), this.safecGreen.getValue(), this.safecBlue.getValue(), this.safecAlpha.getValue()), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), true);
                            }
                        }
                    }
                }
            }
        }
    }
}
