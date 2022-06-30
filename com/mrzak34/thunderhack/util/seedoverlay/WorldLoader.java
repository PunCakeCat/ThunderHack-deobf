//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.seedoverlay;

import java.util.*;
import net.minecraft.world.storage.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.terraingen.*;

public class WorldLoader
{
    public static IChunkGenerator ChunkGenerator;
    public static long seed;
    public static boolean GenerateStructures;
    public static AwesomeWorld fakeworld;
    public static Random rand;
    
    public static void setup() {
        final WorldSettings worldSettings = new WorldSettings(WorldLoader.seed, GameType.SURVIVAL, WorldLoader.GenerateStructures, false, WorldType.DEFAULT);
        final WorldInfo worldInfo = new WorldInfo(worldSettings, "FakeWorld");
        worldInfo.setMapFeaturesEnabled(true);
        WorldLoader.fakeworld = new AwesomeWorld(worldInfo);
        if (Util.mc.player.dimension == -1) {
            WorldLoader.ChunkGenerator = (IChunkGenerator)new ChunkGeneratorHell((World)WorldLoader.fakeworld, WorldLoader.fakeworld.getWorldInfo().isMapFeaturesEnabled(), WorldLoader.seed);
        }
        else {
            WorldLoader.ChunkGenerator = WorldLoader.fakeworld.provider.createChunkGenerator();
        }
    }
    
    public static Chunk CreateChunk(final int x, final int z, final int dis) {
        if (dis == -1 && !(WorldLoader.ChunkGenerator instanceof ChunkGeneratorHell)) {
            WorldLoader.ChunkGenerator = (IChunkGenerator)new ChunkGeneratorHell((World)WorldLoader.fakeworld, WorldLoader.fakeworld.getWorldInfo().isMapFeaturesEnabled(), WorldLoader.seed);
        }
        Chunk Testchunk;
        if (!WorldLoader.fakeworld.isChunkGeneratedAt(x, z)) {
            Testchunk = WorldLoader.ChunkGenerator.generateChunk(x, z);
        }
        else {
            Testchunk = WorldLoader.fakeworld.getChunk(x, z);
        }
        WorldLoader.fakeworld.getChunkProvider().loadedChunks.put(ChunkPos.asLong(x, z), (Object)Testchunk);
        Testchunk.onLoad();
        populate((IChunkProvider)WorldLoader.fakeworld.getChunkProvider(), WorldLoader.ChunkGenerator, x, z);
        return Testchunk;
    }
    
    public static void populate(final IChunkProvider chunkProvider, final IChunkGenerator chunkGenrator, final int x, final int z) {
        final Chunk chunk = chunkProvider.getLoadedChunk(x, z - 1);
        final Chunk chunk2 = chunkProvider.getLoadedChunk(x + 1, z);
        final Chunk chunk3 = chunkProvider.getLoadedChunk(x, z + 1);
        final Chunk chunk4 = chunkProvider.getLoadedChunk(x - 1, z);
        if (chunk2 != null && chunk3 != null && chunkProvider.getLoadedChunk(x + 1, z + 1) != null) {
            Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x, z);
        }
        if (chunk4 != null && chunk3 != null && chunkProvider.getLoadedChunk(x - 1, z + 1) != null) {
            Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x - 1, z);
        }
        if (chunk != null && chunk2 != null && chunkProvider.getLoadedChunk(x + 1, z - 1) != null) {
            Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x, z - 1);
        }
        if (chunk != null && chunk4 != null) {
            final Chunk chunk5 = chunkProvider.getLoadedChunk(x - 1, z - 1);
            if (chunk5 != null) {
                Awesomepopulate(chunkGenrator, WorldLoader.fakeworld, x - 1, z - 1);
            }
        }
    }
    
    private static void Awesomepopulate(final IChunkGenerator overworldChunkGen, final AwesomeWorld fakeworld, final int x, final int z) {
        final Chunk testchunk = fakeworld.getChunk(x, z);
        if (testchunk.isTerrainPopulated()) {
            if (overworldChunkGen.generateStructures(testchunk, x, z)) {
                testchunk.markDirty();
            }
        }
        else {
            testchunk.checkLight();
            overworldChunkGen.populate(x, z);
            testchunk.markDirty();
        }
    }
    
    public static void event(final PopulateChunkEvent.Populate event) {
        event.setResult(Event.Result.ALLOW);
    }
    
    public static void DecorateBiomeEvent(final DecorateBiomeEvent.Decorate event) {
        event.setResult(Event.Result.ALLOW);
    }
    
    static {
        WorldLoader.seed = 44776655L;
        WorldLoader.GenerateStructures = true;
    }
}
