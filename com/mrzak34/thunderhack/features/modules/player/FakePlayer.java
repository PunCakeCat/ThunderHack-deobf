//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.item.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.*;
import com.mrzak34.thunderhack.util.*;

public class FakePlayer extends Module
{
    private final ItemStack[] armors;
    private Setting<movingmode> moving;
    public Setting<Integer> vulnerabilityTick;
    public Setting<Integer> resetHealth;
    public Setting<Integer> tickRegenVal;
    public Setting<Integer> startHealth;
    public Setting<Float> speed;
    public Setting<Float> range;
    public Setting<String> nameFakePlayer;
    private Setting<Boolean> copyInventory;
    private Setting<Boolean> playerStacked;
    private Setting<Boolean> onShift;
    private Setting<Boolean> simulateDamage;
    private Setting<Boolean> followPlayer;
    private Setting<Boolean> resistance;
    private Setting<Boolean> pop;
    int incr;
    boolean beforePressed;
    ArrayList<playerInfo> listPlayers;
    movingManager manager;
    
    public FakePlayer() {
        super("FakePlayer", "\u0444\u0435\u0439\u043a\u043f\u043b\u0435\u0435\u0440 \u0434\u043b\u044f \u0442\u0435\u0441\u0442\u043e\u0432", Module.Category.PLAYER, true, false, false);
        this.armors = new ItemStack[] { new ItemStack((Item)Items.DIAMOND_BOOTS), new ItemStack((Item)Items.DIAMOND_LEGGINGS), new ItemStack((Item)Items.DIAMOND_CHESTPLATE), new ItemStack((Item)Items.DIAMOND_HELMET) };
        this.moving = (Setting<movingmode>)this.register(new Setting("Target", (T)movingmode.None));
        this.vulnerabilityTick = (Setting<Integer>)this.register(new Setting("Vulnerability Tick", (T)4, (T)0, (T)10));
        this.resetHealth = (Setting<Integer>)this.register(new Setting("Reset Health", (T)10, (T)0, (T)36));
        this.tickRegenVal = (Setting<Integer>)this.register(new Setting("Tick Regen", (T)4, (T)0, (T)30));
        this.startHealth = (Setting<Integer>)this.register(new Setting("Start Health", (T)20, (T)0, (T)30));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.36f, (T)0.0f, (T)4.0f, v -> this.moving.getValue() != movingmode.None || this.moving.getValue() != movingmode.Random));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)3.0f, (T)0.0f, (T)14.0f, v -> this.moving.getValue() == movingmode.Circle));
        this.nameFakePlayer = (Setting<String>)this.register(new Setting("Name FakePlayer", (T)"Ebatte_Sratte"));
        this.copyInventory = (Setting<Boolean>)this.register(new Setting("Copy Inventory", (T)false));
        this.playerStacked = (Setting<Boolean>)this.register(new Setting("Player Stacked", (T)true, v -> !this.copyInventory.getValue()));
        this.onShift = (Setting<Boolean>)this.register(new Setting("On Shift", (T)false));
        this.simulateDamage = (Setting<Boolean>)this.register(new Setting("Simulate Damage", (T)false));
        this.followPlayer = (Setting<Boolean>)this.register(new Setting("Follow Player", (T)true, v -> this.moving.getValue() == movingmode.Line));
        this.resistance = (Setting<Boolean>)this.register(new Setting("Resistance", (T)true));
        this.pop = (Setting<Boolean>)this.register(new Setting("Pop", (T)true));
        this.listPlayers = new ArrayList<playerInfo>();
        this.manager = new movingManager();
    }
    
    public void onLogout() {
        if (this.isOn()) {
            this.disable();
        }
    }
    
    public void onEnable() {
        this.incr = 0;
        this.beforePressed = false;
        if (FakePlayer.mc.player == null || FakePlayer.mc.player.isDead) {
            this.disable();
            return;
        }
        if (!this.onShift.getValue()) {
            this.spawnPlayer();
        }
    }
    
    void spawnPlayer() {
        final EntityOtherPlayerMP clonedPlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), this.nameFakePlayer.getValue() + this.incr));
        clonedPlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        clonedPlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        clonedPlayer.rotationYaw = FakePlayer.mc.player.rotationYaw;
        clonedPlayer.rotationPitch = FakePlayer.mc.player.rotationPitch;
        clonedPlayer.setGameType(GameType.SURVIVAL);
        clonedPlayer.setHealth((float)this.startHealth.getValue());
        FakePlayer.mc.world.addEntityToWorld(-1234 + this.incr, (Entity)clonedPlayer);
        ++this.incr;
        if (this.copyInventory.getValue()) {
            clonedPlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
        }
        else if (this.playerStacked.getValue()) {
            for (int i = 0; i < 4; ++i) {
                final ItemStack item = this.armors[i];
                item.addEnchantment((i == 3) ? Enchantments.BLAST_PROTECTION : Enchantments.PROTECTION, 4);
                clonedPlayer.inventory.armorInventory.set(i, (Object)item);
            }
        }
        if (this.resistance.getValue()) {
            clonedPlayer.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 123456789, 0));
        }
        clonedPlayer.onEntityUpdate();
        this.listPlayers.add(new playerInfo(clonedPlayer.getName()));
        if (this.moving.getValue() != movingmode.None) {
            this.manager.addPlayer(clonedPlayer.entityId, this.moving.getValue(), this.speed.getValue(), (this.moving.getValue() == movingmode.Line) ? this.getDirection() : -1, this.range.getValue(), this.followPlayer.getValue());
        }
    }
    
    public void onUpdate() {
        if (this.onShift.getValue() && FakePlayer.mc.gameSettings.keyBindSneak.isPressed() && !this.beforePressed) {
            this.beforePressed = true;
            this.spawnPlayer();
        }
        else {
            this.beforePressed = false;
        }
        for (int i = 0; i < this.listPlayers.size(); ++i) {
            if (this.listPlayers.get(i).update()) {
                final int finalI = i;
                final Optional<EntityPlayer> temp = (Optional<EntityPlayer>)FakePlayer.mc.world.playerEntities.stream().filter(e -> e.getName().equals(this.listPlayers.get(finalI).name)).findAny();
                if (temp.isPresent() && temp.get().getHealth() < 20.0f) {
                    temp.get().setHealth(temp.get().getHealth() + 1.0f);
                }
            }
        }
        this.manager.update();
    }
    
    int getDirection() {
        int yaw = (int)RotationUtil.normalizeAngle(FakePlayer.mc.player.getPitchYaw().y);
        if (yaw < 0) {
            yaw += 360;
        }
        yaw += 22;
        yaw %= 360;
        return yaw / 45;
    }
    
    public void onDisable() {
        if (FakePlayer.mc.world != null) {
            for (int i = 0; i < this.incr; ++i) {
                FakePlayer.mc.world.removeEntityFromWorld(-1234 + i);
            }
        }
        this.listPlayers.clear();
        this.manager.remove();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (this.simulateDamage.getValue()) {
            final Packet<?> packet = (Packet<?>)event.getPacket();
            if (packet instanceof SPacketSoundEffect) {
                final SPacketSoundEffect packetSoundEffect = (SPacketSoundEffect)packet;
                if (packetSoundEffect.getCategory() == SoundCategory.BLOCKS && packetSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    for (final Entity entity : new ArrayList<Entity>(FakePlayer.mc.world.loadedEntityList)) {
                        if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ()) <= 36.0) {
                            for (final EntityPlayer entityPlayer : FakePlayer.mc.world.playerEntities) {
                                if (entityPlayer.getName().split(this.nameFakePlayer.getValue()).length == 2) {
                                    final Optional<playerInfo> temp = this.listPlayers.stream().filter(e -> e.name.equals(entityPlayer.getName())).findAny();
                                    if (!temp.isPresent()) {
                                        continue;
                                    }
                                    if (!temp.get().canPop()) {
                                        continue;
                                    }
                                    final float damage = DamageUtil2.calculateDamage(packetSoundEffect.getX(), packetSoundEffect.getY(), packetSoundEffect.getZ(), (Entity)entityPlayer, false);
                                    if (damage > entityPlayer.getHealth()) {
                                        entityPlayer.setHealth((float)this.resetHealth.getValue());
                                        if (this.pop.getValue()) {
                                            FakePlayer.mc.effectRenderer.emitParticleAtEntity((Entity)entityPlayer, EnumParticleTypes.TOTEM, 30);
                                            FakePlayer.mc.world.playSound(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
                                        }
                                        MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(entityPlayer));
                                    }
                                    else {
                                        entityPlayer.setHealth(entityPlayer.getHealth() - damage);
                                    }
                                    temp.get().tickPop = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public enum movingmode
    {
        None, 
        Line, 
        Circle, 
        Random;
    }
    
    class playerInfo
    {
        final String name;
        int tickPop;
        int tickRegen;
        
        public playerInfo(final String name) {
            this.tickPop = -1;
            this.tickRegen = 0;
            this.name = name;
        }
        
        boolean update() {
            if (this.tickPop != -1 && ++this.tickPop >= FakePlayer.this.vulnerabilityTick.getValue()) {
                this.tickPop = -1;
            }
            if (++this.tickRegen >= FakePlayer.this.tickRegenVal.getValue()) {
                this.tickRegen = 0;
                return true;
            }
            return false;
        }
        
        boolean canPop() {
            return this.tickPop == -1;
        }
    }
    
    static class movingPlayer
    {
        private final int id;
        private final movingmode type;
        private final double speed;
        private final int direction;
        private final double range;
        private final boolean follow;
        int rad;
        
        public movingPlayer(final int id, final movingmode type, final double speed, final int direction, final double range, final boolean follow) {
            this.rad = 0;
            this.id = id;
            this.type = type;
            this.speed = speed;
            this.direction = Math.abs(direction);
            this.range = range;
            this.follow = follow;
        }
        
        void move() {
            final Entity player = Util.mc.world.getEntityByID(this.id);
            if (player != null) {
                switch (this.type) {
                    case Line: {
                        double posX = this.follow ? Util.mc.player.posX : player.posX;
                        double posY = this.follow ? Util.mc.player.posY : player.posY;
                        double posZ = this.follow ? Util.mc.player.posZ : player.posZ;
                        switch (this.direction) {
                            case 0: {
                                posZ += this.speed;
                                break;
                            }
                            case 1: {
                                posX -= this.speed / 2.0;
                                posZ += this.speed / 2.0;
                                break;
                            }
                            case 2: {
                                posX -= this.speed / 2.0;
                                break;
                            }
                            case 3: {
                                posZ -= this.speed / 2.0;
                                posX -= this.speed / 2.0;
                                break;
                            }
                            case 4: {
                                posZ -= this.speed;
                                break;
                            }
                            case 5: {
                                posX += this.speed / 2.0;
                                posZ -= this.speed / 2.0;
                                break;
                            }
                            case 6: {
                                posX += this.speed;
                                break;
                            }
                            case 7: {
                                posZ += this.speed / 2.0;
                                posX += this.speed / 2.0;
                                break;
                            }
                        }
                        if (BlockUtil.getBlock(posX, posY, posZ) instanceof BlockAir) {
                            for (int i = 0; i < 5 && BlockUtil.getBlock(posX, posY - 1.0, posZ) instanceof BlockAir; --posY, ++i) {}
                        }
                        else {
                            for (int i = 0; i < 5 && !(BlockUtil.getBlock(posX, posY, posZ) instanceof BlockAir); ++posY, ++i) {}
                        }
                        player.setPositionAndUpdate(posX, posY, posZ);
                        break;
                    }
                    case Circle: {
                        final double posXCir = Math.cos(this.rad / 100.0) * this.range + Util.mc.player.posX;
                        final double posZCir = Math.sin(this.rad / 100.0) * this.range + Util.mc.player.posZ;
                        double posYCir = Util.mc.player.posY;
                        if (BlockUtil.getBlock(posXCir, posYCir, posZCir) instanceof BlockAir) {
                            for (int j = 0; j < 5 && BlockUtil.getBlock(posXCir, posYCir - 1.0, posZCir) instanceof BlockAir; --posYCir, ++j) {}
                        }
                        else {
                            for (int j = 0; j < 5 && !(BlockUtil.getBlock(posXCir, posYCir, posZCir) instanceof BlockAir); ++posYCir, ++j) {}
                        }
                        player.setPositionAndUpdate(posXCir, posYCir, posZCir);
                        this.rad += (int)(this.speed * 10.0);
                        break;
                    }
                }
            }
        }
    }
    
    static class movingManager
    {
        private final ArrayList<movingPlayer> players;
        
        movingManager() {
            this.players = new ArrayList<movingPlayer>();
        }
        
        void addPlayer(final int id, final movingmode type, final double speed, final int direction, final double range, final boolean follow) {
            this.players.add(new movingPlayer(id, type, speed, direction, range, follow));
        }
        
        void update() {
            this.players.forEach(movingPlayer::move);
        }
        
        void remove() {
            this.players.clear();
        }
    }
}
