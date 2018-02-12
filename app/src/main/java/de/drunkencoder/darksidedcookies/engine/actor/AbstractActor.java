package de.drunkencoder.darksidedcookies.engine.actor;

abstract public class AbstractActor implements ActorInterface
{
    abstract public float getVelocity();
    abstract public float moveUp(float delta);
    abstract public float moveLeft(float delta);
    abstract public float moveRight(float delta);
    abstract public float moveDown(float delta);
}
