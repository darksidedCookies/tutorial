package de.drunkencoder.darksidedcookies.framework.ui.scene;

import android.content.Context;
import android.util.Log;

public class SceneManager implements Runnable
{
    volatile boolean playing;
    protected Thread gameThread;

    protected Context context;
    protected int screenX;
    protected int screenY;

    protected SceneInterface currentScene;

    public SceneManager(Context context, int screenX, int screenY, SceneInterface scene)
    {
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.currentScene = scene;
    }

    @Override
    public void run()
    {
        while (this.playing)
        {
            this.update();
            this.draw();
            this.control();
        }
    }

    protected void update()
    {
        // @ToDo: Update logic
    }

    protected void draw()
    {
        // @ToDo: Draw logic
    }

    protected void control()
    {
        try {
            this.gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause()
    {
        this.playing = false;
        try {
            this.gameThread.join();
        } catch (InterruptedException e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    public void resume()
    {
        this.playing = true;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public SceneInterface getScene()
    {
        if(null == this.currentScene)
            this.currentScene = new GLScene(this.context, this.screenX, this.screenY);

        return this.currentScene;
    }
}
