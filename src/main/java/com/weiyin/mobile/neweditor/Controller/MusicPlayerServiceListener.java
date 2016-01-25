package com.weiyin.mobile.neweditor.Controller;

/**
 * Created by jacyayj on 2015/12/15 0015.
 */
public interface MusicPlayerServiceListener {

    void Pre(String url);

    void Next(String url);

    void Play(String url);

    void Play();

    void Progress();
}
