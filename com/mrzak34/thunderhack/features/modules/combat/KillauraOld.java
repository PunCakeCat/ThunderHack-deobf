//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.combat;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.inventory.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.features.modules.misc.*;
import java.awt.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.client.network.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import net.minecraft.client.gui.*;

public class KillauraOld extends Module
{
    public static EntityPlayer target;
    public boolean killauraen;
    private final Timer timer;
    public boolean rotate;
    int easingHealth;
    private static KillauraOld INSTANCE;
    private final ResourceLocation logo;
    public Setting<Float> range;
    public Setting<Boolean> delay;
    public Setting<Boolean> onlySharp;
    public Setting<Float> raytrace;
    public Setting<Boolean> tps;
    public Setting<Boolean> packet;
    public Setting<Boolean> onlycrits;
    public Setting<Float> critfall;
    public Setting<SubBind> targetlock;
    public Setting<SubBind> abbind;
    public Setting<Float> fov;
    private Setting<sortEn> sort;
    public static ArrayList<EntityPlayer> targets;
    public Setting<Boolean> shieldsfucker;
    private Setting<rmode> rMode;
    private final Setting<Float> aboba;
    private final Setting<Float> aboba2;
    public Setting<Boolean> chance;
    public Setting<Integer> chanceval;
    public Setting<Boolean> owncircle;
    public Setting<Boolean> armorBreaker;
    public Setting<Boolean> swing;
    public Setting<Boolean> circle;
    public Setting<Boolean> timrreset;
    public Setting<Integer> owncirclepoints;
    public Setting<Integer> circlepoints;
    public Setting<Float> owncirclerange;
    public Timer locktimer;
    boolean looked;
    
