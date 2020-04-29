package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VKListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true); //Тк знаем размер списка
        adapter = new VKListAdapter(50, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("position",String.valueOf(VKPagerAdapter.pos));
        recyclerView.scrollToPosition(VKPagerAdapter.pos);
    }
}
