//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.client.gui.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.inventory.*;
import java.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.util.*;

public class ToolTips extends Module
{
    private static final ResourceLocation MAP;
    private static final ResourceLocation SHULKER_GUI_TEXTURE;
    private static ToolTips INSTANCE;
    public Setting<Boolean> maps;
    public Setting<Boolean> shulkers;
    public Setting<Bind> peek;
    public Setting<Boolean> shulkerSpy;
    public Setting<Boolean> render;
    public Setting<Boolean> own;
    public Setting<Integer> cooldown;
    public Setting<Boolean> textColor;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    public Setting<Boolean> offsets;
    private final Setting<Integer> yPerPlayer;
    private final Setting<Integer> xOffset;
    private final Setting<Integer> yOffset;
    private final Setting<Integer> trOffset;
    public Setting<Integer> invH;
    public Map<EntityPlayer, ItemStack> spiedPlayers;
    public Map<EntityPlayer, TimerUtil> playerTimers;
    private int textRadarY;
    
    public ToolTips() {
        super("ToolTips", "\u043f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0441\u043e\u0434\u0435\u0440\u0436\u0438\u043c\u043e\u0435-\u0448\u0430\u043b\u043a\u0435\u0440\u0430/\u043a\u0430\u0440\u0442\u044b/\u043a\u043d\u0438\u0433\u0438", Category.MISC, true, false, false);
        this.maps = (Setting<Boolean>)this.register(new Setting("Maps", (T)true));
        this.shulkers = (Setting<Boolean>)this.register(new Setting("ShulkerViewer", (T)true));
        this.peek = (Setting<Bind>)this.register(new Setting("Peek", (T)new Bind(-1)));
        this.shulkerSpy = (Setting<Boolean>)this.register(new Setting("ShulkerSpy", (T)true));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true, v -> this.shulkerSpy.getValue()));
        this.own = (Setting<Boolean>)this.register(new Setting("OwnShulker", (T)true, v -> this.shulkerSpy.getValue()));
        this.cooldown = (Setting<Integer>)this.register(new Setting("ShowForS", (T)2, (T)0, (T)5, v -> this.shulkerSpy.getValue()));
        this.textColor = (Setting<Boolean>)this.register(new Setting("TextColor", (T)false, v -> this.shulkers.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255, v -> this.textColor.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255, v -> this.textColor.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255, v -> this.textColor.getValue()));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255, v -> this.textColor.getValue()));
        this.offsets = (Setting<Boolean>)this.register(new Setting("Offsets", (T)false));
        this.yPerPlayer = (Setting<Integer>)this.register(new Setting("Y/Player", (T)18, v -> this.offsets.getValue()));
        this.xOffset = (Setting<Integer>)this.register(new Setting("XOffset", (T)4, v -> this.offsets.getValue()));
        this.yOffset = (Setting<Integer>)this.register(new Setting("YOffset", (T)2, v -> this.offsets.getValue()));
        this.trOffset = (Setting<Integer>)this.register(new Setting("TROffset", (T)2, v -> this.offsets.getValue()));
        this.invH = (Setting<Integer>)this.register(new Setting("InvH", (T)3, v -> this.offsets.getValue()));
        this.spiedPlayers = new ConcurrentHashMap<EntityPlayer, ItemStack>();
        this.playerTimers = new ConcurrentHashMap<EntityPlayer, TimerUtil>();
        this.setInstance();
    }
    
    public static ToolTips getInstance() {
        if (ToolTips.INSTANCE == null) {
            ToolTips.INSTANCE = new ToolTips();
        }
        return ToolTips.INSTANCE;
    }
    
    public static void displayInv(final ItemStack stack, final String name) {
        try {
            final Item item = stack.getItem();
            final TileEntityShulkerBox entityBox = new TileEntityShulkerBox();
            final ItemShulkerBox shulker = (ItemShulkerBox)item;
            entityBox.blockType = shulker.getBlock();
            entityBox.setWorld((World)ToolTips.mc.world);
            ItemStackHelper.loadAllItems(Objects.requireNonNull(stack.getTagCompound()).getCompoundTag("BlockEntityTag"), entityBox.items);
            entityBox.readFromNBT(stack.getTagCompound().getCompoundTag("BlockEntityTag"));
            entityBox.setCustomName((name == null) ? stack.getDisplayName() : name);
            final IInventory inventory;
            new Thread(() -> {
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException ex) {}
                ToolTips.mc.player.displayGUIChest(inventory);
            }).start();
        }
        catch (Exception ex2) {}
    }
    
    private void setInstance() {
        ToolTips.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck() || !this.shulkerSpy.getValue()) {
            return;
        }
        final Slot slot;
        final ItemStack stack;
        if (this.peek.getValue().getKey() != -1 && ToolTips.mc.currentScreen instanceof GuiContainer && Keyboard.isKeyDown(this.peek.getValue().getKey()) && (slot = ((GuiContainer)ToolTips.mc.currentScreen).getSlotUnderMouse()) != null && (stack = slot.getStack()) != null && stack.getItem() instanceof ItemShulkerBox) {
            displayInv(stack, null);
        }
        for (final EntityPlayer player : ToolTips.mc.world.playerEntities) {
            if (player != null && player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox) {
                if (!this.own.getValue() && ToolTips.mc.player.equals((Object)player)) {
                    continue;
                }
                final ItemStack stack2 = player.getHeldItemMainhand();
                this.spiedPlayers.put(player, stack2);
            }
        }
    }
    
    @Override
    public void onRender2D(final Render2DEvent event) {
        if (fullNullCheck() || !this.shulkerSpy.getValue() || !this.render.getValue()) {
            return;
        }
        final int x = -4 + this.xOffset.getValue();
        int y = 10 + this.yOffset.getValue();
        this.textRadarY = 0;
        for (final EntityPlayer player : ToolTips.mc.world.playerEntities) {
            if (this.spiedPlayers.get(player) == null) {
                continue;
            }
            player.getHeldItemMainhand();
            if (!(player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox)) {
                final TimerUtil playerTimer = this.playerTimers.get(player);
                if (playerTimer == null) {
                    final TimerUtil timer = new TimerUtil();
                    timer.reset();
                    this.playerTimers.put(player, timer);
                }
                else if (playerTimer.passedS(this.cooldown.getValue())) {
                    continue;
                }
            }
            else {
                final TimerUtil playerTimer;
                if (player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox && (playerTimer = this.playerTimers.get(player)) != null) {
                    playerTimer.reset();
                    this.playerTimers.put(player, playerTimer);
                }
            }
            final ItemStack stack = this.spiedPlayers.get(player);
            this.renderShulkerToolTip(stack, x, y, player.getName());
            this.textRadarY = (y += this.yPerPlayer.getValue() + 60) - 10 - this.yOffset.getValue() + this.trOffset.getValue();
        }
    }
    
    public int getTextRadarY() {
        return this.textRadarY;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void makeTooltip(final ItemTooltipEvent event) {
    }
    
    @SubscribeEvent
    public void renderTooltip(final RenderTooltipEvent.PostText event) {
        final MapData mapData;
        if (this.maps.getValue() && !event.getStack().isEmpty() && event.getStack().getItem() instanceof ItemMap && (mapData = Items.FILLED_MAP.getMapData(event.getStack(), (World)ToolTips.mc.world)) != null) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            RenderHelper.disableStandardItemLighting();
            ToolTips.mc.getTextureManager().bindTexture(ToolTips.MAP);
            final Tessellator instance = Tessellator.getInstance();
            final BufferBuilder buffer = instance.getBuffer();
            final int n = 7;
            final float n2 = 135.0f;
            final float n3 = 0.5f;
            GlStateManager.translate((float)event.getX(), event.getY() - n2 * n3 - 5.0f, 0.0f);
            GlStateManager.scale(n3, n3, n3);
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos((double)(-n), (double)n2, 0.0).tex(0.0, 1.0).endVertex();
            buffer.pos((double)n2, (double)n2, 0.0).tex(1.0, 1.0).endVertex();
            buffer.pos((double)n2, (double)(-n), 0.0).tex(1.0, 0.0).endVertex();
            buffer.pos((double)(-n), (double)(-n), 0.0).tex(0.0, 0.0).endVertex();
            instance.draw();
            ToolTips.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
    
    public void renderShulkerToolTip(final ItemStack stack, final int x, final int y, final String name) {
        final NBTTagCompound tagCompound = stack.getTagCompound();
        final NBTTagCompound blockEntityTag;
        if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10) && (blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            ToolTips.mc.getTextureManager().bindTexture(ToolTips.SHULKER_GUI_TEXTURE);
            RenderUtil.drawTexturedRect(x, y, 0, 0, 176, 16, 500);
            RenderUtil.drawTexturedRect(x, y + 16, 0, 16, 176, 54 + this.invH.getValue(), 500);
            RenderUtil.drawTexturedRect(x, y + 16 + 54, 0, 160, 176, 8, 500);
            GlStateManager.disableDepth();
            Color color = new Color(0, 0, 0, 255);
            if (this.textColor.getValue()) {
                color = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue());
            }
            this.renderer.drawStringWithShadow((name == null) ? stack.getDisplayName() : name, (float)(x + 8), (float)(y + 6), ColorUtil.toRGBA(color));
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            final NonNullList nonnulllist = NonNullList.withSize(27, (Object)ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(blockEntityTag, nonnulllist);
            for (int i = 0; i < nonnulllist.size(); ++i) {
                final int iX = x + i % 9 * 18 + 8;
                final int iY = y + i / 9 * 18 + 18;
                final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                ToolTips.mc.getRenderItem().zLevel = 501.0f;
                RenderUtil.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                RenderUtil.itemRender.renderItemOverlayIntoGUI(ToolTips.mc.fontRenderer, itemStack, iX, iY, (String)null);
                ToolTips.mc.getRenderItem().zLevel = 0.0f;
            }
            GlStateManager.disableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    static {
        MAP = new ResourceLocation("textures/map/map_background.png");
        SHULKER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
        ToolTips.INSTANCE = new ToolTips();
    }
}
