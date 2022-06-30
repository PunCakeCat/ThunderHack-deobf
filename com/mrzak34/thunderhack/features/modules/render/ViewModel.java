//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ViewModel extends Module
{
    private static ViewModel INSTANCE;
    public Setting<Settings> settings;
    public Setting<Boolean> noEatAnimation;
    public Setting<Double> eatX;
    public Setting<Double> eatY;
    public Setting<Boolean> doBob;
    public Setting<Double> mainX;
    public Setting<Double> mainY;
    public Setting<Double> mainZ;
    public Setting<Double> offX;
    public Setting<Double> offY;
    public Setting<Double> offZ;
    public Setting<Integer> mainRotX;
    public Setting<Integer> mainRotY;
    public Setting<Integer> mainRotZ;
    public Setting<Integer> offRotX;
    public Setting<Integer> offRotY;
    public Setting<Integer> offRotZ;
    public Setting<Double> mainScaleX;
    public Setting<Double> mainScaleY;
    public Setting<Double> mainScaleZ;
    public Setting<Double> offScaleX;
    public Setting<Double> offScaleY;
    public Setting<Double> offScaleZ;
    public Timer timer2;
    public Setting<Boolean> killauraattack;
    public Setting<Double> kmainScaleX;
    public Setting<Double> kmainScaleY;
    public Setting<Double> kmainScaleZ;
    public Setting<Integer> kmainRotX;
    public Setting<Integer> kmainRotY;
    public Setting<Integer> kmainRotZ;
    public Setting<Double> kmainX;
    public Setting<Double> kmainY;
    public Setting<Double> kmainZ;
    public Setting<Boolean> rotatexo;
    public Setting<Boolean> rotateyo;
    public Setting<Boolean> rotatezo;
    public Setting<Boolean> krotatex;
    public Setting<Boolean> krotatey;
    public Setting<Boolean> krotatez;
    public Setting<Boolean> rotatex;
    public Setting<Boolean> rotatey;
    public Setting<Boolean> rotatez;
    public Setting<Integer> animdelay;
    int negripidari;
    
    public ViewModel() {
        super("ViewModel", "Cool", Module.Category.RENDER, true, false, false);
        this.settings = (Setting<Settings>)this.register(new Setting("Settings", (T)Settings.TRANSLATE));
        this.noEatAnimation = (Setting<Boolean>)this.register(new Setting("NoEatAnimation", (T)false, v -> this.settings.getValue() == Settings.TWEAKS));
        this.eatX = (Setting<Double>)this.register(new Setting("EatX", (T)1.0, (T)(-2.0), (T)5.0, v -> this.settings.getValue() == Settings.TWEAKS && !this.noEatAnimation.getValue()));
        this.eatY = (Setting<Double>)this.register(new Setting("EatY", (T)1.0, (T)(-2.0), (T)5.0, v -> this.settings.getValue() == Settings.TWEAKS && !this.noEatAnimation.getValue()));
        this.doBob = (Setting<Boolean>)this.register(new Setting("ItemBob", (T)true, v -> this.settings.getValue() == Settings.TWEAKS));
        this.mainX = (Setting<Double>)this.register(new Setting("MainX", (T)1.2, (T)(-2.0), (T)4.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainY = (Setting<Double>)this.register(new Setting("MainY", (T)(-0.95), (T)(-3.0), (T)3.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainZ = (Setting<Double>)this.register(new Setting("MainZ", (T)(-1.45), (T)(-5.0), (T)5.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offX = (Setting<Double>)this.register(new Setting("OffX", (T)1.2, (T)(-2.0), (T)4.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offY = (Setting<Double>)this.register(new Setting("OffY", (T)(-0.95), (T)(-3.0), (T)3.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offZ = (Setting<Double>)this.register(new Setting("OffZ", (T)(-1.45), (T)(-5.0), (T)5.0, v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainRotX = (Setting<Integer>)this.register(new Setting("MainRotationX", (T)0, (T)(-36), (T)36, v -> this.settings.getValue() == Settings.ROTATE));
        this.mainRotY = (Setting<Integer>)this.register(new Setting("MainRotationY", (T)0, (T)(-36), (T)36, v -> this.settings.getValue() == Settings.ROTATE));
        this.mainRotZ = (Setting<Integer>)this.register(new Setting("MainRotationZ", (T)0, (T)(-36), (T)36, v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotX = (Setting<Integer>)this.register(new Setting("OffRotationX", (T)0, (T)(-36), (T)36, v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotY = (Setting<Integer>)this.register(new Setting("OffRotationY", (T)0, (T)(-36), (T)36, v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotZ = (Setting<Integer>)this.register(new Setting("OffRotationZ", (T)0, (T)(-36), (T)36, v -> this.settings.getValue() == Settings.ROTATE));
        this.mainScaleX = (Setting<Double>)this.register(new Setting("MainScaleX", (T)1.0, (T)0.1, (T)5.0, v -> this.settings.getValue() == Settings.SCALE));
        this.mainScaleY = (Setting<Double>)this.register(new Setting("MainScaleY", (T)1.0, (T)0.1, (T)5.0, v -> this.settings.getValue() == Settings.SCALE));
        this.mainScaleZ = (Setting<Double>)this.register(new Setting("MainScaleZ", (T)1.0, (T)0.1, (T)5.0, v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleX = (Setting<Double>)this.register(new Setting("OffScaleX", (T)1.0, (T)0.1, (T)5.0, v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleY = (Setting<Double>)this.register(new Setting("OffScaleY", (T)1.0, (T)0.1, (T)5.0, v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleZ = (Setting<Double>)this.register(new Setting("OffScaleZ", (T)1.0, (T)0.1, (T)5.0, v -> this.settings.getValue() == Settings.SCALE));
        this.timer2 = new Timer();
        this.killauraattack = (Setting<Boolean>)this.register(new Setting("KillAura", (T)false));
        this.kmainScaleX = (Setting<Double>)this.register(new Setting("KMainScaleX", (T)1.0, (T)0.1, (T)5.0, v -> this.killauraattack.getValue()));
        this.kmainScaleY = (Setting<Double>)this.register(new Setting("KMainScaleY", (T)1.0, (T)0.1, (T)5.0, v -> this.killauraattack.getValue()));
        this.kmainScaleZ = (Setting<Double>)this.register(new Setting("KMainScaleZ", (T)1.0, (T)0.1, (T)5.0, v -> this.killauraattack.getValue()));
        this.kmainRotX = (Setting<Integer>)this.register(new Setting("KMainRotationX", (T)0, (T)(-36), (T)36, v -> this.killauraattack.getValue()));
        this.kmainRotY = (Setting<Integer>)this.register(new Setting("KMainRotationY", (T)0, (T)(-36), (T)36, v -> this.killauraattack.getValue()));
        this.kmainRotZ = (Setting<Integer>)this.register(new Setting("kMainRotationZ", (T)0, (T)(-36), (T)36, v -> this.killauraattack.getValue()));
        this.kmainX = (Setting<Double>)this.register(new Setting("KMainX", (T)1.2, (T)(-2.0), (T)4.0, v -> this.killauraattack.getValue()));
        this.kmainY = (Setting<Double>)this.register(new Setting("KMainY", (T)(-0.95), (T)(-3.0), (T)3.0, v -> this.killauraattack.getValue()));
        this.kmainZ = (Setting<Double>)this.register(new Setting("KMainZ", (T)(-1.45), (T)(-5.0), (T)5.0, v -> this.killauraattack.getValue()));
        this.rotatexo = (Setting<Boolean>)this.register(new Setting("RotateX", (T)false));
        this.rotateyo = (Setting<Boolean>)this.register(new Setting("RotateY", (T)false));
        this.rotatezo = (Setting<Boolean>)this.register(new Setting("RotateZ", (T)false));
        this.krotatex = (Setting<Boolean>)this.register(new Setting("KRotateX", (T)false, v -> this.killauraattack.getValue()));
        this.krotatey = (Setting<Boolean>)this.register(new Setting("KRotateY", (T)false, v -> this.killauraattack.getValue()));
        this.krotatez = (Setting<Boolean>)this.register(new Setting("KRotateZ", (T)false, v -> this.killauraattack.getValue()));
        this.rotatex = (Setting<Boolean>)this.register(new Setting("RotateXOff", (T)false));
        this.rotatey = (Setting<Boolean>)this.register(new Setting("RotateYOff", (T)false));
        this.rotatez = (Setting<Boolean>)this.register(new Setting("RotateZOff", (T)false));
        this.animdelay = (Setting<Integer>)this.register(new Setting("RotateSpeed", (T)36, (T)1, (T)1200, v -> this.killauraattack.getValue() || this.rotatex.getValue() || this.rotatey.getValue() || this.rotatez.getValue()));
        this.negripidari = -180;
        this.setInstance();
    }
    
    public static ViewModel getInstance() {
        if (ViewModel.INSTANCE == null) {
            ViewModel.INSTANCE = new ViewModel();
        }
        return ViewModel.INSTANCE;
    }
    
    private void setInstance() {
        ViewModel.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onItemRender(final RenderItemEvent event) {
        event.setOffX(-this.offX.getValue());
        event.setOffY((double)this.offY.getValue());
        event.setOffZ((double)this.offZ.getValue());
        if (this.timer2.passedMs(1000 / this.animdelay.getValue())) {
            ++this.negripidari;
            if (this.negripidari > 180) {
                this.negripidari = -180;
            }
            this.timer2.reset();
        }
        if (!this.rotatex.getValue()) {
            event.setOffRotX((double)(this.offRotX.getValue() * 5));
        }
        else {
            event.setOffRotX((double)this.negripidari);
        }
        if (!this.rotatey.getValue()) {
            event.setOffRotY((double)(this.offRotY.getValue() * 5));
        }
        else {
            event.setOffRotY((double)this.negripidari);
        }
        if (!this.rotatez.getValue()) {
            event.setOffRotZ((double)(this.offRotZ.getValue() * 5));
        }
        else {
            event.setOffRotZ((double)this.negripidari);
        }
        event.setOffHandScaleX((double)this.offScaleX.getValue());
        event.setOffHandScaleY((double)this.offScaleY.getValue());
        event.setOffHandScaleZ((double)this.offScaleZ.getValue());
        if (this.killauraattack.getValue() && KillauraOld.target != null) {
            event.setMainHandScaleX((double)this.kmainScaleX.getValue());
            event.setMainHandScaleY((double)this.kmainScaleY.getValue());
            event.setMainHandScaleZ((double)this.kmainScaleZ.getValue());
            if (!this.krotatex.getValue()) {
                event.setMainRotX((double)(this.kmainRotX.getValue() * 5));
            }
            else {
                event.setMainRotX((double)this.negripidari);
            }
            if (!this.krotatey.getValue()) {
                event.setMainRotY((double)(this.kmainRotY.getValue() * 5));
            }
            else {
                event.setMainRotY((double)this.negripidari);
            }
            if (!this.krotatez.getValue()) {
                event.setMainRotZ((double)(this.kmainRotZ.getValue() * 5));
            }
            else {
                event.setMainRotZ((double)this.negripidari);
            }
            event.setMainX((double)this.kmainX.getValue());
            event.setMainY((double)this.kmainY.getValue());
            event.setMainZ((double)this.kmainZ.getValue());
        }
        else {
            event.setMainHandScaleX((double)this.mainScaleX.getValue());
            event.setMainHandScaleY((double)this.mainScaleY.getValue());
            event.setMainHandScaleZ((double)this.mainScaleZ.getValue());
            if (!this.rotatexo.getValue()) {
                event.setMainRotX((double)(this.mainRotX.getValue() * 5));
            }
            else {
                event.setMainRotX((double)this.negripidari);
            }
            if (!this.rotateyo.getValue()) {
                event.setMainRotY((double)(this.mainRotY.getValue() * 5));
            }
            else {
                event.setMainRotY((double)this.negripidari);
            }
            if (!this.rotatezo.getValue()) {
                event.setMainRotZ((double)(this.mainRotZ.getValue() * 5));
            }
            else {
                event.setMainRotZ((double)this.negripidari);
            }
            event.setMainX((double)this.mainX.getValue());
            event.setMainY((double)this.mainY.getValue());
            event.setMainZ((double)this.mainZ.getValue());
        }
    }
    
    static {
        ViewModel.INSTANCE = new ViewModel();
    }
    
    private enum Settings
    {
        TRANSLATE, 
        ROTATE, 
        SCALE, 
        TWEAKS;
    }
}
