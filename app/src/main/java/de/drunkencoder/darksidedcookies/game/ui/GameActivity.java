package de.drunkencoder.darksidedcookies.game.ui;

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
        this.sceneManager = new SceneManager(this, this.screenX, this.screenY, new GameScene(this, this.screenX, this.screenY));
        this.setContentView((SurfaceView) this.sceneManager.getScene());
    }
}