//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.block.state.*;

public interface IBlock
{
    void setHarvestLevelNonForge(final String p0, final int p1);
    
    String getHarvestToolNonForge(final IBlockState p0);
    
    int getHarvestLevelNonForge(final IBlockState p0);
}
