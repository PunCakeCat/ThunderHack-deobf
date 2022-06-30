//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.features.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.modules.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.stream.*;

public class FileManager extends Feature
{
    private final Path base;
    private final Path config;
    private final Path notebot;
    private final Path avatars;
    private final Path skins;
    
    public FileManager() {
        this.base = this.getMkDirectory(this.getRoot(), "ThunderHack");
        this.config = this.getMkDirectory(this.base, "config");
        this.notebot = this.getMkDirectory(this.base, "tmp");
        this.avatars = this.getMkDirectory(this.base, "friendsAvatars");
        this.skins = this.getMkDirectory(this.base, "skins");
        this.getMkDirectory(this.base, "pvp");
        for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
            this.getMkDirectory(this.config, category.getName());
        }
    }
    
    public static boolean appendTextFile(final String data, final String file) {
        try {
            final Path path = Paths.get(file, new String[0]);
            Files.write(path, Collections.singletonList(data), StandardCharsets.UTF_8, Files.exists(path, new LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        }
        catch (IOException e) {
            System.out.println("\u041d\u0435 \u0443\u0434\u0430\u0435\u0442\u0441\u044f \u0437\u0430\u043f\u0438\u0441\u0430\u0442\u044c \u0444\u0430\u0439\u043b: " + file);
            return false;
        }
        return true;
    }
    
    public static List<String> readTextFileAllLines(final String file) {
        try {
            final Path path = Paths.get(file, new String[0]);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            System.out.println("\u041d\u0435 \u0443\u0434\u0430\u0435\u0442\u0441\u044f \u043f\u0440\u043e\u0447\u0438\u0442\u0430\u0442\u044c \u0444\u0430\u0439\u043b: " + file);
            appendTextFile("", file);
            return Collections.emptyList();
        }
    }
    
    private String[] expandPath(final String fullPath) {
        return fullPath.split(":?\\\\\\\\|\\/");
    }
    
    private Stream<String> expandPaths(final String... paths) {
        return Arrays.stream(paths).map((Function<? super String, ?>)this::expandPath).flatMap((Function<? super Object, ? extends Stream<? extends String>>)Arrays::stream);
    }
    
    private Path lookupPath(final Path root, final String... paths) {
        return Paths.get(root.toString(), paths);
    }
    
    private Path getRoot() {
        return Paths.get("", new String[0]);
    }
    
    private void createDirectory(final Path dir) {
        try {
            if (!Files.isDirectory(dir, new LinkOption[0])) {
                if (Files.exists(dir, new LinkOption[0])) {
                    Files.delete(dir);
                }
                Files.createDirectories(dir, (FileAttribute<?>[])new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Path getMkDirectory(final Path parent, final String... paths) {
        if (paths.length < 1) {
            return parent;
        }
        final Path dir = this.lookupPath(parent, paths);
        this.createDirectory(dir);
        return dir;
    }
    
    public Path getBasePath() {
        return this.base;
    }
    
    public Path getBaseResolve(final String... paths) {
        final String[] names = this.expandPaths(paths).toArray(String[]::new);
        if (names.length < 1) {
            throw new IllegalArgumentException("missing path");
        }
        return this.lookupPath(this.getBasePath(), names);
    }
    
    public Path getMkBaseResolve(final String... paths) {
        final Path path = this.getBaseResolve(paths);
        this.createDirectory(path.getParent());
        return path;
    }
    
    public Path getConfig() {
        return this.getBasePath().resolve("config");
    }
    
    public Path getCache() {
        return this.getBasePath().resolve("cache");
    }
    
    public Path getNotebot() {
        return this.getBasePath().resolve("tmp");
    }
    
    public Path getCustomBitch() {
        return this.getBasePath().resolve("customimage");
    }
    
    public Path getMkBaseDirectory(final String... names) {
        return this.getMkDirectory(this.getBasePath(), this.expandPaths(names).collect(Collectors.joining(File.separator)));
    }
    
    public Path getMkConfigDirectory(final String... names) {
        return this.getMkDirectory(this.getConfig(), this.expandPaths(names).collect(Collectors.joining(File.separator)));
    }
}
