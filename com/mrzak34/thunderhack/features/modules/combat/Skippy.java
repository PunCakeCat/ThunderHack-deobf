//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import java.io.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import java.util.concurrent.*;
import net.minecraft.entity.player.*;
import javax.sound.sampled.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.math.*;

public class Skippy extends Module
{
    private final File welcmsound;
    private final File arrowsound;
    private final File failsound;
    private final File fail2sound;
    private final File killsound;
    private final File provocationsound;
    private final File provocation2sound;
    private final File provocation3sound;
    private final File provocation4sound;
    private final File provocation5sound;
    private final File randomsound;
    private final File random2sound;
    private final File random3sound;
    private final File random4sound;
    private final File randoms5sound;
    private final File random6sound;
    private final File fail3sound;
    public Setting<Boolean> autoFire;
    public Setting<Integer> time;
    public Setting<Integer> spoof;
    public Setting<Boolean> render;
    private boolean hs;
    int ticks;
    private long lastHsTime;
    long percent;
    private Timer timer;
    private Timer cd;
    private Timer vcd;
    public Setting<Float> volume;
    private static Skippy INSTANCE;
    private ConcurrentHashMap<String, Integer> targetedPlayers;
    private boolean cooldown;
    public Map<EntityPlayer, Integer> targets;
    
