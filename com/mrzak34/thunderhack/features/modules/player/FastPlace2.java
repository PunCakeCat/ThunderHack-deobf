//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.helper.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.util.math.*;

public class FastPlace2 extends Module
{
    public static BlockPos target;
    private Setting<Integer> threshold;
    public Setting<Integer> waterMarkZ1;
    public Setting<Integer> waterMarkZ2;
    private Setting<Integer> dlay;
    private Setting<Integer> armdlay;
    public Setting<SubBind> aboba;
    public static boolean isMending;
    private boolean shouldMend;
    private Timer timer;
    private Timer timer2;
    int arm1;
    int arm2;
    int arm3;
    int arm4;
    int totalarmor;
    boolean ch;
    boolean po;
    boolean sh;
    boolean bo;
    private float yawi;
    int startingItem;
    public boolean rotate;
    
    public FastPlace2() {
        super("FastPlace", "\u043f\u0430\u0439\u0440\u043e \u0430\u0432\u0442\u043e\u043c\u0435\u043d\u0434", Module.Category.PLAYER, true, false, false);
        this.threshold = (Setting<Integer>)this.register(new Setting("Percent", (T)100, (T)0, (T)100));
        this.waterMarkZ1 = (Setting<Integer>)this.register(new Setting("Y", (T)10, (T)0, (T)524));
        this.waterMarkZ2 = (Setting<Integer>)this.register(new Setting("X", (T)20, (T)0, (T)862));
        this.dlay = (Setting<Integer>)this.register(new Setting("delay", (T)100, (T)0, (T)100));
        this.armdlay = (Setting<Integer>)this.register(new Setting("ArmorDelay", (T)100, (T)0, (T)1000));
        this.aboba = (Setting<SubBind>)this.register(new Setting("Butt", (T)new SubBind(56)));
        this.shouldMend = false;
        this.timer = new Timer();
        this.timer2 = new Timer();
        this.rotate = true;
    }
    
