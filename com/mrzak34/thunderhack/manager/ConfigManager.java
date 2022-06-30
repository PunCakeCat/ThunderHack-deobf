//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.*;
import java.util.stream.*;
import java.nio.file.attribute.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;
import com.mrzak34.thunderhack.features.modules.*;

public class ConfigManager implements Util
{
    public ArrayList<Feature> features;
    public String currentcfg;
    public String config;
    
    public ConfigManager() {
        this.features = new ArrayList<Feature>();
        this.config = "ThunderHack/config/";
    }
    
    public static void setValueFromJson(final Feature feature, final Setting setting, final JsonElement element) {
        final String type = setting.getType();
        switch (type) {
            case "Boolean": {
                setting.setValue((Object)element.getAsBoolean());
            }
            case "Double": {
                setting.setValue((Object)element.getAsDouble());
            }
            case "Float": {
                setting.setValue((Object)element.getAsFloat());
            }
            case "Integer": {
                setting.setValue((Object)element.getAsInt());
            }
            case "String": {
                final String str = element.getAsString();
                setting.setValue((Object)str.replace("_", " "));
            }
            case "Bind": {
                setting.setValue((Object)new Bind.BindConverter().doBackward(element));
            }
            case "BlockListSetting": {
                final JsonArray array2 = element.getAsJsonArray();
                final String str2;
                array2.forEach(jsonElement -> {
                    str2 = jsonElement.getAsString();
                    ((BlockListSetting)setting.getValue()).addBlock(str2);
                    return;
                });
                ((BlockListSetting)setting.getValue()).refreshBlocks();
            }
            case "ColorSetting": {
                final JsonArray array3 = element.getAsJsonArray();
                ((ColorSetting)setting.getValue()).setColor(array3.get(0).getAsInt());
                ((ColorSetting)setting.getValue()).setCycle(array3.get(1).getAsBoolean());
                ((ColorSetting)setting.getValue()).setGlobalOffset(array3.get(2).getAsInt());
                System.out.println("color loaded");
            }
            case "PositionSetting": {
                final JsonArray array4 = element.getAsJsonArray();
                ((PositionSetting)setting.getValue()).setX(array4.get(0).getAsFloat());
                ((PositionSetting)setting.getValue()).setY(array4.get(1).getAsFloat());
                System.out.println("positions geted");
            }
            case "SubBind": {
                setting.setValue((Object)new SubBind.SubBindConverter().doBackward(element));
            }
            case "Enum": {
                try {
                    final EnumConverter converter = new EnumConverter((Class)((Enum)setting.getValue()).getClass());
                    final Enum value = converter.doBackward(element);
                    setting.setValue((value == null) ? setting.getDefaultValue() : value);
                }
                catch (Exception ex) {}
            }
            default: {
                Thunderhack.LOGGER.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
            }
        }
    }
    
