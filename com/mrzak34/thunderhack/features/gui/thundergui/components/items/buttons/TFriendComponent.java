//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons;

import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.gui.thundergui.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import com.mrzak34.thunderhack.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class TFriendComponent extends TItem
{
    private final String nickname;
    ResourceLocation head;
    ResourceLocation crackedSkin;
    ResourceLocation bin;
    
    public TFriendComponent(final String nickname, final int x, final int y) {
        super(nickname);
        this.head = null;
        this.crackedSkin = new ResourceLocation("textures/cracked.png");
        this.bin = new ResourceLocation("textures/trashbinnigga.png");
        this.nickname = nickname;
        this.setLocation((float)x, (float)y);
        this.head = SkinStorageManipulationer.getTexture2(nickname, "png");
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.y + 26.0f < 81 + ThunderGui.thunderguiY) {
            return;
        }
        RenderUtil.drawRect2(this.x, this.y, this.x + 432.0f, 26.0f + this.y, ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
        FontRender.drawString(this.nickname, this.x + 30.0f, this.y + 3.0f, new Color(12961221).getRGB());
        FontRender.drawString3(this.checkOnline(this.nickname) ? "Online" : "Offline", (int)this.x + 30, (int)this.y + 16, this.checkOnline(this.nickname) ? new Color(121866).getRGB() : new Color(7829367).getRGB());
        if (this.isHoveringItem(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            RenderUtil.drawSmoothRect(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, new Color(16600142).getRGB());
        }
        else {
            RenderUtil.drawSmoothRect(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, new Color(16515843).getRGB());
        }
        if (this.head != null) {
            Util.mc.getTextureManager().bindTexture(this.head);
            ModuleButton.drawCompleteImage(this.x + 2.0f, this.y + 2.0f, 22, 22);
        }
        else {
            Util.mc.getTextureManager().bindTexture(this.crackedSkin);
            ModuleButton.drawCompleteImage(this.x + 2.0f, this.y + 2.0f, 22, 22);
        }
        if (this.isHoveringItem(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            drawImage(this.bin, this.x + 411.0f, this.y + 6.0f, 14.0f, 14.0f, new Color(6316128));
        }
        else {
            drawImage(this.bin, this.x + 411.0f, this.y + 6.0f, 13.0f, 13.0f, new Color(16579836));
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.y + 26.0f < 81 + ThunderGui.thunderguiY) {
            return;
        }
        if (this.isHoveringItem(this.x + 410.0f, this.y + 5.0f, this.x + 426.0f, this.y + 21.0f, (float)mouseX, (float)mouseY)) {
            Thunderhack.friendManager.removeFriend(this.nickname);
        }
    }
    
    public boolean isHoveringItem(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }
    
    public boolean checkOnline(final String name) {
        return TFriendComponent.mc.player.connection.getPlayerInfo(name) != null;
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final float x, final float y, final float width, final float height, final Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        setColor(color.getRGB());
        Util.mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, 0.0f, 0.0f, (int)width, (int)height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }
    
    public static void setColor(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    public static void setColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
}
