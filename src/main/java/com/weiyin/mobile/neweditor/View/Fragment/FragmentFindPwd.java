package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.weiyin.mobile.neweditor.Bean.Request;
import com.weiyin.mobile.neweditor.Bean.User;
import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.StringUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 找回密码界面
 * Created by jacyayj on 2016/1/11 0011.
 */
public class FragmentFindPwd extends Fragment{

    private View rootView = null;

    //定时器、控制获取验证码按钮
    private Handler handler = null;

    //获取验证码按钮
    @ViewInject(R.id.btn_getcode_find)
    private Button getcode = null;

    @ViewInject(R.id.find_phone)
    private EditText phone_edittext = null;
    @ViewInject(R.id.find_pwd)
    private EditText pwd_edittext = null;
    @ViewInject(R.id.find_pwd_affirm)
    private EditText pwd_affirm_edittext = null;
    @ViewInject(R.id.find_code)
    private EditText code_edittext = null;

    private String phone = null;
    private String pwd = null;
    private String pwd_affirm = null;
    private String code = null;

    //再次发送验证码时间
    private int seconds = 3;

    //控件fragment跳转的工具类
    private static FragmentHelper helper = null;

    public static FragmentFindPwd newInstacne(FragmentHelper helper1){
        helper = helper1;
        return new FragmentFindPwd();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_findpwd,null);

        x.view().inject(this,rootView);

        initViews();

        return rootView;
    }

    /**
     * 初始化页面布局、数据
     */
    private void initViews(){
        initCodeBtn();
    }

    /**
     * 使用xutils处理页面中的点击事件
     * @param v 触发事件的控件
     */
    @Event(value = {R.id.btn_getcode_find,R.id.afirm_change,R.id.change_back})
    private void onClicke(View v){

        switch (v.getId()){

            case R.id.btn_getcode_find : handler.sendEmptyMessage(0x01);

            case R.id.afirm_change : find();break;

            case R.id.change_back : getActivity().onBackPressed();break;

            default:break;

        }
    }

    /**
     * 初始化获取验证码的操作
     */
    private void initCodeBtn(){

        phone = phone_edittext.getText().toString();

//        if (StringUtils.isPhone(phone)){
//            ActivityUtils.toast(getActivity(),"验证码已发送");
//        }else {
//            ActivityUtils.toast(getActivity(),"请输入正确的手机号");
//        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x01){
                    getcode.setEnabled(false);
                    getcode.setText("再次发送（"+seconds+"S)");
                    handler.sendEmptyMessageDelayed(0x01,1000);
                    seconds--;
                    if (seconds == 0){
                        handler.removeMessages(0x01);
                        handler.sendEmptyMessageDelayed(0x02,1000);
                    }
                }else if (msg.what == 0x02){
                        seconds = 3;
                        getcode.setText(R.string.send_again);
                        getcode.setEnabled(true);
                        handler.removeMessages(0x02);
                }
            }
        };

    }

    private void find(){
        phone = phone_edittext.getText().toString();
        pwd = pwd_edittext.getText().toString();
        pwd_affirm = pwd_affirm_edittext.getText().toString();
        code = code_edittext.getText().toString();

            if (StringUtils.isNULL(pwd)){
                ActivityUtils.toast(getActivity(),"请输入密码！");
            }else {
                if (StringUtils.isNULL(pwd_affirm)){
                    ActivityUtils.toast(getActivity(),"请再次输入密码！");
                }else {
                    if (pwd.equals(pwd_affirm)){
                        ActivityUtils.toast(getActivity(),"修改成功！");

                        Gson gson = new Gson();
                        Request request = new Request("find");
                        String req = gson.toJson(request);

                        RequestParams params = new RequestParams("");
                        params.setCharset("UTF-8");
                        params.addParameter("request",req);
                        params.addParameter("oldPass",pwd);
                        params.addParameter("newPass",pwd_affirm);
                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                ActivityUtils.toast(getActivity(),"修改成功");
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                ActivityUtils.toast(getActivity(),"服务器出错:"+ex.getMessage());
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                ActivityUtils.toast(getActivity(),"取消修改");
                            }

                            @Override
                            public void onFinished() {
                                ActivityUtils.toast(getActivity(),"修改失败");
                            }
                        });
                    }else {
                        ActivityUtils.toast(getActivity(),"两次输入密码不同、请确认后再试！");
                    }
                }
            }
    }
}
