//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.notification;

import java.awt.*;

public enum NotificationType
{
    SUCCESS(new Color(100, 255, 100).getRGB()), 
    INFO(new Color(225, 225, 255).getRGB()), 
    ERROR(new Color(255, 100, 100).getRGB()), 
    WARNING(new Color(255, 211, 53).getRGB());
    
    private final int color;
    
    private NotificationType(final int color) {
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
}
