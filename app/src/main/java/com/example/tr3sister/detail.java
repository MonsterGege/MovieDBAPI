package com.example.tr3sister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

    }
}