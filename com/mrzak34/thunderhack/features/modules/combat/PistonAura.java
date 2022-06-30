//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.manager.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;

public class PistonAura extends Module
{
    private Setting<Mode> mode;
    private Setting<Boolean> smart;
    private Setting<Boolean> triggerable;
    private Setting<Boolean> disableWhenNone;
    private static PistonAura INSTANCE;
    private Setting<Integer> targetRange;
    private Setting<Integer> breakDelay;
    private Setting<Integer> actionShift;
    private Setting<Integer> actionInterval;
    private Setting<Boolean> strict;
    private Setting<Boolean> bbreakery;
    private Setting<Boolean> raytrace;
    private Setting<Boolean> antiSuicide;
    private Setting<Boolean> mine;
    private Setting<Boolean> autotrap;
    private Setting<rMode> rmode;
    public Setting<Boolean> render;
    public final Setting<ColorSetting> arrowColor;
    public final Setting<ColorSetting> outlineColorCurrent;
    public final Setting<ColorSetting> colorFull;
    public final Setting<ColorSetting> outlineColorFull;
    public final Setting<ColorSetting> colorCurrent;
    private Setting<bMode> bmode;
    private Setting<Boolean> renderCurrent;
    private Setting<Boolean> renderFull;
    private Setting<Boolean> arrow;
    private Setting<Boolean> bottomArrow;
    private Setting<Boolean> topArrow;
    private Stage stage;
    public BlockPos facePos;
    public EnumFacing faceOffset;
    public BlockPos crystalPos;
    public BlockPos pistonNeighbour;
    public EnumFacing pistonOffset;
    private BlockPos torchPos;
    private Timer torchTimer;
    private boolean skipPiston;
    private boolean playertrapped;
    private Timer delayTimer;
    private Timer brdel;
    private int delayTime;
    private Timer renderTimer;
    private Runnable postAction;
    private int tickCounter;
    private BlockPos placedPiston;
    private Timer placedPistonTimer;
    private Timer actionTimer;
    boolean sendPacket;
    boolean firstPacket;
    
