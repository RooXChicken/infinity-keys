package com.rooxchicken.screen;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rooxchicken.InfinityKeys;
import com.rooxchicken.client.InfinityKeysClient;
import com.rooxchicken.data.AbilityData;
import com.rooxchicken.data.AbilityDesc;
import com.rooxchicken.data.Node;
import com.rooxchicken.data.SkillTree;
import com.rooxchicken.mixin.MixinScrolls;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AbilitySelection extends Screen
{
    private SkillTree tree;
    private int mouseStatus = -2;
    private int clickAction = -1;

    private double smoothScale = 3;
    private double scale = 1;

    private double offsetX = 0;
    private double offsetY = 0;

    private double smoothX = 0;
    private double smoothY = 0;

    private int mouseX = 0;
    private int mouseY = 0;

    private double oldMouseX = 0;
    private double oldMouseY = 0;

    private int oldWW = 0;
    private int oldWH = 0;

    private boolean dragging = false;
    private Identifier frameTex = Identifier.of("infinity-keys", "textures/gui/frame.png");

    public AbilitySelection(Text title, SkillTree _tree)
    {
        super(title);
        tree = _tree;
    }

    @Override
	public void init()
	{
        for(Node node : tree.nodes)
        {
            node.positionX += -oldWW + width/2;
            node.positionY += -oldWH + height/2;
        }
        
        oldWW = width/2;
        oldWH = height/2;

        smoothX = oldWW;
        smoothY = oldWH;
	}
	 
	@Override
	public void close()
	{
		super.close();
	}
	 
    @Override
    public boolean mouseClicked(double _mouseX, double _mouseY, int button)
    {
    	mouseStatus = button;

        if(clickAction != -1)
        {
            client.world.playSound(client.player, client.player.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 0.6f, 1f);
            switch(clickAction)
            {
                case 0:
                    client.world.playSound(client.player, client.player.getBlockPos(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.MASTER, 0.6f, 1f);
                break;
                case 1:
                    
                break;
                case 2:
                    
                break;
            }
        }
        else
        {
            dragging = true;
            oldMouseX = mouseX - smoothX;
            oldMouseY = mouseY - smoothY;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button)
    {
    	mouseStatus = -1;
        dragging = false;
    	
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    public void doTick()
    {
        double scalingFactor = client.getWindow().getScaleFactor();

    	scale += InfinityKeysClient.scrolls/8.0;
        InfinityKeysClient.scrolls = 0;

        double smooth = 1;
        if(scale < smoothScale)
        {
            double prog = (scale/smoothScale)*smooth;
            if(prog > 0.98)
                prog = 1;
            smoothScale = lerp(scale, smoothScale, prog);
        }
        if(scale > smoothScale)
        {
            double prog = (smoothScale/scale)*smooth;
            if(prog > 0.98)
                prog = 1;
            smoothScale = lerp(scale, smoothScale, prog);
        }

        if(dragging)
        {
            smoothX = mouseX - oldMouseX;
            smoothY = mouseY - oldMouseY;
        }

        if(scale < 0.1)
            scale = 0.1;
        if(smoothScale < 0.1)
            smoothScale = 0.1;

        offsetX = smoothX/smoothScale - width/2 - (16/2*smoothScale);
        offsetY = smoothY/smoothScale - height/2 - (16/2*smoothScale);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
        {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public void render(DrawContext context, int _mouseX, int _mouseY, float delta)
    {
        doTick();
        mouseX = _mouseX;
        mouseY = _mouseY;
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
		this.renderBackground(context);

        double screenX = mouseX/smoothScale - offsetX + 1;
        double screenY = mouseY/smoothScale - offsetY + 1;

        clickAction = -1;
		
        RenderSystem.enableBlend();
        context.drawText(textRenderer, "Points: " + 6, 2, 2, 0xFFFFFFFF, true);
        startScaling(context, smoothScale);

        RenderSystem.setShaderColor(tree.r, tree.g, tree.b, 1);
        
        Node prevNode = null;
        int i = 0;
        for(Node node : tree.nodes)
        {
            GlStateManager._enableBlend();
            GlStateManager._disableDepthTest();

            if(prevNode != null)
            {
                int nodeScale = 8;
                if(node.positionX != prevNode.positionX)
                    context.fill((int)node.positionX + nodeScale, (int)node.positionY + nodeScale + 1, (int)prevNode.positionX + nodeScale, (int)prevNode.positionY + nodeScale - 1, 0, 0xFFFFFFFF);
                
                if(node.positionY != prevNode.positionY)
                    context.fill((int)node.positionX + nodeScale + 1, (int)node.positionY + nodeScale, (int)prevNode.positionX + nodeScale - 1, (int)prevNode.positionY + nodeScale, 0, 0xFFFFFFFF);
            }

            if(node.render)
            {
                if(screenX > node.positionX && screenX < (node.positionX + 16) && screenY > node.positionY && screenY < (node.positionY+16))
                {
                    setTooltip(Text.of(node.description));
                    clickAction = i;

                    RenderSystem.setShaderColor(tree.r*0.6f, tree.g*0.6f, tree.b*0.6f, 1);
                }

                if(node.unlocked)
                    RenderSystem.setShaderColor(tree.r*0.4f, tree.g*0.4f, tree.b*0.4f, 1);
                
                context.drawTexture(frameTex, (int)node.positionX, (int)node.positionY, 2, 0f, 0f, 16, 16, 16, 16);
                context.drawTexture(node.texture, (int)node.positionX, (int)node.positionY, 2, 0f, 0f, 16, 16, 16, 16);

                RenderSystem.setShaderColor(tree.r, tree.g, tree.b, 1);

                i++;
            }
            else if(node.texture != null)
                context.drawTexture(node.texture, (int)node.positionX + 5, (int)node.positionY + 4, 2, 0f, 0f, 8, 8, 8, 8);

            // if(prevNode != null)
            // {
                // Tessellator tessellator = RenderSystem.renderThreadTesselator();
                // RenderSystem.setShader(GameRenderer::getPositionProgram);
                
                // BufferBuilder bufferBuilder = tessellator.getBuffer();
                // bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

                // bufferBuilder.vertex((node.positionX + offsetX) * smoothScale + nodeScale, (node.positionY + offsetY) * smoothScale + nodeScale, -1).next();
                // bufferBuilder.vertex((prevNode.positionX + offsetX) * smoothScale + nodeScale, (prevNode.positionY + offsetY) * smoothScale + nodeScale, -1).next();

                // if(node.positionX != prevNode.positionX)
                // {
                //     bufferBuilder.vertex((node.positionX + offsetX) * smoothScale + nodeScale, (node.positionY + offsetY + 0.1) * smoothScale + nodeScale, -1).next();
                //     bufferBuilder.vertex((prevNode.positionX + offsetX) * smoothScale + nodeScale, (prevNode.positionY + offsetY + 0.1) * smoothScale + nodeScale, -1).next();
                //     bufferBuilder.vertex((node.positionX + offsetX) * smoothScale + nodeScale, (node.positionY + offsetY - 0.1) * smoothScale + nodeScale, -1).next();
                //     bufferBuilder.vertex((prevNode.positionX + offsetX) * smoothScale + nodeScale, (prevNode.positionY + offsetY - 0.1) * smoothScale + nodeScale, -1).next();
                // }

                // if(node.positionY != prevNode.positionY)
                // {
                //     bufferBuilder.vertex((node.positionX + offsetX + 0.1) * smoothScale + nodeScale, (node.positionY + offsetY) * smoothScale + nodeScale, -1).next();
                //     bufferBuilder.vertex((prevNode.positionX + offsetX + 0.1) * smoothScale + nodeScale, (prevNode.positionY + offsetY) * smoothScale + nodeScale, -1).next();
                //     bufferBuilder.vertex((node.positionX + offsetX - 0.1) * smoothScale + nodeScale, (node.positionY + offsetY) * smoothScale + nodeScale, -1).next();
                //     bufferBuilder.vertex((prevNode.positionX + offsetX - 0.1) * smoothScale + nodeScale, (prevNode.positionY + offsetY) * smoothScale + nodeScale, -1).next();
                // }

                // tessellator.draw();
                // bufferBuilder.clear();
            //}

            prevNode = node;
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);

        GlStateManager._disableBlend();
        GlStateManager._enableDepthTest();

        stopScaling(context);
    	
    	super.render(context, _mouseX, _mouseY, delta);
    }

    private void startScaling(DrawContext drawContext, double scale)
    {
        MatrixStack matrixStack = drawContext.getMatrices();
		matrixStack.push();
		matrixStack.scale((float)scale, (float)scale, (float)scale);
        matrixStack.translate(offsetX, offsetY, 0);
    }

    private void stopScaling(DrawContext drawContext)
    {
        drawContext.getMatrices().pop();
    }

    private double lerp(double a, double b, double amount)
    {
        return (a * (1.0 - amount)) + (b * amount);
    }
}
