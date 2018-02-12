package de.drunkencoder.darksidedcookies.framework.loop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import de.drunkencoder.darksidedcookies.engine.frame.GameFrame;
import de.drunkencoder.darksidedcookies.framework.ui.receiver.BaseReceiver;

abstract public class BaseGameLoop
{
    protected Context context;
    protected BaseReceiver receiver;

    protected GameFrame lastFrame;
    protected GameFrame currentFrame;

    public BaseGameLoop(Context context, BaseReceiver receiver)
    {
        this.receiver = receiver;
        this.context = context;
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.receiver, new IntentFilter("up"));
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.receiver, new IntentFilter("down"));
    }

    abstract public GameFrame run();
    abstract public GameFrame update(GameFrame delta);
}
