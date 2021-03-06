package com.example.firebasepushnotification.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.firebasepushnotification.Receivers.NotificationBroadcastReceiver;

public class NotificationDetectionService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        BroadcastReceiver myReceiver = new NotificationBroadcastReceiver();
        registerReceiver(myReceiver, filter);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
