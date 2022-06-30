//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.math.*;
import java.net.*;
import java.nio.charset.*;
import com.google.common.collect.*;
import com.google.gson.*;
import javax.net.ssl.*;
import net.minecraft.advancements.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.network.*;
import com.mrzak34.thunderhack.features.command.*;
import com.mojang.util.*;
import org.apache.commons.io.*;
import java.util.*;
import java.io.*;

public class PlayerUtil implements Util
{
    private static final JsonParser PARSER;
    
    public static String getNameFromUUID(final UUID uuid) {
        try {
            final lookUpName process = new lookUpName(uuid);
            final Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getName();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(PlayerUtil.mc.player.posX), Math.floor(PlayerUtil.mc.player.posY), Math.floor(PlayerUtil.mc.player.posZ));
    }
    
    public static void centerPlayer(final Vec3d centeredBlock) {
        final double xDeviation = Math.abs(centeredBlock.x - PlayerUtil.mc.player.posX);
        final double zDeviation = Math.abs(centeredBlock.z - PlayerUtil.mc.player.posZ);
        if (xDeviation <= 0.1 && zDeviation <= 0.1) {
            double newX = -2.0;
            double newZ = -2.0;
            final int xRel = (PlayerUtil.mc.player.posX < 0.0) ? -1 : 1;
            final int zRel = (PlayerUtil.mc.player.posZ < 0.0) ? -1 : 1;
            if (BlockUtil.getBlock(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY - 1.0, PlayerUtil.mc.player.posZ) instanceof BlockAir) {
                if (Math.abs(PlayerUtil.mc.player.posX % 1.0) * 100.0 <= 30.0) {
                    newX = Math.round(PlayerUtil.mc.player.posX - 0.3 * xRel) + 0.5 * -xRel;
                }
                else if (Math.abs(PlayerUtil.mc.player.posX % 1.0) * 100.0 >= 70.0) {
                    newX = Math.round(PlayerUtil.mc.player.posX + 0.3 * xRel) - 0.5 * -xRel;
                }
                if (Math.abs(PlayerUtil.mc.player.posZ % 1.0) * 100.0 <= 30.0) {
                    newZ = Math.round(PlayerUtil.mc.player.posZ - 0.3 * zRel) + 0.5 * -zRel;
                }
                else if (Math.abs(PlayerUtil.mc.player.posZ % 1.0) * 100.0 >= 70.0) {
                    newZ = Math.round(PlayerUtil.mc.player.posZ + 0.3 * zRel) - 0.5 * -zRel;
                }
            }
            if (newX == -2.0) {
                if (PlayerUtil.mc.player.posX > Math.round(PlayerUtil.mc.player.posX)) {
                    newX = Math.round(PlayerUtil.mc.player.posX) + 0.5;
                }
                else if (PlayerUtil.mc.player.posX < Math.round(PlayerUtil.mc.player.posX)) {
                    newX = Math.round(PlayerUtil.mc.player.posX) - 0.5;
                }
                else {
                    newX = PlayerUtil.mc.player.posX;
                }
            }
            if (newZ == -2.0) {
                if (PlayerUtil.mc.player.posZ > Math.round(PlayerUtil.mc.player.posZ)) {
                    newZ = Math.round(PlayerUtil.mc.player.posZ) + 0.5;
                }
                else if (PlayerUtil.mc.player.posZ < Math.round(PlayerUtil.mc.player.posZ)) {
                    newZ = Math.round(PlayerUtil.mc.player.posZ) - 0.5;
                }
                else {
                    newZ = PlayerUtil.mc.player.posZ;
                }
            }
            PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(newX, PlayerUtil.mc.player.posY, newZ, true));
            PlayerUtil.mc.player.setPosition(newX, PlayerUtil.mc.player.posY, newZ);
        }
    }
    
    public static EntityPlayer findClosestTarget(final double rangeMax, final EntityPlayer aimTarget) {
        return findClosestTarget(rangeMax, aimTarget, false);
    }
    
    public static EntityPlayer findClosestTarget(double rangeMax, final EntityPlayer aimTarget, final boolean moving) {
        rangeMax *= rangeMax;
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PlayerUtil.mc.world.playerEntities;
        EntityPlayer closestTarget = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (basicChecksEntity((Entity)entityPlayer)) {
                continue;
            }
            if (entityPlayer.motionX + PlayerUtil.mc.player.motionZ == 0.0 && moving) {
                continue;
            }
            if (aimTarget == null && PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) <= rangeMax) {
                closestTarget = entityPlayer;
            }
            else {
                if (aimTarget == null || PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) > rangeMax || PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) >= PlayerUtil.mc.player.getDistanceSq((Entity)aimTarget)) {
                    continue;
                }
                closestTarget = entityPlayer;
            }
        }
        return closestTarget;
    }
    
    public static EntityPlayer findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PlayerUtil.mc.world.playerEntities;
        EntityPlayer closestTarget = null;
        for (final EntityPlayer entityPlayer : playerList) {
            if (basicChecksEntity((Entity)entityPlayer)) {
                continue;
            }
            if (closestTarget == null) {
                closestTarget = entityPlayer;
            }
            else {
                if (PlayerUtil.mc.player.getDistanceSq((Entity)entityPlayer) >= PlayerUtil.mc.player.getDistanceSq((Entity)closestTarget)) {
                    continue;
                }
                closestTarget = entityPlayer;
            }
        }
        return closestTarget;
    }
    
    public static boolean basicChecksEntity(final Entity pl) {
        return pl.getName().equals(PlayerUtil.mc.player.getName()) || Thunderhack.friendManager.isFriend(pl.getName()) || pl.isDead || pl.getName().length() == 0;
    }
    
    public static EntityPlayer findLookingPlayer(final double rangeMax) {
        final ArrayList<EntityPlayer> listPlayer = new ArrayList<EntityPlayer>();
        for (final EntityPlayer playerSin : PlayerUtil.mc.world.playerEntities) {
            if (basicChecksEntity((Entity)playerSin)) {
                continue;
            }
            if (PlayerUtil.mc.player.getDistance((Entity)playerSin) > rangeMax) {
                continue;
            }
            listPlayer.add(playerSin);
        }
        EntityPlayer target = null;
        final Vec3d positionEyes = PlayerUtil.mc.player.getPositionEyes(PlayerUtil.mc.getRenderPartialTicks());
        final Vec3d rotationEyes = PlayerUtil.mc.player.getLook(PlayerUtil.mc.getRenderPartialTicks());
        final int precision = 2;
        for (int i = 0; i < (int)rangeMax; ++i) {
            for (int j = precision; j > 0; --j) {
                for (final EntityPlayer targetTemp : listPlayer) {
                    final AxisAlignedBB playerBox = targetTemp.getEntityBoundingBox();
                    final double xArray = positionEyes.x + rotationEyes.x * i + rotationEyes.x / j;
                    final double yArray = positionEyes.y + rotationEyes.y * i + rotationEyes.y / j;
                    final double zArray = positionEyes.z + rotationEyes.z * i + rotationEyes.z / j;
                    if (playerBox.maxY >= yArray && playerBox.minY <= yArray && playerBox.maxX >= xArray && playerBox.minX <= xArray && playerBox.maxZ >= zArray && playerBox.minZ <= zArray) {
                        target = targetTemp;
                    }
                }
            }
        }
        return target;
    }
    
    public static double[] Method1086(final double d) {
        float f = PlayerUtil.mc.player.movementInput.moveForward;
        float f2 = PlayerUtil.mc.player.movementInput.moveStrafe;
        float f3 = PlayerUtil.mc.player.prevRotationYaw + (PlayerUtil.mc.player.rotationYaw - PlayerUtil.mc.player.prevRotationYaw) * PlayerUtil.mc.getRenderPartialTicks();
        if (f != 0.0f) {
            if (f2 > 0.0f) {
                f3 += ((f > 0.0f) ? -45 : 45);
            }
            else if (f2 < 0.0f) {
                f3 += ((f > 0.0f) ? 45 : -45);
            }
            f2 = 0.0f;
            if (f > 0.0f) {
                f = 1.0f;
            }
            else if (f < 0.0f) {
                f = -1.0f;
            }
        }
        final double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
        final double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
        final double d4 = f * d * d3 + f2 * d * d2;
        final double d5 = f * d * d2 - f2 * d * d3;
        return new double[] { d4, d5 };
    }
    
    public static String getNameFromUUID(final String uuid) {
        try {
            final lookUpName process = new lookUpName(uuid);
            final Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getName();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static UUID getUUIDFromName(final String name) {
        try {
            final lookUpUUID process = new lookUpUUID(name);
            final Thread thread = new Thread(process);
            thread.start();
            thread.join();
            return process.getUUID();
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String requestIDs(final String data) {
        try {
            final String query = "https://api.mojang.com/profiles/minecraft";
            final URL url = new URL(query);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            final OutputStream os = conn.getOutputStream();
            os.write(data.getBytes(StandardCharsets.UTF_8));
            os.close();
            final InputStream in = new BufferedInputStream(conn.getInputStream());
            final String res = convertStreamToString(in);
            in.close();
            conn.disconnect();
            return res;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String convertStreamToString(final InputStream is) {
        final Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "/";
    }
    
    public static List<String> getHistoryOfNames(final UUID id) {
        try {
            final JsonArray array = getResources(new URL("https://api.mojang.com/user/profiles/" + getIdNoHyphens(id) + "/names"), "GET").getAsJsonArray();
            final List<String> temp = (List<String>)Lists.newArrayList();
            for (final JsonElement e : array) {
                final JsonObject node = e.getAsJsonObject();
                final String name = node.get("name").getAsString();
                final long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
                temp.add(name + "\u00c2§8" + new Date(changedAt).toString());
            }
            Collections.sort(temp);
            return temp;
        }
        catch (Exception ignored) {
            return null;
        }
    }
    
    public static String getIdNoHyphens(final UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }
    
    private static JsonElement getResources(final URL url, final String request) throws Exception {
        return getResources(url, request, null);
    }
    
    private static JsonElement getResources(final URL url, final String request, final JsonElement element) throws Exception {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request);
            connection.setRequestProperty("Content-Type", "application/json");
            if (element != null) {
                final DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.writeBytes(AdvancementManager.GSON.toJson(element));
                output.close();
            }
            final Scanner scanner = new Scanner(connection.getInputStream());
            final StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();
            final String json = builder.toString();
            final JsonElement data = PlayerUtil.PARSER.parse(json);
            return data;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    public static int findObiInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = PlayerUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockEnderChest) {
                    return i;
                }
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    static {
        PARSER = new JsonParser();
    }
    
    public static class lookUpUUID implements Runnable
    {
        private final String name;
        private volatile UUID uuid;
        
        public lookUpUUID(final String name) {
            this.name = name;
        }
        
        @Override
        public void run() {
            NetworkPlayerInfo profile;
            try {
                final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfoMap());
                profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(this.name)).findFirst().orElse(null);
                assert profile != null;
                this.uuid = profile.getGameProfile().getId();
            }
            catch (Exception e2) {
                profile = null;
            }
            if (profile == null) {
                Command.sendMessage("Player isn't online. Looking up UUID..");
                final String s = PlayerUtil.requestIDs("[\"" + this.name + "\"]");
                if (s == null || s.isEmpty()) {
                    Command.sendMessage("Couldn't find player ID. Are you connected to the internet? (0)");
                }
                else {
                    final JsonElement element = new JsonParser().parse(s);
                    if (element.getAsJsonArray().size() == 0) {
                        Command.sendMessage("Couldn't find player ID. (1)");
                    }
                    else {
                        try {
                            final String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                            this.uuid = UUIDTypeAdapter.fromString(id);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Command.sendMessage("Couldn't find player ID. (2)");
                        }
                    }
                }
            }
        }
        
        public UUID getUUID() {
            return this.uuid;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public static class lookUpName implements Runnable
    {
        private final String uuid;
        private final UUID uuidID;
        private volatile String name;
        
        public lookUpName(final String input) {
            this.uuid = input;
            this.uuidID = UUID.fromString(input);
        }
        
        public lookUpName(final UUID input) {
            this.uuidID = input;
            this.uuid = input.toString();
        }
        
        @Override
        public void run() {
            this.name = this.lookUpName();
        }
        
        public String lookUpName() {
            EntityPlayer player = null;
            if (Util.mc.world != null) {
                player = Util.mc.world.getPlayerEntityByUUID(this.uuidID);
            }
            if (player == null) {
                final String url = "https://api.mojang.com/user/profiles/" + this.uuid.replace("-", "") + "/names";
                try {
                    final String nameJson = IOUtils.toString(new URL(url));
                    if (nameJson.contains(",")) {
                        final List<String> names = Arrays.asList(nameJson.split(","));
                        Collections.reverse(names);
                        return names.get(1).replace("{\"name\":\"", "").replace("\"", "");
                    }
                    return nameJson.replace("[{\"name\":\"", "").replace("\"}]", "");
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
            return player.getName();
        }
        
        public String getName() {
            return this.name;
        }
    }
}
