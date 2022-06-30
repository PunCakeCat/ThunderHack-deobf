//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui.thundergui;

import java.awt.*;
import net.minecraft.util.*;
import java.util.stream.*;
import java.util.concurrent.atomic.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.manager.*;
import java.util.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.features.gui.thundergui.components.items.buttons.*;
import com.mrzak34.thunderhack.features.modules.*;

public class ThunderGui extends GuiScreen
{
    public static int thunderguiX;
    public static int thunderguiY;
    public static int thunderguiscaleX;
    public static int thunderguiscaleY;
    public final ArrayList<TModuleButt> components;
    public final ArrayList<TFriendComponent> friendslist;
    public final ArrayList<TConfigComponent> configlist;
    boolean friendlistening;
    boolean configListening;
    int anim;
    int anim2;
    static Categories currentCategory;
    boolean dragging;
    int dragX;
    int i;
    Timer timer;
    int dragY;
    float coolfade;
    Color oldcolor;
    String addConfigLine;
    String addFriendLine;
    public int totalwheel;
    ResourceLocation head;
    ResourceLocation combaticon;
    ResourceLocation miscicon;
    ResourceLocation movementicon;
    ResourceLocation clienticon;
    ResourceLocation playericon;
    ResourceLocation hudicon;
    ResourceLocation friendmanagericon;
    ResourceLocation rendericon;
    ResourceLocation configicon;
    ResourceLocation funnygameicon;
    private static ThunderGui INSTANCE;
    
    public ThunderGui() {
        this.components = new ArrayList<TModuleButt>();
        this.friendslist = new ArrayList<TFriendComponent>();
        this.configlist = new ArrayList<TConfigComponent>();
        this.friendlistening = false;
        this.configListening = false;
        this.anim = 0;
        this.anim2 = 0;
        this.dragging = false;
        this.dragX = 0;
        this.i = 0;
        this.timer = new Timer();
        this.dragY = 0;
        this.coolfade = 0.0f;
        this.oldcolor = new Color(getCatColor().getRGB());
        this.addConfigLine = "";
        this.addFriendLine = "";
        this.totalwheel = 0;
        this.combaticon = new ResourceLocation("textures/combaticon.png");
        this.miscicon = new ResourceLocation("textures/miscicon.png");
        this.movementicon = new ResourceLocation("textures/movementicon.png");
        this.clienticon = new ResourceLocation("textures/clienticon.png");
        this.playericon = new ResourceLocation("textures/playericon.png");
        this.hudicon = new ResourceLocation("textures/hudicon.png");
        this.friendmanagericon = new ResourceLocation("textures/friendmanagericon.png");
        this.rendericon = new ResourceLocation("textures/rendericon.png");
        this.configicon = new ResourceLocation("textures/configpng.png");
        this.funnygameicon = new ResourceLocation("textures/funnygameicon.png");
        this.setInstance();
        this.load();
    }
    
