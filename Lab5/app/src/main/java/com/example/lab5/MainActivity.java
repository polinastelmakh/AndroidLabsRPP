package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = findViewById(R.id.spinner);
        spinner.setDropDownVerticalOffset(150);
        VoidFunc func = new VoidFunc() {
            @Override
            public void start() {
                SpeciesInfo repository = SpeciesInfo.getInstance();
                List<String> breeds = repository.getBreedNames();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, breeds);
                spinner.setAdapter(arrayAdapter);
            }
        };
        BreedLoader loader = new BreedLoader(func);
        loader.execute();
        Button button = findViewById(R.id.btn);
        UrlInfo.createInstance("");
        LikeInfo.createInstance();
        final RecyclerView view = findViewById(R.id.recycler_view);
        final RecyclerAdapter adapter = new RecyclerAdapter(getApplicationContext());
        final VoidFunc notifyData = new VoidFunc() {
            @Override
            public void start() {
                adapter.notifyDataSetChanged();
            }
        };
        view.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                String breedId = "";
                if(spinner.getSelectedItem() != null) {
                    breedId = SpeciesInfo.getInstance().getBreedId(spinner.getSelectedItem().toString());
                }
                UrlInfo urlInfo = UrlInfo.createInstance(breedId);
                urlInfo.setNotify(notifyData);
                urlInfo.load();
            }
        });
        Button liked = findViewById(R.id.liked);
        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LikeListActivity.class);
                startActivity(intent);
            }
        });
    }

    public class BreedLoader extends AsyncTask<Void, Void, Void> {
        VoidFunc func;

        public BreedLoader(VoidFunc func) {
            super();
            this.func = func;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            func.start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("x-api-key", "8b399562-7d23-44ba-a61a-316c1fa086fe")
                    .url("https://api.thecatapi.com/v1/breeds")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String body = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                List<Species> species = mapper.readValue(body, new TypeReference<List<Species>>() {
                });
                SpeciesInfo.createInstance(species);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
