//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.math.*;

public class BlockHighlight extends Module
{
    private final Setting<Float> lineWidth;
    private final Setting<ColorSetting> color;
    
    public BlockHighlight() {
        super("BlockHighlight", "\u043f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u0431\u043b\u043e\u043a \u043d\u0430-\u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0442\u044b \u0441\u043c\u043e\u0442\u0440\u0438\u0448\u044c", Module.Category.RENDER, false, false, false);
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(575714484)));
    }
    
    public void onRender3D(final Render3DEvent event) {
        final RayTraceResult ray = BlockHighlight.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos blockpos = ray.getBlockPos();
            RenderUtil.drawBlockOutline(blockpos, this.color.getValue().getColorObject(), this.lineWidth.getValue(), false);
        }
    }
}
