package de.drunkencoder.darksidedcookies.framework.ui.listener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchListener implements View.OnTouchListener
{
    protected Context context;

    public OnTouchListener(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        Intent intent;
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.context);

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                intent = new Intent("down");
                localBroadcastManager.sendBroadcast(intent);
                break;
            case MotionEvent.ACTION_UP:
                intent = new Intent("up");
                localBroadcastManager.sendBroadcast(intent);
                break;
            default:
                intent = new Intent("default");
                localBroadcastManager.sendBroadcast(intent);
                break;
        }

        return true;
    }
}