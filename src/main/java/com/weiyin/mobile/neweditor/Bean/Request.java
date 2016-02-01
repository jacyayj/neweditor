package com.weiyin.mobile.neweditor.Bean;

import android.util.Log;

import com.weiyin.mobile.neweditor.MyApplication;
import com.weiyin.mobile.neweditor.Utils.DataUtils;
import com.weiyin.mobile.neweditor.Utils.StringUtils;

/**
 * Created by jacyayj on 2016/1/28.
 */
public class Request {


    /**
     * Constructs a new instance of {@code Object}.
     */
    public Request(String requestId) {

        setRequestId(MyApplication.uuid);
        setChannel("WY001");
        setVersion(MyApplication.version);
        setTimeStamp(DataUtils.timeStamp());
        Log.v("jacy",timeStamp+":"+timeStamp.length());
        setTerminalId(MyApplication.androidId);
    }

    /**
     *  requestId : 标识一次请求和反馈
     *  version : 客户端版本
     *  channel : 渠道编码
     *  terminalId : 终端标识
     *  timeStamp : 时间戳
     *  hashCode : 请求信息HASH码
     */

    private String requestId = null;
    private String version = null;
    private String channel = null;
    private String terminalId = null;
    private String timeStamp = null;
    private String hashCode = null;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(Object... values) {
        String temp = "";
        for (Object ob : values){
            temp+= String.valueOf(ob);
        }
        Log.v("jacy","temp:" + temp);
        hashCode = StringUtils.MD5(requestId + temp + timeStamp + "123456");
        Log.v("jacy","hashcode:" + hashCode+":"+hashCode.length());
    }
    public void setHashCode(String userkey,Object... values) {
        String temp = "";
        for (Object ob : values){
            temp+= String.valueOf(ob);
        }
        Log.v("jacy","temp:" + temp);
        hashCode = StringUtils.MD5(requestId + temp + timeStamp + userkey);
        Log.v("jacy","hashcode:" + hashCode+":"+hashCode.length() + "/n userkey : " + userkey);
    }
}
