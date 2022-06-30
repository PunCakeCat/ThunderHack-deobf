//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ArrayList extends Module
{
    private static ArrayList INSTANCE;
    public final Setting<ColorSetting> color;
    public Setting<Integer> animationHorizontalTime;
    public Setting<Integer> animationVerticalTime;
    private final Setting<PositionSetting> pos;
    float x1;
    float y1;
    
    public ArrayList() {
        super("ArrayList", "Autopot", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.animationHorizontalTime = (Setting<Integer>)this.register(new Setting("AnimationHTime", (T)500, (T)1, (T)1000));
        this.animationVerticalTime = (Setting<Integer>)this.register(new Setting("AnimationVTime", (T)50, (T)1, (T)500));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.setInstance();
    }
    
    public static ArrayList getInstance() {
        if (ArrayList.INSTANCE == null) {
            ArrayList.INSTANCE = new ArrayList();
        }
        return ArrayList.INSTANCE;
    }
    
    private void setInstance() {
        ArrayList.INSTANCE = this;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(ArrayList.mc);
        final int[] counter1 = { 1 };
        int j = (ArrayList.mc.currentScreen instanceof GuiChat) ? 14 : 0;
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        for (int k = 0; k < Thunderhack.moduleManager.sortedModules.size(); ++k) {
            final Module module = Thunderhack.moduleManager.sortedModules.get(k);
            final String str = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
            this.renderer.drawString(str, this.x1 - 2.0f - this.renderer.getStringWidth(str), this.y1 + j * 10, ((boolean)ClickGui.getInstance().rainbow.getValue()) ? ColorUtil.rainbow(counter1[0] * ClickGui.getInstance().rainbowHue.getValue()).getRGB() : this.color.getValue().getRawColor(), true);
            ++j;
            ++counter1[0];
        }
    }
    
    static {
        ArrayList.INSTANCE = new ArrayList();
    }
    
    public enum RenderingMode
    {
        ABC, 
        Length;
    }
}
