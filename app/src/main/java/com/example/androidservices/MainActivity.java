package com.example.androidservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidservices.services.MyService;

public class MainActivity extends AppCompatActivity {
    private Button startServiceBtn, stopServicebtn;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region init UI and perform UI interactions
        initUI();
        bindUiWithComponents();
        //endregion
    }

    //region init UI
    private void initUI() {
        startServiceBtn = findViewById(R.id.startService);
        stopServicebtn = findViewById(R.id.stopService);
        serviceIntent = new Intent(MainActivity.this, MyService.class);
    }
    //endregion

    //region perform UI interactions
    private void bindUiWithComponents() {
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(serviceIntent);
            }
        });

        stopServicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });
    }
    //endregion
}