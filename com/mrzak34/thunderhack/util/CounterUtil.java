//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public class CounterUtil
{
    private long lastMS;
    private long prevMS;
    
    public CounterUtil() {
        this.lastMS = 0L;
        this.prevMS = 0L;
    }
    
    public boolean isDelay(final long delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public void setLastMS(final long lastMS) {
        this.lastMS = lastMS;
    }
    
    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public int convertToMS(final int d) {
        return 1000 / d;
    }
    
    public boolean hasReached(final float f) {
        return this.getCurrentMS() - this.lastMS >= f;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public boolean delay(final float milliSec) {
        return this.getTime() - this.prevMS >= milliSec;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
}
