//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.seedoverlay;

import net.minecraft.client.multiplayer.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.storage.*;

public class AwesomeWorld extends World
{
    private ChunkProviderClient clientChunkProvider;
    
    public ChunkProviderClient getChunkProvider() {
        return (ChunkProviderClient)super.getChunkProvider();
    }
    
    protected AwesomeWorld(final WorldInfo worldInfo) {
        super((ISaveHandler)new SaveHandlerMP(), worldInfo, DimensionManager.createProviderFor(0), Util.mc.profiler, true);
        this.getWorldInfo().setDifficulty(EnumDifficulty.PEACEFUL);
        this.provider.setWorld((World)this);
        this.setSpawnPoint(new BlockPos(8, 64, 8));
        this.chunkProvider = this.createChunkProvider();
        this.mapStorage = (MapStorage)new SaveDataMemoryStorage();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.initCapabilities();
    }
    
    protected IChunkProvider createChunkProvider() {
        return (IChunkProvider)(this.clientChunkProvider = new ChunkProviderClient((World)this));
    }
    
    protected boolean isChunkLoaded(final int x, final int z, final boolean allowEmpty) {
        return allowEmpty || !this.getChunkProvider().provideChunk(x, z).isEmpty();
    }
}
