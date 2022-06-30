//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.movement;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.network.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import java.util.concurrent.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.event.events.*;

public class PacketFly extends Module
{
    private Setting<Type> type;
    private Setting<Mode> packetMode;
    private Setting<Phase> phase;
    private Setting<AntiKick> antiKickMode;
    private Setting<Limit> limit;
    public Setting<SubBind> facrotize;
    public Setting<Float> motion;
    public Setting<Float> speed;
    public Setting<Float> factor;
    public Setting<Boolean> boost;
    public Setting<Boolean> jitter;
    public Setting<Boolean> constrict;
    public Setting<Boolean> noPhaseSlow;
    public Setting<Boolean> multiAxis;
    public Setting<Boolean> bounds;
    public Setting<Boolean> strict;
    private int teleportId;
    private CPacketPlayer.Position startingOutOfBoundsPos;
    private ArrayList<CPacketPlayer> packets;
    private Map<Integer, TimeVec3d> posLooks;
    private int antiKickTicks;
    private int vDelay;
    private int hDelay;
    private boolean limitStrict;
    private int limitTicks;
    private int jitterTicks;
    private boolean oddJitter;
    double speedX;
    double speedY;
    double speedZ;
    private float postYaw;
    private float postPitch;
    private int factorCounter;
    private Timer intervalTimer;
    private static final Random random;
    
