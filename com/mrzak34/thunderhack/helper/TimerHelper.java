//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

public class TimerHelper
{
    private long ms;
    
    public TimerHelper() {
        this.ms = this.getCurrentMS();
    }
    
    private long getCurrentMS() {
        return System.currentTimeMillis();
    }
    
    public boolean hasReached(final float milliseconds) {
        return this.getCurrentMS() - this.ms > milliseconds;
    }
    
    public void reset() {
        this.ms = this.getCurrentMS();
    }
    
    public long getTime() {
        return this.getCurrentMS() - this.ms;
    }
}
