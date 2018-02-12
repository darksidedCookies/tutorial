package de.drunkencoder.darksidedcookies.engine.frame;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFrame
{
    protected Map<String, GameFrameDataInterface> simpleData;
    protected Map<String, List<GameFrameDataInterface>> complexData;

    public GameFrame()
    {
        this.simpleData = new HashMap<>();
        this.complexData = new HashMap<>();
    }

    public GameFrame add(String key, GameFrameDataInterface gameFrameData)
    {
        this.simpleData.put(key, gameFrameData);
        return this;
    }

    public GameFrame addList(String key, List<GameFrameDataInterface> gameFrameDataList)
    {
        this.complexData.put(key, gameFrameDataList);
        return this;
    }

    public GameFrameDataInterface get(String key)
    {
        return this.simpleData.get(key);
    }

    public List<GameFrameDataInterface> getList(String key)
    {
        return this.complexData.get(key);
    }

    public boolean containsKey(String key)
    {
        return this.simpleData.containsKey(key);
    }

    public boolean containsListWithKey(String key)
    {
        return this.complexData.containsKey(key);
    }
}
