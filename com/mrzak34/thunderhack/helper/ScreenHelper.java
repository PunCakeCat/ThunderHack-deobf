//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.helper;

import net.minecraft.client.*;

public class ScreenHelper
{
    private float x;
    private float y;
    private long lastMS;
    
    public ScreenHelper(final float x, final float y) {
        this.x = x;
        this.y = y;
        this.lastMS = System.currentTimeMillis();
    }
    
    public static double animate(final double target, double current, double speed) {
        final boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        }
        else if (speed > 1.0) {
            speed = 1.0;
        }
        final double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += factor;
        }
        else {
            current -= factor;
        }
        return current;
    }
    
    public static double progressiveAnimation(final double now, final double desired, final double speed) {
        final double dif = Math.abs(now - desired);
        final int fps = Minecraft.getDebugFPS();
        if (dif > 0.0) {
            double animationSpeed = MathematicHelper.round(Math.min(10.0, Math.max(0.05, 144.0 / fps * (dif / 10.0) * speed)), 0.05);
            if (dif < animationSpeed) {
                animationSpeed = dif;
            }
            if (now < desired) {
                return now + animationSpeed;
            }
            if (now > desired) {
                return now - animationSpeed;
            }
        }
        return now;
    }
    
    public static double linearAnimation(final double now, final double desired, final double speed) {
        final double dif = Math.abs(now - desired);
        final int fps = Minecraft.getDebugFPS();
        if (dif > 0.0) {
            double animationSpeed = MathematicHelper.round(Math.min(10.0, Math.max(0.005, 144.0 / fps * speed)), 0.005);
            if (dif != 0.0 && dif < animationSpeed) {
                animationSpeed = dif;
            }
            if (now < desired) {
                return now + animationSpeed;
            }
            if (now > desired) {
                return now - animationSpeed;
            }
        }
        return now;
    }
    
    public void interpolate(final float targetX, final float targetY, final double speed) {
        final long currentMS = System.currentTimeMillis();
        final long delta = currentMS - this.lastMS;
        this.lastMS = currentMS;
        double deltaX = 0.0;
        double deltaY = 0.0;
        if (speed != 0.0) {
            deltaX = Math.abs(targetX - this.x) * 0.35f / (10.0 / speed);
            deltaY = Math.abs(targetY - this.y) * 0.35f / (10.0 / speed);
        }
        this.x = AnimationHelper.calculateCompensation(targetX, this.x, delta, deltaX);
        this.y = AnimationHelper.calculateCompensation(targetY, this.y, delta, deltaY);
    }
    
    public void calculateCompensation(final float targetX, final float targetY, final float xSpeed, final float ySpeed) {
        final int deltaX = (int)(Math.abs(targetX - this.x) * xSpeed);
        final int deltaY = (int)(Math.abs(targetY - this.y) * ySpeed);
        this.x = AnimationHelper.calculateCompensation(targetX, this.x, 1L / Minecraft.getDebugFPS(), (double)deltaX);
        this.y = AnimationHelper.calculateCompensation(targetY, this.y, 1L / Minecraft.getDebugFPS(), (double)deltaY);
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
}
