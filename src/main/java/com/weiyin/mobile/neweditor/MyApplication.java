package com.weiyin.mobile.neweditor;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.x;

import java.util.UUID;


/**
 * Created by jacyayj on 2016/1/8 0008.
 */
public class MyApplication extends Application{

    public static  String version = null;
    public static  String androidId = null;
    public static  String uuid = null;

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

        version = getVersion();

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice = ""+tm.getDeviceId();
        String tmSerial = ""+tm.getSimSerialNumber();

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        uuid = deviceUuid.toString();

        Log.v("jacy","androidid:"+androidId);
        Log.v("jacy","uuid:"+uuid);
        Log.v("jacy","version:"+version);
        PlatformConfig.setWeixin("wxfca8e7c9871267f0", "55fc4a2937862be32ea2cd64c8488678");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("2387276003", "55fc4a2937862be32ea2cd64c8488678");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105030399", "hDzKBUN83jNWDRER");        // QQ和Qzone appid appke
//        com.umeng.socialize.utils.Log.LOG = true;
    }

    //获取当前版本号
    private String getVersion(){
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
