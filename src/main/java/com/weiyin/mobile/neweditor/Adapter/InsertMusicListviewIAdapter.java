package com.weiyin.mobile.neweditor.Adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiyin.mobile.neweditor.Bean.AdapterData;
import com.weiyin.mobile.neweditor.Bean.Music;
import com.weiyin.mobile.neweditor.R;

import java.util.ArrayList;


/**
 * 音乐列表适配器、初始化音乐列表、更新数据界面
 * Created by jacyayj on 2015/12/23 0023.
 */
public class InsertMusicListviewIAdapter extends BaseAdapter{

    private Context context = null;
    private LayoutInflater inflater = null;
    private Display display = null;
    private ViewHodler hodler = null;

    private ArrayList<Music> datas = null;

    public InsertMusicListviewIAdapter(Context context, ArrayList<Music> datas) {
        this.context = context;
        display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Music getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = View.inflate(context,R.layout.item_insertmusic,null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,(int)(display.getHeight()*0.08));
            convertView.setLayoutParams(params);
            hodler.name = (TextView) convertView.findViewById(R.id.insertmusic_name);
            hodler.state = (ImageView) convertView.findViewById(R.id.insertmusic_state);

            convertView.setTag(hodler);
        }else
            hodler = (ViewHodler) convertView.getTag();

        hodler.name.setText(datas.get(position).getName());
        return convertView;
    }

    public void refresh(ArrayList<Music> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * item管理类
     */
    class ViewHodler{

        private TextView name = null;

        private ImageView state = null;

    }

}
