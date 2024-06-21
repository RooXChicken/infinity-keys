package com.rooxchicken.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.rooxchicken.client.InfinityKeysClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.screen.slot.SlotActionType;

@Mixin(Mouse.class)
public class MixinScrolls
{
    @Inject(method = "onMouseScroll(JDD)V", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info)
    {   
        if(MinecraftClient.getInstance().player != null)
            InfinityKeysClient.scrolls += vertical;
    }
}
