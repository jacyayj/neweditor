package com.weiyin.mobile.neweditor.View.Fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weiyin.mobile.neweditor.Adapter.InsertMusicListviewIAdapter;
import com.weiyin.mobile.neweditor.Bean.Music;
import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Controller.FindMusicSrevice;
import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.Controller.MusicPlayerService;
import com.weiyin.mobile.neweditor.Controller.MusicPlayerServiceListener;
import com.weiyin.mobile.neweditor.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 出入音乐界面
 * Created by jacyayj on 2016/1/12 0012.
 */
public class FragmentInsertMusic extends Fragment{

    private View rootView = null;

    //音乐列表
    @ViewInject(R.id.insertmusic_listview)
    private ListView musiclistview = null;

    //操作fragment跳转的工具
    private static FragmentHelper helper = null;

    //音乐数据
    private ArrayList<Music> musics = null;

    //自定义列表适配器
    private InsertMusicListviewIAdapter adapter = null;

    //音乐播放、暂停的控制器
    private MusicPlayerServiceListener musicPlayer = null;

    //当前播放的音乐的索引
    private int index = -1;

    //广播接收器
    private BroadcastReceiver receiver = null;

    //查找音乐的连接
    private ServiceConnection findconnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.v("jacy","查找?");
            musics = ((FindMusicSrevice.MusicListBinder)service).getMusicFile();
            for (Music m : musics){
                Log.v("jacy",m.getName()+"?");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    //播放音乐的连接
    private ServiceConnection playconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicPlayer = ((MusicPlayerService.MusicPlayerBinder)service).getMusicPlayerService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayer = null;
        }
    };

    public static FragmentInsertMusic newInstacne(FragmentHelper helper1){
        helper = helper1;
        return new FragmentInsertMusic();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_insertmusic,null);

        x.view().inject(this,rootView);

        init();

        return rootView;
    }

    /**
     * 初始化页面布局、数据
     */
    private void init(){

        //绑定查找音乐服务
        getActivity().bindService(new Intent(getActivity(),FindMusicSrevice.class), findconnection, Context.BIND_AUTO_CREATE);
        if (musicPlayer == null)
            //绑定播放音乐服务
            getActivity().bindService(new Intent(getActivity(),MusicPlayerService.class),playconnection,Context.BIND_AUTO_CREATE);

        musics = new ArrayList<>();
        adapter = new InsertMusicListviewIAdapter(getActivity(),musics);
        receiver = new MusicBrocastRecevier();

        //初始化音乐列表
        musiclistview.setAdapter(adapter);

        //为音乐列表添加item点击事件
        musiclistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                musicPlayer.Play(musics.get(position).getUrl());
            }
        });

        //注册广播负责监控音乐播放状态
        getActivity().registerReceiver(receiver,new IntentFilter(Static.MUSIC_BROADCAST_ACTION));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(findconnection);
    }

    /**
     * 自定义广播接收装置、处理接收到的广播
     */
    class MusicBrocastRecevier extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isplaying = intent.getBooleanExtra(Static.MUSIC_ISPLAYING,false);
            musiclistview.setItemChecked(index,isplaying);
        }
    }

}
