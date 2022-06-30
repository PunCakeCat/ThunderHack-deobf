//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.gui.inventory.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class KDShop extends Module
{
    public static GuiScreen lastGui;
    public Setting<SubBind> breakBind;
    
    public KDShop() {
        super("KDShop", "\u041d\u0435 \u0432\u0441\u0435\u0433\u0434\u0430 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442-\u043d\u043e \u0434\u0430 \u043b\u0430\u0434\u043d\u043e", Category.FUNNYGAME, true, false, false);
        this.breakBind = (Setting<SubBind>)this.register(new Setting("Open", (T)new SubBind(23)));
    }
    
    @Override
    public void onUpdate() {
        if (KDShop.mc.currentScreen instanceof GuiContainer) {
            KDShop.lastGui = KDShop.mc.currentScreen;
        }
        if (PlayerUtils.isKeyDown(this.breakBind.getValue().getKey())) {
            KDShop.mc.displayGuiScreen(KDShop.lastGui);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.setCanceled(true);
        }
    }
}
