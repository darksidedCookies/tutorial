package de.drunkencoder.darksidedcookies.game.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.drunkencoder.darksidedcookies.game.R;

public class GameOverActivity extends AppCompatActivity
{
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Game Over! :(");
    }

    @Override
    public void onBackPressed()
    {
        Intent startMain = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(startMain);
    }

}
