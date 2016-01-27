package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.StringUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * 注册页面
 * Created by jacyayj on 2016/1/11 0011.
 */
public class FragmentRegister extends Fragment{

    private View rootView = null;

    //处理时间
    private Handler handler = null;

    //获取验证码按钮
    @ViewInject(R.id.btn_getcode)
    private Button getcode = null;

    //注册协议
    @ViewInject(R.id.reg_agreement)
    private TextView agreement = null;

    //再次发送验证码的时间
    private int seconds = 3;

    @ViewInject(R.id.register_phone)
    private EditText phone_edittext = null;
    @ViewInject(R.id.register_pwd)
    private EditText pwd_edittext = null;
    @ViewInject(R.id.register_pwd_affirm)
    private EditText pwd_affirm_edittext = null;
    @ViewInject(R.id.register_code)
    private EditText code_edittext = null;

    private String phone = null;
    private String pwd = null;
    private String pwd_affirm = null;
    private String code = null;
    //页面跳转辅助工具
    private static FragmentHelper helper = null;

    public static FragmentRegister newInstacne(FragmentHelper helper1){
        helper = helper1;
        return new FragmentRegister();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_register,null);

        x.view().inject(this,rootView);

        initViews();

        return rootView;
    }

    /**
     * 初始化页面布局、数据
     */
    private void initViews(){

        SpannableString ss = new SpannableString(agreement.getText());
        ForegroundColorSpan scf = new ForegroundColorSpan(getResources().getColor(R.color.tagscolor));
        ss.setSpan(scf,11,19, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        agreement.setText(ss);

        initCodeBtn();
    }

    /**
     * 页面中的点击事件
     * @param v 触发事件的控件
     */
    @Event(value = {R.id.btn_getcode,R.id.register})
    private void onClicke(View v){

        switch (v.getId()){

            case R.id.btn_getcode : handler.sendEmptyMessage(0x01);break;

            case R.id.register : register();break;

            default:break;

        }
    }

    /**
     * 初始化获取验证码的功能与操作
     */
    private void initCodeBtn(){
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

    /**
     * 发送获取验证码请求
     */

    public String getCode() {
        String code = null;
        RequestParams params = new RequestParams("");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

        return code;
    }

    /**
     * 发送注册请求
     */
    private void register(){
        phone = phone_edittext.getText().toString();
        pwd = pwd_edittext.getText().toString();
        pwd_affirm = pwd_affirm_edittext.getText().toString();
        code = code_edittext.getText().toString();

        if (StringUtils.isPhone(phone)){
            if (StringUtils.isNULL(code)){
                ActivityUtils.toast(getActivity(),"请输入验证码！");
            }else {
                if("1234".equals(code)){
                    if (StringUtils.isNULL(pwd)){
                        ActivityUtils.toast(getActivity(),"请输入密码！");
                    }else {
                        if (StringUtils.isNULL(pwd_affirm)){
                            ActivityUtils.toast(getActivity(),"请再次输入密码！");
                        }else {
                            if (pwd.equals(pwd_affirm)){

                                RequestParams params = new RequestParams("");
                                params.addBodyParameter("img",new File("url"));
                                x.http().post(params, new Callback.CommonCallback<String>() {

                                    @Override
                                    public void onSuccess(String result) {
                                    }

                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {
                                    }

                                    @Override
                                    public void onCancelled(CancelledException cex) {
                                    }

                                    @Override
                                    public void onFinished() {
                                    }
                                });
                                ActivityUtils.toast(getActivity(),"修改成功！");
                            }else {
                                ActivityUtils.toast(getActivity(),"两次输入密码不同、请确认后再试！");
                            }
                        }
                    }
                }else {
                    ActivityUtils.toast(getActivity(),"验证码错误");
                }
            }
        }else {
            ActivityUtils.toast(getActivity(),"请输入正确的手机号！");
        }

    }

}
