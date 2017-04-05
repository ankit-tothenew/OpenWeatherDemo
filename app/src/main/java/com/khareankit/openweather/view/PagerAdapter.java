package com.khareankit.openweather.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.khareankit.openweather.models.futdays.TempInfoList;

import java.util.List;

/**
 * Created by Ankit Bansal on 04-Mar-17.
 */
public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm, List<TempInfoList> list) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
