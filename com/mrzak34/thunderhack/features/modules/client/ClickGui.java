//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.gui.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.features.gui.particles.*;

public class ClickGui extends Module
{
    private final ResourceLocation logo;
    private final ResourceLocation neko;
    private final ResourceLocation neko2;
    private final ResourceLocation crushcat;
    private final ResourceLocation zamorozka;
    private final ResourceLocation neko3;
    private final ResourceLocation anime;
    private static final String pathSaveim = "oyvey/custom_image/1.png";
    private ResourceLocation custom;
    private static ClickGui INSTANCE;
    private int heightt;
    private long fadeinn;
    private long fadeinnn;
    private boolean mouse;
    private boolean rainbowparticle;
    private int dist;
    private long start;
    private long fadedIn;
    private long fadeOut;
    private long end;
    private int widtht;
    private float animatorx;
    private int curfps;
    private int n;
    private final Timer timer;
    private final Timer timer2;
    public int i;
    public Setting<String> prefix;
    public final Setting<ColorSetting> mainColor;
    public final Setting<ColorSetting> topColor;
    public final Setting<ColorSetting> downColor;
    public final Setting<ColorSetting> imagecc;
    public Setting<Boolean> rainbow;
    public Setting<Integer> rainbowHue;
    public Setting<Float> rainbowBrightness;
    public Setting<Float> rainbowSaturation;
    private OyVeyGui click;
    public Setting<Boolean> tips;
    public Setting<Boolean> darkBackGround;
    public Setting<Boolean> antialias;
    public Setting<Boolean> notifyToggles;
    public boolean image;
    public Setting<Integer> imageScaleX;
    public Setting<Integer> imageScaleY;
    public Setting<Integer> hoverAlpha;
    public Setting<Integer> imagex;
    public Setting<Integer> imagey;
    public Setting<Integer> fadeintimeout;
    public Setting<Float> fadeintimespeed;
    public Setting<String> command;
    public Setting<TextUtil.Color> bracketColor;
    public Setting<TextUtil.Color> commandColor;
    public Setting<String> commandBracket;
    public Setting<String> commandBracket2;
    public String[] myString;
    private Setting<Moderator> shader;
    private Setting<mode> picture;
    
    public String getCommandMessage() {
        return TextUtil.coloredString(this.commandBracket.getPlannedValue(), this.bracketColor.getPlannedValue()) + TextUtil.coloredString(this.command.getPlannedValue(), this.commandColor.getPlannedValue()) + TextUtil.coloredString(this.commandBracket2.getPlannedValue(), this.bracketColor.getPlannedValue());
    }
    
