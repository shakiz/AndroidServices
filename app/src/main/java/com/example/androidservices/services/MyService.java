package com.example.androidservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.Random;

public class MyService extends Service {
    private int randomNumber;
    private boolean enableRandomGenerator = false;

    public class MyServiceBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    //This ensures that we can receive the on bind request from an external resources or agent
    private IBinder myIBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Shakil","onBind");
        return myIBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("Shakil","onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("Shakil","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Shakil","onStartCommand");
        Log.i("Shakil","Thread ID:"+Thread.currentThread().getId());
        enableRandomGenerator = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.i("Shakil","onDestroy Called");
    }

    private void startRandomNumberGenerator() {
        while (enableRandomGenerator){
            try{
                randomNumber = new Random().nextInt(100)+11;
                Log.i("Shakil","Random Number : "+ randomNumber + " Thread ID:" + Thread.currentThread().getId());
            }
            catch (Exception e){
                Log.i("Shakil","startRandomNumberGenerator exception: "+e.getMessage());
            }
        }
    }

    private void stopRandomNumberGenerator(){
        enableRandomGenerator = false;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
