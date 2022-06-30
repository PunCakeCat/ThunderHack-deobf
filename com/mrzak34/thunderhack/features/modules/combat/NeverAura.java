//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.features.setting.*;

public class NeverAura extends Module
{
    private final Timer blockTimer;
    private final Timer shieldBreakerTimer;
    private double circleAnim;
    private boolean isBlocking;
    private int changeSlotCounter;
    private double direction;
    private double yPos;
    private double progress;
    private float delta;
    private long lastMS;
    private long lastDeltaMS;
    public static Timer oldTimerPvp;
    public static Timer timer;
    public static ColorSetting targetHudColor;
    public static EntityLivingBase target;
    private Setting<targetEspModeEn> targetEspMode;
    private Setting<swingModeEn> swingMode;
    private Setting<rotationModeEn> rotationMode;
    private Setting<targetHudModeEn> targetHudMode;
    private Setting<rotationStrafeModeEn> rotationStrafeMode;
    private Setting<strafeModeEn> strafeMode;
    private Setting<sortEn> sort;
    private Setting<clickModeEn> clickMode;
    public final Setting<ColorSetting> targetEspColor;
    public Setting<Boolean> randomRotation;
    public Setting<Boolean> targetHud;
    public Setting<Boolean> targetEsp;
    public Setting<Boolean> attackInInvetory;
    public Setting<Boolean> clientLook;
    public Setting<Boolean> raycast;
    public Setting<Boolean> onlyCrit;
    public Setting<Boolean> autoDisable;
    public Setting<Boolean> autoBlock;
    public Setting<Boolean> shieldBlockCheck;
    public Setting<Boolean> autoShieldUnPress;
    public Setting<Boolean> shieldFixer;
    public Setting<Boolean> shieldBreaker;
    public Setting<Boolean> usingItemCheck;
    public Setting<Boolean> weaponOnly;
    public Setting<Boolean> sprinting;
    public Setting<Boolean> walls;
    public Setting<Boolean> nakedPlayer;
    public Setting<Boolean> invis;
    public Setting<Boolean> team;
    public Setting<Boolean> animals;
    public Setting<Boolean> pets;
    public Setting<Boolean> monsters;
    public Setting<Boolean> armorStands;
    public Setting<Boolean> players;
    public Setting<Boolean> visualPitch;
    public Setting<Boolean> visualYaw;
    public Setting<Integer> maxSpeedRotation;
    public Setting<Integer> minSpeedRotation;
    public Setting<Integer> points;
    public Setting<Integer> maxAps;
    public Setting<Integer> hitChance;
    public Setting<Integer> breakerDelay;
    public Setting<Integer> minAps;
    public Setting<Float> randomYaw;
    public Setting<Float> randomPitch;
    public Setting<Float> pitchValue;
    public Setting<Float> rotPredict;
    public Setting<Float> circleRange;
    public Setting<Float> attackCoolDown;
    public Setting<Float> fov;
    public Setting<Float> range;
    public Setting<Float> critFallDistance;
    
