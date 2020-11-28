package com.deadely.itgenglish.ui.lessons;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.deadely.itgenglish.ui.mapsfragments.MapCityFragment;
import com.deadely.itgenglish.ui.mapsfragments.MapCurortFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MapCityFragment();
            default:
                return new MapCurortFragment();
        }
    }

}
