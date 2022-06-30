//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.item.*;
import com.mrzak34.thunderhack.features.modules.render.*;
import com.mrzak34.thunderhack.features.modules.funnygame.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;
import org.apache.logging.log4j.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    private static final Logger LOGGER;
    @Shadow
    protected ModelBase mainModel;
    @Shadow
    protected boolean renderMarker;
    float red;
    float green;
    float blue;
    
    protected MixinRenderLivingBase(final RenderManager renderManager) {
        super(renderManager);
        this.red = 0.0f;
        this.green = 0.0f;
        this.blue = 0.0f;
    }
    
    @Shadow
    protected abstract boolean isVisible(final EntityLivingBase p0);
    
    @Shadow
    protected abstract float getSwingProgress(final T p0, final float p1);
    
    @Shadow
    protected abstract float interpolateRotation(final float p0, final float p1, final float p2);
    
    @Shadow
    protected abstract float handleRotationFloat(final T p0, final float p1);
    
    @Shadow
    protected abstract void applyRotations(final T p0, final float p1, final float p2, final float p3);
    
    @Shadow
    public abstract float prepareScale(final T p0, final float p1);
    
    @Shadow
    protected abstract void unsetScoreTeamColor();
    
    @Shadow
    protected abstract boolean setScoreTeamColor(final T p0);
    
    @Shadow
    protected abstract void renderLivingAt(final T p0, final double p1, final double p2, final double p3);
    
    @Shadow
    protected abstract void unsetBrightness();
    
    @Shadow
    protected abstract void renderModel(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    @Shadow
    protected abstract void renderLayers(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
    
    @Shadow
    protected abstract boolean setDoRenderBrightness(final T p0, final float p1);
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") }, cancellable = true)
    private <T extends EntityLivingBase> void injectChamsPre(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo callbackInfo) {
        if (entity instanceof EntityArmorStand && ((boolean)NoRender.getInstance().noarmorstands.getValue() || (boolean)((AntiTittle)Thunderhack.moduleManager.getModuleByClass((Class)AntiTittle.class)).armorstands.getValue())) {
            callbackInfo.cancel();
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
