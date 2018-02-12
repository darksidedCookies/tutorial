package de.drunkencoder.darksidedcookies.framework.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import de.drunkencoder.darksidedcookies.framework.ui.scene.SceneManager;

abstract public class BaseGameActivity extends AppCompatActivity
{
    protected SceneManager sceneManager;

    protected int screenX;
    protected int screenY;

    public BaseGameActivity()
    {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        this.screenX = size.x;
        this.screenY = size.y;

        this.init();
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        this.sceneManager.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        this.sceneManager.resume();
    }

    abstract protected void init();
}