    public KillauraOld() {
        super("Killaura", "\u041d\u0443 \u043a\u0438\u043b\u043b\u0430\u0443\u0440\u0430 \u0442\u0438\u043f\u0430", Category.COMBAT, true, false, false);
        this.killauraen = false;
        this.timer = new Timer();
        this.rotate = true;
        this.easingHealth = 0;
        this.logo = new ResourceLocation("textures/logo.png");
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)3.8f, (T)0.1f, (T)7.0f));
        this.delay = (Setting<Boolean>)this.register(new Setting("1.9+", (T)true));
        this.onlySharp = (Setting<Boolean>)this.register(new Setting("SwordOnly", (T)true));
        this.raytrace = (Setting<Float>)this.register(new Setting("WallRange", (T)6.0f, (T)0.1f, (T)7.0f, "Wall Range."));
        this.tps = (Setting<Boolean>)this.register(new Setting("TpsSync", (T)true));
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketAttack", (T)false));
        this.onlycrits = (Setting<Boolean>)this.register(new Setting("OnlyCrits", (T)false));
        this.critfall = (Setting<Float>)this.register(new Setting("FallDist", (T)0.4f, (T)0.1f, (T)1.0f, v -> this.onlycrits.getValue()));
        this.targetlock = (Setting<SubBind>)this.register(new Setting("LockTarget", (T)new SubBind(38)));
        this.abbind = (Setting<SubBind>)this.register(new Setting("ArmorBreak", (T)new SubBind(37)));
        this.fov = (Setting<Float>)this.register(new Setting("FOV", (T)360.0f, (T)5.0f, (T)360.0f));
        this.sort = (Setting<sortEn>)this.register(new Setting("SortMode", (T)sortEn.Distance));
        this.shieldsfucker = (Setting<Boolean>)this.register(new Setting("FuckShields", (T)false));
        this.rMode = (Setting<rmode>)this.register(new Setting("Rotation Mode", (T)rmode.FunnyGame));
        this.aboba = (Setting<Float>)this.register(new Setting("HudX", (T)400.0f, (T)0.0f, (T)2048.0f));
        this.aboba2 = (Setting<Float>)this.register(new Setting("HudY", (T)400.0f, (T)0.0f, (T)2048.0f));
        this.chance = (Setting<Boolean>)this.register(new Setting("RandomChance", (T)false));
        this.chanceval = (Setting<Integer>)this.register(new Setting("Chance", (T)100, (T)1, (T)100, v -> this.chance.getValue()));
        this.owncircle = (Setting<Boolean>)this.register(new Setting("Own Cirlce", (T)true));
        this.armorBreaker = (Setting<Boolean>)this.register(new Setting("ArmorBreaker", (T)false));
        this.swing = (Setting<Boolean>)this.register(new Setting("Swing", (T)true));
        this.circle = (Setting<Boolean>)this.register(new Setting("Range Circle", (T)true));
        this.timrreset = (Setting<Boolean>)this.register(new Setting("ABTimerReset", (T)false));
        this.owncirclepoints = (Setting<Integer>)this.register(new Setting("OwnCirclePoints", (T)16, (T)1, (T)64, v -> this.owncircle.getValue()));
        this.circlepoints = (Setting<Integer>)this.register(new Setting("CirclePoints", (T)16, (T)1, (T)64, v -> this.circle.getValue()));
        this.owncirclerange = (Setting<Float>)this.register(new Setting("OwnCirlceRange", (T)1.8f, (T)0.1f, (T)7.0f));
        this.locktimer = new Timer();
        this.looked = false;
        this.setInstance();
    }
    
    public static KillauraOld getInstance() {
        if (KillauraOld.INSTANCE == null) {
            KillauraOld.INSTANCE = new KillauraOld();
        }
        return KillauraOld.INSTANCE;
    }
    
    private void setInstance() {
        KillauraOld.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        this.doKillaura();
    }
    
    @Override
    public void onEnable() {
        this.killauraen = true;
    }
    
    @Override
    public void onDisable() {
        this.killauraen = false;
        KillauraOld.target = null;
    }
    
    public void setYaw(final float yawi) {
        Minecraft.getMinecraft();
        Util.mc.player.rotationYawHead = yawi;
        Minecraft.getMinecraft();
        Util.mc.player.renderYawOffset = yawi;
        Util.mc.player.rotationYawHead = yawi;
    }
    
    @Override
    public void onUpdate() {
        if (KillauraOld.mc.currentScreen instanceof GuiGameOver && KillauraOld.mc.player.isDead) {
            NotificationManager.publicity("KillAura", "\u041b\u043e\u0448\u0430\u0440\u0430, \u043a\u0430\u043a \u0442\u044b \u0441\u0434\u043e\u0445?", 2, NotificationType.INFO);
            this.toggle();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && KillauraOld.target != null) {
            if (this.rMode.getValue() == rmode.FunnyGame) {
                lookAtEntity((Entity)KillauraOld.target);
            }
            this.doKillaura();
        }
    }
    
    @SubscribeEvent
    public void onUpdateMotionEvent(final MotionUpdateEvent event) {
        final float cdValue = this.onlycrits.getValue() ? 0.95f : 1.0f;
        if (this.rMode.getValue() == rmode.Client && KillauraOld.target != null) {
            final float[] rots = getMatrixRotations((Entity)KillauraOld.target, false);
            event.rotationYaw = rots[0];
            event.rotationPitch = rots[1];
            Util.mc.player.rotationYaw = rots[0];
            Util.mc.player.rotationPitch = rots[1];
        }
        if (this.rMode.getValue() == rmode.Wint && Util.mc.player.getCooledAttackStrength(0.0f) > cdValue && getRandomDouble(0.0, 100.0) <= this.chanceval.getValue() && KillauraOld.target != null) {
            final float[] rots = getMatrixRotations((Entity)KillauraOld.target, false);
            event.rotationYaw = rots[0];
            event.rotationPitch = rots[1];
            Util.mc.player.rotationYaw = rots[0];
            Util.mc.player.rotationPitch = rots[1];
        }
    }
    
    public static double getRandomDouble(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1.0);
    }
    
    public static float[] getMatrixRotations(final Entity e, final boolean oldPositionUse) {
        final double diffX = (oldPositionUse ? e.prevPosX : e.posX) - (oldPositionUse ? Util.mc.player.prevPosX : Util.mc.player.posX);
        final double diffZ = (oldPositionUse ? e.prevPosZ : e.posZ) - (oldPositionUse ? Util.mc.player.prevPosZ : Util.mc.player.posZ);
        double diffY;
        if (e instanceof EntityPlayer) {
            final EntityPlayer EntityPlayer = (EntityPlayer)e;
            final float randomed = nextFloat((float)(EntityPlayer.posY + EntityPlayer.getEyeHeight() / 1.5f), (float)(EntityPlayer.posY + EntityPlayer.getEyeHeight() - EntityPlayer.getEyeHeight() / 3.0f));
            diffY = randomed - (Util.mc.player.posY + Util.mc.player.getEyeHeight());
        }
        else {
            diffY = nextFloat((float)e.getEntityBoundingBox().minY, (float)e.getEntityBoundingBox().maxY) - (Util.mc.player.posY + Util.mc.player.getEyeHeight());
        }
        final double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793 - 90.0) + nextFloat(-2.0f, 2.0f);
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793)) + nextFloat(-2.0f, 2.0f);
        yaw = Util.mc.player.rotationYaw + getFixedRotation(MathHelper.wrapDegrees(yaw - Util.mc.player.rotationYaw));
        pitch = Util.mc.player.rotationPitch + getFixedRotation(MathHelper.wrapDegrees(pitch - Util.mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        return new float[] { yaw, pitch };
    }
    
    public static float getFixedRotation(final float rot) {
        return getDeltaMouse(rot) * getGCDValue();
    }
    
    public static float getGCDValue() {
        return (float)(getGCD() * 0.15);
    }
    
    public static float getGCD() {
        final float f1 = (float)(KillauraOld.mc.gameSettings.mouseSensitivity * 0.6 + 0.2);
        return f1 * f1 * f1 * 8.0f;
    }
    
    public static float getDeltaMouse(final float delta) {
        return (float)Math.round(delta / getGCDValue());
    }
    
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f) {
            return startInclusive;
        }
        return (float)(startInclusive + (endInclusive - startInclusive) * Math.random());
    }
    
    public static void lookAtEntity(final Entity entity) {
        final float[] angle = calcAngle(KillauraOld.mc.player.getPositionEyes(KillauraOld.mc.getRenderPartialTicks()), entity.getPositionEyes(KillauraOld.mc.getRenderPartialTicks()));
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.renderYawOffset = angle[0];
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        KillauraOld.mc.player.rotationYaw = yaw;
        KillauraOld.mc.player.rotationYawHead = yaw;
        KillauraOld.mc.player.rotationPitch = pitch;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    private void doKillaura() {
        if (this.shieldsfucker.getValue()) {
            if (KillauraOld.target == null) {
                KillauraOld.target = this.getTarget();
                return;
            }
            if (KillauraOld.target.isActiveItemStackBlocking() && KillauraOld.target.getHeldItemOffhand().getItem() instanceof ItemShield) {
                if (InventoryUtil.getAxeAtHotbar() != -1) {
                    KillauraOld.mc.player.inventory.currentItem = InventoryUtil.getAxeAtHotbar();
                    EntityUtil.attackEntity((Entity)KillauraOld.target, true, true);
                }
            }
            else if (InventoryUtil.getSwordAtHotbar() != -1) {
                KillauraOld.mc.player.inventory.currentItem = InventoryUtil.getSwordAtHotbar();
            }
        }
        if (this.onlySharp.getValue() && !EntityUtil.holdingWeapon((EntityPlayer)KillauraOld.mc.player)) {
            KillauraOld.target = null;
            return;
        }
        final int wait = this.delay.getValue() ? ((int)(DamageUtil.getCooldownByWeapon((EntityPlayer)KillauraOld.mc.player) * (this.tps.getValue() ? Thunderhack.serverManager.getTpsFactor() : 1.0f))) : 0;
        if (!this.timer.passedMs(wait)) {
            return;
        }
        KillauraOld.target = this.getTarget();
        if (KillauraOld.target == null) {
            return;
        }
        if (this.armorBreaker.getValue() && PlayerUtils.isKeyDown(this.abbind.getValue().getKey())) {
            KillauraOld.mc.playerController.windowClick(KillauraOld.mc.player.inventoryContainer.windowId, 9, KillauraOld.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)KillauraOld.mc.player);
            EntityUtil.attackEntity((Entity)KillauraOld.target, this.packet.getValue(), this.swing.getValue());
            KillauraOld.mc.playerController.windowClick(KillauraOld.mc.player.inventoryContainer.windowId, 9, KillauraOld.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)KillauraOld.mc.player);
            EntityUtil.attackEntity((Entity)KillauraOld.target, this.packet.getValue(), this.swing.getValue());
            if (this.timrreset.getValue()) {
                this.timer.reset();
            }
        }
        if (Util.mc.player.fallDistance > this.critfall.getValue() && this.onlycrits.getValue() && !isBlockAboveHead()) {
            if (Util.mc.player.isInWater() && Util.mc.player.isInLava()) {
                return;
            }
            if (this.rMode.getValue() == rmode.Wint) {
                EntityUtil.attackEntity((Entity)KillauraOld.target, false, true);
                this.timer.reset();
            }
            else {
                EntityUtil.attackEntity((Entity)KillauraOld.target, this.packet.getValue(), true);
                this.timer.reset();
            }
        }
        if (!this.onlycrits.getValue() || isBlockAboveHead()) {
            EntityUtil.attackEntity((Entity)KillauraOld.target, this.packet.getValue(), true);
            this.timer.reset();
        }
    }
    
    public static boolean isBlockAboveHead() {
        final AxisAlignedBB bb = new AxisAlignedBB(KillauraOld.mc.player.posX - 0.3, KillauraOld.mc.player.posY + KillauraOld.mc.player.getEyeHeight(), KillauraOld.mc.player.posZ + 0.3, KillauraOld.mc.player.posX + 0.3, KillauraOld.mc.player.posY + 2.5, KillauraOld.mc.player.posZ - 0.3);
        return !MovementUtil.mc.world.getCollisionBoxes((Entity)KillauraOld.mc.player, bb).isEmpty();
    }
    
    private EntityPlayer getTarget() {
        if (KillauraOld.target != null) {
            if (PlayerUtils.isKeyDown(this.targetlock.getValue().getKey()) && !this.looked && this.locktimer.passedMs(1000L)) {
                this.looked = true;
                if (Thunderhack.moduleManager.getModuleByClass(NotificationManager.class).isEnabled()) {
                    NotificationManager.publicity("KillAura", "\u0426\u0435\u043b\u044c " + KillauraOld.target.getName() + " \u0437\u0430\u0445\u0432\u0430\u0447\u0435\u043d\u0430!", 2, NotificationType.SUCCESS);
                }
                else {
                    Command.sendMessage("\u0426\u0435\u043b\u044c " + KillauraOld.target.getName() + " \u0437\u0430\u0445\u0432\u0430\u0447\u0435\u043d\u0430!");
                }
                this.locktimer.reset();
            }
            if (PlayerUtils.isKeyDown(this.targetlock.getValue().getKey()) && this.looked && this.locktimer.passedMs(1000L)) {
                this.looked = false;
                this.locktimer.reset();
                if (Thunderhack.moduleManager.getModuleByClass(NotificationManager.class).isEnabled()) {
                    NotificationManager.publicity("KillAura", "\u0415\u0441\u0442\u044c \u043e\u0442\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u0446\u0435\u043b\u044c!", 2, NotificationType.SUCCESS);
                }
                else {
                    Command.sendMessage("\u0415\u0441\u0442\u044c \u043e\u0442\u0441\u0442\u0430\u0432\u0438\u0442\u044c \u0446\u0435\u043b\u044c!");
                }
            }
            if (this.looked) {
                return KillauraOld.target;
            }
        }
        if (this.sort.getValue() == sortEn.Smart) {
            Entity target = null;
            double distance = this.range.getValue();
            double maxHealth = 36.0;
            for (final Entity entity : KillauraOld.mc.world.playerEntities) {
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (EntityUtil.isntValid(entity, distance)) {
                    continue;
                }
                if (!KillauraOld.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && KillauraOld.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace.getValue())) {
                    continue;
                }
                if (AntiBot.getBots().contains(entity)) {
                    continue;
                }
                if (((EntityPlayer)entity).isCreative()) {
                    continue;
                }
                if (target == null) {
                    target = entity;
                    distance = KillauraOld.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
                else {
                    if (DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
                        target = entity;
                        break;
                    }
                    if (KillauraOld.mc.player.getDistanceSq(entity) < distance) {
                        target = entity;
                        distance = KillauraOld.mc.player.getDistanceSq(entity);
                        maxHealth = EntityUtil.getHealth(entity);
                    }
                    if (EntityUtil.getHealth(entity) >= maxHealth) {
                        continue;
                    }
                    target = entity;
                    distance = KillauraOld.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
            }
        }
        if (this.sort.getValue() == sortEn.Distance) {
            KillauraOld.target = this.getClosest(this.range.getValue());
        }
        if (this.sort.getValue() == sortEn.LowestArmor) {
            KillauraOld.target = this.getArmorLess();
        }
        if (this.sort.getValue() == sortEn.HigherArmor) {
            KillauraOld.target = this.getArmorhigh();
        }
        if (this.sort.getValue() == sortEn.HealthUp) {
            KillauraOld.target = this.getHealthUp();
        }
        if (this.sort.getValue() == sortEn.HealthDown) {
            KillauraOld.target = this.getHealthDown();
        }
        if (this.sort.getValue() == sortEn.HurtTime) {
            KillauraOld.target = this.getHurttimer();
        }
        if (this.sort.getValue() == sortEn.BlockingStatus) {
            if (this.getBloking() != null) {
                KillauraOld.target = this.getBloking();
            }
            else {
                KillauraOld.target = this.getClosest(this.range.getValue());
            }
        }
        return KillauraOld.target;
    }
    
    private EntityPlayer getClosest(final double range) {
        KillauraOld.targets.clear();
        double dist = range;
        EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currentDist = KillauraOld.mc.player.getDistance((Entity)player);
                if (currentDist > dist) {
                    continue;
                }
                dist = currentDist;
                target = player;
                KillauraOld.targets.add(player);
            }
        }
        return target;
    }
    
    private EntityPlayer getBloking() {
        KillauraOld.targets.clear();
        final EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (this.canAttack(player) && player.isActiveItemStackBlocking()) {
                    return player;
                }
                continue;
            }
        }
        return target;
    }
    
    private EntityPlayer getArmorLess() {
        KillauraOld.targets.clear();
        double armor = 1815.0;
        EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currentarm = DamageUtil.ChekTotalarmorDamage(player);
                if (currentarm > armor) {
                    continue;
                }
                armor = currentarm;
                target = player;
                KillauraOld.targets.add(player);
            }
        }
        return target;
    }
    
    private EntityPlayer getHurttimer() {
        KillauraOld.targets.clear();
        double hurttime = 0.0;
        EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currenthurttime = player.hurtTime;
                if (currenthurttime > hurttime) {
                    continue;
                }
                hurttime = currenthurttime;
                target = player;
                KillauraOld.targets.add(player);
            }
        }
        return target;
    }
    
    private EntityPlayer getArmorhigh() {
        KillauraOld.targets.clear();
        double armor = 100.0;
        EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currentarm = DamageUtil.ChekTotalarmorDamage(player);
                if (currentarm < armor) {
                    continue;
                }
                armor = currentarm;
                target = player;
                KillauraOld.targets.add(player);
            }
        }
        return target;
    }
    
    private EntityPlayer getHealthUp() {
        KillauraOld.targets.clear();
        double health = 10.0;
        EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currenhealth = player.getHealth();
                if (currenhealth < health) {
                    continue;
                }
                health = currenhealth;
                target = player;
                KillauraOld.targets.add(player);
            }
        }
        return target;
    }
    
    private EntityPlayer getHealthDown() {
        KillauraOld.targets.clear();
        double health = 36.0;
        EntityPlayer target = null;
        for (final Entity object : KillauraOld.mc.world.loadedEntityList) {
            if (object instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)object;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currenhealth = player.getHealth();
                if (currenhealth > health) {
                    continue;
                }
                health = currenhealth;
                target = player;
                KillauraOld.targets.add(player);
            }
        }
        return target;
    }
    
    public boolean canAttack(final EntityPlayer nigger) {
        return nigger != KillauraOld.mc.player && !Thunderhack.friendManager.isFriend(nigger.getName()) && canSeeEntityAtFov((Entity)nigger, this.fov.getValue()) && !AntiBot.isBot(nigger) && KillauraOld.mc.player.getDistance((Entity)nigger) <= this.range.getValue();
    }
    
    @Override
    public String getDisplayInfo() {
        if (KillauraOld.target != null) {
            return KillauraOld.target.getName();
        }
        return null;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (KillauraOld.target != null) {
            if (this.circle.getValue()) {
                RenderHelper.drawCircle3D((Entity)KillauraOld.target, this.range.getValue() - 0.00625, event.getPartialTicks(), this.circlepoints.getValue(), 2.0f, new Color(0, 255, 135).getRGB());
                RenderHelper.drawCircle3D((Entity)KillauraOld.target, this.range.getValue() + 0.00625, event.getPartialTicks(), this.circlepoints.getValue(), 2.0f, new Color(0, 255, 135).getRGB());
                RenderHelper.drawCircle3D((Entity)KillauraOld.target, this.range.getValue(), event.getPartialTicks(), this.circlepoints.getValue(), 2.0f, -1);
            }
            if (this.owncircle.getValue()) {
                RenderHelper.drawCircle3D((Entity)KillauraOld.mc.player, this.owncirclerange.getValue() - 0.00625, event.getPartialTicks(), this.owncirclepoints.getValue(), 2.0f, PaletteHelper.astolfo(false, (int)KillauraOld.mc.player.height).getRGB());
                RenderHelper.drawCircle3D((Entity)KillauraOld.mc.player, this.owncirclerange.getValue() + 0.00625, event.getPartialTicks(), this.owncirclepoints.getValue(), 2.0f, PaletteHelper.astolfo(false, (int)KillauraOld.mc.player.height).getRGB());
                RenderHelper.drawCircle3D((Entity)KillauraOld.mc.player, this.owncirclerange.getValue(), event.getPartialTicks(), this.owncirclepoints.getValue(), 2.0f, PaletteHelper.astolfo(false, (int)KillauraOld.mc.player.height).getRGB());
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (KillauraOld.target != null) {
            final EntityPlayer entityPlayer = KillauraOld.target;
            final int ht = entityPlayer.hurtTime;
            final int width = 60 + Util.fr.getStringWidth(KillauraOld.target.getName());
            GL11.glPushMatrix();
            RenderUtil.drawSmoothRect(-10.0f + this.aboba.getValue(), 20.0f + this.aboba2.getValue(), 2 + width + this.aboba.getValue(), ((KillauraOld.target.getTotalArmorValue() != 0) ? 56.0f : 50.0f) + this.aboba2.getValue(), new Color(35, 35, 40, 230).getRGB());
            Util.fr.drawString(KillauraOld.target.getName(), (int)(10.0f + this.aboba.getValue()), (int)(26.0f + this.aboba2.getValue()), 16777215);
            Util.fr.drawStringWithShadow(MathUtil.round(KillauraOld.target.getHealth(), 1) + " HP", 10.0f + this.aboba.getValue(), 35.0f + this.aboba2.getValue(), 16777215);
            String status = null;
            Color statusColor = null;
            for (final PotionEffect effect : entityPlayer.getActivePotionEffects()) {
                if (effect.getPotion() == MobEffects.WEAKNESS) {
                    status = "Weakness!";
                    statusColor = new Color(135, 0, 25);
                }
                else if (effect.getPotion() == MobEffects.INVISIBILITY) {
                    status = "Invisible!";
                    statusColor = new Color(90, 90, 90);
                }
                else {
                    if (effect.getPotion() != MobEffects.STRENGTH) {
                        continue;
                    }
                    status = "Strength!";
                    statusColor = new Color(185, 65, 185);
                }
            }
            if (status != null) {
                Util.fr.drawString(status, (int)(8.0f + this.aboba.getValue()), (int)(19.0f + this.aboba2.getValue()), statusColor.getRGB());
            }
            try {
                this.drawHead(Objects.requireNonNull(KillauraOld.mc.getConnection()).getPlayerInfo(KillauraOld.target.getUniqueID()).getLocationSkin(), (int)(-8.0f + this.aboba.getValue()), (int)(22.0f + this.aboba2.getValue()));
            }
            catch (Exception exception) {
                this.drawfakeHead((int)(-8.0f + this.aboba.getValue()), (int)(22.0f + this.aboba2.getValue()));
            }
            final ItemStack renderOffhand = KillauraOld.target.getHeldItemOffhand().copy();
            if (renderOffhand.hasEffect() && (renderOffhand.getItem() instanceof ItemTool || renderOffhand.getItem() instanceof ItemArmor)) {
                renderOffhand.stackSize = 1;
            }
            this.renderItemStack(renderOffhand, (int)(60.0f + this.aboba.getValue()), (int)(20.0f + this.aboba2.getValue()));
            RenderUtil.drawSmoothRect(-8.0f + this.aboba.getValue(), 44.0f + this.aboba2.getValue(), width + this.aboba.getValue(), 46.0f + this.aboba2.getValue(), new Color(25, 25, 35, 255).getRGB());
            this.easingHealth = (int)animation((float)this.easingHealth, KillauraOld.target.getHealth() - this.easingHealth, 1.0E-4f);
            this.easingHealth += (int)((KillauraOld.target.getHealth() - this.easingHealth) / Math.pow(2.0, 7.0));
            if (this.easingHealth < 0 || this.easingHealth > KillauraOld.target.getMaxHealth()) {
                this.easingHealth = (int)KillauraOld.target.getHealth();
            }
            if (this.easingHealth > KillauraOld.target.getHealth()) {
                RenderUtil.drawSmoothRect(-8.0f + this.aboba.getValue(), 66.0f + this.aboba2.getValue(), this.easingHealth / KillauraOld.target.getMaxHealth() * width + this.aboba.getValue(), 58.0f + this.aboba2.getValue(), new Color(231, 182, 0, 255).getRGB());
            }
            if (this.easingHealth < KillauraOld.target.getHealth()) {
                RenderUtil.drawRect(this.easingHealth / KillauraOld.target.getMaxHealth() * width + this.aboba.getValue(), 56.0f + this.aboba2.getValue(), this.easingHealth / KillauraOld.target.getMaxHealth() * width + this.aboba.getValue(), 58.0f + this.aboba2.getValue(), new Color(231, 182, 0, 255).getRGB());
            }
            RenderUtil.drawSmoothRect(-8.0f + this.aboba.getValue(), 44.0f + this.aboba2.getValue(), this.aboba.getValue() + KillauraOld.target.getHealth() / KillauraOld.target.getMaxHealth() * width, 46.0f + this.aboba2.getValue(), ColorUtil.getHealthColor((EntityLivingBase)KillauraOld.target).getRGB());
            if (KillauraOld.target.getTotalArmorValue() != 0) {
                RenderUtil.drawSmoothRect(-8.0f + this.aboba.getValue(), 50.0f + this.aboba2.getValue(), width + this.aboba.getValue(), 52.0f + this.aboba2.getValue(), new Color(25, 25, 35, 255).getRGB());
                RenderUtil.drawSmoothRect(-8.0f + this.aboba.getValue(), 50.0f + this.aboba2.getValue(), this.aboba.getValue() + DamageUtil.ChekTotalarmorDamage(KillauraOld.target) / 1815.0f * width, 52.0f + this.aboba2.getValue(), new Color(77, 128, 255, 255).getRGB());
            }
            GL11.glPopMatrix();
            if (ht >= 7) {
                RenderUtil.drawSmoothRect(-8.0f + this.aboba.getValue(), 22.0f + this.aboba2.getValue(), 8.0f + this.aboba.getValue(), 38.0f + this.aboba2.getValue(), new Color(225, 3, 3, 166).getRGB());
            }
        }
    }
    
    private void renderItemStack(final ItemStack stack, final int x, final int y) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        NameTags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        NameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        NameTags.mc.getRenderItem().renderItemOverlays(NameTags.mc.fontRenderer, stack, x, y);
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    public void drawfakeHead(final int width, final int height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        KillauraOld.mc.getTextureManager().bindTexture(this.logo);
        Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
    }
    
    public void drawHead(final ResourceLocation skin, final int width, final int height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        KillauraOld.mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(width, height, 8.0f, 8.0f, 8, 8, 16, 16, 64.0f, 64.0f);
    }
    
    public static float animation(final float current, final float targetAnimation, final float speed) {
        final float speedTarget = 0.125f;
        return animation(current, targetAnimation, speedTarget, speed);
    }
    
    public static float animation(final float animation, final float target, final float poxyi, final float speedTarget) {
        float da = (target - animation) / Math.max((float)Minecraft.getDebugFPS(), 5.0f) * 15.0f;
        if (da > 0.0f) {
            da = Math.max(speedTarget, da);
            da = Math.min(target - animation, da);
        }
        else if (da < 0.0f) {
            da = Math.min(-speedTarget, da);
            da = Math.max(target - animation, da);
        }
        return animation + da;
    }
    
    public static boolean canSeeEntityAtFov(final Entity entityLiving, final float scope) {
        final double diffX = entityLiving.posX - Minecraft.getMinecraft().player.posX;
        final double diffZ = entityLiving.posZ - Minecraft.getMinecraft().player.posZ;
        final float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        final double difference = angleDifference(newYaw, Minecraft.getMinecraft().player.rotationYaw);
        return difference <= scope;
    }
    
    public static double angleDifference(final double a, final double b) {
        float yaw360 = (float)(Math.abs(a - b) % 360.0);
        if (yaw360 > 180.0f) {
            yaw360 = 360.0f - yaw360;
        }
        return yaw360;
    }
    
    static {
        KillauraOld.INSTANCE = new KillauraOld();
        KillauraOld.targets = new ArrayList<EntityPlayer>();
    }
    
    public enum sortEn
    {
        Distance, 
        HigherArmor, 
        BlockingStatus, 
        LowestArmor, 
        HealthUp, 
        HealthDown, 
        HurtTime, 
        Smart;
    }
    
    public enum rmode
    {
        FunnyGame, 
        Client, 
        Wint, 
        Predict;
    }
}
