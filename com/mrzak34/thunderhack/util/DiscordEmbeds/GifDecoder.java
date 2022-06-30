//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.DiscordEmbeds;

import java.io.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;

public final class GifDecoder
{
    static final boolean DEBUG_MODE = false;
    
    public static final GifImage read(final byte[] in) throws IOException {
        final GifDecoder decoder = new GifDecoder();
        final GifImage img = decoder.new GifImage();
        GifFrame frame = null;
        int pos = readHeader(in, img);
        pos = readLogicalScreenDescriptor(img, in, pos);
        if (img.hasGlobColTbl) {
            img.globalColTbl = new int[img.sizeOfGlobColTbl];
            pos = readColTbl(in, img.globalColTbl, pos);
        }
        while (pos < in.length) {
            final int block = in[pos] & 0xFF;
            switch (block) {
                case 33: {
                    if (pos + 1 >= in.length) {
                        throw new IOException("Unexpected end of file.");
                    }
                    switch (in[pos + 1] & 0xFF) {
                        case 254: {
                            pos = readTextExtension(in, pos);
                            continue;
                        }
                        case 255: {
                            pos = readAppExt(img, in, pos);
                            continue;
                        }
                        case 1: {
                            frame = null;
                            pos = readTextExtension(in, pos);
                            continue;
                        }
                        case 249: {
                            if (frame == null) {
                                frame = decoder.new GifFrame();
                                img.frames.add(frame);
                            }
                            pos = readGraphicControlExt(frame, in, pos);
                            continue;
                        }
                        default: {
                            throw new IOException("Unknown extension at " + pos);
                        }
                    }
                    break;
                }
                case 44: {
                    if (frame == null) {
                        frame = decoder.new GifFrame();
                        img.frames.add(frame);
                    }
                    pos = readImgDescr(frame, in, pos);
                    if (frame.hasLocColTbl) {
                        frame.localColTbl = new int[frame.sizeOfLocColTbl];
                        pos = readColTbl(in, frame.localColTbl, pos);
                    }
                    pos = readImgData(frame, in, pos);
                    frame = null;
                    continue;
                }
                case 59: {
                    return img;
                }
                default: {
                    final double progress = 1.0 * pos / in.length;
                    if (progress < 0.9) {
                        throw new IOException("Unknown block at: " + pos);
                    }
                    pos = in.length;
                    continue;
                }
            }
        }
        return img;
    }
    
    public static final GifImage read(final InputStream is) throws IOException {
        final byte[] data = new byte[is.available()];
        is.read(data, 0, data.length);
        return read(data);
    }
    
    static final int readAppExt(final GifImage img, final byte[] in, int i) {
        img.appId = new String(in, i + 3, 8);
        img.appAuthCode = new String(in, i + 11, 3);
        i += 14;
        final int subBlockSize = in[i] & 0xFF;
        if (subBlockSize == 3) {
            img.repetitions = ((in[i + 2] & 0xFF) | (in[i + 3] & 0xFF00));
            return i + 5;
        }
        while ((in[i] & 0xFF) != 0x0) {
            i += (in[i] & 0xFF) + 1;
        }
        return i + 1;
    }
    
    static final int readColTbl(final byte[] in, final int[] colors, int i) {
        for (int numColors = colors.length, c = 0; c < numColors; ++c) {
            final int a = 255;
            final int r = in[i++] & 0xFF;
            final int g = in[i++] & 0xFF;
            final int b = in[i++] & 0xFF;
            colors[c] = (((0xFF00 | r) << 8 | g) << 8 | b);
        }
        return i;
    }
    
    static final int readGraphicControlExt(final GifFrame fr, final byte[] in, final int i) {
        fr.disposalMethod = (in[i + 3] & 0x1C) >>> 2;
        fr.transpColFlag = ((in[i + 3] & 0x1) == 0x1);
        fr.delay = ((in[i + 4] & 0xFF) | (in[i + 5] & 0xFF) << 8);
        fr.transpColIndex = (in[i + 6] & 0xFF);
        return i + 8;
    }
    
    static final int readHeader(final byte[] in, final GifImage img) throws IOException {
        if (in.length < 6) {
            throw new IOException("Image is truncated.");
        }
        img.header = new String(in, 0, 6);
        if (!img.header.equals("GIF87a") && !img.header.equals("GIF89a")) {
            throw new IOException("Invalid GIF header.");
        }
        return 6;
    }
    
