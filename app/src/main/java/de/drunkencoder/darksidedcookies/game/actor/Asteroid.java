package de.drunkencoder.darksidedcookies.game.actor;

import de.drunkencoder.darksidedcookies.engine.actor.AbstractActor;

public class Asteroid extends AbstractActor
{
    protected float velocity = 1.0f/100.0f;

    public float getVelocity()
    {
        return this.velocity;
    }

    public float moveUp(float delta)
    {
        return 0.0f;
    }

    public float moveLeft(float delta)
    {
        return 0.0f;
    }

    public float moveRight(float delta)
    {
        return 0.0f;
    }

    public float moveDown(float delta)
    {
        return 0.0f;
    }
}