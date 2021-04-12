package com.example.tr3sister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
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

public class MainActivity extends Activity {

    List<MovieModel> filmlist;
    RecyclerView recycler;
    SearchView sv;
    ImageSlider IS;

    private String TAG = MainActivity.class.getSimpleName();
    private static String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=402eb03154f33ed947a8852a65b92f16&language=en-US"; //upcoming
    private static String url2 = "https://api.themoviedb.org/3/movie/top_rated?api_key=dd16bfdacacfdc28592b1efb50d4db1e&language=en-US"; //top_rated
    private static String url3 = "https://api.themoviedb.org/3/movie/popular?api_key=dd16bfdacacfdc28592b1efb50d4db1e&language=en-US"; //popular

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IS = findViewById(R.id.slider);

        List<SlideModel> SM = new ArrayList<>();

        SM.add(new SlideModel("https://image.tmdb.org/t/p/w500/xMIyotorUv2Yz7zpQz2QYc8wkWB.jpg","The Green Mile"));
        SM.add(new SlideModel("https://image.tmdb.org/t/p/w500/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg","Godzilla vs. Kong"));
        SM.add(new SlideModel("https://image.tmdb.org/t/p/w500/z8TvnEVRenMSTemxYZwLGqFofgF.jpg","Monster Hunter"));
        SM.add(new SlideModel("https://image.tmdb.org/t/p/w500/rSPw7tgCH9c6NqICZef4kZjFOQ5.jpg","The Godfather"));
        IS.setImageList(SM,true);

        sv = findViewById(R.id.search_menu);
        sv.setQueryHint(getResources().getString(R.string.search_hint));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String urlsearch = "https://api.themoviedb.org/3/search/movie?api_key=402eb03154f33ed947a8852a65b92f16&language=en-US&query="+query+"&include_adult=false";
                filmlist = new ArrayList<>();
                recycler = findViewById(R.id.recycler);
                new GetJSON().execute(urlsearch);
                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });




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
                model.setOverview(newjsonObject.getString("overview"));
                model.setRelease_date(newjsonObject.getString("release_date"));
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


}