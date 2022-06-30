//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.features.command.*;

public class AntiTittle extends Module
{
    public Setting<Boolean> tittle;
    public Setting<Boolean> armorstands;
    public Setting<Boolean> scoreBoard;
    public Setting<Boolean> chat;
    public Setting<Integer> waterMarkZ1;
    public Setting<Integer> waterMarkZ2;
    public Setting<Boolean> counter;
    int count;
    int y1;
    int x1;
    ScaledResolution sr;
    
    public AntiTittle() {
        super("Adblock", "\u0410\u0434\u0431\u043b\u043e\u043a \u0434\u043b\u044f \u0435\u0431\u0443\u0447\u0435\u0433\u043e-\u0444\u0432\u043d\u0438\u0433\u0435\u0439\u043c\u0430", Category.FUNNYGAME, true, false, false);
        this.tittle = (Setting<Boolean>)this.register(new Setting("AntiTitle", (T)true));
        this.armorstands = (Setting<Boolean>)this.register(new Setting("AntiSpawnLag", (T)true));
        this.scoreBoard = (Setting<Boolean>)this.register(new Setting("ScoreBoard", (T)true));
        this.chat = (Setting<Boolean>)this.register(new Setting("ChatAds", (T)true));
        this.waterMarkZ1 = (Setting<Integer>)this.register(new Setting("Y", (T)10, (T)0, (T)524));
        this.waterMarkZ2 = (Setting<Integer>)this.register(new Setting("X", (T)20, (T)0, (T)862));
        this.counter = (Setting<Boolean>)this.register(new Setting("Counter", (T)false));
        this.count = 0;
        this.y1 = 0;
        this.x1 = 0;
        this.sr = new ScaledResolution(AntiTittle.mc);
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        if (this.counter.getValue()) {
            this.y1 = (int)(this.sr.getScaledHeight() / (1000.0f / this.waterMarkZ1.getValue()));
            this.x1 = (int)(this.sr.getScaledWidth() / (1000.0f / this.waterMarkZ2.getValue()));
            RenderUtil.drawSmoothRect(this.waterMarkZ2.getValue(), this.waterMarkZ1.getValue(), (float)(75 + this.waterMarkZ2.getValue()), (float)(11 + this.waterMarkZ1.getValue()), new Color(35, 35, 40, 230).getRGB());
            Util.fr.drawStringWithShadow("Ads Blocked : ", (float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
            Util.fr.drawStringWithShadow(String.valueOf(this.count), (float)(this.waterMarkZ2.getValue() + 6 + Util.fr.getStringWidth("Ads Blocked : ")), (float)(this.waterMarkZ1.getValue() + 1), -1);
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (this.tittle.getValue() && e.getPacket() instanceof SPacketTitle) {
            ++this.count;
            e.setCanceled(true);
        }
        if (this.scoreBoard.getValue() && e.getPacket() instanceof SPacketScoreboardObjective) {
            ++this.count;
            e.setCanceled(true);
        }
        if (this.chat.getValue() && !Thunderhack.moduleManager.getModuleByClass(ChatTweaks.class).isOn() && e.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)e.getPacket();
            if (packet.getType() != ChatType.GAME_INFO && this.tryProcessChat(packet.getChatComponent().getFormattedText(), packet.getChatComponent().getUnformattedText())) {
                e.setCanceled(true);
            }
        }
    }
    
    private boolean tryProcessChat(final String message, final String unformatted) {
        String out = message;
        final String[] parts = out.split(" ");
        final String[] partsUnformatted = unformatted.split(" ");
        out = message;
        if (out.contains("\u0434\u043e\u043d\u0430\u0442")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u043e\u043f\u043b\u0430\u0442\u044b")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u041a\u0443\u043f\u0438\u0442\u044c \u043a\u043b\u044e\u0447")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u041f\u043e\u0441\u043b\u0435 \u0432\u0430\u0439\u043f\u0430")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043a\u0443\u043f\u043b\u0435\u043d\u043d\u044b\u0435")) {
            ++this.count;
            out = "";
        }
        if (out.contains("§7[§r§e§l+§r§7]")) {
            ++this.count;
            out = "";
        }
        if (out.contains("/prize")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u043d\u0430\u0433\u0440\u0430\u0434\u0430")) {
            ++this.count;
            out = "";
        }
        if (out.contains("§a§l§m")) {
            ++this.count;
            out = "";
        }
        if (out.contains("§a§l[!]")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u0432\u044b\u0431\u0438\u043b \u0438\u0437 \u0431\u0435\u0441\u043f\u043b\u0430\u0442\u043d\u043e\u0433\u043e")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u0432\u044b\u0434\u0435\u043b\u0438\u0442\u044c\u0441\u044f \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u0431\u043e\u043b\u044c\u0448\u0438\u0435 \u0441\u043a\u0438\u0434\u043a\u0438")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u0440\u0443\u0431")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u043f\u0440\u0438\u0432\u044f\u0436\u0438\u0442\u0435 \u0441\u0432\u043e\u0439")) {
            ++this.count;
            out = "";
        }
        if (out.contains("\u0441\u0430\u0439\u0442\u0435")) {
            ++this.count;
            out = "";
        }
        try {
            if (!out.equals("")) {
                Command.sendMessageWithoutTH(out);
            }
        }
        catch (Exception ex) {}
        return true;
    }
}
