//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.*;
import java.io.*;
import javax.imageio.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.fml.client.*;
import java.awt.image.*;
import com.mrzak34.thunderhack.features.modules.misc.*;

public class SkinStorageManipulationer
{
    public static ResourceLocation getTexture2(final String name, final String format) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/friendsAvatars/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        return wr.location;
    }
    
    public static ResourceLocation getTexture3(final String name, final String format) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/skins/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        return wr.location;
    }
    
    public static ResourceLocation getTexture(final String name, final String format) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/tmp/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final float ratio = bufferedImage.getWidth() / (float)bufferedImage.getHeight();
        ChatTweaks.getInstance().nigw = (float)(int)ChatTweaks.getInstance().multip.getValue();
        ChatTweaks.getInstance().nigh = (int)ChatTweaks.getInstance().multip.getValue() / ratio;
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        return wr.location;
    }
    
    public static ResourceLocation getTexture(final String name) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/tmp/" + name));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name, texture));
        return wr.location;
    }
    
    public static class WrappedResource
    {
        public final ResourceLocation location;
        
        public WrappedResource(final ResourceLocation location) {
            this.location = location;
        }
    }
}
