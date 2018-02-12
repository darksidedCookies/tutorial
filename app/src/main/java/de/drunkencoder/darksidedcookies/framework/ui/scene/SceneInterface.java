package de.drunkencoder.darksidedcookies.framework.ui.scene;

import android.view.View;

public interface SceneInterface
{
    public void setOnTouchListener(View.OnTouchListener listener);
    public int getScreenX();
    public int getScreenY();
}
