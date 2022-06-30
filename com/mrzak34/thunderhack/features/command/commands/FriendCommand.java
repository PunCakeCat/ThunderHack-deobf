//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.manager.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend", new String[] { "<add/del/name/clear>", "<name>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Thunderhack.friendManager.getFriends().isEmpty()) {
                sendMessage("Friend list empty D:.");
            }
            else {
                String f = "Friends: ";
                for (final FriendManager.Friend friend : Thunderhack.friendManager.getFriends()) {
                    try {
                        f = f + friend.getUsername() + ", ";
                    }
                    catch (Exception ex) {}
                }
                sendMessage(f);
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        Thunderhack.friendManager.addFriend(commands[1]);
                        sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
                        FriendCommand.mc.player.sendChatMessage("/w " + commands[1] + " i friended u at ThunderHack");
                    }
                    case "del": {
                        Thunderhack.friendManager.removeFriend(commands[1]);
                        sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
                    }
                    default: {
                        sendMessage("Unknown Command, try friend add/del (name)");
                        break;
                    }
                }
            }
            return;
        }
        final String s2 = commands[0];
        switch (s2) {
            case "reset": {
                Thunderhack.friendManager.onLoad();
                sendMessage("Friends got reset.");
            }
            default: {
                sendMessage(commands[0] + (Thunderhack.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            }
        }
    }
}
