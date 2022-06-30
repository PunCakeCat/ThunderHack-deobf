//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.item.*;

public class ArmorUtils
{
    public static float calculatePercentage(final ItemStack stack) {
        final float durability = (float)(stack.getMaxDamage() - stack.getItemDamage());
        return durability / stack.getMaxDamage() * 100.0f;
    }
}
