//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import net.minecraft.client.network.*;

public class ThunderUtils
{
    public static void saveUserAvatar(final String s, final String nickname) {
        try {
            final URL url = new URL(s);
            final URLConnection openConnection = url.openConnection();
            boolean check = true;
            try {
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                if (openConnection.getContentLength() > 8000000) {
                    System.out.println(" file size is too big.");
                    check = false;
                }
            }
            catch (Exception e) {
                System.out.println("Couldn't create a connection to the link, please recheck the link.");
                check = false;
                e.printStackTrace();
            }
            if (check) {
                BufferedImage img = null;
                try {
                    final InputStream in = new BufferedInputStream(openConnection.getInputStream());
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    final byte[] response = out.toByteArray();
                    img = ImageIO.read(new ByteArrayInputStream(response));
                }
                catch (Exception e2) {
                    System.out.println(" couldn't read an image from this link.");
                    e2.printStackTrace();
                }
                try {
                    ImageIO.write(img, "png", new File("ThunderHack/friendsAvatars/" + nickname + ".png"));
                }
                catch (IOException e3) {
                    System.out.println("Couldn't create/send the output image.");
                    e3.printStackTrace();
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static String solvename(final String notsolved) {
        final AtomicReference<String> mb = new AtomicReference<String>("err");
        final AtomicReference<String> atomicReference;
        Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfoMap().forEach(player -> {
            if (notsolved.contains(player.getGameProfile().getName())) {
                atomicReference.set(player.getGameProfile().getName());
            }
            return;
        });
        return mb.get();
    }
    
    public static void savePlayerSkin(final String s, final String nickname) {
        try {
            final URL url = new URL(s);
            final URLConnection openConnection = url.openConnection();
            boolean check = true;
            try {
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                if (openConnection.getContentLength() > 8000000) {
                    System.out.println(" file size is too big.");
                    check = false;
                }
            }
            catch (Exception e) {
                System.out.println("Couldn't create a connection to the link, please recheck the link.");
                check = false;
                e.printStackTrace();
            }
            if (check) {
                BufferedImage img = null;
                try {
                    final InputStream in = new BufferedInputStream(openConnection.getInputStream());
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    final byte[] response = out.toByteArray();
                    img = ImageIO.read(new ByteArrayInputStream(response));
                }
                catch (Exception e2) {
                    System.out.println(" couldn't read an image from this link.");
                    e2.printStackTrace();
                }
                try {
                    ImageIO.write(img, "png", new File("ThunderHack/skins/" + nickname + ".png"));
                }
                catch (IOException e3) {
                    System.out.println("Couldn't create/send the output image.");
                    e3.printStackTrace();
                }
            }
        }
        catch (Exception ex) {}
    }
}
