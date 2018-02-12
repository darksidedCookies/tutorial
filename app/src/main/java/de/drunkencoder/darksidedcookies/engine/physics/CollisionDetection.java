package de.drunkencoder.darksidedcookies.engine.physics;

public class CollisionDetection
{
    public static boolean detectCollision(float px, float py, float pz, float ox, float oy, float oz)
    {
        return epsilonEqual(px, ox, 0.19f) && epsilonEqual(py, oy, 0.19f) && epsilonEqual(pz, oz, 0.19f);
    }

    public static boolean epsilonEqual(float a, float b, float eps)
    {
        if(a==b)
           return true;
        else
            return Math.abs(a - b) < eps;
    }
}
