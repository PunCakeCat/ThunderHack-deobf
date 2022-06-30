//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang3.reflect.*;

public class ReflectUtils
{
    public static Field getField(final Class<?> c, final String... names) {
        final int l = names.length;
        int fernflowerSucks = 0;
        while (fernflowerSucks < l) {
            final String s = names[fernflowerSucks];
            try {
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                final Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                return f;
            }
            catch (Exception e) {
                ++fernflowerSucks;
                continue;
            }
            break;
        }
        System.out.println("Invalid Fields: " + Arrays.asList(names) + " For Class: " + c.getName());
        return null;
    }
    
    public static Object callMethod(final Object target, final Object[] params, final String... names) {
        final int len = names.length;
        int i = 0;
        while (i < len) {
            final String s = names[i];
            try {
                return MethodUtils.invokeMethod(target, true, s, params);
            }
            catch (Exception e) {
                ++i;
                continue;
            }
            break;
        }
        System.out.println("Invalid Method: " + Arrays.asList(names));
        return null;
    }
}
