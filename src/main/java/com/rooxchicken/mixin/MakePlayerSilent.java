package com.rooxchicken.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.rooxchicken.InfinityKeys;
import com.rooxchicken.data.HandleData;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(Entity.class)
public class MakePlayerSilent 
{
    @ModifyArg(method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;stepOnBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;ZZLnet/minecraft/util/math/Vec3d;)Z"), index = 2)
    public boolean stepOnBlock(boolean playSound)
    {
        return (HandleData.silentPlayers.contains(((Entity)(Object)this).getName().getString()));
    }

}
