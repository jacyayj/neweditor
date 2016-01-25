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
import android.widget.Toast;

import com.weiyin.mobile.neweditor.Bean.AdapterData;
import com.weiyin.mobile.neweditor.R;

import java.util.ArrayList;


/**
 * Created by jacyayj on 2015/12/23 0023.
 *
 * 自定义首页listview适配器、初始化listview，初始化数据，更新界面
 */
public class MainListviewIAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context = null;
    private LayoutInflater inflater = null;
    private Display display = null;
    private ViewHodler hodler = null;
    private ArrayList<AdapterData> datas = null;

    public MainListviewIAdapter(Context context,ArrayList<AdapterData> datas) {
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
            convertView = inflater.inflate(R.layout.item_mainlist,null);

            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,(int)(display.getHeight()*0.15));
            convertView.setLayoutParams(params);

            hodler.icon = (ImageView) convertView.findViewById(R.id.main_list_icon);
            hodler.lable = (TextView) convertView.findViewById(R.id.main_list_lable);
            hodler.content = (TextView) convertView.findViewById(R.id.main_list_content);
            hodler.pviews = (TextView) convertView.findViewById(R.id.main_list_pviews);
            hodler.time = (TextView) convertView.findViewById(R.id.main_list_time);
            hodler.rootin = (TextView) convertView.findViewById(R.id.main_list_rootin);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.lable.setText(datas.get(position).getLable());
        hodler.content.setText(datas.get(position).getContent());
        hodler.icon.setImageResource(datas.get(position).getIcon());
        hodler.pviews.setText("阅读:"+datas.get(position).getPviews());
        hodler.time.setText(datas.get(position).getTime());
        hodler.rootin.setText("来源:"+datas.get(position).getRootin());

        return convertView;
    }

    public void refresh(ArrayList<AdapterData> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_list_icon : Toast.makeText(context,"ICON",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_list_lable : Toast.makeText(context,"LABLE",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_list_content : Toast.makeText(context,"CONTENT",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_list_pviews : Toast.makeText(context,"PRIVIEWS",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_list_time : Toast.makeText(context,"TIME",Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_list_rootin : Toast.makeText(context,"ROOTIN",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }
    class ViewHodler{

        private ImageView icon = null;
        private TextView lable = null;
        private TextView content = null;
        private TextView pviews = null;
        private TextView time = null;
        private TextView rootin = null;

    }

}
