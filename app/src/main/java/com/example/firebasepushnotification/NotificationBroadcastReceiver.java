package com.example.firebasepushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static int count;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_ON.equals(intent.getAction()) || Intent.ACTION_SCREEN_OFF.equals(intent.getAction()))
        {
            count++;
            Toast.makeText(context,"Yes " + count,Toast.LENGTH_SHORT).show();
        }

        if(count == 2)
        {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(count < 4)
                        count = 0;
                }
            },2000);
        }

        if(count > 5)
        {
            count=0;
            Intent i = new Intent(context, SendAlert.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //context.startActivity(i);

            SendAlert sendAlert = new SendAlert();
        }
    }
}
