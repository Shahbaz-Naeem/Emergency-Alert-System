package com.example.firebasepushnotification.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasepushnotification.R;

public class NotificationActivity extends AppCompatActivity {

    TextView message;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        message = findViewById(R.id.message);

        String data = getIntent().getStringExtra("message");
        String from = getIntent().getStringExtra("from_id");
        String longitude = getIntent().getStringExtra("from_longitude");
        String latitude = getIntent().getStringExtra("from_latitude");


        //String.format("geo:0,0?q=%s,%s(Help)",location.getLatitude(),location.getLongitude());

        if(!TextUtils.isEmpty(data) && !"0.0".equals(latitude) && !"0.0".equals(longitude))
        {
            String location = String.format("geo:0,0?q=%s,%s(%s)",latitude,longitude,data);

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
            message.setText("Hi it's me " + from + ", \n\nThat's my current location please help me out:\n" + "Latitude : " +latitude + "\nLongitude :" + longitude) ;
        }
        else
        {
                message.setText("Hi "+ from + "\n" + data);
        }


    }
}
