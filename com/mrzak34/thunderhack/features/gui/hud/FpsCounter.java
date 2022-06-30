//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FpsCounter extends Module
{
    public final Setting<ColorSetting> color;
    private final Setting<PositionSetting> pos;
    float x1;
    float y1;
    
    public FpsCounter() {
        super("Fps", "fps", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(FpsCounter.mc);
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        final String fpsText = "FPS " + ChatFormatting.WHITE + Minecraft.debugFPS;
        Util.fr.drawStringWithShadow(fpsText, this.x1, this.y1, this.color.getValue().getRawColor());
    }
}
