package de.drunkencoder.darksidedcookies.game.ui.receiver;

import android.content.Context;
import android.content.Intent;

import de.drunkencoder.darksidedcookies.framework.ui.receiver.BaseReceiver;

public class GameReceiver extends BaseReceiver
{
    protected boolean up = false;
    protected boolean down = false;

    public void onReceive(Context context, Intent intent)
    {
        switch (intent.getAction())
        {
            case "up":
                this.up = true;
                this.down = false;
                break;
            case "down":
                this.up = false;
                this.down = true;
                break;
            default:
                this.up = false;
                this.down = false;
                break;
        }
    }

    public boolean isUp()
    {
        return this.up;
    }

    public boolean isDown()
    {
        return this.down;
    }
}