package com.weiyin.mobile.neweditor.View.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Controller.ActivityController;
import com.weiyin.mobile.neweditor.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacyayj on 2016/1/8 0008.
 *
 * 引导页、只在用户第一次启动程序进入
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{

    @ViewInject(R.id.guide_vip)
    private ViewPager pager = null;

    @ViewInject(R.id.guide_dot1)
    private ImageView dot1 = null;
    @ViewInject(R.id.guide_dot2)
    private ImageView dot2 = null;
    @ViewInject(R.id.guide_dot3)
    private ImageView dot3 = null;
    @ViewInject(R.id.guide_dot4)
    private ImageView dot4 = null;

    private List<ImageView> dots = null;

    private TextView[] tvs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        x.view().inject(this);
        pager.setOnPageChangeListener(this);

        tvs = new TextView[4];
        dots = new ArrayList<ImageView>();

        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);

        for (int i = 0; i < tvs.length ; i++){
            tvs[i] = new TextView(this);
            tvs[i].setGravity(Gravity.CENTER);
            tvs[i].setTextSize(25);
            tvs[i].setText("引导页"+(i+1));
        }

        pager.setAdapter(new myPaggerAdapter());

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        ActivityController.getInstance().toast(this,"引导页"+position+1);
        for (int i = 0 ; i< dots.size() ; i++){
            if (i == position)
                dots.get(i).setImageResource(R.drawable.dot_a);
            else
                dots.get(i).setImageResource(R.drawable.dot_b);

        }
        if (position == 3){
            ActivityController.getInstance().start(this, Static.ACTION_MAIN);
            finish();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class myPaggerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return tvs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(tvs[position]);
            return tvs[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(tvs[position]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("jacy","GuideDestroy");
    }

}
