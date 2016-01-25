package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.weiyin.mobile.neweditor.Adapter.ArticleListviewAdapter;
import com.weiyin.mobile.neweditor.Bean.AdapterData;
import com.weiyin.mobile.neweditor.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by jacyayj on 2016/1/6 0006.
 * 我的文章界面fragment布局
 */
public class FragmentMyArtcile extends Fragment{

    private View rootView = null;

    //发表的文章数
    @ViewInject(R.id.my_article)
    private TextView myarticle = null;
    //界面主体
    @ViewInject(R.id.article_listview)
    private ListView articlelistview = null;

    private ArticleListviewAdapter adapter = null;

    private ArrayList<AdapterData> datas = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_myatrcile,null);

        //注入xutils
        x.view().inject(this,rootView);

        initViews();
        return rootView;
    }

    private void initViews(){
        datas = new ArrayList<>();

        AdapterData data = new AdapterData();
        data.setLable("我的文章");
        data.setCheck("审核失败");
        data.setTime("12.12 12:00");
        data.setPviews("666");
        data.setContent("我的文章内容");
        AdapterData data1 = new AdapterData();
        data1.setLable("我的文章");
        data1.setCheck("审核中");
        data1.setTime("12.12 12:00");
        data1.setPviews("666");
        data1.setContent("我的文章内容");
        AdapterData data2 = new AdapterData();
        data2.setLable("我的文章");
        data2.setCheck("审核通过");
        data2.setTime("12.12 12:00");
        data2.setPviews("666");
        data2.setContent("我的文章内容");
        datas.add(data);
        datas.add(data1);
        datas.add(data2);

        adapter = new ArticleListviewAdapter(getActivity(),datas);
        articlelistview.setAdapter(adapter);
    }

    /**
     * 使用xutils处理页面中的点击事件
     * @param v 触发事件的控件
     */
    @Event(value = R.id.my_article)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_article :
                Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }

}
