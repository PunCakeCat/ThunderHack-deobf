//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.shaders.impl.outline.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.util.shaders.impl.fill.*;
import java.awt.*;
import java.util.function.*;

public class ItemShaders extends Module
{
    private static ItemShaders INSTANCE;
    public Setting<fillShadermode> fillShader;
    public Setting<glowESPmode> glowESP;
    private Setting<Boolean> rangeCheck;
    private Setting<Boolean> fadeFill;
    private Setting<Boolean> fadeOutline;
    private Setting<Boolean> GradientAlpha;
    public Setting<Float> duplicateOutline;
    public Setting<Float> duplicateFill;
    public Setting<Float> speedOutline;
    public Setting<Float> speedFill;
    public Setting<Float> rad;
    public Setting<Float> PI;
    public Setting<Float> saturationFill;
    public Setting<Float> distfadingFill;
    public Setting<Float> titleFill;
    public Setting<Float> stepSizeFill;
    public Setting<Float> volumStepsFill;
    public Setting<Float> zoomFill;
    public Setting<Float> formuparam2Fill;
    public Setting<Float> saturationOutline;
    public Setting<Integer> iterationsFill;
    public Setting<Integer> redFill;
    public Setting<Integer> MaxIterFill;
    public Setting<Integer> NUM_OCTAVESFill;
    public Setting<Integer> BSTARTFIll;
    public Setting<Integer> GSTARTFill;
    public Setting<Integer> RSTARTFill;
    public Setting<Integer> WaveLenghtFIll;
    public Setting<Integer> volumStepsOutline;
    public Setting<Integer> iterationsOutline;
    public Setting<Integer> redOutline;
    public Setting<Integer> MaxIterOutline;
    public Setting<Integer> NUM_OCTAVESOutline;
    public Setting<Integer> BSTARTOutline;
    public Setting<Integer> GSTARTOutline;
    public Setting<Integer> RSTARTOutline;
    public Setting<Integer> alphaValue;
    public Setting<Integer> WaveLenghtOutline;
    public Setting<Boolean> cancelItem;
    public Setting<Float> alphaFill;
    public Setting<Float> blueFill;
    public Setting<Float> greenFill;
    public Setting<Float> tauFill;
    public Setting<Float> creepyFill;
    public Setting<Float> moreGradientFill;
    public Setting<Float> distfadingOutline;
    public Setting<Float> titleOutline;
    public Setting<Float> stepSizeOutline;
    public Setting<Float> zoomOutline;
    public Setting<Float> formuparam2Outline;
    public Setting<Float> alphaOutline;
    public Setting<Float> blueOutline;
    public Setting<Float> greenOutline;
    public Setting<Float> tauOutline;
    public Setting<Float> creepyOutline;
    public Setting<Float> moreGradientOutline;
    public Setting<Float> radOutline;
    public Setting<Float> PIOutline;
    public Setting<Float> quality;
    public Setting<Float> radius;
    private final Setting<ColorSetting> colorImgOutline;
    private final Setting<ColorSetting> secondColorImgOutline;
    private final Setting<ColorSetting> thirdColorImgOutline;
    private final Setting<ColorSetting> colorESP;
    private final Setting<ColorSetting> colorImgFill;
    private final Setting<ColorSetting> secondcolorImgFill;
    private final Setting<ColorSetting> thirdcolorImgFill;
    
