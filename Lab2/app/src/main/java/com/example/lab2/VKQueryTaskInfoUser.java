package com.example.lab2;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class VKQueryTaskInfoUser extends AsyncTask<URL, Void, String> {

    private View view;

    public VKQueryTaskInfoUser(View view) {
        this.view = view;
    }

    protected String doInBackground(URL... urls) {
        String response = null;
        try {
            response = VKConnect.query(urls[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(String response) {
        TextView textName = view.findViewById(R.id.textName);
        String firstName;
        String lastName;
        String resultName = "NoName";
        String photo = null;

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArray = jsonResponse.getJSONArray("response");
            JSONObject user = jsonArray.getJSONObject(0);

            firstName = user.getString("first_name");
            lastName = user.getString("last_name");
            photo = user.getString("photo_max_orig");

            resultName = firstName + "\n" + lastName;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        textName.setText(resultName);
        if (photo != null) {
            new DownloadImage((ImageView) view.findViewById(R.id.imageView)).execute(photo);
        }
    }
}
