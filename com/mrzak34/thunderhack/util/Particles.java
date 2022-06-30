//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

public class Particles
{
    public int ticks;
    public Location location;
    public String text;
    
    public Particles(final Location location, final String text) {
        this.location = location;
        this.text = text;
        this.ticks = 0;
    }
}
