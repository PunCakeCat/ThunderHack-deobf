//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.modules.*;

public class RenameCommand extends Command
{
    public RenameCommand() {
        super("rename", new String[] { "<module>", "<name>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            ModuleCommand.sendMessage("\u0414\u0435\u0431\u0438\u043b");
            return;
        }
        Module module = Thunderhack.moduleManager.getModuleByDisplayName(commands[0]);
        if (module == null) {
            module = Thunderhack.moduleManager.getModuleByName(commands[0]);
            if (module == null) {
                ModuleCommand.sendMessage("This module doesnt exist.");
                return;
            }
            ModuleCommand.sendMessage(" This is the original name of the module. Its current name is: " + module.getDisplayName());
        }
        else if (commands.length == 2) {
            ModuleCommand.sendMessage(module.getDisplayName() + " : " + module.getDescription());
        }
    }
}
