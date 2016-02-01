package com.weiyin.mobile.neweditor.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentEditdata;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentFindPwd;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentInsertMusic;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentLogin;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentPay;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentRegister;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * Created by jacyayj on 2016/1/9 0009.
 *
 * 用于界面、负责管理与用户信息相关的fragment
 *
 */
public class UserActivity extends FragmentActivity implements FragmentHelper{

    private FragmentManager manager = null;

    //用户fragment队列
    private Map<String,Fragment> fragments = null;

    //fragment之间传递的信息
    private Bundle bundle = null;

    //加载fragment时显示的进度条
    @ViewInject(R.id.user_bg_progressbar)
    private TextView progressBar = null;

    private int startTag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        x.view().inject(this);
        init();

        startTag = getIntent().getIntExtra("start",-1);

        switch (startTag){

            case Static.TAG_LOGIN :
                setBundle(new Bundle());
                show(Static.TITLE_LOGIN);
                break;
            case Static.TAG_EDITDATA :
                setBundle(new Bundle());
                show(Static.TITLE_EDITDATA);
                break;
            case Static.TAG_CHANGE :
                setBundle(new Bundle());
                show(Static.TITLE_FINDPWD);
                break;
            default:break;

        }
    }
    /**
     * 初始化管理器、fragment
     */
    private void init(){
        manager = getSupportFragmentManager();
        fragments = new HashMap<>();

        fragments.put(Static.TITLE_LOGIN,FragmentLogin.newInstacne(this));
        fragments.put(Static.TITLE_REGISTER,FragmentRegister.newInstacne(this));
        fragments.put(Static.TITLE_FINDPWD,FragmentFindPwd.newInstacne(this));
        fragments.put(Static.TITLE_PAY,FragmentPay.newInstacne(this));
        fragments.put(Static.TITLE_INSERTMUSIC,FragmentInsertMusic.newInstacne(this));
        fragments.put(Static.TITLE_EDITDATA, FragmentEditdata.newInstacne(this));
    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount()>1){
            manager.popBackStack();
        }else
            finish();
    }

    @Override
    public void show(String arg) {
        ActivityUtils.toast(this,"show");
        Fragment fragment = null;
        Set<String> keys = fragments.keySet();

        for (String key : keys){
            if (key.equals(arg)){
                fragment = fragments.get(key);
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_open,R.anim.fragment_close,R.anim.fragment_back_open,R.anim.fragment_back_close)
                        .replace(R.id.user_frame,fragment)
                        .addToBackStack(key)
                        .commit();
            }
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("jacy","UserDestroy");
    }
}