    public ItemShaders() {
        super("ItemShaders", "ItemShaders", Module.Category.RENDER, true, false, false);
        this.fillShader = (Setting<fillShadermode>)this.register(new Setting("Fill Shader", (T)fillShadermode.None));
        this.glowESP = (Setting<glowESPmode>)this.register(new Setting("Glow ESP", (T)glowESPmode.None));
        this.rangeCheck = (Setting<Boolean>)this.register(new Setting("Range Check", (T)true));
        this.fadeFill = (Setting<Boolean>)this.register(new Setting("Fade Fill", (T)false));
        this.fadeOutline = (Setting<Boolean>)this.register(new Setting("FadeOL Fill", (T)false));
        this.GradientAlpha = (Setting<Boolean>)this.register(new Setting("Gradient Alpha", (T)false));
        this.duplicateOutline = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)0.0f, (T)20.0f));
        this.duplicateFill = (Setting<Float>)this.register(new Setting("Duplicate Fill", (T)1.0f, (T)0.0f, (T)5.0f));
        this.speedOutline = (Setting<Float>)this.register(new Setting("Speed Outline", (T)10.0f, (T)1.0f, (T)100.0f));
        this.speedFill = (Setting<Float>)this.register(new Setting("Speed Fill", (T)10.0f, (T)1.0f, (T)100.0f));
        this.rad = (Setting<Float>)this.register(new Setting("RAD Fill", (T)0.75f, (T)0.0f, (T)5.0f, v -> this.fillShader.getValue() == fillShadermode.Circle));
        this.PI = (Setting<Float>)this.register(new Setting("PI Fill", (T)3.1415927f, (T)0.0f, (T)10.0f, v -> this.fillShader.getValue() == fillShadermode.Circle));
        this.saturationFill = (Setting<Float>)this.register(new Setting("saturation", (T)0.4f, (T)0.0f, (T)3.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.distfadingFill = (Setting<Float>)this.register(new Setting("distfading", (T)0.56f, (T)0.0f, (T)1.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.titleFill = (Setting<Float>)this.register(new Setting("Tile", (T)0.45f, (T)0.0f, (T)1.3f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.stepSizeFill = (Setting<Float>)this.register(new Setting("Step Size", (T)0.19f, (T)0.0f, (T)0.7f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.volumStepsFill = (Setting<Float>)this.register(new Setting("Volum Steps", (T)10.0f, (T)0.0f, (T)10.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.zoomFill = (Setting<Float>)this.register(new Setting("Zoom", (T)3.9f, (T)0.0f, (T)20.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.formuparam2Fill = (Setting<Float>)this.register(new Setting("formuparam2", (T)0.89f, (T)0.0f, (T)1.5f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        final glowESPmode astral;
        final glowESPmode glowESPmode;
        this.saturationOutline = (Setting<Float>)this.register(new Setting("saturation", (T)0.4f, (T)0.0f, (T)3.0f, v -> {
            this.glowESP.getValue();
            astral = ItemShaders.glowESPmode.Astral;
            return glowESPmode == ItemShaders.glowESPmode.Astral;
        }));
        this.iterationsFill = (Setting<Integer>)this.register(new Setting("Iteration", (T)4, (T)3, (T)20, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.redFill = (Setting<Integer>)this.register(new Setting("Tick Regen", (T)0, (T)0, (T)100, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.MaxIterFill = (Setting<Integer>)this.register(new Setting("Max Iter", (T)5, (T)0, (T)30, v -> this.fillShader.getValue() == fillShadermode.Aqua));
        this.NUM_OCTAVESFill = (Setting<Integer>)this.register(new Setting("NUM_OCTAVES", (T)5, (T)1, (T)30, v -> this.fillShader.getValue() == fillShadermode.Smoke));
        this.BSTARTFIll = (Setting<Integer>)this.register(new Setting("BSTART", (T)0, (T)0, (T)1000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.GSTARTFill = (Setting<Integer>)this.register(new Setting("GSTART", (T)0, (T)0, (T)1000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.RSTARTFill = (Setting<Integer>)this.register(new Setting("RSTART", (T)0, (T)0, (T)1000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.WaveLenghtFIll = (Setting<Integer>)this.register(new Setting("Wave Lenght", (T)555, (T)0, (T)2000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.volumStepsOutline = (Setting<Integer>)this.register(new Setting("Volum Steps", (T)10, (T)0, (T)10, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.iterationsOutline = (Setting<Integer>)this.register(new Setting("Iteration", (T)4, (T)3, (T)20, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.redOutline = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)100, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.MaxIterOutline = (Setting<Integer>)this.register(new Setting("Max Iter", (T)5, (T)0, (T)30, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Aqua));
        this.NUM_OCTAVESOutline = (Setting<Integer>)this.register(new Setting("NUM_OCTAVES", (T)5, (T)1, (T)30, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Smoke));
        this.BSTARTOutline = (Setting<Integer>)this.register(new Setting("BSTART", (T)0, (T)0, (T)1000, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.RainbowCube));
        this.GSTARTOutline = (Setting<Integer>)this.register(new Setting("GSTART", (T)0, (T)0, (T)1000, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.RainbowCube));
        this.RSTARTOutline = (Setting<Integer>)this.register(new Setting("RSTART", (T)0, (T)0, (T)1000, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.RainbowCube));
        this.alphaValue = (Setting<Integer>)this.register(new Setting("Alpha Outline", (T)255, (T)0, (T)255, v -> !this.GradientAlpha.getValue()));
        this.WaveLenghtOutline = (Setting<Integer>)this.register(new Setting("Wave Lenght", (T)555, (T)0, (T)2000, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.RainbowCube));
        this.cancelItem = (Setting<Boolean>)this.register(new Setting("Cancel Item", (T)false));
        this.alphaFill = (Setting<Float>)this.register(new Setting("AlphaF", (T)1.0f, (T)0.0f, (T)1.0f, v -> this.fillShader.getValue() == fillShadermode.Astral || this.fillShader.getValue() == fillShadermode.Smoke));
        this.blueFill = (Setting<Float>)this.register(new Setting("BlueF", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.greenFill = (Setting<Float>)this.register(new Setting("GreenF", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.tauFill = (Setting<Float>)this.register(new Setting("TAU", (T)6.2831855f, (T)0.0f, (T)20.0f, v -> this.fillShader.getValue() == fillShadermode.Aqua));
        this.creepyFill = (Setting<Float>)this.register(new Setting("Creepy", (T)1.0f, (T)0.0f, (T)20.0f, v -> this.fillShader.getValue() == fillShadermode.Smoke));
        this.moreGradientFill = (Setting<Float>)this.register(new Setting("More Gradient", (T)1.0f, (T)0.0f, (T)10.0f, v -> this.fillShader.getValue() == fillShadermode.Smoke));
        this.distfadingOutline = (Setting<Float>)this.register(new Setting("distfading", (T)0.56f, (T)0.0f, (T)1.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.titleOutline = (Setting<Float>)this.register(new Setting("Tile", (T)0.45f, (T)0.0f, (T)1.3f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.stepSizeOutline = (Setting<Float>)this.register(new Setting("Step Size", (T)0.2f, (T)0.1f, (T)1.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.zoomOutline = (Setting<Float>)this.register(new Setting("Zoom", (T)3.9f, (T)0.0f, (T)20.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.formuparam2Outline = (Setting<Float>)this.register(new Setting("formuparam2", (T)0.89f, (T)0.0f, (T)1.5f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.alphaOutline = (Setting<Float>)this.register(new Setting("Alpha", (T)1.0f, (T)0.0f, (T)1.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral || this.glowESP.getValue() == ItemShaders.glowESPmode.Gradient));
        this.blueOutline = (Setting<Float>)this.register(new Setting("Blue", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.greenOutline = (Setting<Float>)this.register(new Setting("Green", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Astral));
        this.tauOutline = (Setting<Float>)this.register(new Setting("TAU", (T)6.2831855f, (T)0.0f, (T)20.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Aqua));
        this.creepyOutline = (Setting<Float>)this.register(new Setting("Creepy", (T)1.0f, (T)0.0f, (T)20.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Gradient));
        this.moreGradientOutline = (Setting<Float>)this.register(new Setting("More Gradient", (T)1.0f, (T)0.0f, (T)10.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Gradient));
        this.radOutline = (Setting<Float>)this.register(new Setting("RAD Outline", (T)0.75f, (T)0.0f, (T)5.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Circle));
        this.PIOutline = (Setting<Float>)this.register(new Setting("PI Outline", (T)3.1415927f, (T)0.0f, (T)10.0f, v -> this.glowESP.getValue() == ItemShaders.glowESPmode.Circle));
        this.quality = (Setting<Float>)this.register(new Setting("quality", (T)1.0f, (T)0.0f, (T)20.0f));
        this.radius = (Setting<Float>)this.register(new Setting("radius", (T)1.0f, (T)0.0f, (T)5.0f));
        this.colorImgOutline = (Setting<ColorSetting>)this.register(new Setting("colorImgOutline", (T)new ColorSetting(-2013200640)));
        this.secondColorImgOutline = (Setting<ColorSetting>)this.register(new Setting("secondColorImgOutline", (T)new ColorSetting(-2013200640)));
        this.thirdColorImgOutline = (Setting<ColorSetting>)this.register(new Setting("thirdColorImgOutline", (T)new ColorSetting(-2013200640)));
        this.colorESP = (Setting<ColorSetting>)this.register(new Setting("colorESP", (T)new ColorSetting(-2013200640)));
        this.colorImgFill = (Setting<ColorSetting>)this.register(new Setting("colorImgFill", (T)new ColorSetting(-2013200640)));
        this.secondcolorImgFill = (Setting<ColorSetting>)this.register(new Setting("secondcolorImgFill", (T)new ColorSetting(-2013200640)));
        this.thirdcolorImgFill = (Setting<ColorSetting>)this.register(new Setting("thirdcolorImgFill", (T)new ColorSetting(-2013200640)));
        this.setInstance();
    }
    
    public static ItemShaders getInstance() {
        if (ItemShaders.INSTANCE == null) {
            ItemShaders.INSTANCE = new ItemShaders();
        }
        return ItemShaders.INSTANCE;
    }
    
    private void setInstance() {
        ItemShaders.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHand.PreOutline event) {
        if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        switch (this.glowESP.getValue()) {
            case Color: {
                GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case RainbowCube: {
                RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Gradient: {
                GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Astral: {
                AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Aqua: {
                AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Circle: {
                CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Smoke: {
                SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHand.PostOutline event) {
        if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
            return;
        }
        switch (this.glowESP.getValue()) {
            case Color: {
                GlowShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue());
                break;
            }
            case RainbowCube: {
                RainbowCubeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.colorImgOutline.getValue().getColorObject(), this.WaveLenghtOutline.getValue(), this.RSTARTOutline.getValue(), this.GSTARTOutline.getValue(), this.BSTARTOutline.getValue());
                RainbowCubeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Gradient: {
                GradientOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.moreGradientOutline.getValue(), this.creepyOutline.getValue(), this.alphaOutline.getValue(), this.NUM_OCTAVESOutline.getValue());
                GradientOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Astral: {
                AstralOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.redOutline.getValue(), this.greenOutline.getValue(), this.blueOutline.getValue(), this.alphaOutline.getValue(), this.iterationsOutline.getValue(), this.formuparam2Outline.getValue(), this.zoomOutline.getValue(), this.volumStepsOutline.getValue(), this.stepSizeOutline.getValue(), this.titleOutline.getValue(), this.distfadingOutline.getValue(), this.saturationOutline.getValue(), 0.0f, ((boolean)this.fadeOutline.getValue()) ? 1 : 0);
                AstralOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Aqua: {
                AquaOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.MaxIterOutline.getValue(), this.tauOutline.getValue());
                AquaOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Circle: {
                CircleOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.PIOutline.getValue(), this.radOutline.getValue());
                CircleOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Smoke: {
                SmokeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.secondColorImgOutline.getValue().getColorObject(), this.thirdColorImgOutline.getValue().getColorObject(), this.NUM_OCTAVESOutline.getValue());
                SmokeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHand.PreFill event) {
        if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        switch (this.fillShader.getValue()) {
            case Astral: {
                FlowShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Aqua: {
                AquaShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Smoke: {
                SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case RainbowCube: {
                RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Gradient: {
                GradientShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Fill: {
                FillShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Circle: {
                CircleShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Phobos: {
                PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHand.PostFill event) {
        if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
            return;
        }
        switch (this.fillShader.getValue()) {
            case Astral: {
                FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.redFill.getValue(), this.greenFill.getValue(), this.blueFill.getValue(), this.alphaFill.getValue(), this.iterationsFill.getValue(), this.formuparam2Fill.getValue(), this.zoomFill.getValue(), this.volumStepsFill.getValue(), this.stepSizeFill.getValue(), this.titleFill.getValue(), this.distfadingFill.getValue(), this.saturationFill.getValue(), 0.0f, ((boolean)this.fadeFill.getValue()) ? 1 : 0);
                FlowShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Aqua: {
                AquaShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                AquaShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Smoke: {
                SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.secondcolorImgFill.getValue().getColorObject(), this.thirdcolorImgFill.getValue().getColorObject(), this.NUM_OCTAVESFill.getValue());
                SmokeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case RainbowCube: {
                RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.WaveLenghtFIll.getValue(), this.RSTARTFill.getValue(), this.GSTARTFill.getValue(), this.BSTARTFIll.getValue());
                RainbowCubeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Gradient: {
                GradientShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.moreGradientFill.getValue(), this.creepyFill.getValue(), this.alphaFill.getValue(), this.NUM_OCTAVESFill.getValue());
                GradientShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Fill: {
                FillShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject());
                FillShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Circle: {
                CircleShader.INSTANCE.stopDraw(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.PI.getValue(), this.rad.getValue());
                CircleShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Phobos: {
                PhobosShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                PhobosShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHand.PreBoth event) {
        if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        switch (this.glowESP.getValue()) {
            case Color: {
                GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case RainbowCube: {
                RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Gradient: {
                GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Astral: {
                AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Aqua: {
                AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Circle: {
                CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
            case Smoke: {
                SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                break;
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderHand.PostBoth event) {
        if (ItemShaders.mc.world == null || ItemShaders.mc.player == null) {
            return;
        }
        final Predicate<Boolean> newFill = this.getFill();
        switch (this.glowESP.getValue()) {
            case Color: {
                GlowShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue());
                break;
            }
            case RainbowCube: {
                RainbowCubeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.colorImgOutline.getValue().getColorObject(), this.WaveLenghtOutline.getValue(), this.RSTARTOutline.getValue(), this.GSTARTOutline.getValue(), this.BSTARTOutline.getValue());
                RainbowCubeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Gradient: {
                GradientOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.moreGradientOutline.getValue(), this.creepyOutline.getValue(), this.alphaOutline.getValue(), this.NUM_OCTAVESOutline.getValue());
                GradientOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Astral: {
                AstralOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.redOutline.getValue(), this.greenOutline.getValue(), this.blueOutline.getValue(), this.alphaOutline.getValue(), this.iterationsOutline.getValue(), this.formuparam2Outline.getValue(), this.zoomOutline.getValue(), this.volumStepsOutline.getValue(), this.stepSizeOutline.getValue(), this.titleOutline.getValue(), this.distfadingOutline.getValue(), this.saturationOutline.getValue(), 0.0f, ((boolean)this.fadeOutline.getValue()) ? 1 : 0);
                AstralOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Aqua: {
                AquaOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.MaxIterOutline.getValue(), this.tauOutline.getValue());
                AquaOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Circle: {
                CircleOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.PIOutline.getValue(), this.radOutline.getValue());
                CircleOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
            case Smoke: {
                SmokeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.secondColorImgOutline.getValue().getColorObject(), this.thirdColorImgOutline.getValue().getColorObject(), this.NUM_OCTAVESOutline.getValue());
                SmokeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                break;
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
    
    Predicate<Boolean> getFill() {
        Predicate<Boolean> output = a -> true;
        switch (this.fillShader.getValue()) {
            case Astral: {
                output = (a -> {
                    FlowShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.redFill.getValue(), this.greenFill.getValue(), this.blueFill.getValue(), this.alphaFill.getValue(), this.iterationsFill.getValue(), this.formuparam2Fill.getValue(), this.zoomFill.getValue(), this.volumStepsFill.getValue(), this.stepSizeFill.getValue(), this.titleFill.getValue(), this.distfadingFill.getValue(), this.saturationFill.getValue(), 0.0f, ((boolean)this.fadeFill.getValue()) ? 1 : 0);
                    return true;
                });
                FlowShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Aqua: {
                output = (a -> {
                    AquaShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                    return true;
                });
                AquaShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Smoke: {
                output = (a -> {
                    SmokeShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.secondcolorImgFill.getValue().getColorObject(), this.thirdcolorImgFill.getValue().getColorObject(), this.NUM_OCTAVESFill.getValue());
                    return true;
                });
                SmokeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case RainbowCube: {
                output = (a -> {
                    RainbowCubeShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.WaveLenghtFIll.getValue(), this.RSTARTFill.getValue(), this.GSTARTFill.getValue(), this.BSTARTFIll.getValue());
                    return true;
                });
                RainbowCubeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Gradient: {
                output = (a -> {
                    GradientShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.moreGradientFill.getValue(), this.creepyFill.getValue(), this.alphaFill.getValue(), this.NUM_OCTAVESFill.getValue());
                    return true;
                });
                GradientShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Fill: {
                final Color col = this.colorImgFill.getValue().getColorObject();
                final Color color;
                output = (a -> {
                    FillShader.INSTANCE.startShader(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                    return false;
                });
                FillShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Circle: {
                output = (a -> {
                    CircleShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.PI.getValue(), this.rad.getValue());
                    return true;
                });
                CircleShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
            case Phobos: {
                output = (a -> {
                    PhobosShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                    return true;
                });
                PhobosShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                break;
            }
        }
        return output;
    }
    
    static {
        ItemShaders.INSTANCE = new ItemShaders();
    }
    
    public enum fillShadermode
    {
        Astral, 
        Aqua, 
        Smoke, 
        RainbowCube, 
        Gradient, 
        Fill, 
        Circle, 
        Phobos, 
        None;
    }
    
    public enum glowESPmode
    {
        None, 
        Color, 
        Astral, 
        RainbowCube, 
        Gradient, 
        Circle, 
        Smoke, 
        Aqua;
    }
}
