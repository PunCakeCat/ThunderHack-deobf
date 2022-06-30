//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.render;

import com.mrzak34.thunderhack.features.modules.*;
import java.util.*;
import com.mrzak34.thunderhack.features.setting.*;
import com.mrzak34.thunderhack.event.events.*;
import com.mrzak34.thunderhack.features.modules.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class HitParticles extends Module
{
    private final Random random;
    public Setting<Integer> particleMultiplier;
    private Setting<mode> particleMode;
    
    public HitParticles() {
        super("HitParticles", "hitmerkrgko", Module.Category.RENDER, true, false, false);
        this.random = new Random();
        this.particleMultiplier = (Setting<Integer>)this.register(new Setting("Particle Multiplier", (T)5, (T)1, (T)15));
        this.particleMode = (Setting<mode>)this.register(new Setting("HitParticles", (T)mode.Heart));
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final CPacketUseEntity packet;
        if (KillauraOld.getInstance().isEnabled() && event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
            final Entity entity = (Entity)KillauraOld.target;
            if (entity != null) {
                if (this.particleMode.getValue() == mode.Redstone) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.effectRenderer.spawnEffectParticle(37, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[] { Block.getIdFromBlock(Blocks.REDSTONE_BLOCK) });
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Speel) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Criticals) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.player.onCriticalHit(entity);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.AngryVillager) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.HappyVillager) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Heart) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.HEART, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Cloud) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Enchant) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.effectRenderer.spawnEffectParticle(37, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[] { Block.getIdFromBlock(Blocks.REDSTONE_BLOCK) });
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Flame) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.FLAME, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
                if (this.particleMode.getValue() == mode.Portal) {
                    for (float i = 0.0f; i < entity.height; i += 0.2f) {
                        for (int i2 = 0; i2 < this.particleMultiplier.getValue(); ++i2) {
                            HitParticles.mc.world.spawnParticle(EnumParticleTypes.PORTAL, entity.posX, entity.posY + i, entity.posZ, (double)((this.random.nextInt(6) - 3) / 5.0f), 0.1, (double)((this.random.nextInt(6) - 3) / 5.0f), new int[0]);
                        }
                    }
                }
            }
        }
    }
    
    public enum mode
    {
        Speel, 
        Enchant, 
        Criticals, 
        Heart, 
        Flame, 
        HappyVillager, 
        AngryVillager, 
        Portal, 
        Redstone, 
        Cloud;
    }
}
