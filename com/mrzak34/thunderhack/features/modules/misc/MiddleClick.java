//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.features.gui.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.command.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;

public class MiddleClick extends Module
{
    private static MiddleClick INSTANCE;
    public Setting<Float> scalefactor;
    private Setting<Action> action;
    private Setting<Integer> range;
    public Setting<Float> circus;
    public Setting<Boolean> fm;
    public Timer timr;
    public Setting<Boolean> throughWalls;
    public Setting<Boolean> rocket;
    public Setting<Boolean> ep;
    public Setting<Boolean> xp;
    public Setting<Boolean> xpInHoles;
    public Setting<String> commandtext;
    public Setting<String> commandname;
    private Timer xpTimer;
    int range2;
    int originalSlot;
    boolean firstSwap;
    
    public MiddleClick() {
        super("MiddleClick", "\u043c\u0435\u043d\u044e \u043d\u0430 \u043a\u043e\u043b\u0435\u0441\u0438\u043a\u043e-\u043c\u044b\u0448\u0438", Category.MISC, false, false, false);
        this.scalefactor = (Setting<Float>)this.register(new Setting("Raytrace", (T)2.0f, (T)0.1f, (T)4.0f, "Wall Range."));
        this.action = (Setting<Action>)this.register(new Setting("Action", (T)Action.MENU));
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)40, (T)10, (T)250, v -> this.action.getValue() == Action.MENU));
        this.circus = (Setting<Float>)this.register(new Setting("circus", (T)2.0f, (T)0.1f, (T)300.0f, "circus"));
        this.fm = (Setting<Boolean>)this.register(new Setting("FriendMessage", (T)true));
        this.timr = new Timer();
        this.throughWalls = (Setting<Boolean>)this.register(new Setting("ThroughWalls", (T)true, v -> this.action.getValue() == Action.MENU));
        this.rocket = (Setting<Boolean>)this.register(new Setting("Rocket", (T)false));
        this.ep = (Setting<Boolean>)this.register(new Setting("EP", (T)false));
        this.xp = (Setting<Boolean>)this.register(new Setting("XP", (T)false));
        this.xpInHoles = (Setting<Boolean>)this.register(new Setting("XPInHoles", (T)false, v -> this.xp.getValue()));
        this.commandtext = (Setting<String>)this.register(new Setting("Buttname", (T)"SampleButt"));
        this.commandname = (Setting<String>)this.register(new Setting("Command", (T)"/kick"));
        this.xpTimer = new Timer();
        this.range2 = 100;
        this.originalSlot = -1;
        this.firstSwap = true;
        this.setInstance();
    }
    
    public static MiddleClick getInstance() {
        if (MiddleClick.INSTANCE == null) {
            MiddleClick.INSTANCE = new MiddleClick();
        }
        return MiddleClick.INSTANCE;
    }
    
    private void setInstance() {
        MiddleClick.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (MiddleClick.mc.player == null || MiddleClick.mc.world == null) {
            return;
        }
        if (this.action.getValue() == Action.MENU) {
            if (!GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            final EntityPlayer rayTracedEntity = this.getEntityUnderMouse(this.range.getValue());
            if (rayTracedEntity != null) {
                this.xpTimer.reset();
                if (MiddleClick.mc.currentScreen == null) {
                    Util.mc.displayGuiScreen((GuiScreen)new GuiMiddleClickMenu(rayTracedEntity));
                }
            }
        }
        if (this.action.getValue() == Action.NEWMENU) {
            if (!GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            final EntityPlayer rayTracedEntity = this.getEntityUnderMouse(this.range.getValue());
            if (rayTracedEntity != null) {
                this.xpTimer.reset();
                if (MiddleClick.mc.currentScreen == null) {
                    Util.mc.displayGuiScreen((GuiScreen)new NewGuiMiddleClickMenu(rayTracedEntity));
                }
            }
        }
        if (this.action.getValue() == Action.FRIEND && MiddleClick.mc.objectMouseOver.entityHit != null) {
            if (!GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            final Entity entity = MiddleClick.mc.objectMouseOver.entityHit;
            if (entity instanceof EntityPlayer && this.timr.passedMs(2500L)) {
                if (Thunderhack.friendManager.isFriend(entity.getName())) {
                    Thunderhack.friendManager.removeFriend(entity.getName());
                    Command.sendMessage("Removed §b" + entity.getName() + "§r as a friend!");
                }
                else {
                    Thunderhack.friendManager.addFriend(entity.getName());
                    if (this.fm.getValue()) {
                        MiddleClick.mc.player.sendChatMessage("/w " + entity.getName() + " i friended u at ThunderHackPlus");
                    }
                    Command.sendMessage("Added §b" + entity.getName() + "§r as a friend!");
                }
                this.xpTimer.reset();
                this.timr.reset();
                return;
            }
        }
        if (this.rocket.getValue() && this.findRocketSlot() != -1) {
            if (!GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            this.xpTimer.reset();
            final int rocketSlot = this.findRocketSlot();
            final int originalSlot = MiddleClick.mc.player.inventory.currentItem;
            if (rocketSlot != -1) {
                MiddleClick.mc.player.inventory.currentItem = rocketSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(rocketSlot));
                MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = originalSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(originalSlot));
                return;
            }
        }
        if (this.ep.getValue() && (!this.xp.getValue() || (this.xpInHoles.getValue() && !BlockUtils.isHole(new BlockPos((Entity)MiddleClick.mc.player))))) {
            if (!GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock)) {
                return;
            }
            final int epSlot = this.findEPSlot();
            final int originalSlot = MiddleClick.mc.player.inventory.currentItem;
            if (epSlot != -1) {
                MiddleClick.mc.player.inventory.currentItem = epSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(epSlot));
                MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                MiddleClick.mc.player.inventory.currentItem = originalSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(originalSlot));
            }
        }
    }
    
    public EntityPlayer getEntityUnderMouse(final int range) {
        final Entity entity = MiddleClick.mc.getRenderViewEntity();
        if (entity != null) {
            Vec3d pos = MiddleClick.mc.player.getPositionEyes(1.0f);
            for (float i = 0.0f; i < range; i += 0.5f) {
                pos = pos.add(MiddleClick.mc.player.getLookVec().scale(0.5));
                if (!this.throughWalls.getValue() && MiddleClick.mc.world.getBlockState(new BlockPos(pos.x, pos.y, pos.z)).getBlock() != Blocks.AIR) {
                    return null;
                }
                for (final EntityPlayer player : MiddleClick.mc.world.playerEntities) {
                    if (player == MiddleClick.mc.player) {
                        continue;
                    }
                    AxisAlignedBB bb = player.getEntityBoundingBox();
                    if (bb == null) {
                        continue;
                    }
                    if (player.getDistance((Entity)MiddleClick.mc.player) > 6.0f) {
                        bb = bb.grow(0.5);
                    }
                    if (bb.contains(pos)) {
                        return player;
                    }
                }
            }
        }
        return null;
    }
    
    public boolean isSomebodylookingonme(final EntityPlayer smbd) {
        Vec3d pos = smbd.getPositionEyes(1.0f);
        for (float i = 0.0f; i < this.range2; i += 0.5f) {
            pos = pos.add(smbd.getLookVec().scale(0.5));
        }
        return true;
    }
    
    @SubscribeEvent
    public void onUpdate(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 1) {
            if (GameSettings.isKeyDown(MiddleClick.mc.gameSettings.keyBindPickBlock) && this.xpTimer.passedMs(350L)) {
                if (this.xp.getValue() && (!this.xpInHoles.getValue() || BlockUtils.isHole(new BlockPos((Entity)MiddleClick.mc.player)))) {
                    final int epSlot = this.findXPSlot();
                    if (this.firstSwap) {
                        this.originalSlot = MiddleClick.mc.player.inventory.currentItem;
                        this.firstSwap = false;
                    }
                    if (epSlot != -1) {
                        MiddleClick.mc.player.inventory.currentItem = epSlot;
                        MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(epSlot));
                        MiddleClick.mc.playerController.processRightClick((EntityPlayer)MiddleClick.mc.player, (World)MiddleClick.mc.world, EnumHand.MAIN_HAND);
                    }
                }
            }
            else if (this.originalSlot != -1) {
                MiddleClick.mc.player.inventory.currentItem = this.originalSlot;
                MiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.originalSlot));
                this.originalSlot = -1;
                this.firstSwap = true;
            }
        }
    }
    
    private int findRocketSlot() {
        int rocketSlot = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS) {
            rocketSlot = MiddleClick.mc.player.inventory.currentItem;
        }
        if (rocketSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(l).getItem() == Items.FIREWORKS) {
                    rocketSlot = l;
                    break;
                }
            }
        }
        return rocketSlot;
    }
    
    private int findEPSlot() {
        int epSlot = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL) {
            epSlot = MiddleClick.mc.player.inventory.currentItem;
        }
        if (epSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(l).getItem() == Items.ENDER_PEARL) {
                    epSlot = l;
                    break;
                }
            }
        }
        return epSlot;
    }
    
    private int findXPSlot() {
        int epSlot = -1;
        if (MiddleClick.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            epSlot = MiddleClick.mc.player.inventory.currentItem;
        }
        if (epSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (MiddleClick.mc.player.inventory.getStackInSlot(l).getItem() == Items.EXPERIENCE_BOTTLE) {
                    epSlot = l;
                    break;
                }
            }
        }
        return epSlot;
    }
    
    static {
        MiddleClick.INSTANCE = new MiddleClick();
    }
    
    private enum Action
    {
        MENU, 
        FRIEND, 
        MISC, 
        NEWMENU;
    }
}
