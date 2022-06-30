//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.util.seedoverlay.*;
import com.mrzak34.thunderhack.notification.*;
import java.util.concurrent.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class SeedOverlay extends Module
{
    public Setting<Integer> BlockLimit;
    public Setting<Integer> Distance;
    public Setting<Boolean> GrassSpread;
    public Setting<Boolean> FalsePositive;
    public Setting<Boolean> LavaMix;
    public Setting<Boolean> Bush;
    public Setting<Boolean> Tree;
    public Setting<Boolean> Liquid;
    public Setting<Boolean> Fallingblock;
    public Setting<String> sd;
    private static ExecutorService executor;
    private static ExecutorService executor2;
    public int currentdis;
    private ArrayList<ChunkData> chunks;
    private ArrayList<int[]> tobesearch;
    private final Timer timer;
    
    public SeedOverlay() {
        super("SeedOverlay", "\u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u0444\u0435\u0439\u043a\u043e\u0432\u044b\u0439 \u043c\u0438\u0440-\u0434\u043b\u044f \u043f\u043e\u0438\u0441\u043a\u0430 \u043d\u0435\u0441\u043e\u043e\u0442\u0432\u0435\u0442\u0441\u0432\u0438\u0439", Category.MISC, false, false, false);
        this.BlockLimit = (Setting<Integer>)this.register(new Setting("Chance", (T)200, (T)0, (T)5000));
        this.Distance = (Setting<Integer>)this.register(new Setting("Distance", (T)6, (T)0, (T)15));
        this.GrassSpread = (Setting<Boolean>)this.register(new Setting("GrassSpread", (T)false));
        this.FalsePositive = (Setting<Boolean>)this.register(new Setting("FalsePositive", (T)false));
        this.LavaMix = (Setting<Boolean>)this.register(new Setting("LavaMix", (T)false));
        this.Bush = (Setting<Boolean>)this.register(new Setting("Bush", (T)false));
        this.Tree = (Setting<Boolean>)this.register(new Setting("Tree", (T)false));
        this.Liquid = (Setting<Boolean>)this.register(new Setting("Liquid", (T)false));
        this.Fallingblock = (Setting<Boolean>)this.register(new Setting("Fallingblock", (T)false));
        this.sd = (Setting<String>)this.register(new Setting("seed", (T)"-4172144997902289642"));
        this.currentdis = 0;
        this.chunks = new ArrayList<ChunkData>();
        this.tobesearch = new ArrayList<int[]>();
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(500L)) {
            if (SeedOverlay.mc.player.dimension != this.currentdis) {
                Command.sendMessage("\u041f\u0435\u0440\u0435\u0432\u043a\u043b\u044e\u0447\u0438 \u043c\u043e\u0434\u0443\u043b\u044c");
                this.toggle();
            }
            this.searchViewDistance();
            this.runviewdistance();
            this.timer.reset();
        }
        int[] remove = null;
        try {
            final Iterator<int[]> iterator = this.tobesearch.iterator();
            while (iterator.hasNext()) {
                final int[] vec2d = remove = iterator.next();
                final Object o;
                SeedOverlay.executor.execute(() -> WorldLoader.CreateChunk(o[0], o[1], SeedOverlay.mc.player.dimension));
            }
        }
        catch (Exception ex) {}
        this.tobesearch.remove(remove);
    }
    
    @Override
    public void onEnable() {
        WorldLoader.seed = Long.parseLong(this.sd.getValue());
        try {
            NotificationManager.publicity("SeedOverlay", "\u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043d \u043d\u0430 " + WorldLoader.seed, 3, NotificationType.INFO);
        }
        catch (Exception ex) {}
        if (SeedOverlay.mc.isSingleplayer()) {
            Command.sendMessage("\u0442\u043e\u043b\u044c\u043a\u043e \u0432 \u043c\u0443\u043b\u044c\u0442\u0438\u043f\u043b\u0435\u0435\u0440\u0435 \u0434\u0435\u0431\u0438\u043b \u0442\u0443\u043f\u043e\u0439 \u043a\u043e\u043d\u0447 \u0431\u0435\u0437\u043c\u0430\u043c\u043d\u044b\u0439");
            this.toggle();
        }
        if (WorldLoader.seed == 44776655L) {
            Command.sendMessage("\u041d\u0435\u0442 \u0441\u0438\u0434\u0430 \u0434\u0435\u0431\u0438\u043b");
            this.toggle();
            return;
        }
        this.currentdis = SeedOverlay.mc.player.dimension;
        SeedOverlay.executor = Executors.newSingleThreadExecutor();
        SeedOverlay.executor2 = Executors.newSingleThreadExecutor();
        WorldLoader.setup();
        this.chunks = new ArrayList<ChunkData>();
        this.searchViewDistance();
    }
    
    private void searchViewDistance() {
        int x;
        int z;
        boolean found;
        final Iterator<int[]> iterator;
        int[] vec2d;
        SeedOverlay.executor.execute(() -> {
            for (x = SeedOverlay.mc.player.chunkCoordX - this.Distance.getValue(); x <= SeedOverlay.mc.player.chunkCoordX + this.Distance.getValue(); ++x) {
                for (z = SeedOverlay.mc.player.chunkCoordZ - this.Distance.getValue(); z <= SeedOverlay.mc.player.chunkCoordZ + this.Distance.getValue(); ++z) {
                    if (this.havenotsearched(x, z) && SeedOverlay.mc.world.isChunkGeneratedAt(x, z)) {
                        found = false;
                        this.tobesearch.iterator();
                        while (iterator.hasNext()) {
                            vec2d = iterator.next();
                            if (vec2d[0] == x && vec2d[1] == z) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            this.tobesearch.add(new int[] { x, z });
                        }
                    }
                }
            }
        });
    }
    
    private void runviewdistance() {
        for (int x = SeedOverlay.mc.player.chunkCoordX - this.Distance.getValue(); x <= SeedOverlay.mc.player.chunkCoordX + this.Distance.getValue(); ++x) {
            for (int z = SeedOverlay.mc.player.chunkCoordZ - this.Distance.getValue(); z <= SeedOverlay.mc.player.chunkCoordZ + this.Distance.getValue(); ++z) {
                if (SeedOverlay.mc.world.isChunkGeneratedAt(x, z) && WorldLoader.fakeworld.isChunkGeneratedAt(x, z) && WorldLoader.fakeworld.isChunkGeneratedAt(x + 1, z) && WorldLoader.fakeworld.isChunkGeneratedAt(x, z + 1) && WorldLoader.fakeworld.isChunkGeneratedAt(x + 1, z + 1) && this.havenotsearched(x, z)) {
                    final ChunkData data = new ChunkData(new ChunkPos(x, z), false);
                    this.searchChunk(SeedOverlay.mc.world.getChunk(x, z), data);
                    this.chunks.add(data);
                }
            }
        }
    }
    
    private boolean havenotsearched(final int x, final int z) {
        for (final ChunkData chunk : this.chunks) {
            if (chunk.chunkPos.x == x && chunk.chunkPos.z == z) {
                return false;
            }
        }
        return true;
    }
    
    private void searchChunk(final Chunk chunk, final ChunkData data) {
        int x;
        int z;
        int y;
        SeedOverlay.executor2.execute(() -> {
            try {
                for (x = chunk.getPos().getXStart(); x <= chunk.getPos().getXEnd(); ++x) {
                    for (z = chunk.getPos().getZStart(); z <= chunk.getPos().getZEnd(); ++z) {
                        for (y = 0; y < 255; ++y) {
                            if (this.BlockFast(new BlockPos(x, y, z), WorldLoader.fakeworld.getBlockState(new BlockPos(x, y, z)).getBlock(), chunk.getBlockState(x, y, z).getBlock())) {
                                data.blocks.add(new BlockPos(x, y, z));
                            }
                        }
                    }
                }
                data.Searched = true;
            }
            catch (Exception ex) {}
        });
    }
    
    private boolean BlockFast(final BlockPos blockPos, final Block FakeChunk, final Block RealChunk) {
        if (RealChunk instanceof BlockSnow) {
            return false;
        }
        if (FakeChunk instanceof BlockSnow) {
            return false;
        }
        if (RealChunk instanceof BlockVine) {
            return false;
        }
        if (FakeChunk instanceof BlockVine) {
            return false;
        }
        if (!this.Fallingblock.getValue()) {
            if (RealChunk instanceof BlockFalling) {
                return false;
            }
            if (FakeChunk instanceof BlockFalling) {
                return false;
            }
        }
        if (!this.Liquid.getValue()) {
            if (RealChunk instanceof BlockLiquid) {
                return false;
            }
            if (FakeChunk instanceof BlockLiquid) {
                return false;
            }
            if (SeedOverlay.mc.world.getBlockState(blockPos.down()).getBlock() instanceof BlockLiquid) {
                return false;
            }
            if (SeedOverlay.mc.world.getBlockState(blockPos.down(2)).getBlock() instanceof BlockLiquid) {
                return false;
            }
        }
        if (!this.Tree.getValue()) {
            if (FakeChunk instanceof BlockGrass && this.Treeroots(blockPos)) {
                return false;
            }
            if (RealChunk instanceof BlockLog || RealChunk instanceof BlockLeaves) {
                return false;
            }
            if (FakeChunk instanceof BlockLog || FakeChunk instanceof BlockLeaves) {
                return false;
            }
        }
        if (!this.GrassSpread.getValue()) {
            if (RealChunk instanceof BlockGrass && FakeChunk instanceof BlockDirt) {
                return false;
            }
            if (RealChunk instanceof BlockDirt && FakeChunk instanceof BlockGrass) {
                return false;
            }
        }
        if (!this.Bush.getValue()) {
            if (RealChunk instanceof BlockBush) {
                return false;
            }
            if (RealChunk instanceof BlockReed) {
                return false;
            }
            if (FakeChunk instanceof BlockBush) {
                return false;
            }
        }
        if (!this.LavaMix.getValue() && (RealChunk instanceof BlockObsidian || RealChunk.equals(Blocks.COBBLESTONE)) && this.Lavamix(blockPos)) {
            return false;
        }
        if (!this.FalsePositive.getValue()) {
            if (FakeChunk instanceof BlockOre && (RealChunk instanceof BlockStone || RealChunk instanceof BlockMagma || RealChunk instanceof BlockNetherrack || RealChunk instanceof BlockDirt)) {
                return false;
            }
            if (RealChunk instanceof BlockOre && (FakeChunk instanceof BlockStone || FakeChunk instanceof BlockMagma || FakeChunk instanceof BlockNetherrack || FakeChunk instanceof BlockDirt)) {
                return false;
            }
            if (FakeChunk instanceof BlockRedstoneOre && (RealChunk instanceof BlockStone || RealChunk instanceof BlockDirt)) {
                return false;
            }
            if (RealChunk instanceof BlockRedstoneOre && (FakeChunk instanceof BlockStone || FakeChunk instanceof BlockDirt)) {
                return false;
            }
            if (FakeChunk instanceof BlockGlowstone && RealChunk instanceof BlockAir) {
                return false;
            }
            if (RealChunk instanceof BlockGlowstone && FakeChunk instanceof BlockAir) {
                return false;
            }
            if (FakeChunk instanceof BlockMagma && RealChunk instanceof BlockNetherrack) {
                return false;
            }
            if (RealChunk instanceof BlockMagma && FakeChunk instanceof BlockNetherrack) {
                return false;
            }
            if (RealChunk instanceof BlockFire || FakeChunk instanceof BlockFire) {
                return false;
            }
            if (RealChunk instanceof BlockOre && FakeChunk instanceof BlockOre) {
                return false;
            }
            if (RealChunk.getLocalizedName().equals(Blocks.MONSTER_EGG.getLocalizedName()) && FakeChunk instanceof BlockStone) {
                return false;
            }
            if ((FakeChunk instanceof BlockStone && RealChunk instanceof BlockDirt) || (FakeChunk instanceof BlockDirt && RealChunk instanceof BlockStone)) {
                return false;
            }
            if (!(FakeChunk instanceof BlockAir) && RealChunk instanceof BlockAir && !SeedOverlay.mc.world.getBlockState(blockPos).getBlock().getLocalizedName().equals(RealChunk.getLocalizedName())) {
                return false;
            }
        }
        return !FakeChunk.getLocalizedName().equals(RealChunk.getLocalizedName());
    }
    
    public boolean Treeroots(final BlockPos b) {
        return SeedOverlay.mc.world.getBlockState(b.up()).getBlock() instanceof BlockLog;
    }
    
    public boolean Lavamix(final BlockPos b) {
        return SeedOverlay.mc.world.getBlockState(b.up()).getBlock() instanceof BlockLiquid || SeedOverlay.mc.world.getBlockState(b.down()).getBlock() instanceof BlockLiquid || SeedOverlay.mc.world.getBlockState(b.add(1, 0, 0)).getBlock() instanceof BlockLiquid || SeedOverlay.mc.world.getBlockState(b.add(0, 0, 1)).getBlock() instanceof BlockLiquid || SeedOverlay.mc.world.getBlockState(b.add(-1, 0, 0)).getBlock() instanceof BlockLiquid || SeedOverlay.mc.world.getBlockState(b.add(0, 0, -1)).getBlock() instanceof BlockLiquid;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        try {
            int blocklimit = 0;
            final ArrayList<ChunkData> Remove = new ArrayList<ChunkData>();
            for (final ChunkData chunk : this.chunks) {
                if (chunk.Searched) {
                    if (SeedOverlay.mc.player.getDistance((double)chunk.chunkPos.getXEnd(), 100.0, (double)chunk.chunkPos.getZEnd()) > 2000.0) {
                        Remove.add(chunk);
                    }
                    for (final BlockPos block : chunk.blocks) {
                        if (blocklimit > this.BlockLimit.getValue()) {
                            break;
                        }
                        RenderUtil.blockEsp(new BlockPos(block.x, block.y, block.z), new Color(8, 253, 0, 255), 1.0, 1.0);
                        ++blocklimit;
                    }
                }
            }
            this.chunks.removeAll(Remove);
        }
        catch (Exception ex) {}
    }
    
    public static class ChunkData
    {
        private boolean Searched;
        public final List<BlockPos> blocks;
        private ChunkPos chunkPos;
        
        public List<BlockPos> getBlocks() {
            return this.blocks;
        }
        
        public ChunkData(final ChunkPos chunkPos, final boolean Searched) {
            this.blocks = new ArrayList<BlockPos>();
            this.chunkPos = chunkPos;
            this.Searched = Searched;
        }
    }
}