    public PistonAura() {
        super("PistonAura", "\u041f\u043e\u0440\u0448\u043d\u0438 \u0432\u0442\u0430\u043b\u043a\u0438\u0432\u0430\u044e\u0442 \u043a\u0440\u0438\u0441\u0442\u0430\u043b-\u0432 \u0447\u0435\u043b\u0430 (\u041e\u0445\u0443\u0435\u043d\u043d\u0430\u044f \u0445\u0443\u0439\u043d\u044f)", Category.COMBAT, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.DAMAGE));
        this.smart = (Setting<Boolean>)this.register(new Setting("Smart", (T)true, v -> this.mode.getValue() == Mode.PUSH));
        this.triggerable = (Setting<Boolean>)this.register(new Setting("DisablePush", (T)true, v -> this.mode.getValue() == Mode.PUSH));
        this.disableWhenNone = (Setting<Boolean>)this.register(new Setting("DisableNone", (T)false, v -> this.mode.getValue() == Mode.DAMAGE));
        this.targetRange = (Setting<Integer>)this.register(new Setting("TargetRange", (T)3, (T)1, (T)6));
        this.breakDelay = (Setting<Integer>)this.register(new Setting("Delay", (T)25, (T)0, (T)100));
        this.actionShift = (Setting<Integer>)this.register(new Setting("ActionShift", (T)3, (T)1, (T)8));
        this.actionInterval = (Setting<Integer>)this.register(new Setting("ActionInterval", (T)0, (T)0, (T)10));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.bbreakery = (Setting<Boolean>)this.register(new Setting("BurrowBreak", (T)false));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("RayTrace", (T)false));
        this.antiSuicide = (Setting<Boolean>)this.register(new Setting("AntiSuicide", (T)false));
        this.mine = (Setting<Boolean>)this.register(new Setting("Mine", (T)false));
        this.autotrap = (Setting<Boolean>)this.register(new Setting("AutoTrap", (T)false));
        this.rmode = (Setting<rMode>)this.register(new Setting("RotateMode", (T)rMode.Konas));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.arrowColor = (Setting<ColorSetting>)this.register(new Setting("Arrow Color", (T)new ColorSetting(-2009289807)));
        this.outlineColorCurrent = (Setting<ColorSetting>)this.register(new Setting("Outline Color", (T)new ColorSetting(-1323314462)));
        this.colorFull = (Setting<ColorSetting>)this.register(new Setting("Full Color", (T)new ColorSetting(-2011093215)));
        this.outlineColorFull = (Setting<ColorSetting>)this.register(new Setting("OutlineColorF", (T)new ColorSetting(-2009289807)));
        this.colorCurrent = (Setting<ColorSetting>)this.register(new Setting("ColorCurrent", (T)new ColorSetting(-1323314462)));
        this.bmode = (Setting<bMode>)this.register(new Setting("BurBreakMode", (T)bMode.Packet));
        this.renderCurrent = (Setting<Boolean>)this.register(new Setting("Current", (T)true, v -> this.render.getValue()));
        this.renderFull = (Setting<Boolean>)this.register(new Setting("Full", (T)true, v -> this.render.getValue()));
        this.arrow = (Setting<Boolean>)this.register(new Setting("Arrow", (T)true, v -> this.render.getValue()));
        this.bottomArrow = (Setting<Boolean>)this.register(new Setting("Bottom", (T)true, v -> this.render.getValue()));
        this.topArrow = (Setting<Boolean>)this.register(new Setting("Top", (T)true, v -> this.render.getValue()));
        this.stage = Stage.SEARCHING;
        this.torchTimer = new Timer();
        this.playertrapped = false;
        this.delayTimer = new Timer();
        this.brdel = new Timer();
        this.renderTimer = new Timer();
        this.postAction = null;
        this.tickCounter = 0;
        this.placedPiston = null;
        this.placedPistonTimer = new Timer();
        this.actionTimer = new Timer();
        this.sendPacket = false;
        this.firstPacket = false;
        this.setInstance();
    }
    
    public static PistonAura getInstance() {
        if (PistonAura.INSTANCE == null) {
            PistonAura.INSTANCE = new PistonAura();
        }
        return PistonAura.INSTANCE;
    }
    
    private void setInstance() {
        PistonAura.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (PistonAura.mc.player == null || PistonAura.mc.world == null) {
            return;
        }
        this.stage = Stage.SEARCHING;
        this.facePos = null;
        this.faceOffset = null;
        this.crystalPos = null;
        this.pistonNeighbour = null;
        this.pistonOffset = null;
        this.delayTime = 0;
        this.tickCounter = 0;
        this.postAction = null;
        this.torchPos = null;
        this.skipPiston = false;
        this.placedPiston = null;
    }
    
    private void handleAction(final boolean extra) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.actionTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //     4: ldc2_w          1000
        //     7: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //    10: ifeq            33
        //    13: aload_0         /* this */
        //    14: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.disableWhenNone:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //    17: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //    20: checkcast       Ljava/lang/Boolean;
        //    23: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    26: ifeq            33
        //    29: aload_0         /* this */
        //    30: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.toggle:()V
        //    33: aload_0         /* this */
        //    34: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.delayTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //    37: aload_0         /* this */
        //    38: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.delayTime:I
        //    41: i2l            
        //    42: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //    45: ifne            49
        //    48: return         
        //    49: aload_0         /* this */
        //    50: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.strict:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //    53: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //    56: checkcast       Ljava/lang/Boolean;
        //    59: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    62: ifeq            115
        //    65: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    68: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    71: getfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //    74: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    77: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    80: getfield        net/minecraft/client/entity/EntityPlayerSP.motionX:D
        //    83: dmul           
        //    84: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    87: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    90: getfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //    93: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //    96: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    99: getfield        net/minecraft/client/entity/EntityPlayerSP.motionZ:D
        //   102: dmul           
        //   103: dadd           
        //   104: invokestatic    java/lang/Math.sqrt:(D)D
        //   107: ldc2_w          9.0E-4
        //   110: dcmpl          
        //   111: ifle            115
        //   114: return         
        //   115: aload_0         /* this */
        //   116: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.mode:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   119: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   122: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$Mode.DAMAGE:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Mode;
        //   125: if_acmpne       2050
        //   128: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$1.$SwitchMap$com$mrzak34$thunderhack$features$modules$combat$PistonAura$Stage:[I
        //   131: aload_0         /* this */
        //   132: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //   135: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage.ordinal:()I
        //   138: iaload         
        //   139: tableswitch {
        //                2: 168
        //                3: 794
        //                4: 1290
        //                5: 1745
        //          default: 2047
        //        }
        //   168: aload_0         /* this */
        //   169: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getTargets:()Ljava/util/List;
        //   172: astore_2        /* candidates */
        //   173: aload_2         /* candidates */
        //   174: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   179: astore_3       
        //   180: aload_3        
        //   181: invokeinterface java/util/Iterator.hasNext:()Z
        //   186: ifeq            791
        //   189: aload_3        
        //   190: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   195: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //   198: astore          candidate
        //   200: aload_0         /* this */
        //   201: aload           candidate
        //   203: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.evaluateTarget:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   206: ifeq            788
        //   209: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   212: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   215: new             Lnet/minecraft/util/math/BlockPos;
        //   218: dup            
        //   219: aload           candidate
        //   221: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   224: aload           candidate
        //   226: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   229: ldc2_w          2.0
        //   232: dadd           
        //   233: aload           candidate
        //   235: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   238: invokespecial   net/minecraft/util/math/BlockPos.<init>:(DDD)V
        //   241: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   244: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   249: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //   252: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   255: ifeq            281
        //   258: aload_0         /* this */
        //   259: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.autotrap:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   262: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   265: checkcast       Ljava/lang/Boolean;
        //   268: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   271: ifeq            281
        //   274: aload_0         /* this */
        //   275: aload           candidate
        //   277: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.traptarget:(Lnet/minecraft/entity/Entity;)V
        //   280: return         
        //   281: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   284: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   287: new             Lnet/minecraft/util/math/BlockPos;
        //   290: dup            
        //   291: aload           candidate
        //   293: getfield        net/minecraft/entity/player/EntityPlayer.posX:D
        //   296: aload           candidate
        //   298: getfield        net/minecraft/entity/player/EntityPlayer.posY:D
        //   301: aload           candidate
        //   303: getfield        net/minecraft/entity/player/EntityPlayer.posZ:D
        //   306: invokespecial   net/minecraft/util/math/BlockPos.<init>:(DDD)V
        //   309: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   312: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   317: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //   320: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   323: ifne            362
        //   326: aload_0         /* this */
        //   327: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.bbreakery:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   330: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   333: checkcast       Ljava/lang/Boolean;
        //   336: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   339: ifeq            362
        //   342: aload_0         /* this */
        //   343: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.brdel:Lcom/mrzak34/thunderhack/util/Timer;
        //   346: ldc2_w          100
        //   349: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //   352: ifeq            362
        //   355: aload_0         /* this */
        //   356: aload           candidate
        //   358: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.removeburrow:(Lnet/minecraft/entity/player/EntityPlayer;)V
        //   361: return         
        //   362: invokestatic    com/mrzak34/thunderhack/features/modules/combat/PistonAura.getPistonSlot:()I
        //   365: istore          itemSlot
        //   367: iload           itemSlot
        //   369: iconst_m1      
        //   370: if_icmpne       384
        //   373: ldc_w           "No pistons found!"
        //   376: invokestatic    com/mrzak34/thunderhack/features/command/Command.sendMessage:(Ljava/lang/String;)V
        //   379: aload_0         /* this */
        //   380: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.toggle:()V
        //   383: return         
        //   384: aload_0         /* this */
        //   385: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.skipPiston:Z
        //   388: ifeq            404
        //   391: aload_0         /* this */
        //   392: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage.CRYSTAL:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //   395: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //   398: aload_0         /* this */
        //   399: iconst_0       
        //   400: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.skipPiston:Z
        //   403: return         
        //   404: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   407: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   410: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //   413: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //   416: iload           itemSlot
        //   418: if_icmpeq       425
        //   421: iconst_1       
        //   422: goto            426
        //   425: iconst_0       
        //   426: istore          changeItem
        //   428: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   431: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   434: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //   437: istore          isSprinting
        //   439: aload_0         /* this */
        //   440: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.pistonNeighbour:Lnet/minecraft/util/math/BlockPos;
        //   443: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.shouldSneakWhileRightClicking:(Lnet/minecraft/util/math/BlockPos;)Z
        //   446: istore          shouldSneak
        //   448: new             Lnet/minecraft/util/math/Vec3d;
        //   451: dup            
        //   452: aload_0         /* this */
        //   453: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.pistonNeighbour:Lnet/minecraft/util/math/BlockPos;
        //   456: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   459: ldc2_w          0.5
        //   462: ldc2_w          0.5
        //   465: ldc2_w          0.5
        //   468: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //   471: new             Lnet/minecraft/util/math/Vec3d;
        //   474: dup            
        //   475: aload_0         /* this */
        //   476: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.pistonOffset:Lnet/minecraft/util/EnumFacing;
        //   479: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //   482: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   485: ldc2_w          0.5
        //   488: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //   491: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //   494: astore          vec
        //   496: iload_1         /* extra */
        //   497: ifeq            536
        //   500: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   503: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   506: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   509: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   512: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   515: aload           vec
        //   517: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   520: astore          angle
        //   522: aload           angle
        //   524: iconst_0       
        //   525: faload         
        //   526: aload           angle
        //   528: iconst_1       
        //   529: faload         
        //   530: invokestatic    com/mrzak34/thunderhack/util/KRotationUtil.update:(FF)V
        //   533: goto            767
        //   536: aload_0         /* this */
        //   537: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.rmode:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   540: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   543: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode.Oyvey:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode;
        //   546: if_acmpne       557
        //   549: getstatic       com/mrzak34/thunderhack/Thunderhack.rotationManager:Lcom/mrzak34/thunderhack/manager/RotationManager;
        //   552: aload           vec
        //   554: invokevirtual   com/mrzak34/thunderhack/manager/RotationManager.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //   557: aload_0         /* this */
        //   558: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.rmode:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   561: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   564: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode.Konas:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode;
        //   567: if_acmpne       603
        //   570: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   573: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   576: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   579: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   582: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   585: aload           vec
        //   587: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   590: astore          angle
        //   592: aload           angle
        //   594: iconst_0       
        //   595: faload         
        //   596: aload           angle
        //   598: iconst_1       
        //   599: faload         
        //   600: invokestatic    com/mrzak34/thunderhack/util/KRotationUtil.update:(FF)V
        //   603: aload_0         /* this */
        //   604: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.rmode:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   607: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   610: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode.Silent:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode;
        //   613: if_acmpne       649
        //   616: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   619: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   622: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   625: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   628: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   631: aload           vec
        //   633: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   636: astore          angle
        //   638: aload           angle
        //   640: iconst_0       
        //   641: faload         
        //   642: aload           angle
        //   644: iconst_1       
        //   645: faload         
        //   646: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.lookAtAngles:(FF)V
        //   649: aload_0         /* this */
        //   650: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.rmode:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   653: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   656: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode.Silent2:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode;
        //   659: if_acmpne       695
        //   662: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   665: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   668: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   671: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   674: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   677: aload           vec
        //   679: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   682: astore          angle
        //   684: aload           angle
        //   686: iconst_0       
        //   687: faload         
        //   688: aload           angle
        //   690: iconst_1       
        //   691: faload         
        //   692: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.update:(FF)V
        //   695: aload_0         /* this */
        //   696: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.rmode:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   699: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   702: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode.Client:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$rMode;
        //   705: if_acmpne       767
        //   708: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   711: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   714: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   717: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //   720: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //   723: aload           vec
        //   725: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //   728: astore          angle
        //   730: aload           angle
        //   732: iconst_0       
        //   733: faload         
        //   734: aload           angle
        //   736: iconst_1       
        //   737: faload         
        //   738: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.lookAtAngles:(FF)V
        //   741: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   744: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   747: aload           angle
        //   749: iconst_0       
        //   750: faload         
        //   751: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   754: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   757: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   760: aload           angle
        //   762: iconst_1       
        //   763: faload         
        //   764: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   767: aload_0         /* this */
        //   768: aload_0         /* this */
        //   769: iload           changeItem
        //   771: iload           itemSlot
        //   773: iload           isSprinting
        //   775: iload           shouldSneak
        //   777: aload           vec
        //   779: invokedynamic   BootstrapMethod #8, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;ZIZZLnet/minecraft/util/math/Vec3d;)Ljava/lang/Runnable;
        //   784: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //   787: return         
        //   788: goto            180
        //   791: goto            2047
        //   794: aload_0         /* this */
        //   795: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   798: ifnull          830
        //   801: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   804: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   807: aload_0         /* this */
        //   808: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   811: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   814: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   819: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //   822: if_acmpne       830
        //   825: aload_0         /* this */
        //   826: aconst_null    
        //   827: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   830: aload_0         /* this */
        //   831: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   834: ifnull          1075
        //   837: aload_0         /* this */
        //   838: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //   841: ldc2_w          1000
        //   844: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //   847: ifeq            1074
        //   850: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   853: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   856: new             Lnet/minecraft/util/math/Vec3d;
        //   859: dup            
        //   860: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   863: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   866: getfield        net/minecraft/client/entity/EntityPlayerSP.posX:D
        //   869: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   872: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   875: getfield        net/minecraft/client/entity/EntityPlayerSP.posY:D
        //   878: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   881: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   884: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getEyeHeight:()F
        //   887: f2d            
        //   888: dadd           
        //   889: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //   892: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   895: getfield        net/minecraft/client/entity/EntityPlayerSP.posZ:D
        //   898: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   901: new             Lnet/minecraft/util/math/Vec3d;
        //   904: dup            
        //   905: aload_0         /* this */
        //   906: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   909: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //   912: i2d            
        //   913: ldc2_w          0.5
        //   916: dadd           
        //   917: aload_0         /* this */
        //   918: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   921: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //   924: i2d            
        //   925: ldc2_w          0.5
        //   928: dadd           
        //   929: aload_0         /* this */
        //   930: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   933: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //   936: i2d            
        //   937: ldc2_w          0.5
        //   940: dadd           
        //   941: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //   944: invokevirtual   net/minecraft/client/multiplayer/WorldClient.rayTraceBlocks:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;
        //   947: astore_2        /* result */
        //   948: aload_2         /* result */
        //   949: ifnull          959
        //   952: aload_2         /* result */
        //   953: getfield        net/minecraft/util/math/RayTraceResult.sideHit:Lnet/minecraft/util/EnumFacing;
        //   956: ifnonnull       965
        //   959: getstatic       net/minecraft/util/EnumFacing.UP:Lnet/minecraft/util/EnumFacing;
        //   962: goto            969
        //   965: aload_2         /* result */
        //   966: getfield        net/minecraft/util/math/RayTraceResult.sideHit:Lnet/minecraft/util/EnumFacing;
        //   969: astore_3        /* f */
        //   970: new             Lnet/minecraft/util/math/Vec3d;
        //   973: dup            
        //   974: aload_0         /* this */
        //   975: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.torchPos:Lnet/minecraft/util/math/BlockPos;
        //   978: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //   981: ldc2_w          0.5
        //   984: ldc2_w          0.5
        //   987: ldc2_w          0.5
        //   990: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //   993: new             Lnet/minecraft/util/math/Vec3d;
        //   996: dup            
        //   997: aload_3         /* f */
        //   998: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //  1001: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //  1004: ldc2_w          0.5
        //  1007: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //  1010: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //  1013: astore          vec
        //  1015: iload_1         /* extra */
        //  1016: ifeq            1055
        //  1019: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1022: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1025: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1028: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1031: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1034: aload           vec
        //  1036: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1039: astore          angle
        //  1041: aload           angle
        //  1043: iconst_0       
        //  1044: faload         
        //  1045: aload           angle
        //  1047: iconst_1       
        //  1048: faload         
        //  1049: invokestatic    com/mrzak34/thunderhack/util/KRotationUtil.update:(FF)V
        //  1052: goto            1063
        //  1055: getstatic       com/mrzak34/thunderhack/Thunderhack.rotationManager:Lcom/mrzak34/thunderhack/manager/RotationManager;
        //  1058: aload           vec
        //  1060: invokevirtual   com/mrzak34/thunderhack/manager/RotationManager.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //  1063: aload_0         /* this */
        //  1064: aload_0         /* this */
        //  1065: aload_3         /* f */
        //  1066: invokedynamic   BootstrapMethod #9, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;Lnet/minecraft/util/EnumFacing;)Ljava/lang/Runnable;
        //  1071: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  1074: return         
        //  1075: aload_0         /* this */
        //  1076: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.isOffhand:()Z
        //  1079: ifne            1140
        //  1082: invokestatic    com/mrzak34/thunderhack/util/CrystalUtils.getCrystalSlot:()I
        //  1085: istore_2        /* crystalSlot */
        //  1086: iload_2         /* crystalSlot */
        //  1087: iconst_m1      
        //  1088: if_icmpne       1102
        //  1091: ldc_w           "No crystals found!"
        //  1094: invokestatic    com/mrzak34/thunderhack/features/command/Command.sendMessage:(Ljava/lang/String;)V
        //  1097: aload_0         /* this */
        //  1098: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.toggle:()V
        //  1101: return         
        //  1102: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1105: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1108: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1111: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //  1114: iload_2         /* crystalSlot */
        //  1115: if_icmpeq       1140
        //  1118: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1121: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1124: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1127: iload_2         /* crystalSlot */
        //  1128: putfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //  1131: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1134: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //  1137: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.updateController:()V
        //  1140: aload_0         /* this */
        //  1141: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1144: ifnonnull       1155
        //  1147: aload_0         /* this */
        //  1148: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage.SEARCHING:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  1151: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  1154: return         
        //  1155: iload_1         /* extra */
        //  1156: ifeq            1233
        //  1159: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1162: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1165: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1168: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1171: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1174: new             Lnet/minecraft/util/math/Vec3d;
        //  1177: dup            
        //  1178: aload_0         /* this */
        //  1179: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1182: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  1185: i2d            
        //  1186: ldc2_w          0.5
        //  1189: dadd           
        //  1190: aload_0         /* this */
        //  1191: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1194: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  1197: i2d            
        //  1198: ldc2_w          0.5
        //  1201: dadd           
        //  1202: aload_0         /* this */
        //  1203: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1206: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  1209: i2d            
        //  1210: ldc2_w          0.5
        //  1213: dadd           
        //  1214: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //  1217: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1220: astore_2        /* angle */
        //  1221: aload_2         /* angle */
        //  1222: iconst_0       
        //  1223: faload         
        //  1224: aload_2         /* angle */
        //  1225: iconst_1       
        //  1226: faload         
        //  1227: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.update:(FF)V
        //  1230: goto            1279
        //  1233: new             Lnet/minecraft/util/math/Vec3d;
        //  1236: dup            
        //  1237: aload_0         /* this */
        //  1238: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1241: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  1244: i2d            
        //  1245: ldc2_w          0.5
        //  1248: dadd           
        //  1249: aload_0         /* this */
        //  1250: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1253: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  1256: i2d            
        //  1257: ldc2_w          0.5
        //  1260: dadd           
        //  1261: aload_0         /* this */
        //  1262: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.crystalPos:Lnet/minecraft/util/math/BlockPos;
        //  1265: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  1268: i2d            
        //  1269: ldc2_w          0.5
        //  1272: dadd           
        //  1273: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //  1276: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.lookAtVector:(Lnet/minecraft/util/math/Vec3d;)V
        //  1279: aload_0         /* this */
        //  1280: aload_0         /* this */
        //  1281: invokedynamic   BootstrapMethod #10, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;)Ljava/lang/Runnable;
        //  1286: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  1289: return         
        //  1290: aload_0         /* this */
        //  1291: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1294: ifnonnull       1305
        //  1297: aload_0         /* this */
        //  1298: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage.SEARCHING:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  1301: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  1304: return         
        //  1305: aload_0         /* this */
        //  1306: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1309: istore_2        /* itemSlot */
        //  1310: iload_2         /* itemSlot */
        //  1311: iconst_m1      
        //  1312: if_icmpne       1326
        //  1315: ldc_w           "No redstone found!"
        //  1318: invokestatic    com/mrzak34/thunderhack/features/command/Command.sendMessage:(Ljava/lang/String;)V
        //  1321: aload_0         /* this */
        //  1322: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.toggle:()V
        //  1325: return         
        //  1326: aload_0         /* this */
        //  1327: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1330: aload_0         /* this */
        //  1331: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1334: iconst_3       
        //  1335: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  1338: iconst_0       
        //  1339: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1342: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1345: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1348: aload_0         /* this */
        //  1349: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1352: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //  1355: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1358: checkcast       Lnet/minecraft/item/ItemBlock;
        //  1361: invokevirtual   net/minecraft/item/ItemBlock.getBlock:()Lnet/minecraft/block/Block;
        //  1364: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  1367: if_acmpne       1374
        //  1370: iconst_1       
        //  1371: goto            1375
        //  1374: iconst_0       
        //  1375: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.generateClickLocation:(Lnet/minecraft/util/math/BlockPos;ZZ)Ljava/util/Optional;
        //  1378: astore_3        /* posCL */
        //  1379: aload_3         /* posCL */
        //  1380: invokevirtual   java/util/Optional.isPresent:()Z
        //  1383: ifne            1548
        //  1386: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1389: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1392: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1395: aload_0         /* this */
        //  1396: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1399: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //  1402: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1405: checkcast       Lnet/minecraft/item/ItemBlock;
        //  1408: invokevirtual   net/minecraft/item/ItemBlock.getBlock:()Lnet/minecraft/block/Block;
        //  1411: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  1414: if_acmpne       1548
        //  1417: getstatic       net/minecraft/util/EnumFacing.HORIZONTALS:[Lnet/minecraft/util/EnumFacing;
        //  1420: astore          4
        //  1422: aload           4
        //  1424: arraylength    
        //  1425: istore          5
        //  1427: iconst_0       
        //  1428: istore          6
        //  1430: iload           6
        //  1432: iload           5
        //  1434: if_icmpge       1548
        //  1437: aload           4
        //  1439: iload           6
        //  1441: aaload         
        //  1442: astore          torchFacing
        //  1444: aload           torchFacing
        //  1446: aload_0         /* this */
        //  1447: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1450: invokevirtual   net/minecraft/util/EnumFacing.equals:(Ljava/lang/Object;)Z
        //  1453: ifne            1542
        //  1456: aload           torchFacing
        //  1458: aload_0         /* this */
        //  1459: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1462: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  1465: invokevirtual   net/minecraft/util/EnumFacing.equals:(Ljava/lang/Object;)Z
        //  1468: ifeq            1474
        //  1471: goto            1542
        //  1474: aload_0         /* this */
        //  1475: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1478: aload_0         /* this */
        //  1479: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.faceOffset:Lnet/minecraft/util/EnumFacing;
        //  1482: iconst_2       
        //  1483: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  1486: aload           torchFacing
        //  1488: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  1491: iconst_0       
        //  1492: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1495: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1498: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1501: aload_0         /* this */
        //  1502: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  1505: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //  1508: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1511: checkcast       Lnet/minecraft/item/ItemBlock;
        //  1514: invokevirtual   net/minecraft/item/ItemBlock.getBlock:()Lnet/minecraft/block/Block;
        //  1517: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  1520: if_acmpne       1527
        //  1523: iconst_1       
        //  1524: goto            1528
        //  1527: iconst_0       
        //  1528: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.generateClickLocation:(Lnet/minecraft/util/math/BlockPos;ZZ)Ljava/util/Optional;
        //  1531: astore_3        /* posCL */
        //  1532: aload_3         /* posCL */
        //  1533: invokevirtual   java/util/Optional.isPresent:()Z
        //  1536: ifeq            1542
        //  1539: goto            1548
        //  1542: iinc            6, 1
        //  1545: goto            1430
        //  1548: aload_3         /* posCL */
        //  1549: invokevirtual   java/util/Optional.isPresent:()Z
        //  1552: ifeq            1737
        //  1555: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1558: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1561: getfield        net/minecraft/client/entity/EntityPlayerSP.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //  1564: getfield        net/minecraft/entity/player/InventoryPlayer.currentItem:I
        //  1567: iload_2         /* itemSlot */
        //  1568: if_icmpeq       1575
        //  1571: iconst_1       
        //  1572: goto            1576
        //  1575: iconst_0       
        //  1576: istore          changeItem
        //  1578: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1581: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1584: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isSprinting:()Z
        //  1587: istore          isSprinting
        //  1589: aload_3         /* posCL */
        //  1590: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //  1593: checkcast       Lcom/mrzak34/thunderhack/util/BlockUtils$ClickLocation;
        //  1596: getfield        com/mrzak34/thunderhack/util/BlockUtils$ClickLocation.neighbour:Lnet/minecraft/util/math/BlockPos;
        //  1599: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.shouldSneakWhileRightClicking:(Lnet/minecraft/util/math/BlockPos;)Z
        //  1602: istore          shouldSneak
        //  1604: new             Lnet/minecraft/util/math/Vec3d;
        //  1607: dup            
        //  1608: aload_3         /* posCL */
        //  1609: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //  1612: checkcast       Lcom/mrzak34/thunderhack/util/BlockUtils$ClickLocation;
        //  1615: getfield        com/mrzak34/thunderhack/util/BlockUtils$ClickLocation.neighbour:Lnet/minecraft/util/math/BlockPos;
        //  1618: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //  1621: ldc2_w          0.5
        //  1624: ldc2_w          0.5
        //  1627: ldc2_w          0.5
        //  1630: invokevirtual   net/minecraft/util/math/Vec3d.add:(DDD)Lnet/minecraft/util/math/Vec3d;
        //  1633: new             Lnet/minecraft/util/math/Vec3d;
        //  1636: dup            
        //  1637: aload_3         /* posCL */
        //  1638: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //  1641: checkcast       Lcom/mrzak34/thunderhack/util/BlockUtils$ClickLocation;
        //  1644: getfield        com/mrzak34/thunderhack/util/BlockUtils$ClickLocation.opposite:Lnet/minecraft/util/EnumFacing;
        //  1647: invokevirtual   net/minecraft/util/EnumFacing.getDirectionVec:()Lnet/minecraft/util/math/Vec3i;
        //  1650: invokespecial   net/minecraft/util/math/Vec3d.<init>:(Lnet/minecraft/util/math/Vec3i;)V
        //  1653: ldc2_w          0.5
        //  1656: invokevirtual   net/minecraft/util/math/Vec3d.scale:(D)Lnet/minecraft/util/math/Vec3d;
        //  1659: invokevirtual   net/minecraft/util/math/Vec3d.add:(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;
        //  1662: astore          vec
        //  1664: iload_1         /* extra */
        //  1665: ifeq            1704
        //  1668: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1671: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1674: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1677: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1680: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1683: aload           vec
        //  1685: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1688: astore          angle
        //  1690: aload           angle
        //  1692: iconst_0       
        //  1693: faload         
        //  1694: aload           angle
        //  1696: iconst_1       
        //  1697: faload         
        //  1698: invokestatic    com/mrzak34/thunderhack/util/KRotationUtil.update:(FF)V
        //  1701: goto            1712
        //  1704: getstatic       com/mrzak34/thunderhack/Thunderhack.rotationManager:Lcom/mrzak34/thunderhack/manager/RotationManager;
        //  1707: aload           vec
        //  1709: invokevirtual   com/mrzak34/thunderhack/manager/RotationManager.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //  1712: aload_3         /* posCL */
        //  1713: astore          finalCL
        //  1715: aload_0         /* this */
        //  1716: aload_0         /* this */
        //  1717: iload           changeItem
        //  1719: iload_2         /* itemSlot */
        //  1720: iload           isSprinting
        //  1722: iload           shouldSneak
        //  1724: aload           finalCL
        //  1726: aload           vec
        //  1728: invokedynamic   BootstrapMethod #11, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;ZIZZLjava/util/Optional;Lnet/minecraft/util/math/Vec3d;)Ljava/lang/Runnable;
        //  1733: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  1736: return         
        //  1737: aload_0         /* this */
        //  1738: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage.BREAKING:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  1741: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  1744: return         
        //  1745: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1748: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  1751: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //  1754: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //  1759: invokedynamic   BootstrapMethod #12, test:()Ljava/util/function/Predicate;
        //  1764: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //  1769: aload_0         /* this */
        //  1770: invokedynamic   BootstrapMethod #13, test:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;)Ljava/util/function/Predicate;
        //  1775: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //  1780: invokedynamic   BootstrapMethod #14, apply:()Ljava/util/function/Function;
        //  1785: invokestatic    java/util/Comparator.comparing:(Ljava/util/function/Function;)Ljava/util/Comparator;
        //  1788: invokeinterface java/util/stream/Stream.min:(Ljava/util/Comparator;)Ljava/util/Optional;
        //  1793: aconst_null    
        //  1794: invokevirtual   java/util/Optional.orElse:(Ljava/lang/Object;)Ljava/lang/Object;
        //  1797: checkcast       Lnet/minecraft/entity/Entity;
        //  1800: astore_2        /* nearestCrystal */
        //  1801: aload_2         /* nearestCrystal */
        //  1802: ifnull          1935
        //  1805: aload_0         /* this */
        //  1806: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.antiSuicide:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  1809: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  1812: checkcast       Ljava/lang/Boolean;
        //  1815: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  1818: ifeq            1838
        //  1821: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1824: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1827: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHealth:()F
        //  1830: ldc_w           7.0
        //  1833: fcmpg          
        //  1834: ifge            1838
        //  1837: return         
        //  1838: aload_0         /* this */
        //  1839: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.delayTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //  1842: invokevirtual   com/mrzak34/thunderhack/util/Timer.reset:()V
        //  1845: aload_0         /* this */
        //  1846: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.renderTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //  1849: invokevirtual   com/mrzak34/thunderhack/util/Timer.reset:()V
        //  1852: aload_0         /* this */
        //  1853: aload_0         /* this */
        //  1854: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.breakDelay:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  1857: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  1860: checkcast       Ljava/lang/Integer;
        //  1863: invokevirtual   java/lang/Integer.intValue:()I
        //  1866: bipush          10
        //  1868: imul           
        //  1869: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.delayTime:I
        //  1872: iload_1         /* extra */
        //  1873: ifeq            1911
        //  1876: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1879: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1882: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1885: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1888: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1891: aload_2         /* nearestCrystal */
        //  1892: invokevirtual   net/minecraft/entity/Entity.getPositionVector:()Lnet/minecraft/util/math/Vec3d;
        //  1895: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1898: astore_3        /* angle */
        //  1899: aload_3         /* angle */
        //  1900: iconst_0       
        //  1901: faload         
        //  1902: aload_3         /* angle */
        //  1903: iconst_1       
        //  1904: faload         
        //  1905: invokestatic    com/mrzak34/thunderhack/util/KRotationUtil.update:(FF)V
        //  1908: goto            1921
        //  1911: getstatic       com/mrzak34/thunderhack/Thunderhack.rotationManager:Lcom/mrzak34/thunderhack/manager/RotationManager;
        //  1914: aload_2         /* nearestCrystal */
        //  1915: invokevirtual   net/minecraft/entity/Entity.getPositionVector:()Lnet/minecraft/util/math/Vec3d;
        //  1918: invokevirtual   com/mrzak34/thunderhack/manager/RotationManager.lookAtVec3d:(Lnet/minecraft/util/math/Vec3d;)V
        //  1921: aload_0         /* this */
        //  1922: aload_0         /* this */
        //  1923: aload_2         /* nearestCrystal */
        //  1924: invokedynamic   BootstrapMethod #15, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;Lnet/minecraft/entity/Entity;)Ljava/lang/Runnable;
        //  1929: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  1932: goto            2047
        //  1935: iload_1         /* extra */
        //  1936: ifeq            2009
        //  1939: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1942: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1945: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  1948: invokevirtual   net/minecraft/client/Minecraft.getRenderPartialTicks:()F
        //  1951: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  1954: new             Lnet/minecraft/util/math/Vec3d;
        //  1957: dup            
        //  1958: aload_0         /* this */
        //  1959: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1962: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  1965: i2d            
        //  1966: ldc2_w          0.5
        //  1969: dadd           
        //  1970: aload_0         /* this */
        //  1971: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1974: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  1977: i2d            
        //  1978: aload_0         /* this */
        //  1979: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  1982: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  1985: i2d            
        //  1986: ldc2_w          0.5
        //  1989: dadd           
        //  1990: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //  1993: invokestatic    com/mrzak34/thunderhack/manager/KonsaRotationManager.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  1996: astore_3        /* angle */
        //  1997: aload_3         /* angle */
        //  1998: iconst_0       
        //  1999: faload         
        //  2000: aload_3         /* angle */
        //  2001: iconst_1       
        //  2002: faload         
        //  2003: invokestatic    com/mrzak34/thunderhack/util/KRotationUtil.update:(FF)V
        //  2006: goto            2047
        //  2009: getstatic       com/mrzak34/thunderhack/Thunderhack.rotationManager:Lcom/mrzak34/thunderhack/manager/RotationManager;
        //  2012: aload_0         /* this */
        //  2013: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  2016: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  2019: i2d            
        //  2020: ldc2_w          0.5
        //  2023: dadd           
        //  2024: aload_0         /* this */
        //  2025: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  2028: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  2031: i2d            
        //  2032: aload_0         /* this */
        //  2033: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.facePos:Lnet/minecraft/util/math/BlockPos;
        //  2036: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  2039: i2d            
        //  2040: ldc2_w          0.5
        //  2043: dadd           
        //  2044: invokevirtual   com/mrzak34/thunderhack/manager/RotationManager.lookAtXYZ:(DDD)V
        //  2047: goto            2831
        //  2050: aload_0         /* this */
        //  2051: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage.SEARCHING:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  2054: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.stage:Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura$Stage;
        //  2057: invokestatic    com/mrzak34/thunderhack/features/modules/combat/PistonAura.getPistonSlot:()I
        //  2060: istore_2        /* pistonSlot */
        //  2061: iload_2         /* pistonSlot */
        //  2062: iconst_m1      
        //  2063: if_icmpne       2077
        //  2066: ldc_w           "No pistons found!"
        //  2069: invokestatic    com/mrzak34/thunderhack/features/command/Command.sendMessage:(Ljava/lang/String;)V
        //  2072: aload_0         /* this */
        //  2073: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.toggle:()V
        //  2076: return         
        //  2077: aload_0         /* this */
        //  2078: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getRedstoneBlockSlot:()I
        //  2081: istore_3        /* redstoneBlockSlot */
        //  2082: iload_3         /* redstoneBlockSlot */
        //  2083: iconst_m1      
        //  2084: if_icmpne       2098
        //  2087: ldc_w           "No redstone found!"
        //  2090: invokestatic    com/mrzak34/thunderhack/features/command/Command.sendMessage:(Ljava/lang/String;)V
        //  2093: aload_0         /* this */
        //  2094: invokevirtual   com/mrzak34/thunderhack/features/modules/combat/PistonAura.toggle:()V
        //  2097: return         
        //  2098: aload_0         /* this */
        //  2099: invokespecial   com/mrzak34/thunderhack/features/modules/combat/PistonAura.getTargets:()Ljava/util/List;
        //  2102: astore          candidates
        //  2104: aload           candidates
        //  2106: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //  2111: astore          5
        //  2113: aload           5
        //  2115: invokeinterface java/util/Iterator.hasNext:()Z
        //  2120: ifeq            2831
        //  2123: aload           5
        //  2125: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //  2130: checkcast       Lnet/minecraft/entity/player/EntityPlayer;
        //  2133: astore          candidate
        //  2135: aload_0         /* this */
        //  2136: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.smart:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2139: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2142: checkcast       Ljava/lang/Boolean;
        //  2145: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2148: ifeq            2198
        //  2151: new             Lnet/minecraft/util/math/BlockPos;
        //  2154: dup            
        //  2155: aload           candidate
        //  2157: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  2160: invokestatic    com/mrzak34/thunderhack/util/BlockUtils.isHole:(Lnet/minecraft/util/math/BlockPos;)Z
        //  2163: ifne            2198
        //  2166: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2169: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2172: new             Lnet/minecraft/util/math/BlockPos;
        //  2175: dup            
        //  2176: aload           candidate
        //  2178: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  2181: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2184: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2189: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //  2192: if_acmpne       2198
        //  2195: goto            2113
        //  2198: new             Lnet/minecraft/util/math/BlockPos;
        //  2201: dup            
        //  2202: aload           candidate
        //  2204: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  2207: invokevirtual   net/minecraft/util/math/BlockPos.up:()Lnet/minecraft/util/math/BlockPos;
        //  2210: astore          candidatePos
        //  2212: aload_0         /* this */
        //  2213: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.antiSuicide:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2216: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2219: checkcast       Ljava/lang/Boolean;
        //  2222: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2225: ifeq            2252
        //  2228: aload           candidatePos
        //  2230: new             Lnet/minecraft/util/math/BlockPos;
        //  2233: dup            
        //  2234: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2237: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  2240: invokespecial   net/minecraft/util/math/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  2243: invokevirtual   net/minecraft/util/math/BlockPos.equals:(Ljava/lang/Object;)Z
        //  2246: ifeq            2252
        //  2249: goto            2113
        //  2252: getstatic       net/minecraft/util/EnumFacing.HORIZONTALS:[Lnet/minecraft/util/EnumFacing;
        //  2255: astore          8
        //  2257: aload           8
        //  2259: arraylength    
        //  2260: istore          9
        //  2262: iconst_0       
        //  2263: istore          10
        //  2265: iload           10
        //  2267: iload           9
        //  2269: if_icmpge       2555
        //  2272: aload           8
        //  2274: iload           10
        //  2276: aaload         
        //  2277: astore          faceTryOffset
        //  2279: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2282: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2285: aload           candidatePos
        //  2287: aload           faceTryOffset
        //  2289: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2292: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2295: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2300: instanceof      Lnet/minecraft/block/BlockPistonBase;
        //  2303: ifne            2341
        //  2306: aload_0         /* this */
        //  2307: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.placedPistonTimer:Lcom/mrzak34/thunderhack/util/Timer;
        //  2310: invokestatic    com/mrzak34/thunderhack/util/CrystalUtils.ping:()I
        //  2313: sipush          150
        //  2316: iadd           
        //  2317: i2l            
        //  2318: invokevirtual   com/mrzak34/thunderhack/util/Timer.passedMs:(J)Z
        //  2321: ifne            2549
        //  2324: aload           candidatePos
        //  2326: aload           faceTryOffset
        //  2328: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2331: aload_0         /* this */
        //  2332: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.placedPiston:Lnet/minecraft/util/math/BlockPos;
        //  2335: invokevirtual   net/minecraft/util/math/BlockPos.equals:(Ljava/lang/Object;)Z
        //  2338: ifeq            2549
        //  2341: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2344: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2347: aload           candidatePos
        //  2349: aload           faceTryOffset
        //  2351: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2354: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2357: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2362: instanceof      Lnet/minecraft/block/BlockPistonBase;
        //  2365: ifeq            2413
        //  2368: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2371: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2374: aload           candidatePos
        //  2376: aload           faceTryOffset
        //  2378: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2381: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2384: getstatic       net/minecraft/block/BlockDirectional.FACING:Lnet/minecraft/block/properties/PropertyDirection;
        //  2387: invokeinterface net/minecraft/block/state/IBlockState.getValue:(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;
        //  2392: checkcast       Lnet/minecraft/util/EnumFacing;
        //  2395: astore          enumfacing
        //  2397: aload           enumfacing
        //  2399: aload           faceTryOffset
        //  2401: invokevirtual   net/minecraft/util/EnumFacing.getOpposite:()Lnet/minecraft/util/EnumFacing;
        //  2404: invokevirtual   net/minecraft/util/EnumFacing.equals:(Ljava/lang/Object;)Z
        //  2407: ifne            2413
        //  2410: goto            2549
        //  2413: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2416: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2419: aload           candidatePos
        //  2421: aload           faceTryOffset
        //  2423: iconst_2       
        //  2424: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2427: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2430: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2435: getstatic       net/minecraft/init/Blocks.REDSTONE_BLOCK:Lnet/minecraft/block/Block;
        //  2438: if_acmpeq       2831
        //  2441: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2444: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2447: aload           candidatePos
        //  2449: aload           faceTryOffset
        //  2451: iconst_2       
        //  2452: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2455: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2458: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2463: getstatic       net/minecraft/init/Blocks.REDSTONE_TORCH:Lnet/minecraft/block/Block;
        //  2466: if_acmpne       2472
        //  2469: goto            2831
        //  2472: aload           candidatePos
        //  2474: aload           faceTryOffset
        //  2476: iconst_2       
        //  2477: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2480: aload_0         /* this */
        //  2481: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2484: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2487: checkcast       Ljava/lang/Boolean;
        //  2490: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2493: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.canPlaceBlock:(Lnet/minecraft/util/math/BlockPos;Z)Z
        //  2496: ifeq            2831
        //  2499: aload           candidatePos
        //  2501: aload           faceTryOffset
        //  2503: iconst_2       
        //  2504: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2507: iconst_1       
        //  2508: iload_1         /* extra */
        //  2509: aload_0         /* this */
        //  2510: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2513: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2516: checkcast       Ljava/lang/Boolean;
        //  2519: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2522: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.preparePlacement:(Lnet/minecraft/util/math/BlockPos;ZZZ)Lcom/mrzak34/thunderhack/util/InteractionUtil$Placement;
        //  2525: astore          placement
        //  2527: aload           placement
        //  2529: ifnull          2546
        //  2532: aload_0         /* this */
        //  2533: aload_0         /* this */
        //  2534: iload_3         /* redstoneBlockSlot */
        //  2535: aload           placement
        //  2537: invokedynamic   BootstrapMethod #16, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;ILcom/mrzak34/thunderhack/util/InteractionUtil$Placement;)Ljava/lang/Runnable;
        //  2542: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  2545: return         
        //  2546: goto            2831
        //  2549: iinc            10, 1
        //  2552: goto            2265
        //  2555: getstatic       net/minecraft/util/EnumFacing.HORIZONTALS:[Lnet/minecraft/util/EnumFacing;
        //  2558: astore          8
        //  2560: aload           8
        //  2562: arraylength    
        //  2563: istore          9
        //  2565: iconst_0       
        //  2566: istore          10
        //  2568: iload           10
        //  2570: iload           9
        //  2572: if_icmpge       2828
        //  2575: aload           8
        //  2577: iload           10
        //  2579: aaload         
        //  2580: astore          faceTryOffset
        //  2582: aload           candidatePos
        //  2584: aload           faceTryOffset
        //  2586: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2589: aload_0         /* this */
        //  2590: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2593: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2596: checkcast       Ljava/lang/Boolean;
        //  2599: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2602: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.canPlaceBlock:(Lnet/minecraft/util/math/BlockPos;Z)Z
        //  2605: ifeq            2822
        //  2608: aload_0         /* this */
        //  2609: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2612: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2615: checkcast       Ljava/lang/Boolean;
        //  2618: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2621: ifeq            2642
        //  2624: aload           candidatePos
        //  2626: aload           faceTryOffset
        //  2628: iconst_2       
        //  2629: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2632: iconst_1       
        //  2633: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.canPlaceBlock:(Lnet/minecraft/util/math/BlockPos;Z)Z
        //  2636: ifeq            2822
        //  2639: goto            2670
        //  2642: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2645: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //  2648: aload           candidatePos
        //  2650: aload           faceTryOffset
        //  2652: iconst_2       
        //  2653: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;I)Lnet/minecraft/util/math/BlockPos;
        //  2656: invokevirtual   net/minecraft/client/multiplayer/WorldClient.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2659: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2664: getstatic       net/minecraft/init/Blocks.AIR:Lnet/minecraft/block/Block;
        //  2667: if_acmpne       2822
        //  2670: getstatic       com/mrzak34/thunderhack/features/modules/combat/PistonAura.mc:Lnet/minecraft/client/Minecraft;
        //  2673: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  2676: fconst_1       
        //  2677: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getPositionEyes:(F)Lnet/minecraft/util/math/Vec3d;
        //  2680: new             Lnet/minecraft/util/math/Vec3d;
        //  2683: dup            
        //  2684: aload           candidatePos
        //  2686: aload           faceTryOffset
        //  2688: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2691: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //  2694: i2d            
        //  2695: ldc2_w          0.5
        //  2698: dadd           
        //  2699: aload           candidatePos
        //  2701: aload           faceTryOffset
        //  2703: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2706: invokevirtual   net/minecraft/util/math/BlockPos.getY:()I
        //  2709: i2d            
        //  2710: dconst_1       
        //  2711: dadd           
        //  2712: aload           candidatePos
        //  2714: aload           faceTryOffset
        //  2716: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2719: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //  2722: i2d            
        //  2723: ldc2_w          0.5
        //  2726: dadd           
        //  2727: invokespecial   net/minecraft/util/math/Vec3d.<init>:(DDD)V
        //  2730: invokestatic    com/mrzak34/thunderhack/util/SilentRotaionUtil.calculateAngle:(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)[F
        //  2733: astore          rots
        //  2735: aload           rots
        //  2737: iconst_0       
        //  2738: faload         
        //  2739: f2d            
        //  2740: invokestatic    net/minecraft/util/EnumFacing.fromAngle:(D)Lnet/minecraft/util/EnumFacing;
        //  2743: astore          facing
        //  2745: aload           rots
        //  2747: iconst_1       
        //  2748: faload         
        //  2749: invokestatic    java/lang/Math.abs:(F)F
        //  2752: ldc_w           55.0
        //  2755: fcmpl          
        //  2756: ifle            2762
        //  2759: goto            2822
        //  2762: aload           facing
        //  2764: aload           faceTryOffset
        //  2766: if_acmpeq       2772
        //  2769: goto            2822
        //  2772: aload           candidatePos
        //  2774: aload           faceTryOffset
        //  2776: invokevirtual   net/minecraft/util/math/BlockPos.offset:(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
        //  2779: iconst_1       
        //  2780: iload_1         /* extra */
        //  2781: aload_0         /* this */
        //  2782: getfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.raytrace:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //  2785: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //  2788: checkcast       Ljava/lang/Boolean;
        //  2791: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  2794: invokestatic    com/mrzak34/thunderhack/util/InteractionUtil.preparePlacement:(Lnet/minecraft/util/math/BlockPos;ZZZ)Lcom/mrzak34/thunderhack/util/InteractionUtil$Placement;
        //  2797: astore          placement
        //  2799: aload           placement
        //  2801: ifnull          2822
        //  2804: aload_0         /* this */
        //  2805: aload_0         /* this */
        //  2806: iload_2         /* pistonSlot */
        //  2807: aload           placement
        //  2809: aload           candidatePos
        //  2811: aload           faceTryOffset
        //  2813: invokedynamic   BootstrapMethod #17, run:(Lcom/mrzak34/thunderhack/features/modules/combat/PistonAura;ILcom/mrzak34/thunderhack/util/InteractionUtil$Placement;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Ljava/lang/Runnable;
        //  2818: putfield        com/mrzak34/thunderhack/features/modules/combat/PistonAura.postAction:Ljava/lang/Runnable;
        //  2821: return         
        //  2822: iinc            10, 1
        //  2825: goto            2568
        //  2828: goto            2113
        //  2831: return         
        //    StackMapTable: 00 4D 21 0F FB 00 41 34 FD 00 0B 07 01 CF 07 01 D5 FC 00 64 07 01 DD FB 00 50 FC 00 15 01 13 14 40 01 FF 00 6D 00 0A 07 00 02 01 07 01 CF 07 01 D5 07 01 DD 01 01 01 01 07 02 32 00 00 14 2D 2D 2D FB 00 47 FF 00 14 00 04 07 00 02 01 07 01 CF 07 01 D5 00 00 FA 00 02 FA 00 02 23 FC 00 80 07 00 2E 05 43 07 02 3D FD 00 55 07 02 3D 07 02 32 07 F8 00 0A 00 FC 00 1A 01 FA 00 25 0E FB 00 4D 2D 0A 0E FC 00 14 01 FF 00 2F 00 03 07 00 02 01 01 00 02 07 01 E3 01 FF 00 00 00 03 07 00 02 01 01 00 03 07 01 E3 01 01 FF 00 36 00 07 07 00 02 01 01 07 02 ED 07 02 F5 01 01 00 00 FC 00 2B 07 02 3D FF 00 34 00 08 07 00 02 01 01 07 02 ED 07 02 F5 01 01 07 02 3D 00 02 07 01 E3 01 FF 00 00 00 08 07 00 02 01 01 07 02 ED 07 02 F5 01 01 07 02 3D 00 03 07 01 E3 01 01 FA 00 0D F8 00 05 1A 40 01 FF 00 7F 00 08 07 00 02 01 01 07 02 ED 01 01 01 07 02 32 00 00 07 FF 00 18 00 04 07 00 02 01 01 07 02 ED 00 00 F9 00 07 FC 00 5C 07 03 49 FB 00 48 09 0D FB 00 49 FA 00 25 02 FC 00 1A 01 FC 00 14 01 FD 00 0E 07 01 CF 07 01 D5 FC 00 54 07 01 DD FC 00 35 07 01 E3 FE 00 0C 07 02 F5 01 01 FC 00 4B 07 02 3D FB 00 47 3A FB 00 49 FA 00 02 F8 00 05 FE 00 0C 07 02 F5 01 01 FC 00 49 07 02 3D 1B FD 00 5B 07 03 98 07 02 3D 09 F8 00 31 FF 00 05 00 06 07 00 02 01 01 01 07 01 CF 07 01 D5 00 00 FF 00 02 00 02 07 00 02 01 00 00
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void removeburrow(final EntityPlayer nigger) {
        this.brdel.reset();
        final EnumFacing direction = BlockUtil.getPlaceableSide(new BlockPos(nigger.posX, nigger.posY, nigger.posZ));
        InventoryUtil.switchToHotbarSlot(InventoryUtil.getPicatHotbar(), false);
        if (this.rmode.getValue() == rMode.Oyvey) {
            Thunderhack.rotationManager.lookAtVec3d(new Vec3d((Vec3i)new BlockPos(nigger.posX, nigger.posY, nigger.posZ)));
        }
        if (this.rmode.getValue() == rMode.Konas) {
            final float[] angle = KonsaRotationManager.calculateAngle(PistonAura.mc.player.getPositionEyes(PistonAura.mc.getRenderPartialTicks()), new Vec3d((Vec3i)new BlockPos(nigger.posX, nigger.posY, nigger.posZ)));
            KRotationUtil.update(angle[0], angle[1]);
        }
        if (this.rmode.getValue() == rMode.Silent) {
            final float[] angle = KonsaRotationManager.calculateAngle(PistonAura.mc.player.getPositionEyes(PistonAura.mc.getRenderPartialTicks()), new Vec3d((Vec3i)new BlockPos(nigger.posX, nigger.posY, nigger.posZ)));
            SilentRotaionUtil.lookAtAngles(angle[0], angle[1]);
        }
        if (this.rmode.getValue() == rMode.Silent2) {
            final float[] angle = KonsaRotationManager.calculateAngle(PistonAura.mc.player.getPositionEyes(PistonAura.mc.getRenderPartialTicks()), new Vec3d((Vec3i)new BlockPos(nigger.posX, nigger.posY, nigger.posZ)));
            SilentRotaionUtil.update(angle[0], angle[1]);
        }
        if (this.rmode.getValue() == rMode.Client) {
            final float[] angle = KonsaRotationManager.calculateAngle(PistonAura.mc.player.getPositionEyes(PistonAura.mc.getRenderPartialTicks()), new Vec3d((Vec3i)new BlockPos(nigger.posX, nigger.posY, nigger.posZ)));
            SilentRotaionUtil.lookAtAngles(angle[0], angle[1]);
            PistonAura.mc.player.rotationYaw = angle[0];
            PistonAura.mc.player.rotationPitch = angle[1];
        }
        if (this.bmode.getValue() == bMode.Packet && !this.sendPacket) {
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(nigger.posX, nigger.posY, nigger.posZ), direction));
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(nigger.posX, nigger.posY, nigger.posZ), direction));
            this.sendPacket = true;
            return;
        }
        if (this.bmode.getValue() == bMode.Normal) {
            PistonAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            PistonAura.mc.playerController.onPlayerDamageBlock(new BlockPos(nigger.posX, nigger.posY, nigger.posZ), direction);
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0) {
            if (this.tickCounter < this.actionInterval.getValue()) {
                ++this.tickCounter;
            }
            if (event.isCanceled() || !InteractionUtil.canPlaceNormally()) {
                return;
            }
            if (this.stage == Stage.BREAKING) {
                SilentRotaionUtil.lookAtXYZ(this.facePos.getX() + 0.5f, (float)this.facePos.getY(), this.facePos.getZ() + 0.5f);
            }
            if (this.tickCounter < this.actionInterval.getValue()) {
                return;
            }
            this.handleAction(false);
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final UpdateWalkingPlayerEvent event) {
        if (this.postAction != null) {
            this.actionTimer.reset();
            this.tickCounter = 0;
            this.postAction.run();
            this.postAction = null;
            for (int extraBlocks = 0; extraBlocks < this.actionShift.getValue() - 1; ++extraBlocks) {
                this.handleAction(true);
                if (this.postAction == null) {
                    return;
                }
                this.postAction.run();
                this.postAction = null;
            }
        }
        this.postAction = null;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (this.crystalPos == null) {
                return;
            }
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && this.crystalPos.up().getDistance((int)packet.getX(), (int)packet.getY(), (int)packet.getZ()) <= 2.0) {
                this.stage = Stage.SEARCHING;
                this.delayTime = 0;
            }
        }
        if (event.getPacket() instanceof SPacketBlockChange && this.torchPos != null && ((SPacketBlockChange)event.getPacket()).getBlockPosition().equals((Object)this.torchPos) && ((SPacketBlockChange)event.getPacket()).getBlockState().getBlock() instanceof BlockAir) {
            this.torchPos = null;
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.facePos == null || this.faceOffset == null) {
            return;
        }
        if (this.renderTimer.passedMs(1000L)) {
            return;
        }
        if (this.renderCurrent.getValue()) {
            BlockPos renderBlock = null;
            switch (this.stage) {
                case SEARCHING: {
                    renderBlock = this.facePos.down().offset(this.faceOffset, 2);
                    break;
                }
                case CRYSTAL:
                case BREAKING: {
                    renderBlock = this.facePos.down().offset(this.faceOffset, 1);
                    break;
                }
                case REDSTONE: {
                    renderBlock = this.facePos.down().offset(this.faceOffset, 3);
                    break;
                }
            }
            if (renderBlock != null) {
                AxisAlignedBB axisAlignedBB = PistonAura.mc.world.getBlockState(renderBlock).getBoundingBox((IBlockAccess)PistonAura.mc.world, renderBlock).offset(renderBlock);
                axisAlignedBB = axisAlignedBB.offset(-((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosZ());
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawFill(axisAlignedBB, this.colorCurrent.getValue().getRawColor());
                BlockRenderUtil.releaseGL();
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawOutline(axisAlignedBB, this.outlineColorCurrent.getValue().getRawColor(), 1.5f);
                BlockRenderUtil.releaseGL();
            }
        }
        if (this.renderFull.getValue()) {
            AxisAlignedBB axisAlignedBB2 = null;
            switch (this.faceOffset) {
                case NORTH: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, -3.0).offset(this.facePos.down());
                    break;
                }
                case SOUTH: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 3.0).offset(this.facePos.down());
                    break;
                }
                case EAST: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, 3.0, 1.0, 1.0).offset(this.facePos.down());
                    break;
                }
                case WEST: {
                    axisAlignedBB2 = new AxisAlignedBB(0.0, 0.0, 0.0, -3.0, 1.0, 1.0).offset(this.facePos.down());
                    break;
                }
            }
            if (axisAlignedBB2 != null) {
                axisAlignedBB2 = axisAlignedBB2.offset(-((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosX(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosY(), -((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosZ());
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawFill(axisAlignedBB2, this.colorFull.getValue().getRawColor());
                BlockRenderUtil.releaseGL();
                BlockRenderUtil.prepareGL();
                BlockRenderUtil.drawOutline(axisAlignedBB2, this.outlineColorFull.getValue().getRawColor(), 1.5f);
                BlockRenderUtil.releaseGL();
            }
        }
        if (this.arrow.getValue()) {
            Vec3d firstVec = null;
            Vec3d secondVec = null;
            Vec3d thirdVec = null;
            final BlockPos offsetPos = this.facePos.offset(this.faceOffset, 2);
            final Vec3d properPos = new Vec3d(offsetPos.getX() + 0.5 - ((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosX(), offsetPos.getY() + 1 - ((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosY(), offsetPos.getZ() + 0.5 - ((IRenderManager)PistonAura.mc.getRenderManager()).getRenderPosZ());
            switch (this.faceOffset) {
                case NORTH: {
                    firstVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z - 0.5);
                    secondVec = new Vec3d(properPos.x, properPos.y, properPos.z + 0.5);
                    thirdVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z - 0.5);
                    break;
                }
                case SOUTH: {
                    firstVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z + 0.5);
                    secondVec = new Vec3d(properPos.x, properPos.y, properPos.z - 0.5);
                    thirdVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z + 0.5);
                    break;
                }
                case EAST: {
                    firstVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z - 0.5);
                    secondVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z);
                    thirdVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z + 0.5);
                    break;
                }
                case WEST: {
                    firstVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z - 0.5);
                    secondVec = new Vec3d(properPos.x + 0.5, properPos.y, properPos.z);
                    thirdVec = new Vec3d(properPos.x - 0.5, properPos.y, properPos.z + 0.5);
                    break;
                }
            }
            if (firstVec != null) {
                BlockRenderUtil.prepareGL();
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2896);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glLineWidth(5.0f);
                GL11.glColor4f((this.arrowColor.getValue().getRawColor() >> 16 & 0xFF) / 255.0f, (this.arrowColor.getValue().getRawColor() >> 8 & 0xFF) / 255.0f, (this.arrowColor.getValue().getRawColor() & 0xFF) / 255.0f, (this.arrowColor.getValue().getRawColor() >> 24 & 0xFF) / 255.0f);
                if (this.topArrow.getValue()) {
                    GL11.glBegin(1);
                    GL11.glVertex3d(firstVec.x, firstVec.y, firstVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y, secondVec.z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(thirdVec.x, thirdVec.y, thirdVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y, secondVec.z);
                    GL11.glEnd();
                }
                if (this.bottomArrow.getValue()) {
                    GL11.glBegin(1);
                    GL11.glVertex3d(firstVec.x, firstVec.y - 1.0, firstVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y - 1.0, secondVec.z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(thirdVec.x, thirdVec.y - 1.0, thirdVec.z);
                    GL11.glVertex3d(secondVec.x, secondVec.y - 1.0, secondVec.z);
                    GL11.glEnd();
                }
                GL11.glLineWidth(1.0f);
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                BlockRenderUtil.releaseGL();
            }
        }
    }
    
    public boolean isOffhand() {
        return PistonAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }
    
    private boolean evaluateTarget(final EntityPlayer candidate) {
        if (this.getRedstoneBlockSlot() == -1) {
            Command.sendMessage("No redstone found!");
            this.toggle();
            return false;
        }
        BlockPos tempFacePos = new BlockPos((Entity)candidate).up();
        if (this.evaluateTarget(tempFacePos)) {
            return true;
        }
        tempFacePos = new BlockPos((Entity)candidate).up().up();
        return this.evaluateTarget(tempFacePos);
    }
    
    public void traptarget2(final Entity rt) {
        if (this.autotrap.getValue() && PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.AIR)) {
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.getobby()));
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK) && this.checkatomizer(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK) && this.checkatomizer(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)).getBlock().equals(Blocks.OBSIDIAN) || (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)).getBlock().equals(Blocks.BEDROCK) && this.checkatomizer(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)).getBlock().equals(Blocks.OBSIDIAN) || (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)).getBlock().equals(Blocks.BEDROCK) && this.checkatomizer(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
            }
        }
    }
    
    public void traptarget(final Entity rt) {
        if (this.autotrap.getValue() && PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.AIR)) {
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.getobby()));
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 1.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 1.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.repeattrap(rt);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 1.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 1.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.repeattrap(rt);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ + 1.0)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ + 1.0)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.repeattrap(rt);
                return;
            }
            if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ - 1.0)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ - 1.0)).getBlock().equals(Blocks.BEDROCK)) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.repeattrap(rt);
                return;
            }
            if (this.checkatomizer(new BlockPos(rt.posX + 1.0, rt.posY + 1.0, rt.posZ))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX + 1.0, rt.posY + 1.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.traptarget2(rt);
                return;
            }
            if (this.checkatomizer(new BlockPos(rt.posX - 1.0, rt.posY + 1.0, rt.posZ))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX - 1.0, rt.posY + 1.0, rt.posZ), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.traptarget2(rt);
                return;
            }
            if (this.checkatomizer(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ + 1.0))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ + 1.0), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.traptarget2(rt);
                return;
            }
            if (this.checkatomizer(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ - 1.0))) {
                final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 1.0, rt.posZ - 1.0), true, true);
                InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
                this.traptarget2(rt);
            }
        }
    }
    
    public boolean checkatomizer(final BlockPos bb) {
        return PistonAura.mc.player.getDistance((double)bb.x, (double)bb.y, (double)bb.z) > 2.0;
    }
    
    public void repeattrap(final Entity rt) {
        PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.getobby()));
        if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX + 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK)) {
            final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
            InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
        }
        if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX - 1.0, rt.posY + 2.0, rt.posZ)).getBlock().equals(Blocks.BEDROCK)) {
            final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
            InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
        }
        if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ + 1.0)).getBlock().equals(Blocks.BEDROCK)) {
            final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
            InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
        }
        if (PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)).getBlock().equals(Blocks.OBSIDIAN) || PistonAura.mc.world.getBlockState(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ - 1.0)).getBlock().equals(Blocks.BEDROCK)) {
            final InteractionUtil.Placement aboba = InteractionUtil.preparePlacement(new BlockPos(rt.posX, rt.posY + 2.0, rt.posZ), true, true);
            InteractionUtil.placeBlock(aboba, EnumHand.MAIN_HAND, true);
        }
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos) {
        if (PistonAura.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && PistonAura.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        final BlockPos boost = blockPos.add(0, 1, 0);
        if (PistonAura.mc.world.getBlockState(boost).getBlock() != Blocks.AIR && PistonAura.mc.world.getBlockState(boost).getBlock() != Blocks.PISTON_HEAD) {
            return false;
        }
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return PistonAura.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && PistonAura.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost, boost2.add(1, 1, 1))).isEmpty();
    }
    
    public boolean evaluateTarget(final BlockPos tempFacePos) {
        if (!this.isAir(tempFacePos) && !this.mine.getValue()) {
            return false;
        }
        for (final EnumFacing faceTryOffset : EnumFacing.HORIZONTALS) {
            this.torchPos = null;
            this.skipPiston = false;
            Label_0800: {
                if (canPlaceCrystal(tempFacePos.offset(faceTryOffset).down())) {
                    if (this.getRedstoneBlockSlot() == -1) {
                        return false;
                    }
                    final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(this.getRedstoneBlockSlot());
                    final Block block = ((ItemBlock)stack.getItem()).getBlock();
                    if (block == Blocks.REDSTONE_BLOCK) {
                        if (!this.isAir(tempFacePos.offset(faceTryOffset, 3))) {
                            if (!this.mine.getValue() || (PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() != Blocks.REDSTONE_TORCH && PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() != Blocks.REDSTONE_BLOCK)) {
                                break Label_0800;
                            }
                            this.torchPos = tempFacePos.offset(faceTryOffset, 3);
                        }
                    }
                    else {
                        Optional<BlockUtils.ClickLocation> posCL = BlockUtils.generateClickLocation(tempFacePos.offset(faceTryOffset, 3), false, true);
                        if (!posCL.isPresent() && this.mine.getValue() && (PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() == Blocks.REDSTONE_TORCH || PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 3)).getBlock() == Blocks.REDSTONE_BLOCK)) {
                            this.torchPos = tempFacePos.offset(faceTryOffset, 3);
                        }
                        if (!posCL.isPresent() && this.torchPos == null && ((ItemBlock)PistonAura.mc.player.inventory.getStackInSlot(this.getRedstoneBlockSlot()).getItem()).getBlock() == Blocks.REDSTONE_TORCH) {
                            for (final EnumFacing torchFacing : EnumFacing.HORIZONTALS) {
                                if (!torchFacing.equals((Object)faceTryOffset)) {
                                    if (!torchFacing.equals((Object)faceTryOffset.getOpposite())) {
                                        posCL = BlockUtils.generateClickLocation(tempFacePos.offset(faceTryOffset, 2).offset(torchFacing), false, true);
                                        if (posCL.isPresent()) {
                                            break;
                                        }
                                        if (this.mine.getValue() && PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 2).offset(torchFacing)).getBlock() == Blocks.REDSTONE_TORCH) {
                                            this.torchPos = tempFacePos.offset(faceTryOffset, 2).offset(torchFacing);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (!posCL.isPresent() && this.torchPos == null) {
                            break Label_0800;
                        }
                    }
                    Optional<BlockUtils.ClickLocation> posCL = BlockUtils.generateClickLocation(tempFacePos.offset(faceTryOffset, 2));
                    this.skipPiston = (this.mine.getValue() && PistonAura.mc.world.getBlockState(tempFacePos.offset(faceTryOffset, 2)).getBlock() instanceof BlockPistonBase);
                    if (posCL.isPresent() || this.skipPiston) {
                        if (!this.skipPiston) {
                            final BlockPos currentPos = posCL.get().neighbour;
                            final EnumFacing currentFace = posCL.get().opposite;
                            final double[] yawPitch = BlockUtils.calculateLookAt(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFace, (EntityPlayer)PistonAura.mc.player);
                            final EnumFacing facing = EnumFacing.fromAngle(yawPitch[0]);
                            if (Math.abs(yawPitch[1]) > 55.0) {
                                break Label_0800;
                            }
                            if (facing != faceTryOffset) {
                                break Label_0800;
                            }
                            if (this.raytrace.getValue() && !this.rayTrace(posCL.get().neighbour)) {
                                break Label_0800;
                            }
                            this.pistonNeighbour = currentPos;
                            this.pistonOffset = currentFace;
                        }
                        this.facePos = tempFacePos;
                        this.faceOffset = faceTryOffset;
                        this.crystalPos = tempFacePos.offset(faceTryOffset).down();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean rayTrace(final BlockPos pos) {
        for (double xS = 0.1; xS < 0.9; xS += 0.1) {
            for (double yS = 0.1; yS < 0.9; yS += 0.1) {
                for (double zS = 0.1; zS < 0.9; zS += 0.1) {
                    final Vec3d eyesPos = new Vec3d(PistonAura.mc.player.posX, PistonAura.mc.player.getEntityBoundingBox().minY + PistonAura.mc.player.getEyeHeight(), PistonAura.mc.player.posZ);
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
                    final RayTraceResult rayTraceResult = PistonAura.mc.world.rayTraceBlocks(eyesPos, eyesRotationVec, false, false, false);
                    if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceResult.getBlockPos().equals((Object)pos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean isAir(final BlockPos pos) {
        return PistonAura.mc.world.getBlockState(pos).getBlock() instanceof BlockAir;
    }
    
    private List<EntityPlayer> getTargets() {
        return (List<EntityPlayer>)PistonAura.mc.world.playerEntities.stream().filter(entityPlayer -> !Thunderhack.friendManager.isFriend(entityPlayer.getName())).filter(entityPlayer -> entityPlayer != PistonAura.mc.player).filter(e -> PistonAura.mc.player.getDistance(e) < this.targetRange.getValue()).sorted(Comparator.comparing(e -> PistonAura.mc.player.getDistance(e))).collect(Collectors.toList());
    }
    
    public static int getPistonSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockPistonBase) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private int getRedstoneBlockSlot() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block == Blocks.REDSTONE_BLOCK || block == Blocks.REDSTONE_TORCH) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    private int getobby() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }
    
    static {
        PistonAura.INSTANCE = new PistonAura();
    }
    
    private enum Mode
    {
        DAMAGE, 
        PUSH;
    }
    
    private enum bMode
    {
        Packet, 
        Normal, 
        Sequential;
    }
    
    private enum Stage
    {
        SEARCHING, 
        CRYSTAL, 
        REDSTONE, 
        BREAKING, 
        EXPLOSION;
    }
    
    private enum rMode
    {
        Konas, 
        Silent, 
        Silent2, 
        Client, 
        Oyvey;
    }
}
