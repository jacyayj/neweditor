package com.weiyin.mobile.neweditor.Controller;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jacyayj on 2016/1/7 0007.
 * 封装一些activity中常用的功能
 */
public class ActivityController{

    private static ActivityController instance = null;

    private ActivityController() {
        super();
    }

    public static ActivityController getInstance() {
        if (instance == null){
            instance = new ActivityController();
        }
        return instance;
    }

    public void toast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public void toast(Context context, String msg, int ms) {
        Toast.makeText(context,msg,ms).show();
    }

    public void finish(Activity activity) {
        activity.finish();
    }

    public void start(Activity activity, String action) {
        Intent i = new Intent(action);
        activity.startActivity(i);
    }
    public void start(Activity activity, String action, Map<String,String> values) {
        Intent i = new Intent(action);
        Set<String> keys = values.keySet();
        for (String key : keys){
            i.putExtra(key,values.get(key));
        }
        activity.startActivity(i);
    }

    public void start(Activity activity,String action,String key,int tag){
        Intent i = new Intent(action);
        i.putExtra(key,tag);
        activity.startActivity(i);
    }

    public void senBroacast(Context context,String action,String key,String msg){

        Intent i = new Intent(action);
        i.putExtra(key,msg);
        context.sendBroadcast(i);
    }
    public void senBroacast(Context context,String action,String key,int msg){

        Intent i = new Intent(action);
        i.putExtra(key,msg);
        context.sendBroadcast(i);
    }
    public void senBroacast(Context context,String action,String key,boolean msg){

            Intent i = new Intent(action);
            i.putExtra(key,msg);
            context.sendBroadcast(i);
        }
}
