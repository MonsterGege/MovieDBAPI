package com.example.tr3sister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detail extends AppCompatActivity {

    ImageView dposter;
    TextView dtitle, drelease, doverview, drate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dtitle = findViewById(R.id.dtitle);
        drate = findViewById(R.id.drate);
        drelease = findViewById(R.id.drelease);
        doverview = findViewById(R.id.doverview);
        dposter = findViewById(R.id.dposter);

        String title = getIntent().getStringExtra("title");
        String rate = getIntent().getStringExtra("rating");
        String release = getIntent().getStringExtra("release_date");
        String overview = getIntent().getStringExtra("overview");
        String poster = getIntent().getStringExtra("img");

        dtitle.setText(title);
        drate.setText(rate);
        drelease.setText(release);
        doverview.setText(overview);
        Glide.with(this)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w500"+poster)
                .into(dposter);
    }
}