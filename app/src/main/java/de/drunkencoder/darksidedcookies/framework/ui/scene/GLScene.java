package de.drunkencoder.darksidedcookies.framework.ui.scene;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLScene extends GLSurfaceView implements SceneInterface
{
    protected Renderer renderer;
    protected Context context;

    public GLScene(Context context)
    {
        super(context);
        setEGLContextClientVersion(2);
        this.context = context;
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
}
