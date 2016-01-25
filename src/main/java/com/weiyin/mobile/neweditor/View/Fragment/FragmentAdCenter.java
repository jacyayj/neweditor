package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;
import com.weiyin.mobile.neweditor.Adapter.AdCenterListviewAdapter;
import com.weiyin.mobile.neweditor.Bean.AdapterData;
import com.weiyin.mobile.neweditor.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 广告中心页面、显示当前用户已经创建的广告
 * Created by jacyayj on 2016/1/6 0006.
 */
public class FragmentAdCenter extends Fragment{

    private View rootView = null;

    //广告控件主体
    @ViewInject(R.id.adcenter_listview)
    private ListView listView = null;

    private AdCenterListviewAdapter adapter = null;

    private ArrayList<AdapterData> datas = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_adcenter,null);

        x.view().inject(this,rootView);

        initListview();

        return rootView;
    }

    /**
     * 初始化主体内容
     */
    private void initListview(){

        datas = new ArrayList<>();

        for (int i = 0; i < 3 ;i++){
            AdapterData data = new AdapterData();
            data.setIcon(R.drawable.test_ad);
            data.setLable("广告"+i);
            datas.add(data);
        }

        adapter = new AdCenterListviewAdapter(getActivity(),datas);
        listView.setAdapter(adapter);
    }
}
