package com.example.firebasepushnotification.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.firebasepushnotification.Adapters.PagerViewAdapter;
import com.example.firebasepushnotification.R;
import com.example.firebasepushnotification.Services.NotificationDetectionService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel;
    private TextView mUsersLabel;
    private TextView mNotificationsLabel;

    private ViewPager mMainPager;
    private PagerViewAdapter mPagerViewAdapter;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null)
            sendToLogin();
        else {
            startService(new Intent(getApplicationContext(), NotificationDetectionService.class));
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mProfileLabel = findViewById(R.id.profileLabel);
        mNotificationsLabel = findViewById(R.id.notificationLabel);
        mUsersLabel = findViewById(R.id.userLabel);

        mMainPager = findViewById(R.id.mainPager);
        mMainPager.setOffscreenPageLimit(2);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void sendToLogin() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int position) {
        if(position == 0)
        {
            mProfileLabel.setTextColor(getColor(R.color.textTabBright));
            mProfileLabel.setTextSize(20);

            mNotificationsLabel.setTextColor(getColor(R.color.textTabLight));
            mNotificationsLabel.setTextSize(16);

            mUsersLabel.setTextColor(getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(16);
        }
        else if( position == 2)
        {
            mProfileLabel.setTextColor(getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(16);

            mNotificationsLabel.setTextColor(getColor(R.color.textTabBright));
            mNotificationsLabel.setTextSize(20);

            mUsersLabel.setTextColor(getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(16);
        }
        else if (position == 1) {
            mProfileLabel.setTextColor(getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(16);

            mNotificationsLabel.setTextColor(getColor(R.color.textTabLight));
            mNotificationsLabel.setTextSize(16);

            mUsersLabel.setTextColor(getColor(R.color.textTabBright));
            mUsersLabel.setTextSize(20);
        }
    }
}
