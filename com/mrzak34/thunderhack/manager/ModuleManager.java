//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.features.*;
import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.notification.*;
import com.mrzak34.thunderhack.features.gui.hud.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import com.mrzak34.thunderhack.features.modules.funnygame.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import com.mrzak34.thunderhack.features.modules.movement.*;
import com.mrzak34.thunderhack.features.modules.client.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import com.mrzak34.thunderhack.features.gui.thundergui.*;
import net.minecraftforge.common.*;
import java.util.function.*;
import com.mrzak34.thunderhack.event.events.*;
import java.util.stream.*;
import java.util.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.features.gui.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.util.*;
import java.util.concurrent.*;

public class ModuleManager extends Feature
{
    public ArrayList<Module> modules;
    public List<Module> sortedModules;
    public List<String> sortedModulesABC;
    public Animation animationThread;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        this.sortedModules = new ArrayList<Module>();
        this.sortedModulesABC = new ArrayList<String>();
    }
    
    public void init() {
        this.modules.add((Module)new ClickGui());
        this.modules.add((Module)new FontMod());
        this.modules.add((Module)new ExtraTab());
        this.modules.add((Module)new BlockHighlight());
        this.modules.add((Module)new TickShift());
        this.modules.add((Module)new StorageEsp());
        this.modules.add((Module)new Ambience());
        this.modules.add((Module)new BowAim());
        this.modules.add((Module)new FluxLongJump());
        this.modules.add((Module)new PacketEat());
        this.modules.add((Module)new HighJump());
        this.modules.add((Module)new AnticheatEm());
        this.modules.add((Module)new Interactions());
        this.modules.add((Module)new MotionBlur());
        this.modules.add((Module)new ThirdPersView());
        this.modules.add((Module)new NoCom());
        this.modules.add((Module)new SilentBow());
        this.modules.add((Module)new Sprint());
        this.modules.add((Module)new AutoMine());
        this.modules.add((Module)new Particles());
        this.modules.add((Module)new FreeLook());
        this.modules.add((Module)new Quiver());
        this.modules.add((Module)new NoFall());
        this.modules.add((Module)new AutoReconnect());
        this.modules.add((Module)new LevitationControl());
        this.modules.add((Module)new PacketRender());
        this.modules.add((Module)new CustomEnchants());
        this.modules.add((Module)new HoleESP());
        this.modules.add((Module)new Skeleton());
        this.modules.add((Module)new Trajectories());
        this.modules.add((Module)new NewSpeed());
        this.modules.add((Module)new FakePlayer());
        this.modules.add((Module)new TpsSync());
        this.modules.add((Module)new ItemShaders());
        this.modules.add((Module)new ThunderHackGui());
        this.modules.add((Module)new ElytraSwap());
        this.modules.add((Module)new VisualRange());
        this.modules.add((Module)new PredictESP());
        this.modules.add((Module)new StashFinder());
        this.modules.add((Module)new AntiBot());
        this.modules.add((Module)new AutoFish());
        this.modules.add((Module)new PlayerTrails());
        this.modules.add((Module)new FreeCam());
        this.modules.add((Module)new AFKtimer());
        this.modules.add((Module)new PacketFly());
        this.modules.add((Module)new Windows());
        this.modules.add((Module)new SpeedNom());
        this.modules.add((Module)new Timer());
        this.modules.add((Module)new AutoTrap());
        this.modules.add((Module)new NoEntityTrace());
        this.modules.add((Module)new PistonAura());
        this.modules.add((Module)new LiquidInteract());
        this.modules.add((Module)new Weather());
        this.modules.add((Module)new Radar());
        this.modules.add((Module)new EChestFarmer());
        this.modules.add((Module)new CrystalModifier());
        this.modules.add((Module)new SeedOverlay());
        this.modules.add((Module)new MiddleClick());
        this.modules.add((Module)new NoInterp());
        this.modules.add((Module)new CordExploit());
        this.modules.add((Module)new Anchor());
        this.modules.add((Module)new HitParticles());
        this.modules.add(new NotificationManager());
        this.modules.add((Module)new Speedmine());
        this.modules.add((Module)new NoVoid());
        this.modules.add((Module)new NoHandShake());
        this.modules.add((Module)new BuildHeight());
        this.modules.add((Module)new WTap());
        this.modules.add((Module)new ChatTweaks());
        this.modules.add((Module)new AutoRegear());
        this.modules.add((Module)new FastUse());
        this.modules.add((Module)new PushAttack());
        this.modules.add((Module)new ArmorBreaker());
        this.modules.add((Module)new ShiftInterp());
        this.modules.add((Module)new AutoGG());
        this.modules.add((Module)new OffhandRewrite());
        this.modules.add((Module)new Trails());
        this.modules.add((Module)new ElytraFlight());
        this.modules.add((Module)new RusherScaffold());
        this.modules.add((Module)new CordExploit());
        this.modules.add((Module)new ChorusDelay());
        this.modules.add((Module)new PortalGodMode());
        this.modules.add((Module)new FpsCounter());
        this.modules.add((Module)new TPSCounter());
        this.modules.add((Module)new WaterMark());
        this.modules.add((Module)new Player());
        this.modules.add((Module)new Speed());
        this.modules.add((Module)new ArmorHud());
        this.modules.add((Module)new HClip());
        this.modules.add((Module)new EntityDesync());
        this.modules.add((Module)new Surround());
        this.modules.add((Module)new LogoutSpots());
        this.modules.add((Module)new LegitStrafe());
        this.modules.add((Module)new CevBreaker());
        this.modules.add((Module)new RBandESP());
        this.modules.add((Module)new LegitScaff());
        this.modules.add((Module)new PopChams());
        this.modules.add((Module)new FlightPitch());
        this.modules.add((Module)new ContainerPreviewModule());
        this.modules.add((Module)new AutoWeb());
        this.modules.add((Module)new EbatteSratte());
        this.modules.add((Module)new RPC());
        this.modules.add((Module)new ViewModel());
        this.modules.add((Module)new KillauraOld());
        this.modules.add((Module)new NewAC());
        this.modules.add((Module)new NoRender());
        this.modules.add((Module)new VoidESP());
        this.modules.add((Module)new TunnelESP());
        this.modules.add((Module)new Criticals());
        this.modules.add((Module)new HoleFiller());
        this.modules.add((Module)new Skippy());
        this.modules.add((Module)new EzingKids());
        this.modules.add((Module)new IceSpeed());
        this.modules.add((Module)new Shaders());
        this.modules.add((Module)new ChestStealer());
        this.modules.add((Module)new InvManager());
        this.modules.add((Module)new Speedmine2());
        this.modules.add((Module)new FastPlace2());
        this.modules.add((Module)new AutoArmor());
        this.modules.add((Module)new PacketCounter());
        this.modules.add((Module)new ChorusESP());
        this.modules.add((Module)new Selftrap());
        this.modules.add((Module)new Burrow());
        this.modules.add((Module)new AntiHunger());
        this.modules.add((Module)new FullBright());
        this.modules.add((Module)new Velocity());
        this.modules.add((Module)new NameTags());
        this.modules.add((Module)new AutoTPaccept());
        this.modules.add((Module)new AutoDupe());
        this.modules.add((Module)new XRay());
        this.modules.add((Module)new com.mrzak34.thunderhack.features.gui.hud.ArrayList());
        this.modules.add((Module)new Coords());
        this.modules.add((Module)new KillEffect());
        this.modules.add((Module)new TargetStrafe());
        this.modules.add((Module)new EZbowPOP());
        this.modules.add((Module)new BowSpam());
        this.modules.add((Module)new ItemESP());
        this.modules.add((Module)new DMGParticles());
        this.modules.add((Module)new PlayerTweaks());
        this.modules.add((Module)new AutoRespawn());
        this.modules.add((Module)new PhotoMath());
        this.modules.add((Module)new KDShop());
        this.modules.add((Module)new NoSlow());
        this.modules.add((Module)new AntiBowBomb());
        this.modules.add((Module)new EffectsRemover());
        this.modules.add((Module)new TrueDurability());
        this.modules.add((Module)new WexsideTPS());
        this.modules.add((Module)new WexsideSpeed());
        this.modules.add((Module)new WexsideArmor());
        this.modules.add((Module)new AntiTittle());
        this.modules.add((Module)new CoolCrosshair());
        this.modules.add((Module)new AutoCappRegear());
        this.modules.add((Module)new ToolTips());
        this.modules.add((Module)new PluginsExploit());
        this.modules.add((Module)new HudEditor());
        this.modules.add((Module)new AutoPot());
        this.modules.add((Module)new RHSpeed());
        this.modules.add((Module)new ACRender());
        this.modules.add((Module)new DotaHud());
        this.modules.add((Module)new Step());
        this.modules.add((Module)new Animations());
        this.modules.add((Module)new StreamerMode());
        this.modules.add((Module)new ImageESP());
        this.modules.add((Module)new FastPlace());
        this.modules.add((Module)new PyroRadar());
        this.modules.add((Module)new JumpCircle());
    }
    
    public Module getModuleByName(final String name) {
        for (final Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public <T extends Module> T getModuleByClass(final Class<T> clazz) {
        for (final Module module : this.modules) {
            if (!clazz.isInstance(module)) {
                continue;
            }
            return (T)module;
        }
        return null;
    }
    
    public void enableModule(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        if (module != null) {
            module.disable();
        }
    }
    
    public void enableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.enable();
        }
    }
    
    public void disableModule(final String name) {
        final Module module = this.getModuleByName(name);
        if (module != null) {
            module.disable();
        }
    }
    
    public boolean isModuleEnabled(final String name) {
        final Module module = this.getModuleByName(name);
        return module != null && module.isOn();
    }
    
    public boolean isModuleEnabled(final Class<Module> clazz) {
        final Module module = this.getModuleByClass(clazz);
        return module != null && module.isOn();
    }
    
    public Module getModuleByDisplayName(final String displayName) {
        for (final Module module : this.modules) {
            if (!module.getDisplayName().equalsIgnoreCase(displayName)) {
                continue;
            }
            return module;
        }
        return null;
    }
    
    public ArrayList<Module> getEnabledModules() {
        final ArrayList<Module> enabledModules = new ArrayList<Module>();
        for (final Module module : this.modules) {
            if (!module.isEnabled()) {
                continue;
            }
            enabledModules.add(module);
        }
        return enabledModules;
    }
    
    public ArrayList<String> getEnabledModulesName() {
        final ArrayList<String> enabledModules = new ArrayList<String>();
        for (final Module module : this.modules) {
            if (module.isEnabled()) {
                if (!module.isDrawn()) {
                    continue;
                }
                enabledModules.add(module.getFullArrayString());
            }
        }
        return enabledModules;
    }
    
    public ArrayList<Module> getModulesByCategory(final Module.Category category) {
        final ArrayList<Module> modulesCategory = new ArrayList<Module>();
        final ArrayList<Module> list;
        this.modules.forEach(module -> {
            if (module.getCategory() == category) {
                list.add(module);
            }
            return;
        });
        return modulesCategory;
    }
    
    public ArrayList<Module> getModulesByCategory(final ThunderGui.Categories aboba) {
        ArrayList<Module> gingerbread = null;
        if (aboba == ThunderGui.Categories.FUNNYGAME) {
            gingerbread = this.getModulesByCategory(Module.Category.FUNNYGAME);
        }
        if (aboba == ThunderGui.Categories.COMBAT) {
            gingerbread = this.getModulesByCategory(Module.Category.COMBAT);
        }
        if (aboba == ThunderGui.Categories.MOVEMENT) {
            gingerbread = this.getModulesByCategory(Module.Category.MOVEMENT);
        }
        if (aboba == ThunderGui.Categories.MISC) {
            gingerbread = this.getModulesByCategory(Module.Category.MISC);
        }
        if (aboba == ThunderGui.Categories.CLIENT) {
            gingerbread = this.getModulesByCategory(Module.Category.CLIENT);
        }
        if (aboba == ThunderGui.Categories.RENDER) {
            gingerbread = this.getModulesByCategory(Module.Category.RENDER);
        }
        if (aboba == ThunderGui.Categories.PLAYER) {
            gingerbread = this.getModulesByCategory(Module.Category.PLAYER);
        }
        if (aboba == ThunderGui.Categories.HUD) {
            gingerbread = this.getModulesByCategory(Module.Category.HUD);
        }
        return gingerbread;
    }
    
    public List<Module.Category> getCategories() {
        return Arrays.asList(Module.Category.values());
    }
    
    public void onLoad() {
        this.modules.stream().filter(Module::listening).forEach(MinecraftForge.EVENT_BUS::register);
        this.modules.forEach(Module::onLoad);
    }
    
    public void onUpdate() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
    }
    
    public void onTick() {
        this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
    }
    
    public void onRender2D(final Render2DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender2D(event));
    }
    
    public void onRender3D(final Render3DEvent event) {
        this.modules.stream().filter(Feature::isEnabled).forEach(module -> module.onRender3D(event));
    }
    
    public void sortModules(final boolean reverse) {
        this.sortedModules = this.getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> this.renderer.getStringWidth(module.getFullArrayString()) * (reverse ? -1 : 1))).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    public void sortModulesABC() {
        (this.sortedModulesABC = new ArrayList<String>(this.getEnabledModulesName())).sort(String.CASE_INSENSITIVE_ORDER);
    }
    
    public void onLogout() {
        this.modules.forEach(Module::onLogout);
    }
    
    public void onLogin() {
        this.modules.forEach(Module::onLogin);
    }
    
    public void onUnload() {
        this.modules.forEach(MinecraftForge.EVENT_BUS::unregister);
        this.modules.forEach(Module::onUnload);
    }
    
    public void onUnloadPost() {
        for (final Module module : this.modules) {
            module.enabled.setValue((Object)false);
        }
    }
    
    public void onKeyPressed(final int eventKey) {
        if (eventKey == 0 || !Keyboard.getEventKeyState() || ModuleManager.mc.currentScreen instanceof OyVeyGui) {
            return;
        }
        this.modules.forEach(module -> {
            if (module.getBind().getKey() == eventKey) {
                module.toggle();
            }
        });
    }
    
    private class Animation extends Thread
    {
        public Module module;
        public float offset;
        public float vOffset;
        ScheduledExecutorService service;
        
        public Animation() {
            super("Animation");
            this.service = Executors.newSingleThreadScheduledExecutor();
        }
        
        @Override
        public void run() {
            for (final String e : ModuleManager.this.sortedModulesABC) {
                final Module module = Thunderhack.moduleManager.getModuleByName(e);
                final String text = module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
                module.offset = ModuleManager.this.renderer.getStringWidth(text) / (float)com.mrzak34.thunderhack.features.gui.hud.ArrayList.getInstance().animationHorizontalTime.getValue();
                module.vOffset = ModuleManager.this.renderer.getFontHeight() / (float)com.mrzak34.thunderhack.features.gui.hud.ArrayList.getInstance().animationVerticalTime.getValue();
                if (module.isEnabled() && (int)com.mrzak34.thunderhack.features.gui.hud.ArrayList.getInstance().animationHorizontalTime.getValue() != 1) {
                    if (module.arrayListOffset <= module.offset) {
                        continue;
                    }
                    if (Util.mc.world == null) {
                        continue;
                    }
                    final Module module2 = module;
                    module2.arrayListOffset -= module.offset;
                    module.sliding = true;
                }
                else {
                    if (!module.isDisabled()) {
                        continue;
                    }
                    if ((int)com.mrzak34.thunderhack.features.gui.hud.ArrayList.getInstance().animationHorizontalTime.getValue() == 1) {
                        continue;
                    }
                    if (module.arrayListOffset < ModuleManager.this.renderer.getStringWidth(text) && Util.mc.world != null) {
                        final Module module3 = module;
                        module3.arrayListOffset += module.offset;
                        module.sliding = true;
                    }
                    else {
                        module.sliding = false;
                    }
                }
            }
        }
        
        @Override
        public void start() {
            System.out.println("Starting animation thread.");
            this.service.scheduleAtFixedRate(this, 0L, 1L, TimeUnit.MILLISECONDS);
        }
    }
}
