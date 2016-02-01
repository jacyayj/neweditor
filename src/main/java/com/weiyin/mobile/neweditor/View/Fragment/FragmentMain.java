package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.weiyin.mobile.neweditor.Adapter.GrideViewAdapter;
import com.weiyin.mobile.neweditor.Adapter.MainListviewIAdapter;
import com.weiyin.mobile.neweditor.Adapter.MainViewPagerAdapter;
import com.weiyin.mobile.neweditor.Bean.AdapterData;
import com.weiyin.mobile.neweditor.Bean.Page;
import com.weiyin.mobile.neweditor.Bean.Request;
import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacyayj on 2016/1/6 0006.
 * 首页布局fragment
 */
public class FragmentMain extends Fragment implements ViewPager.OnPageChangeListener{

    //fragment根窗口
    private View rootView = null;

    //具体标签布局控件
    @ViewInject(R.id.main_grid)
    private GridView grid = null;

    //广告轮播控件
    @ViewInject(R.id.main_pagger)
    private ViewPager pager = null;

    //页面主体ListView
    @ViewInject(R.id.main_listview)
    private ListView listView =null;

    //轮播广告图片
    private ImageView[] imageViews = null;

    //gridview开启状态
    private boolean gridon = false;

    private MainListviewIAdapter adapter = null;

    private ArrayList<AdapterData> datas = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main,null);
        //注入xutils
        x.view().inject(this,rootView)         ;

        datas = new ArrayList<>();

        initViews();

        return rootView;
    }

    /**
     * 初始化页面
     */
    private void initViews(){
        //初始化具体标签
        grid.setAdapter(new GrideViewAdapter(getActivity()));

        ViewGroup view = (ViewGroup)LayoutInflater.from(getActivity()).inflate(R.layout.item_mainpagger,null);
        View[] views = new View[3];
        views[0] = view.findViewById(R.id.gg1);
        views[1] = view.findViewById(R.id.gg2);
        views[2] = view.findViewById(R.id.gg3);

        imageViews = new ImageView[3];
        imageViews[0] = (ImageView)rootView.findViewById(R.id.dot1);
        imageViews[1] = (ImageView)rootView.findViewById(R.id.dot2);
        imageViews[2] = (ImageView)rootView.findViewById(R.id.dot3);

        view.removeAllViews();

        //初始化广告轮播布局
        pager = (ViewPager)rootView.findViewById(R.id.main_pagger);
        pager.setAdapter(new MainViewPagerAdapter(views));
        pager.setOnPageChangeListener(this);

        for (int i = 0; i < 3 ; i++){
            AdapterData data = new AdapterData();
            data.setIcon(R.drawable.test_main_lable);
            data.setLable("标题"+i);
            data.setContent("内容"+i);
            data.setPviews("6666");
            data.setTime("01.15 11:11");
            data.setRootin("老炮儿");
            datas.add(data);
        }

        //初始化内容主体
        adapter = new MainListviewIAdapter(getActivity(),datas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map values = new HashMap();
                values.put("title",datas.get(position).getLable());
                values.put("content",datas.get(position).getContent());
                ActivityUtils.start(getActivity(), Static.ACTION_ARTCILE,values);
            }
        });

    }

    /**
     * 页面中的点击事件处理
     * @param v 触发事件的控件
     */
    @Event(value = {R.id.grid_on})
    private void onClick(View v){
        switch (v.getId()){
            case R.id.grid_on :
                if (gridon){
                    grid.setVisibility(View.GONE);
                    gridon = false;
                }else {
                    grid.setVisibility(View.VISIBLE);
                    gridon = true;
                }
                default:break;
        }
    }

    /**
     * 加载文章列表
     */
    private void getArticle(){
                Gson gson = new Gson();

                Request request = new Request("login");

                Page pageT = Page.newInstance();

                request.setHashCode(0,0,pageT.getCurrPage(),pageT.getPageSize(),0,1);

                String req = gson.toJson(request);

                RequestParams params = new RequestParams("");
                params.setCharset("UTF-8");
                params.addParameter("request",Base64.encode(req.getBytes(),0));
                params.addParameter("type",1);
                params.addParameter("pageSize",pageT.getPageSize());
                params.addParameter("page",pageT.getCurrPage());
                params.addParameter("userId",pageT.getPageSize());
                params.addParameter("pageSize",pageT.getPageSize());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        ActivityUtils.toast(getActivity(),"登录成功");
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ActivityUtils.toast(getActivity(),"服务器出错:"+ex.getMessage());
                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {
                        ActivityUtils.toast(getActivity(),"取消登录");
                    }

                    @Override
                    public void onFinished() {
                        ActivityUtils.toast(getActivity(),"登录失败");
                    }
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0;i<imageViews.length;i++){
            if (i == position)
                imageViews[i].setImageResource(R.drawable.dot_a);
            else
                imageViews[i].setImageResource(R.drawable.dot_b);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
