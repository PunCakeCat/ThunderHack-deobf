//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import java.io.*;
import java.util.stream.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super("config", new String[] { "<save/load>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            sendMessage("\u041a\u043e\u043d\u0444\u0438\u0433\u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u044f\u044e\u0442\u0441\u044f \u0432  ThunderHack/config");
            return;
        }
        if (commands.length == 2) {
            if ("list".equals(commands[0])) {
                String configs = "Configs: ";
                final File file = new File("ThunderHack/");
                final List<File> directories = Arrays.stream(file.listFiles()).filter(File::isDirectory).filter(f -> !f.getName().equals("util")).collect((Collector<? super File, ?, List<File>>)Collectors.toList());
                final StringBuilder builder = new StringBuilder(configs);
                for (final File file2 : directories) {
                    builder.append(file2.getName() + ", ");
                }
                configs = builder.toString();
                sendMessage(configs);
            }
            else {
                sendMessage("\u041d\u0435\u0442 \u0442\u0430\u043a\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b!... \u041c\u043e\u0436\u0435\u0442 list ?");
            }
        }
        if (commands.length >= 3) {
            final String s = commands[0];
            switch (s) {
                case "save": {
                    Thunderhack.configManager.saveConfig(commands[1]);
                    sendMessage(ChatFormatting.GREEN + "\u041a\u043e\u043d\u0444\u0438\u0433 '" + commands[1] + "' \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d");
                }
                case "load": {
                    if (Thunderhack.configManager.configExists(commands[1])) {
                        Thunderhack.configManager.loadConfig(commands[1]);
                        sendMessage(ChatFormatting.GREEN + "\u0417\u0430\u0433\u0440\u0443\u0436\u0435\u043d \u043a\u043e\u043d\u0444\u0438\u0433 '" + commands[1]);
                    }
                    else {
                        sendMessage(ChatFormatting.RED + "\u041a\u043e\u043d\u0444\u0438\u0433 '" + commands[1] + "' \u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442");
                    }
                }
                default: {
                    sendMessage("\u041d\u0435\u0442 \u0442\u0430\u043a\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u044b! \u041f\u0440\u0438\u043c\u0435\u0440 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f: <save/load>");
                    break;
                }
            }
        }
    }
}
