//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.notification;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import net.minecraft.util.text.*;
import java.util.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.concurrent.*;

public class NotificationManager extends Module
{
    public int scaledWidth;
    public int scaledHeight;
    public Setting<Integer> fade;
    public Setting<Integer> fade2;
    private static final List<Notification> notifications;
    
    public NotificationManager() {
        super("Notifications", "aga", Module.Category.CLIENT, true, false, false);
        this.fade = (Setting<Integer>)this.register(new Setting("fade", (Object)100, (Object)0, (Object)2048));
        this.fade2 = (Setting<Integer>)this.register(new Setting("fade2", (Object)500, (Object)0, (Object)2048));
    }
    
    public static void publicity(final String title, final String content, final int second, final NotificationType type) {
        final FontRenderer fontRenderer = Util.fr;
        NotificationManager.notifications.add(new Notification(title, content, type, second * 1000, fontRenderer));
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        if (!NotificationManager.notifications.isEmpty()) {
            final ScaledResolution sr = new ScaledResolution(NotificationManager.mc);
            final int srScaledHeight = sr.getScaledHeight();
            final int scaledWidth = sr.getScaledWidth();
            int y = srScaledHeight - 60;
            for (final Notification notification : NotificationManager.notifications) {
                final ScreenHelper screenHelper = notification.getTranslate();
                final int width = notification.getWidth() + 40 + Util.fr.getStringWidth(notification.getContent()) / 2;
                if (!notification.getTimer().hasReached((float)(notification.getTime() - (int)this.fade.getValue()))) {
                    try {
                        screenHelper.calculateCompensation((float)(scaledWidth - width), (float)y, 0.8f, 5.0f);
                    }
                    catch (Exception exception) {
                        System.out.println("fuck");
                    }
                }
                else {
                    try {
                        screenHelper.calculateCompensation((float)scaledWidth, notification.getTranslate().getY(), 0.8f, 5.0f);
                    }
                    catch (Exception exception) {
                        System.out.println("aboba");
                    }
                    if (Util.mc.player != null && Util.mc.world != null && notification.getTimer().getTime() > notification.getTime() + (int)this.fade2.getValue()) {
                        NotificationManager.notifications.remove(notification);
                    }
                }
                final float translateX = screenHelper.getX();
                final float translateY = screenHelper.getY();
                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                RectHelper.drawRect((double)translateX, (double)translateY, (double)(translateX - 2.0f), (double)(translateY + 28.0f), notification.getType().getColor());
                RectHelper.drawRect((double)translateX, (double)translateY, (double)scaledWidth, (double)(translateY + 28.0f), new Color(35, 34, 34).getRGB());
                Util.fr.drawStringWithShadow(TextFormatting.BOLD + notification.getTitle(), translateX + 5.0f, translateY + 4.0f, -1);
                Util.fr.drawStringWithShadow(notification.getContent(), translateX + 5.0f, translateY + 15.0f, new Color(245, 245, 245).getRGB());
                GlStateManager.popMatrix();
                if (NotificationManager.notifications.size() > 1) {
                    y -= 35;
                }
            }
        }
    }
    
    static {
        notifications = new CopyOnWriteArrayList<Notification>();
    }
}
