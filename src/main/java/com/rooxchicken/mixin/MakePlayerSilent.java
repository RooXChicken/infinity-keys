package com.rooxchicken.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.rooxchicken.InfinityKeys;
import com.rooxchicken.data.HandleData;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

@Mixin(World.class)
public class MakePlayerSilent 
{
//     @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
//     public void playSound(@Nullable PlayerEntity except, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, CallbackInfo info)
//     {
//         InfinityKeys.LOGGER.info("HI");
//         if((Object)this instanceof PlayerEntity)
//         {
//             String name = ((PlayerEntity)(Object)this).getName().getString();
//             InfinityKeys.LOGGER.info(name);
//             if(HandleData.silentPlayers.contains(name))
//             {
//                 info.cancel();
//                 return;
//             }
//         }
//     }

}
