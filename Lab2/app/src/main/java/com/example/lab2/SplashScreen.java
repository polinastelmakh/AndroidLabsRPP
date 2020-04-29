package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {

    final private String LINK = "https://api.vk.com/method/friends.get?user_id=23397835&fields=first_name&count=50&v=5.8&access_token=9a763b3bbb20066cd777f1853a477ca7d1227c1d2846996d84b9517a9d7237378ece3dc1c482240afc218";
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!flag) {
            URL url = null;
            try {
                url = new URL(LINK);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            new VKQueryTaskList(this).execute(url);
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
