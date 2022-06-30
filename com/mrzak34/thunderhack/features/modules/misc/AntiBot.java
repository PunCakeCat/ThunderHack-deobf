//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.*;

public class AntiBot extends Module
{
    public static ArrayList<EntityPlayer> bots;
    public static ArrayList<String> botsbyname;
    
    public AntiBot() {
        super("AntiBot", "\u0430\u043d\u0442\u0438\u0431\u043e\u0442 \u0442\u0438\u043f\u0430", Category.MISC, false, false, false);
    }
    
    @Override
    public void onDisable() {
        AntiBot.bots.clear();
    }
    
    public static List<EntityPlayer> getBots() {
        return AntiBot.bots;
    }
    
    public static List<String> getBotsByName() {
        return AntiBot.botsbyname;
    }
    
    @Override
    public void onUpdate() {
        if (AntiBot.mc.currentScreen instanceof GuiDownloadTerrain && !AntiBot.bots.isEmpty()) {
            AntiBot.bots.clear();
        }
        if (AntiBot.mc.world != null) {
            AntiBot.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).filter(e -> e != AntiBot.mc.player && e != null).filter(e -> isBot(e)).forEach(entity -> {
                if (!AntiBot.bots.contains(entity)) {
                    AntiBot.bots.add(entity);
                }
                AntiBot.botsbyname.add(((Entity)entity).getName());
            });
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return String.valueOf(AntiBot.bots.size());
    }
    
    public static boolean isBot(final EntityPlayer player) {
        final NetworkPlayerInfo npi = Objects.requireNonNull(AntiBot.mc.getConnection()).getPlayerInfo(player.getGameProfile().getId());
        return npi == null || (npi.getResponseTime() <= 0 && !player.equals((Object)AntiBot.mc.player) && npi.getGameProfile() == null && player.hasCustomName());
    }
    
    static {
        AntiBot.bots = new ArrayList<EntityPlayer>();
        AntiBot.botsbyname = new ArrayList<String>();
    }
}
