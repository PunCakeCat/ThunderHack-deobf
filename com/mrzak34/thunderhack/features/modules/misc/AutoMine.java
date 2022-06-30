//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.settings.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.features.modules.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class AutoMine extends Module
{
    private Setting<Mode> mode;
    public Setting<Boolean> focused;
    public Setting<Boolean> requirepickaxe;
    public Setting<Boolean> switchbool;
    public Setting<Boolean> autodisable;
    private BlockPos blockpos;
    
    public AutoMine() {
        super("AutoMine", "\u0415\u0441\u043b\u0438 \u043b\u0435\u043d\u044c \u0441\u0430\u043c\u043e\u043c\u0443", Category.MISC, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.FEET));
        this.focused = (Setting<Boolean>)this.register(new Setting("Focused", (T)true));
        this.requirepickaxe = (Setting<Boolean>)this.register(new Setting("RequirePickaxe", (T)false));
        this.switchbool = (Setting<Boolean>)this.register(new Setting("Switch", (T)false));
        this.autodisable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)false));
        this.blockpos = null;
    }
    
    @Override
    public void onEnable() {
        this.blockpos = null;
    }
    
    @Override
    public void onDisable() {
        this.blockpos = null;
        KeyBinding.setKeyBindState(AutoMine.mc.gameSettings.keyBindAttack.getKeyCode(), false);
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.CONTINIOUS) {
            if (!this.focused.getValue()) {
                ((AccessorMinecraft)AutoMine.mc).setLeftClickCounter(0);
            }
            ((AccessorMinecraft)AutoMine.mc).invokeSendClickBlockToController(true);
        }
    }
    
    @SubscribeEvent
    public void onMotionUpdate(final MotionUpdateEvent event) {
        if (event.stage != 0) {
            return;
        }
        if (this.mode.getValue() == Mode.CONTINIOUS) {
            return;
        }
        if (!this.switchbool.getValue() || this.checkPickaxe()) {
            if (this.blockpos != null && AutoMine.mc.world.getBlockState(this.blockpos).getBlock().equals(Blocks.AIR)) {
                if (this.autodisable.getValue()) {
                    this.disable();
                    return;
                }
                this.blockpos = null;
            }
            BlockPos blockpos2 = null;
            for (final Entity obj : (List)AutoMine.mc.world.playerEntities.stream().filter(player -> player != AutoMine.mc.player && !Thunderhack.friendManager.isFriend(((EntityPlayer)player).getName()) && Float.compare(AutoMine.mc.player.getDistance(player), 7.0f) < 0).collect(Collectors.toList())) {
                final BlockPos pos = new BlockPos(obj.getPositionVector());
                if (!this.checkBlockPos(pos)) {
                    continue;
                }
                for (final BlockPos pos2 : blockPosList(pos)) {
                    if (!(AutoMine.mc.world.getBlockState(pos2).getBlock() instanceof BlockObsidian)) {
                        continue;
                    }
                    if (!AutoMine.mc.world.getBlockState(pos2.add(0, 1, 0)).getMaterial().equals(Material.AIR)) {
                        continue;
                    }
                    final double dist = AutoMine.mc.player.getDistance((double)pos2.getX(), (double)pos2.getY(), (double)pos2.getZ());
                    if (dist < 5.0) {
                        blockpos2 = pos2;
                        break;
                    }
                }
            }
            if (blockpos2 != null) {
                if (this.switchbool.getValue() && ItemUtil.findItem(Items.DIAMOND_PICKAXE.getClass()) != -1) {
                    AutoMine.mc.player.inventory.currentItem = ItemUtil.findItem(Items.DIAMOND_PICKAXE.getClass());
                }
                final float[] rotation = shitMethod2(blockpos2);
                event.rotationYaw = rotation[0];
                event.rotationPitch = rotation[1];
                if (!this.requirepickaxe.getValue() || AutoMine.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE)) {
                    if (this.blockpos != null && this.blockpos.equals((Object)blockpos2) && (Thunderhack.moduleManager.getModuleByClass(Speedmine.class).isOn() || Thunderhack.moduleManager.getModuleByClass(Speedmine2.class).isOn())) {
                        return;
                    }
                    AutoMine.mc.playerController.onPlayerDamageBlock(blockpos2, getFacing(blockpos2));
                    AutoMine.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.blockpos = blockpos2;
                }
            }
        }
    }
    
    public boolean checkPickaxe() {
        final Item item = AutoMine.mc.player.getHeldItemMainhand().getItem();
        return item.equals(Items.DIAMOND_PICKAXE) || item.equals(Items.IRON_PICKAXE) || item.equals(Items.GOLDEN_PICKAXE) || item.equals(Items.STONE_PICKAXE) || item.equals(Items.WOODEN_PICKAXE);
    }
    
    public boolean checkValidBlock(final Block block) {
        return block.equals(Blocks.OBSIDIAN) || block.equals(Blocks.BEDROCK);
    }
    
    public boolean checkBlockPos(final BlockPos blockPos) {
        final Block block = AutoMine.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock();
        final Block block2 = AutoMine.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock();
        final Block block3 = AutoMine.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock();
        final Block block4 = AutoMine.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock();
        final Block block5 = AutoMine.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock();
        return AutoMine.mc.world.isAirBlock(blockPos) && AutoMine.mc.world.isAirBlock(blockPos.add(0, 1, 0)) && AutoMine.mc.world.isAirBlock(blockPos.add(0, 2, 0)) && this.checkValidBlock(block) && this.checkValidBlock(block2) && this.checkValidBlock(block3) && this.checkValidBlock(block4) && this.checkValidBlock(block5);
    }
    
    public static List<BlockPos> blockPosList(final BlockPos blockPos) {
        final ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        arrayList.add(blockPos.add(1, 0, 0));
        arrayList.add(blockPos.add(-1, 0, 0));
        arrayList.add(blockPos.add(0, 0, 1));
        arrayList.add(blockPos.add(0, 0, -1));
        return arrayList;
    }
    
    public static Vec3d vec3dPosition() {
        return new Vec3d(AutoMine.mc.player.posX, AutoMine.mc.player.posY + AutoMine.mc.player.getEyeHeight(), AutoMine.mc.player.posZ);
    }
    
    public static float[] shitMethod(final Vec3d vec3d) {
        final Vec3d vec3d2 = vec3dPosition();
        final Vec3d vec3d3 = vec3d;
        final double d = vec3d3.x - vec3d2.x;
        final double d2 = vec3d3.y - vec3d2.y;
        final double d3 = vec3d3.z - vec3d2.z;
        final double d4 = d;
        final double d5 = d3;
        final double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        final float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f;
        final float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d6)));
        final float[] fArray = { AutoMine.mc.player.rotationYaw + MathHelper.wrapDegrees(f - AutoMine.mc.player.rotationYaw), AutoMine.mc.player.rotationPitch + MathHelper.wrapDegrees(f2 - AutoMine.mc.player.rotationPitch) };
        return fArray;
    }
    
    public static float[] shitMethod2(final BlockPos blockPos) {
        final Vec3d vec3d = vec3dPosition();
        final Vec3d vec3d2 = new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5);
        final double d = vec3d.squareDistanceTo(vec3d2);
        final EnumFacing[] enumFacingArray = EnumFacing.values();
        final int n = enumFacingArray.length;
        final int n2 = 0;
        if (0 < n) {
            final EnumFacing enumFacing = enumFacingArray[n2];
            final Vec3d vec3d3 = vec3d2.add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
            return shitMethod(vec3d3);
        }
        return shitMethod(vec3d2);
    }
    
    public static EnumFacing getFacing(final BlockPos blockPos) {
        final Vec3d vec3d = vec3dPosition();
        final Vec3d vec3d2 = new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5);
        final double d = vec3d.squareDistanceTo(vec3d2);
        final EnumFacing[] enumFacingArray = EnumFacing.values();
        final int n = enumFacingArray.length;
        final int n2 = 0;
        if (0 < n) {
            final EnumFacing enumFacing = enumFacingArray[n2];
            return enumFacing;
        }
        return EnumFacing.UP;
    }
    
    public enum Mode
    {
        FEET, 
        CONTINIOUS;
    }
}
