package com.rooxchicken.keybinding;

import com.rooxchicken.client.InfinityKeysClient;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Keybind
{
    public KeyBinding binding;
    public String command;

    private boolean wasPressed = false;

    public Keybind(String category, String name, int GLFWkey, String _command)
    {
        command = _command;

        binding = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(name, InputUtil.Type.KEYSYM, GLFWkey, category));

        MinecraftClient.getInstance();
    }
    
    public void CheckKey()
    {
        if(binding.isPressed() && !wasPressed)
        {
            InfinityKeysClient.sendChatCommand(command + "_srt");
            wasPressed = true;
        }
        else if(binding.isPressed())
        {
            InfinityKeysClient.sendChatCommand(command + "_rpt");
        }
        else if(wasPressed)
        {
            wasPressed = false;
            InfinityKeysClient.sendChatCommand(command + "_end");
        }
    }
}