    public void setYaw(final float yawi) {
        this.yawi = yawi;
        final Minecraft mc = Util.mc;
        Minecraft.getMinecraft();
        Util.mc.player.rotationYawHead = yawi;
        final Minecraft mc2 = Util.mc;
        Minecraft.getMinecraft();
        Util.mc.player.renderYawOffset = yawi;
        Util.mc.player.rotationYawHead = yawi;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent e) {
        if (FastPlace2.mc.player == null || FastPlace2.mc.world == null) {
            return;
        }
        final ItemStack[] armorStacks2 = { FastPlace2.mc.player.inventory.getStackInSlot(39), FastPlace2.mc.player.inventory.getStackInSlot(38), FastPlace2.mc.player.inventory.getStackInSlot(37), FastPlace2.mc.player.inventory.getStackInSlot(36) };
        final ItemStack stack2 = armorStacks2[0];
        final ItemStack stack3 = armorStacks2[1];
        final ItemStack stack4 = armorStacks2[2];
        final ItemStack stack5 = armorStacks2[3];
        if (PlayerUtils.isKeyDown(this.aboba.getValue().getKey()) && (ArmorUtils.calculatePercentage(stack2) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack3) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack4) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack5) < this.threshold.getValue())) {
            final int itemSlot = this.getXpSlot();
            final boolean changeItem = FastPlace2.mc.player.inventory.currentItem != itemSlot && itemSlot != -1;
            this.startingItem = FastPlace2.mc.player.inventory.currentItem;
            if (changeItem) {
                FastPlace2.mc.player.inventory.currentItem = itemSlot;
                FastPlace2.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(itemSlot));
            }
        }
        if (FastPlace2.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.EXPERIENCE_BOTTLE || (this.getXpSlot() != -1 && FastPlace2.target != null)) {
            if (PlayerUtils.isKeyDown(this.aboba.getValue().getKey()) && (ArmorUtils.calculatePercentage(stack2) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack3) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack4) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack5) < this.threshold.getValue())) {
                this.shouldMend = false;
                FastPlace2.target = FastPlace2.mc.player.getPosition().add(0.0, -1.0, 0.0);
                final ItemStack[] armorStacks3 = { FastPlace2.mc.player.inventory.getStackInSlot(39), FastPlace2.mc.player.inventory.getStackInSlot(38), FastPlace2.mc.player.inventory.getStackInSlot(37), FastPlace2.mc.player.inventory.getStackInSlot(36) };
                for (int i = 0; i < 4; ++i) {
                    final ItemStack stack6 = armorStacks3[i];
                    if (stack6.getItem() instanceof ItemArmor) {
                        if (ArmorUtils.calculatePercentage(stack6) >= this.threshold.getValue()) {
                            for (int s = 0; s < 36; ++s) {
                                final ItemStack emptyStack = FastPlace2.mc.player.inventory.getStackInSlot(s);
                                if (emptyStack.isEmpty()) {
                                    if (emptyStack.getItem() == Items.AIR) {
                                        FastPlace2.isMending = true;
                                        if (this.timer2.passedMs(this.armdlay.getValue())) {
                                            FastPlace2.mc.playerController.windowClick(FastPlace2.mc.player.inventoryContainer.windowId, i + 5, 0, ClickType.PICKUP, (EntityPlayer)FastPlace2.mc.player);
                                            FastPlace2.mc.playerController.windowClick(FastPlace2.mc.player.inventoryContainer.windowId, (s < 9) ? (s + 36) : s, 0, ClickType.PICKUP, (EntityPlayer)FastPlace2.mc.player);
                                            FastPlace2.mc.playerController.windowClick(FastPlace2.mc.player.inventoryContainer.windowId, i + 5, 0, ClickType.PICKUP, (EntityPlayer)FastPlace2.mc.player);
                                            FastPlace2.mc.playerController.updateController();
                                            this.timer2.reset();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < 4; ++i) {
                    final ItemStack stack6 = armorStacks3[i];
                    if (stack6.getItem() instanceof ItemArmor) {
                        if (ArmorUtils.calculatePercentage(stack6) < this.threshold.getValue()) {
                            this.shouldMend = true;
                        }
                    }
                }
                if (!this.shouldMend) {
                    FastPlace2.isMending = false;
                }
                if (this.shouldMend && FastPlace2.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle && this.timer.passedMs(this.dlay.getValue())) {
                    FastPlace2.mc.playerController.processRightClick((EntityPlayer)FastPlace2.mc.player, (World)FastPlace2.mc.world, EnumHand.MAIN_HAND);
                    this.timer.reset();
                }
            }
            else {
                FastPlace2.isMending = false;
                FastPlace2.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.startingItem));
                this.arm1 = 0;
                this.arm2 = 0;
                this.arm3 = 0;
                this.arm4 = 0;
                this.totalarmor = 0;
                this.ch = false;
                this.po = false;
                this.sh = false;
                this.bo = false;
                FastPlace2.target = null;
            }
        }
    }
    
    private int getXpSlot() {
        ItemStack stack = FastPlace2.mc.player.getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemExpBottle) {
            return FastPlace2.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            stack = FastPlace2.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemExpBottle) {
                return i;
            }
        }
        return -1;
    }
    
    public void onDisable() {
        FastPlace2.isMending = false;
        FastPlace2.target = null;
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent e) {
        final ItemStack[] armorStacks21 = { FastPlace2.mc.player.inventory.getStackInSlot(39), FastPlace2.mc.player.inventory.getStackInSlot(38), FastPlace2.mc.player.inventory.getStackInSlot(37), FastPlace2.mc.player.inventory.getStackInSlot(36) };
        final ItemStack stack21 = armorStacks21[0];
        final ItemStack stack22 = armorStacks21[1];
        final ItemStack stack23 = armorStacks21[2];
        final ItemStack stack24 = armorStacks21[3];
        if (PlayerUtils.isKeyDown(this.aboba.getValue().getKey()) && (ArmorUtils.calculatePercentage(stack21) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack22) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack23) < this.threshold.getValue() || ArmorUtils.calculatePercentage(stack24) < this.threshold.getValue())) {
            final ScaledResolution sr = new ScaledResolution(FastPlace2.mc);
            RenderUtil.drawSmoothRect(this.waterMarkZ2.getValue(), this.waterMarkZ1.getValue(), (float)(106 + this.waterMarkZ2.getValue()), (float)(35 + this.waterMarkZ1.getValue()), new Color(35, 35, 40, 230).getRGB());
            RenderUtil.drawSmoothRect((float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 12), (float)(103 + this.waterMarkZ2.getValue()), (float)(15 + this.waterMarkZ1.getValue()), new Color(51, 51, 58, 230).getRGB());
            final ItemStack[] armorStacks22 = { FastPlace2.mc.player.inventory.getStackInSlot(39), FastPlace2.mc.player.inventory.getStackInSlot(38), FastPlace2.mc.player.inventory.getStackInSlot(37), FastPlace2.mc.player.inventory.getStackInSlot(36) };
            final ItemStack stack25 = armorStacks22[0];
            final ItemStack stack26 = armorStacks22[1];
            final ItemStack stack27 = armorStacks22[2];
            final ItemStack stack28 = armorStacks22[3];
            if ((int)ArmorUtils.calculatePercentage(stack25) >= this.arm1) {
                this.arm1 = (int)ArmorUtils.calculatePercentage(stack25);
            }
            if ((int)ArmorUtils.calculatePercentage(stack26) >= this.arm2) {
                this.arm2 = (int)ArmorUtils.calculatePercentage(stack26);
            }
            if ((int)ArmorUtils.calculatePercentage(stack27) >= this.arm3) {
                this.arm3 = (int)ArmorUtils.calculatePercentage(stack27);
            }
            if ((int)ArmorUtils.calculatePercentage(stack28) >= this.arm4) {
                this.arm4 = (int)ArmorUtils.calculatePercentage(stack28);
            }
            this.totalarmor = (this.arm1 + this.arm3 + this.arm4 + this.arm2) / 4;
            final float progress = (this.arm1 + this.arm3 + this.arm4 + this.arm2) / 400.0f;
            final int color = PaletteHelper.fade(new Color(255, 0, 0, 255).getRGB(), new Color(0, 255, 0, 255).getRGB(), progress);
            final int expCount = this.getExpCount();
            FastPlace2.mc.renderItem.renderItemIntoGUI(new ItemStack(Items.EXPERIENCE_BOTTLE), (int)(this.waterMarkZ2.getValue() + this.offset + 70.0f + 11.0f), this.waterMarkZ1.getValue() + 17);
            final String s3 = String.valueOf(expCount);
            Util.fr.drawStringWithShadow(s3, this.waterMarkZ2.getValue() + this.offset + 85.0f + 11.0f, this.waterMarkZ1.getValue() + 9 + this.offset + 17.0f, 16777215);
            RenderUtil.drawSmoothRect((float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 12), (float)(this.totalarmor + this.waterMarkZ2.getValue() + 5), (float)(15 + this.waterMarkZ1.getValue()), color);
            Util.fr.drawStringWithShadow("Mending...", (float)(this.waterMarkZ2.getValue() + 3), (float)(this.waterMarkZ1.getValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
            final int width = this.waterMarkZ2.getValue() - 12;
            final int height = this.waterMarkZ1.getValue() + 17;
            GlStateManager.enableTexture2D();
            final int i = width;
            int iteration = 0;
            final int y = height;
            for (final ItemStack is : FastPlace2.mc.player.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) {
                    continue;
                }
                final int x = i - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                RenderUtil.itemRender.zLevel = 200.0f;
                RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
                RenderUtil.itemRender.renderItemOverlayIntoGUI(FastPlace2.mc.fontRenderer, is, x, y, "");
                RenderUtil.itemRender.zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final String s4 = (is.getCount() > 1) ? (is.getCount() + "") : "";
                this.renderer.drawStringWithShadow(s4, (float)(x + 19 - 2 - this.renderer.getStringWidth(s4)), (float)(y + 9), 16777215);
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
    
    private int getExpCount() {
        int expCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (FastPlace2.mc.player.inventory.getStackInSlot(i).getItem().equals(Items.EXPERIENCE_BOTTLE)) {
                expCount += FastPlace2.mc.player.inventory.getStackInSlot(i).stackSize;
            }
        }
        if (FastPlace2.mc.player.getHeldItemOffhand().getItem().equals(Items.EXPERIENCE_BOTTLE)) {
            ++expCount;
        }
        return expCount;
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && FastPlace2.target != null) {
            lookAt(FastPlace2.target);
        }
    }
    
    public static void lookAt(final BlockPos blockPos) {
        final float[] angle = calcAngle(FastPlace2.mc.player.getPositionEyes(FastPlace2.mc.getRenderPartialTicks()), new Vec3d((Vec3i)blockPos));
        setPlayerRotations(angle[0], angle[1]);
        Util.mc.player.renderYawOffset = angle[0];
        Util.mc.player.rotationYawHead = angle[0];
    }
    
    public static void setPlayerRotations(final float yaw, final float pitch) {
        FastPlace2.mc.player.rotationYaw = yaw;
        FastPlace2.mc.player.rotationYawHead = yaw;
        FastPlace2.mc.player.rotationPitch = pitch;
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    static {
        FastPlace2.isMending = false;
    }
}