    public void retryLoadHead() {
        if (SkinStorageManipulationer.getTexture2(this.mc.player.getName(), "png") != null) {
            this.head = SkinStorageManipulationer.getTexture2(this.mc.player.getName(), "png");
        }
        else {
            try {
                ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + this.mc.player.getName() + "/100.png", this.mc.player.getName());
            }
            catch (Exception ex) {}
        }
    }
    
    public static ThunderGui getInstance() {
        if (ThunderGui.INSTANCE == null) {
            ThunderGui.INSTANCE = new ThunderGui();
        }
        return ThunderGui.INSTANCE;
    }
    
    public static ThunderGui getThunderGui() {
        return getInstance();
    }
    
    private void setInstance() {
        ThunderGui.INSTANCE = this;
    }
    
    public void load() {
        final File file = new File("ThunderHack/");
        final List<File> directories = Arrays.stream((Object[])Objects.requireNonNull((T[])file.listFiles())).filter(File::isDirectory).filter(f -> !f.getName().equals("util")).collect((Collector<? super Object, ?, List<File>>)Collectors.toList());
        final AtomicInteger index = new AtomicInteger();
        final AtomicInteger index2 = new AtomicInteger();
        final AtomicInteger index3 = new AtomicInteger();
        if (ThunderGui.currentCategory != Categories.FRIENDS && ThunderGui.currentCategory != Categories.CONFIGS) {
            final AtomicInteger atomicInteger;
            Thunderhack.moduleManager.getModulesByCategory(ThunderGui.currentCategory).forEach(module -> {
                if (!module.hidden) {
                    this.components.add(new TModuleButt(module, ThunderGui.thunderguiX + 140, ThunderGui.thunderguiY + 58 + 44 * atomicInteger.get()));
                    atomicInteger.set(atomicInteger.get() + 1);
                }
            });
        }
        else if (ThunderGui.currentCategory == Categories.FRIENDS) {
            for (final FriendManager.Friend friend : Thunderhack.friendManager.getFriends()) {
                this.friendslist.add(new TFriendComponent(friend.getUsername(), ThunderGui.thunderguiX + 140, ThunderGui.thunderguiY + 90 + 35 * index2.get()));
                index2.set(index2.get() + 1);
            }
        }
        else {
            for (final File file2 : directories) {
                if (!Objects.equals(file2.getName(), "customimage") && !Objects.equals(file2.getName(), "pvp") && !Objects.equals(file2.getName(), "notebot") && !Objects.equals(file2.getName(), "customimage") && !Objects.equals(file2.getName(), "kits") && !Objects.equals(file2.getName(), "tmp") && !Objects.equals(file2.getName(), "friendsAvatars") && !Objects.equals(file2.getName(), "skins") && !file2.getName().contains("oldcfg")) {
                    this.configlist.add(new TConfigComponent(file2.getName(), ThunderGui.thunderguiX + 140, ThunderGui.thunderguiY + 90 + 35 * index3.get()));
                    index3.set(index3.get() + 1);
                }
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final Color downplatecolor = new Color(PaletteHelper.fadeColor(this.oldcolor.getRGB(), getCatColor().getRGB(), this.coolfade));
        if (this.coolfade <= 1.0f) {
            this.coolfade += 0.02f;
        }
        if (this.coolfade >= 1.0f) {
            this.oldcolor = getCatColor();
        }
        if (this.dragging) {
            final int raznostX = mouseX - this.dragX - ThunderGui.thunderguiX;
            final int raznostY = mouseY - this.dragY - ThunderGui.thunderguiY;
            ThunderGui.thunderguiX = mouseX - this.dragX;
            ThunderGui.thunderguiY = mouseY - this.dragY;
            this.configlist.forEach(components -> components.setLocation(components.getX() + raznostX, components.getY() + raznostY));
            this.friendslist.forEach(components -> components.setLocation(components.getX() + raznostX, components.getY() + raznostY));
            this.components.forEach(components -> components.setLocation(components.getX() + raznostX, components.getY() + raznostY));
            TModuleButt.items.forEach(components -> components.setLocation(components.getX() + raznostX, components.getY() + raznostY));
        }
        RenderUtil.drawRect2(ThunderGui.thunderguiX + 10, ThunderGui.thunderguiY + 63, ThunderGui.thunderguiX + 117, ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY - 13, ThunderHackGui.getInstance().shadow.getValue().getRawColor());
        this.checkMouseWheel(mouseX);
        final ScaledResolution sr = new ScaledResolution(this.mc);
        BlurUtil.getInstance().blur((float)ThunderGui.thunderguiX, (float)ThunderGui.thunderguiY, (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 87), (float)(ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY), ThunderHackGui.getInstance().blurstr.getValue() / 5);
        RenderUtil.drawRect2(ThunderGui.thunderguiX, ThunderGui.thunderguiY, ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 87, ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY, new Color(downplatecolor.getRed(), downplatecolor.getGreen(), downplatecolor.getBlue(), 19).getRGB());
        RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 7), (float)(ThunderGui.thunderguiY + 5), (float)(ThunderGui.thunderguiX + 127), (float)(ThunderGui.thunderguiY + 43), ThunderHackGui.getInstance().thplate.getValue().getRawColor());
        RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 137), (float)(ThunderGui.thunderguiY + 5), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 87), (float)(ThunderGui.thunderguiY + 43), new Color(16777215).getRGB());
        if (this.head != null) {
            RenderUtil.drawCircleWithTexture((float)(ThunderGui.thunderguiX + 156), (float)(ThunderGui.thunderguiY + 24), 0, 360, 15.0f, this.head, new Color(-1).getRGB());
        }
        else {
            this.retryLoadHead();
        }
        FontRender.drawString2(this.mc.player.getName(), ThunderGui.thunderguiX + 182, ThunderGui.thunderguiY + 9, new Color(2763306).getRGB());
        FontRender.drawString3("current cfg: " + Thunderhack.configManager.currentcfg, ThunderGui.thunderguiX + 182, ThunderGui.thunderguiY + 28, new Color(657930).getRGB());
        if (this.timer.passedMs(30L)) {
            ++this.i;
            this.timer.reset();
        }
        final String w1 = "SNHRGHPKUXF";
        final String w2 = "TvVTMLFERJC";
        final String w3 = "THdTSGLHLBU";
        final String w4 = "THUtWVZMQVX";
        final String w5 = "THUNgGUDLUF";
        final String w6 = "THUNDgMZDUB";
        final String w7 = "THUNDEeBYZZY";
        final String w8 = "THUNDERjHJCH";
        final String w9 = "THUNDERHkKQG";
        final String w10 = "THUNDERHApK";
        final String w11 = "THUNDERHACu";
        final String w12 = "THUNDERHACK";
        String text = "";
        if (this.i == 0) {
            text = w1;
        }
        if (this.i == 1) {
            text = w2;
        }
        if (this.i == 2) {
            text = w3;
        }
        if (this.i == 3) {
            text = w4;
        }
        if (this.i == 4) {
            text = w5;
        }
        if (this.i == 5) {
            text = w6;
        }
        if (this.i == 6) {
            text = w7;
        }
        if (this.i == 7) {
            text = w8;
        }
        if (this.i == 8) {
            text = w9;
        }
        if (this.i == 9) {
            text = w10;
        }
        if (this.i == 10) {
            text = w11;
        }
        if (this.i >= 11) {
            text = w12;
        }
        FontRender.drawString2(text, ThunderGui.thunderguiX + 12, ThunderGui.thunderguiY + 16, -1);
        FontRender.drawString2("+", ThunderGui.thunderguiX + 22 + FontRender.getStringWidth("THUNDERHACK"), ThunderGui.thunderguiY + 16, PaletteHelper.astolfo(false, 1).getRGB());
        if (ThunderGui.currentCategory != Categories.COMBAT) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Combat", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62, -1);
            drawImage(this.combaticon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60), 15.0f, 15.0f, new Color(16316150));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77), getCatColor().getRGB());
            FontRender.drawString("Combat", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62, new Color(0).getRGB());
            drawImage(this.combaticon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60), 15.0f, 15.0f, new Color(657930));
        }
        if (ThunderGui.currentCategory != Categories.MOVEMENT) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 21), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 21), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Movement", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21, -1);
            drawImage(this.movementicon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21), 15.0f, 15.0f, new Color(16316150));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 21), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 21), getCatColor().getRGB());
            FontRender.drawString("Movement", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21, new Color(0).getRGB());
            drawImage(this.movementicon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.RENDER) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 42), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 42), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Render", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21, -1);
            drawImage(this.rendericon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21), 15.0f, 15.0f, new Color(16777215));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 42), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 42), getCatColor().getRGB());
            FontRender.drawString("Render", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21, new Color(0).getRGB());
            drawImage(this.rendericon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.MISC) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 63), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 63), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Misc", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21, -1);
            drawImage(this.miscicon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21), 15.0f, 15.0f, new Color(16316150));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 63), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 63), getCatColor().getRGB());
            FontRender.drawString("Misc", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21, new Color(0).getRGB());
            drawImage(this.miscicon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.PLAYER) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 84), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 84), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Player", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21 + 21, -1);
            drawImage(this.playericon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21 + 21), 15.0f, 15.0f, new Color(16316150));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 84), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 84), getCatColor().getRGB());
            FontRender.drawString("Player", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21 + 21, new Color(0).getRGB());
            drawImage(this.playericon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21 + 21), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.FUNNYGAME) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 105), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 105), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("FunnyGame", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21 + 21 + 21, -1);
            drawImage(this.funnygameicon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21 + 21 + 21), 15.0f, 15.0f, new Color(16777215));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 58 + 105), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 105), getCatColor().getRGB());
            FontRender.drawString("FunnyGame", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21 + 21 + 21, new Color(0).getRGB());
            drawImage(this.funnygameicon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21 + 21 + 21), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.CLIENT) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 184), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 126), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Client", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21 + 21 + 21 + 21, -1);
            drawImage(this.clienticon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21 + 21 + 21 + 21), 15.0f, 15.0f, new Color(16316150));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 10), (float)(ThunderGui.thunderguiY + 184), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 77 + 126), getCatColor().getRGB());
            FontRender.drawString("Client", ThunderGui.thunderguiX + 30 + 10, ThunderGui.thunderguiY + 62 + 21 + 21 + 21 + 21 + 21 + 21, new Color(0).getRGB());
            drawImage(this.clienticon, (float)(ThunderGui.thunderguiX + 12), (float)(ThunderGui.thunderguiY + 60 + 21 + 21 + 21 + 21 + 21 + 21), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.FRIENDS) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 226), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Friends", ThunderGui.thunderguiX + 40 + 10, ThunderGui.thunderguiY + 230, -1);
            drawImage(this.friendmanagericon, (float)(ThunderGui.thunderguiX + 22), (float)(ThunderGui.thunderguiY + 228), 15.0f, 15.0f, new Color(16777215));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 226), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245), getCatColor().getRGB());
            FontRender.drawString("Friends", ThunderGui.thunderguiX + 40 + 10, ThunderGui.thunderguiY + 230, new Color(0).getRGB());
            drawImage(this.friendmanagericon, (float)(ThunderGui.thunderguiX + 22), (float)(ThunderGui.thunderguiY + 228), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.CONFIGS) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 247), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245 + 21), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("Configs", ThunderGui.thunderguiX + 40 + 10, ThunderGui.thunderguiY + 251, -1);
            drawImage(this.configicon, (float)(ThunderGui.thunderguiX + 22), (float)(ThunderGui.thunderguiY + 249), 15.0f, 15.0f, new Color(16777215));
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 247), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245 + 21), getCatColor().getRGB());
            FontRender.drawString("Configs", ThunderGui.thunderguiX + 40 + 10, ThunderGui.thunderguiY + 251, new Color(0).getRGB());
            drawImage(this.configicon, (float)(ThunderGui.thunderguiX + 22), (float)(ThunderGui.thunderguiY + 249), 15.0f, 15.0f, new Color(0));
        }
        if (ThunderGui.currentCategory != Categories.HUD) {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 268), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245 + 21 + 21), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString("HUD", ThunderGui.thunderguiX + 40 + 10, ThunderGui.thunderguiY + 272, -1);
            this.mc.getTextureManager().bindTexture(this.hudicon);
            drawCompleteImage((float)(ThunderGui.thunderguiX + 22), (float)(ThunderGui.thunderguiY + 272), 14, 14);
        }
        else {
            RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 268), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245 + 21 + 21), getCatColor().getRGB());
            FontRender.drawString("HUD", ThunderGui.thunderguiX + 40 + 10, ThunderGui.thunderguiY + 272, new Color(0).getRGB());
            this.mc.getTextureManager().bindTexture(this.hudicon);
            drawCompleteImage2((float)(ThunderGui.thunderguiX + 22), (float)(ThunderGui.thunderguiY + 272), 14, 14);
        }
        if (ThunderGui.currentCategory != Categories.CONFIGS && ThunderGui.currentCategory != Categories.FRIENDS) {
            RenderUtil.glScissor((float)(ThunderGui.thunderguiX + 139), (float)(ThunderGui.thunderguiY + 58), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 87), (float)(ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY), sr);
        }
        else {
            RenderUtil.glScissor((float)(ThunderGui.thunderguiX + 139), (float)(ThunderGui.thunderguiY + 82), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 87), (float)(ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY), sr);
        }
        GL11.glEnable(3089);
        this.friendslist.forEach(friend -> friend.drawScreen(mouseX, mouseY, partialTicks));
        this.configlist.forEach(friend -> friend.drawScreen(mouseX, mouseY, partialTicks));
        TModuleButt.items.forEach(items -> items.drawScreen(mouseX, mouseY, partialTicks));
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
        GL11.glDisable(3089);
        if (ThunderGui.currentCategory != Categories.CONFIGS && ThunderGui.currentCategory != Categories.FRIENDS) {
            RenderUtil.drawSmoothRect((float)(290 + ThunderGui.thunderguiX), (float)(ThunderGui.thunderguiY + 58), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 87), (float)(69 + ThunderGui.thunderguiY), ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            FontRender.drawString3(this.whoasked(), 291 + ThunderGui.thunderguiX, ThunderGui.thunderguiY + 59, -1);
            Util.fr.drawString(this.countSettings(), 281 + ThunderGui.thunderguiX + FontRender.getStringWidth(this.whoasked()), ThunderGui.thunderguiY + 59, new Color(10132122).getRGB());
        }
        if (ThunderGui.currentCategory == Categories.FRIENDS) {
            RenderUtil.drawRect2(ThunderGui.thunderguiX + 137, ThunderGui.thunderguiY + 58, ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 87, 76 + ThunderGui.thunderguiY, ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            RenderUtil.drawRect2(ThunderGui.thunderguiX + 137, ThunderGui.thunderguiY + 79, ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 87, 81 + ThunderGui.thunderguiY, getCatColor().getRGB());
            if (!this.isHoveringItem((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), (float)mouseX, (float)mouseY)) {
                RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), new Color(13553358).getRGB());
                FontRender.drawString3("+ Add Friend", ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6, ThunderGui.thunderguiY + 63, new Color(4013373).getRGB());
            }
            else {
                RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), new Color(6579300).getRGB());
                FontRender.drawString3("+ Add Friend", ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6, ThunderGui.thunderguiY + 63, new Color(11974069).getRGB());
            }
            if (Objects.equals(this.addFriendLine, "")) {
                if (!this.friendlistening) {
                    FontRender.drawString("Type friend's name", ThunderGui.thunderguiX + 139, ThunderGui.thunderguiY + 60, new Color(-1).getRGB());
                }
                else {
                    FontRender.drawString("Type friend's name", ThunderGui.thunderguiX + 139, ThunderGui.thunderguiY + 60, new Color(6447714).getRGB());
                }
            }
            else {
                FontRender.drawString(this.addFriendLine + "_", ThunderGui.thunderguiX + 139, ThunderGui.thunderguiY + 60, new Color(-1).getRGB());
            }
        }
        if (ThunderGui.currentCategory == Categories.CONFIGS) {
            RenderUtil.drawRect2(ThunderGui.thunderguiX + 137, ThunderGui.thunderguiY + 58, ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 87, 76 + ThunderGui.thunderguiY, ThunderHackGui.getInstance().LeftsideColor.getValue().getRawColor());
            RenderUtil.drawRect2(ThunderGui.thunderguiX + 137, ThunderGui.thunderguiY + 79, ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 87, 81 + ThunderGui.thunderguiY, getCatColor().getRGB());
            if (!this.isHoveringItem((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), (float)mouseX, (float)mouseY)) {
                RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), new Color(13553358).getRGB());
                FontRender.drawString3("+ Add Config", ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6, ThunderGui.thunderguiY + 63, new Color(4013373).getRGB());
            }
            else {
                RenderUtil.drawSmoothRect((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), new Color(6579300).getRGB());
                FontRender.drawString3("+ Add Config", ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6, ThunderGui.thunderguiY + 63, new Color(11974069).getRGB());
            }
            if (Objects.equals(this.addConfigLine, "") || !this.configListening) {
                if (!this.configListening) {
                    FontRender.drawString("Type config name", ThunderGui.thunderguiX + 139, ThunderGui.thunderguiY + 60, new Color(-1).getRGB());
                }
                else {
                    FontRender.drawString("Type config name", ThunderGui.thunderguiX + 139, ThunderGui.thunderguiY + 60, new Color(6579300).getRGB());
                }
            }
            else {
                FontRender.drawString(this.addConfigLine + "_", ThunderGui.thunderguiX + 139, ThunderGui.thunderguiY + 60, new Color(-1).getRGB());
            }
        }
    }
    
    public String whoasked() {
        for (final TModuleButt module : this.components) {
            if (module.isSetting()) {
                return module.getName() + " settings";
            }
        }
        return "choose module";
    }
    
    public String countSettings() {
        for (final TModuleButt module : this.components) {
            if (module.isSetting()) {
                return "b: " + module.sbools + " i: " + module.sintegers + " f: " + module.sfloats + " c: " + module.scolors + " e: " + module.senums;
            }
        }
        return "  ";
    }
    
    public static Color getCatColor() {
        if (ThunderGui.currentCategory == Categories.COMBAT) {
            return new Color(16728660);
        }
        if (ThunderGui.currentCategory == Categories.MISC) {
            return new Color(12793343);
        }
        if (ThunderGui.currentCategory == Categories.PLAYER) {
            return new Color(16621893);
        }
        if (ThunderGui.currentCategory == Categories.MOVEMENT) {
            return new Color(1572863);
        }
        if (ThunderGui.currentCategory == Categories.FUNNYGAME) {
            return new Color(13958935);
        }
        if (ThunderGui.currentCategory == Categories.RENDER) {
            return new Color(4951295);
        }
        if (ThunderGui.currentCategory == Categories.CLIENT) {
            return new Color(4718000);
        }
        if (ThunderGui.currentCategory == Categories.FRIENDS) {
            return new Color(64042);
        }
        if (ThunderGui.currentCategory == Categories.HUD) {
            return new Color(16777215);
        }
        if (ThunderGui.currentCategory == Categories.CONFIGS) {
            return new Color(14354943);
        }
        return new Color(51086);
    }
    
    public void resetThunderGui(final Categories cat) {
        this.friendlistening = false;
        this.configListening = false;
        this.coolfade = 0.0f;
        ThunderGui.currentCategory = cat;
        getInstance().components.forEach(component -> component.setSetting(false));
        getInstance().components.forEach(component -> TModuleButt.items.clear());
        this.friendslist.clear();
        this.configlist.clear();
        this.components.clear();
        this.load();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        if (this.isHoveringItem((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 6), (float)(ThunderGui.thunderguiY + 62), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX - 7 + 80), (float)(72 + ThunderGui.thunderguiY), (float)mouseX, (float)mouseY)) {
            if (ThunderGui.currentCategory == Categories.FRIENDS && !Objects.equals(this.addFriendLine, "") && this.friendlistening) {
                Thunderhack.friendManager.addFriend(this.addFriendLine);
                this.resetThunderGui(ThunderGui.currentCategory);
                this.addFriendLine = "";
            }
            if (ThunderGui.currentCategory == Categories.CONFIGS && !Objects.equals(this.addConfigLine, "") && this.configListening) {
                Thunderhack.configManager.saveConfig(this.addConfigLine);
                this.resetThunderGui(ThunderGui.currentCategory);
                this.addConfigLine = "";
            }
        }
        if (ThunderGui.currentCategory == Categories.FRIENDS) {
            this.friendlistening = (mouseX >= ThunderGui.thunderguiX + 137 && mouseX <= ThunderGui.thunderguiX + 137 + 137 && mouseY >= ThunderGui.thunderguiY + 58 && mouseY <= 76 + ThunderGui.thunderguiY);
        }
        if (ThunderGui.currentCategory == Categories.CONFIGS) {
            this.configListening = (mouseX >= ThunderGui.thunderguiX + 137 && mouseX <= ThunderGui.thunderguiX + 137 + 137 && mouseY >= ThunderGui.thunderguiY + 58 && mouseY <= 76 + ThunderGui.thunderguiY);
        }
        if (this.isHoveringLogo(mouseX, mouseY)) {
            this.dragging = true;
            this.dragX = mouseX - ThunderGui.thunderguiX;
            this.dragY = mouseY - ThunderGui.thunderguiY;
        }
        this.friendslist.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
        this.configlist.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
        TModuleButt.items.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 && mouseY <= ThunderGui.thunderguiY + 77) {
            this.resetThunderGui(Categories.COMBAT);
        }
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 + 21 && mouseY <= ThunderGui.thunderguiY + 77 + 21) {
            this.resetThunderGui(Categories.MOVEMENT);
        }
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 + 21 + 21 && mouseY <= ThunderGui.thunderguiY + 77 + 21 + 21) {
            this.resetThunderGui(Categories.RENDER);
        }
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 + 21 + 21 + 21 && mouseY <= ThunderGui.thunderguiY + 77 + 21 + 21 + 21) {
            this.resetThunderGui(Categories.MISC);
        }
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 + 21 + 21 + 21 + 21 && mouseY <= ThunderGui.thunderguiY + 77 + 21 + 21 + 21 + 21) {
            this.resetThunderGui(Categories.PLAYER);
        }
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 + 21 + 21 + 21 + 21 + 21 && mouseY <= ThunderGui.thunderguiY + 77 + 21 + 21 + 21 + 21 + 21) {
            this.resetThunderGui(Categories.FUNNYGAME);
        }
        if (mouseX >= ThunderGui.thunderguiX + 10 && mouseX <= ThunderGui.thunderguiX + 127 && mouseY >= ThunderGui.thunderguiY + 58 + 21 + 21 + 21 + 21 + 21 + 21 && mouseY <= ThunderGui.thunderguiY + 77 + 21 + 21 + 21 + 21 + 21 + 21) {
            this.resetThunderGui(Categories.CLIENT);
        }
        if (this.isHoveringItem((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 226), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245), (float)mouseX, (float)mouseY)) {
            this.resetThunderGui(Categories.FRIENDS);
        }
        if (this.isHoveringItem((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 247), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245 + 21), (float)mouseX, (float)mouseY)) {
            this.resetThunderGui(Categories.CONFIGS);
        }
        if (this.isHoveringItem((float)(ThunderGui.thunderguiX + 20), (float)(ThunderGui.thunderguiY + 268), (float)(ThunderGui.thunderguiX + 120), (float)(ThunderGui.thunderguiY + 245 + 21 + 21), (float)mouseX, (float)mouseY)) {
            this.resetThunderGui(Categories.HUD);
        }
        if (this.isHoveringItem((float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 35), (float)(81 + ThunderGui.thunderguiY), (float)(ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 73), (float)(ThunderGui.thunderguiscaleY + ThunderGui.thunderguiY), (float)mouseX, (float)mouseY) && (ThunderGui.currentCategory == Categories.CONFIGS || ThunderGui.currentCategory == Categories.FRIENDS)) {
            this.resetThunderGui(ThunderGui.currentCategory);
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.dragging = false;
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public ArrayList<TModuleButt> getComponents() {
        return this.components;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        TModuleButt.items.forEach(components -> components.onKeyTyped(typedChar, keyCode));
        if (this.friendlistening) {
            if (keyCode == 42) {
                return;
            }
            if (keyCode == 54) {
                return;
            }
            if (keyCode == 1) {
                this.addFriendLine = "";
                this.friendlistening = false;
                return;
            }
            if (keyCode == 14) {
                this.addFriendLine = removeLastChar(this.addFriendLine);
                return;
            }
            if (keyCode == 28) {
                Thunderhack.friendManager.addFriend(this.addFriendLine);
                this.addFriendLine = "";
                this.friendlistening = false;
                this.resetThunderGui(ThunderGui.currentCategory);
                return;
            }
            this.addFriendLine += typedChar;
        }
        if (this.configListening) {
            if (keyCode == 42) {
                return;
            }
            if (keyCode == 54) {
                return;
            }
            if (keyCode == 1) {
                this.addConfigLine = "";
                this.configListening = false;
                return;
            }
            if (keyCode == 14) {
                this.addConfigLine = removeLastChar(this.addConfigLine);
                return;
            }
            if (keyCode == 28) {
                Thunderhack.configManager.saveConfig(this.addConfigLine);
                this.addConfigLine = "";
                this.configListening = false;
                this.resetThunderGui(ThunderGui.currentCategory);
                return;
            }
            this.addConfigLine += typedChar;
        }
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public void checkMouseWheel(final int mouseX) {
        final int dWheel = Mouse.getDWheel();
        if (ThunderGui.currentCategory != Categories.CONFIGS && ThunderGui.currentCategory != Categories.FRIENDS) {
            if (mouseX >= ThunderGui.thunderguiX + 140 && mouseX <= ThunderGui.thunderguiX + 140 + 127) {
                if (dWheel < 0) {
                    if (this.components.get(this.components.size() - 1).getY() + 30.0f < ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY) {
                        return;
                    }
                    this.components.forEach(component -> component.setY(component.getY() - 5.0f));
                    this.anim = -10;
                }
                else if (dWheel > 0) {
                    if (this.components.get(0).getY() + 15.0f > ThunderGui.thunderguiY + 58) {
                        return;
                    }
                    this.components.forEach(component -> component.setY(component.getY() + 5.0f));
                    this.anim = 10;
                }
                if (dWheel == 0) {
                    if (this.anim > 0) {
                        final int finalAnim = this.anim;
                        this.components.forEach(component -> component.setY(component.getY() + finalAnim));
                        --this.anim;
                    }
                    if (this.anim < 0) {
                        final int finalAnim = this.anim;
                        final int finalAnim2;
                        this.components.forEach(component -> component.setY(component.getY() + finalAnim2));
                        ++this.anim;
                    }
                }
            }
            else {
                if (dWheel < 0) {
                    TModuleButt.items.forEach(component -> component.setY(component.getY() - 5.0f));
                    this.anim2 = -10;
                }
                else if (dWheel > 0) {
                    TModuleButt.items.forEach(component -> component.setY(component.getY() + 5.0f));
                    this.anim2 = 10;
                }
                if (dWheel == 0) {
                    if (this.anim2 > 0) {
                        final int finalAnim = this.anim2;
                        final int finalAnim3;
                        TModuleButt.items.forEach(component -> component.setY(component.getY() + finalAnim3));
                        --this.anim2;
                    }
                    if (this.anim2 < 0) {
                        final int finalAnim = this.anim2;
                        final int finalAnim4;
                        TModuleButt.items.forEach(component -> component.setY(component.getY() + finalAnim4));
                        ++this.anim2;
                    }
                }
            }
        }
        else if (ThunderGui.currentCategory == Categories.FRIENDS) {
            try {
                if (dWheel < 0) {
                    if (this.friendslist.get(this.friendslist.size() - 1).getY() + 30.0f < ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY) {
                        return;
                    }
                    this.friendslist.forEach(component -> component.setY(component.getY() - 5.0f));
                    this.anim = -10;
                    this.totalwheel -= 15;
                }
                else if (dWheel > 0) {
                    if (this.friendslist.get(0).getY() + 15.0f > ThunderGui.thunderguiY + 58) {
                        return;
                    }
                    this.friendslist.forEach(component -> component.setY(component.getY() + 5.0f));
                    this.anim = 10;
                    this.totalwheel += 15;
                }
                if (dWheel == 0) {
                    if (this.anim > 0) {
                        final int finalAnim = this.anim;
                        final int finalAnim5;
                        this.friendslist.forEach(component -> component.setY(component.getY() + finalAnim5));
                        --this.anim;
                    }
                    if (this.anim < 0) {
                        final int finalAnim = this.anim;
                        final int finalAnim6;
                        this.friendslist.forEach(component -> component.setY(component.getY() + finalAnim6));
                        ++this.anim;
                    }
                }
            }
            catch (Exception ex) {}
        }
        else {
            try {
                if (dWheel < 0) {
                    if (this.configlist.get(this.configlist.size() - 1).getY() + 30.0f < ThunderGui.thunderguiY + ThunderGui.thunderguiscaleY) {
                        return;
                    }
                    this.configlist.forEach(component -> component.setY(component.getY() - 5.0f));
                    this.anim = -10;
                    this.totalwheel -= 15;
                }
                else if (dWheel > 0) {
                    if (this.configlist.get(0).getY() + 15.0f > ThunderGui.thunderguiY + 58) {
                        return;
                    }
                    this.configlist.forEach(component -> component.setY(component.getY() + 5.0f));
                    this.anim = 10;
                    this.totalwheel += 15;
                }
                if (dWheel == 0) {
                    if (this.anim > 0) {
                        final int finalAnim = this.anim;
                        final int finalAnim7;
                        this.configlist.forEach(component -> component.setY(component.getY() + finalAnim7));
                        --this.anim;
                    }
                    if (this.anim < 0) {
                        final int finalAnim = this.anim;
                        final int finalAnim8;
                        this.configlist.forEach(component -> component.setY(component.getY() + finalAnim8));
                        ++this.anim;
                    }
                }
            }
            catch (Exception ex2) {}
        }
    }
    
    public void onGuiClosed() {
        this.i = 0;
        Thunderhack.moduleManager.getModuleByClass(ThunderHackGui.class).toggle();
    }
    
    public boolean isHoveringLogo(final int mouseX, final int mouseY) {
        return mouseX >= ThunderGui.thunderguiX + 7 && mouseX <= ThunderGui.thunderguiX + ThunderGui.thunderguiscaleX + 87 && mouseY >= ThunderGui.thunderguiY + 5 && mouseY <= ThunderGui.thunderguiY + 43;
    }
    
    public boolean isHoveringItem(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        setColor(new Color(16185078).getRGB());
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
    
    public static void drawCompleteImage2(final float posX, final float posY, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        setColor(new Color(10790052).getRGB());
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
    
    public static void setColor(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    public static void drawImage(final ResourceLocation resourceLocation, final float x, final float y, final float width, final float height, final Color color) {
        GL11.glPushMatrix();
        setColor(color.getRGB());
        Util.mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, 0.0f, 0.0f, (int)width, (int)height, width, height);
        GL11.glPopMatrix();
    }
    
    public static void setColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255.0f;
        final float green = color.getGreen() / 255.0f;
        final float blue = color.getBlue() / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    static {
        ThunderGui.thunderguiX = 150;
        ThunderGui.thunderguiY = 100;
        ThunderGui.thunderguiscaleX = 500;
        ThunderGui.thunderguiscaleY = 300;
        ThunderGui.currentCategory = Categories.COMBAT;
        ThunderGui.INSTANCE = new ThunderGui();
    }
    
    public enum Categories
    {
        RENDER, 
        MISC, 
        HUD, 
        COMBAT, 
        PLAYER, 
        CLIENT, 
        MOVEMENT, 
        FUNNYGAME, 
        FRIENDS, 
        CONFIGS;
    }
}
