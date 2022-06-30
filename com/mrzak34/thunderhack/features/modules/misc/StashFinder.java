//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.world.chunk.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.awt.*;
import net.minecraft.tileentity.*;
import java.io.*;
import java.time.format.*;
import java.time.*;
import java.time.temporal.*;

public class StashFinder extends Module
{
    private Timer timer;
    private HashMap<Chunk, ArrayList<TileEntity>> map;
    private ArrayList<Chunk> loggedChunks;
    public Setting<Integer> amount;
    public Setting<Boolean> windowsAlert;
    public Setting<Boolean> sound;
    public Setting<Boolean> chatMessage;
    public Setting<Boolean> hoppers;
    public Setting<Boolean> shulkers;
    public Setting<Boolean> dispensers;
    public Setting<Boolean> droppers;
    public Setting<Boolean> chests;
    public Setting<Boolean> notif;
    private static final String pathSave = "oyvey/stashes/StashLogger.txt";
    
    public StashFinder() {
        super("StashFinder", "\u0438\u0449\u0435\u0442 \u0441\u0442\u0435\u0448\u0438 \u0432 \u0437\u043e\u043d\u0435-\u043f\u0440\u043e\u0433\u0440\u0443\u0437\u043a\u0438", Category.MISC, true, false, false);
        this.timer = new Timer();
        this.map = new HashMap<Chunk, ArrayList<TileEntity>>();
        this.loggedChunks = new ArrayList<Chunk>();
        this.amount = (Setting<Integer>)this.register(new Setting("Amount", (T)15, (T)1, (T)100));
        this.windowsAlert = (Setting<Boolean>)this.register(new Setting("WindowsAlert", (T)true));
        this.sound = (Setting<Boolean>)this.register(new Setting("Sound", (T)true));
        this.chatMessage = (Setting<Boolean>)this.register(new Setting("chatMessage", (T)true));
        this.hoppers = (Setting<Boolean>)this.register(new Setting("Hoppers", (T)true));
        this.shulkers = (Setting<Boolean>)this.register(new Setting("shulkers", (T)true));
        this.dispensers = (Setting<Boolean>)this.register(new Setting("dispensers", (T)true));
        this.droppers = (Setting<Boolean>)this.register(new Setting("droppers", (T)true));
        this.chests = (Setting<Boolean>)this.register(new Setting("chests", (T)true));
        this.notif = (Setting<Boolean>)this.register(new Setting("notification", (T)true));
    }
    
    @Override
    public void onDisable() {
        this.loggedChunks.clear();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
        if (this.timer.passedMs(500L) && StashFinder.mc.player != null && StashFinder.mc.world != null && StashFinder.mc.world.loadedEntityList != null) {
            this.timer.reset();
            this.map.clear();
            for (final TileEntity tileEntity : StashFinder.mc.world.loadedTileEntityList) {
                if (this.isValid(tileEntity)) {
                    final Chunk chunk = StashFinder.mc.world.getChunk(tileEntity.getPos());
                    ArrayList<TileEntity> list = new ArrayList<TileEntity>();
                    if (this.map.containsKey(chunk)) {
                        list = this.map.get(chunk);
                    }
                    list.add(tileEntity);
                    this.map.put(chunk, list);
                }
            }
            for (final Chunk chunk2 : this.map.keySet()) {
                if (this.map.get(chunk2).size() >= this.amount.getValue() && !this.loggedChunks.contains(chunk2)) {
                    this.loggedChunks.add(chunk2);
                    this.log(chunk2, this.map.get(chunk2));
                }
            }
        }
    }
    
