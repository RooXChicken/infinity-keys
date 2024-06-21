package com.rooxchicken.data;

import com.rooxchicken.InfinityKeys;

import net.minecraft.util.Identifier;

public class AbilityData
{
    public Identifier texture1;
    public Identifier texture2;
    public Identifier cooldownTexture;
    public Identifier outlineTexture;

    public int cooldown1;
    public int cooldown2;

    public int cooldown1Max;
    public int cooldown2Max;

    public boolean secondLocked = true;

    public AbilityData(String name)
    {
        cooldown1 = 0;
        cooldown2 = 0;

        cooldown1Max = 1;
        cooldown2Max = 1;

        cooldownTexture = Identifier.of("infinity-keys", "textures/cooldown.png");
        outlineTexture = Identifier.of("infinity-keys", "textures/empty.png");
        
        if(name.equals("empty"))
        {
            texture1 = Identifier.of("infinity-keys", "textures/gui/unlock.png");
            texture2 = Identifier.of("infinity-keys", "textures/gui/unlock.png");
            return;
        }

        texture1 = Identifier.of("infinity-keys", "textures/abilities/" + name + "_0.png");
        texture2 = Identifier.of("infinity-keys", "textures/abilities/" + name + "_1.png");
    }
}
