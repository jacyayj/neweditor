package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiyin.mobile.neweditor.Bean.Request;
import com.weiyin.mobile.neweditor.Bean.Response;
import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Bean.User;
import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.DataUtils;
import com.weiyin.mobile.neweditor.Utils.DbUtils;
import com.weiyin.mobile.neweditor.Utils.StringUtils;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 *
 * 登录界面
 * Created by jacyayj on 2016/1/11 0011.
 */
public class FragmentLogin extends Fragment{

    private View rootView = null;

    //操作fragment的跳转
    private static FragmentHelper helper = null;

    @ViewInject(R.id.login_account)
    private EditText phone_edittext = null;
    @ViewInject(R.id.login_pwd)
    private EditText pwd_edittext = null;

    private String account = null;
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
    @Event(value = {R.id.to_register,R.id.to_findpwd,R.id.login,R.id.login_back})
    private void onClick(View v){

        switch (v.getId()){
            case R.id.to_register : helper.show(Static.TITLE_REGISTER);break;
            case R.id.to_findpwd : helper.show(Static.TITLE_FINDPWD);break;
            case R.id.login_back : getActivity().onBackPressed();break;
            case R.id.login : login();break;
            default:break;
        }

    }

    /**
     * 发送登录命令
     */
    private void login(){
        account = phone_edittext.getText().toString();
        pwd = pwd_edittext.getText().toString();
            if(StringUtils.isNULL(pwd)){
                ActivityUtils.toast(getActivity(),"请输入密码！");
            }else {
                ActivityUtils.dialog(getActivity());
//                ActivityUtils.toast(getActivity(),"二狗子已登录");
//                Static.ISLOGIN = true;
//                getActivity().finish();

                final Gson gson = new Gson();

                Request request = new Request("login");

                request.setHashCode(new String[]{account,StringUtils.MD5(pwd)});

                String req = gson.toJson(request);

                RequestParams params = new RequestParams("http://192.168.0.200/micro-silver/api/index.php?i=2&m=wy_neweditor&do=login");
                params.setCharset("UTF-8");

                Log.v("jacy","reqjson : "+req);
                Log.v("jacy","base64 : "+new String(Base64.encode(req.getBytes(),Base64.DEFAULT)));

                params.addParameter("request",new String(Base64.encode(req.getBytes(),Base64.DEFAULT)));
                params.addParameter("userNo", account);
                params.addParameter("userPass",StringUtils.MD5(pwd));
                x.http().post(params, new Callback.CommonCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        Log.v("jacy",result);
                        Type type = new TypeToken<Response<User>>(){}.getType();
                        Response<User> rsp = gson.fromJson(result,type);
                        int code = rsp.getCode();
                        switch (code){

                            case Static.CODE_CLIENTNOTCFG : ActivityUtils.toast(getActivity(),"客户端版本未配置");break;
                            case Static.CODE_ERROR_HASH : ActivityUtils.toast(getActivity(),"HASH错误");break;
                            case Static.CODE_ERROR_OTHER : ActivityUtils.toast(getActivity(),"未知错误");break;
                            case Static.CODE_ERROR_PARAM : ActivityUtils.toast(getActivity(),"参数错误");break;
                            case Static.CODE_ERROR_USERORPWD: ActivityUtils.toast(getActivity(),"用户名或密码错误");break;
                            case Static.CODE_SUCCESS :
                                ActivityUtils.toast(getActivity(),"登录成功");
                                try {
                                    DbManager manager = DbUtils.openOrCreatDb();
                                    DataUtils.writeBoolean(getActivity(),"islogin",true);
                                    manager.dropTable(User.class);
                                    manager.save(rsp.getData());

                                    User user = manager.selector(User.class).findFirst();
                                    Log.v("jacy","user : "+new Gson().toJson(user));

                                } catch (DbException e) {
                                    Log.v("jacy","chucuo?");
                                    e.printStackTrace();
                                }
                                break;
                            case Static.CODE_USERNOTUSE : ActivityUtils.toast(getActivity(),"用户已被禁用");break;

                            default:break;
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ActivityUtils.toast(getActivity(),"服务器出错:"+ex.getMessage()+isOnCallback);
                        Log.v("jacy","code : "+ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        ActivityUtils.toast(getActivity(),"取消登录");
                        Log.v("jacy",cex.getMessage());
                    }

                    @Override
                    public void onFinished() {
                        ActivityUtils.dialog(getActivity());
                        getActivity().finish();
                    }
                });

            }
    }

}
