//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import java.util.*;
import com.mrzak34.thunderhack.util.*;

public class ChangeSkinCommand extends Command
{
    public ArrayList<String> changedplayers;
    private static ChangeSkinCommand INSTANCE;
    
    public ChangeSkinCommand() {
        super("skinset", new String[] { "<name>", "<skinname>" });
        this.changedplayers = new ArrayList<String>();
        this.setInstance();
    }
    
    private void setInstance() {
        ChangeSkinCommand.INSTANCE = this;
    }
    
    public static ChangeSkinCommand getInstance() {
        if (ChangeSkinCommand.INSTANCE == null) {
            ChangeSkinCommand.INSTANCE = new ChangeSkinCommand();
        }
        return ChangeSkinCommand.INSTANCE;
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("skinset \u0438\u043c\u044f\u0438\u0433\u0440\u043e\u043a\u0430 \u0438\u043c\u044f\u0441\u043a\u0438\u043d\u0430");
            return;
        }
        if (commands.length == 2) {
            Command.sendMessage("skinset \u0438\u043c\u044f\u0438\u0433\u0440\u043e\u043a\u0430 \u0438\u043c\u044f\u0441\u043a\u0438\u043d\u0430");
            return;
        }
        if (commands.length == 3) {
            ThunderUtils.savePlayerSkin("https://minotar.net/skin/" + commands[1], commands[0]);
            this.changedplayers.add(commands[0]);
            Command.sendMessage("\u0421\u043a\u0438\u043d \u0438\u0433\u0440\u043e\u043a\u0430 " + commands[0] + " \u0438\u0437\u043c\u0435\u043d\u0435\u043d \u043d\u0430 " + commands[1]);
        }
    }
    
    static {
        ChangeSkinCommand.INSTANCE = new ChangeSkinCommand();
    }
}
