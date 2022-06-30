//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.google.common.eventbus.*;

public class Anchor extends Module
{
    public static boolean Anchoring;
    private final Setting<Integer> pitch;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> pull;
    int holeblocks;
    
    public Anchor() {
        super("Anchor", "\u0435\u0441\u043b\u0438 \u043d\u0430\u0434 \u0445\u043e\u043b\u043a\u043e\u0439-\u0434\u0432\u0438\u0436\u0435\u043d\u0438\u0435=0 \u0442\u0430\u043a \u043f\u043e\u043d\u044f\u0442\u043d\u043e?", Module.Category.MOVEMENT, false, false, false);
        this.pitch = (Setting<Integer>)this.register(new Setting("Pitch", (T)60, (T)0, (T)90));
        this.disable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true));
        this.pull = (Setting<Boolean>)this.register(new Setting("Pull", (T)true));
    }
    
    public boolean isBlockHole(final BlockPos blockPos) {
        this.holeblocks = 0;
        if (Anchor.mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(0, 0, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Anchor.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || Anchor.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        return this.holeblocks >= 9;
    }
    
    public Vec3d GetCenter(final double d, final double d2, final double d3) {
        final double d4 = Math.floor(d) + 0.5;
        final double d5 = Math.floor(d2);
        final double d6 = Math.floor(d3) + 0.5;
        return new Vec3d(d4, d5, d6);
    }
    
    @Subscribe
    public void onUpdate() {
        if (Anchor.mc.world == null) {
            return;
        }
        if (Anchor.mc.player.rotationPitch >= this.pitch.getValue()) {
            if (this.isBlockHole(this.getPlayerPos().down(1)) || this.isBlockHole(this.getPlayerPos().down(2)) || this.isBlockHole(this.getPlayerPos().down(3)) || this.isBlockHole(this.getPlayerPos().down(4))) {
                Anchor.Anchoring = true;
                if (!this.pull.getValue()) {
                    Anchor.mc.player.motionX = 0.0;
                    Anchor.mc.player.motionZ = 0.0;
                }
                else {
                    final Vec3d center = this.GetCenter(Anchor.mc.player.posX, Anchor.mc.player.posY, Anchor.mc.player.posZ);
                    final double d = Math.abs(center.x - Anchor.mc.player.posX);
                    final double d2 = Math.abs(center.z - Anchor.mc.player.posZ);
                    if (d > 0.1 || d2 > 0.1) {
                        final double d3 = center.x - Anchor.mc.player.posX;
                        final double d4 = center.z - Anchor.mc.player.posZ;
                        Anchor.mc.player.motionX = d3 / 2.0;
                        Anchor.mc.player.motionZ = d4 / 2.0;
                    }
                }
            }
            else {
                Anchor.Anchoring = false;
            }
        }
        if (this.disable.getValue() && EntityUtil.isSafe((Entity)Anchor.mc.player)) {
            this.disable();
        }
    }
    
    public void onDisable() {
        Anchor.Anchoring = false;
        this.holeblocks = 0;
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Anchor.mc.player.posX), Math.floor(Anchor.mc.player.posY), Math.floor(Anchor.mc.player.posZ));
    }
}
