package com.udacitytuts.batmanapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
  /*AppCompactActivity is used for lollipop and below
    Use Activity for Marshmallow and above*/
    private ImageButton batButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        batButton = (ImageButton) findViewById(R.id.batman);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.iambatman);

        batButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(MainActivity.this,"I'm Done",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