    static final int readImgData(final GifFrame fr, final byte[] in, int i) {
        final int fileSize = in.length;
        final int minCodeSize = in[i++] & 0xFF;
        final int clearCode = 1 << minCodeSize;
        fr.firstCodeSize = minCodeSize + 1;
        fr.clearCode = clearCode;
        fr.endOfInfoCode = clearCode + 1;
        final int imgDataSize = readImgDataSize(in, i);
        final byte[] imgData = new byte[imgDataSize + 2];
        int imgDataPos = 0;
        int subBlockSize = in[i] & 0xFF;
        while (subBlockSize > 0) {
            try {
                final int nextSubBlockSizePos = i + subBlockSize + 1;
                final int nextSubBlockSize = in[nextSubBlockSizePos] & 0xFF;
                System.arraycopy(in, i + 1, imgData, imgDataPos, subBlockSize);
                imgDataPos += subBlockSize;
                i = nextSubBlockSizePos;
                subBlockSize = nextSubBlockSize;
                continue;
            }
            catch (Exception e) {
                subBlockSize = fileSize - i - 1;
                System.arraycopy(in, i + 1, imgData, imgDataPos, subBlockSize);
                imgDataPos += subBlockSize;
                i += subBlockSize + 1;
            }
            break;
        }
        fr.data = imgData;
        return ++i;
    }
    
    static final int readImgDataSize(final byte[] in, int i) {
        final int fileSize = in.length;
        int imgDataPos = 0;
        int subBlockSize = in[i] & 0xFF;
        while (subBlockSize > 0) {
            try {
                final int nextSubBlockSizePos = i + subBlockSize + 1;
                final int nextSubBlockSize = in[nextSubBlockSizePos] & 0xFF;
                imgDataPos += subBlockSize;
                i = nextSubBlockSizePos;
                subBlockSize = nextSubBlockSize;
                continue;
            }
            catch (Exception e) {
                subBlockSize = fileSize - i - 1;
                imgDataPos += subBlockSize;
            }
            break;
        }
        return imgDataPos;
    }
    
    static final int readImgDescr(final GifFrame fr, final byte[] in, int i) {
        fr.x = ((in[++i] & 0xFF) | (in[++i] & 0xFF) << 8);
        fr.y = ((in[++i] & 0xFF) | (in[++i] & 0xFF) << 8);
        fr.w = ((in[++i] & 0xFF) | (in[++i] & 0xFF) << 8);
        fr.h = ((in[++i] & 0xFF) | (in[++i] & 0xFF) << 8);
        fr.wh = fr.w * fr.h;
        final byte b = in[++i];
        fr.hasLocColTbl = ((b & 0x80) >>> 7 == 1);
        fr.interlaceFlag = ((b & 0x40) >>> 6 == 1);
        fr.sortFlag = ((b & 0x20) >>> 5 == 1);
        final int colTblSizePower = (b & 0x7) + 1;
        fr.sizeOfLocColTbl = 1 << colTblSizePower;
        return ++i;
    }
    
    static final int readLogicalScreenDescriptor(final GifImage img, final byte[] in, final int i) {
        img.w = ((in[i] & 0xFF) | (in[i + 1] & 0xFF) << 8);
        img.h = ((in[i + 2] & 0xFF) | (in[i + 3] & 0xFF) << 8);
        img.wh = img.w * img.h;
        final byte b = in[i + 4];
        img.hasGlobColTbl = ((b & 0x80) >>> 7 == 1);
        final int colResPower = ((b & 0x70) >>> 4) + 1;
        img.colorResolution = 1 << colResPower;
        img.sortFlag = ((b & 0x8) >>> 3 == 1);
        final int globColTblSizePower = (b & 0x7) + 1;
        img.sizeOfGlobColTbl = 1 << globColTblSizePower;
        img.bgColIndex = (in[i + 5] & 0xFF);
        img.pxAspectRatio = (in[i + 6] & 0xFF);
        return i + 7;
    }
    
    static final int readTextExtension(final byte[] in, final int pos) {
        int i = pos + 2;
        for (int subBlockSize = in[i++] & 0xFF; subBlockSize != 0 && i < in.length; i += subBlockSize, subBlockSize = (in[i++] & 0xFF)) {}
        return i;
    }
    
    static final class BitReader
    {
        private int bitPos;
        private int numBits;
        private int bitMask;
        private byte[] in;
        
        private final void init(final byte[] in) {
            this.in = in;
            this.bitPos = 0;
        }
        
        private final int read() {
            int i = this.bitPos >>> 3;
            final int rBits = this.bitPos & 0x7;
            final int b0 = this.in[i++] & 0xFF;
            final int b2 = this.in[i++] & 0xFF;
            final int b3 = this.in[i] & 0xFF;
            final int buf = ((b3 << 8 | b2) << 8 | b0) >>> rBits;
            this.bitPos += this.numBits;
            return buf & this.bitMask;
        }
        
