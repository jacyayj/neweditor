package com.weiyin.mobile.neweditor.Bean;

/**
 * 公用静态类、存放项目中的静态变量
 * Created by jacyayj on 2016/1/8 0008.
 */
public class Static {

    public static final String ACTION_MAIN = "android.weiyin.neweditor.MAIN";
    public static final String ACTION_GUIDE = "android.weiyin.neweditor.GUIDE";
    public static final String ACTION_USER = "android.weiyin.neweditor.USER";
    public static final String ACTION_ARTCILE = "android.weiyin.neweditor.ARTCILE";

    public static final String MUSIC_BROADCAST_ACTION = "weiyin.music.action";
    public static final String MUSIC_BROADCAST_INT_KEY = "weiyin.music.int_key";
    public static final String MUSIC_BROADCAST_STRING_KEY = "weiyin.music.string_key";
    public static final String MUSIC_BROADCAST_FLAG = "weiyin.music.flag";
    public static final String MUSIC_ISPLAYING = "weiyin.music.isplaying";

    public static final String TITLE_LOGIN = "登录";
    public static final String TITLE_REGISTER = "注册";
    public static final String TITLE_FINDPWD = "找回密码";
    public static final String TITLE_PAY = "会员支付";
    public static final String TITLE_INSERTMUSIC = "插音乐";
    public static final String TITLE_EDITDATA = "编辑资料";

    public static final int TAG_EDITDATA = 0x998;
    public static final int TAG_LOGIN = 0x997;
    public static final int TAG_CHANGE = 0x996;

    public static boolean ISLOGIN = false;

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_ERROR_HASH = 1001;
    public static final int CODE_ERROR_PARAM = 1002;
    public static final int CODE_CLIENTNOTCFG = 1003;
    public static final int CODE_ERROR_USERORPWD = 2001;
    public static final int CODE_USERNOTUSE = 2002;
    public static final int CODE_ERROR_OTHER = 9999;


}
