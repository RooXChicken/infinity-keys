package com.rooxchicken.infinity.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.rooxchicken.infinity.client.InfinityKeysClient;
import com.rooxchicken.infinity.data.HandleData;

import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;

@Mixin(MessageHandler.class)
public class OnChatMessageInfinity
{
    @Inject(method = "onGameMessage(Lnet/minecraft/text/Text;Z)V", at = @At("HEAD"), cancellable = true)
    public void onGameMessage(Text message, boolean overlay, CallbackInfo info)
    {
        String content = message.getString();
        if(content.length() < 7)
            return;
        if(content.substring(0, 6).equals("infb63"))
        {
            HandleData.parseData(content);

            info.cancel();
        }
    }
}