        private final void setNumBits(final int numBits) {
            this.numBits = numBits;
            this.bitMask = (1 << numBits) - 1;
        }
    }
    
    static final class CodeTable
    {
        private final int[][] tbl;
        private int initTableSize;
        private int initCodeSize;
        private int initCodeLimit;
        private int codeSize;
        private int nextCode;
        private int nextCodeLimit;
        private BitReader br;
        
        public CodeTable() {
            this.tbl = new int[4096][1];
        }
        
        private final int add(final int[] indices) {
            if (this.nextCode < 4096) {
                if (this.nextCode == this.nextCodeLimit && this.codeSize < 12) {
                    ++this.codeSize;
                    this.br.setNumBits(this.codeSize);
                    this.nextCodeLimit = (1 << this.codeSize) - 1;
                }
                this.tbl[this.nextCode++] = indices;
            }
            return this.codeSize;
        }
        
        private final int clear() {
            this.codeSize = this.initCodeSize;
            this.br.setNumBits(this.codeSize);
            this.nextCodeLimit = this.initCodeLimit;
            this.nextCode = this.initTableSize;
            return this.codeSize;
        }
        
        private final void init(final GifFrame fr, final int[] activeColTbl, final BitReader br) {
            this.br = br;
            final int numColors = activeColTbl.length;
            this.initCodeSize = fr.firstCodeSize;
            this.initCodeLimit = (1 << this.initCodeSize) - 1;
            this.initTableSize = fr.endOfInfoCode + 1;
            this.nextCode = this.initTableSize;
            for (int c = numColors - 1; c >= 0; --c) {
                this.tbl[c][0] = activeColTbl[c];
            }
            this.tbl[fr.clearCode] = new int[] { fr.clearCode };
            this.tbl[fr.endOfInfoCode] = new int[] { fr.endOfInfoCode };
            if (fr.transpColFlag && fr.transpColIndex < numColors) {
                this.tbl[fr.transpColIndex][0] = 0;
            }
        }
    }
    
    final class GifFrame
    {
        private int disposalMethod;
        private boolean transpColFlag;
        private int delay;
        private int transpColIndex;
        private int x;
        private int y;
        private int w;
        private int h;
        private int wh;
        private boolean hasLocColTbl;
        private boolean interlaceFlag;
        private boolean sortFlag;
        private int sizeOfLocColTbl;
        private int[] localColTbl;
        private int firstCodeSize;
        private int clearCode;
        private int endOfInfoCode;
        private byte[] data;
        private BufferedImage img;
    }
    
    public final class GifImage
    {
        public String header;
        private int w;
        private int h;
        private int wh;
        public boolean hasGlobColTbl;
        public int colorResolution;
        public boolean sortFlag;
        public int sizeOfGlobColTbl;
        public int bgColIndex;
        public int pxAspectRatio;
        public int[] globalColTbl;
        private final List<GifFrame> frames;
        public String appId;
        public String appAuthCode;
        public int repetitions;
        private BufferedImage img;
        private int[] prevPx;
        private final BitReader bits;
        private final CodeTable codes;
        private Graphics2D g;
        
        public GifImage() {
            this.frames = new ArrayList<GifFrame>(64);
            this.appId = "";
            this.appAuthCode = "";
            this.repetitions = 0;
            this.img = null;
            this.prevPx = null;
            this.bits = new BitReader();
            this.codes = new CodeTable();
        }
        
