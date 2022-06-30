//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.particles;

import com.mrzak34.thunderhack.features.setting.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;

public class ParticleSystem
{
    private static final float SPEED = 0.1f;
    private static List<Particle> particleList;
    private boolean linesa;
    private int dist;
    private ColorSetting color3;
    private ColorSetting color8;
    private float scale;
    private float nigger;
    
    public ParticleSystem(final int initAmount, final float scale, final ColorSetting color2, final ColorSetting color5, final boolean niggersarepidors, final float tnigga, final int nigg) {
        this.addParticles(initAmount);
        this.dist = nigg;
        this.nigger = tnigga;
        this.color3 = color2;
        this.scale = scale;
        this.color8 = color5;
        this.linesa = niggersarepidors;
    }
    
    public void addParticles(final int amount) {
        for (int i = 0; i < amount; ++i) {
            ParticleSystem.particleList.add(Particle.generateParticle());
        }
    }
    
    public static void clearParticles() {
        ParticleSystem.particleList.clear();
    }
    
    public void tick(final int delta) {
        if (Mouse.isButtonDown(0)) {
            this.addParticles(1);
        }
        for (final Particle particle : ParticleSystem.particleList) {
            particle.tick(delta, 0.1f);
        }
    }
    
    public void render() {
        for (final Particle particle : ParticleSystem.particleList) {
            RenderUtil.drawSmoothRect(particle.getX(), particle.getY(), particle.getX() + this.scale, particle.getY() + this.scale, this.color3.getRawColor());
            Particle nearestParticle = null;
            for (final Particle particle2 : ParticleSystem.particleList) {
                final float distance = particle.getDistanceTo(particle2);
                if (distance <= this.dist) {
                    nearestParticle = particle2;
                }
            }
            if (nearestParticle != null && this.linesa) {
                RenderUtil.drawLine(particle.getX() + this.scale / 2.0f, particle.getY() + this.scale / 2.0f, nearestParticle.getX(), nearestParticle.getY(), 1.0f, this.color8.getColor());
            }
        }
    }
    
    public static double distance(final float x, final float y, final float x1, final float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }
    
    static {
        ParticleSystem.particleList = new ArrayList<Particle>();
    }
}
