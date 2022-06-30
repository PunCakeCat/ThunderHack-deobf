//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import java.awt.*;
import java.util.*;

public class KeyUtil extends Thread
{
    public static List<Integer> keys_;
    
    public static void clip(final List<Integer> keys) {
        KeyUtil.keys_ = keys;
        final KeyUtil obj = new KeyUtil();
        final Thread thread = new Thread(obj);
        thread.start();
    }
    
    @Override
    public void run() {
        Robot r = null;
        try {
            r = new Robot();
        }
        catch (AWTException e) {
            e.printStackTrace();
            return;
        }
        for (final Integer integer : KeyUtil.keys_) {
            r.keyPress(integer);
        }
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        for (final Integer integer : KeyUtil.keys_) {
            r.keyRelease(integer);
        }
    }
}
