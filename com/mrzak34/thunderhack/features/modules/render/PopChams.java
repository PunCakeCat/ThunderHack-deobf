//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.concurrent.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import com.mrzak34.thunderhack.util.*;

public class PopChams extends Module
{
    public Setting<Boolean> self;
    public Setting<Boolean> anim;
    public Setting<Float> maxOffset;
    public Setting<Float> speed;
    private final Setting<ColorSetting> color;
    public final CopyOnWriteArrayList<Person> popList;
    private int fps;
    private final LinkedList<Long> frames;
    
    public PopChams() {
        super("PopChams", "Renders when some1 pops", Module.Category.RENDER, true, false, false);
        this.self = (Setting<Boolean>)this.register(new Setting("SelfPop", (T)false));
        this.anim = (Setting<Boolean>)this.register(new Setting("Copy Animations", (T)false));
        this.maxOffset = (Setting<Float>)this.register(new Setting("MaxOffset", (T)0.1f, (T)0.1f, (T)15.0f));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)0.1f, (T)0.1f, (T)10.0f));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.popList = new CopyOnWriteArrayList<Person>();
        this.frames = new LinkedList<Long>();
    }
    
    @SubscribeEvent
    public void onTotemPop(final TotemPopEvent e) {
        if (!this.self.getValue() && e.getEntity() == PopChams.mc.player) {
            return;
        }
        final EntityPlayer sp = e.getEntity();
        final EntityPlayer entity = new EntityPlayer(PopChams.mc.world, new GameProfile(sp.getUniqueID(), sp.getName())) {
            public boolean isSpectator() {
                return false;
            }
            
            public boolean isCreative() {
                return false;
            }
        };
        entity.copyLocationAndAnglesFrom((Entity)sp);
        if (this.anim.getValue()) {
            entity.limbSwing = sp.limbSwing;
            entity.limbSwingAmount = sp.limbSwingAmount;
            entity.setSneaking(sp.isSneaking());
        }
        this.popList.add(new Person(entity));
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent e) {
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        this.popList.forEach(person -> {
            person.update(this.popList);
            person.modelPlayer.bipedLeftLegwear.showModel = false;
            person.modelPlayer.bipedRightLegwear.showModel = false;
            person.modelPlayer.bipedLeftArmwear.showModel = false;
            person.modelPlayer.bipedRightArmwear.showModel = false;
            person.modelPlayer.bipedBodyWear.showModel = false;
            person.modelPlayer.bipedHead.showModel = true;
            person.modelPlayer.bipedHeadwear.showModel = false;
            GlStateManager.color(this.color.getValue().getRed() / 255.0f, this.color.getValue().getGreen() / 255.0f, this.color.getValue().getBlue() / 255.0f, (float)person.alpha / 255.0f);
            GL11.glPolygonMode(1032, 6914);
            renderEntity((EntityLivingBase)person.player, (ModelBase)person.modelPlayer, person.player.limbSwing, person.player.limbSwingAmount, (float)person.player.ticksExisted, person.player.rotationYawHead, person.player.rotationPitch, 1.0f);
            GL11.glPolygonMode(1032, 6913);
            renderEntity((EntityLivingBase)person.player, (ModelBase)person.modelPlayer, person.player.limbSwing, person.player.limbSwingAmount, (float)person.player.ticksExisted, person.player.rotationYawHead, person.player.rotationPitch, 1.0f);
            GL11.glPolygonMode(1032, 6914);
            return;
        });
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }
    
    public static void renderEntity(final EntityLivingBase entity, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (modelBase instanceof ModelPlayer) {
            final ModelPlayer modelPlayer = (ModelPlayer)modelBase;
            modelPlayer.bipedBodyWear.showModel = false;
            modelPlayer.bipedLeftLegwear.showModel = false;
            modelPlayer.bipedRightLegwear.showModel = false;
            modelPlayer.bipedLeftArmwear.showModel = false;
            modelPlayer.bipedRightArmwear.showModel = false;
            modelPlayer.bipedHeadwear.showModel = true;
            modelPlayer.bipedHead.showModel = false;
        }
        final float partialTicks = PopChams.mc.getRenderPartialTicks();
        final double x = entity.posX - PopChams.mc.getRenderManager().viewerPosX;
        double y = entity.posY - PopChams.mc.getRenderManager().viewerPosY;
        final double z = entity.posZ - PopChams.mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            y -= 0.125;
        }
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        final float f4 = prepareScale(entity, scale);
        final float yaw = entity.rotationYawHead;
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, 0.0f, yaw, entity.rotationPitch, f4, (Entity)entity);
        modelBase.render((Entity)entity, limbSwing, limbSwingAmount, 0.0f, yaw, entity.rotationPitch, f4);
        GlStateManager.popMatrix();
    }
    
    private static float prepareScale(final EntityLivingBase entity, final float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        final double widthX = entity.getRenderBoundingBox().maxX - entity.getRenderBoundingBox().minX;
        final double widthZ = entity.getRenderBoundingBox().maxZ - entity.getRenderBoundingBox().minZ;
        GlStateManager.scale(scale + widthX, (double)(scale * entity.height), scale + widthZ);
        final float f = 0.0625f;
        GlStateManager.translate(0.0f, -1.501f, 0.0f);
        return f;
    }
    
    public void onUpdate() {
        final long time = System.nanoTime();
        this.frames.add(time);
        while (true) {
            final long f = this.frames.getFirst();
            final long ONE_SECOND = 1000000000L;
            if (time - f <= 1000000000L) {
                break;
            }
            this.frames.remove();
        }
        this.fps = this.frames.size();
    }
    
    public int getFPS() {
        return this.fps;
    }
    
    public float getFrametime() {
        return 1.0f / this.fps;
    }
    
    public class Person
    {
        private double alpha;
        private double ticks;
        private final EntityPlayer player;
        private final ModelPlayer modelPlayer;
        
        public Person(final EntityPlayer player) {
            this.player = player;
            this.modelPlayer = new ModelPlayer(0.0f, false);
            this.alpha = 180.0;
            this.ticks = 0.0;
        }
        
        public void update(final CopyOnWriteArrayList<Person> arrayList) {
            ++this.ticks;
            if (this.alpha <= 0.0) {
                arrayList.remove(this);
                Util.mc.world.removeEntity((Entity)this.player);
                return;
            }
            this.alpha -= 180.0f / PopChams.this.speed.getValue() * PopChams.this.getFrametime();
            final EntityPlayer player = this.player;
            player.posY += PopChams.this.maxOffset.getValue() / PopChams.this.speed.getValue() * PopChams.this.getFrametime();
        }
    }
}
