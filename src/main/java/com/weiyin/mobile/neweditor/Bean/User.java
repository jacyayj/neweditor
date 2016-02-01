package com.weiyin.mobile.neweditor.Bean;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacyayj on 2016/1/28.
 */
@Table(name = "User")
public class User{
    /**
     * userId :用户标识
     * userNo :用户账号
     * userKey :用户密匙
     * userName :用户姓名
     * avatar :头像地址
     */
    @Column(name = "id",isId = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(name = "userId")
    private String userId;
    @Column(name = "userNo")
    private String userNo;
    @Column(name = "userKey")
    private String userKey;
    @Column(name = "userName")
    private String userName;
    @Column(name = "avatar")
    private String avatar;

    public static User objectFromData(String str) {

        return new Gson().fromJson(str, User.class);
    }

    public static User objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<User> arrayUserFromData(String str) {

        Type listType = new TypeToken<ArrayList<User>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<User> arrayUserFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }
}
