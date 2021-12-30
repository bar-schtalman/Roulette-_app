package com.example.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Loser_screen extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loser_screen);
        textView = findViewById(R.id.textView12);
        long value = getIntent().getLongExtra("num",1);
        textView.setText("you lost "+ value+"$");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Loser_screen.this,Table.class));
            }
        },3000);
    }
}