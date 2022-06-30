//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WaterMark extends Module
{
    int i;
    public Timer timer;
    
    public WaterMark() {
        super("WaterMark", "WaterMark", Category.HUD, true, false, false);
        this.i = 0;
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        RenderUtil.drawSmoothRect(4.0f, 4.0f, (float)(Util.fr.getStringWidth("ThunderHack") + 4 + Util.fr.getStringWidth(WaterMark.mc.player.getName()) + Util.fr.getStringWidth(" 9999 \u043c\u0441 ") + 140), 18.0f, new Color(35, 35, 40, 230).getRGB());
        if (this.timer.passedMs(350L)) {
            ++this.i;
            this.timer.reset();
        }
        if (this.i == 24) {
            this.i = 0;
        }
        final String w1 = "_";
        final String w2 = "T_";
        final String w3 = "Th_";
        final String w4 = "Thu_";
        final String w5 = "Thun_";
        final String w6 = "Thund_";
        final String w7 = "Thunde_";
        final String w8 = "Thunder_";
        final String w9 = "ThunderH_";
        final String w10 = "ThunderHa_";
        final String w11 = "ThunderHac_";
        final String w12 = "ThunderHack";
        final String w13 = "ThunderHack";
        final String w14 = "ThunderHack";
        final String w15 = "ThunderHac_";
        final String w16 = "ThunderHa_";
        final String w17 = "ThunderH_";
        final String w18 = "Thunder_";
        final String w19 = "Thunde_";
        final String w20 = "Thund_";
        final String w21 = "Thun_";
        final String w22 = "Thu_";
        final String w23 = "Th_";
        final String w24 = "T_";
        final String w25 = "_";
        String text = "";
        if (this.i == 0) {
            text = w1;
        }
        if (this.i == 1) {
            text = w2;
        }
        if (this.i == 2) {
            text = w3;
        }
        if (this.i == 3) {
            text = w4;
        }
        if (this.i == 4) {
            text = w5;
        }
        if (this.i == 5) {
            text = w6;
        }
        if (this.i == 6) {
            text = w7;
        }
        if (this.i == 7) {
            text = w8;
        }
        if (this.i == 8) {
            text = w9;
        }
        if (this.i == 9) {
            text = w10;
        }
        if (this.i == 10) {
            text = w11;
        }
        if (this.i == 11) {
            text = w12;
        }
        if (this.i == 12) {
            text = w13;
        }
        if (this.i == 13) {
            text = w14;
        }
        if (this.i == 14) {
            text = w15;
        }
        if (this.i == 15) {
            text = w16;
        }
        if (this.i == 16) {
            text = w17;
        }
        if (this.i == 17) {
            text = w18;
        }
        if (this.i == 18) {
            text = w19;
        }
        if (this.i == 19) {
            text = w20;
        }
        if (this.i == 20) {
            text = w21;
        }
        if (this.i == 21) {
            text = w22;
        }
        if (this.i == 22) {
            text = w23;
        }
        if (this.i == 23) {
            text = w24;
        }
        if (this.i == 23) {
            text = w25;
        }
        Util.fr.drawStringWithShadow(text, 9.0f, 7.0f, -1);
        Util.fr.drawStringWithShadow("|  " + WaterMark.mc.player.getName(), (float)(Util.fr.getStringWidth(w13) + 20), 7.0f, -1);
        Util.fr.drawStringWithShadow("|  " + Thunderhack.serverManager.getPing() + " \u043c\u0441", (float)(Util.fr.getStringWidth(w13) + 35 + Util.fr.getStringWidth(WaterMark.mc.player.getName())), 7.0f, -1);
        Util.fr.drawStringWithShadow("|  " + ((Minecraft.getMinecraft().currentServerData.serverIP == null) ? "SinglePlayer" : Minecraft.getMinecraft().currentServerData.serverIP), (float)(Util.fr.getStringWidth(w13) + 38 + Util.fr.getStringWidth(WaterMark.mc.player.getName()) + Util.fr.getStringWidth(" 9999 \u043c\u0441 ")), 7.0f, -1);
    }
}
