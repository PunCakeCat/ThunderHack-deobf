//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public class TimerUtil
{
    private long time;
    public long timeSaved;
    
    public TimerUtil() {
        this.timeSaved = System.currentTimeMillis();
        this.time = -1L;
    }
    
    public boolean passedS(final double s) {
        return this.passedMs((long)s * 1000L);
    }
    
    public boolean passedDms(final double dms) {
        return this.passedMs((long)dms * 10L);
    }
    
    public boolean passedDs(final double ds) {
        return this.passedMs((long)ds * 100L);
    }
    
    public boolean passedMs(final long ms) {
        return this.passedNS(this.convertToNS(ms));
    }
    
    public void setMs(final long ms) {
        this.time = System.nanoTime() - this.convertToNS(ms);
    }
    
    public boolean passedNS(final long ns) {
        return System.nanoTime() - this.time >= ns;
    }
    
    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }
    
    public TimerUtil reset() {
        this.time = System.nanoTime();
        return this;
    }
    
    public boolean GetDifferenceTiming(final double d) {
        return System.currentTimeMillis() - this.timeSaved >= d;
    }
    
    public void SetCurrentTime(final long l) {
        this.timeSaved = l;
    }
    
    public void UpdateCurrentTime() {
        this.timeSaved = System.currentTimeMillis();
    }
    
    public long getMs(final long time) {
        return time / 1000000L;
    }
    
    public long convertToNS(final long time) {
        return time * 1000000L;
    }
    
    public boolean sleep(final long l) {
        if (System.nanoTime() / 1000000L - l >= l) {
            this.reset();
            return true;
        }
        return false;
    }
}
