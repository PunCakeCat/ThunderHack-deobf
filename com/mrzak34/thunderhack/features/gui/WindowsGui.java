//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.gui;

import java.awt.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.features.gui.font.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.manager.*;
import com.mrzak34.thunderhack.util.*;
import java.util.stream.*;
import net.minecraft.client.gui.*;
import java.util.*;
import com.mrzak34.thunderhack.features.command.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.io.*;

public class WindowsGui extends GuiScreen
{
    private static WindowsGui windowsgui;
    private static WindowsGui INSTANCE;
    protected float x;
    protected float y;
    private float dragX;
    private float dragY;
    private boolean dragging;
    private float resizeX;
    private float resizeY;
    private boolean resizing;
    protected float width;
    protected float height;
    public String info;
    public String title;
    protected float x2;
    protected float y2;
    private float dragX2;
    private float dragY2;
    private boolean dragging2;
    private float resizeX2;
    private float resizeY2;
    private boolean resizing2;
    protected float width2;
    protected float height2;
    public String info2;
    public String title2;
    Color color1;
    int limit;
    boolean addingFriend;
    boolean addingCFG;
    String AddingCFGName;
    ArrayList<String> cats;
    HashMap<Integer, String> hashmap;
    HashMap<Integer, String> Confighashmap;
    public int wheely2;
    public int wheely;
    public String addingFriendName;
    
    public WindowsGui() {
        this.width = 130.0f;
        this.height = 100.0f;
        this.info = "";
        this.title = "Friend Manager";
        this.x2 = 200.0f;
        this.width2 = 130.0f;
        this.height2 = 100.0f;
        this.info2 = "";
        this.title2 = "Config Manager";
        this.color1 = ClickGui.getInstance().topColor.getValue().getColorObject();
        this.limit = 0;
        this.AddingCFGName = "";
        this.cats = new ArrayList<String>();
        this.hashmap = new HashMap<Integer, String>();
        this.Confighashmap = new HashMap<Integer, String>();
        this.wheely2 = 0;
        this.wheely = 0;
        this.addingFriendName = "";
        this.setInstance();
        this.load();
    }
    
    public static WindowsGui getInstance() {
        if (WindowsGui.INSTANCE == null) {
            WindowsGui.INSTANCE = new WindowsGui();
        }
        return WindowsGui.INSTANCE;
    }
    
    public static WindowsGui getWindowsGui() {
        return getInstance();
    }
    
