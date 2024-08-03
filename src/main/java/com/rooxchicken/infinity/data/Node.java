package com.rooxchicken.infinity.data;

import net.minecraft.util.Identifier;

public class Node
{
    public Identifier texture;
    public double positionX;
    public double positionY;

    public String description;
    public boolean render;
    public boolean skip;
    public boolean unlocked;
    public boolean locked = false;

    public int clickAction;

    public Node() {}

    public Node(Identifier _texture, double _positionX, double _positionY, String _description, boolean _render, boolean _skip, boolean _unlocked, boolean _locked, int _clickAction)
    {
        texture = _texture;
        positionX = _positionX;
        positionY = _positionY;

        description = _description;
        render = _render;
        skip = _skip;

        unlocked = _unlocked;
        locked = _locked;

        clickAction = _clickAction;
    }
}
