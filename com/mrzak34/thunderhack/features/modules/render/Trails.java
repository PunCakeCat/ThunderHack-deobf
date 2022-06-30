//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import java.util.concurrent.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.helper.*;
import java.util.function.*;
import java.util.*;
import java.awt.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;

public class Trails extends Module
{
    public Setting<Float> width;
    public Setting<Boolean> arrows;
    public Setting<Boolean> pearls;
    public Setting<Boolean> snowballs;
    public Setting<Integer> time;
    private Setting<mode2> Mode2;
    private final Setting<ColorSetting> color;
    protected Map<Integer, TimeAnimation> ids;
    protected Map<Integer, List<Trace>> traceLists;
    protected Map<Integer, Trace> traces;
    public static final Vec3d ORIGIN;
    
    public Trails() {
        super("Trails", "viewmodle", Module.Category.RENDER, true, false, false);
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)1.6f, (T)0.1f, (T)10.0f));
        this.arrows = (Setting<Boolean>)this.register(new Setting("Arrows", (T)false));
        this.pearls = (Setting<Boolean>)this.register(new Setting("Pearls", (T)false));
        this.snowballs = (Setting<Boolean>)this.register(new Setting("Snowballs", (T)false));
        this.time = (Setting<Integer>)this.register(new Setting("Time", (T)1, (T)1, (T)10));
        this.Mode2 = (Setting<mode2>)this.register(new Setting("Color Mode", (T)mode2.Custom));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2013200640)));
        this.ids = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.traceLists = new ConcurrentHashMap<Integer, List<Trace>>();
        this.traces = new ConcurrentHashMap<Integer, Trace>();
    }
    
    public void onEnable() {
        this.ids = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.traces = new ConcurrentHashMap<Integer, Trace>();
        this.traceLists = new ConcurrentHashMap<Integer, List<Trace>>();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            for (final int id : ((SPacketDestroyEntities)event.getPacket()).getEntityIDs()) {
                if (this.ids.containsKey(id)) {
                    this.ids.get(id).play();
                }
            }
        }
        if (event.getPacket() instanceof SPacketSpawnObject && ((this.pearls.getValue() && ((SPacketSpawnObject)event.getPacket()).getType() == 65) || (this.arrows.getValue() && ((SPacketSpawnObject)event.getPacket()).getType() == 60) || (this.snowballs.getValue() && ((SPacketSpawnObject)event.getPacket()).getType() == 61))) {
            final TimeAnimation animation = new TimeAnimation(this.time.getValue() * 1000, 0.0, this.color.getValue().getAlpha(), false, AnimationMode.LINEAR);
            animation.stop();
            this.ids.put(((SPacketSpawnObject)event.getPacket()).getEntityID(), animation);
            this.traceLists.put(((SPacketSpawnObject)event.getPacket()).getEntityID(), new ArrayList<Trace>());
            try {
                this.traces.put(((SPacketSpawnObject)event.getPacket()).getEntityID(), new Trace(0, null, Trails.mc.world.provider.getDimensionType(), new Vec3d(((SPacketSpawnObject)event.getPacket()).getX(), ((SPacketSpawnObject)event.getPacket()).getY(), ((SPacketSpawnObject)event.getPacket()).getZ()), new ArrayList<Trace.TracePos>()));
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        for (final Map.Entry<Integer, List<Trace>> entry : this.traceLists.entrySet()) {
            startRender();
            GL11.glLineWidth((float)this.width.getValue());
            final TimeAnimation animation = this.ids.get(entry.getKey());
            animation.add(event.getPartialTicks());
            if (this.Mode2.getValue() == mode2.Custom) {
                GL11.glColor4f((float)this.color.getValue().getRed(), (float)this.color.getValue().getGreen(), (float)this.color.getValue().getBlue(), MathHelper.clamp((float)(this.color.getValue().getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            }
            if (this.Mode2.getValue() == mode2.Astolfo) {
                final Color color = PaletteHelper.astolfo(false, 1);
                GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), MathHelper.clamp((float)(color.getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            }
            if (this.Mode2.getValue() == mode2.Rainbow) {
                final Color color = PaletteHelper.rainbow(300, 1.0f, 1.0f);
                GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), MathHelper.clamp((float)(color.getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            }
            entry.getValue().forEach(trace -> {
                GL11.glBegin(3);
                trace.getTrace().forEach(this::renderVec);
                GL11.glEnd();
                return;
            });
            if (this.Mode2.getValue() == mode2.Custom) {
                GL11.glColor4f((float)this.color.getValue().getRed(), (float)this.color.getValue().getGreen(), (float)this.color.getValue().getBlue(), MathHelper.clamp((float)(this.color.getValue().getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            }
            if (this.Mode2.getValue() == mode2.Astolfo) {
                final Color color2 = PaletteHelper.astolfo(false, 1);
                GL11.glColor4f((float)color2.getRed(), (float)color2.getGreen(), (float)color2.getBlue(), MathHelper.clamp((float)(color2.getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            }
            if (this.Mode2.getValue() == mode2.Astolfo) {
                final Color color2 = PaletteHelper.rainbow(300, 1.0f, 1.0f);
                GL11.glColor4f((float)color2.getRed(), (float)color2.getGreen(), (float)color2.getBlue(), MathHelper.clamp((float)(color2.getAlpha() - animation.getCurrent() / 255.0), 0.0f, 255.0f));
            }
            GL11.glBegin(3);
            final Trace trace2 = this.traces.get(entry.getKey());
            if (trace2 != null) {
                trace2.getTrace().forEach(this::renderVec);
            }
            GL11.glEnd();
            endRender();
        }
    }
    
    private void renderVec(final Trace.TracePos tracePos) {
        final double x = tracePos.getPos().x - getRenderPosX();
        final double y = tracePos.getPos().y - getRenderPosY();
        final double z = tracePos.getPos().z - getRenderPosZ();
        GL11.glVertex3d(x, y, z);
    }
    
    public void onUpdate() {
        if (Trails.mc.world == null) {
            return;
        }
        if (this.ids.keySet().isEmpty()) {
            return;
        }
        for (final Integer id : this.ids.keySet()) {
            if (id == null) {
                continue;
            }
            if (Trails.mc.world.loadedEntityList == null) {
                return;
            }
            if (Trails.mc.world.loadedEntityList.isEmpty()) {
                return;
            }
            Trace idTrace = this.traces.get(id);
            final Entity entity = Trails.mc.world.getEntityByID((int)id);
            if (entity != null) {
                final Vec3d vec = entity.getPositionVector();
                if (vec == null) {
                    continue;
                }
                if (vec.equals((Object)Trails.ORIGIN)) {
                    continue;
                }
                if (!this.traces.containsKey(id) || idTrace == null) {
                    this.traces.put(id, new Trace(0, null, Trails.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>()));
                    idTrace = this.traces.get(id);
                }
                List<Trace.TracePos> trace = idTrace.getTrace();
                final Vec3d vec3d = trace.isEmpty() ? vec : trace.get(trace.size() - 1).getPos();
                if (!trace.isEmpty() && (vec.distanceTo(vec3d) > 100.0 || idTrace.getType() != Trails.mc.world.provider.getDimensionType())) {
                    this.traceLists.get(id).add(idTrace);
                    trace = new ArrayList<Trace.TracePos>();
                    this.traces.put(id, new Trace(this.traceLists.get(id).size() + 1, null, Trails.mc.world.provider.getDimensionType(), vec, new ArrayList<Trace.TracePos>()));
                }
                if (trace.isEmpty() || !vec.equals((Object)vec3d)) {
                    trace.add(new Trace.TracePos(vec));
                }
            }
            final TimeAnimation animation = this.ids.get(id);
            if (entity instanceof EntityArrow && (entity.onGround || entity.collided || !entity.isAirBorne)) {
                animation.play();
            }
            if (animation == null || this.color.getValue().getAlpha() - animation.getCurrent() > 0.0) {
                continue;
            }
            animation.stop();
            this.ids.remove(id);
            this.traceLists.remove(id);
            this.traces.remove(id);
        }
    }
    
    public static double getRenderPosX() {
        return ((IRenderManager)Trails.mc.getRenderManager()).getRenderPosX();
    }
    
    public static double getRenderPosY() {
        return ((IRenderManager)Trails.mc.getRenderManager()).getRenderPosY();
    }
    
    public static void startRender() {
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }
    
    public static void endRender() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public static double getRenderPosZ() {
        return ((IRenderManager)Trails.mc.getRenderManager()).getRenderPosZ();
    }
    
    static {
        ORIGIN = new Vec3d(8.0, 64.0, 8.0);
    }
    
    public enum mode2
    {
        Custom, 
        Rainbow, 
        Astolfo;
    }
}
