package com.example.firebasepushnotification;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        message = findViewById(R.id.message);

        String data = getIntent().getStringExtra("message");
        String from = getIntent().getStringExtra("from_id");

        message.setText("From : " + from + " | Message : " + data);
    }
}
