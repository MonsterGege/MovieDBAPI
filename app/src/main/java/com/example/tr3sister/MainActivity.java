package com.example.tr3sister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    List<MovieModel> filmlist;
    RecyclerView recycler;
    private String TAG = MainActivity.class.getSimpleName();
    private static String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=402eb03154f33ed947a8852a65b92f16&language=en-US"; //upcoming
    private static String url2 = "https://api.themoviedb.org/3/movie/top_rated?api_key=dd16bfdacacfdc28592b1efb50d4db1e&language=en-US"; //top_rated
    private static String url3 = "https://api.themoviedb.org/3/movie/popular?api_key=dd16bfdacacfdc28592b1efb50d4db1e&language=en-US"; //popular

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
                        filmlist = new ArrayList<>();
                        recycler = findViewById(R.id.recycler);
                        new GetJSON().execute(url2);
                        break;
                    case R.id.page_popular:
                        filmlist = new ArrayList<>();
                        recycler = findViewById(R.id.recycler);
                        new GetJSON().execute(url3);
                        break;
                    case R.id.page_upcoming:
                        filmlist = new ArrayList<>();
                        recycler = findViewById(R.id.recycler);
                        new GetJSON().execute(url);
                        break;
                }

                return true;
            }
        });
        bottomNav.setSelectedItemId(R.id.page_popular);
        filmlist = new ArrayList<>();
        recycler = findViewById(R.id.recycler);
        new GetJSON().execute(url3);
    }
    private class GetJSON extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            String js = arg0[0];
            String result = "";

            try{
                URL url;
                HttpsURLConnection urlConnection = null;

                try{
                    url = new URL(js);
                    urlConnection = (HttpsURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while(data != -1){
                        result += (char) data;
                        data = isr.read();
                    }
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i =0;i<jsonArray.length();i++){
                JSONObject newjsonObject = jsonArray.getJSONObject(i);

                MovieModel model = new MovieModel();
                model.setRating(newjsonObject.getString("vote_average"));
                model.setTitle(newjsonObject.getString("title"));
                model.setImg(newjsonObject.getString("poster_path"));

                filmlist.add(model);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            putDataIntoRecyclerView(filmlist);
        }

    }

    private void putDataIntoRecyclerView(List<MovieModel>filmlist) {
        CustomAdapter adapter = new CustomAdapter(this,filmlist);
        RecyclerView.LayoutManager layout = new GridLayoutManager(this,3);
        recycler.setLayoutManager(layout);
        recycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (sm != null) {
            SearchView sv = (SearchView) (menu.findItem(R.id.search)).getActionView();
            sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
            sv.setQueryHint(getResources().getString(R.string.search_hint));
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //cari nya diapain masukin ke sini
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }

            });
        }
        return true;
    }
}