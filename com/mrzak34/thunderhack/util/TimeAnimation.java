//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.math.*;

public class TimeAnimation
{
    private final long length;
    private double start;
    private double end;
    private double current;
    private double progress;
    private boolean playing;
    private boolean backwards;
    private boolean reverseOnEnd;
    private long startTime;
    private long lastTime;
    private double per;
    private long dif;
    private boolean flag;
    private AnimationMode mode;
    
    public TimeAnimation(final long length, final double start, final double end, final boolean backwards, final AnimationMode mode) {
        this.length = length;
        this.start = start;
        this.current = start;
        this.end = end;
        this.mode = mode;
        this.backwards = backwards;
        this.startTime = System.currentTimeMillis();
        this.playing = true;
        this.dif = System.currentTimeMillis() - this.startTime;
        switch (mode) {
            case LINEAR: {
                this.per = (end - start) / length;
                break;
            }
            case EXPONENTIAL: {
                double dif = end - start;
                this.flag = (dif < 0.0);
                if (this.flag) {
                    dif *= -1.0;
                }
                for (int i = 0; i < length; ++i) {
                    dif = Math.sqrt(dif);
                }
                this.per = dif;
                break;
            }
        }
        this.lastTime = System.currentTimeMillis();
    }
    
    public TimeAnimation(final long length, final double start, final double end, final boolean backwards, final boolean reverseOnEnd, final AnimationMode mode) {
        this(length, start, end, backwards, mode);
        this.reverseOnEnd = reverseOnEnd;
    }
    
    public void add(final float partialTicks) {
        if (this.playing) {
            if (this.mode == AnimationMode.LINEAR) {
                this.current = this.start + this.progress;
                this.progress += this.per * (System.currentTimeMillis() - this.lastTime);
            }
            else if (this.mode == AnimationMode.EXPONENTIAL) {}
            this.current = MathHelper.clamp(this.current, this.start, this.end);
            if (this.current >= this.end || (this.backwards && this.current <= this.start)) {
                if (this.reverseOnEnd) {
                    this.reverse();
                    this.reverseOnEnd = false;
                }
                else {
                    this.playing = false;
                }
            }
        }
        this.lastTime = System.currentTimeMillis();
    }
    
    public long getLength() {
        return this.length;
    }
    
    public double getStart() {
        return this.start;
    }
    
    public double getEnd() {
        return this.end;
    }
    
    public double getCurrent() {
        return this.current;
    }
    
    public void setCurrent(final double current) {
        this.current = current;
    }
    
    public AnimationMode getMode() {
        return this.mode;
    }
    
    public void setMode(final AnimationMode mode) {
        this.mode = mode;
    }
    
    public boolean isPlaying() {
        return this.playing;
    }
    
    public void play() {
        this.playing = true;
    }
    
    public void stop() {
        this.playing = false;
    }
    
    public boolean isBackwards() {
        return this.backwards;
    }
    
    public void reverse() {
        this.backwards = !this.backwards;
        this.per *= -1.0;
    }
}
