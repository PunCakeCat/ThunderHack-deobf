//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.awt.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mojang.realmsclient.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.*;

public class FontMod extends Module
{
    private static FontMod INSTANCE;
    public Setting<String> fontName;
    public Setting<Boolean> antiAlias;
    public Setting<Boolean> fractionalMetrics;
    public Setting<Integer> fontSize;
    public Setting<Integer> fontStyle;
    private boolean reloadFont;
    
    public FontMod() {
        super("CustomFont", "\u043a\u0430\u0441\u0442\u043e\u043c\u0444\u043e\u043d\u0442 \u0434\u043b\u044f \u0441\u0442\u0430\u0440\u043e\u0433\u043e-\u043a\u043b\u0438\u043a \u0433\u0443\u0438", Category.CLIENT, true, false, false);
        this.fontName = (Setting<String>)this.register(new Setting("FontName", (T)"Arial"));
        this.antiAlias = (Setting<Boolean>)this.register(new Setting("AntiAlias", (T)true, "Smoother font."));
        this.fractionalMetrics = (Setting<Boolean>)this.register(new Setting("Metrics", (T)true, "Thinner font."));
        this.fontSize = (Setting<Integer>)this.register(new Setting("Size", (T)18, (T)12, (T)30, "Size of the font."));
        this.fontStyle = (Setting<Integer>)this.register(new Setting("Style", (T)0, (T)0, (T)3, "Style of the font."));
        this.reloadFont = false;
        this.setInstance();
    }
    
    public static FontMod getInstance() {
        if (FontMod.INSTANCE == null) {
            FontMod.INSTANCE = new FontMod();
        }
        return FontMod.INSTANCE;
    }
    
    public static boolean checkFont(final String font, final boolean message) {
        final String[] availableFontFamilyNames;
        final String[] fonts = availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (final String s : availableFontFamilyNames) {
            if (!message && s.equals(font)) {
                return true;
            }
            if (message) {
                Command.sendMessage(s);
            }
        }
        return false;
    }
    
    private void setInstance() {
        FontMod.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        final Setting setting;
        if (event.getStage() == 2 && (setting = event.getSetting()) != null && setting.getFeature().equals(this)) {
            if (setting.getName().equals("FontName") && !checkFont(setting.getPlannedValue().toString(), false)) {
                Command.sendMessage(ChatFormatting.RED + "That font doesnt exist.");
                event.setCanceled(true);
                return;
            }
            this.reloadFont = true;
        }
    }
    
    @Override
    public void onTick() {
        if (this.reloadFont) {
            Thunderhack.textManager.init(false);
            this.reloadFont = false;
        }
    }
    
    static {
        FontMod.INSTANCE = new FontMod();
    }
}
