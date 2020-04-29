package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class VKPagerAdapter extends FragmentStatePagerAdapter {

    public static int pos = 0;
    public VKPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        pos = position;
        return VKFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 100;
    }
}
