package com.weiyin.mobile.neweditor.Utils;

import java.security.MessageDigest;
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

    /**
     * MD5加密
     */
    public static String MD5(String s){

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
