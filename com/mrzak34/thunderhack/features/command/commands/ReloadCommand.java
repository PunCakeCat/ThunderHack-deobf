//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.*;

public class ReloadCommand extends Command
{
    public ReloadCommand() {
        super("reload", new String[0]);
    }
    
    public void execute(final String[] commands) {
        Thunderhack.reload();
    }
}
