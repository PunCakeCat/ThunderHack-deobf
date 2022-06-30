//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class AutoCappRegear extends Module
{
    public Timer timer;
    public Setting<SubBind> aboba;
    public Setting<Integer> delay;
    boolean open_shop;
    
    public AutoCappRegear() {
        super("CappRegear", "\u0440\u0435\u0433\u0438\u0440\u0438\u0442 \u043a\u0430\u043f\u043f\u0443\u0447\u0438\u043d\u043e-\u043f\u043e \u0431\u0438\u043d\u0434\u0443", Category.FUNNYGAME, true, false, false);
        this.timer = new Timer();
        this.aboba = (Setting<SubBind>)this.register(new Setting("BuyBind", (T)new SubBind(24)));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)100, (T)1, (T)500));
        this.open_shop = false;
    }
    
    @Override
    public void onUpdate() {
        if (PlayerUtils.isKeyDown(this.aboba.getValue().getKey())) {
            if (!this.open_shop) {
                AutoCappRegear.mc.player.sendChatMessage("/drinks");
                this.open_shop = true;
            }
            if (this.open_shop && this.timer.passedMs(this.delay.getValue())) {
                AutoCappRegear.mc.playerController.windowClick(AutoCappRegear.mc.player.openContainer.windowId, 1, 0, ClickType.PICKUP, (EntityPlayer)AutoCappRegear.mc.player);
                AutoCappRegear.mc.playerController.windowClick(0, 1, 0, ClickType.PICKUP, (EntityPlayer)AutoCappRegear.mc.player);
                this.timer.reset();
            }
        }
        else {
            this.open_shop = false;
        }
    }
}
