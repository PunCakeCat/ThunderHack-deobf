//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;

public class ChorusESP extends Module
{
    private final Setting<Integer> time;
    private final Setting<Boolean> box;
    private final Setting<Boolean> outline;
    private final Setting<Float> lineWidth;
    private final TimerUtil timer;
    private double x;
    private double y;
    private double z;
    private final Setting<ColorSetting> outlineColor;
    private final Setting<ColorSetting> boxColor;
    
    public ChorusESP() {
        super("ChorusESP", "\u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442 \u0437\u0432\u0443\u043a \u0445\u043e\u0440\u0443\u0441\u0430", Module.Category.RENDER, true, false, false);
        this.outlineColor = (Setting<ColorSetting>)this.register(new Setting("OutlineColor", (T)new ColorSetting(575714484)));
        this.boxColor = (Setting<ColorSetting>)this.register(new Setting("BoxColor", (T)new ColorSetting(575714484)));
        this.time = (Setting<Integer>)this.register(new Setting("Duration", (T)500, (T)50, (T)3000));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.outline.getValue()));
        this.timer = new TimerUtil();
    }
    
    @SubscribeEvent
    public void onChorus(final ChorusEvent event) {
        this.x = event.getChorusX();
        this.y = event.getChorusY();
        this.z = event.getChorusZ();
        this.timer.reset();
    }
    
    public void onRender3D(final Render3DEvent render3DEvent) {
        if (this.timer.passedMs(this.time.getValue())) {
            return;
        }
        final AxisAlignedBB pos = RenderUtil.interpolateAxis(new AxisAlignedBB(this.x - 0.3, this.y, this.z - 0.3, this.x + 0.3, this.y + 1.8, this.z + 0.3));
        if (this.outline.getValue()) {
            RenderUtil.drawBlockOutline(pos, this.outlineColor.getValue().getColorObject(), this.lineWidth.getValue());
        }
        if (this.box.getValue()) {
            RenderUtil.drawFilledBox(pos, this.boxColor.getValue().getRawColor());
        }
    }
}
