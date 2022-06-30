//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.particles;

import java.util.*;
import javax.vecmath.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import java.awt.*;

public class Particle
{
    private static final Random random;
    private Vector2f velocity;
    private Vector2f pos;
    private float size;
    private float alpha;
    
    public Particle(final Vector2f velocity, final float x, final float y, final float size) {
        this.velocity = velocity;
        this.pos = new Vector2f(x, y);
        this.size = size;
    }
    
    public static Particle generateParticle() {
        final ScaledResolution sr = new ScaledResolution(Util.mc);
        final Vector2f velocity = new Vector2f((float)(Math.random() * 2.0 - 1.0), (float)(Math.random() * 2.0 - 1.0));
        final float x = (float)Particle.random.nextInt(sr.getScaledWidth());
        final float y = (float)Particle.random.nextInt(sr.getScaledHeight());
        final float size = (float)(Math.random() * 4.0) + 1.0f;
        return new Particle(velocity, x, y, size);
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public Vector2f getVelocity() {
        return this.velocity;
    }
    
    public void setVelocity(final Vector2f velocity) {
        this.velocity = velocity;
    }
    
    public float getX() {
        return this.pos.getX();
    }
    
    public void setX(final float x) {
        this.pos.setX(x);
    }
    
    public float getY() {
        return this.pos.getY();
    }
    
    public void setY(final float y) {
        this.pos.setY(y);
    }
    
    public float getSize() {
        return this.size;
    }
    
    public void setSize(final float size) {
        this.size = size;
    }
    
    public void tick(final int delta, final float speed) {
        final Vector2f pos = this.pos;
        pos.x += this.velocity.getX() * delta * speed;
        final Vector2f pos2 = this.pos;
        pos2.y += this.velocity.getY() * delta * speed;
        if (this.alpha < 255.0f) {
            this.alpha += 0.05f * delta;
        }
        if (this.pos.getX() > Display.getWidth()) {
            this.pos.setX(0.0f);
        }
        if (this.pos.getX() < 0.0f) {
            this.pos.setX((float)Display.getWidth());
        }
        if (this.pos.getY() > Display.getHeight()) {
            this.pos.setY(0.0f);
        }
        if (this.pos.getY() < 0.0f) {
            this.pos.setY((float)Display.getHeight());
        }
    }
    
    public float getDistanceTo(final Particle particle1) {
        return this.getDistanceTo(particle1.getX(), particle1.getY());
    }
    
    public float getDistanceTo(final float x, final float y) {
        return (float)distance(this.getX(), this.getY(), x, y);
    }
    
    public static Color rainbow(final float speed, final float off) {
        double time = System.currentTimeMillis() / (double)speed;
        time += off;
        time %= 255.0;
        return Color.getHSBColor((float)(time / 255.0), 1.0f, 1.0f);
    }
    
    public static double distance(final float x, final float y, final float x1, final float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }
    
    static {
        random = new Random();
    }
}
