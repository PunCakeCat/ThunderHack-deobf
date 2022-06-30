//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleEntityMetadata" }, at = { @At("RETURN") }, cancellable = true)
    private void handleEntityMetadataHook(final SPacketEntityMetadata packetIn, final CallbackInfo info) {
        final Entity entity;
        final EntityPlayer player;
        if (Util.mc.world != null && (entity = Util.mc.world.getEntityByID(packetIn.getEntityId())) instanceof EntityPlayer && (player = (EntityPlayer)entity).getHealth() <= 0.0f) {
            MinecraftForge.EVENT_BUS.post((Event)new DeathEvent(player));
            if (Thunderhack.totemPopManager != null) {
                Thunderhack.totemPopManager.onDeath(player);
            }
        }
    }
}