    public void log(final Chunk chunk, final ArrayList<TileEntity> list) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/util/ArrayList.size:()I
        //     4: ifgt            8
        //     7: return         
        //     8: aload_2         /* list */
        //     9: iconst_0       
        //    10: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    13: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //    16: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/math/BlockPos;
        //    19: invokevirtual   net/minecraft/util/math/BlockPos.getX:()I
        //    22: istore_3        /* x */
        //    23: aload_2         /* list */
        //    24: iconst_0       
        //    25: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //    28: checkcast       Lnet/minecraft/tileentity/TileEntity;
        //    31: invokevirtual   net/minecraft/tileentity/TileEntity.getPos:()Lnet/minecraft/util/math/BlockPos;
        //    34: invokevirtual   net/minecraft/util/math/BlockPos.getZ:()I
        //    37: istore          z
        //    39: aload_0         /* this */
        //    40: getfield        com/mrzak34/thunderhack/features/modules/misc/StashFinder.chatMessage:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //    43: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //    46: checkcast       Ljava/lang/Boolean;
        //    49: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    52: ifeq            102
        //    55: new             Ljava/lang/StringBuilder;
        //    58: dup            
        //    59: invokespecial   java/lang/StringBuilder.<init>:()V
        //    62: ldc_w           "\u041d\u0430\u0448\u0435\u043b \u0447\u0430\u043d\u043a \u0441 "
        //    65: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    68: aload_2         /* list */
        //    69: invokevirtual   java/util/ArrayList.size:()I
        //    72: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    75: ldc_w           " \u043d\u0430 X: "
        //    78: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    81: iload_3         /* x */
        //    82: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    85: ldc_w           " Z: "
        //    88: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    91: iload           z
        //    93: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    96: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    99: invokestatic    com/mrzak34/thunderhack/features/command/Command.sendMessage:(Ljava/lang/String;)V
        //   102: aload_0         /* this */
        //   103: getfield        com/mrzak34/thunderhack/features/modules/misc/StashFinder.notif:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   106: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   109: checkcast       Ljava/lang/Boolean;
        //   112: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   115: ifeq            171
        //   118: ldc             "StashFinder"
        //   120: new             Ljava/lang/StringBuilder;
        //   123: dup            
        //   124: invokespecial   java/lang/StringBuilder.<init>:()V
        //   127: ldc_w           "\u041d\u0430\u0448\u0435\u043b \u0447\u0430\u043d\u043a \u0441 "
        //   130: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   133: aload_2         /* list */
        //   134: invokevirtual   java/util/ArrayList.size:()I
        //   137: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   140: ldc_w           " \u043d\u0430 X: "
        //   143: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   146: iload_3         /* x */
        //   147: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   150: ldc_w           " Z: "
        //   153: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   156: iload           z
        //   158: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   161: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   164: iconst_3       
        //   165: getstatic       com/mrzak34/thunderhack/notification/NotificationType.SUCCESS:Lcom/mrzak34/thunderhack/notification/NotificationType;
        //   168: invokestatic    com/mrzak34/thunderhack/notification/NotificationManager.publicity:(Ljava/lang/String;Ljava/lang/String;ILcom/mrzak34/thunderhack/notification/NotificationType;)V
        //   171: aload_0         /* this */
        //   172: getfield        com/mrzak34/thunderhack/features/modules/misc/StashFinder.sound:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   175: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   178: checkcast       Ljava/lang/Boolean;
        //   181: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   184: ifeq            212
        //   187: getstatic       com/mrzak34/thunderhack/features/modules/misc/StashFinder.mc:Lnet/minecraft/client/Minecraft;
        //   190: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //   193: invokestatic    com/mrzak34/thunderhack/util/PlayerUtil.getPlayerPos:()Lnet/minecraft/util/math/BlockPos;
        //   196: getstatic       net/minecraft/init/SoundEvents.ENTITY_PLAYER_LEVELUP:Lnet/minecraft/util/SoundEvent;
        //   199: getstatic       net/minecraft/util/SoundCategory.AMBIENT:Lnet/minecraft/util/SoundCategory;
        //   202: ldc_w           100.0
        //   205: ldc_w           18.0
        //   208: iconst_1       
        //   209: invokevirtual   net/minecraft/client/multiplayer/WorldClient.playSound:(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/SoundEvent;Lnet/minecraft/util/SoundCategory;FFZ)V
        //   212: aload_0         /* this */
        //   213: getfield        com/mrzak34/thunderhack/features/modules/misc/StashFinder.windowsAlert:Lcom/mrzak34/thunderhack/features/setting/Setting;
        //   216: invokevirtual   com/mrzak34/thunderhack/features/setting/Setting.getValue:()Ljava/lang/Object;
        //   219: checkcast       Ljava/lang/Boolean;
        //   222: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   225: ifeq            234
        //   228: ldc_w           "\u041d\u0430\u0448\u0435\u043b \u0441\u0442\u0435\u0448!"
        //   231: invokestatic    com/mrzak34/thunderhack/features/modules/misc/StashFinder.sendWindowsAlert:(Ljava/lang/String;)V
        //   234: new             Ljava/lang/Thread;
        //   237: dup            
        //   238: iload_3         /* x */
        //   239: iload           z
        //   241: aload_2         /* list */
        //   242: invokedynamic   BootstrapMethod #0, run:(IILjava/util/ArrayList;)Ljava/lang/Runnable;
        //   247: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //   250: invokevirtual   java/lang/Thread.start:()V
        //   253: return         
        //    Signature:
        //  (Lnet/minecraft/world/chunk/Chunk;Ljava/util/ArrayList<Lnet/minecraft/tileentity/TileEntity;>;)V
        //    StackMapTable: 00 05 08 FD 00 5D 01 01 FB 00 44 28 15
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
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
    
    public static void sendWindowsAlert(final String message) {
        try {
            final SystemTray tray = SystemTray.getSystemTray();
            final Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            final TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);
            trayIcon.displayMessage("ThunderHack", message, TrayIcon.MessageType.INFO);
        }
        catch (Exception ex) {}
    }
    
    public boolean isValid(final TileEntity tileEntity) {
        return (this.chests.getValue() && tileEntity instanceof TileEntityChest) || (this.droppers.getValue() && tileEntity instanceof TileEntityDropper) || (this.dispensers.getValue() && tileEntity instanceof TileEntityDispenser) || (this.shulkers.getValue() && tileEntity instanceof TileEntityShulkerBox) || (this.hoppers.getValue() && tileEntity instanceof TileEntityDropper);
    }
}
