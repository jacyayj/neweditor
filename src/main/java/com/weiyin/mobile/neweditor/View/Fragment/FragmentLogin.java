package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Controller.ActivityController;
import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.DataUtils;
import com.weiyin.mobile.neweditor.Utils.StringUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 *
 * 登录界面
 * Created by jacyayj on 2016/1/11 0011.
 */
public class FragmentLogin extends Fragment{

    private View rootView = null;

    //操作fragment的跳转
    private static FragmentHelper helper = null;

    @ViewInject(R.id.login_phone)
    private EditText phone_edittext = null;
    @ViewInject(R.id.login_pwd)
    private EditText pwd_edittext = null;

    private String phone = null;
    private String pwd = null;

    public static FragmentLogin newInstacne(FragmentHelper helper1){
        helper = helper1;
        return new FragmentLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_login,null);

        x.view().inject(this,rootView);

        return rootView;
    }

    /**
     * 页面中的点击事件
     * @param v 触发事件的控件
     */
    @Event(value = {R.id.to_register,R.id.to_findpwd,R.id.login})
    private void onClick(View v){

        switch (v.getId()){
            case R.id.to_register : helper.show(Static.TITLE_REGISTER);break;
            case R.id.to_findpwd : helper.show(Static.TITLE_FINDPWD);break;
            case R.id.login : login();break;
            default:break;
        }

    }

    /**
     * 发送登录命令
     */
    private void login(){

        phone = phone_edittext.getText().toString();
        pwd = pwd_edittext.getText().toString();
        if (StringUtils.isPhone(phone)){
            if("".equals(pwd)){
                ActivityController.getInstance().toast(getActivity(),"请输入密码！");
            }else {
                ActivityController.getInstance().toast(getActivity(),"二狗子已登录");
                DataUtils.writeString(getActivity(),"phone",phone);
                DataUtils.writeString(getActivity(),"pwd",pwd);
                Static.ISLOGIN = true;
                getActivity().finish();
            }
        }else{
            ActivityController.getInstance().toast(getActivity(),"请输入正确的手机号码！");
        }
    }

}
