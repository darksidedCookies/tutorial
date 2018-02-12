package de.drunkencoder.darksidedcookies.framework.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

abstract public class BaseReceiver extends BroadcastReceiver
{
    abstract public void onReceive(Context context, Intent intent);
    abstract public boolean isUp();
    abstract public boolean isDown();
}