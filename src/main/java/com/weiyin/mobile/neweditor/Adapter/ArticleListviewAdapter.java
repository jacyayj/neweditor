package com.weiyin.mobile.neweditor.Adapter;

import android.app.Activity;
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

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.weiyin.mobile.neweditor.Bean.AdapterData;
import com.weiyin.mobile.neweditor.R;

import java.util.ArrayList;


/**
 * Created by jacyayj on 2015/12/23 0023.
 *
 * 自定义我的文章Listview适配器，用于初始化listview，更新界面数据等。
 */
public class ArticleListviewAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context = null;
    private LayoutInflater inflater = null;
    private Display display = null;
    private ViewHodler hodler = null;
    private ArrayList<AdapterData> datas = null;

    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
            };


    private ShareAction shareAction = null;

    public ArticleListviewAdapter(Context context,ArrayList<AdapterData> datas) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        shareAction = new ShareAction((Activity) context);
        shareAction.setDisplayList(displaylist)
                .withText("呵呵")
                .withTitle("title")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(new UMImage(context,R.drawable.icon_weixin));
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
            convertView = inflater.inflate(R.layout.item_article,null);
            hodler.lable = (TextView) convertView.findViewById(R.id.article_lable);
            hodler.content = (TextView) convertView.findViewById(R.id.article_content);
            hodler.time = (TextView) convertView.findViewById(R.id.article_time);
            hodler.pviews = (TextView) convertView.findViewById(R.id.article_pviews);
            hodler.check = (TextView) convertView.findViewById(R.id.article_check);
            hodler.share = (ImageView) convertView.findViewById(R.id.article_share);
            hodler.delete = (ImageView) convertView.findViewById(R.id.article_delete);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }

        hodler.lable.setText(datas.get(position).getLable());
        hodler.content.setText(datas.get(position).getContent());
        hodler.time.setText(datas.get(position).getTime());
        hodler.pviews.setText("阅读:"+datas.get(position).getPviews());
        hodler.check.setText("["+datas.get(position).getCheck()+"]");
        if ("审核通过".equals(datas.get(position).getCheck()))
            hodler.share.setEnabled(true);
        else
            hodler.share.setEnabled(false);

        hodler.share.setOnClickListener(this);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,(int)(display.getHeight()*0.13));
        convertView.setLayoutParams(params);
        return convertView;
    }

    public void refresh(ArrayList<AdapterData> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.article_share : shareAction.open();

                break;
            default:break;
        }
    }

    /**
     * listview的item管理器，管理item中的控件的数据不知和点击事件
     */
    class ViewHodler{

        private TextView lable = null;
        private TextView content = null;
        private TextView time = null;
        private TextView pviews = null;
        private TextView check = null;
        private ImageView share = null;
        private ImageView delete = null;
    }

}
