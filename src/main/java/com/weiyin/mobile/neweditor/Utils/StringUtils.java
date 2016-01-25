package com.weiyin.mobile.neweditor.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jacyayj on 2016/1/18 0018.
 * 字符串工具类
 */
public class StringUtils {

    /**
     *  判断手机号是否为手机号
     * @param phone 需要判断的字符串
     * @return  判断结果
     */
    public static boolean isPhone(String phone){
        if ("".equals(phone)){
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(phone);

        return m.matches();
    }

    /**
     * 判断字符串是否为空
     * @param str   需要判断的字符串
     * @return  判断结果
     */
    public static boolean isNULL(String str){
        if (str == null || "".equals(str)){
            return true;
        }
        return false;
    }

}
