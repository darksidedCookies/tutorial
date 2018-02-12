package de.drunkencoder.darksidedcookies.framework.ui.scene;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLScene extends GLSurfaceView implements SceneInterface
{
    protected Renderer renderer;
    protected Context context;

    protected int screenX;
    protected int screenY;

    public GLScene(Context context, int screenX, int screenY)
    {
        super(context);
        setEGLContextClientVersion(2);
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void setRenderer(Renderer renderer)
    {
        this.renderer = renderer;
        super.setRenderer(this.renderer);
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener)
    {
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
