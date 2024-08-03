package com.rooxchicken.infinity.screen;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rooxchicken.infinity.InfinityKeys;
import com.rooxchicken.infinity.client.InfinityKeysClient;
import com.rooxchicken.infinity.data.HandleData;
import com.rooxchicken.infinity.data.Node;
import com.rooxchicken.infinity.data.SkillTree;
import com.rooxchicken.infinity.mixin.MixinScrolls;

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
    private Node selectedNode = null;

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

    private boolean resetZoom;

    private boolean dragging = false;
    private Identifier frameTex = Identifier.of("infinity-keys", "textures/gui/frame.png");
    private Identifier lockTex = Identifier.of("infinity-keys", "textures/gui/icons/37.png");

    public AbilitySelection(Text title, SkillTree _tree, boolean _resetZoom)
    {
        super(title);

        resetZoom = _resetZoom;
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

        if(resetZoom)
        {
            HandleData.smoothX = -width/2 + 8;
            HandleData.smoothY = -height/2 + 8;

            scale = tree.defaultScale;
            HandleData.smoothScale = tree.defaultScale*3;
        }
        else
            scale = HandleData.smoothScale;
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

        if(selectedNode != null && selectedNode.clickAction != -1)
        {
            if(!selectedNode.locked)
                client.world.playSound(client.player, client.player.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 0.6f, 1f);
            else
                client.world.playSound(client.player, client.player.getBlockPos(), SoundEvents.BLOCK_CHAIN_HIT, SoundCategory.MASTER, 0.6f, 1f);

            InfinityKeysClient.sendChatCommand("hdn_action " + tree.index + " " + selectedNode.clickAction + " " + button);
        }
        else
        {
            dragging = true;
            oldMouseX = mouseX;
            oldMouseY = mouseY;
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
        smoothScale = HandleData.smoothScale;
        smoothX = HandleData.smoothX;
        smoothY = HandleData.smoothY;

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
            smoothX += (mouseX - oldMouseX)/smoothScale;
            smoothY += (mouseY - oldMouseY)/smoothScale;

            oldMouseX = mouseX;
            oldMouseY = mouseY;
        }

        if(scale < 0.4)
            scale = 0.4;
        if(smoothScale < 0.4)
            smoothScale = 0.4;

        offsetX = width/smoothScale/2 + smoothX - 16;
        offsetY = height/smoothScale/2 + smoothY - 16;

        HandleData.smoothScale = smoothScale;
        HandleData.smoothX = smoothX;
        HandleData.smoothY = smoothY;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
        {
            if(tree.index == 0)
                close();
            else
                InfinityKeysClient.sendChatCommand("skilltree");
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
		this.renderBackground(context, _mouseX, _mouseY, delta);

        double screenX = mouseX/smoothScale - offsetX + 1;
        double screenY = mouseY/smoothScale - offsetY + 1;

        selectedNode = null;
		
        RenderSystem.enableBlend();
        context.drawText(textRenderer, "Tokens: " + tree.points, 2, 2, 0xFFFFFFFF, true);
        startScaling(context, smoothScale);

        RenderSystem.setShaderColor(tree.r, tree.g, tree.b, 1);
        
        Node prevNode = null;
        Node prevIconNode = null;
        int i = 0;
        for(Node node : tree.nodes)
        {
            GlStateManager._enableBlend();
            GlStateManager._disableDepthTest();

            if(tree.nodesConnected && prevNode != null)
            {
                if(!node.skip)
                {
                    int nodeScale = 8;

                    if(prevIconNode != null && !prevIconNode.unlocked)
                        RenderSystem.setShaderColor(tree.r*0.4f, tree.g*0.4f, tree.b*0.4f, 1);
                    
                    if(node.positionX != prevNode.positionX)
                        context.fill((int)node.positionX + nodeScale, (int)node.positionY + nodeScale + 1, (int)prevNode.positionX + nodeScale, (int)prevNode.positionY + nodeScale - 1, 0, 0xFFFFFFFF);
                    
                    if(node.positionY != prevNode.positionY)
                        context.fill((int)node.positionX + nodeScale + 1, (int)node.positionY + nodeScale, (int)prevNode.positionX + nodeScale - 1, (int)prevNode.positionY + nodeScale, 0, 0xFFFFFFFF);
                    
                    RenderSystem.setShaderColor(tree.r, tree.g, tree.b, 1);
                }
            }

            if(node.render)
            {
                if(screenX > node.positionX && screenX < (node.positionX + 16) && screenY > node.positionY && screenY < (node.positionY+16))
                {
                    setTooltip(Text.of(node.description));
                    selectedNode = node;

                    RenderSystem.setShaderColor(tree.r*0.6f, tree.g*0.6f, tree.b*0.6f, 1);
                }

                if(node.unlocked || node.locked)
                    RenderSystem.setShaderColor(tree.r*0.4f, tree.g*0.4f, tree.b*0.4f, 1);
                
                context.drawTexture(frameTex, (int)node.positionX, (int)node.positionY, 2, 0f, 0f, 16, 16, 16, 16);
                context.drawTexture(node.texture, (int)node.positionX, (int)node.positionY, 2, 0f, 0f, 16, 16, 16, 16);

                RenderSystem.setShaderColor(tree.r, tree.g, tree.b, 1);

                if(node.locked)
                    context.drawTexture(lockTex, (int)node.positionX, (int)node.positionY, 2, 0f, 0f, 16, 16, 16, 16);

                i++;
            }
            else if(node.texture != null && !node.description.equals("heart"))
            {
                if(prevIconNode != null && !prevIconNode.unlocked)
                    RenderSystem.setShaderColor(tree.r*0.4f, tree.g*0.4f, tree.b*0.4f, 1);
                context.drawTexture(node.texture, (int)node.positionX + 4, (int)node.positionY + 4, 2, 0f, 0f, 10, 8, 10, 8);
                RenderSystem.setShaderColor(tree.r, tree.g, tree.b, 1);
            }

            if(node.description.equals("heart"))
            {
                RenderSystem.enableBlend();
                context.drawTexture(node.texture, (int)(node.positionX), (int)(node.positionY), 2, 0f, 0f, 120, 90, 120, 90);
            }

            prevNode = node;
            if(node.clickAction != -1)
                prevIconNode = node;
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);

        GlStateManager._disableBlend();
        GlStateManager._enableDepthTest();

        stopScaling(context);
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
