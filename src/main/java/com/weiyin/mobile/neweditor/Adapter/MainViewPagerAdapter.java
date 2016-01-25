package com.weiyin.mobile.neweditor.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jacyayj on 2015/12/22 0022.
 *
 * 首页广告轮播适配器
 */
public class MainViewPagerAdapter extends PagerAdapter{

    private View[] views = null;

    public MainViewPagerAdapter(View[] views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views[position]);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
