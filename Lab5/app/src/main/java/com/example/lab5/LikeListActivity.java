package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class LikeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        RecyclerView view = findViewById(R.id.liked_recycler);
        view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LikeRecyclerAdapter adapter = new LikeRecyclerAdapter(getApplicationContext());
        view.setAdapter(adapter);
    }
}
