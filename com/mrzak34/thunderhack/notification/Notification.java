//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.notification;

import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.gui.*;

public class Notification
{
    private final ScreenHelper screenHelper;
    private final FontRenderer fontRenderer;
    private final String title;
    private final String content;
    private final int time;
    private final NotificationType type;
    private final TimerHelper timer;
    
    public Notification(final String title, final String content, final NotificationType type, final int second, final FontRenderer fontRenderer) {
        this.title = title;
        this.content = content;
        this.time = second;
        this.type = type;
        this.timer = new TimerHelper();
        this.fontRenderer = fontRenderer;
        final ScaledResolution sr = new ScaledResolution(Util.mc);
        this.screenHelper = new ScreenHelper((float)(sr.getScaledWidth() - this.getWidth() + this.getWidth()), (float)(sr.getScaledHeight() - 60));
    }
    
    public int getWidth() {
        return Math.max(100, Math.max(this.fontRenderer.getStringWidth(this.title), this.fontRenderer.getStringWidth(this.content)) + 40);
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public NotificationType getType() {
        return this.type;
    }
    
    public TimerHelper getTimer() {
        return this.timer;
    }
    
    public ScreenHelper getTranslate() {
        return this.screenHelper;
    }
}