    public PacketFly() {
        super("PacketFly", "\u043b\u0435\u0442\u0430\u0442\u044c \u043d\u0430 \u043f\u0430\u043a\u0435\u0442\u0430\u0445-\u0438\u0437 \u043f\u044f\u0442\u0435\u0440\u043e\u0447\u043a\u0438", Module.Category.MOVEMENT, true, false, false);
        this.type = (Setting<Type>)this.register(new Setting("Type", (T)Type.FAST));
        this.packetMode = (Setting<Mode>)this.register(new Setting("PacketMode", (T)Mode.UP));
        this.phase = (Setting<Phase>)this.register(new Setting("Phase", (T)Phase.NCP));
        this.antiKickMode = (Setting<AntiKick>)this.register(new Setting("AntiKick", (T)AntiKick.NORMAL));
        this.limit = (Setting<Limit>)this.register(new Setting("Limit", (T)Limit.NONE));
        this.facrotize = (Setting<SubBind>)this.register(new Setting("Snap", (T)new SubBind(0), v -> this.type.getValue() == Type.FACTOR));
        this.motion = (Setting<Float>)this.register(new Setting("Distance", (T)5.0f, (T)1.0f, (T)20.0f, v -> this.type.getValue() == Type.FACTOR));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)0.1f, (T)2.0f));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)1.0f, (T)1.0f, (T)10.0f, v -> this.type.getValue() == Type.FACTOR || this.type.getValue() == Type.DESYNC));
        this.boost = (Setting<Boolean>)this.register(new Setting("Boost", (T)false));
        this.jitter = (Setting<Boolean>)this.register(new Setting("Jitter", (T)false));
        this.constrict = (Setting<Boolean>)this.register(new Setting("Constrict", (T)false));
        this.noPhaseSlow = (Setting<Boolean>)this.register(new Setting("NoPhaseSlow", (T)false));
        this.multiAxis = (Setting<Boolean>)this.register(new Setting("MultiAxis", (T)false));
        this.bounds = (Setting<Boolean>)this.register(new Setting("Bounds", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)true));
        this.packets = new ArrayList<CPacketPlayer>();
        this.posLooks = new ConcurrentHashMap<Integer, TimeVec3d>();
        this.antiKickTicks = 0;
        this.vDelay = 0;
        this.hDelay = 0;
        this.limitStrict = false;
        this.limitTicks = 0;
        this.jitterTicks = 0;
        this.oddJitter = false;
        this.speedX = 0.0;
        this.speedY = 0.0;
        this.speedZ = 0.0;
        this.postYaw = -400.0f;
        this.postPitch = -400.0f;
        this.factorCounter = 0;
        this.intervalTimer = new Timer();
    }
    
    public void onUpdate() {
        if (PacketFly.mc.currentScreen instanceof GuiDisconnected || PacketFly.mc.currentScreen instanceof GuiMainMenu || PacketFly.mc.currentScreen instanceof GuiMultiplayer || PacketFly.mc.currentScreen instanceof GuiDownloadTerrain) {
            this.toggle();
        }
        if (this.boost.getValue()) {
            Thunderhack.TICK_TIMER = 1.088f;
        }
        else {
            Thunderhack.TICK_TIMER = 1.0f;
        }
    }
    
    @SubscribeEvent
    public void onPlayerUpdate(final PlayerUpdateEvent event) {
        if (PacketFly.mc.player == null || PacketFly.mc.world == null) {
            this.toggle();
            return;
        }
        if (PacketFly.mc.player.ticksExisted % 20 == 0) {
            this.cleanPosLooks();
        }
        PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
        if (this.teleportId <= 0 && this.type.getValue() != Type.SETBACK) {
            this.startingOutOfBoundsPos = new CPacketPlayer.Position(this.randomHorizontal(), 1.0, this.randomHorizontal(), PacketFly.mc.player.onGround);
            this.packets.add((CPacketPlayer)this.startingOutOfBoundsPos);
            PacketFly.mc.player.connection.sendPacket((Packet)this.startingOutOfBoundsPos);
            return;
        }
        final boolean phasing = this.checkCollisionBox();
        this.speedX = 0.0;
        this.speedY = 0.0;
        this.speedZ = 0.0;
        if (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() && (this.hDelay < 1 || (this.multiAxis.getValue() && phasing))) {
            if (PacketFly.mc.player.ticksExisted % ((this.type.getValue() == Type.SETBACK || this.type.getValue() == Type.SLOW || this.limit.getValue() == Limit.STRICT) ? 10 : 20) == 0) {
                this.speedY = ((this.antiKickMode.getValue() != AntiKick.NONE) ? -0.032 : 0.062);
            }
            else {
                this.speedY = 0.062;
            }
            this.antiKickTicks = 0;
            this.vDelay = 5;
        }
        else if (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() && (this.hDelay < 1 || (this.multiAxis.getValue() && phasing))) {
            this.speedY = -0.062;
            this.antiKickTicks = 0;
            this.vDelay = 5;
        }
        if ((this.multiAxis.getValue() && phasing) || !PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() || !PacketFly.mc.gameSettings.keyBindJump.isKeyDown()) {
            if (PlayerUtils.isPlayerMoving()) {
                final double[] dir = PlayerUtils.directionSpeed(((phasing && this.phase.getValue() == Phase.NCP) ? (this.noPhaseSlow.getValue() ? (this.multiAxis.getValue() ? 0.0465 : 0.062) : 0.031) : 0.26) * this.speed.getValue());
                if ((dir[0] != 0.0 || dir[1] != 0.0) && (this.vDelay < 1 || (this.multiAxis.getValue() && phasing))) {
                    this.speedX = dir[0];
                    this.speedZ = dir[1];
                    this.hDelay = 5;
                }
            }
            if (this.antiKickMode.getValue() != AntiKick.NONE && (this.limit.getValue() == Limit.NONE || this.limitTicks != 0)) {
                if (this.antiKickTicks < ((this.packetMode.getValue() == Mode.BYPASS && !this.bounds.getValue()) ? 1 : 3)) {
                    ++this.antiKickTicks;
                }
                else {
                    this.antiKickTicks = 0;
                    if (this.antiKickMode.getValue() != AntiKick.LIMITED || !phasing) {
                        this.speedY = ((this.antiKickMode.getValue() == AntiKick.STRICT) ? -0.08 : -0.04);
                    }
                }
            }
        }
        if (phasing && ((this.phase.getValue() == Phase.NCP && PacketFly.mc.player.moveForward != 0.0) || (PacketFly.mc.player.moveStrafing != 0.0 && this.speedY != 0.0))) {
            this.speedY /= 2.5;
        }
        if (this.limit.getValue() != Limit.NONE) {
            if (this.limitTicks == 0) {
                this.speedX = 0.0;
                this.speedY = 0.0;
                this.speedZ = 0.0;
            }
            else if (this.limitTicks == 2 && this.jitter.getValue()) {
                if (this.oddJitter) {
                    this.speedX = 0.0;
                    this.speedY = 0.0;
                    this.speedZ = 0.0;
                }
                this.oddJitter = !this.oddJitter;
            }
        }
        else if (this.jitter.getValue() && this.jitterTicks == 7) {
            this.speedX = 0.0;
            this.speedY = 0.0;
            this.speedZ = 0.0;
        }
        switch (this.type.getValue()) {
            case FAST: {
                PacketFly.mc.player.setVelocity(this.speedX, this.speedY, this.speedZ);
                this.sendPackets(this.speedX, this.speedY, this.speedZ, this.packetMode.getValue(), true, false);
                break;
            }
            case SLOW: {
                this.sendPackets(this.speedX, this.speedY, this.speedZ, this.packetMode.getValue(), true, false);
                break;
            }
            case SETBACK: {
                PacketFly.mc.player.setVelocity(this.speedX, this.speedY, this.speedZ);
                this.sendPackets(this.speedX, this.speedY, this.speedZ, this.packetMode.getValue(), false, false);
                break;
            }
            case FACTOR:
            case DESYNC: {
                float rawFactor = this.factor.getValue();
                if (PlayerUtils.isKeyDown(this.facrotize.getValue().getKey()) && this.intervalTimer.passedMs(3500L)) {
                    this.intervalTimer.reset();
                    rawFactor = this.motion.getValue();
                }
                int factorInt = (int)Math.floor(rawFactor);
                ++this.factorCounter;
                if (this.factorCounter > (int)(20.0 / ((rawFactor - (double)factorInt) * 20.0))) {
                    ++factorInt;
                    this.factorCounter = 0;
                }
                for (int i = 1; i <= factorInt; ++i) {
                    PacketFly.mc.player.setVelocity(this.speedX * i, this.speedY * i, this.speedZ * i);
                    this.sendPackets(this.speedX * i, this.speedY * i, this.speedZ * i, this.packetMode.getValue(), true, false);
                }
                this.speedX = PacketFly.mc.player.motionX;
                this.speedY = PacketFly.mc.player.motionY;
                this.speedZ = PacketFly.mc.player.motionZ;
                break;
            }
        }
        --this.vDelay;
        --this.hDelay;
        if (this.constrict.getValue() && (this.limit.getValue() == Limit.NONE || this.limitTicks > 1)) {
            PacketFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PacketFly.mc.player.posX, PacketFly.mc.player.posY, PacketFly.mc.player.posZ, false));
        }
        ++this.limitTicks;
        ++this.jitterTicks;
        if (this.limitTicks > ((this.limit.getValue() == Limit.STRICT) ? (this.limitStrict ? 1 : 2) : 3)) {
            this.limitTicks = 0;
            this.limitStrict = !this.limitStrict;
        }
        if (this.jitterTicks > 7) {
            this.jitterTicks = 0;
        }
    }
    
    private void sendPackets(final double x, final double y, final double z, final Mode mode, final boolean sendConfirmTeleport, final boolean sendExtraCT) {
        final Vec3d nextPos = new Vec3d(PacketFly.mc.player.posX + x, PacketFly.mc.player.posY + y, PacketFly.mc.player.posZ + z);
        final Vec3d bounds = this.getBoundsVec(x, y, z, mode);
        final CPacketPlayer nextPosPacket = (CPacketPlayer)new CPacketPlayer.Position(nextPos.x, nextPos.y, nextPos.z, PacketFly.mc.player.onGround);
        this.packets.add(nextPosPacket);
        PacketFly.mc.player.connection.sendPacket((Packet)nextPosPacket);
        if (this.limit.getValue() != Limit.NONE && this.limitTicks == 0) {
            return;
        }
        final CPacketPlayer boundsPacket = (CPacketPlayer)new CPacketPlayer.Position(bounds.x, bounds.y, bounds.z, PacketFly.mc.player.onGround);
        this.packets.add(boundsPacket);
        PacketFly.mc.player.connection.sendPacket((Packet)boundsPacket);
        if (sendConfirmTeleport) {
            ++this.teleportId;
            if (sendExtraCT) {
                PacketFly.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId - 1));
            }
            PacketFly.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId));
            this.posLooks.put(this.teleportId, new TimeVec3d(nextPos.x, nextPos.y, nextPos.z, System.currentTimeMillis()));
            if (sendExtraCT) {
                PacketFly.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportId + 1));
            }
        }
    }
    
    private Vec3d getBoundsVec(final double x, final double y, final double z, final Mode mode) {
        switch (mode) {
            case UP: {
                return new Vec3d(PacketFly.mc.player.posX + x, ((boolean)this.bounds.getValue()) ? ((double)(this.strict.getValue() ? 255 : 256)) : (PacketFly.mc.player.posY + 420.0), PacketFly.mc.player.posZ + z);
            }
            case PRESERVE: {
                return new Vec3d(((boolean)this.bounds.getValue()) ? (PacketFly.mc.player.posX + this.randomHorizontal()) : this.randomHorizontal(), ((boolean)this.strict.getValue()) ? Math.max(PacketFly.mc.player.posY, 2.0) : PacketFly.mc.player.posY, ((boolean)this.bounds.getValue()) ? (PacketFly.mc.player.posZ + this.randomHorizontal()) : this.randomHorizontal());
            }
            case LIMITJITTER: {
                return new Vec3d(PacketFly.mc.player.posX + (this.strict.getValue() ? x : randomLimitedHorizontal()), PacketFly.mc.player.posY + randomLimitedVertical(), PacketFly.mc.player.posZ + (this.strict.getValue() ? z : randomLimitedHorizontal()));
            }
            case BYPASS: {
                if (this.bounds.getValue()) {
                    final double rawY = y * 510.0;
                    return new Vec3d(PacketFly.mc.player.posX + x, PacketFly.mc.player.posY + ((rawY > ((PacketFly.mc.player.dimension == -1) ? 127 : 255)) ? (-rawY) : ((rawY < 1.0) ? (-rawY) : rawY)), PacketFly.mc.player.posZ + z);
                }
                return new Vec3d(PacketFly.mc.player.posX + ((x == 0.0) ? (PacketFly.random.nextBoolean() ? -10 : 10) : (x * 38.0)), PacketFly.mc.player.posY + y, PacketFly.mc.player.posX + ((z == 0.0) ? (PacketFly.random.nextBoolean() ? -10 : 10) : (z * 38.0)));
            }
            case OBSCURE: {
                return new Vec3d(PacketFly.mc.player.posX + this.randomHorizontal(), Math.max(1.5, Math.min(PacketFly.mc.player.posY + y, 253.5)), PacketFly.mc.player.posZ + this.randomHorizontal());
            }
            default: {
                return new Vec3d(PacketFly.mc.player.posX + x, ((boolean)this.bounds.getValue()) ? ((double)(((boolean)this.strict.getValue()) ? 1 : 0)) : (PacketFly.mc.player.posY - 1337.0), PacketFly.mc.player.posZ + z);
            }
        }
    }
    
    public double randomHorizontal() {
        final int randomValue = PacketFly.random.nextInt(((boolean)this.bounds.getValue()) ? 80 : ((this.packetMode.getValue() == Mode.OBSCURE) ? ((PacketFly.mc.player.ticksExisted % 2 == 0) ? 480 : 100) : 29000000)) + (this.bounds.getValue() ? 5 : 500);
        if (PacketFly.random.nextBoolean()) {
            return randomValue;
        }
        return -randomValue;
    }
    
    public static double randomLimitedVertical() {
        int randomValue = PacketFly.random.nextInt(22);
        randomValue += 70;
        if (PacketFly.random.nextBoolean()) {
            return randomValue;
        }
        return -randomValue;
    }
    
    public static double randomLimitedHorizontal() {
        final int randomValue = PacketFly.random.nextInt(10);
        if (PacketFly.random.nextBoolean()) {
            return randomValue;
        }
        return -randomValue;
    }
    
    private void cleanPosLooks() {
        this.posLooks.forEach((tp, timeVec3d) -> {
            if (System.currentTimeMillis() - timeVec3d.getTime() > TimeUnit.SECONDS.toMillis(30L)) {
                this.posLooks.remove(tp);
            }
        });
    }
    
    public void onEnable() {
        if (PacketFly.mc.player == null || PacketFly.mc.world == null) {
            this.toggle();
            return;
        }
        this.packets.clear();
        this.posLooks.clear();
        this.teleportId = 0;
        this.vDelay = 0;
        this.hDelay = 0;
        this.postYaw = -400.0f;
        this.postPitch = -400.0f;
        this.antiKickTicks = 0;
        this.limitTicks = 0;
        this.jitterTicks = 0;
        this.speedX = 0.0;
        this.speedY = 0.0;
        this.speedZ = 0.0;
        this.oddJitter = false;
        this.startingOutOfBoundsPos = null;
        this.startingOutOfBoundsPos = new CPacketPlayer.Position(this.randomHorizontal(), 1.0, this.randomHorizontal(), PacketFly.mc.player.onGround);
        this.packets.add((CPacketPlayer)this.startingOutOfBoundsPos);
        PacketFly.mc.player.connection.sendPacket((Packet)this.startingOutOfBoundsPos);
    }
    
    public void onDisable() {
        if (PacketFly.mc.player != null) {
            PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
        Thunderhack.TICK_TIMER = 1.0f;
    }
    
    @SubscribeEvent
    public void onReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            if (!(PacketFly.mc.currentScreen instanceof GuiDownloadTerrain)) {
                final SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
                if (PacketFly.mc.player.isEntityAlive()) {
                    if (this.teleportId <= 0) {
                        this.teleportId = ((SPacketPlayerPosLook)event.getPacket()).getTeleportId();
                    }
                    else if (PacketFly.mc.world.isBlockLoaded(new BlockPos(PacketFly.mc.player.posX, PacketFly.mc.player.posY, PacketFly.mc.player.posZ), false) && this.type.getValue() != Type.SETBACK) {
                        if (this.type.getValue() == Type.DESYNC) {
                            this.posLooks.remove(packet.getTeleportId());
                            event.setCanceled(true);
                            if (this.type.getValue() == Type.SLOW) {
                                PacketFly.mc.player.setPosition(packet.getX(), packet.getY(), packet.getZ());
                            }
                            return;
                        }
                        if (this.posLooks.containsKey(packet.getTeleportId())) {
                            final TimeVec3d vec = this.posLooks.get(packet.getTeleportId());
                            if (vec.x == packet.getX() && vec.y == packet.getY() && vec.z == packet.getZ()) {
                                this.posLooks.remove(packet.getTeleportId());
                                event.setCanceled(true);
                                if (this.type.getValue() == Type.SLOW) {
                                    PacketFly.mc.player.setPosition(packet.getX(), packet.getY(), packet.getZ());
                                }
                                return;
                            }
                        }
                    }
                }
                ((ISPacketPlayerPosLook)packet).setYaw(PacketFly.mc.player.rotationYaw);
                ((ISPacketPlayerPosLook)packet).setPitch(PacketFly.mc.player.rotationPitch);
                packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
                packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
                this.teleportId = packet.getTeleportId();
            }
            else {
                this.teleportId = 0;
            }
        }
    }
    
    @SubscribeEvent
    public void onMove(final EventMove event) {
        if (this.type.getValue() != Type.SETBACK && this.teleportId <= 0) {
            return;
        }
        if (this.type.getValue() != Type.SLOW) {
            event.set_x(this.speedX);
            event.set_y(this.speedY);
            event.set_z(this.speedZ);
        }
        if ((this.phase.getValue() != Phase.NONE && this.phase.getValue() == Phase.VANILLA) || this.checkCollisionBox()) {
            PacketFly.mc.player.noClip = true;
        }
    }
    
    private boolean checkCollisionBox() {
        return !PacketFly.mc.world.getCollisionBoxes((Entity)PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().expand(0.0, 0.0, 0.0)).isEmpty() || !PacketFly.mc.world.getCollisionBoxes((Entity)PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().offset(0.0, 2.0, 0.0).contract(0.0, 1.99, 0.0)).isEmpty();
    }
    
    @SubscribeEvent
    public void onSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Position)) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            if (this.packets.contains(packet)) {
                this.packets.remove(packet);
                return;
            }
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPush(final PushEvent event) {
        event.setCanceled(true);
    }
    
    static {
        random = new Random();
    }
    
    public enum Limit
    {
        NONE, 
        STRONG, 
        STRICT;
    }
    
    public enum Mode
    {
        UP, 
        PRESERVE, 
        DOWN, 
        LIMITJITTER, 
        BYPASS, 
        OBSCURE;
    }
    
    public enum Type
    {
        FACTOR, 
        SETBACK, 
        FAST, 
        SLOW, 
        DESYNC;
    }
    
    public enum Phase
    {
        NONE, 
        VANILLA, 
        NCP;
    }
    
    private enum AntiKick
    {
        NONE, 
        NORMAL, 
        LIMITED, 
        STRICT;
    }
}
