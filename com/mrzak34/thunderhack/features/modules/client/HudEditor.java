//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.client.gui.*;

public class HudEditor extends Module
{
    private static HudEditor INSTANCE;
    
    public HudEditor() {
        super("HudEditor", "\u0445\u0443\u0434 \u0438\u0437\u043c\u0435\u043d\u044f\u0442\u044c \u0434\u0430", Category.CLIENT, true, false, false);
        this.setInstance();
    }
    
    public static HudEditor getInstance() {
        if (HudEditor.INSTANCE == null) {
            HudEditor.INSTANCE = new HudEditor();
        }
        return HudEditor.INSTANCE;
    }
    
    private void setInstance() {
        HudEditor.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)HudEditorGui.getHudGui());
        this.toggle();
    }
    
    @Override
    public void onDisable() {
    }
    
    static {
        HudEditor.INSTANCE = new HudEditor();
    }
}
