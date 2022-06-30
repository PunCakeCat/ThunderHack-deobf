//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;

public class CustomEnchants extends Module
{
    private static CustomEnchants INSTANCE;
    
    public CustomEnchants() {
        super("RainbowEnchants", "\u0440\u0430\u0434\u0443\u0436\u043d\u044b\u0435 \u0437\u0430\u0447\u0430\u0440\u044b", Module.Category.RENDER, true, false, false);
        this.setInstance();
    }
    
    public static CustomEnchants getInstance() {
        if (CustomEnchants.INSTANCE == null) {
            CustomEnchants.INSTANCE = new CustomEnchants();
        }
        return CustomEnchants.INSTANCE;
    }
    
    private void setInstance() {
        CustomEnchants.INSTANCE = this;
    }
    
    static {
        CustomEnchants.INSTANCE = new CustomEnchants();
    }
}
