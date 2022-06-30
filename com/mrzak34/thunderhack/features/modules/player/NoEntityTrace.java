//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class NoEntityTrace extends Module
{
    private static NoEntityTrace INSTANCE;
    public Setting<Boolean> pick;
    public Setting<Boolean> gap;
    public Setting<Boolean> obby;
    public boolean noTrace;
    
    public NoEntityTrace() {
        super("NoEntityTrace", "\u043a\u043e\u043f\u0430\u0442\u044c \u0441\u043a\u0432\u043e\u0437\u044c \u0438\u0433\u0440\u043e\u043a\u043e\u0432", Module.Category.PLAYER, false, false, false);
        this.pick = (Setting<Boolean>)this.register(new Setting("Pick", (T)true));
        this.gap = (Setting<Boolean>)this.register(new Setting("Gap", (T)false));
        this.obby = (Setting<Boolean>)this.register(new Setting("Obby", (T)false));
        this.setInstance();
    }
    
    public static NoEntityTrace getINSTANCE() {
        if (NoEntityTrace.INSTANCE == null) {
            NoEntityTrace.INSTANCE = new NoEntityTrace();
        }
        return NoEntityTrace.INSTANCE;
    }
    
    private void setInstance() {
        NoEntityTrace.INSTANCE = this;
    }
    
    public void onUpdate() {
        final Item item = NoEntityTrace.mc.player.getHeldItemMainhand().getItem();
        if (item instanceof ItemPickaxe && this.pick.getValue()) {
            this.noTrace = true;
            return;
        }
        if (item == Items.GOLDEN_APPLE && this.gap.getValue()) {
            this.noTrace = true;
            return;
        }
        if (item instanceof ItemBlock) {
            this.noTrace = (((ItemBlock)item).getBlock() == Blocks.OBSIDIAN && this.obby.getValue());
            return;
        }
        this.noTrace = false;
    }
    
    static {
        NoEntityTrace.INSTANCE = new NoEntityTrace();
    }
}
