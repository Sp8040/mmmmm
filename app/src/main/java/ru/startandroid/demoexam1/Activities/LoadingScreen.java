package ru.startandroid.demoexam1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import ru.startandroid.demoexam1.R;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);


        Timer timer = new Timer();
        TimerTask ts = new TimerTask() {
            @Override
            public void run() {
                boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("FirstRun", true);

                if(isFirstRun){
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("FirstRun", false).apply();
                    startActivity(new Intent(LoadingScreen.this, RegisterScreen.class));
                }
                else{
                    startActivity(new Intent(LoadingScreen.this, LoginScreen.class));
                }

            }
        };

        timer.schedule(ts, 3000L);
    }
}
