package com.rooxchicken.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rooxchicken.InfinityKeys;
import com.rooxchicken.client.InfinityKeysClient;
import com.rooxchicken.data.AbilityData;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DrawGUICallback implements HudRenderCallback
{
    private MatrixStack matrixStack;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta)
    {
        // MinecraftClient client = MinecraftClient.getInstance();
        // TextRenderer textRenderer = client.textRenderer;

        // if(InfinityKeysClient.playerAbility == -1)
        //     return;
        
        // int scalingFactor = (int)client.getWindow().getScaleFactor();

        // AbilityData abilityData = InfinityKeysClient.abilityData;

        // // int posX = 100;
        // // int posY = 200;
        // double scale1 = InfinityKeysClient.abilityElement1.Scale;
        // double pos1X = InfinityKeysClient.abilityElement1.PositionX * (1/scale1);
        // double pos1Y = InfinityKeysClient.abilityElement1.PositionY * (1/scale1);

        // double scale2 = InfinityKeysClient.abilityElement2.Scale;
        // double pos2X = InfinityKeysClient.abilityElement2.PositionX * (1/scale2);
        // double pos2Y = InfinityKeysClient.abilityElement2.PositionY * (1/scale2);

        // int cooldown1 = abilityData.cooldown1;
        // int cooldown2 = abilityData.cooldown2;

        // int cooldown1Max = abilityData.cooldown1Max;
        // int cooldown2Max = abilityData.cooldown2Max;

        // String txt1 = "";
        // String txt2 = "";

        // if(cooldown1 > 0)
        //     txt1 += abilityData.cooldown1;
        // else
        //     txt1 = "READY";

        // if(cooldown2 > 0)
        //     txt2 += abilityData.cooldown2;
        // else
        //     txt2 = "READY";

        // startScaling(drawContext, scale1);
        // matrixStack.translate(pos1X, pos1Y, 0);
        
        // drawContext.drawTexture(abilityData.texture1, 0, 0, 0, 0, 64, 64, 64, 64);
        // int cooldown1Offset = (int)(64 * ((0.0+cooldown1)/cooldown1Max));
        // RenderSystem.enableBlend();
        // drawContext.setShaderColor(1, 1, 1, 0.5f);
        // drawContext.drawTexture(abilityData.cooldownTexture, 0, 64-cooldown1Offset, 0, 0, 64, cooldown1Offset, 64, 64);
        // drawContext.setShaderColor(1, 1, 1, 1);
        // drawContext.drawTexture(abilityData.outlineTexture, 0, 0, 0, 0, 64, 64, 64, 64);
        
		// stopScaling(drawContext);

        // startScaling(drawContext, scale2);
        // matrixStack.translate(pos2X, pos2Y, 0);
        
        // drawContext.drawTexture(abilityData.texture2, 0, 0, 0, 0, 64, 64, 64, 64);

        // int cooldown2Offset = (int)(64 * ((0.0+cooldown2)/cooldown2Max));
        // RenderSystem.enableBlend();
        // drawContext.setShaderColor(1, 1, 1, 0.5f);

        // drawContext.drawTexture(abilityData.cooldownTexture, 0, 64-cooldown2Offset, 0, 0, 64, cooldown2Offset, 64, 64);

        // drawContext.setShaderColor(1, 1, 1, 1);

        // if(abilityData.secondLocked)
        // {
        //     drawContext.drawTexture(Identifier.of("infinity-keys", "textures/gui/locked.png"), 0, 0, 0, 0, 64, 64, 64, 64);
        //     txt2 = "LOCKED";
        // }
        // drawContext.drawTexture(abilityData.outlineTexture, 0, 0, 0, 0, 64, 64, 64, 64);
        
		// stopScaling(drawContext);


        // startScaling(drawContext, scale1*1.5);
        // matrixStack.translate(pos1X/1.5, pos1Y/1.5, 0);
        // drawContext.drawCenteredTextWithShadow(textRenderer, txt1, 23, -10, 0xFFFFFFFF);
        // stopScaling(drawContext);

        // startScaling(drawContext, scale2*1.5);
        // matrixStack.translate(pos2X/1.5, pos2Y/1.5, 0);
        // drawContext.drawCenteredTextWithShadow(textRenderer, txt2, 23, -10, 0xFFFFFFFF);
        // stopScaling(drawContext);
    }
    
    private void startScaling(DrawContext drawContext, double scale)
    {
        matrixStack = drawContext.getMatrices();
		matrixStack.push();
		matrixStack.scale((float)scale, (float)scale, (float)scale);
    }

    private void stopScaling(DrawContext drawContext)
    {
        matrixStack.pop();
    }
}
