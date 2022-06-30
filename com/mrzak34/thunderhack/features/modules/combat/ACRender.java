//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ACRender extends Module
{
    public ACRender() {
        super("ACRender", "Speeds up mining.", Category.PLAYER, true, true, false);
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (ACRender.mc.world == null || ACRender.mc.player == null) {
            return;
        }
        if (NewAC.getInstance().renderDmg.getValue() != NewAC.RenderTextMode.NONE && NewAC.getInstance().renderBlock != null) {
            if (NewAC.getInstance().renderTimeoutTimer.passedMs(1000L)) {
                return;
            }
            GlStateManager.pushMatrix();
            try {
                NewAC.glBillboardDistanceScaled(NewAC.getInstance().renderBlock.getX() + 0.5f, NewAC.getInstance().renderBlock.getY() + 0.5f, NewAC.getInstance().renderBlock.getZ() + 0.5f, (EntityPlayer)ACRender.mc.player, 1.0f);
            }
            catch (Exception ex) {}
            final String damageText = ((Math.floor(NewAC.getInstance().renderDamage) == NewAC.getInstance().renderDamage) ? Integer.valueOf((int)NewAC.getInstance().renderDamage) : String.format("%.1f", NewAC.getInstance().renderDamage)) + "";
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (NewAC.getInstance().renderDmg.getValue() == NewAC.RenderTextMode.SHADED) {
                ACRender.mc.fontRenderer.drawStringWithShadow(damageText, (float)(int)(-(ACRender.mc.fontRenderer.getStringWidth(damageText) / 2.0)), -4.0f, -1);
            }
            else {
                ACRender.mc.fontRenderer.drawString(damageText, (int)(-(ACRender.mc.fontRenderer.getStringWidth(damageText) / 2.0)), -4, -1);
            }
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
    }
    
    private enum RenderTextMode
    {
        NONE, 
        FLAT, 
        SHADED;
    }
}
