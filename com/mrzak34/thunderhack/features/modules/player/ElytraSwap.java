//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.player;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.mrzak34.thunderhack.event.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ElytraSwap extends Module
{
    private final ResourceLocation toelytra;
    private final ResourceLocation tochest;
    public Setting<Boolean> image;
    public Setting<Integer> imagex;
    public Setting<Integer> imagey;
    public Timer timer;
    public int swap;
    
    public ElytraSwap() {
        super("ElytraSwap", "\u0441\u0432\u0430\u043f \u043c\u0435\u0436\u0434\u0443 \u043d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a\u043e\u043c-\u0438 \u044d\u043b\u0438\u0442\u0440\u043e\u0439", Module.Category.PLAYER, true, false, false);
        this.toelytra = new ResourceLocation("textures/swapel.png");
        this.tochest = new ResourceLocation("textures/swapch.png");
        this.image = (Setting<Boolean>)this.register(new Setting("indicator", (T)true));
        this.imagex = (Setting<Integer>)this.register(new Setting("indicatorX", (T)512, (T)0, (T)1023, v -> this.image.getValue()));
        this.imagey = (Setting<Integer>)this.register(new Setting("indicatorY", (T)512, (T)0, (T)1023, v -> this.image.getValue()));
        this.timer = new Timer();
        this.swap = 0;
    }
    
    public void onEnable() {
        ElytraSwap.mc.player.setSneaking(true);
        this.timer.reset();
        final ItemStack itemStack = getItemStack(38);
        if (itemStack.getItem() == Items.ELYTRA) {
            final int slot = this.getChestPlateSlot();
            if (slot != -1) {
                clickSlot(slot);
                clickSlot(38);
                clickSlot(slot);
                this.swap = 1;
            }
            else {
                Command.sendMessage("\u0423 \u0442\u0435\u0431\u044f \u043d\u0435\u0442 \u0447\u0435\u0441\u0442\u043f\u043b\u0435\u0439\u0442\u0430!");
            }
        }
        else if (hasItem(Items.ELYTRA)) {
            final int slot = getSlot(Items.ELYTRA);
            clickSlot(slot);
            clickSlot(38);
            clickSlot(slot);
            this.swap = 2;
        }
        else {
            Command.sendMessage("\u0423 \u0442\u0435\u0431\u044f \u043d\u0435\u0442 \u044d\u043b\u0438\u0442\u0440\u044b!");
        }
    }
    
    public int getChestPlateSlot() {
        final Item[] array;
        final Item[] items = array = new Item[] { (Item)Items.DIAMOND_CHESTPLATE, (Item)Items.CHAINMAIL_CHESTPLATE, (Item)Items.IRON_CHESTPLATE, (Item)Items.GOLDEN_CHESTPLATE, (Item)Items.LEATHER_CHESTPLATE };
        for (final Item item : array) {
            if (hasItem(item)) {
                return getSlot(item);
            }
        }
        return -1;
    }
    
    public static boolean hasItem(final Item item) {
        return getAmountOfItem(item) != 0;
    }
    
    public static int getAmountOfItem(final Item item) {
        int count = 0;
        for (final ItemStackUtil itemStack : getAllItems()) {
            if (itemStack.itemStack != null && itemStack.itemStack.getItem().equals(item)) {
                count += itemStack.itemStack.getCount();
            }
        }
        return count;
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final int width, final int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f((float)width, (float)height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f((float)width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public static int getClickSlot(int id) {
        if (id == -1) {
            return id;
        }
        if (id < 9) {
            id += 36;
            return id;
        }
        if (id == 39) {
            id = 5;
        }
        else if (id == 38) {
            id = 6;
        }
        else if (id == 37) {
            id = 7;
        }
        else if (id == 36) {
            id = 8;
        }
        else if (id == 40) {
            id = 45;
        }
        return id;
    }
    
    public static void clickSlot(final int id) {
        if (id != -1) {
            try {
                ElytraSwap.mc.playerController.windowClick(ElytraSwap.mc.player.openContainer.windowId, getClickSlot(id), 0, ClickType.PICKUP, (EntityPlayer)ElytraSwap.mc.player);
            }
            catch (Exception ex) {}
        }
    }
    
    public static int getSlot(final Item item) {
        try {
            for (final ItemStackUtil itemStack : getAllItems()) {
                if (itemStack.itemStack.getItem().equals(item)) {
                    return itemStack.slotId;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public static ArrayList<ItemStackUtil> getAllItems() {
        final ArrayList<ItemStackUtil> items = new ArrayList<ItemStackUtil>();
        for (int i = 0; i < 36; ++i) {
            items.add(new ItemStackUtil(getItemStack(i), i));
        }
        return items;
    }
    
    public static ItemStack getItemStack(final int id) {
        try {
            return ElytraSwap.mc.player.inventory.getStackInSlot(id);
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    @SubscribeEvent
    public void onRender2D(final Render2DEvent event) {
        System.out.println(this.swap);
        final double psx = this.imagex.getValue();
        final double psy = this.imagey.getValue();
        final float xOffset = (float)psx + 10.0f;
        final float yOffset = (float)psy;
        if (this.swap == 1) {
            RenderUtil.drawRect(400.0f, 400.0f, 400.0f, 400.0f, new Color(252, 252, 252, 255).getRGB());
            Util.mc.getTextureManager().bindTexture(this.toelytra);
            drawCompleteImage(xOffset - 1.0f, yOffset - 160.0f, 49, 49);
        }
        if (this.swap == 2) {
            RenderUtil.drawRect(400.0f, 400.0f, 400.0f, 400.0f, new Color(252, 252, 252, 255).getRGB());
            Util.mc.getTextureManager().bindTexture(this.tochest);
            drawCompleteImage(xOffset - 1.0f, yOffset - 160.0f, 49, 49);
        }
        if (this.timer.passedMs(1000L)) {
            ElytraSwap.mc.player.setSneaking(false);
            this.disable();
        }
    }
    
    public static class ItemStackUtil
    {
        public ItemStack itemStack;
        public int slotId;
        
        public ItemStackUtil(final ItemStack itemStack, final int slotId) {
            this.itemStack = itemStack;
            this.slotId = slotId;
        }
    }
}
