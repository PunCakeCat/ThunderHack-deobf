//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.text.event.*;
import com.mrzak34.thunderhack.notification.*;
import java.text.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import net.minecraft.util.text.*;
import org.apache.commons.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.features.gui.components.items.buttons.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.atomic.*;
import net.minecraft.client.network.*;

public class ChatTweaks extends Module
{
    private static ChatTweaks INSTANCE;
    public Setting<Boolean> clean;
    public Setting<Boolean> infinite;
    public Setting<Boolean> timestamps;
    public Setting<Boolean> namehighlight;
    public Setting<Boolean> welcomer;
    private final Map<String, String> uuidNameCache;
    public Setting<Boolean> futils;
    public Setting<Boolean> popusktome;
    public Setting<Boolean> shittyclans;
    public Setting<Boolean> serverads;
    public Setting<Boolean> primer;
    public Setting<Boolean> donators;
    public Setting<Boolean> itemclear;
    public Setting<Boolean> chatmarks;
    public Setting<Boolean> bans;
    public Setting<Boolean> welcom;
    public Setting<Boolean> privat;
    public boolean check;
    public String date;
    public String forrpc;
    public String clean1;
    public String clean2;
    public String clean3;
    public String clean4;
    public Setting<Integer> multip;
    public boolean inq;
    String welcmout;
    String discord;
    String last;
    HashMap<String, String> pics;
    HashMap<Integer, String> posts;
    public boolean nado;
    String lasturl;
    int postid;
    String filename;
    String formatfila;
    public boolean once;
    public ResourceLocation logo;
    public float nigw;
    public float nigh;
    public Timer timer;
    int xvalue;
    int yvalue;
    
    public ChatTweaks() {
        super("ChatTweaks", "\u0438\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0447\u0430\u0442", Category.MISC, true, false, false);
        this.clean = (Setting<Boolean>)this.register(new Setting("NoChatBackground", (T)false, "Cleans your chat"));
        this.infinite = (Setting<Boolean>)this.register(new Setting("InfiniteChat", (T)false, "Makes your chat infinite."));
        this.timestamps = (Setting<Boolean>)this.register(new Setting("TimeStamps", (T)false, "Makes your chat infinite."));
        this.namehighlight = (Setting<Boolean>)this.register(new Setting("NameHighLight", (T)false, "Makes your chat infinite."));
        this.welcomer = (Setting<Boolean>)this.register(new Setting("WelcomerCS", (T)false, "Cleans your chat"));
        this.uuidNameCache = (Map<String, String>)Maps.newConcurrentMap();
        this.futils = (Setting<Boolean>)this.register(new Setting("FunnyGam Utils", (T)true));
        this.popusktome = (Setting<Boolean>)this.register(new Setting("[\u041f\u043e\u043f\u0443\u0441\u043a -> \u042f] adds", (T)true, v -> this.futils.getValue()));
        this.shittyclans = (Setting<Boolean>)this.register(new Setting("nn clans", (T)true, v -> this.futils.getValue()));
        this.serverads = (Setting<Boolean>)this.register(new Setting("Server adds", (T)true, v -> this.futils.getValue()));
        this.primer = (Setting<Boolean>)this.register(new Setting("\u0440\u0435\u0448\u0438 2+2 \u0435\u0441\u043b\u0438 \u043d\u0435 \u0434\u0430\u0443\u043d", (T)true, v -> this.futils.getValue()));
        this.donators = (Setting<Boolean>)this.register(new Setting("fuck donators", (T)true, v -> this.futils.getValue()));
        this.itemclear = (Setting<Boolean>)this.register(new Setting("item clear", (T)true, v -> this.futils.getValue()));
        this.chatmarks = (Setting<Boolean>)this.register(new Setting("G |", (T)true, v -> this.futils.getValue()));
        this.bans = (Setting<Boolean>)this.register(new Setting("kick/mute/ban", (T)true, v -> this.futils.getValue()));
        this.welcom = (Setting<Boolean>)this.register(new Setting("NN JOINS", (T)true, v -> this.futils.getValue()));
        this.privat = (Setting<Boolean>)this.register(new Setting("Privates", (T)true, v -> this.futils.getValue()));
        this.date = "";
        this.forrpc = "";
        this.clean1 = "";
        this.clean2 = "";
        this.clean3 = "";
        this.clean4 = "";
        this.multip = (Setting<Integer>)this.register(new Setting("Scale", (T)200, (T)50, (T)1280));
        this.inq = false;
        this.welcmout = "server";
        this.discord = "";
        this.last = "";
        this.pics = new HashMap<String, String>();
        this.posts = new HashMap<Integer, String>();
        this.nado = false;
        this.lasturl = "";
        this.postid = 0;
        this.filename = "";
        this.formatfila = "";
        this.once = false;
        this.logo = SkinStorageManipulationer.getTexture(this.filename, this.formatfila);
        this.nigw = 0.0f;
        this.nigh = 0.0f;
        this.timer = new Timer();
        this.xvalue = 0;
        this.yvalue = 0;
        this.setInstance();
    }
    
