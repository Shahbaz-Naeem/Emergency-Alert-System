package com.pucit.emergencyalertsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_ON.equals(intent.getAction()))
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
            },3000);
        }

        if(count >= 4)
        {
            count=0;
            Toast.makeText(context, "MAIN ACTIVITY IS BEING CALLED ", Toast.LENGTH_LONG).show();
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }

    }
}
