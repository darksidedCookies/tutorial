package de.drunkencoder.darksidedcookies.framework.ui.scene;

import android.content.Context;
import android.view.SurfaceView;

public class Scene extends SurfaceView implements SceneInterface
{
    protected OnTouchListener listener;

    protected int screenX;
    protected int screenY;

    public Scene(Context context, int screenX, int screenY) {
        super(context);
        this.screenX = screenX;
        this.screenY = screenY;
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener) {
        this.listener = listener;
        super.setOnTouchListener(listener);
    }

    public int getScreenX()
    {
        return this.screenX;
    }

    public int getScreenY()
    {
        return this.screenY;
    }
}
