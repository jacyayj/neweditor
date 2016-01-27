package com.weiyin.mobile.neweditor.View.Activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.DataUtils;

/**
 * Created by jacyayj on 2016/1/8 0008.
 * 启动页、加载本地数据，更新版本
 */
public class LaunchActivity extends Activity{

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isFirst = DataUtils.readBoolean(this,"isFirst");
        Configuration config = getResources().getConfiguration();
        int smallestScreenWidth = config.smallestScreenWidthDp;
        ActivityUtils.toast(this,"smallestScreenWidth:"+smallestScreenWidth+",smallest width : "+ smallestScreenWidth);
        update();
    }
    /**
     * 友盟自动更新
     */
    private void update(){
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int i, final UpdateResponse updateResponse) {
                switch (i){
                    case UpdateStatus.Yes : UmengUpdateAgent.showUpdateDialog(getApplicationContext(),updateResponse);break;
                    case UpdateStatus.No : go();break;
                    case UpdateStatus.NoneWifi : go();break;
                    case UpdateStatus.Timeout : ActivityUtils.toast(getApplicationContext(),"检测超时");go();break;
                    default:break;
                }
            }
        });

        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
            @Override
            public void onClick(int i) {
                go();
            }
        });
        UmengUpdateAgent.update(this);
    }

    /**
     *  判断是否第一次启动
     */
    private void go(){
        if (isFirst) {
            DataUtils.writeBoolean(this,"isFirst", false);
            ActivityUtils.start(LaunchActivity.this, Static.ACTION_GUIDE);
            finish();
        } else {
            ActivityUtils.start(LaunchActivity.this, Static.ACTION_MAIN);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("jacy","LaunchDestroy");
    }

}
