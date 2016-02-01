package com.weiyin.mobile.neweditor.View.Activity;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.weiyin.mobile.neweditor.Adapter.FragmentPagerSelectorAdapter;
import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Bean.User;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.DataUtils;
import com.weiyin.mobile.neweditor.Utils.DbUtils;
import com.weiyin.mobile.neweditor.View.CostomView.MainmenuLayout;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentAdCenter;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentMain;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentMyArtcile;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 程序主界面容器activity
 * 使用了第三方框架xutils
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,NavigationView.OnNavigationItemSelectedListener{

    //自定义适配器、用于fragment滑动切换
    private FragmentPagerSelectorAdapter mSectionsPagerAdapter = null;

    //使用viewpagger实现滑动切换效果
    private ViewPager mViewPager = null;

    //fragment队列
    private List<Fragment> fs = null;

    //弹出悬浮框
    private PopupWindow popupWindow = null;


    //菜单
    @ViewInject(R.id.menu1)
    private MainmenuLayout menu1 = null;
    @ViewInject(R.id.menu2)
    private MainmenuLayout menu2 = null;
    @ViewInject(R.id.menu3)
    private MainmenuLayout menu3 = null;


    //悬浮框开关
    @ViewInject(R.id.menu_btn)
    private View menubtn = null;

    @ViewInject(R.id.main_drawer)
    private DrawerLayout drawer = null;

    //用户图标
    @ViewInject(R.id.user_icon)
    private ImageView usericon = null;

    //用户姓名
    @ViewInject(R.id.user_name)
    private TextView username = null;

    //抽屉用户姓名
    private TextView drawer_username = null;

    //抽屉用户头像
    private ImageView drawer_userhead = null;


    @ViewInject(R.id.controller_btn)
    private TextView controller_btn = null;
    @ViewInject(R.id.app_title)
    private TextView title = null;

    //当前页面索引
    private int index = 0;
    //退出状态
    private boolean isExit = false;

    @ViewInject(R.id.nav_view)
    private NavigationView nav_view = null;

    private boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注入xutils
        x.view().inject(this);

        //默认首页选中状态
        menu1.setChecked(true);

        //初始化fragment队列
        fs = new ArrayList<Fragment>();
        fs.add(new FragmentMain());
        fs.add(new FragmentMyArtcile());
        fs.add(new FragmentAdCenter());

        //为viewpagger设置适配器实现滑动效果
        mSectionsPagerAdapter = new FragmentPagerSelectorAdapter(getSupportFragmentManager(),fs);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        nav_view.setNavigationItemSelectedListener(this);

        drawer_username = (TextView) nav_view.getHeaderView(0).findViewById(R.id.drawer_name);
        drawer_userhead = (ImageView) nav_view.getHeaderView(0).findViewById(R.id.drawer_head);

    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            islogin = DataUtils.readBoolean(this,"islogin");
            initView();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initView() throws DbException {

        if (islogin){
            DbManager manager = DbUtils.openOrCreatDb();

            User user = manager.selector(User.class).findFirst();

//            user.setUserName("二狗");
//
//            manager.dropTable(User.class);
//            manager.save(user);

            if (user != null){
                username.setText(user.getUserName());
                drawer_username.setText(user.getUserName());
                if (user.getAvatar() != null){
                    x.image().bind(usericon,user.getAvatar());
                    x.image().bind(drawer_userhead,user.getAvatar());
                }else {
                    usericon.setImageResource(R.drawable.default_head);
                    drawer_userhead.setImageResource(R.drawable.default_head);
                }
            }else {
                username.setText("游客");
                usericon.setImageResource(R.drawable.default_head);
                drawer_userhead.setImageResource(R.drawable.default_head);
            }
        }else {
            username.setText("游客");
            usericon.setImageResource(R.drawable.default_head);
            drawer_userhead.setImageResource(R.drawable.default_head);
        }

    }

    /**
     * 使用xutils处理当前页面中的点击事件
     * @param v 触发点击事件的控件
     */
    @Event(value = {R.id.menu1,R.id.menu2,R.id.menu3,R.id.menu_btn,R.id.controller_btn})
    private void onClick(View v){
            switch (v.getId()){
                case R.id.menu1 : mViewPager.setCurrentItem(0);
                    break;
                case R.id.menu2 :
                    if (islogin){
                        mViewPager.setCurrentItem(1);
                    }else {
                        ActivityUtils.start(this,Static.ACTION_USER,"start",Static.TAG_LOGIN);
                    }
                    break;
                case R.id.menu3 :
                    if (islogin){
                        mViewPager.setCurrentItem(2);
                    }else {
                        ActivityUtils.start(this,Static.ACTION_USER,"start",Static.TAG_LOGIN);
                    }
                    break;
                case R.id.menu_btn :
                    if (islogin){
                        drawer.openDrawer(GravityCompat.START);
                    }else {
                        ActivityUtils.start(this,Static.ACTION_USER,"start",Static.TAG_LOGIN);
                    }
                    break;
                case R.id.controller_btn :
                    switch (index){
                        case 1 : ActivityUtils.toast(this,"写一篇");break;
                        case 2 : ActivityUtils.toast(this,"广告中心");break;
                        default:break;
                    }
                    break;
                default:break;
            }
//        }

    }

    /**
     *页面滑动是菜单的变化处理
     * @param index 当前页面index索引
     */
    private void menuSelected(int index){
              switch (index){
                  case 0 :
                      menu1.setChecked(true);
                      menu2.setChecked(false);
                      menu3.setChecked(false);
                      controller_btn.setVisibility(View.GONE);
                      title.setText(R.string.index);
                      break;
                  case 1 :
                      menu1.setChecked(false);
                      menu2.setChecked(true);
                      menu3.setChecked(false);
                      controller_btn.setVisibility(View.VISIBLE);
                      controller_btn.setText(R.string.edit_artcial);
                      title.setText(R.string.myartcial);
                      break;
                  case 2 :
                      menu1.setChecked(false);
                      menu2.setChecked(false);
                      menu3.setChecked(true);
                      controller_btn.setVisibility(View.VISIBLE);
                      controller_btn.setText(R.string.new_ad);
                      title.setText(R.string.adcenter);
                      break;
              }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.v("jacy","postion"+position);
        index = position;
        menuSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        if(isExit){
            finish();
        }else {
            isExit = true;
            ActivityUtils.toast(this,"再按一次退出程序",3000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            },3000);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.drawer_menu1 : ActivityUtils.start(this, Static.ACTION_USER,"start",Static.TAG_EDITDATA); break;
            case R.id.drawer_menu2 : ActivityUtils.start(this, Static.ACTION_USER,"start",Static.TAG_CHANGE); break;
            default:break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("jacy","MainDestroy");
    }

}
