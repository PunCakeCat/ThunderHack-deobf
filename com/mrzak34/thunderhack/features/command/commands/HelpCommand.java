//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help");
    }
    
    public void execute(final String[] commands) {
        sendMessage("Commands: ");
        for (final Command command : Thunderhack.commandManager.getCommands()) {
            sendMessage(ChatFormatting.GRAY + Thunderhack.commandManager.getPrefix() + command.getName());
        }
    }
}
