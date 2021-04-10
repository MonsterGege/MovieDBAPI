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
    }
}