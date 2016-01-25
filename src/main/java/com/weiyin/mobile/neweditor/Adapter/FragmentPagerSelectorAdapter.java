package com.weiyin.mobile.neweditor.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jacyayj on 2016/1/6 0006.
 *
 * 实现fragment滑动切换的适配器
 */
public class FragmentPagerSelectorAdapter extends FragmentPagerAdapter{

    private List<Fragment> fs;

    public FragmentPagerSelectorAdapter(FragmentManager fm,List<Fragment> fs) {
        super(fm);
        this.fs = fs;
    }

    @Override
    public int getCount() {
        return fs.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fs.get(position);
    }
}
