package com.weiyin.mobile.neweditor;

import android.app.Application;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * Created by jacyayj on 2016/1/8 0008.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wxfca8e7c9871267f0", "55fc4a2937862be32ea2cd64c8488678");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("2387276003", "55fc4a2937862be32ea2cd64c8488678");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105030399", "hDzKBUN83jNWDRER");        // QQ和Qzone appid appke
//        com.umeng.socialize.utils.Log.LOG = true;
    }
}