    public static ChatTweaks getInstance() {
        if (ChatTweaks.INSTANCE == null) {
            ChatTweaks.INSTANCE = new ChatTweaks();
        }
        return ChatTweaks.INSTANCE;
    }
    
    private void setInstance() {
        ChatTweaks.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getType() != ChatType.GAME_INFO && this.tryProcessChat(packet.getChatComponent().getFormattedText(), packet.getChatComponent().getUnformattedText())) {
                event.setCanceled(true);
            }
        }
        final String[] myString = { "See you later, ", "Catch ya later, ", "See you next time, ", "Farewell, ", "Bye, ", "Good bye, ", "Later, " };
        final int n = (int)Math.floor(Math.random() * myString.length);
        final String[] myString2 = { "Good to see you, ", "Greetings, ", "Hello, ", "Howdy, ", "Hey, ", "Good evening, ", "Welcome to SERVERIP1D5A9E, " };
        final int n2 = (int)Math.floor(Math.random() * myString2.length);
        final String welcm = myString2[n2];
        if (ChatTweaks.mc.world != null) {
            this.welcmout = welcm.replace("SERVERIP1D5A9E", Minecraft.getMinecraft().currentServerData.serverIP);
        }
        else {
            this.welcmout = "server";
        }
        if (this.welcomer.getValue() && ChatTweaks.mc.player != null && ChatTweaks.mc.world != null && !Objects.equals(Minecraft.getMinecraft().currentServerData.serverIP, "mcfunny.su")) {
            try {
                final SPacketPlayerListItem packet2 = (SPacketPlayerListItem)event.getPacket();
                if (packet2.getEntries().size() <= 1) {
                    if (packet2.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                        packet2.getEntries().forEach(data -> {
                            if ((AntiBot.getBotsByName().contains(data.getProfile().getName()) || !data.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId())) && data.getProfile().getName() == null && data.getProfile().getId().toString() == "b9523a25-2b04-4a75-bee0-b84027824fe0") {
                                if (data.getProfile().getId().toString() == "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
                                    return;
                                }
                            }
                            try {
                                Command.sendMessage(this.welcmout + data.getProfile().getName());
                            }
                            catch (Exception ex) {}
                        });
                    }
                    else if (packet2.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                        final Object o;
                        final int n3;
                        packet2.getEntries().forEach(data2 -> {
                            if ((!data2.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId()) || data2.getProfile().getId() == null || data2.getProfile().getId().toString() != "b9523a25-2b04-4a75-bee0-b84027824fe0" || data2.getProfile().getId().toString() != "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") && !AntiBot.getBotsByName().contains(this.uuidtoname(data2.getProfile().getId().toString()))) {
                                Command.sendMessage(o[n3] + this.uuidtoname(data2.getProfile().getId().toString()));
                            }
                        });
                    }
                }
            }
            catch (Exception ex2) {}
        }
    }
    
    private boolean tryProcessChat(final String message, final String unformatted) {
        String out = message;
        final String[] parts = out.split(" ");
        final String[] partsUnformatted = unformatted.split(" ");
        out = message;
        if (out.contains("discordapp") && out.contains("png")) {
            this.discord = out;
            try {
                final String[] splitted = this.discord.toString().split("https://");
                final String url = "https://" + splitted[1];
                final String[] splitted2 = url.split(".png");
                this.last = splitted2[0] + ".png";
                final URL nigUrl = new URL(this.last);
                final String withoutlink = this.discord.replace(this.last, "");
                final ITextComponent cancel2 = (ITextComponent)new TextComponentString(this.last);
                final ITextComponent cancel3 = (ITextComponent)new TextComponentString("<" + this.solvename(out) + "> \u041e\u0442\u043f\u0440\u0430\u0432\u0438\u043b \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0443 [Show Discord Image]");
                cancel3.setStyle(cancel3.getStyle().setColor(TextFormatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cancel2)));
                Command.sendIText(cancel3);
                out = "";
            }
            catch (Exception ec) {
                System.out.println(ec.getMessage());
            }
        }
        if (out.contains("discordapp") && out.contains("jpg")) {
            this.discord = out;
            try {
                final String[] splitted = this.discord.toString().split("https://");
                final String url = "https://" + splitted[1];
                final String[] splitted2 = url.split(".jpg");
                this.last = splitted2[0] + ".jpg";
                final URL nigUrl = new URL(this.last);
                final String withoutlink = this.discord.replace(this.last, "");
                final ITextComponent cancel2 = (ITextComponent)new TextComponentString(this.last);
                final ITextComponent cancel3 = (ITextComponent)new TextComponentString("<" + this.solvename(out) + "> \u041e\u0442\u043f\u0440\u0430\u0432\u0438\u043b \u043a\u0430\u0440\u0442\u0438\u043d\u043a\u0443 [Show Discord Image]");
                cancel3.setStyle(cancel3.getStyle().setColor(TextFormatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cancel2)));
                Command.sendIText(cancel3);
                out = "";
            }
            catch (Exception ec) {
                System.out.println(ec.getMessage());
            }
        }
        if (this.popusktome.getValue() && out.contains("§r§c\u042f§r§6")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u0434\u043e\u043d\u0430\u0442")) {
            out = "";
        }
        if (this.itemclear.getValue() && out.contains("\u0412\u0441\u0435 \u043e\u0447\u0438\u0441\u0442\u0438\u0442\u0441\u044f \u0447\u0435\u0440\u0435\u0437")) {
            out = "";
        }
        if (this.itemclear.getValue() && out.contains("\u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0435\u043d\u044b")) {
            out = "";
        }
        if (this.shittyclans.getValue() && out.contains("\u0441lan")) {
            out = "";
        }
        if (this.shittyclans.getValue() && out.contains("\u043a\u043b\u0430\u043d")) {
            out = "";
        }
        if (this.donators.getValue() && out.contains("\u043c\u0438\u043b\u043b\u0438\u0430\u0440\u0434\u0435\u0440")) {
            out = "";
        }
        if (this.primer.getValue() && out.contains("\u0440\u0435\u0448\u0438\u043b \u043f\u0440\u0438\u043c\u0435\u0440")) {
            out = "";
        }
        if (this.primer.getValue() && out.contains("\u041e\u0442\u0432\u0435\u0442 \u0431\u044b\u043b:")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u043e\u043f\u043b\u0430\u0442\u044b")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u041a\u0443\u043f\u0438\u0442\u044c \u043a\u043b\u044e\u0447")) {
            out = "";
        }
        if (this.welcom.getValue() && out.contains("§7§2§l?")) {
            out = "";
        }
        if (this.welcom.getValue() && out.contains("§7\n")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u041f\u043e\u0441\u043b\u0435 \u0432\u0430\u0439\u043f\u0430")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043a\u0443\u043f\u043b\u0435\u043d\u043d\u044b\u0435")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("§7[§r§e§l+§r§7]")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("/prize")) {
            out = "";
        }
        if (this.bans.getValue() && out.contains("*")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("*")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u043d\u0430\u0433\u0440\u0430\u0434\u0430")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("§a§l§m")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("§a§l[!]")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u0432\u044b\u0431\u0438\u043b \u0438\u0437 \u0431\u0435\u0441\u043f\u043b\u0430\u0442\u043d\u043e\u0433\u043e")) {
            out = "";
        }
        if (this.privat.getValue() && out.contains("[!]")) {
            out = "";
            NotificationManager.publicity("§c\u041f\u0440\u0438\u0432\u0430\u0442!§r", "\u0427\u0435\u043b, \u0442\u044b \u0432\u043b\u0435\u0437 \u0432 \u043f\u0440\u0438\u0432\u0430\u0442", 1, NotificationType.ERROR);
        }
        if (this.serverads.getValue() && out.contains("\u0432\u044b\u0434\u0435\u043b\u0438\u0442\u044c\u0441\u044f \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440\u0435")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u0431\u043e\u043b\u044c\u0448\u0438\u0435 \u0441\u043a\u0438\u0434\u043a\u0438")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u0440\u0443\u0431")) {
            out = "";
        }
        if (this.serverads.getValue() && out.contains("\u043f\u0440\u0438\u0432\u044f\u0436\u0438\u0442\u0435 \u0441\u0432\u043e\u0439")) {
            out = "";
        }
        if (this.primer.getValue() && out.contains("\u043a\u0442\u043e \u043f\u0435\u0440\u0432\u044b\u0439 \u0440\u0435\u0448\u0438\u0442 \u043f\u043e\u043b\u0443\u0447\u0438\u0442")) {
            out = "";
        }
        if (this.timestamps.getValue()) {
            this.date = new SimpleDateFormat("k:mm").format(new Date());
        }
        if (this.namehighlight.getValue()) {
            if (Util.mc.player == null) {
                return false;
            }
            if (out.contains(ChatTweaks.mc.player.getName())) {
                NotificationManager.publicity("ChatTweaks", "\u0422\u044b \u0431\u044b\u043b \u0443\u043f\u043e\u043c\u044f\u043d\u0443\u0442 \u0432 \u0447\u0430\u0442\u0435!", 5, NotificationType.WARNING);
            }
            if (RPC.INSTANCE.queue.getValue() && out.contains("Position in queue:")) {
                this.clean1 = out.replace("Position in queue:", "");
                this.clean2 = this.clean1.replace("§6", "");
                this.clean3 = this.clean2.replace("§r", "");
                this.clean4 = this.clean3.replace("§7", "");
                this.forrpc = this.clean4.replace("§", "");
                this.forrpc = this.forrpc.replace("l", "");
                this.inq = true;
            }
            if (!out.contains("Position in queue:")) {
                this.inq = false;
            }
            out = out.replace(ChatTweaks.mc.player.getName(), "§c" + ChatTweaks.mc.player.getName() + "§r");
        }
        if (this.donators.getValue()) {
            out = out.replace("§r§6§l[§r§b§l\u041f\u0420\u0415\u0417\u0418\u0414\u0415\u041d\u0422§r§6§l]§r", "§r");
            out = out.replace("§r§d§l[§r§5§l\u0410\u0434\u043c\u0438\u043d§r§d§l]§r", "§r");
            out = out.replace("§r§b§l[§r§3§l\u0413\u043b.\u0410\u0434\u043c\u0438\u043d§r§b§l]§r", "§r");
            out = out.replace("§8[§r§6\u0418\u0433\u0440\u043e\u043a§r§8]§r", "§r");
            out = out.replace("§r§5§l[§r§e§l\u0411\u041e\u0413§r§5§l]§r", "§r");
            out = out.replace("§r§a§l[§r§2§l\u041a\u0440\u0435\u0430\u0442\u0438\u0432§r§a§l]", "§r");
            out = out.replace("§r§4§l[§r§c§l\u0412\u043b\u0430\u0434\u0435\u043b\u0435\u0446§r§4§l]", "§r");
            out = out.replace("§r§5§l[§r§d§l\u041e\u0441\u043d\u043e\u0432\u0430\u0442\u0435\u043b\u044c§r§5§l]", "§r");
            out = out.replace("§r§b§l[§r§e§l?§r§d§lMORGENSHTERN§r§e§l?§r§b§l]", "§r");
            out = out.replace("§r§8[§r§4§l§oBlood§r§0§l§oRavens§r§8]", "§r");
            out = out.replace("§r§6§l[§r§e§l\u041b\u043e\u0440\u0434§r§6§l]", "§r");
            out = out.replace("§r§4§l[§r§2§l\u0412\u041b\u0410\u0414\u042b\u041a\u0410§r§4§l]", "§r");
        }
        if (this.chatmarks.getValue()) {
            out = out.replace("|", "§r");
            out = out.replace("§r§a§l§r", "§r");
            out = out.replace("§r§f§l", "§r");
            out = out.replace("§7§b?", "§r");
            out = out.replace("§r§4?", "§r");
            out = out.replace("§7§a? §r§f §r §r§r", "§r");
            out = out.replace("\u24bc", "§r");
        }
        try {
            if (this.timestamps.getValue() && !out.equals("")) {
                Command.sendMessageWithoutTH("[" + this.date + "]  " + out);
            }
            else if (!out.equals("")) {
                Command.sendMessageWithoutTH(out);
            }
        }
        catch (Exception ex) {}
        return true;
    }
    
    public String uuidtoname(String uuid) {
        uuid = uuid.replace("-", "");
        if (this.uuidNameCache.containsKey(uuid)) {
            return this.uuidNameCache.get(uuid);
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject)jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get((Object)"name").toString();
                    }
                }
            }
        }
        catch (IOException | ParseException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return null;
    }
    
    public void saveDickPick(final String s, final String format) {
        if (Objects.equals(this.lasturl, s) && this.nado) {
            return;
        }
        try {
            this.lasturl = s;
            final URL url = new URL(s);
            final URLConnection openConnection = url.openConnection();
            boolean check = true;
            try {
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                if (openConnection.getContentLength() > 8000000) {
                    System.out.println(" file size is too big.");
                    check = false;
                }
            }
            catch (Exception e) {
                System.out.println("Couldn't create a connection to the link, please recheck the link.");
                check = false;
                e.printStackTrace();
            }
            if (check) {
                BufferedImage img = null;
                try {
                    final InputStream in = new BufferedInputStream(openConnection.getInputStream());
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    final byte[] response = out.toByteArray();
                    img = ImageIO.read(new ByteArrayInputStream(response));
                }
                catch (Exception e2) {
                    System.out.println(" couldn't read an image from this link.");
                    e2.printStackTrace();
                }
                try {
                    final int niggermod = (int)(Math.random() * 10000.0);
                    ImageIO.write(img, format, new File("ThunderHack/tmp/" + niggermod + "." + format));
                    this.filename = String.valueOf(niggermod);
                    this.formatfila = format;
                    this.once = true;
                }
                catch (IOException e3) {
                    System.out.println("Couldn't create/send the output image.");
                    e3.printStackTrace();
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        final GuiScreen guiscreen = ChatTweaks.mc.currentScreen;
        if (this.nado && guiscreen instanceof GuiChat && !Objects.equals(this.filename, "") && !Objects.equals(this.formatfila, "")) {
            if (this.once) {
                this.logo = SkinStorageManipulationer.getTexture(this.filename, this.formatfila);
                this.once = false;
            }
            if (this.logo != null) {
                Util.mc.getTextureManager().bindTexture(this.logo);
                ModuleButton.drawCompleteImage((float)this.xvalue, this.yvalue - this.nigh, (int)this.nigw, (int)this.nigh);
            }
        }
        if (!this.nado) {
            this.logo = null;
        }
        if (this.timer.passedMs(1500L)) {
            this.nado = false;
        }
        final ScaledResolution sr = new ScaledResolution(ChatTweaks.mc);
        this.xvalue = Mouse.getX();
        this.yvalue = sr.getScaledHeight() - Mouse.getY();
    }
    
    public String solvename(final String notsolved) {
        final AtomicReference<String> mb = new AtomicReference<String>("err");
        final AtomicReference<String> atomicReference;
        Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfoMap().forEach(player -> {
            if (notsolved.contains(player.getGameProfile().getName())) {
                atomicReference.set(player.getGameProfile().getName());
            }
            return;
        });
        return mb.get();
    }
    
    static {
        ChatTweaks.INSTANCE = new ChatTweaks();
    }
}
