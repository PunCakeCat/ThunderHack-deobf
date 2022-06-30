//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.client;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.*;

public class RPC extends Module
{
    public static RPC INSTANCE;
    public Setting<Boolean> showIP;
    public Setting<Boolean> queue;
    public Setting<String> state;
    public Setting<mode> Mode;
    public String out;
    
    public RPC() {
        super("DiscordRPC", "\u043a\u0440\u0443\u0442\u0430\u044f \u0440\u043f\u0441", Category.CLIENT, false, false, false);
        this.showIP = (Setting<Boolean>)this.register(new Setting("ShowIP", (T)Boolean.TRUE, "Shows the server IP in your discord presence."));
        this.queue = (Setting<Boolean>)this.register(new Setting("Queue", (T)Boolean.TRUE, "qdickpick"));
        this.state = (Setting<String>)this.register(new Setting("State", (T)"ThunderHack+", "Sets the state of the DiscordRPC."));
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.cute));
        this.out = "";
        RPC.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Discord.start();
    }
    
    @Override
    public void onUpdate() {
        if (!Discord.started) {
            Discord.start();
        }
    }
    
    @Override
    public void onDisable() {
        Discord.stop();
    }
    
    public enum mode
    {
        Konas, 
        cute, 
        Thlogo, 
        Unknown, 
        minecraft, 
        thbeta, 
        cat, 
        newver;
    }
}
