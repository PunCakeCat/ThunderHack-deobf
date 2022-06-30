//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.manager.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ExtraTab extends Module
{
    private static ExtraTab INSTANCE;
    public Setting<Integer> size;
    public Setting<Integer> X;
    public Setting<Integer> Y;
    public Setting<SubBind> breakBind;
    float y1;
    float x1;
    private ArrayList onlineFriends;
    private ArrayList onlineEnemies;
    
    public ExtraTab() {
        super("ExtraTab", "\u0440\u0430\u0441\u0448\u0438\u0440\u044f\u0435\u0442 \u0447\u0430\u0442", Category.MISC, false, false, false);
        this.size = (Setting<Integer>)this.register(new Setting("Size", (T)250, (T)1, (T)1000));
        this.X = (Setting<Integer>)this.register(new Setting("ManagerX", (T)900, (T)0, (T)1000));
        this.Y = (Setting<Integer>)this.register(new Setting("ManagerY", (T)900, (T)0, (T)1000));
        this.breakBind = (Setting<SubBind>)this.register(new Setting("Bind", (T)new SubBind(15)));
        this.y1 = 0.0f;
        this.x1 = 0.0f;
        this.onlineFriends = new ArrayList();
        this.onlineEnemies = new ArrayList();
        this.setInstance();
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String string;
        final String name = string = ((networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName()));
        if (Thunderhack.friendManager.isFriend(name)) {
            return ChatFormatting.GREEN + name;
        }
        return name;
    }
    
    public static ExtraTab getINSTANCE() {
        if (ExtraTab.INSTANCE == null) {
            ExtraTab.INSTANCE = new ExtraTab();
        }
        return ExtraTab.INSTANCE;
    }
    
    private void setInstance() {
        ExtraTab.INSTANCE = this;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        if (!PlayerUtils.isKeyDown(this.breakBind.getValue().getKey())) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(ExtraTab.mc);
        this.y1 = sr.getScaledHeight() / (1000.0f / this.Y.getValue());
        RenderUtil.drawSmoothRect(this.x1 = sr.getScaledWidth() / (1000.0f / this.X.getValue()), this.y1, this.x1 + 250.0f, this.y1 + 150.0f, new Color(-1644167168, true).getRGB());
        RenderUtil.drawSmoothRect(this.x1, this.y1, this.x1 + 250.0f, this.y1 + 15.0f, new Color(-635034074, true).getRGB());
        Util.fr.drawStringWithShadow("\u0414\u0440\u0443\u0437\u044c\u044f", this.x1 + 15.0f, this.y1 + 3.0f, PaletteHelper.astolfo(false, 1).getRGB());
        Util.fr.drawStringWithShadow("\u041f\u043e\u043f\u0443\u0449\u0435\u043d\u0446\u044b", this.x1 + 200.0f, this.y1 + 3.0f, PaletteHelper.astolfo(false, 1).getRGB());
        RenderUtil.drawSmoothRect(this.x1 + 124.0f, this.y1, this.x1 + 126.0f, this.y1 + 150.0f, new Color(-1205459418, true).getRGB());
        for (final FriendManager.Friend friend : Thunderhack.friendManager.getFriends()) {
            if (ExtraTab.mc.player.connection.getPlayerInfo(friend.getUsername()) != null && !this.onlineFriends.contains(friend.getUsername())) {
                this.onlineFriends.add(friend.getUsername());
            }
            if (ExtraTab.mc.player.connection.getPlayerInfo(friend.getUsername()) == null && this.onlineFriends.contains(friend.getUsername())) {
                this.onlineFriends.remove(friend.getUsername());
            }
            if (ExtraTab.mc.player.connection.getPlayerInfo(friend.getUsername()) == null && this.onlineFriends.contains(friend.getUsername())) {
                this.onlineFriends.remove(friend.getUsername());
            }
        }
        for (final EnemyManager.Enemy enemy : Thunderhack.enemyManager.getEnemies()) {
            if (ExtraTab.mc.player.connection.getPlayerInfo(enemy.getUsername()) != null && !this.onlineEnemies.contains(enemy.getUsername())) {
                this.onlineEnemies.add(enemy.getUsername());
            }
            if (ExtraTab.mc.player.connection.getPlayerInfo(enemy.getUsername()) == null && this.onlineEnemies.contains(enemy.getUsername())) {
                this.onlineEnemies.remove(enemy.getUsername());
            }
            if (ExtraTab.mc.player.connection.getPlayerInfo(enemy.getUsername()) == null && this.onlineEnemies.contains(enemy.getUsername())) {
                this.onlineEnemies.remove(enemy.getUsername());
            }
        }
        int schetchik_gaygera = 2;
        int schetchik_gaygera2 = 2;
        for (final Object onlinefriendnames : this.onlineFriends) {
            this.FriendNigger(onlinefriendnames.toString(), schetchik_gaygera);
            ++schetchik_gaygera;
        }
        for (final Object onlineenemiesnames : this.onlineEnemies) {
            this.EnemyNigger(onlineenemiesnames.toString(), schetchik_gaygera2);
            ++schetchik_gaygera2;
        }
    }
    
    public void FriendNigger(final String name, final int position) {
        Util.fr.drawStringWithShadow(name, this.x1 + 5.0f, this.y1 + position * 10, new Color(3209219).getRGB());
    }
    
    public void EnemyNigger(final String name, final int position) {
        Util.fr.drawStringWithShadow(name, this.x1 + 190.0f, this.y1 + position * 10, new Color(16253699).getRGB());
    }
    
    static {
        ExtraTab.INSTANCE = new ExtraTab();
    }
}
