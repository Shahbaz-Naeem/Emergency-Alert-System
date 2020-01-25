package com.example.firebasepushnotification.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.firebasepushnotification.Utils.SendAlert;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static int count;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_ON.equals(intent.getAction()) || Intent.ACTION_SCREEN_OFF.equals(intent.getAction()))
        {
            count++;
            Toast.makeText(context,"Yes " + count,Toast.LENGTH_SHORT).show();
        }

        if(count == 2 || count == 3)
        {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(count < 5)
                        count = 0;
                }
            },2000);
        }

        if(count > 5)
        {
            count=0;
            SendAlert sendAlert = new SendAlert(context);
        }
    }
}
