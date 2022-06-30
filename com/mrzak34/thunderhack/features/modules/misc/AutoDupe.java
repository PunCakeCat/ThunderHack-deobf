//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import java.awt.*;
import java.net.*;

public class AutoDupe extends Module
{
    public AutoDupe() {
        super("2b2tAutoDupe", "\u043f\u0440\u0438\u0432\u0430\u0442\u043d\u044b\u0439 \u0434\u044e\u043f 2\u0431", Category.MISC, true, false, false);
    }
    
    @Override
    public void onEnable() {
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            }
            catch (Throwable t) {}
        }
    }
    
    @Override
    public void onDisable() {
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            }
            catch (Throwable t) {}
        }
    }
}
