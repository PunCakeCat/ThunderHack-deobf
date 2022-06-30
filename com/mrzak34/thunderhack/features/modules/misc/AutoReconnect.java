//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraft.client.multiplayer.*;
import com.mrzak34.thunderhack.util.*;

public class AutoReconnect extends Module
{
    private static ServerData serverData;
    private static AutoReconnect INSTANCE;
    public Setting<Integer> delay;
    
    public AutoReconnect() {
        super("AutoReconnect", "\u043a\u043e\u043d\u043d\u0435\u043a\u0442\u0438\u0442 \u043a \u0441\u0435\u0440\u0432\u0443-\u0435\u0441\u043b\u0438 \u043a\u0438\u043a\u043d\u0443\u043b\u043e", Category.MISC, true, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("delay", (T)34, (T)0, (T)90));
        this.setInstance();
    }
    
    public static AutoReconnect getInstance() {
        if (AutoReconnect.INSTANCE == null) {
            AutoReconnect.INSTANCE = new AutoReconnect();
        }
        return AutoReconnect.INSTANCE;
    }
    
    private void setInstance() {
        AutoReconnect.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void sendPacket(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiDisconnected) {
            this.updateLastConnectedServer();
            final GuiDisconnected disconnected = (GuiDisconnected)event.getGui();
            event.setGui((GuiScreen)new GuiDisconnectedHook(disconnected));
        }
    }
    
    @SubscribeEvent
    public void onWorldUnload(final WorldEvent.Unload event) {
        this.updateLastConnectedServer();
    }
    
    public void updateLastConnectedServer() {
        final ServerData data = AutoReconnect.mc.getCurrentServerData();
        if (data != null) {
            AutoReconnect.serverData = data;
        }
    }
    
    static {
        AutoReconnect.INSTANCE = new AutoReconnect();
    }
    
    private class GuiDisconnectedHook extends GuiDisconnected
    {
        private final TimerUtil timer;
        
        public GuiDisconnectedHook(final GuiDisconnected disconnected) {
            super(disconnected.parentScreen, disconnected.reason, disconnected.message);
            (this.timer = new TimerUtil()).reset();
        }
        
        public void updateScreen() {
            if (this.timer.passedS(AutoReconnect.this.delay.getValue())) {
                this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this.parentScreen, this.mc, (AutoReconnect.serverData == null) ? this.mc.currentServerData : AutoReconnect.serverData));
            }
        }
        
        public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
            super.drawScreen(mouseX, mouseY, partialTicks);
            final String s = "Reconnecting in " + MathUtil.round((AutoReconnect.this.delay.getValue() * 1000 - this.timer.getPassedTimeMs()) / 1000.0, 1);
            AutoReconnect.this.renderer.drawString(s, (float)(this.width / 2 - AutoReconnect.this.renderer.getStringWidth(s) / 2), (float)(this.height - 16), 16777215, true);
        }
    }
}
