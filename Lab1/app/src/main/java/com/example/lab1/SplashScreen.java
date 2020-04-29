package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

public class SplashScreen extends AppCompatActivity {
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (!flag) {
            Thread timer = new Thread()
            {
                public void run()
                {
                    try
                    {
                        int timer = 0;
                        while(timer < 5000)
                        {
                            sleep(100);
                            timer = timer +100;
                        }
                    }
                    catch (Exception e)
                    {
                        e.getMessage();
                    }
                    finally
                    {
                        Intent intent = new Intent(SplashScreen.this, Activity1.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("flag", true);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        flag = savedInstanceState.getBoolean("flag");
    }
}
