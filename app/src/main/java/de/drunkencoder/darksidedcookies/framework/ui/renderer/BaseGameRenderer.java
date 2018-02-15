package de.drunkencoder.darksidedcookies.framework.ui.renderer;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

abstract public class BaseGameRenderer implements GLSurfaceView.Renderer
{
    @Override
    abstract public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig);

    @Override
    abstract public void onSurfaceChanged(GL10 unused, int width, int height);

    @Override
    abstract public void onDrawFrame(GL10 gl10);
}
