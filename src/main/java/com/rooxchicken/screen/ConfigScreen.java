package com.rooxchicken.screen;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rooxchicken.InfinityKeys;
import com.rooxchicken.client.InfinityKeysClient;
import com.rooxchicken.data.AbilityData;
import com.rooxchicken.data.AbilityDesc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConfigScreen extends Screen
{
    private int mouseStatus = -2;
    private int index = 0;
    private int clickAction = -1;

    private ButtonWidget resetButton;
    public boolean ObjectSelected = false;

    public ConfigScreen(Text title)
    {
        super(title);
    }

    @Override
	public void init()
	{	
		// resetButton = ButtonWidget.builder(Text.of("Reset"), (button) ->
        // {
        //     InfinityKeysClient.abilityElement1.reset();
        //     InfinityKeysClient.abilityElement2.reset();
        // }).dimensions(width/2 - 50, height - 30, 100, 20).build();

        // addDrawableChild(resetButton);
	}
	 
	@Override
	public void close()
	{
		super.close();
	}
	 
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
    	mouseStatus = button;
        // InfinityKeysClient.abilityElement1.MouseStatus = mouseStatus;
        // InfinityKeysClient.abilityElement2.MouseStatus = mouseStatus;
        
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
    	mouseStatus = -1;
        // InfinityKeysClient.abilityElement1.MouseStatus = mouseStatus;
        // InfinityKeysClient.abilityElement2.MouseStatus = mouseStatus;

        InfinityKeysClient.save();
    	
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public void tick()
    {

    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
        {
            MinecraftClient.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        MinecraftClient client = MinecraftClient.getInstance();
		this.renderBackground(context);
		
        RenderSystem.enableBlend();
        // InfinityKeysClient.abilityElement1.HandleLines(this, context, textRenderer, mouseX, mouseY);
        // InfinityKeysClient.abilityElement2.HandleLines(this, context, textRenderer, mouseX, mouseY);
    	
    	super.render(context, mouseX, mouseY, delta);
    }
    
}
