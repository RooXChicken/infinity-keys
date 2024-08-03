package com.rooxchicken.infinity.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.lwjgl.glfw.GLFW;

import com.rooxchicken.infinity.InfinityKeys;

public class InfinityKeysClient implements ClientModInitializer
{
	public static boolean doLogic = false;
	public static double scrolls;

	@Override
	public void onInitializeClient()
	{
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
		{
			doLogic = false;
		});

		load();
	}

	public static void sendChatCommand(String msg)
	{
		if(!msg.equals("hdn_verifymod") && !doLogic)
            return;
			
		MinecraftClient client = MinecraftClient.getInstance();
    	ClientPlayNetworkHandler handler = client.getNetworkHandler();
		if(handler == null)
			return;
    	handler.sendChatCommand(msg);
	}

	public static void load()
	{
		File file = new File("infinity-keys.cfg");
		if(!file.exists())
		{
			save();
			return;
		}
		try
		{
			Scanner scan = new Scanner(file);
			scan.close();
		}
		catch (FileNotFoundException e)
		{
			InfinityKeys.LOGGER.error("Failed to open config file.", e);
		}
	}

	public static void save()
	{
		File file = new File("infinity-keys.cfg");
		try
		{
			FileWriter write = new FileWriter(file);

			write.close();

		}
		catch (IOException e)
		{
			InfinityKeys.LOGGER.error("Failed to save config file.", e);
		}
	}
}