//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.shader.*;
import org.spongepowered.asm.mixin.gen.*;
import java.util.*;
import net.minecraft.client.renderer.*;

@Mixin({ RenderGlobal.class })
public interface IRenderGlobal
{
    @Accessor("entityOutlineShader")
    ShaderGroup getEntityOutlineShader();
    
    @Accessor("damagedBlocks")
    Map<Integer, DestroyBlockProgress> getDamagedBlocks();
}
