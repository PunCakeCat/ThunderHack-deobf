//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

public class PredictESP extends Module
{
    public Setting<Boolean> manualOutHole;
    public Setting<Boolean> showPredictions;
    public Setting<Boolean> debug;
    public Setting<Boolean> justOnce;
    public Setting<Boolean> hideSelf;
    public Setting<Boolean> splitXZ;
    public Setting<Boolean> onlyaura;
    public Setting<Boolean> calculateYPredict;
    public Setting<Integer> width;
    public Setting<Integer> exponentIncreaseY;
    public Setting<Integer> increaseY;
    public Setting<Integer> exponentDecreaseY;
    public Setting<Integer> decreaseY;
    public Setting<Integer> expnentStartDecrease;
    public Setting<Integer> startDecrease;
    public Setting<Integer> tickPredict;
    public Setting<Integer> range;
    public final Setting<ColorSetting> mainColor;
    public Setting<Boolean> aboveHoleManual;
    
    public PredictESP() {
        super("PredictESP", "\u0442\u0438\u043a\u0448\u0438\u0444\u0442 \u044d\u043a\u0441\u043f\u043b\u043e\u0438\u0442", Module.Category.RENDER, true, false, false);
        this.manualOutHole = (Setting<Boolean>)this.register(new Setting("ManualOutHole", (T)false));
        this.showPredictions = (Setting<Boolean>)this.register(new Setting("ShowPredictions", (T)false));
        this.debug = (Setting<Boolean>)this.register(new Setting("Debug", (T)false));
        this.justOnce = (Setting<Boolean>)this.register(new Setting("JustOnce", (T)false));
        this.hideSelf = (Setting<Boolean>)this.register(new Setting("Hide Self", (T)false));
        this.splitXZ = (Setting<Boolean>)this.register(new Setting("SplitXZ", (T)true));
        this.onlyaura = (Setting<Boolean>)this.register(new Setting("onlyaura", (T)true));
        this.calculateYPredict = (Setting<Boolean>)this.register(new Setting("Calculate Y Predict", (T)true));
        this.width = (Setting<Integer>)this.register(new Setting("Line Width", (T)2, (T)1, (T)5));
        this.exponentIncreaseY = (Setting<Integer>)this.register(new Setting("ExponentIncreaseY", (T)2, (T)1, (T)3, v -> this.calculateYPredict.getValue()));
        this.increaseY = (Setting<Integer>)this.register(new Setting("IncreaseY", (T)3, (T)1, (T)5, v -> this.calculateYPredict.getValue()));
        this.exponentDecreaseY = (Setting<Integer>)this.register(new Setting("ExponentDecreaseY", (T)1, (T)1, (T)3, v -> this.calculateYPredict.getValue()));
        this.decreaseY = (Setting<Integer>)this.register(new Setting("Decrease Y", (T)2, (T)1, (T)5, v -> this.calculateYPredict.getValue()));
        this.expnentStartDecrease = (Setting<Integer>)this.register(new Setting("Exponent Start", (T)2, (T)1, (T)5, v -> this.calculateYPredict.getValue()));
        this.startDecrease = (Setting<Integer>)this.register(new Setting("Start Decrease", (T)39, (T)0, (T)200, v -> this.calculateYPredict.getValue()));
        this.tickPredict = (Setting<Integer>)this.register(new Setting("Tick Predict", (T)8, (T)0, (T)30));
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)10, (T)0, (T)100));
        this.mainColor = (Setting<ColorSetting>)this.register(new Setting("color", (T)new ColorSetting(-2013200640)));
        this.aboveHoleManual = (Setting<Boolean>)this.register(new Setting("AboveHoleManual", (T)false, v -> this.manualOutHole.getValue()));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        double[] posVec;
        final double[] newPosVec;
        final double motionX;
        double motionY;
        final double motionZ;
        boolean goingUp;
        boolean start;
        int up;
        int down;
        boolean isHole;
        final Object o;
        final int n;
        int i;
        double[] array;
        double[] newPosVec2;
        final int n2;
        RayTraceResult result;
        double[] array2;
        double[] newPosVec3;
        final int n3;
        RayTraceResult result2;
        double[] array3;
        double[] newPosVec4;
        final int n4;
        final Object o2;
        final int n5;
        RayTraceResult result3;
        double[] newPosVec5;
        final Object o3;
        final int n6;
        RayTraceResult result4;
        final Object o4;
        final int n7;
        final Object o5;
        final int n8;
        final EntityOtherPlayerMP entityOtherPlayerMP;
        final EntityOtherPlayerMP clonedPlayer;
        PredictESP.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityPlayer && (!this.hideSelf.getValue() || entity != PredictESP.mc.player)).filter(this::rangeEntityCheck).forEach(entity -> {
            posVec = new double[] { ((Entity)entity).posX, ((Entity)entity).posY, ((Entity)entity).posZ };
            newPosVec = posVec.clone();
            motionX = ((Entity)entity).posX - ((Entity)entity).prevPosX;
            motionY = ((Entity)entity).posY - ((Entity)entity).prevPosY;
            motionZ = ((Entity)entity).posZ - ((Entity)entity).prevPosZ;
            goingUp = false;
            start = true;
            up = 0;
            down = 0;
            if (this.debug.getValue()) {
                Command.sendMessage(String.format("Values: %f %f %f", newPosVec[0], newPosVec[1], newPosVec[2]));
            }
            isHole = false;
            if (this.manualOutHole.getValue() && motionY > 0.0) {
                if (HoleUtil.isHole(new BlockPos(((Entity)entity).posX, ((Entity)entity).posY, ((Entity)entity).posZ), false, true).getType() != HoleUtil.HoleType.NONE) {
                    isHole = true;
                }
                else if (this.aboveHoleManual.getValue()) {
                    if (HoleUtil.isHole(new BlockPos(((Entity)entity).posX, ((Entity)entity).posY - 1.0, ((Entity)entity).posZ), false, true).getType() != HoleUtil.HoleType.NONE) {
                        isHole = true;
                    }
                }
                if (isHole) {
                    ++o[n];
                }
            }
            for (i = 0; i < this.tickPredict.getValue(); ++i) {
                if (this.splitXZ.getValue()) {
                    newPosVec2 = (array = posVec.clone());
                    array[n2] += motionX;
                    result = PredictESP.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec2[0], posVec[1], posVec[2]));
                    if (result == null || result.typeOfHit == RayTraceResult.Type.ENTITY) {
                        posVec = newPosVec2.clone();
                    }
                    newPosVec3 = (array2 = posVec.clone());
                    array2[n3] += motionZ;
                    result2 = PredictESP.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec3[0], posVec[1], newPosVec3[2]));
                    if (result2 == null || result2.typeOfHit == RayTraceResult.Type.ENTITY) {
                        posVec = newPosVec3.clone();
                    }
                }
                else {
                    newPosVec4 = (array3 = posVec.clone());
                    array3[n4] += motionX;
                    o2[n5] += motionZ;
                    result3 = PredictESP.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec4[0], posVec[1], newPosVec4[2]));
                    if (result3 == null || result3.typeOfHit == RayTraceResult.Type.ENTITY) {
                        posVec = newPosVec4.clone();
                    }
                }
                if (this.calculateYPredict.getValue() && !isHole) {
                    newPosVec5 = posVec.clone();
                    if (!((Entity)entity).onGround && motionY != -0.0784000015258789) {
                        if (start) {
                            if (motionY == 0.0) {
                                motionY = this.startDecrease.getValue() / Math.pow(10.0, this.expnentStartDecrease.getValue());
                            }
                            goingUp = false;
                            start = false;
                            if (this.debug.getValue()) {
                                Command.sendMessage("Start motionY: " + motionY);
                            }
                        }
                        motionY += (goingUp ? (this.increaseY.getValue() / Math.pow(10.0, this.exponentIncreaseY.getValue())) : (this.decreaseY.getValue() / Math.pow(10.0, this.exponentDecreaseY.getValue())));
                        if (Math.abs(motionY) > this.startDecrease.getValue() / Math.pow(10.0, this.expnentStartDecrease.getValue())) {
                            goingUp = false;
                            if (this.debug.getValue()) {
                                ++up;
                            }
                            motionY = this.decreaseY.getValue() / Math.pow(10.0, this.exponentDecreaseY.getValue());
                        }
                        o3[n6] += (goingUp ? 1 : -1) * motionY;
                        result4 = PredictESP.mc.world.rayTraceBlocks(new Vec3d(posVec[0], posVec[1], posVec[2]), new Vec3d(newPosVec5[0], newPosVec5[1], newPosVec5[2]));
                        if (result4 == null || result4.typeOfHit == RayTraceResult.Type.ENTITY) {
                            posVec = newPosVec5.clone();
                        }
                        else if (!goingUp) {
                            goingUp = true;
                            o4[n7] += this.increaseY.getValue() / Math.pow(10.0, this.exponentIncreaseY.getValue());
                            motionY = this.increaseY.getValue() / Math.pow(10.0, this.exponentIncreaseY.getValue());
                            o5[n8] += motionY;
                            if (this.debug.getValue()) {
                                ++down;
                            }
                        }
                    }
                }
                if (this.showPredictions.getValue()) {
                    Command.sendMessage(String.format("Values: %f %f %f", posVec[0], posVec[1], posVec[2]));
                }
            }
            if (this.debug.getValue()) {
                Command.sendMessage(String.format("Player: %s Total ticks: %d Up: %d Down: %d", entity.getGameProfile().getName(), this.tickPredict.getValue(), up, down));
            }
            new EntityOtherPlayerMP((World)PredictESP.mc.world, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), "Fit"));
            clonedPlayer = entityOtherPlayerMP;
            clonedPlayer.setPosition(posVec[0], posVec[1], posVec[2]);
            RenderUtil.drawBlockOutline(new BlockPos(posVec[0], posVec[1], posVec[2]), this.mainColor.getValue().getColorObject(), 3.0f, true);
            return;
        });
        if (this.justOnce.getValue()) {
            this.disable();
        }
    }
    
    private boolean rangeEntityCheck(final Entity entity) {
        return entity.getDistance((Entity)PredictESP.mc.player) <= this.range.getValue();
    }
}
