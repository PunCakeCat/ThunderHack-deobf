//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraft.util.math.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.awt.*;
import java.util.*;
import com.mrzak34.thunderhack.helper.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.item.*;

public class StorageEsp extends Module
{
    private final Setting<Float> range;
    private final Setting<Boolean> chest;
    private final Setting<Boolean> dispenser;
    private final Setting<Boolean> shulker;
    private final Setting<Boolean> echest;
    private final Setting<Boolean> furnace;
    private final Setting<Boolean> hopper;
    private final Setting<Boolean> cart;
    private final Setting<Boolean> frame;
    private final Setting<Boolean> box;
    private final Setting<Integer> boxAlpha;
    private final Setting<Boolean> outline;
    private final Setting<Float> lineWidth;
    private final Setting<ColorSetting> chestColor;
    private final Setting<ColorSetting> shulkColor;
    private final Setting<ColorSetting> echestColor;
    private Setting<mode> Mode;
    public Setting<Integer> del;
    
    public StorageEsp() {
        super("StorageESP", "Highlights Containers.", Module.Category.RENDER, false, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)50.0f, (T)1.0f, (T)300.0f));
        this.chest = (Setting<Boolean>)this.register(new Setting("Chest", (T)true));
        this.dispenser = (Setting<Boolean>)this.register(new Setting("Dispenser", (T)false));
        this.shulker = (Setting<Boolean>)this.register(new Setting("Shulker", (T)true));
        this.echest = (Setting<Boolean>)this.register(new Setting("Ender Chest", (T)true));
        this.furnace = (Setting<Boolean>)this.register(new Setting("Furnace", (T)false));
        this.hopper = (Setting<Boolean>)this.register(new Setting("Hopper", (T)false));
        this.cart = (Setting<Boolean>)this.register(new Setting("Minecart", (T)false));
        this.frame = (Setting<Boolean>)this.register(new Setting("Item Frame", (T)false));
        this.box = (Setting<Boolean>)this.register(new Setting("Box", (T)false));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("BoxAlpha", (T)125, (T)0, (T)255, v -> this.box.getValue()));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)true));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.outline.getValue()));
        this.chestColor = (Setting<ColorSetting>)this.register(new Setting("colorImgOutline", (T)new ColorSetting(-2013200640)));
        this.shulkColor = (Setting<ColorSetting>)this.register(new Setting("secondColorImgOutline", (T)new ColorSetting(-2013200640)));
        this.echestColor = (Setting<ColorSetting>)this.register(new Setting("thirdColorImgOutline", (T)new ColorSetting(-2013200640)));
        this.Mode = (Setting<mode>)this.register(new Setting("Shulker Mode", (T)mode.Rainbow));
        this.del = (Setting<Integer>)this.register(new Setting("Rainbow delay", (T)1, (T)0, (T)2000, v -> this.Mode.getValue() == mode.Rainbow));
    }
    
    public void onRender3D(final Render3DEvent event) {
        final HashMap<BlockPos, Integer> positions = new HashMap<BlockPos, Integer>();
        for (final TileEntity tileEntity : StorageEsp.mc.world.loadedTileEntityList) {
            final BlockPos pos;
            if (((tileEntity instanceof TileEntityChest && this.chest.getValue()) || (tileEntity instanceof TileEntityDispenser && this.dispenser.getValue()) || (tileEntity instanceof TileEntityShulkerBox && this.shulker.getValue()) || (tileEntity instanceof TileEntityEnderChest && this.echest.getValue()) || (tileEntity instanceof TileEntityFurnace && this.furnace.getValue()) || (tileEntity instanceof TileEntityHopper && this.hopper.getValue())) && StorageEsp.mc.player.getDistanceSq(pos = tileEntity.getPos()) <= MathUtil.square(this.range.getValue())) {
                final int color;
                if ((color = this.getTileEntityColor(tileEntity)) == -1) {
                    continue;
                }
                positions.put(pos, color);
            }
        }
        for (final Entity entity : StorageEsp.mc.world.loadedEntityList) {
            final BlockPos pos;
            if (((entity instanceof EntityItemFrame && this.frame.getValue()) || (entity instanceof EntityMinecartChest && this.cart.getValue())) && StorageEsp.mc.player.getDistanceSq(pos = entity.getPosition()) <= MathUtil.square(this.range.getValue())) {
                final int color;
                if ((color = this.getEntityColor(entity)) == -1) {
                    continue;
                }
                positions.put(pos, color);
            }
        }
        for (final Map.Entry<BlockPos, Integer> entry : positions.entrySet()) {
            final BlockPos blockPos = entry.getKey();
            final int color = entry.getValue();
            RenderUtil.drawBoxESP(blockPos, new Color(color), false, new Color(color), this.lineWidth.getValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), false);
        }
    }
    
    private int getTileEntityColor(final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            return this.chestColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            return this.echestColor.getValue().getColor();
        }
        if (tileEntity instanceof TileEntityShulkerBox) {
            if (this.Mode.getValue() == mode.Custom) {
                return this.shulkColor.getValue().getColor();
            }
            if (this.Mode.getValue() == mode.Rainbow) {
                return PaletteHelper.rainbow(this.del.getValue(), 100.0f, 50.0f).getRGB();
            }
            if (this.Mode.getValue() == mode.Astolfo) {
                return PaletteHelper.astolfo(false, (int)StorageEsp.mc.player.height).getRGB();
            }
        }
        if (tileEntity instanceof TileEntityFurnace) {
            return ColorUtil.toRGBA(255, 128, 0, 255);
        }
        if (tileEntity instanceof TileEntityHopper) {
            return ColorUtil.toRGBA(255, 128, 0, 255);
        }
        if (tileEntity instanceof TileEntityDispenser) {
            return ColorUtil.toRGBA(255, 128, 0, 255);
        }
        return -1;
    }
    
    private int getEntityColor(final Entity entity) {
        if (entity instanceof EntityMinecartChest) {
            return ColorUtil.toRGBA(255, 128, 0, 255);
        }
        if (entity instanceof EntityItemFrame && ((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof ItemShulkerBox) {
            return ColorUtil.toRGBA(255, 128, 0, 255);
        }
        if (entity instanceof EntityItemFrame && !(((EntityItemFrame)entity).getDisplayedItem().getItem() instanceof ItemShulkerBox)) {
            return ColorUtil.toRGBA(255, 128, 0, 255);
        }
        return -1;
    }
    
    public enum mode
    {
        Custom, 
        Rainbow, 
        Astolfo;
    }
}
