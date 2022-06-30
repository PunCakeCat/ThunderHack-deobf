//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.math.*;
import java.net.*;
import org.apache.commons.io.*;
import org.json.simple.*;
import java.io.*;
import org.json.simple.parser.*;
import com.mojang.authlib.*;

public class LogoutSpots extends Module
{
    private final Setting<Integer> removeDistance;
    private final Map<String, EntityPlayer> playerCache;
    private final Map<String, PlayerData> logoutCache;
    private final Map<String, String> uuidNameCache;
    
    public LogoutSpots() {
        super("LogoutSpots", "Puts Armor on for you.", Module.Category.RENDER, true, false, false);
        this.removeDistance = (Setting<Integer>)this.register(new Setting("RemoveDistance", (T)255, (T)1, (T)2000));
        this.playerCache = (Map<String, EntityPlayer>)Maps.newConcurrentMap();
        this.logoutCache = (Map<String, PlayerData>)Maps.newConcurrentMap();
        this.uuidNameCache = (Map<String, String>)Maps.newConcurrentMap();
    }
    
    public void onEnable() {
        super.onToggle();
        this.playerCache.clear();
        this.logoutCache.clear();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        try {
            final SPacketPlayerListItem packet = (SPacketPlayerListItem)event.getPacket();
            if (packet.getEntries().size() <= 1) {
                if (packet.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                    packet.getEntries().forEach(data -> {
                        if (!data.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId()) && data.getProfile().getName() == null && data.getProfile().getId().toString() == "b9523a25-2b04-4a75-bee0-b84027824fe0") {
                            if (data.getProfile().getId().toString() == "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
                                return;
                            }
                        }
                        try {
                            this.onPlayerJoin(data.getProfile().getId().toString());
                        }
                        catch (Exception ex) {}
                    });
                }
                else if (packet.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                    packet.getEntries().forEach(data2 -> {
                        if (!data2.getProfile().getId().equals(Minecraft.getMinecraft().player.getGameProfile().getId()) || data2.getProfile().getId() == null || data2.getProfile().getId().toString() != "b9523a25-2b04-4a75-bee0-b84027824fe0" || data2.getProfile().getId().toString() != "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
                            this.onPlayerLeave(data2.getProfile().getId().toString());
                        }
                    });
                }
            }
        }
        catch (Exception ex2) {}
    }
    
    public void onUpdate() {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) {
            return;
        }
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (player != null) {
                if (player.equals((Object)mc.player)) {
                    continue;
                }
                this.updatePlayerCache(player.getGameProfile().getId().toString(), player);
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (final String uuid : this.logoutCache.keySet()) {
            final PlayerData data = this.logoutCache.get(uuid);
            if (this.isOutOfRange(data)) {
                this.logoutCache.remove(uuid);
            }
            else {
                data.ghost.prevLimbSwingAmount = 0.0f;
                data.ghost.limbSwing = 0.0f;
                data.ghost.limbSwingAmount = 0.0f;
                data.ghost.hurtTime = 0;
                GlStateManager.pushMatrix();
                GlStateManager.enableLighting();
                GlStateManager.enableBlend();
                GlStateManager.enableDepth();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                try {
                    mc.getRenderManager().renderEntity((Entity)data.ghost, data.position.x - mc.getRenderManager().renderPosX, data.position.y - mc.getRenderManager().renderPosY, data.position.z - mc.getRenderManager().renderPosZ, data.ghost.rotationYaw, mc.getRenderPartialTicks(), false);
                }
                catch (Exception ex) {}
                GlStateManager.disableLighting();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (final String uuid : this.logoutCache.keySet()) {
            final PlayerData data = this.logoutCache.get(uuid);
            if (this.isOutOfRange(data)) {
                this.logoutCache.remove(uuid);
            }
            else {
                final GLUProjection.Projection projection = GLUProjection.getInstance().project(data.position.x - mc.getRenderManager().renderPosX, data.position.y - mc.getRenderManager().renderPosY, data.position.z - mc.getRenderManager().renderPosZ, GLUProjection.ClampMode.NONE, true);
                if (!projection.isType(GLUProjection.Projection.Type.INSIDE)) {
                    continue;
                }
                GlStateManager.pushMatrix();
                GlStateManager.translate(projection.getX(), projection.getY(), 0.0);
                final String text = data.profile.getName() + " logout";
                final float textWidth = (float)mc.fontRenderer.getStringWidth(text);
                Util.fr.drawStringWithShadow(text, -(textWidth / 2.0f), 0.0f, -1);
                GlStateManager.translate(-projection.getX(), -projection.getY(), 0.0);
                GlStateManager.popMatrix();
            }
        }
    }
    
    public void onPlayerLeave(final String uuid2) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (final String uuid3 : this.playerCache.keySet()) {
            if (!uuid3.equals(uuid2)) {
                continue;
            }
            final EntityPlayer player = this.playerCache.get(uuid3);
            final PlayerData data = new PlayerData(player.getPositionVector(), player.getGameProfile(), player);
            if (this.hasPlayerLogged(uuid3)) {
                continue;
            }
            this.logoutCache.put(uuid3, data);
        }
        this.playerCache.clear();
    }
    
    public void onPlayerJoin(final String uuid3) {
        final Minecraft mc = Minecraft.getMinecraft();
        for (final String uuid4 : this.logoutCache.keySet()) {
            if (!uuid4.equals(uuid3)) {
                continue;
            }
            this.logoutCache.remove(uuid4);
        }
        this.playerCache.clear();
    }
    
    private void cleanLogoutCache(final String uuid) {
        this.logoutCache.remove(uuid);
    }
    
    private void updatePlayerCache(final String uuid, final EntityPlayer player) {
        this.playerCache.put(uuid, player);
    }
    
    private boolean hasPlayerLogged(final String uuid) {
        return this.logoutCache.containsKey(uuid);
    }
    
    private boolean isOutOfRange(final PlayerData data) {
        try {
            final Vec3d position = data.position;
            return Minecraft.getMinecraft().player.getDistance(position.x, position.y, position.z) > this.removeDistance.getValue();
        }
        catch (Exception ex) {
            return true;
        }
    }
    
    public Map<String, EntityPlayer> getPlayerCache() {
        return this.playerCache;
    }
    
    public Map<String, PlayerData> getLogoutCache() {
        return this.logoutCache;
    }
    
    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (this.uuidNameCache.containsKey(uuid)) {
            return this.uuidNameCache.get(uuid);
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject)jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get((Object)"name").toString();
                    }
                }
            }
        }
        catch (IOException ex) {}
        catch (ParseException ex2) {}
        return null;
    }
    
    private class PlayerData
    {
        Vec3d position;
        GameProfile profile;
        EntityPlayer ghost;
        
        public PlayerData(final Vec3d position, final GameProfile profile, final EntityPlayer ghost) {
            this.position = position;
            this.profile = profile;
            this.ghost = ghost;
        }
    }
}
