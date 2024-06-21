package com.rooxchicken.data;

import java.util.ArrayList;

public class SkillTree
{
    public String name;
    public float r;
    public float g;
    public float b;

    public ArrayList<Node> nodes;

    public SkillTree(String _name, float _r, float _g, float _b)
    {
        name = _name;
        r = _r;
        g = _g;
        b = _b;

        nodes = new ArrayList<Node>();
    }
}
