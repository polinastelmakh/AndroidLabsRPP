package com.example.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;

public class VKQueryTaskList extends AsyncTask<URL, Void, String> {

    private Activity context;
    public VKQueryTaskList(Activity context) {
        this.context = context;
    }

    protected String doInBackground(URL... urls) {
        Singleton singleton = Singleton.getInstance();
        try {
            String responseToQuery = VKConnect.query(urls[0]);
            JSONObject user;
            JSONObject jsonResponseToQuery = new JSONObject(responseToQuery);
            JSONObject response = jsonResponseToQuery.getJSONObject("response");
            JSONArray items = response.getJSONArray("items");

            for (int i = 0; i < 100; i++) {
                user = items.getJSONObject(i);
                singleton.id[i] = user.getString("id");
                singleton.name[i] = user.getString("first_name") + " " + user.getString("last_name");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }

    protected void onPostExecute(String response) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }
}
