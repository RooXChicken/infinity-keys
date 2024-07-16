package com.rooxchicken.data;

import java.util.ArrayList;

public class SkillTree
{
    public String name;
    public int index;
    public float r;
    public float g;
    public float b;
    public boolean nodesConnected;
    public double defaultScale;

    public int points = 0;

    public ArrayList<Node> nodes;

    public SkillTree(String _name, int _index, float _r, float _g, float _b, boolean connected, double _defaultScale)
    {
        name = _name;
        index = _index;
        
        r = _r;
        g = _g;
        b = _b;

        nodesConnected = connected;
        defaultScale = _defaultScale;

        nodes = new ArrayList<Node>();
    }
}
