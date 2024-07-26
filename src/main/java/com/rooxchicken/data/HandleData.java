package com.rooxchicken.data;

import java.util.ArrayList;

import com.rooxchicken.InfinityKeys;
import com.rooxchicken.client.InfinityKeysClient;
import com.rooxchicken.screen.AbilitySelection;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HandleData
{
    private static SkillTree currentTree;

    public static double smoothX;
    public static double smoothY;
    public static double smoothScale;

    public static ArrayList<String> silentPlayers;

    public static void parseData(String msg)
    {
        String[] data = msg.split("_");
        int mode = Integer.parseInt(data[1]);

        switch(mode)
        {
            case 0: //set ability
                InfinityKeysClient.playerAbility = Integer.parseInt(data[2]);
                InfinityKeysClient.abilityData = new AbilityData("" + InfinityKeysClient.playerAbility);
                InfinityKeysClient.abilityData.secondLocked = !Boolean.parseBoolean(data[3]);
                InfinityKeysClient.sendChatCommand("hdn_verifymod");
            break;
            case 1: //add silent player
                if(Integer.parseInt(data[3]) == 0)
                    { if(!silentPlayers.contains(data[2])) silentPlayers.add(data[2]); }
                else
                    { if(silentPlayers.contains(data[2])) silentPlayers.remove(data[2]); }
            break;
            case 2:
                if(data[2].equals("srt"))
                {
                    currentTree = new SkillTree(data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5]), Float.parseFloat(data[6]), Float.parseFloat(data[7]), Boolean.parseBoolean(data[8]), Double.parseDouble(data[9]));
                    return;
                }

                Node node = new Node();

                if(data[2].equals("n") || data[2].equals("l"))
                    node.texture = null;
                else
                    node.texture = Identifier.of("infinity-keys", "textures/gui/" + data[2] + ".png");

                node.positionX = Integer.parseInt(data[3]);
                node.positionY = Integer.parseInt(data[4]);

                if(data[5].equals("n") || data[5].equals("l"))
                    node.description = "";
                else
                    node.description = data[5];

                node.render = Boolean.parseBoolean(data[6]);
                node.skip = Boolean.parseBoolean(data[7]);
                node.unlocked = Boolean.parseBoolean(data[8]);
                node.locked = Boolean.parseBoolean(data[9]);

                node.clickAction = Integer.parseInt(data[10]);

                currentTree.nodes.add(node);
            break;
            case 3:
                InfinityKeysClient.playerAbility = -2;
                InfinityKeysClient.abilityData = new AbilityData("empty");

                currentTree.points = Integer.parseInt(data[2]);

                MinecraftClient client = MinecraftClient.getInstance();

                client.setScreen(new AbilitySelection(Text.of("Ability Selection"), currentTree, Boolean.parseBoolean(data[3])));
            break;
        }
    }
}
