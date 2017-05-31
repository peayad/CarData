package com.peayad.cardata;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static int pageNum = 1;

    String[] titles = {"hello", "hello there", "hello again"};
    String[] subtitles = {"welcome", "welcome here", "welcome again"};

    ArrayList<CarItem> carList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyAsyncTask().execute();
    }

    private void initData() throws IOException, JSONException {
        JSONObject myPage = getJSONObjectFromURL("http://demo1286023.mockable.io/api/v1/cars?page=" + pageNum);
        JSONArray carsData = myPage.getJSONArray("data");

        for (int i = 0; i < carsData.length(); i++) {
            Gson gson = new Gson();
            CarItem myCarItem = gson.fromJson(carsData.get(i).toString(), CarItem.class);
            carList.add(myCarItem);
        }

        Log.d("ptr", "initData: " + carList.size());
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    private void initListView() {
        CarListAdapter carListAdapter = new CarListAdapter(getApplicationContext(), carList);
        ListView programsLv = (ListView) findViewById(R.id.listView);
        programsLv.setAdapter(carListAdapter);
//        programsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, titles[position], Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private class MyAsyncTask extends AsyncTask {

        private Exception exception;

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                initData();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            initListView();
        }
    }
}
