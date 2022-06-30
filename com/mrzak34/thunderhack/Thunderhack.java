//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack;

import net.minecraftforge.fml.common.*;
import com.mrzak34.thunderhack.util.ffpshit.*;
import com.mrzak34.thunderhack.features.gui.thundergui.fontstuff.*;
import com.mrzak34.thunderhack.manager.*;
import com.mrzak34.thunderhack.mainmenu.*;
import java.awt.*;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.common.*;
import org.apache.logging.log4j.*;

@Mod(modid = "oyvey", name = "ThunderHack", version = "0.0.7")
public class Thunderhack
{
    public static final String MODID = "oyvey";
    public static final String MODNAME = "OyVey";
    public static final String MODVER = "0.0.7";
    public static final Logger LOGGER;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static TotemPopManager totemPopManager;
    public static ModuleManager moduleManager;
    public static EnemyManager enemyManager;
    public static PacketManager packetManager;
    public static NetworkHandler networkHandler;
    public static CFontRenderer fontRenderer;
    public static CFontRenderer2 fontRenderer2;
    public static CFontRenderer3 fontRenderer3;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static KonsaRotationManager krotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    public static TimerManager timerManager;
    @Mod.Instance
    public static Thunderhack INSTANCE;
    private static boolean unloaded;
    public static Shaders shaders;
    public static float TICK_TIMER;
    
    public static void load() {
        Thunderhack.LOGGER.info("\n\nLoading TH");
        Thunderhack.unloaded = false;
        if (Thunderhack.reloadManager != null) {
            Thunderhack.reloadManager.unload();
            Thunderhack.reloadManager = null;
        }
        try {
            Font verdanapro = Font.createFont(0, Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"));
            verdanapro = verdanapro.deriveFont(24.0f);
            Thunderhack.fontRenderer = new CFontRenderer(verdanapro, true, true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            Font verdanapro2 = Font.createFont(0, Thunderhack.class.getResourceAsStream("/fonts/ThunderFont3.ttf"));
            verdanapro2 = verdanapro2.deriveFont(36.0f);
            Thunderhack.fontRenderer2 = new CFontRenderer2(verdanapro2, true, true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            Font verdanapro3 = Font.createFont(0, Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"));
            verdanapro3 = verdanapro3.deriveFont(18.0f);
            Thunderhack.fontRenderer3 = new CFontRenderer3(verdanapro3, true, true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Thunderhack.textManager = new TextManager();
        Thunderhack.krotationManager = new KonsaRotationManager();
        Thunderhack.commandManager = new CommandManager();
        Thunderhack.totemPopManager = new TotemPopManager();
        Thunderhack.friendManager = new FriendManager();
        Thunderhack.moduleManager = new ModuleManager();
        Thunderhack.enemyManager = new EnemyManager();
        Thunderhack.rotationManager = new RotationManager();
        Thunderhack.packetManager = new PacketManager();
        Thunderhack.eventManager = new EventManager();
        Thunderhack.networkHandler = new NetworkHandler();
        Thunderhack.speedManager = new SpeedManager();
        Thunderhack.potionManager = new PotionManager();
        Thunderhack.inventoryManager = new InventoryManager();
        Thunderhack.serverManager = new ServerManager();
        Thunderhack.fileManager = new FileManager();
        Thunderhack.colorManager = new ColorManager();
        Thunderhack.positionManager = new PositionManager();
        Thunderhack.configManager = new ConfigManager();
        Thunderhack.holeManager = new HoleManager();
        Thunderhack.timerManager = new TimerManager();
        Thunderhack.LOGGER.info("Managers loaded.");
        Thunderhack.moduleManager.init();
        Thunderhack.LOGGER.info("Modules loaded.");
        Thunderhack.configManager.init();
        Thunderhack.eventManager.init();
        Thunderhack.timerManager.init();
        Thunderhack.LOGGER.info("EventManager loaded.");
        Thunderhack.textManager.init(true);
        Thunderhack.moduleManager.onLoad();
        Thunderhack.LOGGER.info("successfully loaded!\n");
    }
    
    public static void unload(final boolean unload) {
        Thunderhack.LOGGER.info("\n\nUnloading ");
        if (unload) {
            (Thunderhack.reloadManager = new ReloadManager()).init((Thunderhack.commandManager != null) ? Thunderhack.commandManager.getPrefix() : ".");
        }
        onUnload();
        Thunderhack.eventManager = null;
        Thunderhack.krotationManager = null;
        Thunderhack.friendManager = null;
        Thunderhack.speedManager = null;
        Thunderhack.holeManager = null;
        Thunderhack.fontRenderer = null;
        Thunderhack.enemyManager = null;
        Thunderhack.positionManager = null;
        Thunderhack.rotationManager = null;
        Thunderhack.networkHandler = null;
        Thunderhack.configManager = null;
        Thunderhack.timerManager = null;
        Thunderhack.totemPopManager = null;
        Thunderhack.commandManager = null;
        Thunderhack.colorManager = null;
        Thunderhack.serverManager = null;
        Thunderhack.fileManager = null;
        Thunderhack.potionManager = null;
        Thunderhack.inventoryManager = null;
        Thunderhack.moduleManager = null;
        Thunderhack.textManager = null;
        Thunderhack.LOGGER.info("unloaded!\n");
    }
    
    public static void reload() {
        unload(false);
        load();
    }
    
    public static void onUnload() {
        if (!Thunderhack.unloaded) {
            Thunderhack.eventManager.onUnload();
            Thunderhack.moduleManager.onUnload();
            Thunderhack.configManager.saveConfig(Thunderhack.configManager.config.replaceFirst("ThunderHack/", ""));
            Thunderhack.moduleManager.onUnloadPost();
            Thunderhack.unloaded = true;
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Thunderhack.LOGGER.info("nigger");
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Display.setTitle("ThunderHack+");
        load();
        MinecraftForge.EVENT_BUS.register((Object)Thunderhack.networkHandler);
        Thunderhack.shaders = new Shaders();
    }
    
    static {
        LOGGER = LogManager.getLogger("ThunderHack");
        Thunderhack.unloaded = false;
        Thunderhack.TICK_TIMER = 1.0f;
    }
}
