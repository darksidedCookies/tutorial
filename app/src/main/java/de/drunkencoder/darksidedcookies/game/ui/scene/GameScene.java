package de.drunkencoder.darksidedcookies.game.ui.scene;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import de.drunkencoder.darksidedcookies.framework.ui.scene.GLScene;
import de.drunkencoder.darksidedcookies.game.ui.GameOverActivity;
import de.drunkencoder.darksidedcookies.game.ui.renderer.GameRenderer;

public class GameScene extends GLScene
{
    public GameScene(Context context, int screenX, int screenY)
    {
        super(context, screenX, screenY);

        this.setRenderer(
                new GameRenderer(context, screenX, screenY)
        );
        this.setOnTouchListener(
                new de.drunkencoder.darksidedcookies.framework.ui.listener.OnTouchListener(context)
        );

        RendererReceiver receiver = new RendererReceiver(this);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter("gameover"));
    }

    public void stop()
    {
        this.setRenderMode(RENDERMODE_WHEN_DIRTY);
        Intent intent = new Intent(this.context, GameOverActivity.class);
        this.context.startActivity(intent);
    }
}


class RendererReceiver extends BroadcastReceiver
{
    private GameScene gameScene;

    public RendererReceiver(GameScene gameScene)
    {
        this.gameScene = gameScene;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.gameScene.stop();
    }
}

