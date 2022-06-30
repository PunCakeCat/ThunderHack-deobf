//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.modules.misc;

import com.mrzak34.thunderhack.features.modules.*;
import com.mrzak34.thunderhack.features.setting.*;

public class ThirdPersView extends Module
{
    public final Setting<Integer> x;
    public final Setting<Integer> y;
    public final Setting<Integer> z;
    
    public ThirdPersView() {
        super("ThirdPersView", "ThirdPersView", Category.MISC, true, false, false);
        this.x = (Setting<Integer>)this.register(new Setting("x", (T)0, (T)(-180), (T)180));
        this.y = (Setting<Integer>)this.register(new Setting("y", (T)0, (T)(-180), (T)180));
        this.z = (Setting<Integer>)this.register(new Setting("z", (T)4, (T)1, (T)50));
    }
}
