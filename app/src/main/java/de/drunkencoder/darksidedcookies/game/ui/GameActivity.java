package de.drunkencoder.darksidedcookies.game.ui;

import android.content.pm.ActivityInfo;
import android.view.SurfaceView;

import de.drunkencoder.darksidedcookies.framework.ui.BaseGameActivity;
import de.drunkencoder.darksidedcookies.framework.ui.scene.SceneManager;
import de.drunkencoder.darksidedcookies.game.ui.scene.GameScene;

public class GameActivity extends BaseGameActivity
{
    public GameActivity()
    {
        super();
    }

    protected void init()
    {
        this.sceneManager = new SceneManager(this, new GameScene(this));
        this.setContentView((SurfaceView) this.sceneManager.getScene());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }
}