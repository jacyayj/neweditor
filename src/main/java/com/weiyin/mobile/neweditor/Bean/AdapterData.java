package com.weiyin.mobile.neweditor.Bean;

/**
 * adapter通用数据类、adapter需要用到的一些通用数据
 * Created by jacyayj on 2016/1/7 0007.
 */
public class AdapterData {

    //图标
    private int icon;

    //标题
    private String lable;

    //时间
    private String time;

    //阅读量
    private String pviews;

    //内容
    private String content;

    //审核
    private String check;

    //来源
    private String rootin;

    public String getRootin() {
        return rootin;
    }

    public void setRootin(String rootin) {
        this.rootin = rootin;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPviews() {
        return pviews;
    }

    public void setPviews(String pviews) {
        this.pviews = pviews;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
