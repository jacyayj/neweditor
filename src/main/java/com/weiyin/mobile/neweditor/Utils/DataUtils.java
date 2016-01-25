package com.weiyin.mobile.neweditor.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jacyayj on 2016/1/18 0018.
 *  数据处理工具类
 */
public class DataUtils {

    /**
     * 读取文件
     * @param context   上下文对象
     * @param key   获取资源的键值
     * @return  获取到的资源
     */
    public static String readString(Context context,String key){

        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return preferences.getString(key,null);
    }
    public static int readInt(Context context,String key){

        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        return preferences.getInt(key,-1);
    }
    public static boolean readBoolean(Context context,String key){

        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);

        return preferences.getBoolean(key,false);

    }

    /**
     * 写入文件
     * @param context   上下文对象
     * @param key   写入数据的键值
     * @param value 写入的数据
     */
    public static void writeString(Context context,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void writeInt(Context context,String key,int value){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static void writeBoolean(Context context,String key,boolean value){
        SharedPreferences preferences = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }


}
