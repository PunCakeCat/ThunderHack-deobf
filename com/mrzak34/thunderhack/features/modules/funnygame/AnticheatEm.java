//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.funnygame;

import com.mrzak34.thunderhack.features.modules.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.features.command.*;
import java.util.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AnticheatEm extends Module
{
    ArrayList<Susplayer> susplayers;
    ArrayList<String> susplayersnames;
    ArrayList<EntityPlayer> shitters;
    Timer noslowtimer;
    
    public AnticheatEm() {
        super("AnticheatEm", "AnticheatEm", Category.FUNNYGAME, true, false, false);
        this.susplayers = new ArrayList<Susplayer>();
        this.susplayersnames = new ArrayList<String>();
        this.shitters = new ArrayList<EntityPlayer>();
        this.noslowtimer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (AnticheatEm.mc.player == null && AnticheatEm.mc.world == null) {
            return;
        }
        for (final Entity entity : AnticheatEm.mc.world.loadedEntityList) {
            if (entity instanceof EntityPlayer) {
                if (entity == AnticheatEm.mc.player) {
                    continue;
                }
                final EntityPlayer player = (EntityPlayer)entity;
                if (player.isCreative()) {
                    return;
                }
                if (Thunderhack.speedManager.getPlayerSpeed(player) >= 20.0 && player.moveForward < 0.0f) {
                    Command.sendMessage("flagged strafe " + player.getName());
                    if (!this.susplayersnames.contains(player.getName())) {
                        this.susplayers.add(new Susplayer(player.getName(), 1, 0));
                    }
                    else {
                        for (final Susplayer plr : this.susplayers) {
                            if (Objects.equals(plr.name, player.getName())) {
                                plr.setSprint(plr.getSprint() + 1);
                            }
                        }
                    }
                }
                if (!player.isHandActive() || player.hurtTime != 0 || Thunderhack.speedManager.getPlayerSpeed(player) < 5.0) {
                    continue;
                }
                Command.sendMessage("flagged noslow " + player.getName() + " " + Thunderhack.speedManager.getPlayerSpeed(player));
                this.noslowtimer.reset();
                if (!this.noslowtimer.passedMs(800L)) {
                    return;
                }
                if (!this.susplayersnames.contains(player.getName())) {
                    this.susplayers.add(new Susplayer(player.getName(), 0, 1));
                }
                else {
                    for (final Susplayer plr : this.susplayers) {
                        if (Objects.equals(plr.name, player.getName())) {
                            plr.setNoslo(plr.getNoslow() + 1);
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent e) {
        if (AnticheatEm.mc.player == null && AnticheatEm.mc.world == null) {
            return;
        }
        for (final Susplayer plr : this.susplayers) {
            if (plr.getNoslow() > 5) {
                for (final Entity ent : AnticheatEm.mc.world.loadedEntityList) {
                    if (ent instanceof EntityPlayer && ent.getName().equals(plr.getName())) {
                        this.shitters.add((EntityPlayer)ent);
                    }
                }
            }
            if (plr.getSprint() > 5) {
                for (final Entity ent : AnticheatEm.mc.world.loadedEntityList) {
                    if (ent instanceof EntityPlayer && ent.getName().equals(plr.getName())) {
                        this.shitters.add((EntityPlayer)ent);
                    }
                }
            }
        }
        for (final EntityPlayer shit : this.shitters) {
            Command.sendMessage(shit.getName());
        }
    }
    
    class Susplayer
    {
        public int sprint;
        public int noslow;
        public String name;
        
        public Susplayer(final String name, final int sprintscore, final int noslowscore) {
            this.sprint = sprintscore;
            this.noslow = noslowscore;
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getSprint() {
            return this.sprint;
        }
        
        public int getNoslow() {
            return this.noslow;
        }
        
        public void setSprint(final int a) {
            this.sprint = a;
        }
        
        public void setNoslo(final int a) {
            this.noslow = a;
        }
    }
}
