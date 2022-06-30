//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.features.modules.movement.*;
import net.minecraft.client.network.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.util.*;

public class NewAC extends Module
{
    private static NewAC INSTANCE;
    public Setting<Parent> antiCheat;
    public Setting<TimingMode> timingMode;
    public Setting<RotationMode> rotationMode;
    public Setting<Boolean> inhibit;
    public Setting<Boolean> limit;
    public Setting<YawStepMode> yawStep;
    public Setting<Float> yawAngle;
    public Setting<Integer> yawTicks;
    public Setting<Boolean> strictDirection;
    public Setting<Parent> placements;
    public Setting<Boolean> check;
    public Setting<DirectionMode> directionMode;
    public Setting<Boolean> protocol;
    public Setting<Boolean> liquids;
    public Setting<Boolean> fire;
    public Setting<Parent> speeds;
    public Setting<Boolean> adaptivedel;
    public Setting<Boolean> hurttimesync;
    public Setting<Integer> adpassive;
    public Setting<Integer> adactive;
    public Setting<ConfirmMode> confirm;
    public Setting<Integer> delay;
    public Setting<Integer> attackFactor;
    public Setting<Float> breakSpeed;
    public Setting<Float> placeSpeed;
    public Setting<SyncMode> syncMode;
    public Setting<Float> mergeOffset;
    public Setting<Parent> ranges;
    public Setting<Float> enemyRange;
    public Setting<Float> crystalRange;
    public Setting<Float> breakRange;
    public Setting<Float> breakWallsRange;
    public Setting<Float> placeRange;
    public Setting<Float> placeWallsRange;
    public Setting<Parent> swap;
    public Setting<Boolean> autoSwap;
    public Setting<Float> swapDelay;
    public Setting<Float> switchDelay;
    public Setting<Boolean> antiWeakness;
    public Setting<Parent> damages;
    public Setting<TargetingMode> targetingMode;
    public Setting<Float> security;
    public Setting<Float> compromise;
    public Setting<Float> minPlaceDamage;
    public Setting<Float> maxSelfPlace;
    public Setting<Float> suicideHealth;
    public Setting<Float> faceplaceHealth;
    public Setting<SubBind> forceFaceplace;
    private final Setting<Boolean> armorBreaker;
    private final Setting<Float> depletion;
    public Setting<Parent> prediction;
    public Setting<Integer> predictTicks;
    public Setting<Boolean> predictPops;
    public Setting<Boolean> terrainIgnore;
    public Setting<Parent> pause;
    public Setting<Boolean> noMineSwitch;
    public Setting<Boolean> noGapSwitch;
    public Setting<Boolean> rightClickGap;
    public Setting<Boolean> disableWhenKA;
    public Setting<Boolean> disableWhenPA;
    public Setting<Float> disableUnderHealth;
    public Setting<Boolean> disableOnTP;
    public Setting<Parent> render;
    public Setting<Boolean> swing;
    public Setting<Boolean> renderBox;
    public Setting<Boolean> renderBreaking;
    public Setting<Float> outlineWidth;
    public Setting<RenderTextMode> renderDmg;
    public Setting<Boolean> targetRender;
    public Setting<Boolean> depth;
    public Setting<Boolean> fill;
    public Setting<Boolean> orbit;
    public Setting<Boolean> trial;
    public Setting<Float> orbitSpeed;
    public Setting<Float> animationSpeed;
    public Setting<Float> circleWidth;
    public final Setting<ColorSetting> color;
    public final Setting<ColorSetting> outlineColor;
    public final Setting<ColorSetting> circleColor;
    public Vec3d rotationVector;
    public float[] rotations;
    public Timer rotationTimer;
    private EntityEnderCrystal postBreakPos;
    private BlockPos postPlacePos;
    private BlockPos prevPlacePos;
    private EnumFacing postFacing;
    private RayTraceResult postResult;
    public final Timer placeTimer;
    public final Timer breakTimer;
    public final Timer noGhostTimer;
    public final Timer switchTimer;
    public BlockPos renderBlock;
    public float renderDamage;
    public final Timer renderTimeoutTimer;
    public BlockPos renderBreakingPos;
    public final Timer renderBreakingTimer;
    public boolean isPlacing;
    public final ConcurrentHashMap<BlockPos, Long> placeLocations;
    public final ConcurrentHashMap<Integer, Long> breakLocations;
    public final Map<EntityPlayer, Timer> totemPops;
    public final List<BlockPos> selfPlacePositions;
    public AtomicBoolean tickRunning;
    public final Timer linearTimer;
    public final Timer cacheTimer;
    public BlockPos cachePos;
    public final Timer inhibitTimer;
    public EntityEnderCrystal inhibitEntity;
    private final Timer scatterTimer;
    private Vec3d bilateralVec;
    private Thread thread;
    private AtomicBoolean shouldRunThread;
    private AtomicBoolean lastBroken;
    private EntityPlayer renderTarget;
    private Timer renderTargetTimer;
    private int ticks;
    private boolean foundDoublePop;
    private static float aboba;
    
