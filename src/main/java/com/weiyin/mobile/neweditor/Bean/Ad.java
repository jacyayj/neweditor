package com.weiyin.mobile.neweditor.Bean;

/**
 * Created by jacyayj on 2016/1/28.
 */
public class Ad {

    /**
     * adId : 广告标识
     * adPic : 广告图片URL
     * adText : 广告语
     * adLink : 广告链接
     */

    private int adId;
    private String adPic = null;
    private String adText = null;
    private String adLink = null;

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getAdPic() {
        return adPic;
    }

    public void setAdPic(String adPic) {
        this.adPic = adPic;
    }

    public String getAdText() {
        return adText;
    }

    public void setAdText(String adText) {
        this.adText = adText;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }
}
