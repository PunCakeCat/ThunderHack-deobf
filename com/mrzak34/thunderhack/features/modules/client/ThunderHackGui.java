//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.features.gui.thundergui.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public class ThunderHackGui extends Module
{
    public final Setting<ColorSetting> buttsColor;
    public final Setting<ColorSetting> catcolorinmodule;
    public final Setting<ColorSetting> LeftsideColor;
    public final Setting<ColorSetting> thplate;
    public final Setting<ColorSetting> shadow;
    public Setting<Integer> blurstr;
    private Setting<ClickGui.mode> picture;
    public int i;
    private final Timer timer;
    private final ResourceLocation logo;
    private final ResourceLocation neko;
    private final ResourceLocation neko2;
    private final ResourceLocation crushcat;
    private final ResourceLocation zamorozka;
    private final ResourceLocation neko3;
    private final ResourceLocation anime;
    public boolean image;
    public Setting<Integer> imageScaleX;
    public Setting<Integer> imageScaleY;
    public final Setting<ColorSetting> imagecc;
    public Setting<Integer> imagex;
    public Setting<Integer> imagey;
    public Setting<Integer> fadeintimeout;
    public Setting<Float> fadeintimespeed;
    private long fadeinn;
    private long fadeinnn;
    public String[] myString;
    int n;
    private static ThunderHackGui INSTANCE;
    
    public ThunderHackGui() {
        super("ThunderGui", "\u043d\u043e\u0432\u044b\u0439 \u043a\u043b\u0438\u043a \u0433\u0443\u0438", Category.CLIENT, true, false, false);
        this.buttsColor = (Setting<ColorSetting>)this.register(new Setting("ButtonsColor", (T)new ColorSetting(-955051502)));
        this.catcolorinmodule = (Setting<ColorSetting>)this.register(new Setting("CatColor", (T)new ColorSetting(-162567959)));
        this.LeftsideColor = (Setting<ColorSetting>)this.register(new Setting("Leftside Color", (T)new ColorSetting(-1390009051)));
        this.thplate = (Setting<ColorSetting>)this.register(new Setting("thplate Color", (T)new ColorSetting(-652291704)));
        this.shadow = (Setting<ColorSetting>)this.register(new Setting("Shadow Color", (T)new ColorSetting(-1727198196)));
        this.blurstr = (Setting<Integer>)this.register(new Setting("blurstr", (T)100, (T)0, (T)100));
        this.picture = (Setting<ClickGui.mode>)this.register(new Setting("image", (T)ClickGui.mode.Zamorozka));
        this.i = 85;
        this.timer = new Timer();
        this.logo = new ResourceLocation("textures/logo.png");
        this.neko = new ResourceLocation("textures/neko.png");
        this.neko2 = new ResourceLocation("textures/image2.png");
        this.crushcat = new ResourceLocation("textures/image1.png");
        this.zamorozka = new ResourceLocation("textures/girl2.png");
        this.neko3 = new ResourceLocation("textures/image6.png");
        this.anime = new ResourceLocation("textures/image3.png");
        this.image = true;
        this.imageScaleX = (Setting<Integer>)this.register(new Setting("ImageScaleX", (T)512, (T)0, (T)1023));
        this.imageScaleY = (Setting<Integer>)this.register(new Setting("ImageScaleY", (T)512, (T)0, (T)1023));
        this.imagecc = (Setting<ColorSetting>)this.register(new Setting("ImageColorCorr", (T)new ColorSetting(449170629)));
        this.imagex = (Setting<Integer>)this.register(new Setting("imagex", (T)1011, (T)0, (T)1500));
        this.imagey = (Setting<Integer>)this.register(new Setting("imagey", (T)90, (T)0, (T)1023));
        this.fadeintimeout = (Setting<Integer>)this.register(new Setting("FadeInTimeout", (T)500, (T)0, (T)2048));
        this.fadeintimespeed = (Setting<Float>)this.register(new Setting("FadeInSpeed", (T)0.3f, (T)0.1f, (T)5.0f));
        this.n = 0;
        this.setInstance();
    }
    
    @Override
    public void onDisable() {
        this.timer.reset();
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        final ScaledResolution sr = new ScaledResolution(ThunderHackGui.mc);
        final double psx = this.imagex.getValue();
        final double psy = this.imagey.getValue();
        final float xOffset = (float)psx + 10.0f;
        final float yOffset = (float)psy;
        final float mouseposx = Mouse.getX() / 100.0f;
        final float mouseposy = Mouse.getY() / 100.0f;
        this.fadeinn = (long)(this.timer.getPassedTimeMs() / this.fadeintimespeed.getValue());
        if (this.fadeinn < this.fadeintimeout.getValue()) {
            this.fadeinnn = this.fadeinn;
        }
        Gui.drawRect(400, 400, 400, 400, this.imagecc.getValue().getRawColor());
        if (this.picture.getValue() == ClickGui.mode.Neko) {
            Util.mc.getTextureManager().bindTexture(this.neko);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.Logo) {
            Util.mc.getTextureManager().bindTexture(this.logo);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.CrushCattyna) {
            Util.mc.getTextureManager().bindTexture(this.crushcat);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.Zamorozka) {
            Util.mc.getTextureManager().bindTexture(this.zamorozka);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.Neko3) {
            Util.mc.getTextureManager().bindTexture(this.neko3);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.Neko2) {
            Util.mc.getTextureManager().bindTexture(this.neko2);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.Anime) {
            Util.mc.getTextureManager().bindTexture(this.anime);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == ClickGui.mode.None) {}
        if (this.picture.getValue() == ClickGui.mode.Random && this.myString != null && this.n != 0) {
            final ResourceLocation rand = new ResourceLocation("textures/" + this.myString[this.n] + ".png");
            Util.mc.getTextureManager().bindTexture(rand);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
    }
    
    private void setInstance() {
        ThunderHackGui.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        this.myString = new String[] { "image1", "image2", "image3", "image5", "image6", "logo", "neko" };
        this.n = (int)Math.floor(Math.random() * this.myString.length);
        this.timer.reset();
        Util.mc.displayGuiScreen((GuiScreen)ThunderGui.getThunderGui());
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f((float)width, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f((float)width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public static ThunderHackGui getInstance() {
        if (ThunderHackGui.INSTANCE == null) {
            ThunderHackGui.INSTANCE = new ThunderHackGui();
        }
        return ThunderHackGui.INSTANCE;
    }
    
    static {
        ThunderHackGui.INSTANCE = new ThunderHackGui();
    }
    
    public enum mode
    {
        CrushCattyna, 
        Logo, 
        Neko, 
        Neko2, 
        Anime, 
        Neko3, 
        Zamorozka, 
        Random, 
        None;
    }
}
