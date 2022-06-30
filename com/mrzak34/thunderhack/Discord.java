//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack;

import club.minnced.discord.rpc.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.features.modules.misc.*;

public class Discord
{
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static Thread thread;
    private static int index;
    public static boolean started;
    public String out;
    
    public static void start() {
        Discord.started = true;
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        Discord.rpc.Discord_Initialize("939112431488225280", handlers, true, "");
        Discord.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        Discord.presence.details = ((Util.mc.currentScreen instanceof GuiMainMenu) ? "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e" : ("\u0418\u0433\u0440\u0430\u0435\u0442 " + ((Minecraft.getMinecraft().currentServerData != null) ? (RPC.INSTANCE.showIP.getValue() ? ("\u043d\u0430 " + Minecraft.getMinecraft().currentServerData.serverIP) : " \u041d\u041d \u0441\u0435\u0440\u0432\u0435\u0440") : " \u0427\u0438\u0442\u0435\u0440\u0438\u0442 \u0432 \u043e\u0434\u0438\u043d\u043e\u0447\u043a\u0435")));
        Discord.presence.state = RPC.INSTANCE.state.getValue();
        Discord.presence.largeImageKey = "aboba3";
        Discord.presence.largeImageText = "ThunderHack+";
        Discord.rpc.Discord_UpdatePresence(Discord.presence);
        DiscordRichPresence presence;
        String string;
        (Discord.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Discord.rpc.Discord_RunCallbacks();
                if (!ChatTweaks.getInstance().inq) {
                    presence = Discord.presence;
                    if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu || Minecraft.getMinecraft().currentScreen instanceof GuiScreenServerList || Minecraft.getMinecraft().currentScreen instanceof GuiScreenAddServer) {
                        string = "\u0412 \u0433\u043b\u0430\u0432\u043d\u043e\u043c \u043c\u0435\u043d\u044e";
                    }
                    else if (Minecraft.getMinecraft().currentServerData != null) {
                        if (RPC.INSTANCE.showIP.getValue()) {
                            string = "\u0418\u0433\u0440\u0430\u0435\u0442 \u043d\u0430 " + Minecraft.getMinecraft().currentServerData.serverIP;
                        }
                        else {
                            string = "\u041d\u041d \u0441\u0435\u0440\u0432\u0435\u0440";
                        }
                    }
                    else {
                        string = "\u0412\u044b\u0431\u0438\u0440\u0430\u0435\u0442 \u0441\u0435\u0440\u0432\u0435\u0440";
                    }
                    presence.details = string;
                }
                if (ChatTweaks.getInstance().inq && !(Util.mc.currentScreen instanceof GuiMainMenu) && !(Minecraft.getMinecraft().currentScreen instanceof GuiScreenServerList) && !(Minecraft.getMinecraft().currentScreen instanceof GuiScreenAddServer)) {
                    Discord.presence.details = "In queue: " + ChatTweaks.getInstance().forrpc;
                }
                if (AFKtimer.INSTANCE.afkrpc.getValue() && AFKtimer.INSTANCE.afk) {
                    Discord.presence.state = "\u0421\u0442\u043e\u0438\u0442 AFK";
                }
                else {
                    Discord.presence.state = RPC.INSTANCE.state.getValue();
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.cute) {
                    Discord.presence.largeImageKey = "zak";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.thbeta) {
                    Discord.presence.largeImageKey = "nek";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.newver) {
                    Discord.presence.largeImageKey = "img23";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.cat) {
                    Discord.presence.largeImageKey = "caaat";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.Konas) {
                    Discord.presence.largeImageKey = "2213";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.Unknown) {
                    Discord.presence.largeImageKey = "th";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.minecraft) {
                    Discord.presence.largeImageKey = "minecraft";
                }
                if (RPC.INSTANCE.Mode.getValue() == RPC.mode.Thlogo) {
                    Discord.presence.largeImageKey = "aboba3";
                }
                Discord.rpc.Discord_UpdatePresence(Discord.presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public static void stop() {
        Discord.started = false;
        if (Discord.thread != null && !Discord.thread.isInterrupted()) {
            Discord.thread.interrupt();
        }
        Discord.rpc.Discord_Shutdown();
    }
    
    static {
        Discord.index = 1;
        rpc = DiscordRPC.INSTANCE;
        Discord.presence = new DiscordRichPresence();
    }
}
