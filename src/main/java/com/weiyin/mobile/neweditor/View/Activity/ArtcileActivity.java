package com.weiyin.mobile.neweditor.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.umeng.message.PushAgent;
import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.View.Fragment.FragmentArtcilePview;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jacyayj on 2016/1/13 0013.
 * 文章预览activity，负责操作与文章相关的fragment动作
 */
public class ArtcileActivity extends FragmentActivity implements FragmentHelper{

    private FragmentManager manager = null;

    private Bundle bundle = null;

    private Map<String,Fragment> fragments = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artcile);

        manager = getSupportFragmentManager();

        fragments = new HashMap<>();
        fragments.put("文章预览", FragmentArtcilePview.newInstance(this));

        String title = getIntent().getStringExtra("title");
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        setBundle(bundle);

        show("文章预览");
    }

    @Override
    public void show(String arg) {
        Fragment fragment = null;
        Set<String> keys = fragments.keySet();
        for (String key : keys){
            if (arg.equals(key)){
                fragment = fragments.get(key);
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.fragment_open,R.anim.fragment_close,R.anim.fragment_back_open,R.anim.fragment_back_close)
                        .replace(R.id.artcile_container,fragment)
                        .addToBackStack(key)
                        .commit();
            }
        }

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
    public void onBackPressed() {
        if (manager.getBackStackEntryCount()>1){
            manager.popBackStack();
        }else
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("jacy","ArtcileDestroy");
    }

}
