//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.features.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.function.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;

public class FriendManager extends Feature
{
    public List<Friend> friends;
    
    public FriendManager() {
        super("Friends");
        this.friends = new ArrayList<Friend>();
    }
    
    public boolean isFriend(final String name) {
        this.cleanFriends();
        return this.friends.stream().anyMatch(friend -> friend.username.equalsIgnoreCase(name));
    }
    
    public boolean isFriend(final EntityPlayer player) {
        return this.isFriend(player.getName());
    }
    
    public void addFriend(final String name) {
        final Friend friend = this.getFriendByName(name, "");
        if (friend != null) {
            this.friends.add(friend);
        }
        this.cleanFriends();
        try {
            ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + name + "/100.png", name);
        }
        catch (Exception ex) {}
    }
    
    public void addFriend(final String name, final String description) {
        final Friend friend = this.getFriendByName(name, description);
        if (friend != null) {
            this.friends.add(friend);
        }
        this.cleanFriends();
        try {
            ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + name + "/100.png", name);
        }
        catch (Exception ex) {}
    }
    
    public void removeFriend(final String name) {
        this.cleanFriends();
        for (final Friend friend : this.friends) {
            if (!friend.getUsername().equalsIgnoreCase(name)) {
                continue;
            }
            this.friends.remove(friend);
            break;
        }
    }
    
    public void onLoad() {
        this.friends = new ArrayList<Friend>();
        this.clearSettings();
    }
    
    public void saveFriends() {
        this.clearSettings();
        this.cleanFriends();
        for (final Friend friend : this.friends) {
            this.register(new Setting(friend.getUuid().toString(), (Object)friend.getUsername()));
        }
    }
    
    public void cleanFriends() {
        this.friends.stream().filter(Objects::nonNull).filter(friend -> friend.getUsername() != null);
    }
    
    public List<Friend> getFriends() {
        this.cleanFriends();
        return this.friends;
    }
    
    public Friend getFriendByName(final String input, final String description) {
        final UUID uuid = PlayerUtil.getUUIDFromName(input);
        if (uuid != null) {
            Friend friend;
            if (Objects.equals(description, "")) {
                friend = new Friend(input, uuid, "");
            }
            else {
                friend = new Friend(input, uuid, description);
            }
            return friend;
        }
        return null;
    }
    
    public void addFriend(final Friend friend) {
        this.friends.add(friend);
    }
    
    public static class Friend
    {
        private final String username;
        private final UUID uuid;
        private final String description;
        
        public Friend(final String username, final UUID uuid, final String description) {
            this.username = username;
            this.uuid = uuid;
            this.description = description;
        }
        
        public String getUsername() {
            return this.username;
        }
        
        public UUID getUuid() {
            return this.uuid;
        }
    }
}
