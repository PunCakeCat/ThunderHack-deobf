//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.setting;

import net.minecraft.block.*;
import java.util.*;

public class BlockListSetting
{
    private List<Block> blocks;
    private List<String> blocksString;
    
    public BlockListSetting(final String... blockNames) {
        this.blocks = new ArrayList<Block>();
        this.blocksString = new ArrayList<String>();
        for (final String name : blockNames) {
            if (!this.blocksString.contains(name.toUpperCase(Locale.ENGLISH)) && Block.getBlockFromName(name) != null) {
                this.blocksString.add(name.toUpperCase(Locale.ENGLISH));
            }
        }
    }
    
    public BlockListSetting(final ArrayList<String> blockNames) {
        this.blocks = new ArrayList<Block>();
        this.blocksString = new ArrayList<String>();
        for (final String name : blockNames) {
            if (!this.blocksString.contains(name.toUpperCase(Locale.ENGLISH)) && Block.getBlockFromName(name) != null) {
                this.blocksString.add(name.toUpperCase(Locale.ENGLISH));
            }
        }
    }
    
    public void addBlockStrings(final ArrayList<String> blockNames) {
        for (final String name : blockNames) {
            if (!this.blocksString.contains(name.toUpperCase(Locale.ENGLISH)) && Block.getBlockFromName(name) != null) {
                this.blocksString.add(name.toUpperCase(Locale.ENGLISH));
            }
        }
    }
    
    public boolean addBlock(final String blockName) {
        if (!this.blocksString.contains(blockName.toUpperCase(Locale.ENGLISH)) && Block.getBlockFromName(blockName) != null) {
            this.blocksString.add(blockName.toUpperCase(Locale.ENGLISH));
            return true;
        }
        return false;
    }
    
    public boolean removeBlock(final String blockName) {
        return this.blocksString.remove(blockName.toUpperCase(Locale.ENGLISH));
    }
    
    public void refreshBlocks() {
        this.blocks.clear();
        final Block block;
        this.blocksString.forEach(str -> {
            block = Block.getBlockFromName(str);
            if (block != null) {
                this.blocks.add(block);
            }
        });
    }
    
    public List<String> getBlocksAsString() {
        final List<String> str = new ArrayList<String>();
        final String path;
        final List<String> list;
        this.blocks.forEach(block -> {
            path = block.getRegistryName().getPath();
            if (path != null) {
                list.add(path);
            }
            return;
        });
        return str;
    }
    
    public List<Block> getBlocks() {
        return this.blocks;
    }
}
