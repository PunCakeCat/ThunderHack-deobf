//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class FlightPitch extends Module
{
    public Setting<Integer> xtt;
    public Setting<Integer> ytt;
    
    public FlightPitch() {
        super("FlightPitch", "\u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0443\u0433\u043e\u043b-\u0442\u0430\u043d\u0433\u0430\u0436\u0430", Module.Category.RENDER, true, false, false);
        this.xtt = (Setting<Integer>)this.register(new Setting("X", (T)512, (T)0, (T)1023));
        this.ytt = (Setting<Integer>)this.register(new Setting("Y", (T)512, (T)0, (T)1023));
    }
    
    public int getW() {
        return 15;
    }
    
    public int getH() {
        return 100;
    }
    
    public int getX() {
        return this.xtt.getValue();
    }
    
    public int getY() {
        return this.ytt.getValue();
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        RenderUtil.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.getW()), (float)(this.getY() + this.getH()), 1963986960);
        RenderUtil.drawRect((float)this.getX(), (this.getY() + this.getH()) / 2.0f, (float)(this.getX() + this.getW()), (this.getY() + this.getH()) / 2.0f + 1.0f, new Color(93, 91, 94, 161).getRGB());
        if (FlightPitch.mc.world != null) {
            final ScaledResolution sr = new ScaledResolution(FlightPitch.mc);
            final float playerPitch = FlightPitch.mc.player.rotationPitch;
            final float rotationPitch = MathUtil.wrap(playerPitch);
            RenderUtil.glScissor((float)this.getX(), (float)this.getY(), (float)(this.getX() + this.getW()), (float)(this.getY() + this.getH()), sr);
            GL11.glEnable(3089);
            FlightPitch.mc.fontRenderer.drawStringWithShadow("0", (float)(this.getX() + 4), this.getY() - rotationPitch + 45.0f, -1);
            FlightPitch.mc.fontRenderer.drawStringWithShadow("90", (float)(this.getX() + 3), this.getY() - rotationPitch + 135.0f, -1);
            FlightPitch.mc.fontRenderer.drawStringWithShadow("-90", (float)(this.getX() + 3), this.getY() - rotationPitch - 45.0f, -1);
            FlightPitch.mc.fontRenderer.drawStringWithShadow("-45", (float)(this.getX() + 3), this.getY() - rotationPitch, -1);
            FlightPitch.mc.fontRenderer.drawStringWithShadow("45", (float)(this.getX() + 3), this.getY() - rotationPitch + 90.0f, -1);
            RenderUtil.drawLine((float)(this.getX() + this.getW() / 2), (float)(this.getY() + 1), (float)(this.getX() + this.getW() / 2), (float)(this.getY() + this.getH() - 1), 2.0f, -7303024);
            GL11.glDisable(3089);
        }
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float thickness, final int hex) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glLineWidth(thickness);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
