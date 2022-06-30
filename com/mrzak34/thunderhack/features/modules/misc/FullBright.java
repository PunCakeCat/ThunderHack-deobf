//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FullBright extends Module
{
    public Setting<Mode> mode;
    public Setting<Boolean> effects;
    private float previousSetting;
    
    public FullBright() {
        super("Fullbright", "\u0434\u0435\u043b\u0430\u0435\u0442 \u043f\u043e\u044f\u0440\u0447\u0435", Category.RENDER, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.GAMMA));
        this.effects = (Setting<Boolean>)this.register(new Setting("Effects", (T)false));
        this.previousSetting = 1.0f;
    }
    
    @Override
    public void onEnable() {
        this.previousSetting = FullBright.mc.gameSettings.gammaSetting;
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.GAMMA) {
            FullBright.mc.gameSettings.gammaSetting = 1000.0f;
        }
        if (this.mode.getValue() == Mode.POTION) {
            FullBright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }
    
    @Override
    public void onDisable() {
        if (this.mode.getValue() == Mode.POTION) {
            FullBright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
        FullBright.mc.gameSettings.gammaSetting = this.previousSetting;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() == 0 && event.getPacket() instanceof SPacketEntityEffect && this.effects.getValue()) {
            final SPacketEntityEffect packet = (SPacketEntityEffect)event.getPacket();
            if (FullBright.mc.player != null && packet.getEntityId() == FullBright.mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15)) {
                event.setCanceled(true);
            }
        }
    }
    
    public enum Mode
    {
        GAMMA, 
        POTION;
    }
}
