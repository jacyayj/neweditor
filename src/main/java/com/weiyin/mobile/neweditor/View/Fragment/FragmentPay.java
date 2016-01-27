package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;

import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * 会员支付界面
 * Created by jacyayj on 2016/1/11 0011.
 */
public class FragmentPay extends Fragment{

    private View rootView = null;

    //fragment页面跳转的辅助类
    private static FragmentHelper helper = null;

    public static FragmentPay newInstacne(FragmentHelper helper1){
        helper = helper1;
        return new FragmentPay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_pay,null);

        x.view().inject(this,rootView);

        initViews();

        return rootView;
    }

    private void initViews(){
    }

    /**
     * 页面中的点击事件
     * @param v 触发点击事件的控件
     */
    @Event(value = {R.id.pay_alippay,R.id.pay_weixin})
    private void onClicke(View v){

        switch (v.getId()){
            case R.id.pay_weixin : ActivityUtils.toast(getActivity(),"微信支付");break;
            case R.id.pay_alippay : ActivityUtils.toast(getActivity(),"支付宝支付");break;
            default:break;

        }
    }

}
