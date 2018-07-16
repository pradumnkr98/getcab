package com.example.ashish.justgetit;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentslist = new ArrayList<>();
    private final List<String> fragmenttitlelist = new ArrayList<>();

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addfragment(Fragment fragment, String title) {
        fragmentslist.add(fragment);
        fragmenttitlelist.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmenttitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentslist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentslist.size();
    }
}
