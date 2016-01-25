package com.weiyin.mobile.neweditor.Bean;

/**
 * 音乐类、存放音乐的属性
 * Created by jacyayj on 2015/12/15 0015.
 */
public class Music {

    private String name = null;

    private String singer = null;

    private String album = null;

    private long size = 0;

    private int duration = 0;

    private String url = null;

    public boolean isplaying() {
        return isplaying;
    }

    public void setIsplaying(boolean isplaying) {
        this.isplaying = isplaying;
    }

    private boolean isplaying = false;

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    private boolean download = false;

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getAlbum() {
        return album;
    }

    public long getSize() {
        return size;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }
}
