//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.client.gui.*;

public class Windows extends Module
{
    private static Windows INSTANCE;
    
    public Windows() {
        super("Windows", "\u043e\u043a\u043d\u0430", Category.CLIENT, true, false, false);
        this.setInstance();
    }
    
    public static Windows getInstance() {
        if (Windows.INSTANCE == null) {
            Windows.INSTANCE = new Windows();
        }
        return Windows.INSTANCE;
    }
    
    private void setInstance() {
        Windows.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)WindowsGui.getWindowsGui());
        this.toggle();
    }
    
    static {
        Windows.INSTANCE = new Windows();
    }
}
