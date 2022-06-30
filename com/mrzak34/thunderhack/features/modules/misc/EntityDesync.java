//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.network.play.server.*;

public class EntityDesync extends Module
{
    private Entity Riding;
    
    public EntityDesync() {
        super("EntityDesync", "\u0434\u0435\u0441\u0438\u043d\u043a\u0430\u0435\u0442 \u0441 \u043b\u043e\u0434\u043a\u0438-\u0438\u043b\u0438 \u043e\u0441\u043b\u0430", Category.MISC, true, false, false);
        this.Riding = null;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (EntityDesync.mc.player == null) {
            this.Riding = null;
            this.toggle();
            return;
        }
        if (!EntityDesync.mc.player.isRiding()) {
            Command.sendMessage("You are not riding an entity");
            this.Riding = null;
            this.toggle();
            return;
        }
        this.Riding = EntityDesync.mc.player.getRidingEntity();
        EntityDesync.mc.player.dismountRidingEntity();
        EntityDesync.mc.world.removeEntity(this.Riding);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.Riding != null) {
            this.Riding.isDead = false;
            if (!EntityDesync.mc.player.isRiding()) {
                EntityDesync.mc.world.spawnEntity(this.Riding);
                EntityDesync.mc.player.startRiding(this.Riding, true);
            }
            this.Riding = null;
            Command.sendMessage("Forced a remount");
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (this.Riding == null) {
            return;
        }
        if (EntityDesync.mc.player.isRiding()) {
            return;
        }
        EntityDesync.mc.player.onGround = true;
        this.Riding.setPosition(EntityDesync.mc.player.posX, EntityDesync.mc.player.posY, EntityDesync.mc.player.posZ);
        EntityDesync.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove(this.Riding));
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSetPassengers) {
            if (this.Riding == null) {
                return;
            }
            final SPacketSetPassengers l_Packet = (SPacketSetPassengers)event.getPacket();
            final Entity en = EntityDesync.mc.world.getEntityByID(l_Packet.getEntityId());
            if (en == this.Riding) {
                for (final int i : l_Packet.getPassengerIds()) {
                    final Entity ent = EntityDesync.mc.world.getEntityByID(i);
                    if (ent == EntityDesync.mc.player) {
                        return;
                    }
                }
                Command.sendMessage("You dismounted");
                this.toggle();
            }
        }
        else if (event.getPacket() instanceof SPacketDestroyEntities) {
            final SPacketDestroyEntities l_Packet2 = (SPacketDestroyEntities)event.getPacket();
            for (final int l_EntityId : l_Packet2.getEntityIDs()) {
                if (l_EntityId == this.Riding.getEntityId()) {
                    Command.sendMessage("Entity is now null SPacketDestroyEntities");
                    return;
                }
            }
        }
    }
}
