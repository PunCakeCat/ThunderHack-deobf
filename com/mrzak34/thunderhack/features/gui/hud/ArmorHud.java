//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.hud;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;

public class ArmorHud extends Module
{
    public final Setting<ColorSetting> color;
    private final Setting<PositionSetting> pos;
    float x1;
    float y1;
    
    public ArmorHud() {
        super("ArmorHud", "fps", Category.HUD, true, false, false);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.pos = (Setting<PositionSetting>)this.register(new Setting("Position", (T)new PositionSetting(0.5f, 0.5f)));
        this.x1 = 0.0f;
        this.y1 = 0.0f;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final ScaledResolution sr = new ScaledResolution(ArmorHud.mc);
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        this.renderArmorHUD(true);
    }
    
    public void renderArmorHUD(final boolean percent) {
        final ScaledResolution sr = new ScaledResolution(ArmorHud.mc);
        this.y1 = sr.getScaledHeight() * this.pos.getValue().getY();
        this.x1 = sr.getScaledWidth() * this.pos.getValue().getX();
        GlStateManager.enableTexture2D();
        int iteration = 0;
        final int y = (int)(this.y1 - 55.0f - ((ArmorHud.mc.player.isInWater() && ArmorHud.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0));
        for (final ItemStack is : ArmorHud.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = (int)(this.x1 - 90.0f + (9 - iteration) * 20 + 2.0f);
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(ArmorHud.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            this.renderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.renderer.getStringWidth(s)), (float)(y + 9), 16777215);
            if (!percent) {
                continue;
            }
            final int dmg = (int)ArmorUtils.calculatePercentage(is);
            this.renderer.drawStringWithShadow(dmg + "", (float)(x + 8 - this.renderer.getStringWidth(dmg + "") / 2), (float)(y - 11), ColorUtil.toRGBA(0, 255, 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
}
