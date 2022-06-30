//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.client.resources.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import org.apache.commons.io.*;
import net.minecraft.client.resources.data.*;
import net.minecraft.util.*;
import java.io.*;

public class MotionBlurResource implements IResource
{
    public InputStream getInputStream() {
        final double amount = 0.7 + (float)((MotionBlur)Thunderhack.moduleManager.getModuleByClass((Class)MotionBlur.class)).amount.getValue() / 100.0 * 3.0 - 0.01;
        return IOUtils.toInputStream(String.format(Locale.ENGLISH, "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f, %.2f, %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}", amount, amount, amount));
    }
    
    public boolean hasMetadata() {
        return false;
    }
    
    public IMetadataSection getMetadata(final String metadata) {
        return null;
    }
    
    public ResourceLocation getResourceLocation() {
        return null;
    }
    
    public String getResourcePackName() {
        return null;
    }
    
    public void close() throws IOException {
    }
}
