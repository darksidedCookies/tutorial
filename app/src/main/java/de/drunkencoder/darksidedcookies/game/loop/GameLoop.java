package de.drunkencoder.darksidedcookies.game.loop;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.drunkencoder.darksidedcookies.engine.actor.ActorInterface;
import de.drunkencoder.darksidedcookies.engine.frame.GameFrame;
import de.drunkencoder.darksidedcookies.engine.frame.GameFrameDataInterface;
import de.drunkencoder.darksidedcookies.engine.physics.CollisionDetection;
import de.drunkencoder.darksidedcookies.framework.loop.BaseGameLoop;
import de.drunkencoder.darksidedcookies.framework.ui.receiver.BaseReceiver;
import de.drunkencoder.darksidedcookies.game.actor.Asteroid;
import de.drunkencoder.darksidedcookies.game.actor.Player;
import de.drunkencoder.darksidedcookies.game.frame.GameFrameData;

public class GameLoop extends BaseGameLoop
{
    protected int screenX;
    protected int screenY;

    public GameLoop(Context context, BaseReceiver receiver, int screenX, int screenY)
    {
        super(context, receiver);
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public GameFrame run()
    {
        this.currentFrame = new GameFrame();

        GameFrameDataInterface player = new GameFrameData(-2.0f, 0.0f, 0.0f);
        List<GameFrameDataInterface> asteroids = new ArrayList<>();

        asteroids.add(new GameFrameData(2.0f, -1.0f, 0.0f));
        asteroids.add(new GameFrameData(2.0f, 0.0f, 0.0f));
        asteroids.add(new GameFrameData(2.0f, 1.0f, 0.0f));

        this.currentFrame.add("Player", player);
        this.currentFrame.addList("Asteroid", asteroids);

        return this.currentFrame;
    }

    public GameFrame update(GameFrame delta)
    {
        this.lastFrame = this.currentFrame;

        GameFrame newGameFrame = new GameFrame();
        GameFrameDataInterface oldPlayerData = this.lastFrame.get("Player");

        boolean gameOver = false;
        boolean isCollided = false;
        boolean isOutOfScreen = false;

        if (this.lastFrame.containsListWithKey("Asteroid"))
        {
            ActorInterface asteroidActor = new Asteroid();
            List<GameFrameDataInterface> newAsteroidData = new ArrayList<>();

            for(GameFrameDataInterface oldAsteroidData : this.lastFrame.getList("Asteroid"))
            {
                isCollided = this.detectCollision(oldPlayerData, oldAsteroidData);
                isOutOfScreen = this.detectOutOfScreen(oldAsteroidData);
                gameOver = gameOver || isCollided;

                newAsteroidData.add(
                        new GameFrameData(
                            oldAsteroidData.getX() - asteroidActor.getVelocity(),
                            oldAsteroidData.getY(),
                            oldAsteroidData.getZ(),
                            isCollided,
                            isOutOfScreen
                        )
                );
            }

            if((int) Math.ceil(Math.random() * 100) <= 5)
            {
                newAsteroidData.add(
                        new GameFrameData(
                                3.0f + (float) (Math.random() * 1.7f),
                                this.random(),
                                0.0f,
                                false,
                                false
                        )
                );
            }

            newGameFrame.addList("Asteroid", newAsteroidData);
        }

        ActorInterface playerActor = new Player();

        float y = oldPlayerData.getY() + (
                ((this.receiver.isUp()) ? -playerActor.getVelocity() : (this.receiver.isDown()) ? playerActor.getVelocity() : 0.0f)
        );

        if(this.detectOutOfScreen(oldPlayerData)) {
            y = Math.copySign(1.1f, y);
        }

        GameFrameDataInterface newPlayerData = new GameFrameData(
                oldPlayerData.getX(),
                y,
                oldPlayerData.getZ(),
                gameOver,
                this.detectOutOfScreen(oldPlayerData)
        );

        newGameFrame.add("Player", newPlayerData);

        this.currentFrame = newGameFrame;
        return this.currentFrame;
    }

    protected float random()
    {
        float sign = (float) Math.pow(-1.0f, Math.floor(Math.random() * 3));
        float pos = (float) Math.floor(Math.random() * 3.7f);
        float random = (float) Math.ceil(Math.random() * 2.3f);
        return (sign * pos) - (-1.0f * sign + random) + 5.0f;
    }

    protected boolean detectCollision(GameFrameDataInterface player, GameFrameDataInterface object)
    {
        return CollisionDetection.detectCollision(
                player.getX(), player.getY(), player.getZ(),
                object.getX(), object.getY(), object.getZ()
        );
    }

    protected boolean detectOutOfScreen(GameFrameDataInterface object)
    {
        return (Math.abs(object.getX()) >= 2.401f || Math.abs(object.getY()) >= 1.101f);
    }
}
