//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.text.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;

public class TPSCounter extends Module
{
    public final Setting<ColorSetting> color;
    private final Setting<PositionSetting> pos;
    public Setting<mode> Mode;
    float x1;
    float y1;
    private static final DecimalFormat df;
    private long timeOfLastPacket;
    float timeDifference;
    
    public TPSCounter() {
        super("TPS", "trps", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.New));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.timeOfLastPacket = -1L;
        this.timeDifference = 0.0f;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(TPSCounter.mc);
        String str;
        if (this.Mode.getValue() == mode.Old) {
            str = "TPS " + ChatFormatting.WHITE + String.valueOf(Double.parseDouble(String.valueOf(Thunderhack.serverManager.getTPS())));
        }
        else {
            str = "TPS " + ChatFormatting.WHITE + TPSCounter.df.format(1000.0f / (this.timeDifference / 20.0f));
        }
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        Util.fr.drawStringWithShadow(str, this.x1, this.y1, this.color.getValue().getRawColor());
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (this.timeOfLastPacket != -1L) {
                final long currentTime = System.currentTimeMillis();
                this.timeDifference = (float)(currentTime - this.timeOfLastPacket);
            }
            this.timeOfLastPacket = System.currentTimeMillis();
        }
    }
    
    static {
        df = new DecimalFormat("0.00");
    }
    
    public enum mode
    {
        Old, 
        New;
    }
}
