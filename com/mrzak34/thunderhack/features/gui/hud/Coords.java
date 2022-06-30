//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Coords extends Module
{
    public final Setting<ColorSetting> color;
    private final Setting<PositionSetting> pos;
    public Setting<Integer> imageScaleX;
    public Setting<Integer> imageScaleY;
    float x1;
    float y1;
    
    public Coords() {
        super("Coords", "Autopot", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.imageScaleX = (Setting<Integer>)this.register(new Setting("X", (T)900, (T)0, (T)1000));
        this.imageScaleY = (Setting<Integer>)this.register(new Setting("Y", (T)900, (T)0, (T)1000));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(Coords.mc);
        final boolean inHell = Coords.mc.world.getBiome(Coords.mc.player.getPosition()).getBiomeName().equals("Hell");
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        final int posX = (int)Coords.mc.player.posX;
        final int posY = (int)Coords.mc.player.posY;
        final int posZ = (int)Coords.mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int)(Coords.mc.player.posX * nether);
        final int hposZ = (int)(Coords.mc.player.posZ * nether);
        final String coordinates = ChatFormatting.WHITE + "XYZ " + ChatFormatting.RESET + (inHell ? (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]" + ChatFormatting.RESET) : (posX + ", " + posY + ", " + posZ + ChatFormatting.WHITE + " [" + ChatFormatting.RESET + hposX + ", " + hposZ + ChatFormatting.WHITE + "]"));
        Util.fr.drawStringWithShadow(coordinates, this.x1, this.y1, this.color.getValue().getRawColor());
    }
}
