package de.drunkencoder.darksidedcookies.framework.asset;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

public class MediaManager
{
    protected Context context;
    protected Map<String, MediaPlayer> mediaPlayer;

    private static MediaManager instance = null;

    public static MediaManager getInstance(Context context)
    {
        if(null == instance)
            instance = new MediaManager(context);
        return instance;
    }

    protected MediaManager(Context context)
    {
        this.context  = context;
        this.mediaPlayer = new HashMap<>();
    }

    public MediaManager add(String name, int id)
    {
        MediaPlayer sound = MediaPlayer.create(this.context, id);
        this.mediaPlayer.put(name, sound);
        return this;
    }

    public void play(String name)
    {
        if(this.mediaPlayer.containsKey(name))
            this.mediaPlayer.get(name).start();
    }

    public void loop(String name)
    {
        if(this.mediaPlayer.containsKey(name))
        {
            this.mediaPlayer.get(name).setLooping(true);
            this.mediaPlayer.get(name).start();
        }
    }


    public void stop(String name)
    {
        if(this.mediaPlayer.containsKey(name))
            this.mediaPlayer.get(name).stop();
    }
}
