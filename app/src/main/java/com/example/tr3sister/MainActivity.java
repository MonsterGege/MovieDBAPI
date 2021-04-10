package com.example.tr3sister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_top_rated:

                        Toast.makeText(MainActivity.this, "Top Rated", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_popular:
                        Toast.makeText(MainActivity.this, "Popular", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_upcoming:
                        Toast.makeText(MainActivity.this, "Upcoming", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        new GetJSON().execute();
    }
    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray results = jsonObj.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String title = "Title : "+c.getString("title");
                        String overview = c.getString("overview");
                        String release_date = "Release Date : "+c.getString("release_date");
                        float vote = Float.parseFloat(c.getString("vote_average"));
                        String vote_average = "Vote Average : "+String.valueOf(vote);
                        String image = c.getString("backdrop_path");

                        HashMap<String, String> result = new HashMap<>();

                        result.put("title", title);
                        result.put("overview", overview);
                        result.put("release_date", release_date);
                        result.put("vote_average", vote_average);
                        result.put("backdrop_path", image);
                        filmlist.add(result);

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, filmlist, R.layout.list_item,
                    new String[]{"vote_average","title", "overview", "release_date","backdrop_path"}, new int[]{R.id.vote_average,R.id.title,R.id.overview, R.id.release_date,R.id.image});

            lv.setAdapter(adapter);
        }

    }
}