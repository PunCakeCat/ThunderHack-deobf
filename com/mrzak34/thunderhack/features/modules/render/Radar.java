//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.awt.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Radar extends Module
{
    public Setting<Integer> posx;
    public Setting<Integer> posy;
    public Setting<Integer> size;
    public int scale;
    
    public Radar() {
        super("SkeetRadar", "RADAROK", Module.Category.HUD, true, false, false);
        this.posx = (Setting<Integer>)this.register(new Setting("PosX", (T)860, (T)0, (T)900));
        this.posy = (Setting<Integer>)this.register(new Setting("PosY", (T)15, (T)100, (T)350));
        this.size = (Setting<Integer>)this.register(new Setting("Size", (T)100, (T)30, (T)300));
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        final double psx = this.posx.getValue();
        final double psy = this.posy.getValue();
        final ScaledResolution sr = new ScaledResolution(Radar.mc);
        this.scale = 2;
        final int sizeRect = this.size.getValue();
        final float xOffset = (float)(sr.getScaledWidth() - sizeRect - psx);
        final float yOffset = (float)psy;
        final double playerPosX = Radar.mc.player.posX;
        final double playerPosZ = Radar.mc.player.posZ;
        RectHelper.drawBorderedRect(xOffset + 2.5f, yOffset + 2.5f, xOffset + sizeRect - 2.5f, yOffset + sizeRect - 2.5f, 0.5f, PaletteHelper.getColor(2), PaletteHelper.getColor(11), false);
        RectHelper.drawBorderedRect(xOffset + 3.0f, yOffset + 3.0f, xOffset + sizeRect - 3.0f, yOffset + sizeRect - 3.0f, 0.2f, PaletteHelper.getColor(2), PaletteHelper.getColor(11), false);
        RectHelper.drawRect(xOffset + (sizeRect / 2.0f - 0.5), yOffset + 3.5, xOffset + (sizeRect / 2.0f + 0.2), yOffset + sizeRect - 3.5, PaletteHelper.getColor(155, 100));
        RectHelper.drawRect(xOffset + 3.5, yOffset + (sizeRect / 2.0f - 0.2), xOffset + sizeRect - 3.5, yOffset + (sizeRect / 2.0f + 0.5), PaletteHelper.getColor(155, 100));
        for (final EntityPlayer entityPlayer : Radar.mc.world.playerEntities) {
            if (entityPlayer == Radar.mc.player) {
                continue;
            }
            final float partialTicks = Radar.mc.timer.renderPartialTicks;
            final float posX = (float)(entityPlayer.posX + (entityPlayer.posX - entityPlayer.lastTickPosX) * partialTicks - playerPosX) * 2.0f;
            final float posZ = (float)(entityPlayer.posZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * partialTicks - playerPosZ) * 2.0f;
            final int color = Radar.mc.player.canEntityBeSeen((Entity)entityPlayer) ? new Color(255, 0, 0).getRGB() : new Color(255, 255, 0).getRGB();
            final float cos = (float)Math.cos(Radar.mc.player.rotationYaw * 0.017453292);
            final float sin = (float)Math.sin(Radar.mc.player.rotationYaw * 0.017453292);
            float rotY = -(posZ * cos - posX * sin);
            float rotX = -(posX * cos + posZ * sin);
            if (rotY > sizeRect / 2.0f - 6.0f) {
                rotY = sizeRect / 2.0f - 6.0f;
            }
            else if (rotY < -(sizeRect / 2.0f - 8.0f)) {
                rotY = -(sizeRect / 2.0f - 8.0f);
            }
            if (rotX > sizeRect / 2.0f - 5.0f) {
                rotX = sizeRect / 2.0f - 5.0f;
            }
            else if (rotX < -(sizeRect / 2.0f - 5.0f)) {
                rotX = -(sizeRect / 2.0f - 5.0f);
            }
            RectHelper.drawRect(xOffset + sizeRect / 2.0f + rotX - 1.5f, yOffset + sizeRect / 2.0f + rotY - 1.5f, xOffset + sizeRect / 2.0f + rotX + 1.5f, yOffset + sizeRect / 2.0f + rotY + 1.5f, color);
        }
    }
}
