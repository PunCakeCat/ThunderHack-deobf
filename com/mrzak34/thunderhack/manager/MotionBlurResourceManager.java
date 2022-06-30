//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import com.mrzak34.thunderhack.util.*;
import java.io.*;
import java.util.*;

public class MotionBlurResourceManager implements IResourceManager
{
    public Set<String> getResourceDomains() {
        return null;
    }
    
    public IResource getResource(final ResourceLocation location) throws IOException {
        return (IResource)new MotionBlurResource();
    }
    
    public List<IResource> getAllResources(final ResourceLocation location) {
        return null;
    }
}
