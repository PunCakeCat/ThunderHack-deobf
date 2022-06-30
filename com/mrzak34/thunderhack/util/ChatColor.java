//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import java.util.regex.*;

public enum ChatColor
{
    BLACK('0'), 
    DARK_BLUE('1'), 
    DARK_GREEN('2'), 
    DARK_AQUA('3'), 
    DARK_RED('4'), 
    DARK_PURPLE('5'), 
    GOLD('6'), 
    GRAY('7'), 
    DARK_GRAY('8'), 
    BLUE('9'), 
    GREEN('a'), 
    AQUA('b'), 
    RED('c'), 
    LIGHT_PURPLE('d'), 
    YELLOW('e'), 
    WHITE('f'), 
    MAGIC('k', true), 
    BOLD('l', true), 
    STRIKETHROUGH('m', true), 
    UNDERLINE('n', true), 
    ITALIC('o', true), 
    RESET('r');
    
    public static final char COLOR_CHAR = '�';
    public static final Pattern PATTERN;
    public static final int[] CODES;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    
    private ChatColor(final char code) {
        this(code, false);
    }
    
    private ChatColor(final char code, final boolean isFormat) {
        this.code = code;
        this.isFormat = isFormat;
        this.toString = new String(new char[] { '�', code });
    }
    
    public static String stripColor(final String input) {
        return (input == null) ? null : Pattern.compile("(?i)�[0-9A-FK-OR]").matcher(input).replaceAll("");
    }
    
    public static String translateAlternateColorCodes(final char altColorChar, final String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();
        for (int bound = b.length - 1, i = 0; i < bound; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '�';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
    
    public char getChar() {
        return this.code;
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    public boolean isFormat() {
        return this.isFormat;
    }
    
    public boolean isColor() {
        return !this.isFormat && this != ChatColor.RESET;
    }
    
    static {
        PATTERN = Pattern.compile("�[0123456789abcdefklmnor]");
        CODES = new int[] { 0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215 };
    }
}
