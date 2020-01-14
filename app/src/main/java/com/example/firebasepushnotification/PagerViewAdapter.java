package com.example.firebasepushnotification;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {


    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                UserFragment userFragment = new UserFragment();
                return userFragment;
            case 2:
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
