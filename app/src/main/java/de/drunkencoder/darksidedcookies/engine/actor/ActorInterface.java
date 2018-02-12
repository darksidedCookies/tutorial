package de.drunkencoder.darksidedcookies.engine.actor;

import de.drunkencoder.darksidedcookies.engine.gameobject.GameObjectInterface;

public interface ActorInterface extends GameObjectInterface
{
    public float moveUp(float delta);
    public float moveLeft(float delta);
    public float moveRight(float delta);
    public float moveDown(float delta);
    public float getVelocity();
}
