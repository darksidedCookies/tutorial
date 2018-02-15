package de.drunkencoder.darksidedcookies.game.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import de.drunkencoder.darksidedcookies.framework.asset.MediaManager;
import de.drunkencoder.darksidedcookies.game.R;


public class MainActivity extends Activity implements View.OnClickListener
{
    private ImageButton buttonPlay;
    private MediaManager mediaManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        this.mediaManager = MediaManager.getInstance(this);
        this.mediaManager.add("gameon", R.raw.gameon);
        this.mediaManager.add("gameover", R.raw.gameover);

        this.buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        this.buttonPlay.setOnClickListener(this);

        this.mediaManager.loop("gameon");
    }

    @Override
    public void onClick(View v)
    {
        if (v == this.buttonPlay) {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        MediaManager mediaManager = MediaManager.getInstance(null);
                        mediaManager.stop("gameon");
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
