package com.weiyin.mobile.neweditor.Controller;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


import com.weiyin.mobile.neweditor.Bean.Static;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by jacyayj on 2015/12/15 0015.
 */
public class MusicPlayerService extends Service implements MusicPlayerServiceListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnBufferingUpdateListener{

    private MediaPlayer mediaPlayer = null;

    private Binder binder = new MusicPlayerBinder();

    private String URL = "initialize";

    private boolean isplaying = false;

    private int progress = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("jacy", "MusicPlayerCreat");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void Pre(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            isplaying = true;
            Progress();
            URL = url;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Next(String url) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            isplaying = true;
            Progress();
            URL = url;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Play(String url) {
        Log.v("jacy","play"+url);
        try {
            if(mediaPlayer.isPlaying()){
                if (URL.equals(url)){
                    mediaPlayer.pause();
                    isplaying = false;
                    Progress();
                }else{
                    mediaPlayer.reset();
                    isplaying = false;
                    Progress();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();
                    isplaying = true;
                    URL = url;
                    Progress();
                }
            }else{
                if (URL.equals("initialize")){
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();
                    isplaying = true;
                    URL = url;
                    Progress();
                }else if(!URL.equals(url)){
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();
                    isplaying = true;
                    URL = url;
                    Progress();
                }else{
                    mediaPlayer.start();
                    isplaying = true;
                    Progress();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Play() {

        if (URL.equals("initialize")){
            Intent intent = new Intent(Static.MUSIC_BROADCAST_ACTION);
            intent.putExtra(Static.MUSIC_BROADCAST_FLAG,1);
            sendBroadcast(intent);
        }else{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                isplaying = false;
                Progress();
            }else {
                mediaPlayer.start();
                isplaying = true;
                Progress();
            }
        }
    }

    @Override
    public void Progress() {
            Intent intent = new Intent(Static.MUSIC_BROADCAST_ACTION);
            intent.putExtra(Static.MUSIC_ISPLAYING,isplaying);
            sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        binder = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Intent intent = new Intent(Static.MUSIC_BROADCAST_ACTION);
        intent.putExtra(Static.MUSIC_BROADCAST_FLAG,3);
        intent.putExtra(Static.MUSIC_BROADCAST_INT_KEY,percent);
        sendBroadcast(intent);
    }

    public class MusicPlayerBinder extends Binder{
        public MusicPlayerServiceListener getMusicPlayerService(){
          return  MusicPlayerService.this;
        };
    }
}