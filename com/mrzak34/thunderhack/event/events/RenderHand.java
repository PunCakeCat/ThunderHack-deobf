//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;

public class RenderHand extends EventStage
{
    private final float ticks;
    
    public RenderHand(final float ticks) {
        this.ticks = ticks;
    }
    
    public float getPartialTicks() {
        return this.ticks;
    }
    
    public static class PostOutline extends RenderHand
    {
        public PostOutline(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreOutline extends RenderHand
    {
        public PreOutline(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PostFill extends RenderHand
    {
        public PostFill(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreFill extends RenderHand
    {
        public PreFill(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PostBoth extends RenderHand
    {
        public PostBoth(final float ticks) {
            super(ticks);
        }
    }
    
    public static class PreBoth extends RenderHand
    {
        public PreBoth(final float ticks) {
            super(ticks);
        }
    }
}