    public ClickGui() {
        super("ClickGui", "\u0441\u0442\u0430\u0440\u044b\u0439 \u043a\u043b\u0438\u043a\u0433\u0443\u0438", Category.CLIENT, true, false, false);
        this.logo = new ResourceLocation("textures/logo.png");
        this.neko = new ResourceLocation("textures/neko.png");
        this.neko2 = new ResourceLocation("textures/image2.png");
        this.crushcat = new ResourceLocation("textures/image1.png");
        this.zamorozka = new ResourceLocation("textures/image5.png");
        this.neko3 = new ResourceLocation("textures/image6.png");
        this.anime = new ResourceLocation("textures/image3.png");
        this.custom = new ResourceLocation("oyvey/custom_image/1.png");
        this.timer = new Timer();
        this.timer2 = new Timer();
        this.i = 85;
        this.prefix = (Setting<String>)this.register(new Setting("Prefix", (T)"."));
        this.mainColor = (Setting<ColorSetting>)this.register(new Setting("Main Color", (T)new ColorSetting(-2009289807)));
        this.topColor = (Setting<ColorSetting>)this.register(new Setting("Cat Color", (T)new ColorSetting(-1323314462)));
        this.downColor = (Setting<ColorSetting>)this.register(new Setting(" Down Color", (T)new ColorSetting(-2011093215)));
        this.imagecc = (Setting<ColorSetting>)this.register(new Setting("ImageColorCorr", (T)new ColorSetting(-2011093215)));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (T)false));
        this.rainbowHue = (Setting<Integer>)this.register(new Setting("RainbowDelay", (T)240, (T)0, (T)600, v -> this.rainbow.getValue()));
        this.rainbowBrightness = (Setting<Float>)this.register(new Setting("Brightness ", (T)150.0f, (T)1.0f, (T)255.0f, v -> this.rainbow.getValue()));
        this.rainbowSaturation = (Setting<Float>)this.register(new Setting("Saturation", (T)150.0f, (T)1.0f, (T)255.0f, v -> this.rainbow.getValue()));
        this.tips = (Setting<Boolean>)this.register(new Setting("Tips", (T)false));
        this.darkBackGround = (Setting<Boolean>)this.register(new Setting("Background", (T)false));
        this.antialias = (Setting<Boolean>)this.register(new Setting("Antialias", (T)true));
        this.notifyToggles = (Setting<Boolean>)this.register(new Setting("NotifyToggles", (T)false));
        this.image = true;
        this.imageScaleX = (Setting<Integer>)this.register(new Setting("ImageScaleX", (T)425, (T)0, (T)1023));
        this.imageScaleY = (Setting<Integer>)this.register(new Setting("ImageScaleY", (T)425, (T)0, (T)1023));
        this.hoverAlpha = (Setting<Integer>)this.register(new Setting("imagey", (T)103, (T)0, (T)255));
        this.imagex = (Setting<Integer>)this.register(new Setting("imagex", (T)1023, (T)0, (T)1023));
        this.imagey = (Setting<Integer>)this.register(new Setting("imagey", (T)512, (T)0, (T)1023));
        this.fadeintimeout = (Setting<Integer>)this.register(new Setting("FadeInTimeout", (T)512, (T)0, (T)2048));
        this.fadeintimespeed = (Setting<Float>)this.register(new Setting("FadeInSpeed", (T)0.5f, (T)0.1f, (T)5.0f));
        this.command = (Setting<String>)this.register(new Setting("Command", (T)"ThunderHack+"));
        this.bracketColor = (Setting<TextUtil.Color>)this.register(new Setting("BracketColor", (T)TextUtil.Color.AQUA));
        this.commandColor = (Setting<TextUtil.Color>)this.register(new Setting("NameColor", (T)TextUtil.Color.GREEN));
        this.commandBracket = (Setting<String>)this.register(new Setting("Bracket", (T)"["));
        this.commandBracket2 = (Setting<String>)this.register(new Setting("Bracket2", (T)"]"));
        this.shader = (Setting<Moderator>)this.register(new Setting("shader", (T)Moderator.none));
        this.picture = (Setting<mode>)this.register(new Setting("image", (T)mode.Zamorozka));
        this.setInstance();
    }
    
    public static ClickGui getInstance() {
        if (ClickGui.INSTANCE == null) {
            ClickGui.INSTANCE = new ClickGui();
        }
        return ClickGui.INSTANCE;
    }
    
    @Override
    public void onEnable() {
        this.myString = new String[] { "image1", "image2", "image3", "image5", "image6", "logo", "neko" };
        this.n = (int)Math.floor(Math.random() * this.myString.length);
        Util.mc.displayGuiScreen((GuiScreen)OyVeyGui.getClickGui());
        this.timer.reset();
        Thunderhack.commandManager.setClientMessage(this.getCommandMessage());
    }
    
    private void setInstance() {
        ClickGui.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.shader.getValue() != Moderator.none) {
            if (OpenGlHelper.shadersSupported && ClickGui.mc.getRenderViewEntity() instanceof EntityPlayer) {
                if (ClickGui.mc.entityRenderer.getShaderGroup() != null) {
                    ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
                try {
                    ClickGui.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (ClickGui.mc.entityRenderer.getShaderGroup() != null && ClickGui.mc.currentScreen == null) {
                ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        final ScaledResolution sr = new ScaledResolution(ClickGui.mc);
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
        if (this.antialias.getValue()) {
            GL11.glEnable(3042);
        }
        Gui.drawRect(400, 400, 400, 400, this.imagecc.getValue().getRawColor());
        if (this.picture.getValue() == mode.Neko) {
            Util.mc.getTextureManager().bindTexture(this.neko);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.Logo) {
            Util.mc.getTextureManager().bindTexture(this.logo);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.CrushCattyna) {
            Util.mc.getTextureManager().bindTexture(this.crushcat);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.Zamorozka) {
            Util.mc.getTextureManager().bindTexture(this.zamorozka);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.Neko3) {
            Util.mc.getTextureManager().bindTexture(this.neko3);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.Neko2) {
            Util.mc.getTextureManager().bindTexture(this.neko2);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.Anime) {
            Util.mc.getTextureManager().bindTexture(this.anime);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
        if (this.picture.getValue() == mode.None) {}
        if (this.picture.getValue() == mode.Random && this.myString != null && this.n != 0) {
            final ResourceLocation rand = new ResourceLocation("textures/" + this.myString[this.n] + ".png");
            Util.mc.getTextureManager().bindTexture(rand);
            drawCompleteImage(xOffset - 1.0f + mouseposx - this.fadeinnn, yOffset - 1.0f - mouseposy, this.imageScaleX.getValue(), this.imageScaleY.getValue());
        }
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                Thunderhack.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + Thunderhack.commandManager.getPrefix());
            }
            Thunderhack.colorManager.setColor(this.mainColor.getValue().getColorObject());
        }
    }
    
    @Override
    public void onDisable() {
        this.timer.reset();
        if (ClickGui.mc.entityRenderer.getShaderGroup() != null) {
            ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        ParticleSystem.clearParticles();
    }
    
    @Override
    public void onLoad() {
        Thunderhack.colorManager.setColor(this.mainColor.getValue().getColorObject());
        Thunderhack.commandManager.setPrefix(this.prefix.getValue());
    }
    
    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof OyVeyGui)) {
            this.disable();
            this.timer.reset();
        }
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
    
    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    static {
        ClickGui.INSTANCE = new ClickGui();
    }
    
    public enum Moderator
    {
        none, 
        notch, 
        antialias, 
        art, 
        bits, 
        blobs, 
        blobs2, 
        blur, 
        bumpy, 
        color_convolve, 
        creeper, 
        deconverge, 
        desaturate, 
        flip, 
        fxaa, 
        green, 
        invert, 
        ntsc, 
        pencil, 
        phosphor, 
        sobel, 
        spider, 
        wobble;
    }
    
    public enum rainbowModeArray
    {
        Static, 
        Up;
    }
    
    public enum rainbowMode
    {
        Static, 
        Sideway;
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