    private void setInstance() {
        WindowsGui.INSTANCE = this;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.checkMouseWheel(mouseX, mouseY);
        GL11.glPushMatrix();
        if (this.dragging) {
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
        }
        if (this.dragging2) {
            this.x2 = mouseX - this.dragX2;
            this.y2 = mouseY - this.dragY2;
        }
        final float infoHeight = FontRendererWrapper.getFontHeight() * 0.66f;
        RenderUtil.drawShadowedOutlineRectRB(this.x, this.y, this.width, this.height + infoHeight, 1610612736, 5.0f);
        final Color cl = new Color(-14474452);
        final Color cl2 = new Color(-15263971);
        final Color cl3 = new Color(-15263971);
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + 1.5f, this.color1.getRGB());
        RenderUtil.drawRect(this.x, this.y + 1.5f, this.x + this.width, this.y + FontRendererWrapper.getFontHeight() + 3.0f, -15263971);
        RenderUtil.drawRect(this.x, this.y + FontRendererWrapper.getFontHeight() + 3.0f, this.width + this.x, this.y + this.height + infoHeight, -14474452);
        RenderUtil.drawRect(this.x, this.y + this.height - 8.0f, this.x + this.width, this.y + this.height + 6.0f, -15263971);
        FontRendererWrapper.drawStringWithShadow(this.title, this.x + 2.0f, this.y + 2.0f, this.color1.brighter().brighter().getRGB());
        int i = 0;
        for (final FriendManager.Friend friend : Thunderhack.friendManager.getFriends()) {
            this.FriendNigger(friend.getUsername(), i);
            ++i;
            ++this.limit;
        }
        if (this.addingFriend) {
            RenderUtil.drawRect(this.x + 5.0f - 0.3f, this.y + this.height - 5.0f - 0.3f, this.x + 90.0f + 0.3f, this.y + this.height + 4.0f + 0.3f, this.color1.getRGB());
        }
        RenderUtil.drawRect(this.x + 5.0f, this.y + this.height - 5.0f, this.x + 90.0f, this.y + this.height + 4.0f, new Color(-366006481, true).getRGB());
        if (this.addingFriend && this.addingFriendName != null && !this.addingFriendName.equals("")) {
            Util.fr.drawStringWithShadow(this.addingFriendName + "_", this.x + 6.0f, this.y + this.height - 5.8f, -1);
        }
        RenderUtil.drawRect(this.width + this.x - 31.0f, this.y + this.height - 5.0f, this.width + this.x - 15.0f, this.y + this.height + 4.0f, new Color(-366006481, true).getRGB());
        Util.fr.drawStringWithShadow("Add", this.width + this.x - 29.5f, this.y + this.height - 4.0f, -1);
        RenderUtil.drawLine2d(this.x + this.width - 4.0f, this.y + this.height + infoHeight - 1.0f, this.x + this.width - 1.0f, this.y + this.height + infoHeight - 4.0f, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.x + this.width - 7.0f, this.y + this.height + infoHeight - 1.0f, this.x + this.width - 1.0f, this.y + this.height + infoHeight - 7.0f, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.x + this.width - 10.0f, this.y + this.height + infoHeight - 1.0f, this.x + this.width - 1.0f, this.y + this.height + infoHeight - 10.0f, 1.0f, this.color1);
        RenderUtil.drawShadowedOutlineRectRB(this.x2, this.y2, this.width2, this.height2, 1610612736, 5.0f);
        RenderUtil.drawRect(this.x2, this.y2, this.x2 + this.width2, this.y2 + 1.5f, this.color1.getRGB());
        RenderUtil.drawRect(this.x2, this.y2 + 1.5f, this.x2 + this.width2, this.y2 + FontRendererWrapper.getFontHeight() + 3.0f, -15263971);
        RenderUtil.drawRect(this.x2, this.y2 + FontRendererWrapper.getFontHeight() + 3.0f, this.width2 + this.x2, this.y2 + this.height2, -14474452);
        RenderUtil.drawRect(this.x2, this.y2 + this.height2 - 8.0f, this.x2 + this.width2, this.y2 + this.height2 + 6.0f, -15263971);
        FontRendererWrapper.drawStringWithShadow(this.title2, this.x2 + 2.0f, this.y2 + 2.0f, this.color1.brighter().brighter().getRGB());
        final File file = new File("ThunderHack/");
        final List<File> directories = Arrays.stream(file.listFiles()).filter(File::isDirectory).filter(f -> !f.getName().equals("util")).collect((Collector<? super File, ?, List<File>>)Collectors.toList());
        int i2 = 0;
        for (final File file2 : directories) {
            if (!Objects.equals(file2.getName(), "customimage") && !Objects.equals(file2.getName(), "pvp") && !Objects.equals(file2.getName(), "notebot") && !Objects.equals(file2.getName(), "customimage") && !Objects.equals(file2.getName(), "kits") && !this.cats.contains(file2.getName())) {
                this.ConfigNigger(file2.getName(), i2);
                ++i2;
            }
        }
        if (this.addingCFG) {
            RenderUtil.drawRect(this.x2 + 5.0f - 0.3f, this.y2 + this.height2 - 5.0f - 0.3f, this.x2 + 90.0f + 0.3f, this.y2 + this.height2 + 4.0f + 0.3f, this.color1.getRGB());
        }
        RenderUtil.drawRect(this.x2 + 5.0f, this.y2 + this.height2 - 5.0f, this.x2 + 90.0f, this.y2 + this.height2 + 4.0f, new Color(-366006481, true).getRGB());
        if (this.addingCFG && this.AddingCFGName != null && !this.AddingCFGName.equals("")) {
            Util.fr.drawStringWithShadow(this.AddingCFGName + "_", this.x2 + 6.0f, this.y2 + this.height2 - 6.0f, -1);
        }
        RenderUtil.drawRect(this.width2 + this.x2 - 33.0f, this.y2 + this.height2 - 5.0f, this.width2 + this.x2 - 13.0f, this.y2 + this.height2 + 4.0f, new Color(-366006481, true).getRGB());
        Util.fr.drawStringWithShadow("Save", this.width2 + this.x2 - 30.0f, this.y2 + this.height2 - 3.8f, -1);
        RenderUtil.drawLine2d(this.x2 + this.width2 - 4.0f, this.y2 + this.height2 + infoHeight - 1.0f, this.x2 + this.width2 - 1.0f, this.y2 + this.height2 + infoHeight - 4.0f, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.x2 + this.width2 - 7.0f, this.y2 + this.height2 + infoHeight - 1.0f, this.x2 + this.width2 - 1.0f, this.y2 + this.height2 + infoHeight - 7.0f, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.x2 + this.width2 - 10.0f, this.y2 + this.height2 + infoHeight - 1.0f, this.x2 + this.width2 - 1.0f, this.y2 + this.height2 + infoHeight - 10.0f, 1.0f, this.color1);
        if (this.resizing2) {
            this.width2 = mouseX - this.x2 + this.resizeX2;
            this.height2 = mouseY - this.y2 + this.resizeY2 - infoHeight;
        }
        if (this.resizing) {
            this.width = mouseX - this.x + this.resizeX;
            this.height = mouseY - this.y + this.resizeY - infoHeight;
        }
        GL11.glPopMatrix();
    }
    
    public void ConfigNigger(final String name, final int position) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        RenderUtil.glScissor(this.x2, this.y2 + FontRendererWrapper.getFontHeight() + 3.0f, this.width2 + this.x2, this.y2 + this.height2 - 10.0f, sr);
        GL11.glEnable(3089);
        Util.fr.drawStringWithShadow(name, this.x2 + 7.0f, this.y2 + 12.0f + 15 * position + this.wheely2, -1);
        RenderUtil.drawSmoothRect(this.width2 + this.x2 - 15.0f, this.y2 + 12.0f + 15 * position + this.wheely2, this.width2 + this.x2 - 5.0f, 10.0f + (this.y2 + 12.0f + 15 * position) + this.wheely2, new Color(-366006481, true).getRGB());
        RenderUtil.drawSmoothRect(this.width2 + this.x2 - 30.0f, this.y2 + 12.0f + 15 * position + this.wheely2, this.width2 + this.x2 - 20.0f + 1.0f, 10.0f + (this.y2 + 12.0f + 15 * position) + this.wheely2, new Color(572569896, true).getRGB());
        Util.fr.drawStringWithShadow("set", this.width2 + this.x2 - 29.7f, this.y2 + 12.0f + 15 * position + this.wheely2 + 0.4f, -1);
        RenderUtil.drawLine2d(this.width2 + this.x2 - 15.0f + 0.3f, this.y2 + 12.0f + 15 * position + this.wheely2 + 0.3f, this.width2 + this.x2 - 5.0f - 0.3f, 10.0f + (this.y2 + 12.0f + 15 * position) + this.wheely2 - 0.3f, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.width2 + this.x2 - 15.0f + 0.3f, 10.0f + (this.y2 + 12.0f + 15 * position) + this.wheely2 + 0.3f, this.width2 + this.x2 - 5.0f - 0.3f, this.y2 + 12.0f + 15 * position + this.wheely2 - 0.3f, 1.0f, this.color1);
        GL11.glDisable(3089);
        this.Confighashmap.put(position, name);
    }
    
    public void FriendNigger(final String name, final int position) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        RenderUtil.glScissor(this.x, this.y + FontRendererWrapper.getFontHeight() + 3.0f, this.width + this.x, this.y + this.height - 10.0f, sr);
        GL11.glEnable(3089);
        Util.fr.drawStringWithShadow(name, this.x + 7.0f, this.y + 12.0f + 15 * position + this.wheely, -1);
        RenderUtil.drawSmoothRect(this.width + this.x - 15.0f, this.y + 12.0f + 15 * position + this.wheely, this.width + this.x - 5.0f, 10.0f + (this.y + 12.0f + 15 * position) + this.wheely, new Color(-366006481, true).getRGB());
        RenderUtil.drawLine2d(this.width + this.x - 15.0f + 0.3f, this.y + 12.0f + 15 * position + this.wheely + 0.3f, this.width + this.x - 5.0f - 0.3f, 10.0f + (this.y + 12.0f + 15 * position) + this.wheely - 0.3f, 1.0f, this.color1);
        RenderUtil.drawLine2d(this.width + this.x - 15.0f + 0.3f, 10.0f + (this.y + 12.0f + 15 * position) + this.wheely + 0.3f, this.width + this.x - 5.0f - 0.3f, this.y + 12.0f + 15 * position + this.wheely - 0.3f, 1.0f, this.color1);
        GL11.glDisable(3089);
        this.hashmap.put(position, name);
    }
    
    private void load() {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        for (final Map.Entry<Integer, String> entry : this.hashmap.entrySet()) {
            final Integer blockPos = entry.getKey();
            final String names = entry.getValue();
            if (mouseY >= this.y + 12.0f + 15 * blockPos + this.wheely && mouseY <= 10.0f + (this.y + 12.0f + 15 * blockPos) + this.wheely && mouseX >= this.width + this.x - 15.0f && mouseX <= this.width + this.x - 5.0f && (mouseX < this.width + this.x - 30.0f || mouseX > this.width + this.x - 15.0f || mouseY < this.y + this.height - 5.0f || mouseY > this.y + this.height + 4.0f)) {
                Thunderhack.friendManager.removeFriend(names);
            }
        }
        for (final Map.Entry<Integer, String> entry : this.Confighashmap.entrySet()) {
            final Integer blockPos = entry.getKey();
            final String names = entry.getValue();
            if (mouseY >= this.y2 + 12.0f + 15 * blockPos + this.wheely2 && mouseY <= 10.0f + (this.y2 + 12.0f + 15 * blockPos) + this.wheely2 && mouseX >= this.width2 + this.x2 - 29.5f && mouseX <= this.width2 + this.x2 - 20.0f + 1.0f && (mouseX < this.width2 + this.x2 - 80.0f || mouseX > this.width2 + this.x2 || mouseY < this.y2 + this.height2 - 5.0f || mouseY > this.y2 + this.height2 + 4.0f)) {
                Thunderhack.configManager.loadConfig(names);
                Command.sendMessage("\u0417\u0430\u0433\u0440\u0443\u0436\u0435\u043d \u043a\u043e\u043d\u0444\u0438\u0433 " + names + "!");
            }
            if (mouseY >= this.y2 + 12.0f + 15 * blockPos + this.wheely2 && mouseY <= 10.0f + (this.y2 + 12.0f + 15 * blockPos) + this.wheely2 && mouseX >= this.width2 + this.x2 - 15.0f && mouseX <= this.width2 + this.x2 - 5.0f && (mouseX < this.width2 + this.x2 - 80.0f || mouseX > this.width2 + this.x2 || mouseY < this.y2 + this.height2 - 5.0f || mouseY > this.y2 + this.height2 + 4.0f)) {
                this.cats.add(names);
                Command.sendMessage("\u041a\u043e\u043d\u0444\u0438\u0433 " + names + "\u0443\u0434\u0430\u043b\u0435\u043d!");
            }
        }
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            this.dragging = true;
            if (this.dragging) {
                this.dragX = mouseX - this.x;
                this.dragY = mouseY - this.y;
            }
        }
        if (mouseX < this.x2 || mouseX > this.x2 + this.width2 || mouseY < this.y2 || mouseY > this.y2 + this.height2) {
            this.addingCFG = false;
        }
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            this.addingFriend = false;
        }
        if (mouseX >= this.x2 && mouseX <= this.x2 + this.width2 && mouseY >= this.y2 && mouseY <= this.y2 + FontRendererWrapper.getFontHeight() + 3.0f) {
            this.dragging2 = true;
            if (this.dragging2) {
                this.dragX2 = mouseX - this.x2;
                this.dragY2 = mouseY - this.y2;
            }
        }
        if (mouseX >= this.x + 5.0f && mouseX <= this.x + 90.0f && mouseY >= this.y + this.height - 5.0f && mouseY <= this.y + this.height + 4.0f) {
            this.addingFriend = true;
        }
        if (mouseX >= this.x2 + 5.0f && mouseX <= this.x2 + 90.0f && mouseY >= this.y2 + this.height2 - 5.0f && mouseY <= this.y2 + this.height2 + 4.0f) {
            this.addingCFG = true;
        }
        if (mouseX >= this.width + this.x - 30.0f && mouseX <= this.width + this.x - 15.0f && mouseY >= this.y + this.height - 5.0f && mouseY <= this.y + this.height + 4.0f && this.addingFriend && this.addingFriendName != null && !this.addingFriendName.equals("")) {
            Thunderhack.friendManager.addFriend(this.addingFriendName);
        }
        if (mouseX >= this.width2 + this.x2 - 30.0f && mouseX <= this.width2 + this.x2 - 15.0f && mouseY >= this.y2 + this.height2 - 5.0f && mouseY <= this.y2 + this.height2 + 4.0f && this.addingCFG && this.AddingCFGName != null && !this.AddingCFGName.equals("")) {
            Thunderhack.configManager.saveConfig(this.AddingCFGName);
        }
        if (mouseX >= this.x + this.width - 10.0f && mouseX <= this.x + this.width - 1.0f && mouseY >= this.y + this.height + FontRendererWrapper.getFontHeight() * 0.75f - 10.0f && mouseY <= this.y + this.height + FontRendererWrapper.getFontHeight() * 0.75f - 1.0f) {
            this.resizeX = mouseX - (this.x + this.width - 10.0f);
            this.resizeY = mouseY - (this.y + this.height + FontRendererWrapper.getFontHeight() * 0.75f - 10.0f);
            this.resizing = true;
        }
        if (mouseX >= this.x2 + this.width2 - 10.0f && mouseX <= this.x2 + this.width2 - 1.0f && mouseY >= this.y2 + this.height2 + FontRendererWrapper.getFontHeight() * 0.75f - 10.0f && mouseY <= this.y + this.height + FontRendererWrapper.getFontHeight() * 0.75f - 1.0f) {
            this.resizeX2 = mouseX - (this.x2 + this.width2 - 10.0f);
            this.resizeY2 = mouseY - (this.y2 + this.height2 + FontRendererWrapper.getFontHeight() * 0.75f - 10.0f);
            this.resizing2 = true;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.dragging = false;
        this.resizing = false;
        this.dragging2 = false;
        this.resizing2 = false;
    }
    
    public void checkMouseWheel(final int mouseX, final int mouseY) {
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height) {
            final int dWheel = Mouse.getDWheel();
            if (dWheel < 0) {
                this.wheely -= 5;
            }
            else if (dWheel > 0) {
                this.wheely += 5;
            }
        }
        if (mouseX >= this.x2 && mouseX <= this.x2 + this.width2 && mouseY >= this.y2 && mouseY <= this.y2 + this.height2) {
            final int dWheel = Mouse.getDWheel();
            if (dWheel < 0) {
                this.wheely2 -= 5;
            }
            else if (dWheel > 0) {
                this.wheely2 += 5;
            }
        }
    }
    
    public static String removeLastChar(final String s) {
        return (s == null || s.length() == 0) ? null : s.substring(0, s.length() - 1);
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (this.addingCFG) {
            final Bind bind = new Bind(keyCode);
            if (bind.toString().equalsIgnoreCase("Enter")) {
                if (this.addingFriend && this.addingFriendName != null && !this.addingFriendName.equals("")) {
                    Thunderhack.friendManager.addFriend(this.addingFriendName);
                }
                if (this.addingCFG && this.AddingCFGName != null && !this.AddingCFGName.equals("")) {
                    Thunderhack.configManager.saveConfig(this.AddingCFGName);
                }
                return;
            }
            if (bind.toString().equalsIgnoreCase("RShift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("LShift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("R_Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("L_Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Back")) {
                if (removeLastChar(this.AddingCFGName) != null) {
                    this.AddingCFGName = removeLastChar(this.AddingCFGName);
                }
                else {
                    this.AddingCFGName = "";
                }
            }
            if (bind.toString().equalsIgnoreCase("Escape")) {
                this.AddingCFGName = "";
            }
            else if (!bind.toString().equalsIgnoreCase("Back")) {
                this.AddingCFGName += typedChar;
            }
        }
        else {
            this.AddingCFGName = "";
        }
        if (this.addingFriend) {
            final Bind bind = new Bind(keyCode);
            if (bind.toString().equalsIgnoreCase("RShift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("LShift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("R_Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("L_Shift")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Back")) {
                if (removeLastChar(this.addingFriendName) != null) {
                    this.addingFriendName = removeLastChar(this.addingFriendName);
                }
                else {
                    this.addingFriendName = "";
                }
            }
            if (bind.toString().equalsIgnoreCase("Escape")) {
                this.addingFriendName = "";
            }
            else if (!bind.toString().equalsIgnoreCase("Back")) {
                this.addingFriendName += typedChar;
            }
        }
        else {
            this.addingFriendName = "";
        }
    }
    
    static {
        WindowsGui.INSTANCE = new WindowsGui();
    }
}
