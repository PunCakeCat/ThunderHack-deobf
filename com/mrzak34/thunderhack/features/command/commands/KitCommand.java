//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.command.commands;

import com.mrzak34.thunderhack.features.command.*;
import com.google.gson.*;
import net.minecraft.item.*;
import java.util.*;
import java.io.*;

public class KitCommand extends Command
{
    private static final String pathSave = "ThunderHack/kits/AutoGear.json";
    private static final HashMap<String, String> errorMessage;
    
    public KitCommand() {
        super("kit", new String[] { "<create/set/del/list>", "<name>" });
    }
    
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("kit <create/set/del/list> <name>");
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "create": {
                        this.save(commands[1]);
                    }
                    case "set": {
                        this.set(commands[1]);
                    }
                    case "del": {
                        this.delete(commands[1]);
                    }
                    default: {
                        sendMessage("WTF R U MEAN \u0422\u0423\u041f\u041e\u0419 \u0414\u0415\u0411\u0418\u041b?!?!?!?!?!?");
                        break;
                    }
                }
            }
            return;
        }
        final String s2 = commands[0];
        switch (s2) {
            case "list": {
                this.listMessage();
            }
            default: {}
        }
    }
    
    private void listMessage() {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject();
            for (int lenghtJson = completeJson.entrySet().size(), i = 0; i < lenghtJson; ++i) {
                final String item = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject().entrySet().toArray()[i].toString().split("=")[0];
                if (!item.equals("pointer")) {
                    Command.sendMessage("Kit avaible: " + item);
                }
            }
        }
        catch (IOException e) {
            errorMessage("NoEx");
        }
    }
    
    private void delete(final String name) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject();
            if (completeJson.get(name) != null && !name.equals("pointer")) {
                completeJson.remove(name);
                if (completeJson.get("pointer").getAsString().equals(name)) {
                    completeJson.addProperty("pointer", "none");
                }
                this.saveFile(completeJson, name, "deleted");
            }
            else {
                errorMessage("NoEx");
            }
        }
        catch (IOException e) {
            errorMessage("NoEx");
        }
    }
    
    private void set(final String name) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject();
            if (completeJson.get(name) != null && !name.equals("pointer")) {
                completeJson.addProperty("pointer", name);
                this.saveFile(completeJson, name, "selected");
            }
            else {
                errorMessage("NoEx");
            }
        }
        catch (IOException e) {
            errorMessage("NoEx");
        }
    }
    
    private void save(final String name) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject();
            if (completeJson.get(name) != null && !name.equals("pointer")) {
                errorMessage("Exist");
                return;
            }
        }
        catch (IOException e) {
            completeJson.addProperty("pointer", "none");
        }
        final StringBuilder jsonInventory = new StringBuilder();
        for (final ItemStack item : KitCommand.mc.player.inventory.mainInventory) {
            jsonInventory.append(item.getItem().getRegistryName().toString() + item.getMetadata()).append(" ");
        }
        completeJson.addProperty(name, jsonInventory.toString());
        this.saveFile(completeJson, name, "saved");
    }
    
    private void saveFile(final JsonObject completeJson, final String name, final String operation) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter("ThunderHack/kits/AutoGear.json"));
            bw.write(completeJson.toString());
            bw.close();
            Command.sendMessage("Kit " + name + " " + operation);
        }
        catch (IOException e) {
            errorMessage("Saving");
        }
    }
    
    private static void errorMessage(final String e) {
        Command.sendMessage("Error: " + KitCommand.errorMessage.get(e));
    }
    
    public static String getCurrentSet() {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject();
            if (!completeJson.get("pointer").getAsString().equals("none")) {
                return completeJson.get("pointer").getAsString();
            }
        }
        catch (IOException ex) {}
        errorMessage("NoEx");
        return "";
    }
    
    public static String getInventoryKit(final String kit) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("ThunderHack/kits/AutoGear.json")).getAsJsonObject();
            return completeJson.get(kit).getAsString();
        }
        catch (IOException ex) {
            errorMessage("NoEx");
            return "";
        }
    }
    
    static {
        errorMessage = new HashMap<String, String>() {
            {
                this.put("NoPar", "Not enough parameters");
                this.put("Exist", "This kit arleady exist");
                this.put("Saving", "Error saving the file");
                this.put("NoEx", "Kit not found");
            }
        };
    }
}
