package de.drunkencoder.darksidedcookies.game.frame;

import de.drunkencoder.darksidedcookies.engine.frame.GameFrameDataInterface;

final public class GameFrameData implements GameFrameDataInterface
{
    final protected float x;
    final protected float y;
    final protected float z;
    final protected boolean isCollided;
    final protected boolean isOutOfScreen;

    public GameFrameData()
    {
        this(0.0f, 0.0f, 0.0f, false, false);
    }

    public GameFrameData(float x, float y, float z)
    {
        this(x, y, z, false, false);
    }

    public GameFrameData(float x, float y, float z, boolean isCollided, boolean isOutOfScreen)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.isCollided = isCollided;
        this.isOutOfScreen = isOutOfScreen;
    }

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public float getZ()
    {
        return this.z;
    }

    public boolean isCollided()
    {
        return this.isCollided;
    }

    public boolean isOutOfScreen()
    {
        return this.isOutOfScreen;
    }
}
