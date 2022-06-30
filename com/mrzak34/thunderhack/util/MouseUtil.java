//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public class MouseUtil
{
    public static int convertToMouse(final int key) {
        switch (key) {
            case -2: {
                return 0;
            }
            case -3: {
                return 1;
            }
            case -4: {
                return 2;
            }
            case -5: {
                return 3;
            }
            case -6: {
                return 4;
            }
            default: {
                return -1;
            }
        }
    }
}
