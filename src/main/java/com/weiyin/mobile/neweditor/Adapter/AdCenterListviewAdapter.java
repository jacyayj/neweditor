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
import com.weiyin.mobile.neweditor.R;

import java.util.ArrayList;


/**
 * 广告中心listview的适配器，用于更新数据、初始化界面
 * Created by jacyayj on 2015/12/23 0023.
 */
public class AdCenterListviewAdapter extends BaseAdapter{

    private Context context = null;
    private LayoutInflater inflater = null;
    private Display display = null;
    private ViewHodler hodler = null;
    private ArrayList<AdapterData> datas = null;

    public AdCenterListviewAdapter(Context context,ArrayList<AdapterData> datas) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public AdapterData getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_adcenter,null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,(int)(display.getHeight()*0.25));
            convertView.setLayoutParams(params);
            hodler.lable = (TextView) convertView.findViewById(R.id.adcenter_lable);
            hodler.edit = (ImageView) convertView.findViewById(R.id.adcenter_edit);
            hodler.delete = (ImageView) convertView.findViewById(R.id.adcenter_delete);

            convertView.setTag(hodler);
        }else
            hodler = (ViewHodler) convertView.getTag();

        convertView.setBackgroundResource(datas.get(position).getIcon());
        hodler.lable.setText(datas.get(position).getLable());

        return convertView;
    }

    public void refresh(ArrayList<AdapterData> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * listview的item的管理类
     */
    class ViewHodler{

        private TextView lable = null;

        private ImageView edit = null;

        private ImageView delete = null;

    }

}