        private final int[] decode(final GifFrame fr, final int[] activeColTbl) {
            this.codes.init(fr, activeColTbl, this.bits);
            this.bits.init(fr.data);
            final int clearCode = fr.clearCode;
            final int endCode = fr.endOfInfoCode;
            final int[] out = new int[this.wh];
            final int[][] tbl = this.codes.tbl;
            int outPos = 0;
            this.codes.clear();
            int code = this.bits.read();
            if (code == clearCode) {
                code = this.bits.read();
            }
            int[] pixels = tbl[code];
            System.arraycopy(pixels, 0, out, outPos, pixels.length);
            outPos += pixels.length;
            try {
                while (true) {
                    final int prevCode = code;
                    code = this.bits.read();
                    if (code == clearCode) {
                        this.codes.clear();
                        code = this.bits.read();
                        pixels = tbl[code];
                        System.arraycopy(pixels, 0, out, outPos, pixels.length);
                        outPos += pixels.length;
                    }
                    else {
                        if (code == endCode) {
                            break;
                        }
                        final int[] prevVals = tbl[prevCode];
                        final int[] prevValsAndK = new int[prevVals.length + 1];
                        System.arraycopy(prevVals, 0, prevValsAndK, 0, prevVals.length);
                        if (code < this.codes.nextCode) {
                            pixels = tbl[code];
                            System.arraycopy(pixels, 0, out, outPos, pixels.length);
                            outPos += pixels.length;
                            prevValsAndK[prevVals.length] = tbl[code][0];
                        }
                        else {
                            prevValsAndK[prevVals.length] = prevVals[0];
                            System.arraycopy(prevValsAndK, 0, out, outPos, prevValsAndK.length);
                            outPos += prevValsAndK.length;
                        }
                        this.codes.add(prevValsAndK);
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException ex) {}
            return out;
        }
        
        private final int[] deinterlace(final int[] src, final GifFrame fr) {
            final int w = fr.w;
            final int h = fr.h;
            final int wh = fr.wh;
            final int[] dest = new int[src.length];
            final int set2Y = h + 7 >>> 3;
            final int set3Y = set2Y + (h + 3 >>> 3);
            final int set4Y = set3Y + (h + 1 >>> 2);
            final int set2 = w * set2Y;
            final int set3 = w * set3Y;
            final int set4 = w * set4Y;
            final int w2 = w << 1;
            final int w3 = w2 << 1;
            final int w4 = w3 << 1;
            int from = 0;
            for (int to = 0; from < set2; from += w, to += w4) {
                System.arraycopy(src, from, dest, to, w);
            }
            for (int to = w3; from < set3; from += w, to += w4) {
                System.arraycopy(src, from, dest, to, w);
            }
            for (int to = w2; from < set4; from += w, to += w3) {
                System.arraycopy(src, from, dest, to, w);
            }
            for (int to = w; from < wh; from += w, to += w2) {
                System.arraycopy(src, from, dest, to, w);
            }
            return dest;
        }
        
        private final void drawFrame(final GifFrame fr) {
            final int[] activeColTbl = fr.hasLocColTbl ? fr.localColTbl : this.globalColTbl;
            int[] pixels = this.decode(fr, activeColTbl);
            if (fr.interlaceFlag) {
                pixels = this.deinterlace(pixels, fr);
            }
            final BufferedImage frame = new BufferedImage(fr.w, fr.h, 2);
            System.arraycopy(pixels, 0, ((DataBufferInt)frame.getRaster().getDataBuffer()).getData(), 0, fr.wh);
            this.g.drawImage(frame, fr.x, fr.y, null);
            this.prevPx = new int[this.wh];
            System.arraycopy(((DataBufferInt)this.img.getRaster().getDataBuffer()).getData(), 0, this.prevPx, 0, this.wh);
            fr.img = new BufferedImage(this.w, this.h, 2);
            System.arraycopy(this.prevPx, 0, ((DataBufferInt)fr.img.getRaster().getDataBuffer()).getData(), 0, this.wh);
            if (fr.disposalMethod == 2) {
                this.g.clearRect(fr.x, fr.y, fr.w, fr.h);
            }
            else if (fr.disposalMethod == 3 && this.prevPx != null) {
                System.arraycopy(this.prevPx, 0, ((DataBufferInt)this.img.getRaster().getDataBuffer()).getData(), 0, this.wh);
            }
        }
        
        public final int getBackgroundColor() {
            final GifFrame frame = this.frames.get(0);
            if (frame.hasLocColTbl) {
                return frame.localColTbl[this.bgColIndex];
            }
            if (this.hasGlobColTbl) {
                return this.globalColTbl[this.bgColIndex];
            }
            return 0;
        }
        
        public final int getDelay(final int index) {
            return this.frames.get(index).delay;
        }
        
        public final BufferedImage getFrame(final int index) {
            if (this.img == null) {
                this.img = new BufferedImage(this.w, this.h, 2);
                (this.g = this.img.createGraphics()).setBackground(new Color(0, true));
            }
            GifFrame fr = this.frames.get(index);
            if (fr.img == null) {
                for (int i = 0; i <= index; ++i) {
                    fr = this.frames.get(i);
                    if (fr.img == null) {
                        this.drawFrame(fr);
                    }
                }
            }
            return fr.img;
        }
        
        public final int getFrameCount() {
            return this.frames.size();
        }
        
        public final int getHeight() {
            return this.h;
        }
        
        public final int getWidth() {
            return this.w;
        }
    }
}
