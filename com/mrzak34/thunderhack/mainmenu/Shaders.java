//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mainmenu;

import java.util.*;
import java.io.*;

public class Shaders
{
    public GLSLSandboxShader currentshader;
    public long time;
    
    public void init() {
        try {
            final Object[] shader = this.getRandomShader();
            if (shader == null) {
                this.currentshader = null;
                System.out.println("No shaders found in .minecraft/glslmenu. Not drawing anything");
            }
            else {
                final String name = (String)shader[0];
                final InputStream is = (InputStream)shader[1];
                this.currentshader = new GLSLSandboxShader(name, is);
                if (!this.currentshader.initialized) {
                    this.currentshader = null;
                }
                else {
                    this.time = System.currentTimeMillis();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.currentshader = null;
        }
    }
    
    public Object[] getRandomShader() throws FileNotFoundException {
        final File folder = new File("glslmenu");
        if (!folder.exists()) {
            return null;
        }
        final List<String> shaders = new ArrayList<String>();
        for (final File file : folder.listFiles()) {
            final String name = file.getName();
            if (name.endsWith(".vsh")) {
                shaders.add(name);
            }
        }
        if (shaders.size() == 0) {
            return null;
        }
        final String randomname = shaders.get(new Random().nextInt(shaders.size()));
        final FileInputStream fis = new FileInputStream(new File("glslmenu/" + randomname));
        return new Object[] { randomname, fis };
    }
}
