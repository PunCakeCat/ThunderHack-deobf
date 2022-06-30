//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import java.awt.*;
import net.minecraft.util.math.*;
import com.ibm.icu.math.*;
import java.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.*;

public class DMGParticles extends Module
{
    public Setting<Integer> deleteAfter;
    private final Map<Integer, Float> hpData;
    private final List<Particle> particles;
    Timer timer;
    
    public DMGParticles() {
        super("DMGParticles", "\u043f\u0430\u0440\u0442\u0438\u043a\u043b\u044b \u0443\u0440\u043e\u043d\u0430", Module.Category.RENDER, true, false, false);
        this.deleteAfter = (Setting<Integer>)this.register(new Setting("Remove Ticks", (T)7, (T)1, (T)20));
        this.hpData = (Map<Integer, Float>)Maps.newHashMap();
        this.particles = (List<Particle>)Lists.newArrayList();
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onRespawn(final DeathEvent event) {
        if (event.player == DMGParticles.mc.player) {
            this.particles.clear();
        }
    }
    
    public void onUpdate() {
        for (final Entity entity : DMGParticles.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase ent = (EntityLivingBase)entity;
                final double lastHp = this.hpData.getOrDefault(ent.getEntityId(), ent.getMaxHealth());
                this.hpData.remove(entity.getEntityId());
                this.hpData.put(entity.getEntityId(), ent.getHealth());
                if (lastHp == ent.getHealth()) {
                    continue;
                }
                Color color;
                if (lastHp > ent.getHealth()) {
                    color = Color.red;
                }
                else {
                    color = Color.GREEN;
                }
                final Vec3d loc = new Vec3d(entity.posX + Math.random() * 0.5 * ((Math.random() > 0.5) ? -1 : 1), entity.getEntityBoundingBox().minY + (entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY) * 0.5, entity.posZ + Math.random() * 0.5 * ((Math.random() > 0.5) ? -1 : 1));
                final double str = new BigDecimal(Math.abs(lastHp - ent.getHealth())).setScale(1, 4).doubleValue();
                this.particles.add(new Particle("" + str, loc.x, loc.y, loc.z, color));
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent e) {
        if (this.timer.passedMs(this.deleteAfter.getValue() * 300)) {
            this.particles.clear();
            this.timer.reset();
        }
        if (!this.particles.isEmpty()) {
            for (final Particle p : this.particles) {
                if (p != null) {
                    GlStateManager.pushMatrix();
                    GlStateManager.enablePolygonOffset();
                    GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
                    GlStateManager.translate(p.posX - DMGParticles.mc.getRenderManager().renderPosX, p.posY - DMGParticles.mc.getRenderManager().renderPosY, p.posZ - DMGParticles.mc.getRenderManager().renderPosZ);
                    GlStateManager.rotate(-DMGParticles.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(DMGParticles.mc.getRenderManager().playerViewX, (DMGParticles.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-0.03, -0.03, 0.03);
                    GL11.glDepthMask(false);
                    Util.fr.drawStringWithShadow(p.str, (float)(-Util.fr.getStringWidth(p.str) * 0.5), (float)(-Util.fr.FONT_HEIGHT + 1), p.color.getRGB());
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glDepthMask(true);
                    GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                    GlStateManager.disablePolygonOffset();
                    GlStateManager.resetColor();
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    class Particle
    {
        public String str;
        public double posX;
        public double posY;
        public double posZ;
        public Color color;
        public int ticks;
        
        public Particle(final String str, final double posX, final double posY, final double posZ, final Color color) {
            this.str = str;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.color = color;
            this.ticks = 0;
        }
    }
}
