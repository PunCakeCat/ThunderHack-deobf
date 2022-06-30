//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public class TimerUtils
{
    private long lastMS;
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public void setTime(final long time) {
        this.lastMS = time;
    }
    
    public long getTime() {
        return this.getCurrentMS() - this.lastMS;
    }
}