    public Skippy() {
        super("Skippy", "\u0421\u043a\u0438\u043f\u043f\u0438 \u0438\u0437 \u043a\u0438\u0431\u0435\u0440\u043f\u0440\u0430\u043d\u043a\u0430", Category.COMBAT, true, false, false);
        this.welcmsound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "welcome.wav");
        this.arrowsound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "arrow.wav");
        this.failsound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "fail.wav");
        this.fail2sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "fail2.wav");
        this.killsound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "kill.wav");
        this.provocationsound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "provocation.wav");
        this.provocation2sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "provocation2.wav");
        this.provocation3sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "provocation3.wav");
        this.provocation4sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "provocation4.wav");
        this.provocation5sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "provocation5.wav");
        this.randomsound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "random.wav");
        this.random2sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "random2.wav");
        this.random3sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "random3.wav");
        this.random4sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "random4.wav");
        this.randoms5sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "random5.wav");
        this.random6sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "random6.wav");
        this.fail3sound = new File(Skippy.mc.gameDir + File.separator + "oyvey" + File.separator + "fake.wav");
        this.autoFire = (Setting<Boolean>)this.register(new Setting("AutoFire", (T)true));
        this.time = (Setting<Integer>)this.register(new Setting("Time", (T)2, (T)1, (T)8));
        this.spoof = (Setting<Integer>)this.register(new Setting("Spoofs", (T)10, (T)1, (T)150));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.timer = new Timer();
        this.cd = new Timer();
        this.vcd = new Timer();
        this.volume = (Setting<Float>)this.register(new Setting("volume", (T)2.5f, (T)0.1f, (T)5.0f));
        this.targetedPlayers = null;
        this.targets = new ConcurrentHashMap<EntityPlayer, Integer>();
    }
    
    private void playSound(final File sound) {
        if (Skippy.mc.player.getHeldItemMainhand().getItem() != Items.BOW) {
            return;
        }
        if (!sound.exists()) {
            return;
        }
        try {
            final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
            final Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            final FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-50.0f + this.volume.getValue() * 10.0f);
            clip.start();
        }
        catch (Exception ex) {}
    }
    
    public static Skippy getINSTANCE() {
        if (Skippy.INSTANCE == null) {
            Skippy.INSTANCE = new Skippy();
        }
        return Skippy.INSTANCE;
    }
    
    private void setInstance() {
        Skippy.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Command.sendMessage("\u0414\u043b\u044f \u044d\u0442\u043e\u0433\u043e \u043c\u043e\u0434\u0443\u043b\u044f \u043d\u0443\u0436\u0435\u043d \u0441\u0430\u0443\u043d\u0434\u043f\u0430\u043a!!!!");
        Command.sendMessage("https://drive.google.com/file/d/1utuiczzXOpELp7KUyiVbUROaUF6O9JU0/view?usp=sharing");
        Command.sendMessage("\u0421\u043a\u0438\u043d\u0443\u0442\u044c \u0432\u0441\u0435 \u0432 \u043f\u0430\u043f\u043a\u0443 .minecraft/ThunderHack");
        this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        this.playSound(this.welcmsound);
        this.timer.reset();
        this.hs = false;
        this.ticks = 0;
        this.lastHsTime = System.currentTimeMillis();
    }
    
    @Override
    public void onDisable() {
        this.playSound(this.failsound);
    }
    
    @Override
    public void onUpdate() {
        if (this.timer.passedMs(120000L)) {
            final int a = 0;
            final int b = 5;
            final int random_number1 = a + (int)(Math.random() * b);
            if (random_number1 == 0) {
                this.playSound(this.random2sound);
            }
            if (random_number1 == 1) {
                this.playSound(this.randomsound);
            }
            if (random_number1 == 2) {
                this.playSound(this.random3sound);
            }
            if (random_number1 == 3) {
                this.playSound(this.random4sound);
            }
            if (random_number1 == 4) {
                this.playSound(this.randoms5sound);
            }
            if (random_number1 == 5) {
                this.playSound(this.random6sound);
            }
            this.timer.reset();
        }
    }
    
    @Override
    public void onTick() {
        if (this.autoFire.getValue() && Skippy.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && Skippy.mc.player.isHandActive() && Skippy.mc.player.getItemInUseMaxCount() >= 4 && this.percent >= 100L) {
            ++this.ticks;
            if (this.ticks >= 14) {
                Skippy.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Skippy.mc.player.getHorizontalFacing()));
                Skippy.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                Skippy.mc.player.stopActiveHand();
                this.ticks = 0;
            }
        }
        this.percent = Math.min((System.currentTimeMillis() - this.lastHsTime) / (this.time.getValue() * 1000L) * 100L, 100L);
    }
    
    public void announceDeath(final EntityPlayer target) {
        this.playSound(this.killsound);
    }
    
    @SubscribeEvent
    public void onEntityDeath(final DeathEvent event) {
        if (this.targets.containsKey(event.player) && !this.cooldown) {
            this.announceDeath(event.player);
            this.cooldown = true;
            this.targets.remove(event.player);
        }
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityPlayer && !Thunderhack.friendManager.isFriend(event.getEntityPlayer())) {
            this.targets.put((EntityPlayer)event.getTarget(), 0);
        }
    }
    
    @SubscribeEvent
    public void onSendAttackPacket(final PacketEvent.Send event) {
        final CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)Skippy.mc.world) instanceof EntityPlayer && !Thunderhack.friendManager.isFriend(Objects.requireNonNull((EntityPlayer)packet.getEntityFromWorld((World)Skippy.mc.world)))) {
            this.targets.put((EntityPlayer)packet.getEntityFromWorld((World)Skippy.mc.world), 0);
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        final SPacketEntityVelocity velocity;
        if (event.getPacket() instanceof SPacketEntityVelocity && (velocity = (SPacketEntityVelocity)event.getPacket()).getEntityID() == Velocity.mc.player.entityId && this.vcd.passedMs(20000L)) {
            final int a = 0;
            final int b = 4;
            final int random_number1 = a + (int)(Math.random() * b);
            if (random_number1 == 0) {
                this.playSound(this.provocation2sound);
            }
            if (random_number1 == 1) {
                this.playSound(this.provocation3sound);
            }
            if (random_number1 == 2) {
                this.playSound(this.provocation4sound);
            }
            if (random_number1 == 3) {
                this.playSound(this.provocation5sound);
            }
            if (random_number1 == 4) {
                this.playSound(this.provocationsound);
            }
            this.vcd.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerDigging) {
            final CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
            if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                final ItemStack handStack = Skippy.mc.player.getHeldItem(EnumHand.MAIN_HAND);
                if (!handStack.isEmpty() && handStack.getItem() != null && handStack.getItem() instanceof ItemBow) {
                    final EntityPlayer rayTracedEntity = this.getEntityUnderMouse(250);
                    if (rayTracedEntity != null && this.cd.passedMs(5000L)) {
                        this.playSound(this.killsound);
                        this.cd.reset();
                    }
                    else {
                        final int a = 0;
                        final int b = 1;
                        final int random_number1 = a + (int)(Math.random() * b);
                        if (random_number1 == 0) {
                            this.playSound(this.fail3sound);
                        }
                        if (random_number1 == 1) {
                            this.playSound(this.fail2sound);
                        }
                        if (System.currentTimeMillis() - this.lastHsTime >= this.time.getValue() * 1000) {
                            this.hs = true;
                            this.lastHsTime = System.currentTimeMillis();
                            Skippy.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Skippy.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                            for (int i = 0; i < this.spoof.getValue(); ++i) {
                                Skippy.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Skippy.mc.player.posX, Skippy.mc.player.posY + 1.0E-10, Skippy.mc.player.posZ, false));
                                Skippy.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Skippy.mc.player.posX, Skippy.mc.player.posY - 1.0E-10, Skippy.mc.player.posZ, true));
                            }
                            this.hs = false;
                        }
                    }
                }
            }
        }
    }
    
    public EntityPlayer getEntityUnderMouse(final int range) {
        final Entity entity = Skippy.mc.getRenderViewEntity();
        if (entity != null) {
            Vec3d pos = Skippy.mc.player.getPositionEyes(1.0f);
            for (float i = 0.0f; i < range; i += 0.5f) {
                pos = pos.add(Skippy.mc.player.getLookVec().scale(0.5));
                if (Skippy.mc.world.getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock() != Blocks.AIR) {
                    return null;
                }
                for (final EntityPlayer player : Skippy.mc.world.playerEntities) {
                    if (player == Skippy.mc.player) {
                        continue;
                    }
                    AxisAlignedBB bb = player.getEntityBoundingBox();
                    if (bb == null) {
                        continue;
                    }
                    if (player.getDistance((Entity)Skippy.mc.player) > 6.0f) {
                        bb = bb.grow(0.5);
                    }
                    if (bb.contains(pos)) {
                        return player;
                    }
                }
            }
        }
        return null;
    }
    
    static {
        Skippy.INSTANCE = new Skippy();
    }
}
