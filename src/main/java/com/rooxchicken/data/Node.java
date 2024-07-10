package com.rooxchicken.data;

import net.minecraft.util.Identifier;

public class Node
{
    public Identifier texture;
    public double positionX;
    public double positionY;

    public String description;
    public boolean render;
    public boolean unlocked;

    public int clickAction;

    public Node() {}

    public Node(Identifier _texture, double _positionX, double _positionY, String _description, boolean _render, boolean _unlocked, int _clickAction)
    {
        texture = _texture;
        positionX = _positionX;
        positionY = _positionY;

        description = _description;
        render = _render;

        unlocked = _unlocked;
        
        clickAction = _clickAction;
    }
}
