package com.weiyin.mobile.neweditor.Controller;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;


import com.weiyin.mobile.neweditor.Bean.Music;

import java.util.ArrayList;

/**
 * Created by jacyayj on 2015/12/15 0015.
 */
public class FindMusicSrevice extends Service{


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("jacy","Bind");
        return new MusicListBinder();
    }

   public class MusicListBinder extends Binder {
            public ArrayList<Music> getMusicFile(){
                ArrayList<Music> musicList = new ArrayList();
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                if (cursor != null) {
                    Log.v("jacy","查到");
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                            Music music = new Music();
                            music.setName(name);
                            music.setSinger(singer);
                            music.setAlbum(album);
                            music.setSize(size);
                            music.setSize(size);
                            music.setUrl(url);
                            musicList.add(music);
                        } while (cursor.moveToNext());
                    }
                }
                return musicList;
            }
    }

}