    public NewAC() {
        super("AutoCrystal", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0441\u0442\u0430\u0432\u0438\u0442-\u0438 \u043b\u043e\u043c\u0430\u0435\u0442 \u043a\u0440\u0438\u0441\u0442\u0430\u043b\u044b", Category.COMBAT, true, false, false);
        this.antiCheat = (Setting<Parent>)this.register(new Setting("AntiCheat", (T)new Parent(false)));
        this.timingMode = this.register(new Setting("Timing", (T)TimingMode.ADAPTIVE)).withParent(this.antiCheat);
        this.rotationMode = this.register(new Setting("Rotate", (T)RotationMode.TRACK)).withParent(this.antiCheat);
        this.inhibit = this.register(new Setting("Inhibit", (T)false)).withParent(this.antiCheat);
        this.limit = this.register(new Setting("Limit", (T)true)).withParent(this.antiCheat);
        this.yawStep = this.register(new Setting("YawStep", (T)YawStepMode.OFF)).withParent(this.antiCheat);
        this.yawAngle = this.register(new Setting("YawAngle", (T)0.3f, (T)0.1f, (T)1.0f)).withParent(this.antiCheat);
        this.yawTicks = this.register(new Setting("YawTicks", (T)1, (T)1, (T)5)).withParent(this.antiCheat);
        this.strictDirection = this.register(new Setting("StrictDirection", (T)true)).withParent(this.antiCheat);
        this.placements = (Setting<Parent>)this.register(new Setting("Placements", (T)new Parent(false)));
        this.check = this.register(new Setting("Check", (T)true)).withParent(this.placements);
        this.directionMode = this.register(new Setting("Interact", (T)DirectionMode.STRICT)).withParent(this.placements);
        this.protocol = this.register(new Setting("Protocol", (T)false)).withParent(this.placements);
        this.liquids = this.register(new Setting("PlaceInLiquids", (T)false)).withParent(this.placements);
        this.fire = this.register(new Setting("PlaceInFire", (T)false)).withParent(this.placements);
        this.speeds = (Setting<Parent>)this.register(new Setting("Speeds", (T)new Parent(false)));
        this.adaptivedel = this.register(new Setting("AdaptiveDelay", (T)true)).withParent(this.placements);
        this.hurttimesync = this.register(new Setting("HTSync", (T)true)).withParent(this.placements);
        this.adpassive = this.register(new Setting("ADpassive", (T)10, (T)0, (T)20)).withParent(this.speeds);
        this.adactive = this.register(new Setting("ADactive", (T)0, (T)0, (T)20)).withParent(this.speeds);
        this.confirm = this.register(new Setting("Confirm", (T)ConfirmMode.OFF)).withParent(this.speeds);
        this.delay = this.register(new Setting("Delay", (T)0, (T)0, (T)20)).withParent(this.speeds);
        this.attackFactor = this.register(new Setting("AttackFactor", (T)3, (T)1, (T)20)).withParent(this.speeds);
        this.breakSpeed = this.register(new Setting("BreakSpeed", (T)20.0f, (T)1.0f, (T)20.0f)).withParent(this.speeds);
        this.placeSpeed = this.register(new Setting("PlaceSpeed", (T)20.0f, (T)2.0f, (T)20.0f)).withParent(this.speeds);
        this.syncMode = this.register(new Setting("Sync", (T)SyncMode.STRICT)).withParent(this.speeds);
        this.mergeOffset = this.register(new Setting("Offset", (T)0.0f, (T)0.0f, (T)8.0f)).withParent(this.speeds);
        this.ranges = (Setting<Parent>)this.register(new Setting("Ranges", (T)new Parent(false)));
        this.enemyRange = this.register(new Setting("EnemyRange", (T)8.0f, (T)4.0f, (T)15.0f)).withParent(this.ranges);
        this.crystalRange = this.register(new Setting("CrystalRange", (T)6.0f, (T)2.0f, (T)12.0f)).withParent(this.ranges);
        this.breakRange = this.register(new Setting("BreakRange", (T)4.3f, (T)1.0f, (T)6.0f)).withParent(this.ranges);
        this.breakWallsRange = this.register(new Setting("BreakWalls", (T)1.5f, (T)1.0f, (T)6.0f)).withParent(this.ranges);
        this.placeRange = this.register(new Setting("PlaceRange", (T)4.0f, (T)1.0f, (T)6.0f)).withParent(this.ranges);
        this.placeWallsRange = this.register(new Setting("PlaceWalls", (T)3.0f, (T)1.0f, (T)6.0f)).withParent(this.ranges);
        this.swap = (Setting<Parent>)this.register(new Setting("Swap", (T)new Parent(false)));
        this.autoSwap = this.register(new Setting("AutoSwap", (T)true)).withParent(this.swap);
        this.swapDelay = this.register(new Setting("SwapDelay", (T)1.0f, (T)0.0f, (T)20.0f)).withParent(this.swap);
        this.switchDelay = this.register(new Setting("GhostDelay", (T)5.0f, (T)0.0f, (T)10.0f)).withParent(this.swap);
        this.antiWeakness = this.register(new Setting("AntiWeakness", (T)false)).withParent(this.swap);
        this.damages = (Setting<Parent>)this.register(new Setting("Damages", (T)new Parent(false)));
        this.targetingMode = this.register(new Setting("Target", (T)TargetingMode.ALL)).withParent(this.damages);
        this.security = this.register(new Setting("Security", (T)1.0f, (T)0.1f, (T)5.0f)).withParent(this.damages);
        this.compromise = this.register(new Setting("Compromise", (T)1.0f, (T)0.05f, (T)2.0f)).withParent(this.damages);
        this.minPlaceDamage = this.register(new Setting("MinDamage", (T)6.0f, (T)0.0f, (T)20.0f)).withParent(this.damages);
        this.maxSelfPlace = this.register(new Setting("MaxSelfDmg", (T)12.0f, (T)0.0f, (T)20.0f)).withParent(this.damages);
        this.suicideHealth = this.register(new Setting("SuicideHealth", (T)2.0f, (T)0.0f, (T)10.0f)).withParent(this.damages);
        this.faceplaceHealth = this.register(new Setting("FaceplaceHealth", (T)4.0f, (T)0.0f, (T)20.0f)).withParent(this.damages);
        this.forceFaceplace = this.register(new Setting("Faceplace", (T)new SubBind(56))).withParent(this.damages);
        this.armorBreaker = this.register(new Setting("ArmorBreaker", (T)true)).withParent(this.damages);
        this.depletion = this.register(new Setting("Depletion", (T)0.9f, (T)0.1f, (T)1.0f, v -> this.armorBreaker.getValue())).withParent(this.damages);
        this.prediction = (Setting<Parent>)this.register(new Setting("Prediction", (T)new Parent(false)));
        this.predictTicks = this.register(new Setting("PredictTicks", (T)1, (T)0, (T)10, v -> this.armorBreaker.getValue())).withParent(this.prediction);
        this.predictPops = this.register(new Setting("PredictPops", (T)false, v -> this.armorBreaker.getValue())).withParent(this.prediction);
        this.terrainIgnore = this.register(new Setting("PredictDestruction", (T)false, v -> this.armorBreaker.getValue())).withParent(this.prediction);
        this.pause = (Setting<Parent>)this.register(new Setting("Pause", (T)new Parent(false)));
        this.noMineSwitch = this.register(new Setting("Mining", (T)false)).withParent(this.pause);
        this.noGapSwitch = this.register(new Setting("Gapping", (T)false)).withParent(this.pause);
        this.rightClickGap = this.register(new Setting("RightClickGap", (T)false, v -> this.noGapSwitch.getValue())).withParent(this.pause);
        this.disableWhenKA = this.register(new Setting("KillAura", (T)true)).withParent(this.pause);
        this.disableWhenPA = this.register(new Setting("PistonAura", (T)true)).withParent(this.pause);
        this.disableUnderHealth = this.register(new Setting("Health", (T)2.0f, (T)0.0f, (T)10.0f)).withParent(this.pause);
        this.disableOnTP = this.register(new Setting("DisableOnTP", (T)false)).withParent(this.pause);
        this.render = (Setting<Parent>)this.register(new Setting("Render", (T)new Parent(false)));
        this.swing = this.register(new Setting("Swing", (T)false)).withParent(this.render);
        this.renderBox = this.register(new Setting("Box", (T)true)).withParent(this.render);
        this.renderBreaking = this.register(new Setting("Breaking", (T)true)).withParent(this.render);
        this.outlineWidth = this.register(new Setting("OutlineWidth", (T)1.5f, (T)0.0f, (T)5.0f, v -> this.renderBox.getValue())).withParent(this.render);
        this.renderDmg = this.register(new Setting("Damage", (T)RenderTextMode.NONE)).withParent(this.render);
        this.targetRender = this.register(new Setting("TargetRender", (T)true)).withParent(this.render);
        this.depth = this.register(new Setting("Depth", (T)true)).withParent(this.render);
        this.fill = this.register(new Setting("Fill", (T)false)).withParent(this.render);
        this.orbit = this.register(new Setting("Orbit", (T)true)).withParent(this.render);
        this.trial = this.register(new Setting("Trail", (T)true)).withParent(this.render);
        this.orbitSpeed = this.register(new Setting("OrbitSpeed", (T)1.0f, (T)0.1f, (T)10.0f)).withParent(this.render);
        this.animationSpeed = this.register(new Setting("AnimSpeed", (T)1.0f, (T)0.1f, (T)10.0f)).withParent(this.damages);
        this.circleWidth = this.register(new Setting("Width", (T)2.5f, (T)0.1f, (T)5.0f)).withParent(this.render);
        this.color = (Setting<ColorSetting>)this.register(new Setting("Box Color", (T)new ColorSetting(1354711231)));
        this.outlineColor = (Setting<ColorSetting>)this.register(new Setting("Outline Color", (T)new ColorSetting(-4243265)));
        this.circleColor = (Setting<ColorSetting>)this.register(new Setting("Circle Color", (T)new ColorSetting(869950564)));
        this.rotationVector = null;
        this.rotations = new float[] { 0.0f, 0.0f };
        this.rotationTimer = new Timer();
        this.prevPlacePos = null;
        this.placeTimer = new Timer();
        this.breakTimer = new Timer();
        this.noGhostTimer = new Timer();
        this.switchTimer = new Timer();
        this.renderDamage = 0.0f;
        this.renderTimeoutTimer = new Timer();
        this.renderBreakingTimer = new Timer();
        this.isPlacing = false;
        this.placeLocations = new ConcurrentHashMap<BlockPos, Long>();
        this.breakLocations = new ConcurrentHashMap<Integer, Long>();
        this.totemPops = new ConcurrentHashMap<EntityPlayer, Timer>();
        this.selfPlacePositions = new CopyOnWriteArrayList<BlockPos>();
        this.tickRunning = new AtomicBoolean(false);
        this.linearTimer = new Timer();
        this.cacheTimer = new Timer();
        this.cachePos = null;
        this.inhibitTimer = new Timer();
        this.inhibitEntity = null;
        this.scatterTimer = new Timer();
        this.bilateralVec = null;
        this.shouldRunThread = new AtomicBoolean(false);
        this.lastBroken = new AtomicBoolean(false);
        this.renderTargetTimer = new Timer();
        this.foundDoublePop = false;
        this.setInstance();
    }
    
    public static NewAC getInstance() {
        if (NewAC.INSTANCE == null) {
            NewAC.INSTANCE = new NewAC();
        }
        return NewAC.INSTANCE;
    }
    
    private void setInstance() {
        NewAC.INSTANCE = this;
    }
    
    @Override
    public void onDisable() {
        if (Thunderhack.moduleManager.getModuleByClass(ACRender.class).isOn()) {
            Thunderhack.moduleManager.getModuleByClass(ACRender.class).toggle();
        }
    }
    
    @Override
    public void onEnable() {
        if (Thunderhack.moduleManager.getModuleByClass(ACRender.class).isOff()) {
            Thunderhack.moduleManager.getModuleByClass(ACRender.class).toggle();
        }
        this.postBreakPos = null;
        this.postPlacePos = null;
        this.postFacing = null;
        this.postResult = null;
        this.prevPlacePos = null;
        this.cachePos = null;
        this.bilateralVec = null;
        this.lastBroken.set(false);
        this.rotationVector = null;
        this.rotationTimer.reset();
        this.isPlacing = false;
        this.foundDoublePop = false;
        this.totemPops.clear();
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (NewAC.mc.world == null || NewAC.mc.player == null) {
            return;
        }
        if (this.renderBox.getValue() && this.renderBlock != null) {
            if (this.renderTimeoutTimer.passedMs(1000L)) {
                return;
            }
            AxisAlignedBB axisAlignedBB = null;
            try {
                axisAlignedBB = NewAC.mc.world.getBlockState(this.renderBlock).getBoundingBox((IBlockAccess)NewAC.mc.world, this.renderBlock).offset(this.renderBlock);
            }
            catch (Exception ex) {}
            if (axisAlignedBB == null) {
                return;
            }
            try {
                RenderUtil.drawBoxESP(this.renderBlock, this.color.getValue().getColorObject(), false, this.outlineColor.getValue().getColorObject(), this.outlineWidth.getValue(), true, true, this.color.getValue().getAlpha(), false);
            }
            catch (Exception ex2) {}
        }
        if (this.renderBreaking.getValue() && this.renderBreakingPos != null && !this.renderBreakingTimer.passedMs(1000L) && !this.renderBreakingPos.equals((Object)this.renderBlock)) {
            AxisAlignedBB axisAlignedBB = null;
            try {
                axisAlignedBB = NewAC.mc.world.getBlockState(this.renderBreakingPos).getBoundingBox((IBlockAccess)NewAC.mc.world, this.renderBreakingPos).offset(this.renderBreakingPos);
            }
            catch (Exception ex3) {}
            if (axisAlignedBB == null) {
                return;
            }
            BlockRenderUtil.prepareGL();
            try {
                RenderUtil.drawBoxESP(this.renderBlock, this.color.getValue().getColorObject(), false, this.outlineColor.getValue().getColorObject(), this.outlineWidth.getValue(), true, true, this.color.getValue().getAlpha(), false);
            }
            catch (Exception ex4) {}
            BlockRenderUtil.releaseGL();
        }
        if (this.targetRender.getValue() && this.renderTarget != null && !this.renderTargetTimer.passedMs(3500L)) {
            GlStateManager.pushMatrix();
            BlockRenderUtil.prepareGL();
            if (this.depth.getValue()) {
                GlStateManager.enableDepth();
            }
            final IRenderManager renderManager = (IRenderManager)NewAC.mc.getRenderManager();
            final float[] hsb = Color.RGBtoHSB(this.circleColor.getValue().getRed(), this.circleColor.getValue().getGreen(), this.circleColor.getValue().getBlue(), null);
            float hue;
            final float initialHue = hue = System.currentTimeMillis() % 7200L / 7200.0f;
            int rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
            final ArrayList<Vec3d> vecs = new ArrayList<Vec3d>();
            final double x = this.renderTarget.lastTickPosX + (this.renderTarget.posX - this.renderTarget.lastTickPosX) * event.getPartialTicks() - renderManager.getRenderPosX();
            final double y = this.renderTarget.lastTickPosY + (this.renderTarget.posY - this.renderTarget.lastTickPosY) * event.getPartialTicks() - renderManager.getRenderPosY();
            final double z = this.renderTarget.lastTickPosZ + (this.renderTarget.posZ - this.renderTarget.lastTickPosZ) * event.getPartialTicks() - renderManager.getRenderPosZ();
            final double height = -Math.cos(System.currentTimeMillis() / 1000.0 * this.animationSpeed.getValue()) * (this.renderTarget.height / 2.0) + this.renderTarget.height / 2.0;
            GL11.glLineWidth((float)this.circleWidth.getValue());
            GL11.glBegin(1);
            for (int i = 0; i <= 360; ++i) {
                final Vec3d vec = new Vec3d(x + Math.sin(i * 3.141592653589793 / 180.0) * 0.5, y + height + 0.01, z + Math.cos(i * 3.141592653589793 / 180.0) * 0.5);
                vecs.add(vec);
            }
            for (int j = 0; j < vecs.size() - 1; ++j) {
                final int red = rgb >> 16 & 0xFF;
                final int green = rgb >> 8 & 0xFF;
                final int blue = rgb & 0xFF;
                final float alpha = this.orbit.getValue() ? (this.trial.getValue() ? ((float)Math.max(0.0, -0.3183098861837907 * Math.atan(Math.tan(3.141592653589793 * (j + 1.0f) / (float)vecs.size() + System.currentTimeMillis() / 1000.0 * this.orbitSpeed.getValue())))) : ((float)Math.max(0.0, Math.abs(Math.sin((j + 1.0f) / vecs.size() * 3.141592653589793 + System.currentTimeMillis() / 1000.0 * this.orbitSpeed.getValue())) * 2.0 - 1.0))) : (this.fill.getValue() ? 1.0f : (this.circleColor.getValue().getAlpha() / 255.0f));
                GL11.glColor4f(this.circleColor.getValue().getRed() / 255.0f, this.circleColor.getValue().getGreen() / 255.0f, this.circleColor.getValue().getBlue() / 255.0f, alpha);
                GL11.glVertex3d(vecs.get(j).x, vecs.get(j).y, vecs.get(j).z);
                GL11.glVertex3d(vecs.get(j + 1).x, vecs.get(j + 1).y, vecs.get(j + 1).z);
                hue += 0.0027777778f;
                rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
            }
            GL11.glEnd();
            if (this.fill.getValue()) {
                hue = initialHue;
                GL11.glBegin(9);
                for (int j = 0; j < vecs.size() - 1; ++j) {
                    final int red = rgb >> 16 & 0xFF;
                    final int green = rgb >> 8 & 0xFF;
                    final int blue = rgb & 0xFF;
                    GL11.glColor4f(this.circleColor.getValue().getRed() / 255.0f, this.circleColor.getValue().getGreen() / 255.0f, this.circleColor.getValue().getBlue() / 255.0f, this.circleColor.getValue().getAlpha() / 255.0f);
                    GL11.glVertex3d(vecs.get(j).x, vecs.get(j).y, vecs.get(j).z);
                    GL11.glVertex3d(vecs.get(j + 1).x, vecs.get(j + 1).y, vecs.get(j + 1).z);
                    hue += 0.0027777778f;
                    rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
                }
                GL11.glEnd();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            BlockRenderUtil.releaseGL();
            GlStateManager.popMatrix();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0) {
            this.placeLocations.forEach((pos, time) -> {
                if (System.currentTimeMillis() - time > 1500L) {
                    this.placeLocations.remove(pos);
                }
                return;
            });
            --this.ticks;
            if (this.bilateralVec != null) {
                for (final Entity entity : NewAC.mc.world.loadedEntityList) {
                    if (entity instanceof EntityEnderCrystal && entity.getDistance(this.bilateralVec.x, this.bilateralVec.y, this.bilateralVec.z) <= 6.0) {
                        this.breakLocations.put(entity.getEntityId(), System.currentTimeMillis());
                    }
                }
                this.bilateralVec = null;
            }
            if (event.isCanceled()) {
                return;
            }
            this.postBreakPos = null;
            this.postPlacePos = null;
            this.postFacing = null;
            this.postResult = null;
            this.foundDoublePop = false;
            this.handleSequential();
            if (this.rotationMode.getValue() != RotationMode.OFF && !this.rotationTimer.passedMs(650L) && this.rotationVector != null) {
                if (this.rotationMode.getValue() == RotationMode.TRACK) {
                    this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                }
                if (this.yawAngle.getValue() < 1.0f && this.yawStep.getValue() != YawStepMode.OFF && (this.postBreakPos != null || this.yawStep.getValue() == YawStepMode.FULL)) {
                    if (this.ticks > 0) {
                        this.rotations[0] = ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw();
                        this.postBreakPos = null;
                        this.postPlacePos = null;
                    }
                    else {
                        final float yawDiff = MathHelper.wrapDegrees(this.rotations[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw());
                        if (Math.abs(yawDiff) > 180.0f * this.yawAngle.getValue()) {
                            this.rotations[0] = ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw() + yawDiff * (180.0f * this.yawAngle.getValue() / Math.abs(yawDiff));
                            this.postBreakPos = null;
                            this.postPlacePos = null;
                            this.ticks = this.yawTicks.getValue();
                        }
                    }
                }
                SilentRotaionUtil.lookAtAngles(this.rotations[0], this.rotations[1]);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final UpdateWalkingPlayerEvent event) {
        NewAC.aboba = this.mergeOffset.getValue() / 10.0f;
        if (event.getStage() == 1) {
            if (this.postBreakPos != null) {
                if (this.breakCrystal(this.postBreakPos)) {
                    this.breakTimer.reset();
                    this.breakLocations.put(this.postBreakPos.getEntityId(), System.currentTimeMillis());
                    for (final Entity entity : NewAC.mc.world.loadedEntityList) {
                        if (entity instanceof EntityEnderCrystal && entity.getDistance(this.postBreakPos.posX, this.postBreakPos.posY, this.postBreakPos.posZ) <= 6.0) {
                            this.breakLocations.put(entity.getEntityId(), System.currentTimeMillis());
                        }
                    }
                    this.postBreakPos = null;
                    if (this.syncMode.getValue() == SyncMode.MERGE) {
                        this.runInstantThread();
                    }
                }
            }
            else if (this.postPlacePos != null) {
                if (!this.placeCrystal(this.postPlacePos, this.postFacing)) {
                    this.shouldRunThread.set(false);
                    this.postPlacePos = null;
                    return;
                }
                this.placeTimer.reset();
                this.postPlacePos = null;
            }
        }
    }
    
    private void handleSequential() {
        if (NewAC.mc.player.getHealth() + NewAC.mc.player.getAbsorptionAmount() < this.disableUnderHealth.getValue() || (this.disableWhenKA.getValue() && Thunderhack.moduleManager.getModuleByClass(KillauraOld.class).isEnabled()) || (this.disableWhenPA.getValue() && Thunderhack.moduleManager.getModuleByClass(PistonAura.class).isEnabled()) || (this.noGapSwitch.getValue() && NewAC.mc.player.getActiveItemStack().getItem() instanceof ItemFood) || (this.noMineSwitch.getValue() && NewAC.mc.playerController.getIsHittingBlock() && NewAC.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool)) {
            this.rotationVector = null;
            return;
        }
        if (this.noGapSwitch.getValue() && this.rightClickGap.getValue() && NewAC.mc.gameSettings.keyBindUseItem.isKeyDown() && NewAC.mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal) {
            int gappleSlot = -1;
            for (int l = 0; l < 9; ++l) {
                if (NewAC.mc.player.inventory.getStackInSlot(l).getItem() == Items.GOLDEN_APPLE) {
                    gappleSlot = l;
                    break;
                }
            }
            if (gappleSlot != -1 && gappleSlot != NewAC.mc.player.inventory.currentItem && this.switchTimer.passedMs((long)(this.swapDelay.getValue() * 50.0f))) {
                NewAC.mc.player.inventory.currentItem = gappleSlot;
                NewAC.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(gappleSlot));
                this.switchTimer.reset();
                this.noGhostTimer.reset();
                return;
            }
        }
        if (!this.isOffhand() && !(NewAC.mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal) && !this.autoSwap.getValue()) {
            return;
        }
        final List<EntityPlayer> targetsInRange = this.getTargetsInRange();
        final EntityEnderCrystal crystal = this.findCrystalTarget(targetsInRange);
        final int adjustedResponseTime = (int)Math.max(100.0f, (CrystalUtils.ping() + 50) / 1.0f) + 150;
        if (!this.adaptivedel.getValue()) {
            if (crystal != null && this.breakTimer.passedMs((long)(1000.0f - this.breakSpeed.getValue() * 50.0f)) && (crystal.ticksExisted >= this.delay.getValue() || this.timingMode.getValue() == TimingMode.ADAPTIVE)) {
                this.postBreakPos = crystal;
                this.handleBreakRotation(this.postBreakPos.posX, this.postBreakPos.posY, this.postBreakPos.posZ);
            }
        }
        else if (crystal != null && this.breakTimer.passedMs((long)(1000.0f - this.breakSpeed.getValue() * 50.0f)) && (this.canibreakplz(false) || this.timingMode.getValue() == TimingMode.ADAPTIVE)) {
            this.postBreakPos = crystal;
            this.handleBreakRotation(this.postBreakPos.posX, this.postBreakPos.posY, this.postBreakPos.posZ);
        }
        if (!this.adaptivedel.getValue()) {
            if (crystal == null && (this.confirm.getValue() != ConfirmMode.FULL || this.inhibitEntity == null || this.inhibitEntity.ticksExisted >= Math.floor(this.delay.getValue())) && (this.syncMode.getValue() != SyncMode.STRICT || this.breakTimer.passedMs((long)(950.0f - this.breakSpeed.getValue() * 50.0f - CrystalUtils.ping()))) && this.placeTimer.passedMs((long)(1000.0f - this.placeSpeed.getValue() * 50.0f)) && (this.timingMode.getValue() == TimingMode.SEQUENTIAL || this.linearTimer.passedMs((long)(this.delay.getValue() * 5.0f)))) {
                if (this.confirm.getValue() != ConfirmMode.OFF && this.cachePos != null && !this.cacheTimer.passedMs(adjustedResponseTime + 100) && this.canPlaceCrystal(this.cachePos)) {
                    this.postPlacePos = this.cachePos;
                    this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                    this.lastBroken.set(false);
                    return;
                }
                final List<BlockPos> blocks = this.findCrystalBlocks();
                if (!blocks.isEmpty()) {
                    final BlockPos candidatePos = this.findPlacePosition(blocks, targetsInRange);
                    if (candidatePos != null) {
                        this.postPlacePos = candidatePos;
                        this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                    }
                }
            }
        }
        else if (crystal == null && (this.confirm.getValue() != ConfirmMode.FULL || this.inhibitEntity == null || this.canibreakplz(false)) && (this.syncMode.getValue() != SyncMode.STRICT || this.breakTimer.passedMs((long)(950.0f - this.breakSpeed.getValue() * 50.0f - CrystalUtils.ping()))) && this.placeTimer.passedMs((long)(1000.0f - this.placeSpeed.getValue() * 50.0f)) && (this.timingMode.getValue() == TimingMode.SEQUENTIAL || this.canibreakplz(false))) {
            if (this.confirm.getValue() != ConfirmMode.OFF && this.cachePos != null && !this.cacheTimer.passedMs(adjustedResponseTime + 100) && this.canPlaceCrystal(this.cachePos)) {
                this.postPlacePos = this.cachePos;
                this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                this.lastBroken.set(false);
                return;
            }
            final List<BlockPos> blocks = this.findCrystalBlocks();
            if (!blocks.isEmpty()) {
                final BlockPos candidatePos = this.findPlacePosition(blocks, targetsInRange);
                if (candidatePos != null) {
                    this.postPlacePos = candidatePos;
                    this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                }
            }
        }
        this.lastBroken.set(false);
    }
    
    private double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double d0 = x1 - x2;
        final double d2 = y1 - y2;
        final double d3 = z1 - z2;
        return Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
    }
    
    private void doInstant() {
        if (!this.adaptivedel.getValue()) {
            if (this.confirm.getValue() != ConfirmMode.OFF && (this.confirm.getValue() != ConfirmMode.FULL || this.inhibitEntity == null || this.inhibitEntity.ticksExisted >= Math.floor(this.delay.getValue()))) {
                final int adjustedResponseTime = (int)Math.max(100.0f, (CrystalUtils.ping() + 50) / 1.0f) + 150;
                if (this.cachePos != null && !this.cacheTimer.passedMs(adjustedResponseTime + 100) && this.canPlaceCrystal(this.cachePos)) {
                    this.postPlacePos = this.cachePos;
                    this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                    if (this.postPlacePos != null) {
                        if (!this.placeCrystal(this.postPlacePos, this.postFacing)) {
                            this.postPlacePos = null;
                            return;
                        }
                        this.placeTimer.reset();
                        this.postPlacePos = null;
                    }
                    return;
                }
            }
        }
        else if (this.confirm.getValue() != ConfirmMode.OFF && (this.confirm.getValue() != ConfirmMode.FULL || this.inhibitEntity == null || this.canibreakplz(false))) {
            final int adjustedResponseTime = (int)Math.max(100.0f, (CrystalUtils.ping() + 50) / 1.0f) + 150;
            if (this.cachePos != null && !this.cacheTimer.passedMs(adjustedResponseTime + 100) && this.canPlaceCrystal(this.cachePos)) {
                this.postPlacePos = this.cachePos;
                this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                if (this.postPlacePos != null) {
                    if (!this.placeCrystal(this.postPlacePos, this.postFacing)) {
                        this.postPlacePos = null;
                        return;
                    }
                    this.placeTimer.reset();
                    this.postPlacePos = null;
                }
                return;
            }
        }
        final List<BlockPos> blocks = this.findCrystalBlocks();
        if (!blocks.isEmpty()) {
            final BlockPos candidatePos = this.findPlacePosition(blocks, this.getTargetsInRange());
            if (candidatePos != null) {
                this.postPlacePos = candidatePos;
                this.postFacing = this.handlePlaceRotation(this.postPlacePos);
                if (this.postPlacePos != null) {
                    if (!this.placeCrystal(this.postPlacePos, this.postFacing)) {
                        this.postPlacePos = null;
                        return;
                    }
                    this.placeTimer.reset();
                    this.postPlacePos = null;
                }
            }
        }
    }
    
    private void runInstantThread() {
        if (this.mergeOffset.getValue() == 0.0f) {
            this.doInstant();
        }
        else {
            this.shouldRunThread.set(true);
            if (this.thread == null || this.thread.isInterrupted() || !this.thread.isAlive()) {
                if (this.thread == null) {
                    this.thread = new Thread(getInstance(this));
                }
                if (this.thread != null && (this.thread.isInterrupted() || !this.thread.isAlive())) {
                    this.thread = new Thread(getInstance(this));
                }
                if (this.thread != null && this.thread.getState() == Thread.State.NEW) {
                    try {
                        this.thread.start();
                    }
                    catch (Exception ex) {}
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSpawnObject) {
            final SPacketSpawnObject packetSpawnObject = (SPacketSpawnObject)event.getPacket();
            if (packetSpawnObject.getType() == 51) {
                final SPacketSpawnObject sPacketSpawnObject;
                CPacketUseEntity packetUseEntity;
                NetHandlerPlayClient connection;
                final CPacketAnimation cPacketAnimation;
                this.placeLocations.forEach((pos, time) -> {
                    if (this.getDistance(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, sPacketSpawnObject.getX(), sPacketSpawnObject.getY() - 1.0, sPacketSpawnObject.getZ()) < 1.0) {
                        try {
                            this.placeLocations.remove(pos);
                            this.cachePos = null;
                            if (!this.limit.getValue() && this.inhibit.getValue()) {
                                this.scatterTimer.reset();
                            }
                        }
                        catch (ConcurrentModificationException ex) {}
                        if (this.timingMode.getValue() == TimingMode.ADAPTIVE) {
                            if (!(!this.noGhostTimer.passedMs((long)(this.switchDelay.getValue() * 100.0f)))) {
                                if (!this.tickRunning.get()) {
                                    if (!NewAC.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                                        if (!this.breakLocations.containsKey(sPacketSpawnObject.getEntityID())) {
                                            if (NewAC.mc.player.getHealth() + NewAC.mc.player.getAbsorptionAmount() < this.disableUnderHealth.getValue() || (this.disableWhenKA.getValue() && Thunderhack.moduleManager.getModuleByClass(KillauraOld.class).isEnabled()) || (this.disableWhenPA.getValue() && Thunderhack.moduleManager.getModuleByClass(PistonAura.class).isEnabled()) || (this.noGapSwitch.getValue() && NewAC.mc.player.getActiveItemStack().getItem() instanceof ItemFood) || (this.noMineSwitch.getValue() && NewAC.mc.playerController.getIsHittingBlock() && NewAC.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool)) {
                                                this.rotationVector = null;
                                            }
                                            else if (NewAC.mc.player.getPositionEyes(1.0f).distanceTo(new Vec3d(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ())) <= this.breakRange.getValue()) {
                                                if (!(!this.breakTimer.passedMs((long)(1000.0f - this.breakSpeed.getValue() * 50.0f)))) {
                                                    if (CrystalUtils.calculateDamage(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ(), (Entity)NewAC.mc.player) + this.suicideHealth.getValue() < NewAC.mc.player.getHealth() + NewAC.mc.player.getAbsorptionAmount()) {
                                                        this.breakLocations.put(sPacketSpawnObject.getEntityID(), System.currentTimeMillis());
                                                        this.bilateralVec = new Vec3d(sPacketSpawnObject.getX(), sPacketSpawnObject.getY(), sPacketSpawnObject.getZ());
                                                        packetUseEntity = new CPacketUseEntity();
                                                        ((ICPacketUseEntity)packetUseEntity).setEntityId(sPacketSpawnObject.getEntityID());
                                                        ((ICPacketUseEntity)packetUseEntity).setAction(CPacketUseEntity.Action.ATTACK);
                                                        connection = NewAC.mc.player.connection;
                                                        new CPacketAnimation(this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                                                        connection.sendPacket((Packet)cPacketAnimation);
                                                        NewAC.mc.player.connection.sendPacket((Packet)packetUseEntity);
                                                        this.swingArmAfterBreaking(this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                                                        this.renderBreakingPos = new BlockPos(sPacketSpawnObject.getX(), sPacketSpawnObject.getY() - 1.0, sPacketSpawnObject.getZ());
                                                        this.renderBreakingTimer.reset();
                                                        this.breakTimer.reset();
                                                        this.linearTimer.reset();
                                                        if (this.syncMode.getValue() == SyncMode.MERGE) {
                                                            this.placeTimer.reset();
                                                        }
                                                        if (this.syncMode.getValue() == SyncMode.STRICT) {
                                                            this.lastBroken.set(true);
                                                        }
                                                        if (this.syncMode.getValue() == SyncMode.MERGE) {
                                                            this.runInstantThread();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        else if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                if (this.inhibitEntity != null && this.inhibitEntity.getDistance(packet.getX(), packet.getY(), packet.getZ()) < 6.0) {
                    this.inhibitEntity = null;
                }
                if (this.security.getValue() >= 0.5f) {
                    try {
                        this.selfPlacePositions.remove(new BlockPos(packet.getX(), packet.getY() - 1.0, packet.getZ()));
                    }
                    catch (ConcurrentModificationException ex2) {}
                }
            }
        }
        else if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet2 = (SPacketEntityStatus)event.getPacket();
            if (packet2.getOpCode() == 35 && packet2.getEntity((World)NewAC.mc.world) instanceof EntityPlayer) {
                this.totemPops.put((EntityPlayer)packet2.getEntity((World)NewAC.mc.world), new Timer());
            }
        }
        else if (event.getPacket() instanceof SPacketPlayerPosLook && this.disableOnTP.getValue() && !Thunderhack.moduleManager.getModuleByClass(PacketFly.class).isEnabled()) {
            this.toggle();
        }
    }
    
    public boolean placeCrystal(final BlockPos pos, final EnumFacing facing) {
        if (pos == null) {
            return false;
        }
        if (this.autoSwap.getValue()) {
            if (!this.switchTimer.passedMs((long)(this.swapDelay.getValue() * 50.0f))) {
                return false;
            }
            if (!this.setCrystalSlot()) {
                return false;
            }
        }
        if (!this.isOffhand() && NewAC.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
            return false;
        }
        if (NewAC.mc.world.getBlockState(pos.up()).getBlock() == Blocks.FIRE) {
            NewAC.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos.up(), EnumFacing.DOWN));
            NewAC.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos.up(), EnumFacing.DOWN));
            return true;
        }
        this.isPlacing = true;
        if (this.postResult == null) {
            BlockUtils.rightClickBlock(pos, NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0), this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, facing, true);
        }
        else {
            NewAC.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (float)this.postResult.hitVec.x, (float)this.postResult.hitVec.y, (float)this.postResult.hitVec.z));
            NewAC.mc.player.connection.sendPacket((Packet)new CPacketAnimation(this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
        }
        if (this.foundDoublePop && this.renderTarget != null) {
            this.totemPops.put(this.renderTarget, new Timer());
        }
        this.isPlacing = false;
        this.placeLocations.put(pos, System.currentTimeMillis());
        if (this.security.getValue() >= 0.5f) {
            this.selfPlacePositions.add(pos);
        }
        this.renderTimeoutTimer.reset();
        this.prevPlacePos = pos;
        return true;
    }
    
    private boolean breakCrystal(final EntityEnderCrystal targetCrystal) {
        if (!this.noGhostTimer.passedMs((long)(this.switchDelay.getValue() * 100.0f))) {
            return false;
        }
        if (targetCrystal == null) {
            return false;
        }
        if (this.antiWeakness.getValue() && NewAC.mc.player.isPotionActive(MobEffects.WEAKNESS) && !(NewAC.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            this.setSwordSlot();
            return false;
        }
        NewAC.mc.playerController.attackEntity((EntityPlayer)NewAC.mc.player, (Entity)targetCrystal);
        NewAC.mc.player.connection.sendPacket((Packet)new CPacketAnimation(this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
        this.swingArmAfterBreaking(this.isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        if (this.syncMode.getValue() == SyncMode.MERGE) {
            this.placeTimer.reset();
        }
        if (this.syncMode.getValue() == SyncMode.STRICT) {
            this.lastBroken.set(true);
        }
        this.inhibitTimer.reset();
        this.inhibitEntity = targetCrystal;
        this.renderBreakingPos = new BlockPos((Entity)targetCrystal).down();
        this.renderBreakingTimer.reset();
        return true;
    }
    
    private void swingArmAfterBreaking(final EnumHand hand) {
        if (!this.swing.getValue()) {
            return;
        }
        final ItemStack stack = NewAC.mc.player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem().onEntitySwing((EntityLivingBase)NewAC.mc.player, stack)) {
            return;
        }
        if (!NewAC.mc.player.isSwingInProgress || NewAC.mc.player.swingProgressInt >= this.getSwingAnimTime((EntityLivingBase)NewAC.mc.player) / 2 || NewAC.mc.player.swingProgressInt < 0) {
            NewAC.mc.player.swingProgressInt = -1;
            NewAC.mc.player.isSwingInProgress = true;
            NewAC.mc.player.swingingHand = hand;
        }
    }
    
    private int getSwingAnimTime(final EntityLivingBase entity) {
        if (entity.isPotionActive(MobEffects.HASTE)) {
            return 6 - (1 + entity.getActivePotionEffect(MobEffects.HASTE).getAmplifier());
        }
        return entity.isPotionActive(MobEffects.MINING_FATIGUE) ? (6 + (1 + entity.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) * 2) : 6;
    }
    
    public EntityEnderCrystal getPostBreakPos() {
        return this.postBreakPos;
    }
    
    public BlockPos getPostPlacePos() {
        return this.postPlacePos;
    }
    
    private List<Entity> getCrystalInRange() {
        return (List<Entity>)NewAC.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> this.isValidCrystalTarget(e)).collect(Collectors.toList());
    }
    
    private boolean isValidCrystalTarget(final EntityEnderCrystal crystal) {
        if (NewAC.mc.player.getPositionEyes(1.0f).distanceTo(crystal.getPositionVector()) > this.breakRange.getValue()) {
            return false;
        }
        if (this.breakLocations.containsKey(crystal.getEntityId()) && this.limit.getValue()) {
            return false;
        }
        if (!this.adaptivedel.getValue()) {
            if (this.breakLocations.containsKey(crystal.getEntityId()) && crystal.ticksExisted > this.delay.getValue() + this.attackFactor.getValue()) {
                return false;
            }
        }
        else if (this.breakLocations.containsKey(crystal.getEntityId()) && this.canibreakplz(true)) {
            return false;
        }
        return CrystalUtils.calculateDamage(crystal, (Entity)NewAC.mc.player) + this.suicideHealth.getValue() < NewAC.mc.player.getHealth() + NewAC.mc.player.getAbsorptionAmount();
    }
    
    private EntityEnderCrystal findCrystalTarget(final List<EntityPlayer> targetsInRange) {
        this.breakLocations.forEach((id, time) -> {
            if (System.currentTimeMillis() - time > 1000L) {
                this.breakLocations.remove(id);
            }
            return;
        });
        if (this.syncMode.getValue() == SyncMode.STRICT && !this.limit.getValue() && this.lastBroken.get()) {
            return null;
        }
        EntityEnderCrystal bestCrystal = null;
        final int adjustedResponseTime = (int)Math.max(100.0f, (CrystalUtils.ping() + 50) / 1.0f) + 150;
        if (this.inhibit.getValue() && !this.limit.getValue() && !this.inhibitTimer.passedMs(adjustedResponseTime) && this.inhibitEntity != null && NewAC.mc.world.getEntityByID(this.inhibitEntity.getEntityId()) != null && this.isValidCrystalTarget(this.inhibitEntity)) {
            bestCrystal = this.inhibitEntity;
            return bestCrystal;
        }
        final List<Entity> crystalsInRange = this.getCrystalInRange();
        if (crystalsInRange.isEmpty()) {
            return null;
        }
        if (this.security.getValue() >= 1.0f) {
            double bestDamage = 0.5;
            for (final Entity eCrystal : crystalsInRange) {
                if (eCrystal.getPositionVector().distanceTo(NewAC.mc.player.getPositionEyes(1.0f)) < this.breakWallsRange.getValue() || CrystalUtils.rayTraceBreak(eCrystal.posX, eCrystal.posY, eCrystal.posZ)) {
                    final EntityEnderCrystal crystal = (EntityEnderCrystal)eCrystal;
                    double damage = 0.0;
                    for (final EntityPlayer target : targetsInRange) {
                        final double targetDamage = CrystalUtils.calculateDamage(crystal, (Entity)target);
                        damage += targetDamage;
                    }
                    final double selfDamage = CrystalUtils.calculateDamage(crystal, (Entity)NewAC.mc.player);
                    if (selfDamage > damage * (this.security.getValue() - 0.8f) && !this.selfPlacePositions.contains(new BlockPos(eCrystal.posX, eCrystal.posY - 1.0, eCrystal.posZ))) {
                        continue;
                    }
                    if (damage <= bestDamage) {
                        continue;
                    }
                    bestDamage = damage;
                    bestCrystal = crystal;
                }
            }
        }
        else if (this.security.getValue() >= 0.5f) {
            bestCrystal = crystalsInRange.stream().filter(c -> this.selfPlacePositions.contains(new BlockPos(c.posX, c.posY - 1.0, c.posZ))).filter(c -> c.getPositionVector().distanceTo(NewAC.mc.player.getPositionEyes(1.0f)) < this.breakWallsRange.getValue() || CrystalUtils.rayTraceBreak(c.posX, c.posY, c.posZ)).min(Comparator.comparing(c -> NewAC.mc.player.getDistance(c))).orElse(null);
        }
        else {
            bestCrystal = crystalsInRange.stream().filter(c -> c.getPositionVector().distanceTo(NewAC.mc.player.getPositionEyes(1.0f)) < this.breakWallsRange.getValue() || CrystalUtils.rayTraceBreak(c.posX, c.posY, c.posZ)).min(Comparator.comparing(c -> NewAC.mc.player.getDistance(c))).orElse(null);
        }
        return bestCrystal;
    }
    
    private boolean shouldArmorBreak(final EntityPlayer target) {
        if (!this.armorBreaker.getValue()) {
            return false;
        }
        for (int index = 3; index >= 0; --index) {
            final ItemStack armourStack = (ItemStack)target.inventory.armorInventory.get(index);
            if (armourStack != null) {
                final double health = armourStack.getItem().getDurabilityForDisplay(armourStack);
                if (health > this.depletion.getValue()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private BlockPos findPlacePosition(final List<BlockPos> blocks, final List<EntityPlayer> targets) {
        if (targets.isEmpty()) {
            return null;
        }
        float maxDamage = 0.5f;
        EntityPlayer currentTarget = null;
        BlockPos currentPos = null;
        this.foundDoublePop = false;
        EntityPlayer targetedPlayer = null;
        for (final BlockPos pos : blocks) {
            final float selfDamage = CrystalUtils.calculateDamage(pos, (Entity)NewAC.mc.player);
            if (selfDamage + (double)this.suicideHealth.getValue() < NewAC.mc.player.getHealth() + NewAC.mc.player.getAbsorptionAmount()) {
                if (selfDamage > this.maxSelfPlace.getValue()) {
                    continue;
                }
                if (this.targetingMode.getValue() != TargetingMode.ALL) {
                    targetedPlayer = targets.get(0);
                    if (targetedPlayer.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > this.crystalRange.getValue()) {
                        continue;
                    }
                    final float playerDamage = CrystalUtils.calculateDamage(pos, (Entity)targetedPlayer);
                    if (this.isDoublePoppable(targetedPlayer, playerDamage) && (currentPos == null || targetedPlayer.getDistanceSq(pos) < targetedPlayer.getDistanceSq(currentPos))) {
                        currentTarget = targetedPlayer;
                        maxDamage = playerDamage;
                        currentPos = pos;
                        this.foundDoublePop = true;
                    }
                    else {
                        if (this.foundDoublePop || playerDamage <= maxDamage) {
                            continue;
                        }
                        if (playerDamage * this.compromise.getValue() <= selfDamage && playerDamage <= targetedPlayer.getHealth() + targetedPlayer.getAbsorptionAmount()) {
                            continue;
                        }
                        if (playerDamage < this.minPlaceDamage.getValue() && targetedPlayer.getHealth() + targetedPlayer.getAbsorptionAmount() > this.faceplaceHealth.getValue() && !PlayerUtils.isKeyDown(this.forceFaceplace.getValue().getKey()) && !this.shouldArmorBreak(targetedPlayer)) {
                            continue;
                        }
                        maxDamage = playerDamage;
                        currentTarget = targetedPlayer;
                        currentPos = pos;
                    }
                }
                else {
                    for (final EntityPlayer player : targets) {
                        if (player.equals((Object)targetedPlayer)) {
                            continue;
                        }
                        if (player.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > this.crystalRange.getValue()) {
                            continue;
                        }
                        final float playerDamage2 = CrystalUtils.calculateDamage(pos, (Entity)player);
                        if (this.isDoublePoppable(player, playerDamage2) && (currentPos == null || player.getDistanceSq(pos) < player.getDistanceSq(currentPos))) {
                            currentTarget = player;
                            maxDamage = playerDamage2;
                            currentPos = pos;
                            this.foundDoublePop = true;
                        }
                        else {
                            if (this.foundDoublePop || playerDamage2 <= maxDamage) {
                                continue;
                            }
                            if (playerDamage2 * this.compromise.getValue() <= selfDamage && playerDamage2 <= player.getHealth() + player.getAbsorptionAmount()) {
                                continue;
                            }
                            if (playerDamage2 < this.minPlaceDamage.getValue() && player.getHealth() + player.getAbsorptionAmount() > this.faceplaceHealth.getValue() && !PlayerUtils.isKeyDown(this.forceFaceplace.getValue().getKey()) && !this.shouldArmorBreak(player)) {
                                continue;
                            }
                            maxDamage = playerDamage2;
                            currentTarget = player;
                            currentPos = pos;
                        }
                    }
                }
            }
        }
        if (currentTarget != null && currentPos != null) {
            this.renderTarget = currentTarget;
            this.renderTargetTimer.reset();
        }
        if (currentPos != null) {
            this.renderBlock = currentPos;
            this.renderDamage = maxDamage;
        }
        this.cachePos = currentPos;
        this.cacheTimer.reset();
        return currentPos;
    }
    
    private boolean isDoublePoppable(final EntityPlayer player, final float damage) {
        if (this.predictPops.getValue() && player.getHealth() + player.getAbsorptionAmount() <= 2.0f && damage > player.getHealth() + (double)player.getAbsorptionAmount() + 0.5 && damage <= 4.0f) {
            final Timer timer = this.totemPops.get(player);
            return timer == null || timer.passedMs(500L);
        }
        return false;
    }
    
    public void handleBreakRotation(final double x, final double y, final double z) {
        if (this.rotationMode.getValue() != RotationMode.OFF) {
            if (this.rotationMode.getValue() == RotationMode.INTERACT && this.rotationVector != null && !this.rotationTimer.passedMs(650L)) {
                if (this.rotationVector.y < y - 0.1) {
                    this.rotationVector = new Vec3d(this.rotationVector.x, y, this.rotationVector.z);
                }
                this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                this.rotationTimer.reset();
                return;
            }
            final AxisAlignedBB bb = new AxisAlignedBB(x - 1.0, y, z - 1.0, x + 1.0, y + 2.0, z + 1.0);
            final Vec3d gEyesPos = new Vec3d(NewAC.mc.player.posX, NewAC.mc.player.getEntityBoundingBox().minY + NewAC.mc.player.getEyeHeight(), NewAC.mc.player.posZ);
            double increment = 0.1;
            double start = 0.15;
            double end = 0.85;
            if (bb.intersects(NewAC.mc.player.getEntityBoundingBox())) {
                start = 0.4;
                end = 0.6;
                increment = 0.05;
            }
            Vec3d finalVec = null;
            double[] finalRotation = null;
            boolean finalVisible = false;
            for (double xS = start; xS <= end; xS += increment) {
                for (double yS = start; yS <= end; yS += increment) {
                    for (double zS = start; zS <= end; zS += increment) {
                        final Vec3d tempVec = new Vec3d(bb.minX + (bb.maxX - bb.minX) * xS, bb.minY + (bb.maxY - bb.minY) * yS, bb.minZ + (bb.maxZ - bb.minZ) * zS);
                        final double diffX = tempVec.x - gEyesPos.x;
                        final double diffY = tempVec.y - gEyesPos.y;
                        final double diffZ = tempVec.z - gEyesPos.z;
                        final double[] tempRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))) };
                        boolean isVisible = true;
                        if (this.directionMode.getValue() != DirectionMode.VANILLA && !CrystalUtils.isVisible(tempVec)) {
                            isVisible = false;
                        }
                        if (this.strictDirection.getValue()) {
                            if (finalVec != null && finalRotation != null) {
                                if ((isVisible || !finalVisible) && NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(tempVec) < NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(finalVec)) {
                                    finalVec = tempVec;
                                    finalRotation = tempRotation;
                                }
                            }
                            else {
                                finalVec = tempVec;
                                finalRotation = tempRotation;
                                finalVisible = isVisible;
                            }
                        }
                        else if (finalVec != null && finalRotation != null) {
                            if ((isVisible || !finalVisible) && Math.hypot(((tempRotation[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, tempRotation[1] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedPitch()) < Math.hypot(((finalRotation[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, finalRotation[1] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedPitch())) {
                                finalVec = tempVec;
                                finalRotation = tempRotation;
                            }
                        }
                        else {
                            finalVec = tempVec;
                            finalRotation = tempRotation;
                            finalVisible = isVisible;
                        }
                    }
                }
            }
            if (finalVec != null && finalRotation != null) {
                this.rotationTimer.reset();
                this.rotationVector = finalVec;
                this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
            }
        }
    }
    
    public EnumFacing handlePlaceRotation(final BlockPos pos) {
        if (pos == null || NewAC.mc.player == null) {
            return null;
        }
        EnumFacing facing = null;
        if (this.directionMode.getValue() != DirectionMode.VANILLA) {
            Vec3d placeVec = null;
            double[] placeRotation = null;
            final double increment = 0.45;
            final double start = 0.05;
            final double end = 0.95;
            final Vec3d eyesPos = new Vec3d(NewAC.mc.player.posX, NewAC.mc.player.getEntityBoundingBox().minY + NewAC.mc.player.getEyeHeight(), NewAC.mc.player.posZ);
            for (double xS = start; xS <= end; xS += increment) {
                for (double yS = start; yS <= end; yS += increment) {
                    for (double zS = start; zS <= end; zS += increment) {
                        final Vec3d posVec = new Vec3d((Vec3i)pos).add(xS, yS, zS);
                        final double distToPosVec = eyesPos.distanceTo(posVec);
                        final double diffX = posVec.x - eyesPos.x;
                        final double diffY = posVec.y - eyesPos.y;
                        final double diffZ = posVec.z - eyesPos.z;
                        final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                        final double[] tempPlaceRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))) };
                        final float yawCos = MathHelper.cos((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                        final float yawSin = MathHelper.sin((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                        final float pitchCos = -MathHelper.cos((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                        final float pitchSin = MathHelper.sin((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                        final Vec3d rotationVec = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
                        final Vec3d eyesRotationVec = eyesPos.add(rotationVec.x * distToPosVec, rotationVec.y * distToPosVec, rotationVec.z * distToPosVec);
                        final RayTraceResult rayTraceResult = NewAC.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, true, false);
                        if (this.placeWallsRange.getValue() >= this.placeRange.getValue() || (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos))) {
                            final Vec3d currVec = posVec;
                            final double[] currRotation = tempPlaceRotation;
                            if (this.strictDirection.getValue()) {
                                if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                    if (NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(currVec) < NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(placeVec)) {
                                        placeVec = currVec;
                                        placeRotation = currRotation;
                                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                            facing = rayTraceResult.sideHit;
                                            this.postResult = rayTraceResult;
                                        }
                                    }
                                }
                                else {
                                    placeVec = currVec;
                                    placeRotation = currRotation;
                                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                        facing = rayTraceResult.sideHit;
                                        this.postResult = rayTraceResult;
                                    }
                                }
                            }
                            else if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                if (Math.hypot(((currRotation[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, currRotation[1] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedPitch()) < Math.hypot(((placeRotation[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, placeRotation[1] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedPitch())) {
                                    placeVec = currVec;
                                    placeRotation = currRotation;
                                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                        facing = rayTraceResult.sideHit;
                                        this.postResult = rayTraceResult;
                                    }
                                }
                            }
                            else {
                                placeVec = currVec;
                                placeRotation = currRotation;
                                if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                    facing = rayTraceResult.sideHit;
                                    this.postResult = rayTraceResult;
                                }
                            }
                        }
                    }
                }
            }
            if (this.placeWallsRange.getValue() < this.placeRange.getValue() && this.directionMode.getValue() == DirectionMode.STRICT) {
                if (placeRotation != null && facing != null) {
                    this.rotationTimer.reset();
                    this.rotationVector = placeVec;
                    this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                    return facing;
                }
                for (double xS = start; xS <= end; xS += increment) {
                    for (double yS = start; yS <= end; yS += increment) {
                        for (double zS = start; zS <= end; zS += increment) {
                            final Vec3d posVec = new Vec3d((Vec3i)pos).add(xS, yS, zS);
                            final double distToPosVec = eyesPos.distanceTo(posVec);
                            final double diffX = posVec.x - eyesPos.x;
                            final double diffY = posVec.y - eyesPos.y;
                            final double diffZ = posVec.z - eyesPos.z;
                            final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                            final double[] tempPlaceRotation = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))) };
                            final float yawCos = MathHelper.cos((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                            final float yawSin = MathHelper.sin((float)(-tempPlaceRotation[0] * 0.01745329238474369 - 3.1415927410125732));
                            final float pitchCos = -MathHelper.cos((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                            final float pitchSin = MathHelper.sin((float)(-tempPlaceRotation[1] * 0.01745329238474369));
                            final Vec3d rotationVec = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
                            final Vec3d eyesRotationVec = eyesPos.add(rotationVec.x * distToPosVec, rotationVec.y * distToPosVec, rotationVec.z * distToPosVec);
                            final RayTraceResult rayTraceResult = NewAC.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, true, true);
                            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                final Vec3d currVec = posVec;
                                final double[] currRotation = tempPlaceRotation;
                                if (this.strictDirection.getValue()) {
                                    if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                        if (NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(currVec) < NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(placeVec)) {
                                            placeVec = currVec;
                                            placeRotation = currRotation;
                                            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                                facing = rayTraceResult.sideHit;
                                                this.postResult = rayTraceResult;
                                            }
                                        }
                                    }
                                    else {
                                        placeVec = currVec;
                                        placeRotation = currRotation;
                                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                            facing = rayTraceResult.sideHit;
                                            this.postResult = rayTraceResult;
                                        }
                                    }
                                }
                                else if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) || facing == null)) {
                                    if (Math.hypot(((currRotation[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, currRotation[1] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedPitch()) < Math.hypot(((placeRotation[0] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, placeRotation[1] - ((IEntityPlayerSP)NewAC.mc.player).getLastReportedPitch())) {
                                        placeVec = currVec;
                                        placeRotation = currRotation;
                                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                            facing = rayTraceResult.sideHit;
                                            this.postResult = rayTraceResult;
                                        }
                                    }
                                }
                                else {
                                    placeVec = currVec;
                                    placeRotation = currRotation;
                                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                                        facing = rayTraceResult.sideHit;
                                        this.postResult = rayTraceResult;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (placeRotation != null) {
                    this.rotationTimer.reset();
                    this.rotationVector = placeVec;
                    this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                }
                if (facing != null) {
                    return facing;
                }
            }
        }
        else {
            EnumFacing bestFacing = null;
            Vec3d bestVector = null;
            for (final EnumFacing enumFacing : EnumFacing.values()) {
                final Vec3d cVector = new Vec3d(pos.getX() + 0.5 + enumFacing.getDirectionVec().getX() * 0.5, pos.getY() + 0.5 + enumFacing.getDirectionVec().getY() * 0.5, pos.getZ() + 0.5 + enumFacing.getDirectionVec().getZ() * 0.5);
                final RayTraceResult rayTraceResult2 = NewAC.mc.world.rayTraceBlocks(new Vec3d(NewAC.mc.player.posX, NewAC.mc.player.posY + NewAC.mc.player.getEyeHeight(), NewAC.mc.player.posZ), cVector, false, true, false);
                if (rayTraceResult2 != null && rayTraceResult2.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK) && rayTraceResult2.getBlockPos().equals((Object)pos)) {
                    if (!this.strictDirection.getValue()) {
                        this.rotationTimer.reset();
                        this.rotationVector = cVector;
                        this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                        return enumFacing;
                    }
                    if (bestVector == null || NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(cVector) < NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(bestVector)) {
                        bestVector = cVector;
                        bestFacing = enumFacing;
                        this.postResult = rayTraceResult2;
                    }
                }
            }
            if (bestFacing != null) {
                this.rotationTimer.reset();
                this.rotationVector = bestVector;
                this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                return bestFacing;
            }
            if (this.strictDirection.getValue()) {
                for (final EnumFacing enumFacing : EnumFacing.values()) {
                    final Vec3d cVector = new Vec3d(pos.getX() + 0.5 + enumFacing.getDirectionVec().getX() * 0.5, pos.getY() + 0.5 + enumFacing.getDirectionVec().getY() * 0.5, pos.getZ() + 0.5 + enumFacing.getDirectionVec().getZ() * 0.5);
                    if (bestVector == null || NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(cVector) < NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0).distanceTo(bestVector)) {
                        bestVector = cVector;
                        bestFacing = enumFacing;
                    }
                }
                if (bestFacing != null) {
                    this.rotationTimer.reset();
                    this.rotationVector = bestVector;
                    this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
                    return bestFacing;
                }
            }
        }
        if (pos.getY() > NewAC.mc.player.posY + NewAC.mc.player.getEyeHeight()) {
            this.rotationTimer.reset();
            this.rotationVector = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
            this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
            return EnumFacing.DOWN;
        }
        this.rotationTimer.reset();
        this.rotationVector = new Vec3d(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
        this.rotations = RotationManager.calculateAngle(NewAC.mc.player.getPositionEyes(1.0f), this.rotationVector);
        return EnumFacing.UP;
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)getSphere(new BlockPos((Entity)NewAC.mc.player), ((boolean)this.strictDirection.getValue()) ? (this.placeRange.getValue() + 2.0f) : ((float)this.placeRange.getValue()), this.placeRange.getValue().intValue(), false, true, 0).stream().filter((Predicate<? super Object>)this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    public boolean canPlaceCrystal(final BlockPos blockPos) {
        if (NewAC.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && NewAC.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        final BlockPos boost = blockPos.add(0, 1, 0);
        if (NewAC.mc.world.getBlockState(boost).getBlock() != Blocks.AIR && (NewAC.mc.world.getBlockState(boost).getBlock() != Blocks.FIRE || !this.fire.getValue()) && (!(NewAC.mc.world.getBlockState(boost).getBlock() instanceof BlockLiquid) || !this.liquids.getValue())) {
            return false;
        }
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        if (!this.protocol.getValue() && NewAC.mc.world.getBlockState(boost2).getBlock() != Blocks.AIR && (!(NewAC.mc.world.getBlockState(boost).getBlock() instanceof BlockLiquid) || !this.liquids.getValue())) {
            return false;
        }
        if (this.check.getValue() && !CrystalUtils.rayTraceBreak(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5) && NewAC.mc.player.getPositionEyes(1.0f).distanceTo(new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5)) > this.breakWallsRange.getValue()) {
            return false;
        }
        if (this.placeWallsRange.getValue() < this.placeRange.getValue()) {
            if (!CrystalUtils.rayTracePlace(blockPos)) {
                if (this.strictDirection.getValue()) {
                    final Vec3d eyesPos = NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0);
                    boolean inRange = false;
                    Label_0720: {
                        if (this.directionMode.getValue() == DirectionMode.VANILLA) {
                            for (final EnumFacing facing : EnumFacing.values()) {
                                final Vec3d cVector = new Vec3d(blockPos.getX() + 0.5 + facing.getDirectionVec().getX() * 0.5, blockPos.getY() + 0.5 + facing.getDirectionVec().getY() * 0.5, blockPos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 0.5);
                                if (eyesPos.distanceTo(cVector) <= this.placeWallsRange.getValue()) {
                                    inRange = true;
                                    break;
                                }
                            }
                        }
                        else {
                            final double increment = 0.45;
                            final double start = 0.05;
                            for (double end = 0.95, xS = start; xS <= end; xS += increment) {
                                for (double yS = start; yS <= end; yS += increment) {
                                    for (double zS = start; zS <= end; zS += increment) {
                                        final Vec3d posVec = new Vec3d((Vec3i)blockPos).add(xS, yS, zS);
                                        final double distToPosVec = eyesPos.distanceTo(posVec);
                                        if (distToPosVec <= this.placeWallsRange.getValue()) {
                                            inRange = true;
                                            break Label_0720;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!inRange) {
                        return false;
                    }
                }
                else if (blockPos.getY() > NewAC.mc.player.posY + NewAC.mc.player.getEyeHeight()) {
                    if (NewAC.mc.player.getDistance(blockPos.getX() + 0.5, (double)blockPos.getY(), blockPos.getZ() + 0.5) > this.placeWallsRange.getValue()) {
                        return false;
                    }
                }
                else if (NewAC.mc.player.getDistance(blockPos.getX() + 0.5, (double)(blockPos.getY() + 1), blockPos.getZ() + 0.5) > this.placeWallsRange.getValue()) {
                    return false;
                }
            }
        }
        else if (this.strictDirection.getValue()) {
            final Vec3d eyesPos = NewAC.mc.player.getPositionVector().add(0.0, (double)NewAC.mc.player.getEyeHeight(), 0.0);
            boolean inRange = false;
            Label_1203: {
                if (this.directionMode.getValue() == DirectionMode.VANILLA) {
                    for (final EnumFacing facing : EnumFacing.values()) {
                        final Vec3d cVector = new Vec3d(blockPos.getX() + 0.5 + facing.getDirectionVec().getX() * 0.5, blockPos.getY() + 0.5 + facing.getDirectionVec().getY() * 0.5, blockPos.getZ() + 0.5 + facing.getDirectionVec().getZ() * 0.5);
                        if (eyesPos.distanceTo(cVector) <= this.placeRange.getValue()) {
                            inRange = true;
                            break;
                        }
                    }
                }
                else {
                    final double increment = 0.45;
                    final double start = 0.05;
                    for (double end = 0.95, xS = start; xS <= end; xS += increment) {
                        for (double yS = start; yS <= end; yS += increment) {
                            for (double zS = start; zS <= end; zS += increment) {
                                final Vec3d posVec = new Vec3d((Vec3i)blockPos).add(xS, yS, zS);
                                final double distToPosVec = eyesPos.distanceTo(posVec);
                                if (distToPosVec <= this.placeRange.getValue()) {
                                    inRange = true;
                                    break Label_1203;
                                }
                            }
                        }
                    }
                }
            }
            if (!inRange) {
                return false;
            }
        }
        return NewAC.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost, boost2.add(1, 1, 1))).stream().filter(entity -> !this.breakLocations.containsKey(entity.getEntityId()) && (!(entity instanceof EntityEnderCrystal) || entity.ticksExisted > 20)).count() == 0L;
    }
    
    private List<EntityPlayer> getTargetsInRange() {
        List<EntityPlayer> stream = (List<EntityPlayer>)NewAC.mc.world.playerEntities.stream().filter(e -> e != NewAC.mc.player && e != NewAC.mc.getRenderViewEntity()).filter(e -> !e.isDead).filter(e -> !Thunderhack.friendManager.isFriend(e.getName())).filter(e -> e.getHealth() > 0.0f).filter(e -> NewAC.mc.player.getDistance(e) < this.enemyRange.getValue()).sorted(Comparator.comparing(e -> NewAC.mc.player.getDistance(e))).collect(Collectors.toList());
        if (this.targetingMode.getValue() == TargetingMode.SMART) {
            final boolean b;
            List<EntityPlayer> safeStream = stream.stream().filter(e -> {
                if (!BlockUtils.isHole(new BlockPos(e))) {
                    if (NewAC.mc.world.getBlockState(new BlockPos(e)).getBlock() != Blocks.AIR) {
                        if (NewAC.mc.world.getBlockState(new BlockPos(e)).getBlock() != Blocks.WEB) {
                            if (!(NewAC.mc.world.getBlockState(new BlockPos(e)).getBlock() instanceof BlockLiquid)) {
                                return 0 != 0;
                            }
                        }
                    }
                    return b;
                }
                return b;
            }).sorted(Comparator.comparing(e -> NewAC.mc.player.getDistance(e))).collect((Collector<? super Object, ?, List<EntityPlayer>>)Collectors.toList());
            if (safeStream.size() > 0) {
                stream = safeStream;
            }
            safeStream = stream.stream().filter(e -> e.getHealth() + e.getAbsorptionAmount() < 10.0f).sorted(Comparator.comparing(e -> NewAC.mc.player.getDistance(e))).collect((Collector<? super Object, ?, List<EntityPlayer>>)Collectors.toList());
            if (safeStream.size() > 0) {
                stream = safeStream;
            }
        }
        return stream;
    }
    
    public void setSwordSlot() {
        final int swordSlot = CrystalUtils.getSwordSlot();
        if (NewAC.mc.player.inventory.currentItem != swordSlot && swordSlot != -1) {
            NewAC.mc.player.inventory.currentItem = swordSlot;
            NewAC.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(swordSlot));
            this.switchTimer.reset();
            this.noGhostTimer.reset();
        }
    }
    
    public boolean isOffhand() {
        return NewAC.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }
    
    public boolean setCrystalSlot() {
        if (this.isOffhand()) {
            return true;
        }
        final int crystalSlot = CrystalUtils.getCrystalSlot();
        if (crystalSlot == -1) {
            return false;
        }
        if (NewAC.mc.player.inventory.currentItem != crystalSlot) {
            NewAC.mc.player.inventory.currentItem = crystalSlot;
            NewAC.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(crystalSlot));
            this.switchTimer.reset();
            this.noGhostTimer.reset();
        }
        return true;
    }
    
    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - ((IRenderManager)NewAC.mc.getRenderManager()).getRenderPosX(), y - ((IRenderManager)NewAC.mc.getRenderManager()).getRenderPosY(), z - ((IRenderManager)NewAC.mc.getRenderManager()).getRenderPosZ());
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Minecraft.getMinecraft().player.rotationPitch, (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public boolean canibreakplz(final boolean af) {
        if (this.targetRender == null) {
            return true;
        }
        final int targethurtrestime = this.renderTarget.hurtResistantTime;
        final int maxtargethurtrestime = this.renderTarget.maxHurtResistantTime;
        if (!af) {
            if (this.hurttimesync.getValue()) {
                return targethurtrestime < maxtargethurtrestime / 2.0f;
            }
            if (this.isMoving(this.renderTarget)) {
                Command.sendMessage("move detected");
                return 20 - targethurtrestime >= this.adactive.getValue();
            }
            return 20 - targethurtrestime >= this.adpassive.getValue();
        }
        else {
            if (this.hurttimesync.getValue()) {
                return targethurtrestime < maxtargethurtrestime / 2.0f + this.attackFactor.getValue();
            }
            if (EntityUtil.isMoving(this.renderTarget)) {
                Command.sendMessage("move detected af");
                return 20 - targethurtrestime >= this.adactive.getValue() + this.attackFactor.getValue();
            }
            return 20 - targethurtrestime >= this.adpassive.getValue() + this.attackFactor.getValue();
        }
    }
    
    public boolean isMoving(final EntityPlayer ent) {
        return ent.moveForward != 0.0 || ent.moveStrafing != 0.0;
    }
    
    static {
        NewAC.INSTANCE = new NewAC();
    }
    
    private enum ConfirmMode
    {
        OFF, 
        SEMI, 
        FULL;
    }
    
    public enum RenderTextMode
    {
        NONE, 
        FLAT, 
        SHADED;
    }
    
    private enum RotationMode
    {
        OFF, 
        TRACK, 
        INTERACT;
    }
    
    private enum TimingMode
    {
        SEQUENTIAL, 
        ADAPTIVE;
    }
    
    private enum YawStepMode
    {
        OFF, 
        BREAK, 
        FULL;
    }
    
    private enum TargetingMode
    {
        ALL, 
        SMART, 
        NEAREST;
    }
    
    public enum DirectionMode
    {
        VANILLA, 
        NORMAL, 
        STRICT;
    }
    
    public enum SyncMode
    {
        STRICT, 
        MERGE;
    }
    
    private static class InstantThread implements Runnable
    {
        private static InstantThread INSTANCE;
        private NewAC autoCrystal;
        
        private static InstantThread getInstance(final NewAC crystalAura) {
            if (InstantThread.INSTANCE == null) {
                InstantThread.INSTANCE = new InstantThread();
                InstantThread.INSTANCE.autoCrystal = crystalAura;
            }
            return InstantThread.INSTANCE;
        }
        
        @Override
        public void run() {
            if (this.autoCrystal.shouldRunThread.get()) {
                try {
                    Thread.sleep((long)(NewAC.aboba * 40.0f));
                }
                catch (InterruptedException e) {
                    this.autoCrystal.thread.interrupt();
                }
                if (!this.autoCrystal.shouldRunThread.get()) {
                    return;
                }
                this.autoCrystal.shouldRunThread.set(false);
                if (this.autoCrystal.tickRunning.get()) {
                    return;
                }
                this.autoCrystal.doInstant();
            }
        }
    }
}
