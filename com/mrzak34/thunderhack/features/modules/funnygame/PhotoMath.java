//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.notification.*;
import java.util.regex.*;

public class PhotoMath extends Module
{
    public Setting<String> autoez;
    public Timer delay;
    int nigger;
    boolean ez;
    boolean check;
    
    public PhotoMath() {
        super("PhotoMath", "\u0420\u0435\u0448\u0430\u0435\u0442 \u0447\u0430\u0442 \u0438\u0433\u0440\u0443 \u0430\u0432\u0442\u043e\u043c\u0430\u0442\u043e\u043c", Category.FUNNYGAME, true, false, false);
        this.autoez = (Setting<String>)this.register(new Setting("Custom", (T)"EZZZZZZZZZ boosted by ThunderHack"));
        this.delay = new Timer();
        this.nigger = 0;
        this.ez = false;
        this.check = false;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getType() != ChatType.GAME_INFO && this.Solve(packet.getChatComponent().getFormattedText())) {
                this.sendEZ();
            }
        }
    }
    
    private boolean Solve(final String message) {
        final String out = message;
        if (Util.mc.player == null && Util.mc.world == null) {
            return false;
        }
        if (out.contains("\u0420\u0435\u0448\u0438\u0442\u0435")) {
            final Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
            final Matcher matcher = pat.matcher(out);
            while (matcher.find()) {
                if (Objects.equals(matcher.group(), "3000")) {
                    PhotoMath.mc.player.sendChatMessage(String.valueOf(this.nigger));
                    this.check = true;
                    this.delay.reset();
                }
                else {
                    this.nigger += Integer.parseInt(matcher.group());
                }
            }
        }
        if (out.contains("\u043f\u043e\u0431\u0435\u0434\u0438\u043b!") && this.check) {
            this.nigger = 0;
            this.ez = true;
            if (Thunderhack.moduleManager.getModuleByClass(NotificationManager.class).isEnabled()) {
                NotificationManager.publicity("PhotoMath", "\u0411\u043e\u0436\u0435, \u0432\u043e\u0442 \u0431\u044b \u043d\u0430 \u0435\u0433\u044d \u0442\u0430\u043a \u043f\u0440\u043e\u0441\u0442\u043e \u0431\u044b\u043b\u043e", 2, NotificationType.SUCCESS);
            }
            this.check = false;
        }
        return true;
    }
    
    public void sendEZ() {
        if (this.ez && this.delay.passedMs(3000L)) {
            PhotoMath.mc.player.sendChatMessage((String)this.autoez.getValue());
            this.ez = false;
        }
    }
}
