package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class VKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("myLog", "VKActivity.oncreate");
        setContentView(R.layout.activity_vk);

        ViewPager viewPager = findViewById(R.id.viewPager);
        VKPagerAdapter vkAdapter = new VKPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Intent intent = getIntent();

        viewPager.setAdapter(vkAdapter);
        viewPager.setCurrentItem(intent.getIntExtra("position", 0));
    }
}
