//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.shaders.impl.fill.*;
import com.mrzak34.thunderhack.util.shaders.impl.outline.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.function.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class Shaders extends Module
{
    private Setting<fillShadermode> fillShader;
    private Setting<glowESPmode> glowESP;
    private Setting<Boolean> rangeCheck;
    private Setting<Boolean> arrowOutline;
    private Setting<Boolean> enderPerleOutline;
    private Setting<Boolean> minecartTntOutline;
    private Setting<Boolean> boatOutline;
    private Setting<Boolean> bottleOutline;
    private Setting<Boolean> xpOutline;
    private Setting<Boolean> crystalsOutline;
    private Setting<Boolean> playersOutline;
    private Setting<Boolean> mobsOutline;
    private Setting<Boolean> itemsOutline;
    private Setting<Boolean> arrowFill;
    private Setting<Boolean> enderPerleFill;
    private Setting<Boolean> minecartFill;
    private Setting<Boolean> boatFill;
    private Setting<Boolean> bottleFill;
    private Setting<Boolean> xpFill;
    private Setting<Boolean> crystalsFill;
    private Setting<Boolean> playersFill;
    private Setting<Boolean> mobsFill;
    private Setting<Boolean> itemsFill;
    private Setting<Boolean> fadeFill;
    private Setting<Boolean> fadeOutline;
    private Setting<Boolean> GradientAlpha;
    public Setting<Float> duplicateOutline;
    public Setting<Float> duplicateFill;
    public Setting<Float> speedOutline;
    public Setting<Float> speedFill;
    public Setting<Float> maxRange;
    public Setting<Float> minRange;
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
    public Setting<Integer> maxEntities;
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
    private final Setting<ColorSetting> colorImgOutline;
    private final Setting<ColorSetting> secondColorImgOutline;
    private final Setting<ColorSetting> thirdColorImgOutline;
    private final Setting<ColorSetting> colorESP;
    private final Setting<ColorSetting> colorImgFill;
    private final Setting<ColorSetting> thirdColorImgFIll;
    private final Setting<ColorSetting> secondColorImgFill;
    public Setting<Float> alphaFill;
    public Setting<Float> blueFill;
    public Setting<Float> greenFill;
    public Setting<Float> tauFill;
    public Setting<Float> creepyFill;
    public Setting<Float> moreGradientFill;
    public Setting<Float> speesaturationOutlined;
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
    public boolean renderTags;
    public boolean renderCape;
    
    public Shaders() {
        super("Shaders", "Spawns in a fake player", Module.Category.RENDER, true, false, false);
        this.fillShader = (Setting<fillShadermode>)this.register(new Setting("Fill Shader", (T)fillShadermode.None));
        this.glowESP = (Setting<glowESPmode>)this.register(new Setting("Glow ESP", (T)glowESPmode.None));
        this.rangeCheck = (Setting<Boolean>)this.register(new Setting("Range Check", (T)true));
        this.arrowOutline = (Setting<Boolean>)this.register(new Setting("Arrow Outline", (T)false));
        this.enderPerleOutline = (Setting<Boolean>)this.register(new Setting("EnderPerle Outline", (T)false));
        this.minecartTntOutline = (Setting<Boolean>)this.register(new Setting("MinecartTnt Outline", (T)false));
        this.boatOutline = (Setting<Boolean>)this.register(new Setting("Boat Outline", (T)false));
        this.bottleOutline = (Setting<Boolean>)this.register(new Setting("Bottle Outline", (T)false));
        this.xpOutline = (Setting<Boolean>)this.register(new Setting("XP Outline", (T)false));
        this.crystalsOutline = (Setting<Boolean>)this.register(new Setting("Crystals Outline", (T)false));
        this.playersOutline = (Setting<Boolean>)this.register(new Setting("Players Outline", (T)false));
        this.mobsOutline = (Setting<Boolean>)this.register(new Setting("Mobs Outline", (T)false));
        this.itemsOutline = (Setting<Boolean>)this.register(new Setting("Items Outline", (T)false));
        this.arrowFill = (Setting<Boolean>)this.register(new Setting("Arrow Fill", (T)false));
        this.enderPerleFill = (Setting<Boolean>)this.register(new Setting("EnderPerle Fill", (T)false));
        this.minecartFill = (Setting<Boolean>)this.register(new Setting("MinecartTnt Fill", (T)false));
        this.boatFill = (Setting<Boolean>)this.register(new Setting("Boat Fill", (T)false));
        this.bottleFill = (Setting<Boolean>)this.register(new Setting("Bottle Fill", (T)false));
        this.xpFill = (Setting<Boolean>)this.register(new Setting("XP Fill", (T)false));
        this.crystalsFill = (Setting<Boolean>)this.register(new Setting("Crystals Fill", (T)false));
        this.playersFill = (Setting<Boolean>)this.register(new Setting("Players Fill", (T)false));
        this.mobsFill = (Setting<Boolean>)this.register(new Setting("Mobs Fill", (T)false));
        this.itemsFill = (Setting<Boolean>)this.register(new Setting("Items Fill", (T)false));
        this.fadeFill = (Setting<Boolean>)this.register(new Setting("Fade Fill", (T)false));
        this.fadeOutline = (Setting<Boolean>)this.register(new Setting("FadeOL Fill", (T)false));
        this.GradientAlpha = (Setting<Boolean>)this.register(new Setting("Gradient Alpha", (T)false));
        this.duplicateOutline = (Setting<Float>)this.register(new Setting("duplicateOutline", (T)1.0f, (T)0.0f, (T)20.0f));
        this.duplicateFill = (Setting<Float>)this.register(new Setting("Duplicate Fill", (T)1.0f, (T)0.0f, (T)5.0f));
        this.speedOutline = (Setting<Float>)this.register(new Setting("Speed Outline", (T)10.0f, (T)1.0f, (T)100.0f));
        this.speedFill = (Setting<Float>)this.register(new Setting("Speed Fill", (T)10.0f, (T)1.0f, (T)100.0f));
        this.maxRange = (Setting<Float>)this.register(new Setting("Max Range", (T)20.0f, (T)10.0f, (T)100.0f, v -> this.rangeCheck.getValue()));
        this.minRange = (Setting<Float>)this.register(new Setting("Min range", (T)1.0f, (T)0.0f, (T)5.0f, v -> this.rangeCheck.getValue()));
        this.rad = (Setting<Float>)this.register(new Setting("RAD Fill", (T)0.75f, (T)0.0f, (T)5.0f, v -> this.fillShader.getValue() == fillShadermode.Circle));
        this.PI = (Setting<Float>)this.register(new Setting("PI Fill", (T)3.1415927f, (T)0.0f, (T)10.0f, v -> this.fillShader.getValue() == fillShadermode.Circle));
        this.saturationFill = (Setting<Float>)this.register(new Setting("saturation", (T)0.4f, (T)0.0f, (T)3.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.distfadingFill = (Setting<Float>)this.register(new Setting("distfading", (T)0.56f, (T)0.0f, (T)1.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.titleFill = (Setting<Float>)this.register(new Setting("Tile", (T)0.45f, (T)0.0f, (T)1.3f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.stepSizeFill = (Setting<Float>)this.register(new Setting("Step Size", (T)0.2f, (T)0.0f, (T)0.7f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.volumStepsFill = (Setting<Float>)this.register(new Setting("Volum Steps", (T)10.0f, (T)0.0f, (T)10.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.zoomFill = (Setting<Float>)this.register(new Setting("Zoom", (T)3.9f, (T)0.0f, (T)20.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.formuparam2Fill = (Setting<Float>)this.register(new Setting("formuparam2", (T)0.89f, (T)0.0f, (T)1.5f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        final glowESPmode astral;
        final glowESPmode glowESPmode;
        this.saturationOutline = (Setting<Float>)this.register(new Setting("saturation", (T)0.4f, (T)0.0f, (T)3.0f, v -> {
            this.glowESP.getValue();
            astral = Shaders.glowESPmode.Astral;
            return glowESPmode == Shaders.glowESPmode.Astral;
        }));
        this.maxEntities = (Setting<Integer>)this.register(new Setting("Max Entities", (T)100, (T)10, (T)500));
        this.iterationsFill = (Setting<Integer>)this.register(new Setting("Iteration", (T)4, (T)3, (T)20, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.redFill = (Setting<Integer>)this.register(new Setting("Tick Regen", (T)0, (T)0, (T)100, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.MaxIterFill = (Setting<Integer>)this.register(new Setting("Max Iter", (T)5, (T)0, (T)30, v -> this.fillShader.getValue() == fillShadermode.Aqua));
        this.NUM_OCTAVESFill = (Setting<Integer>)this.register(new Setting("NUM_OCTAVES", (T)5, (T)1, (T)30, v -> this.fillShader.getValue() == fillShadermode.Smoke));
        this.BSTARTFIll = (Setting<Integer>)this.register(new Setting("BSTART", (T)0, (T)0, (T)1000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.GSTARTFill = (Setting<Integer>)this.register(new Setting("GSTART", (T)0, (T)0, (T)1000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.RSTARTFill = (Setting<Integer>)this.register(new Setting("RSTART", (T)0, (T)0, (T)1000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.WaveLenghtFIll = (Setting<Integer>)this.register(new Setting("Wave Lenght", (T)555, (T)0, (T)2000, v -> this.fillShader.getValue() == fillShadermode.RainbowCube));
        this.volumStepsOutline = (Setting<Integer>)this.register(new Setting("Volum Steps", (T)10, (T)0, (T)10, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.iterationsOutline = (Setting<Integer>)this.register(new Setting("Iteration", (T)4, (T)3, (T)20, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.redOutline = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)100, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.MaxIterOutline = (Setting<Integer>)this.register(new Setting("Max Iter", (T)5, (T)0, (T)30, v -> this.glowESP.getValue() == Shaders.glowESPmode.Aqua));
        this.NUM_OCTAVESOutline = (Setting<Integer>)this.register(new Setting("NUM_OCTAVES", (T)5, (T)1, (T)30, v -> this.glowESP.getValue() == Shaders.glowESPmode.Smoke));
        this.BSTARTOutline = (Setting<Integer>)this.register(new Setting("BSTART", (T)0, (T)0, (T)1000, v -> this.glowESP.getValue() == Shaders.glowESPmode.RainbowCube));
        this.GSTARTOutline = (Setting<Integer>)this.register(new Setting("GSTART", (T)0, (T)0, (T)1000, v -> this.glowESP.getValue() == Shaders.glowESPmode.RainbowCube));
        this.RSTARTOutline = (Setting<Integer>)this.register(new Setting("RSTART", (T)0, (T)0, (T)1000, v -> this.glowESP.getValue() == Shaders.glowESPmode.RainbowCube));
        this.alphaValue = (Setting<Integer>)this.register(new Setting("Alpha Outline", (T)255, (T)0, (T)255, v -> !this.GradientAlpha.getValue()));
        this.WaveLenghtOutline = (Setting<Integer>)this.register(new Setting("Wave Lenght", (T)555, (T)0, (T)2000, v -> this.glowESP.getValue() == Shaders.glowESPmode.RainbowCube));
        this.colorImgOutline = (Setting<ColorSetting>)this.register(new Setting("colorImgOutline", (T)new ColorSetting(-2013200640)));
        this.secondColorImgOutline = (Setting<ColorSetting>)this.register(new Setting("secondColorImgOutline", (T)new ColorSetting(-2013200640)));
        this.thirdColorImgOutline = (Setting<ColorSetting>)this.register(new Setting("thirdColorImgOutline", (T)new ColorSetting(-2013200640)));
        this.colorESP = (Setting<ColorSetting>)this.register(new Setting("colorESP", (T)new ColorSetting(-2013200640)));
        this.colorImgFill = (Setting<ColorSetting>)this.register(new Setting("colorImgFill", (T)new ColorSetting(-2013200640)));
        this.thirdColorImgFIll = (Setting<ColorSetting>)this.register(new Setting("thirdcolorImgFill", (T)new ColorSetting(-2013200640)));
        this.secondColorImgFill = (Setting<ColorSetting>)this.register(new Setting("thirdcolorImgFill", (T)new ColorSetting(-2013200640)));
        this.alphaFill = (Setting<Float>)this.register(new Setting("AlphaF", (T)1.0f, (T)0.0f, (T)1.0f, v -> this.fillShader.getValue() == fillShadermode.Astral || this.fillShader.getValue() == fillShadermode.Smoke));
        this.blueFill = (Setting<Float>)this.register(new Setting("BlueF", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.greenFill = (Setting<Float>)this.register(new Setting("GreenF", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.fillShader.getValue() == fillShadermode.Astral));
        this.tauFill = (Setting<Float>)this.register(new Setting("TAU", (T)6.2831855f, (T)0.0f, (T)20.0f, v -> this.fillShader.getValue() == fillShadermode.Aqua));
        this.creepyFill = (Setting<Float>)this.register(new Setting("Creepy", (T)1.0f, (T)0.0f, (T)20.0f, v -> this.fillShader.getValue() == fillShadermode.Smoke));
        this.moreGradientFill = (Setting<Float>)this.register(new Setting("More Gradient", (T)1.0f, (T)0.0f, (T)10.0f, v -> this.fillShader.getValue() == fillShadermode.Smoke));
        this.speesaturationOutlined = (Setting<Float>)this.register(new Setting("saturation", (T)0.4f, (T)0.0f, (T)3.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.distfadingOutline = (Setting<Float>)this.register(new Setting("distfading", (T)0.56f, (T)0.0f, (T)1.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.titleOutline = (Setting<Float>)this.register(new Setting("Tile", (T)0.45f, (T)0.0f, (T)1.3f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.stepSizeOutline = (Setting<Float>)this.register(new Setting("Step Size", (T)0.19f, (T)0.0f, (T)0.7f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.zoomOutline = (Setting<Float>)this.register(new Setting("Zoom", (T)3.9f, (T)0.0f, (T)20.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.formuparam2Outline = (Setting<Float>)this.register(new Setting("formuparam2", (T)0.89f, (T)0.0f, (T)1.5f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.alphaOutline = (Setting<Float>)this.register(new Setting("Alpha", (T)1.0f, (T)0.0f, (T)1.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral || this.glowESP.getValue() == Shaders.glowESPmode.Gradient));
        this.blueOutline = (Setting<Float>)this.register(new Setting("Blue", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.greenOutline = (Setting<Float>)this.register(new Setting("Green", (T)0.0f, (T)0.0f, (T)5.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Astral));
        this.tauOutline = (Setting<Float>)this.register(new Setting("TAU", (T)6.2831855f, (T)0.0f, (T)20.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Aqua));
        this.creepyOutline = (Setting<Float>)this.register(new Setting("Creepy", (T)1.0f, (T)0.0f, (T)20.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Gradient));
        this.moreGradientOutline = (Setting<Float>)this.register(new Setting("More Gradient", (T)1.0f, (T)0.0f, (T)10.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Gradient));
        this.radOutline = (Setting<Float>)this.register(new Setting("RAD Outline", (T)0.75f, (T)0.0f, (T)5.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Circle));
        this.PIOutline = (Setting<Float>)this.register(new Setting("PI Outline", (T)3.1415927f, (T)0.0f, (T)10.0f, v -> this.glowESP.getValue() == Shaders.glowESPmode.Circle));
        this.quality = (Setting<Float>)this.register(new Setting("quality", (T)1.0f, (T)0.0f, (T)20.0f));
        this.radius = (Setting<Float>)this.register(new Setting("radius", (T)1.0f, (T)0.0f, (T)5.0f));
        this.renderTags = true;
        this.renderCape = true;
    }
    
    @SubscribeEvent
    public void onRenderGameOverlay(final RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (Shaders.mc.world == null || Shaders.mc.player == null) {
                return;
            }
            GlStateManager.pushMatrix();
            this.renderTags = false;
            this.renderCape = false;
            if (this.glowESP.getValue() != glowESPmode.None && this.fillShader.getValue() != fillShadermode.None) {
                switch (this.fillShader.getValue()) {
                    case Astral: {
                        FlowShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.redFill.getValue(), this.greenFill.getValue(), this.blueFill.getValue(), this.alphaFill.getValue(), this.iterationsFill.getValue(), this.formuparam2Fill.getValue(), this.zoomFill.getValue(), this.volumStepsFill.getValue(), this.stepSizeFill.getValue(), this.titleFill.getValue(), this.distfadingFill.getValue(), this.saturationFill.getValue(), 0.0f, ((boolean)this.fadeFill.getValue()) ? 1 : 0);
                        FlowShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Aqua: {
                        AquaShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        AquaShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                        AquaShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Smoke: {
                        SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.secondColorImgFill.getValue().getColorObject(), this.thirdColorImgFIll.getValue().getColorObject(), this.NUM_OCTAVESFill.getValue());
                        SmokeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case RainbowCube: {
                        RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.WaveLenghtFIll.getValue(), this.RSTARTFill.getValue(), this.GSTARTFill.getValue(), this.BSTARTFIll.getValue());
                        RainbowCubeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Gradient: {
                        GradientShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        GradientShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.moreGradientFill.getValue(), this.creepyFill.getValue(), this.alphaFill.getValue(), this.NUM_OCTAVESFill.getValue());
                        GradientShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Fill: {
                        FillShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        FillShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject());
                        FillShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Circle: {
                        CircleShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        CircleShader.INSTANCE.stopDraw(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.PI.getValue(), this.rad.getValue());
                        CircleShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Phobos: {
                        PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        PhobosShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                        PhobosShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                }
                switch (this.glowESP.getValue()) {
                    case Color: {
                        GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        GlowShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue());
                        break;
                    }
                    case RainbowCube: {
                        RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        RainbowCubeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.colorImgOutline.getValue().getColorObject(), this.WaveLenghtOutline.getValue(), this.RSTARTOutline.getValue(), this.GSTARTOutline.getValue(), this.BSTARTOutline.getValue());
                        RainbowCubeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Gradient: {
                        GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        GradientOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.moreGradientOutline.getValue(), this.creepyOutline.getValue(), this.alphaOutline.getValue(), this.NUM_OCTAVESOutline.getValue());
                        GradientOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Astral: {
                        AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        AstralOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.redOutline.getValue(), this.greenOutline.getValue(), this.blueOutline.getValue(), this.alphaOutline.getValue(), this.iterationsOutline.getValue(), this.formuparam2Outline.getValue(), this.zoomOutline.getValue(), this.volumStepsOutline.getValue(), this.stepSizeOutline.getValue(), this.titleOutline.getValue(), this.distfadingOutline.getValue(), this.saturationOutline.getValue(), 0.0f, ((boolean)this.fadeOutline.getValue()) ? 1 : 0);
                        AstralOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Aqua: {
                        AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        AquaOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.MaxIterOutline.getValue(), this.tauOutline.getValue());
                        AquaOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Circle: {
                        CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        CircleOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.PIOutline.getValue(), this.radOutline.getValue());
                        CircleOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Smoke: {
                        SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        SmokeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.secondColorImgOutline.getValue().getColorObject(), this.thirdColorImgOutline.getValue().getColorObject(), this.NUM_OCTAVESOutline.getValue());
                        SmokeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                }
            }
            else {
                switch (this.glowESP.getValue()) {
                    case Color: {
                        GlowShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        GlowShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue());
                        break;
                    }
                    case RainbowCube: {
                        RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        RainbowCubeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.colorImgOutline.getValue().getColorObject(), this.WaveLenghtOutline.getValue(), this.RSTARTOutline.getValue(), this.GSTARTOutline.getValue(), this.BSTARTOutline.getValue());
                        RainbowCubeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Gradient: {
                        GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        GradientOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.moreGradientOutline.getValue(), this.creepyOutline.getValue(), this.alphaOutline.getValue(), this.NUM_OCTAVESOutline.getValue());
                        GradientOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Astral: {
                        AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        AstralOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.redOutline.getValue(), this.greenOutline.getValue(), this.blueOutline.getValue(), this.alphaOutline.getValue(), this.iterationsOutline.getValue(), this.formuparam2Outline.getValue(), this.zoomOutline.getValue(), this.volumStepsOutline.getValue(), this.stepSizeOutline.getValue(), this.titleOutline.getValue(), this.distfadingOutline.getValue(), this.saturationOutline.getValue(), 0.0f, ((boolean)this.fadeOutline.getValue()) ? 1 : 0);
                        AstralOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Aqua: {
                        AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        AquaOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.MaxIterOutline.getValue(), this.tauOutline.getValue());
                        AquaOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Circle: {
                        CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        CircleOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.PIOutline.getValue(), this.radOutline.getValue());
                        CircleOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                    case Smoke: {
                        SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersOutline(event.getPartialTicks());
                        SmokeOutlineShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), this.radius.getValue(), this.quality.getValue(), this.GradientAlpha.getValue(), this.alphaValue.getValue(), this.duplicateOutline.getValue(), this.secondColorImgOutline.getValue().getColorObject(), this.thirdColorImgOutline.getValue().getColorObject(), this.NUM_OCTAVESOutline.getValue());
                        SmokeOutlineShader.INSTANCE.update(this.speedOutline.getValue() / 1000.0f);
                        break;
                    }
                }
                switch (this.fillShader.getValue()) {
                    case Astral: {
                        FlowShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.redFill.getValue(), this.greenFill.getValue(), this.blueFill.getValue(), this.alphaFill.getValue(), this.iterationsFill.getValue(), this.formuparam2Fill.getValue(), this.zoomFill.getValue(), this.volumStepsFill.getValue(), this.stepSizeFill.getValue(), this.titleFill.getValue(), this.distfadingFill.getValue(), this.saturationFill.getValue(), 0.0f, ((boolean)this.fadeFill.getValue()) ? 1 : 0);
                        FlowShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Aqua: {
                        AquaShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        AquaShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                        AquaShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Smoke: {
                        SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.secondColorImgFill.getValue().getColorObject(), this.thirdColorImgFIll.getValue().getColorObject(), this.NUM_OCTAVESFill.getValue());
                        SmokeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case RainbowCube: {
                        RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0f, 1.0f, this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.WaveLenghtFIll.getValue(), this.RSTARTFill.getValue(), this.GSTARTFill.getValue(), this.BSTARTFIll.getValue());
                        RainbowCubeShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Gradient: {
                        GradientShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        GradientShader.INSTANCE.stopDraw(this.colorESP.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.moreGradientFill.getValue(), this.creepyFill.getValue(), this.alphaFill.getValue(), this.NUM_OCTAVESFill.getValue());
                        GradientShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Fill: {
                        FillShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        FillShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject());
                        FillShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Circle: {
                        CircleShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        CircleShader.INSTANCE.stopDraw(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.PI.getValue(), this.rad.getValue());
                        CircleShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                    case Phobos: {
                        PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
                        this.renderPlayersFill(event.getPartialTicks());
                        PhobosShader.INSTANCE.stopDraw(this.colorImgFill.getValue().getColorObject(), 1.0f, 1.0f, this.duplicateFill.getValue(), this.MaxIterFill.getValue(), this.tauFill.getValue());
                        PhobosShader.INSTANCE.update(this.speedFill.getValue() / 1000.0f);
                        break;
                    }
                }
            }
            this.renderTags = true;
            this.renderCape = true;
            GlStateManager.popMatrix();
        }
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
                    SmokeShader.INSTANCE.startShader(this.duplicateFill.getValue(), this.colorImgFill.getValue().getColorObject(), this.secondColorImgFill.getValue().getColorObject(), this.thirdColorImgFIll.getValue().getColorObject(), this.NUM_OCTAVESFill.getValue());
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
    
    void renderPlayersFill(final float tick) {
        final boolean rangeCheck = this.rangeCheck.getValue();
        final double minRange = this.minRange.getValue() * this.minRange.getValue();
        final double maxRange = this.maxRange.getValue() * this.maxRange.getValue();
        final AtomicInteger nEntities = new AtomicInteger();
        final int maxEntities = this.maxEntities.getValue();
        try {
            final boolean b;
            double distancePl;
            final double n;
            final double n2;
            Shaders.mc.world.loadedEntityList.stream().filter(e -> {
                if (nEntities.getAndIncrement() > maxEntities) {
                    return false;
                }
                else {
                    if (e instanceof EntityPlayer) {
                        if (this.playersFill.getValue() && (e != Shaders.mc.player || Shaders.mc.gameSettings.thirdPersonView != 0)) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityItem) {
                        if (this.itemsFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityCreature) {
                        if (this.mobsFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityEnderCrystal) {
                        if (this.crystalsFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityXPOrb) {
                        if (this.xpFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityExpBottle) {
                        if (this.bottleFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityBoat) {
                        if (this.boatFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityMinecart) {
                        if (this.minecartFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityEnderPearl) {
                        if (this.enderPerleFill.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityArrow && this.arrowFill.getValue()) {
                        return true;
                    }
                    return false;
                }
            }).filter(e -> {
                if (!b) {
                    return true;
                }
                else {
                    distancePl = Shaders.mc.player.getDistanceSq(e);
                    return distancePl > n && distancePl < n2;
                }
            }).forEach(e -> Shaders.mc.getRenderManager().renderEntityStatic(e, tick, true));
        }
        catch (Exception ex) {}
    }
    
    void renderPlayersOutline(final float tick) {
        try {
            final boolean rangeCheck = this.rangeCheck.getValue();
            final double minRange = this.minRange.getValue() * this.minRange.getValue();
            final double maxRange = this.maxRange.getValue() * this.maxRange.getValue();
            final AtomicInteger nEntities = new AtomicInteger();
            final int maxEntities = this.maxEntities.getValue();
            Shaders.mc.world.addEntityToWorld(-1000, (Entity)new EntityXPOrb((World)Shaders.mc.world, Shaders.mc.player.posX, Shaders.mc.player.posY + 1000000.0, Shaders.mc.player.posZ, 1));
            final boolean b;
            double distancePl;
            final double n;
            final double n2;
            Shaders.mc.world.loadedEntityList.stream().filter(e -> {
                if (nEntities.getAndIncrement() > maxEntities) {
                    return false;
                }
                else {
                    if (e instanceof EntityPlayer) {
                        if (this.playersOutline.getValue() && (e != Shaders.mc.player || Shaders.mc.gameSettings.thirdPersonView != 0)) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityItem) {
                        if (this.itemsOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityCreature) {
                        if (this.mobsOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityEnderCrystal) {
                        if (this.crystalsOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityXPOrb) {
                        if (this.xpOutline.getValue() || ((Entity)e).getEntityId() == -1000) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityExpBottle) {
                        if (this.bottleOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityBoat) {
                        if (this.boatOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityMinecart) {
                        if (this.minecartTntOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityEnderPearl) {
                        if (this.enderPerleOutline.getValue()) {
                            return true;
                        }
                    }
                    else if (e instanceof EntityArrow && this.arrowOutline.getValue()) {
                        return true;
                    }
                    return false;
                }
            }).filter(e -> {
                if (!b) {
                    return true;
                }
                else {
                    distancePl = Shaders.mc.player.getDistanceSq(e);
                    return (distancePl > n && distancePl < n2) || e.getEntityId() == -1000;
                }
            }).forEach(e -> Shaders.mc.getRenderManager().renderEntityStatic(e, tick, true));
            Shaders.mc.world.removeEntityFromWorld(-1000);
        }
        catch (Exception ex) {}
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
