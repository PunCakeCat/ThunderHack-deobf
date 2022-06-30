//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.util.math.*;
import java.util.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;

public class VoidESP extends Module
{
    public Setting<Float> range;
    public Setting<Boolean> down;
    private List<BlockPos> holes;
    
    public VoidESP() {
        super("VoidESP", "VoidESP", Module.Category.PLAYER, false, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)3.0f, (T)16.0f));
        this.down = (Setting<Boolean>)this.register(new Setting("Up", (T)false));
        this.holes = new ArrayList<BlockPos>();
    }
    
    public void onUpdate() {
        if (VoidESP.mc.player == null || VoidESP.mc.world == null) {
            return;
        }
        this.holes = this.calcHoles();
    }
    
    public void onRender3D(final Render3DEvent event) {
        for (int size = this.holes.size(), i = 0; i < size; ++i) {
            final BlockPos pos = this.holes.get(i);
            RenderUtil.renderCrosses(((boolean)this.down.getValue()) ? pos.up() : pos, new Color(255, 255, 255), 2.0f);
        }
    }
    
    public List<BlockPos> calcHoles() {
        final ArrayList<BlockPos> voidHoles = new ArrayList<BlockPos>();
        final List<BlockPos> positions = BlockUtil.getSphere(this.range.getValue(), false);
        for (int size = positions.size(), i = 0; i < size; ++i) {
            final BlockPos pos = positions.get(i);
            if (pos.getY() == 0) {
                if (VoidESP.mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                    voidHoles.add(pos);
                }
            }
        }
        return voidHoles;
    }
}
