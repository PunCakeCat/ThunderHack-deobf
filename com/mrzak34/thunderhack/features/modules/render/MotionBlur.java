//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import com.mrzak34.thunderhack.manager.*;
import com.mrzak34.thunderhack.features.command.*;

public class MotionBlur extends Module
{
    public final Setting<Float> amount;
    float lastValue;
    private Map domainResourceManagers;
    
    public MotionBlur() {
        super("MotionBlur", "MotionBlur", Module.Category.RENDER, true, false, false);
        this.amount = (Setting<Float>)this.register(new Setting("Amount", (T)1.0f, (T)0.0f, (T)10.0f));
    }
    
    public void onDisable() {
        MotionBlur.mc.entityRenderer.stopUseShader();
    }
    
    public void onTick() {
        try {
            final float curValue = this.amount.getValue();
            if (!MotionBlur.mc.entityRenderer.isShaderActive() && MotionBlur.mc.world != null) {
                MotionBlur.mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
            }
            if (this.domainResourceManagers == null) {
                this.domainResourceManagers = ((SimpleReloadableResourceManager)MotionBlur.mc.resourceManager).domainResourceManagers;
            }
            if (!this.domainResourceManagers.containsKey("motionblur")) {
                this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
            }
            if (curValue != this.lastValue) {
                Command.sendMessage("Motion Blur \u043f\u0435\u0440\u0435\u0437\u0430\u043f\u0443\u0449\u0435\u043d!");
                this.domainResourceManagers.remove("motionblur");
                this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
                MotionBlur.mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
            }
            this.lastValue = curValue;
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
