package com.devdiv.vca.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.devdiv.vca.Fragment.LoginFragment;
import com.devdiv.vca.Fragment.SignUPFragment;

public class TabVPAdapter extends FragmentPagerAdapter {

    int tabcount;

    public TabVPAdapter(@NonNull FragmentManager fm, int tabcount) {
        super(fm);

        this.tabcount = tabcount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignUPFragment signUPFragment = new SignUPFragment();
                return signUPFragment;

            default:
                loginFragment = new LoginFragment();
                return loginFragment;

        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
