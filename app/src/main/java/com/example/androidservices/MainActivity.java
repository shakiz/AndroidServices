package com.example.androidservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidservices.services.MyService;

public class MainActivity extends AppCompatActivity {
    private Button startServiceBtn, stopServiceBtn, bindServiceBtn, unBindServiceBtn, getRandomNumberBtn;
    private TextView randomNumberTxt;
    private Intent serviceIntent;
    private MyService myService;
    private ServiceConnection serviceConnection;
    private boolean isServiceBound;

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
        stopServiceBtn = findViewById(R.id.stopService);
        bindServiceBtn = findViewById(R.id.bindService);
        unBindServiceBtn = findViewById(R.id.unBindService);
        getRandomNumberBtn = findViewById(R.id.getRandomNumber);
        randomNumberTxt = findViewById(R.id.randomNumberTxt);
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

        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
            }
        });

        bindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });

        unBindServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unBindService();
            }
        });

        getRandomNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRandomNumber();
            }
        });
    }
    //endregion

    //region bind service
    private void bindService(){
        if (serviceConnection == null){
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    Log.i("Shakil","onServiceConnected::"+name.getClassName());
                    MyService.MyServiceBinder serviceBinder = ((MyService.MyServiceBinder) binder);
                    myService = serviceBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    Log.i("Shakil","onServiceDisconnected::"+name.getClassName());
                    isServiceBound = true;
                }
            };
        }
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }
    //endregion

    //region unbind service
    private void unBindService(){
        if (isServiceBound){
            isServiceBound = false;
            unbindService(serviceConnection);
        }
    }
    //endregion

    //region set random number text
    private void setRandomNumber(){
        if (isServiceBound){
            randomNumberTxt.setText(String.valueOf(myService.getRandomNumber()));
        }
        else{
            randomNumberTxt.setText(getString(R.string.service_not_bound));
        }
    }
    //endregion
}