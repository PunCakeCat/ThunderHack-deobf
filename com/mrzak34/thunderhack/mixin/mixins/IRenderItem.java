//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;

public interface IRenderItem
{
    void setNotRenderingEffectsInGUI(final boolean p0);
    
    void invokeRenderModel(final IBakedModel p0, final ItemStack p1);
}
