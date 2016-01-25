package com.weiyin.mobile.neweditor.Adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.weiyin.mobile.neweditor.R;

/**
 * gridview适配器、初始化界面、更新数据
 * Created by jacyayj on 2016/1/6 0006.
 */
public class GrideViewAdapter extends BaseAdapter{

    private LayoutInflater inflater = null;
    private Context context = null;
    private viewHodler hodler = null;
    private Display display = null;

    public GrideViewAdapter(Context context) {
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            hodler = new viewHodler();
            convertView = inflater.inflate(R.layout.item_maingrid,null);
            hodler.btn = (Button) convertView.findViewById(R.id.main_grid_btn);
            convertView.setTag(hodler);
        }
        else
            hodler = (viewHodler) convertView.getTag();

        hodler.btn.setText("复古家饰");
        return convertView;
    }

    class viewHodler{
        private Button btn = null;
    }

}
