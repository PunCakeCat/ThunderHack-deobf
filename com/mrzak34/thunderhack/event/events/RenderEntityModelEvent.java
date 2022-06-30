//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.event.events;

import com.mrzak34.thunderhack.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

@Cancelable
public class RenderEntityModelEvent extends EventStage
{
    public ModelBase modelBase;
    public Entity entity;
    public float limbSwing;
    public float limbSwingAmount;
    public float age;
    public float headYaw;
    public float headPitch;
    public float scale;
    
    public RenderEntityModelEvent(final int stage, final ModelBase modelBase, final Entity entity, final float limbSwing, final float limbSwingAmount, final float age, final float headYaw, final float headPitch, final float scale) {
        super(stage);
        this.modelBase = modelBase;
        this.entity = entity;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.age = age;
        this.headYaw = headYaw;
        this.headPitch = headPitch;
        this.scale = scale;
    }
}