    public NeverAura() {
        super("NeverAura", "NeverAura", Category.COMBAT, true, false, false);
        this.blockTimer = new Timer();
        this.shieldBreakerTimer = new Timer();
        this.direction = 1.0;
        this.progress = 0.0;
        this.delta = 0.0f;
        this.lastMS = System.currentTimeMillis();
        this.lastDeltaMS = 0L;
        this.targetEspMode = (Setting<targetEspModeEn>)this.register(new Setting("TargetESPMode", (T)targetEspModeEn.Default));
        this.swingMode = (Setting<swingModeEn>)this.register(new Setting("Swing Mode", (T)swingModeEn.Client));
        this.rotationMode = (Setting<rotationModeEn>)this.register(new Setting("Rotation Mode", (T)rotationModeEn.Packet));
        this.targetHudMode = (Setting<targetHudModeEn>)this.register(new Setting("TargetHudMode", (T)targetHudModeEn.Astolfo));
        this.rotationStrafeMode = (Setting<rotationStrafeModeEn>)this.register(new Setting("RotationStrafeMode", (T)rotationStrafeModeEn.Default));
        this.strafeMode = (Setting<strafeModeEn>)this.register(new Setting("StrafeMode", (T)strafeModeEn.None));
        this.sort = (Setting<sortEn>)this.register(new Setting("SortMode", (T)sortEn.Distance));
        this.clickMode = (Setting<clickModeEn>)this.register(new Setting("clickMode", (T)clickModeEn.New));
        this.targetEspColor = (Setting<ColorSetting>)this.register(new Setting("TargetEspColor", (T)new ColorSetting(1354711231)));
        this.randomRotation = (Setting<Boolean>)this.register(new Setting("Random Rotation", (T)false));
        this.targetHud = (Setting<Boolean>)this.register(new Setting("targetHud", (T)false));
        this.targetEsp = (Setting<Boolean>)this.register(new Setting("Target Esp", (T)false));
        this.attackInInvetory = (Setting<Boolean>)this.register(new Setting("attackInInvetory", (T)false));
        this.clientLook = (Setting<Boolean>)this.register(new Setting("Client Look", (T)false));
        this.raycast = (Setting<Boolean>)this.register(new Setting("raycast", (T)false));
        this.onlyCrit = (Setting<Boolean>)this.register(new Setting("Only Critical", (T)false));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("Auto Disable", (T)false));
        this.autoBlock = (Setting<Boolean>)this.register(new Setting("Auto Block", (T)false));
        this.shieldBlockCheck = (Setting<Boolean>)this.register(new Setting("Shield Block Check", (T)false));
        this.autoShieldUnPress = (Setting<Boolean>)this.register(new Setting("Auto Shield UnPress", (T)false));
        this.shieldFixer = (Setting<Boolean>)this.register(new Setting("Shield Sync Fix", (T)false));
        this.shieldBreaker = (Setting<Boolean>)this.register(new Setting("Break Shield", (T)false));
        this.usingItemCheck = (Setting<Boolean>)this.register(new Setting("Using Item Check", (T)false));
        this.weaponOnly = (Setting<Boolean>)this.register(new Setting("Weapon Only", (T)false));
        this.sprinting = (Setting<Boolean>)this.register(new Setting("Stop Sprinting", (T)false));
        this.walls = (Setting<Boolean>)this.register(new Setting("Hit Through Walls Bypass", (T)false));
        this.nakedPlayer = (Setting<Boolean>)this.register(new Setting("Ignore Naked Players", (T)false));
        this.invis = (Setting<Boolean>)this.register(new Setting("Attack Invisible", (T)false));
        this.team = (Setting<Boolean>)this.register(new Setting("Attack Teams", (T)false));
        this.animals = (Setting<Boolean>)this.register(new Setting("Attack Animals", (T)false));
        this.pets = (Setting<Boolean>)this.register(new Setting("Attack Pets", (T)false));
        this.monsters = (Setting<Boolean>)this.register(new Setting("Attack Monsters", (T)false));
        this.armorStands = (Setting<Boolean>)this.register(new Setting("Attack Armor Stands", (T)false));
        this.players = (Setting<Boolean>)this.register(new Setting("Attack Players", (T)false));
        this.visualPitch = (Setting<Boolean>)this.register(new Setting("visualPitch", (T)false));
        this.visualYaw = (Setting<Boolean>)this.register(new Setting("visualYaw", (T)false));
        this.maxSpeedRotation = (Setting<Integer>)this.register(new Setting("Max Speed Rotation", (T)360, (T)0, (T)360));
        this.minSpeedRotation = (Setting<Integer>)this.register(new Setting("OwnCirclePoints", (T)360, (T)0, (T)360));
        this.points = (Setting<Integer>)this.register(new Setting("OwnCirclePoints", (T)32, (T)2, (T)64));
        this.maxAps = (Setting<Integer>)this.register(new Setting("Max CPS", (T)13, (T)1, (T)20));
        this.hitChance = (Setting<Integer>)this.register(new Setting("HitChance", (T)100, (T)1, (T)100));
        this.breakerDelay = (Setting<Integer>)this.register(new Setting("Breaker Delay", (T)100, (T)0, (T)600));
        this.minAps = (Setting<Integer>)this.register(new Setting("Min CPS", (T)12, (T)1, (T)20));
        this.randomYaw = (Setting<Float>)this.register(new Setting("Random Yaw", (T)2.0f, (T)0.0f, (T)4.0f));
        this.randomPitch = (Setting<Float>)this.register(new Setting("Random Pitch", (T)2.0f, (T)0.0f, (T)4.0f));
        this.pitchValue = (Setting<Float>)this.register(new Setting("Pitch Value", (T)0.16f, (T)(-4.0f), (T)4.0f));
        this.rotPredict = (Setting<Float>)this.register(new Setting("Rotation Predict", (T)0.05f, (T)0.0f, (T)10.0f));
        this.circleRange = (Setting<Float>)this.register(new Setting("Circle Range", (T)3.0f, (T)0.1f, (T)6.0f));
        this.attackCoolDown = (Setting<Float>)this.register(new Setting("Cool Down", (T)0.85f, (T)0.1f, (T)1.0f));
        this.fov = (Setting<Float>)this.register(new Setting("FOV", (T)180.0f, (T)5.0f, (T)180.0f));
        this.range = (Setting<Float>)this.register(new Setting("AttackRange", (T)3.8f, (T)3.0f, (T)7.0f));
        this.critFallDistance = (Setting<Float>)this.register(new Setting("Criticals Fall Distance", (T)0.2f, (T)0.1f, (T)1.0f));
    }
    
    static {
        NeverAura.oldTimerPvp = new Timer();
        NeverAura.timer = new Timer();
    }
    
    public enum targetEspModeEn
    {
        Default, 
        Sims, 
        Jello, 
        Astolfo;
    }
    
    public enum swingModeEn
    {
        Client, 
        None, 
        Server;
    }
    
    public enum rotationModeEn
    {
        Packet, 
        None;
    }
    
    public enum targetHudModeEn
    {
        Astolfo, 
        Small, 
        Flux, 
        NovolineOld, 
        NovolineNew;
    }
    
    public enum rotationStrafeModeEn
    {
        Default, 
        Silent;
    }
    
    public enum strafeModeEn
    {
        None, 
        AlwaysF;
    }
    
    public enum sortEn
    {
        Distance, 
        HigherArmor, 
        BlockingStatus, 
        LowestArmor, 
        Health, 
        Angle, 
        HurtTime;
    }
    
    public enum clickModeEn
    {
        Old, 
        New;
    }
}
