package com.example.lab2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import java.net.URL;

public class VKFragment extends Fragment {

    private Singleton singleton = Singleton.getInstance();
    private int position;
    public View view;

    public static VKFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("pos", position);
        VKFragment f = new VKFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        this.position = getArguments().getInt("pos");
        view = inflater.inflate(R.layout.fragment_vk, parent, false);
        URL url = null;
        try {
            url = new URL("https://api.vk.com/method/users.get?user_ids=" + (singleton.id[position]) + "&fields=photo_max_orig&v=5.8&access_token=9a763b3bbb20066cd777f1853a477ca7d1227c1d2846996d84b9517a9d7237378ece3dc1c482240afc218");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        new VKQueryTaskInfoUser(view).execute(url);
        return view;
    }
}

