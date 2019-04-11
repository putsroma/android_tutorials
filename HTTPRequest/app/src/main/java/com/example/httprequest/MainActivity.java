package com.example.httprequest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView responseTextView;
    private ListView top100;
    private ArrayList<String> artistsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top100 = (ListView) findViewById(R.id.artistCharts);

        GetArtistCharts apiRequest = new GetArtistCharts();
        apiRequest.execute();
    }

    class GetArtistCharts extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... strings) {
            String url = "https://api.vagalume.com.br/rank.php?type=art&period=day&scope=all&limit=100";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = null;

            try {
                response = client.newCall(request).execute();
                Log.i("request response", response.toString());

                try {
                    JSONObject obj = new JSONObject(response.body().string());
                    Log.i("JSON object", obj.toString());
                    obj = obj.getJSONObject("art");
                    obj = obj.getJSONObject("day");
                    JSONArray artistsArray = obj.getJSONArray("all");
                    Log.i("array composing", artistsArray.toString());
                    return artistsArray;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject o = jsonArray.getJSONObject(i);
                    artistsList.add(o.getString("name"));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
            Log.i("passing to onCreate", artistsList.toString());
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, artistsList);
            Log.i("artist List after async task", artistsList.toString());
            top100.setAdapter(arrayAdapter);
        }
    }
}