    private static void loadFile(final JsonObject input, final Feature feature) {
        for (final Map.Entry<String, JsonElement> entry : input.entrySet()) {
            final String settingName = entry.getKey();
            final JsonElement element = entry.getValue();
            if (feature instanceof FriendManager) {
                try {
                    Thunderhack.friendManager.addFriend(new FriendManager.Friend(element.getAsString(), UUID.fromString(settingName), ""));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (feature instanceof EnemyManager) {
                try {
                    Thunderhack.enemyManager.addEnemy(new EnemyManager.Enemy(element.getAsString(), UUID.fromString(settingName)));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                boolean settingFound = false;
                for (final Setting setting : feature.getSettings()) {
                    if (settingName.equals(setting.getName())) {
                        try {
                            setValueFromJson(feature, setting, element);
                        }
                        catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        settingFound = true;
                    }
                }
                if (settingFound) {}
            }
        }
    }
    
    public void loadConfig(final String name) {
        this.currentcfg = name;
        final List<File> files = Arrays.stream((Object[])Objects.requireNonNull((T[])new File("ThunderHack").listFiles())).filter(File::isDirectory).collect((Collector<? super Object, ?, List<File>>)Collectors.toList());
        if (files.contains(new File("ThunderHack/" + name + "/"))) {
            this.config = "ThunderHack/" + name + "/";
        }
        else {
            this.config = "ThunderHack/config/";
        }
        Thunderhack.friendManager.onLoad();
        Thunderhack.enemyManager.onLoad();
        for (final Feature feature : this.features) {
            try {
                this.loadSettings(feature);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveCurrentConfig();
    }
    
    public boolean configExists(final String name) {
        final List<File> files = Arrays.stream((Object[])Objects.requireNonNull((T[])new File("ThunderHack").listFiles())).filter(File::isDirectory).collect((Collector<? super Object, ?, List<File>>)Collectors.toList());
        return files.contains(new File("ThunderHack/" + name + "/"));
    }
    
    public void deleteConfig(final String name) {
        this.config = "ThunderHack/" + name;
        final File path = new File(this.config);
        path.renameTo(new File("ThunderHack/oldcfg" + Math.random() * 1000.0));
    }
    
    public void saveConfig(final String name) {
        this.config = "ThunderHack/" + name + "/";
        final File path = new File(this.config);
        if (!path.exists()) {
            path.mkdir();
        }
        Thunderhack.friendManager.saveFriends();
        Thunderhack.enemyManager.saveEnemies();
        for (final Feature feature : this.features) {
            try {
                this.saveSettings(feature);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveCurrentConfig();
    }
    
    public void saveCurrentConfig() {
        final File currentConfig = new File("ThunderHack/currentconfig.txt");
        try {
            if (currentConfig.exists()) {
                final FileWriter writer = new FileWriter(currentConfig);
                final String tempConfig = this.config.replaceAll("/", "");
                writer.write(tempConfig.replaceAll("ThunderHack", ""));
                writer.close();
            }
            else {
                currentConfig.createNewFile();
                final FileWriter writer = new FileWriter(currentConfig);
                final String tempConfig = this.config.replaceAll("/", "");
                writer.write(tempConfig.replaceAll("ThunderHack", ""));
                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String loadCurrentConfig() {
        final File currentConfig = new File("ThunderHack/currentconfig.txt");
        String name = "config";
        try {
            if (currentConfig.exists()) {
                final Scanner reader = new Scanner(currentConfig);
                while (reader.hasNextLine()) {
                    name = reader.nextLine();
                }
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.currentcfg = name;
    }
    
    public void resetConfig(final boolean saveConfig, final String name) {
        for (final Feature feature : this.features) {
            feature.reset();
        }
        if (saveConfig) {
            this.saveConfig(name);
        }
    }
    
    public void saveSettings(final Feature feature) throws IOException {
        final JsonObject object = new JsonObject();
        final File directory = new File(this.config + this.getDirectory(feature));
        if (!directory.exists()) {
            directory.mkdir();
        }
        final String featureName = this.config + this.getDirectory(feature) + feature.getName() + ".json";
        final Path outputFile = Paths.get(featureName, new String[0]);
        if (!Files.exists(outputFile, new LinkOption[0])) {
            Files.createFile(outputFile, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson((JsonElement)this.writeSettings(feature));
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outputFile, new OpenOption[0])));
        writer.write(json);
        writer.close();
    }
    
    public void init() {
        this.features.addAll((Collection<? extends Feature>)Thunderhack.moduleManager.modules);
        this.features.add(Thunderhack.friendManager);
        this.features.add(Thunderhack.enemyManager);
        final String name = this.loadCurrentConfig();
        this.loadConfig(name);
        Thunderhack.LOGGER.info("Config loaded.");
    }
    
    private void loadSettings(final Feature feature) throws IOException {
        final String featureName = this.config + this.getDirectory(feature) + feature.getName() + ".json";
        final Path featurePath = Paths.get(featureName, new String[0]);
        if (!Files.exists(featurePath, new LinkOption[0])) {
            return;
        }
        this.loadPath(featurePath, feature);
    }
    
    private void loadPath(final Path path, final Feature feature) throws IOException {
        final InputStream stream = Files.newInputStream(path, new OpenOption[0]);
        try {
            loadFile(new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject(), feature);
        }
        catch (IllegalStateException e) {
            Thunderhack.LOGGER.error("Bad Config File for: " + feature.getName() + ". Resetting...");
            loadFile(new JsonObject(), feature);
        }
        stream.close();
    }
    
    public JsonObject writeSettings(final Feature feature) {
        final JsonObject object = new JsonObject();
        final JsonParser jp = new JsonParser();
        for (final Setting setting : feature.getSettings()) {
            if (setting.isEnumSetting()) {
                final EnumConverter converter = new EnumConverter((Class)((Enum)setting.getValue()).getClass());
                object.add(setting.getName(), converter.doForward((Enum)setting.getValue()));
            }
            else {
                if (setting.isStringSetting()) {
                    final String str = (String)setting.getValue();
                    setting.setValue((Object)str.replace(" ", "_"));
                }
                if (setting.isBlocklist()) {
                    final JsonArray array = new JsonArray();
                    for (final String str2 : ((BlockListSetting)setting.getValue()).getBlocksAsString()) {
                        array.add(str2);
                    }
                    object.add(setting.getName(), (JsonElement)array);
                }
                if (setting.isColorSetting()) {
                    final JsonArray array = new JsonArray();
                    array.add((Number)((ColorSetting)setting.getValue()).getRawColor());
                    array.add(Boolean.valueOf(((ColorSetting)setting.getValue()).isCycle()));
                    array.add((Number)((ColorSetting)setting.getValue()).getGlobalOffset());
                    object.add(setting.getName(), (JsonElement)array);
                }
                else if (setting.isPositionSetting()) {
                    final JsonArray array = new JsonArray();
                    array.add((Number)((PositionSetting)setting.getValue()).getX());
                    array.add((Number)((PositionSetting)setting.getValue()).getY());
                    object.add(setting.getName(), (JsonElement)array);
                }
                else {
                    try {
                        object.add(setting.getName(), jp.parse(setting.getValueAsString()));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return object;
    }
    
    public String getDirectory(final Feature feature) {
        String directory = "";
        if (feature instanceof Module) {
            directory = directory + ((Module)feature).getCategory().getName() + "/";
        }
        return directory;
    }
}
