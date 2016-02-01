package com.weiyin.mobile.neweditor.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Map;
import java.util.Set;

/**
 * Created by jacyayj on 2016/1/26.
 */
public class ActivityUtils {

    public static ProgressDialog dialog = null;

    public static void toast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, String msg, int ms) {
        Toast.makeText(context,msg,ms).show();
    }

    public static void finish(Activity activity) {
        activity.finish();
    }

    public static void start(Activity activity, String action) {
        Intent i = new Intent(action);
        activity.startActivity(i);
    }
    public static void start(Activity activity, String action, Map<String,String> values) {
        Intent i = new Intent(action);
        Set<String> keys = values.keySet();
        for (String key : keys){
            i.putExtra(key,values.get(key));
        }
        activity.startActivity(i);
    }

    public static void start(Activity activity,String action,String key,int tag){
        Intent i = new Intent(action);
        i.putExtra(key,tag);
        activity.startActivity(i);
    }

    public static void senBroacast(Context context,String action,String key,String msg){

        Intent i = new Intent(action);
        i.putExtra(key,msg);
        context.sendBroadcast(i);
    }
    public static void senBroacast(Context context,String action,String key,int msg){

        Intent i = new Intent(action);
        i.putExtra(key,msg);
        context.sendBroadcast(i);
    }
    public static void senBroacast(Context context,String action,String key,boolean msg){

        Intent i = new Intent(action);
        i.putExtra(key,msg);
        context.sendBroadcast(i);
    }

    public static void dialog(Context context){
        if (dialog == null){
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("请求中...");
            dialog.show();
        }else {
            dialog.dismiss();
            dialog = null;
        }
    }

}
