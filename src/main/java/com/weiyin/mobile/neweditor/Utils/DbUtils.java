package com.weiyin.mobile.neweditor.Utils;


import android.util.Log;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by jacyayj on 2016/1/29.
 */
public class DbUtils {

    public static DbManager openOrCreatDb(){
        DbManager.DaoConfig daoConfig =  new DbManager.DaoConfig()
                        .setDbName("user")
                        .setDbVersion(1)
                        .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                            @Override
                            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                                Log.v("jacy","update!!!");
                            }
                        });
        return x.getDb(daoConfig);
    }
}
