package de.drunkencoder.darksidedcookies.framework.ui.scene;

import android.content.Context;
import android.view.SurfaceView;

public class Scene extends SurfaceView implements SceneInterface
{
    public Scene(Context context)
    {
        super(context);
    }

    @Override
    public void setOnTouchListener(OnTouchListener listener) {
        super.setOnTouchListener(listener);
    }
}
