//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.network.play.server.*;
import net.minecraft.block.state.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.util.*;

public class ContainerPreviewModule extends Module
{
    public Setting<Integer> av;
    public Setting<Integer> bv;
    public Setting<Integer> colorr;
    public Setting<Integer> colorg;
    public Setting<Integer> colorb;
    public Setting<Integer> colora;
    private HashMap<BlockPos, ArrayList<ItemStack>> PosItems;
    private ICamera camera;
    private int TotalSlots;
    public ScaledResolution scaledResolution;
    
    public ContainerPreviewModule() {
        super("ContainerPrev", "\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435 \u0441\u043e\u0434\u0435\u0440\u0436\u0438\u043c\u043e\u0435-\u043a\u043e\u043d\u0442\u0435\u0439\u043d\u0435\u0440\u0430", Category.MISC, true, false, false);
        this.av = (Setting<Integer>)this.register(new Setting("x", (T)256, (T)0, (T)1500));
        this.bv = (Setting<Integer>)this.register(new Setting("y", (T)256, (T)0, (T)1500));
        this.colorr = (Setting<Integer>)this.register(new Setting("Red", (T)100, (T)0, (T)255));
        this.colorg = (Setting<Integer>)this.register(new Setting("Green", (T)100, (T)0, (T)255));
        this.colorb = (Setting<Integer>)this.register(new Setting("Blue", (T)100, (T)0, (T)255));
        this.colora = (Setting<Integer>)this.register(new Setting("Alpha", (T)100, (T)0, (T)255));
        this.PosItems = new HashMap<BlockPos, ArrayList<ItemStack>>();
        this.camera = (ICamera)new Frustum();
        this.TotalSlots = 0;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketWindowItems) {
            final RayTraceResult ray = ContainerPreviewModule.mc.objectMouseOver;
            if (ray == null) {
                return;
            }
            if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
                return;
            }
            final IBlockState l_State = ContainerPreviewModule.mc.world.getBlockState(ray.getBlockPos());
            if (l_State == null) {
                return;
            }
            if (l_State.getBlock() != Blocks.CHEST && !(l_State.getBlock() instanceof BlockShulkerBox)) {
                return;
            }
            final SPacketWindowItems l_Packet = (SPacketWindowItems)event.getPacket();
            final BlockPos blockpos = ray.getBlockPos();
            if (this.PosItems.containsKey(blockpos)) {
                this.PosItems.remove(blockpos);
            }
            final ArrayList<ItemStack> l_List = new ArrayList<ItemStack>();
            for (int i = 0; i < l_Packet.getItemStacks().size(); ++i) {
                final ItemStack itemStack = l_Packet.getItemStacks().get(i);
                if (itemStack != null) {
                    if (i >= this.TotalSlots) {
                        break;
                    }
                    l_List.add(itemStack);
                }
            }
            this.PosItems.put(blockpos, l_List);
        }
        else if (event.getPacket() instanceof SPacketOpenWindow) {
            final SPacketOpenWindow l_Packet2 = (SPacketOpenWindow)event.getPacket();
            this.TotalSlots = l_Packet2.getSlotCount();
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent p_Event) {
        final RayTraceResult ray = ContainerPreviewModule.mc.objectMouseOver;
        if (ray == null) {
            return;
        }
        if (ray.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }
        if (!this.PosItems.containsKey(ray.getBlockPos())) {
            return;
        }
        final BlockPos l_Pos = ray.getBlockPos();
        final ArrayList<ItemStack> l_Items = this.PosItems.get(l_Pos);
        if (l_Items == null) {
            return;
        }
        final IBlockState pan4ur = ContainerPreviewModule.mc.world.getBlockState(ray.getBlockPos());
        final float[] bounds = this.convertBounds(l_Pos, p_Event.getPartialTicks(), 400, 400);
        if (bounds != null) {
            int l_I = 0;
            int l_Y = -20;
            int x = 0;
            for (final ItemStack stack : l_Items) {
                if (stack != null) {
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    RenderHelper.enableGUIStandardItemLighting();
                    RenderUtil.drawSmoothRect((float)(this.av.getValue() - 3), (float)(this.bv.getValue() - 50), (float)(this.av.getValue() + 150), (pan4ur.getBlock() != Blocks.CHEST) ? ((float)this.bv.getValue()) : ((float)(this.bv.getValue() + 48)), new Color(this.colorr.getValue(), this.colorg.getValue(), this.colorb.getValue(), this.colora.getValue()).getRGB());
                    GlStateManager.translate(bounds[0] + (bounds[2] - bounds[0]) / 2.0f + x + this.av.getValue(), l_Y + bounds[1] + (bounds[3] - bounds[1]) - ContainerPreviewModule.mc.fontRenderer.FONT_HEIGHT - 19.0f + this.bv.getValue(), 0.0f);
                    ContainerPreviewModule.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
                    ContainerPreviewModule.mc.getRenderItem().renderItemOverlays(ContainerPreviewModule.mc.fontRenderer, stack, 0, 0);
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                    x += 16;
                }
                if (++l_I % 9 == 0) {
                    x = 0;
                    l_Y += 15;
                }
            }
        }
    }
    
    private float[] convertBounds(final BlockPos e, final float partialTicks, final int width, final int height) {
        float x = -1.0f;
        float y = -1.0f;
        float w = (float)(width + 1);
        float h = (float)(height + 1);
        final Vec3d pos = new Vec3d((double)e.getX(), (double)e.getY(), (double)e.getZ());
        if (pos == null) {
            return null;
        }
        AxisAlignedBB bb = new AxisAlignedBB(e.getX() - ContainerPreviewModule.mc.getRenderManager().viewerPosX, e.getY() - ContainerPreviewModule.mc.getRenderManager().viewerPosY, e.getZ() - ContainerPreviewModule.mc.getRenderManager().viewerPosZ, e.getX() + 1 - ContainerPreviewModule.mc.getRenderManager().viewerPosX, e.getY() + 1 - ContainerPreviewModule.mc.getRenderManager().viewerPosY, e.getZ() + 1 - ContainerPreviewModule.mc.getRenderManager().viewerPosZ);
        bb = bb.expand(0.15000000596046448, 0.10000000149011612, 0.15000000596046448);
        this.camera.setPosition(ContainerPreviewModule.mc.getRenderViewEntity().posX, ContainerPreviewModule.mc.getRenderViewEntity().posY, ContainerPreviewModule.mc.getRenderViewEntity().posZ);
        if (!this.camera.isBoundingBoxInFrustum(bb)) {}
        final Vec3d[] array;
        final Vec3d[] corners = array = new Vec3d[] { new Vec3d(bb.minX - bb.maxX + 0.0, 0.0, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, 0.0, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.minX - bb.maxX + 0.0, 0.0, bb.maxZ - bb.minZ - 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, 0.0, bb.maxZ - bb.minZ - 0.0), new Vec3d(bb.minX - bb.maxX + 0.0, bb.maxY - bb.minY, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, bb.maxY - bb.minY, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.minX - bb.maxX + 0.0, bb.maxY - bb.minY, bb.maxZ - bb.minZ - 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, bb.maxY - bb.minY, bb.maxZ - bb.minZ - 0.0) };
        for (final Vec3d vec : array) {
            final GLUProjection.Projection projection = GLUProjection.getInstance().project(pos.x + vec.x - ContainerPreviewModule.mc.getRenderManager().viewerPosX, pos.y + vec.y - ContainerPreviewModule.mc.getRenderManager().viewerPosY, pos.z + vec.z - ContainerPreviewModule.mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, false);
            if (projection == null) {
                return null;
            }
            x = Math.max(x, (float)projection.getX());
            y = Math.max(y, (float)projection.getY());
            w = Math.min(w, (float)projection.getX());
            h = Math.min(h, (float)projection.getY());
        }
        if (x != -1.0f && y != -1.0f && w != width + 1 && h != height + 1) {
            return new float[] { x, y, w, h };
        }
        return null;
    }
}
