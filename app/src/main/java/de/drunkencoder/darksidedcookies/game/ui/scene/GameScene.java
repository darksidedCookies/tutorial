package de.drunkencoder.darksidedcookies.game.ui.scene;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import de.drunkencoder.darksidedcookies.framework.asset.MediaManager;
import de.drunkencoder.darksidedcookies.framework.ui.scene.GLScene;
import de.drunkencoder.darksidedcookies.game.ui.GameOverActivity;
import de.drunkencoder.darksidedcookies.game.ui.renderer.GameRenderer;

public class GameScene extends GLScene
{
    public GameScene(Context context)
    {
        super(context);

        this.setRenderer(
                new GameRenderer(context)
        );
        this.setOnTouchListener(
                new de.drunkencoder.darksidedcookies.framework.ui.listener.OnTouchListener(context)
        );

        GameOverReceiver receiver = new GameOverReceiver(this);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter("gameover"));
    }

    public void stop()
    {
        this.setRenderMode(RENDERMODE_WHEN_DIRTY);
        Intent intent = new Intent(this.context, GameOverActivity.class);
        this.context.startActivity(intent);
    }
}


class GameOverReceiver extends BroadcastReceiver
{
    private GameScene gameScene;

    public GameOverReceiver(GameScene gameScene)
    {
        this.gameScene = gameScene;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        MediaManager mediaManager = MediaManager.getInstance(context);
        mediaManager.stop("gameon");
        mediaManager.play("gameover");
        this.gameScene.stop();
    }
}

