//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.*;
import java.util.*;

public interface Globals
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final Random random = new Random();
    public static final char SECTIONSIGN = '�';
    
    default boolean nullCheck() {
        return Globals.mc.player == null || Globals.mc.world == null;
    }
}
