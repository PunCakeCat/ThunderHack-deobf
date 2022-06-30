//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin;

import net.minecraftforge.fml.relauncher.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import java.awt.*;
import java.net.*;
import java.util.*;

public class OyVeyLoader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public OyVeyLoader() {
        Thunderhack.LOGGER.info("\n\nLoading mixins");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.oyvey.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        Thunderhack.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://www.youtube.com/watch?v=_FtE0S-IyY0"));
            }
            catch (Throwable t) {}
        }
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        OyVeyLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        OyVeyLoader.isObfuscatedEnvironment = false;
    }
}
