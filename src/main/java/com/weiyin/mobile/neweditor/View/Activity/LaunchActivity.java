package com.weiyin.mobile.neweditor.View.Activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;
import com.weiyin.mobile.neweditor.Bean.Request;
import com.weiyin.mobile.neweditor.Bean.Response;
import com.weiyin.mobile.neweditor.Bean.Static;
import com.weiyin.mobile.neweditor.Bean.User;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.DataUtils;
import com.weiyin.mobile.neweditor.Utils.DbUtils;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by jacyayj on 2016/1/8 0008.
 * 启动页、加载本地数据，更新版本
 */
public class LaunchActivity extends Activity{
    private boolean isFirst = true;
    private boolean islogin = false;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        update();
        isFirst = DataUtils.readBoolean(this,"isFirst");
        islogin = DataUtils.readBoolean(this,"islogin");
//        go();
    }
    /**
     * 友盟自动更新
     */
    private void update(){
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int i, final UpdateResponse updateResponse) {
                switch (i){
                    case UpdateStatus.Yes : UmengUpdateAgent.showUpdateDialog(getApplicationContext(),updateResponse);break;
                    case UpdateStatus.No : go();break;
                    case UpdateStatus.NoneWifi : go();break;
                    case UpdateStatus.Timeout : ActivityUtils.toast(getApplicationContext(),"检测超时");go();break;
                    default:break;
                }
            }
        });

        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {
            @Override
            public void onClick(int i) {
                go();
            }
        });
        UmengUpdateAgent.update(this);
    }

    /**
     *  判断是否第一次启动
     */
    private void go(){
        //第一次进入、进入引导页
        if (isFirst) {
            DataUtils.writeBoolean(this,"isFirst", false);
            ActivityUtils.start(LaunchActivity.this, Static.ACTION_GUIDE);
            finish();
        //非第一次进入、判断身份
        } else {
//            islogin = true;
            //已经登录、从数据库中获取用户数据进行后台登录。
            if (islogin){
                try {
                    DbManager manager = DbUtils.openOrCreatDb();
                    user = manager.selector(User.class).findFirst();
                    Log.v("jacy","user : "+new Gson().toJson(user));
                    if (user != null){
                        login();
                    }else {
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        Log.v("jacy","user = null");
                        ActivityUtils.start(this, Static.ACTION_MAIN);
                        finish();
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }
            //未登录、游客身份。直接进入主界面。
            }else {
                ActivityUtils.start(LaunchActivity.this, Static.ACTION_MAIN);
                Log.v("jacy","游客");
                finish();
            }
        }
    }

    /**
     * 后台自动登录、用户出现任何信息错误身份变为游客
     */
    private void login(){
        final Gson gson = new Gson();

        Request request = new Request("login");

        //设置hash值进行MD5加密运算
        request.setHashCode(user.getUserKey(),user.getUserNo());

        //将request转换为json字符串
        String req = gson.toJson(request);

        RequestParams params = new RequestParams("http://192.168.0.200/micro-silver/api/index.php?i=2&m=wy_neweditor&do=login");
        params.setCharset("UTF-8");

        Log.v("jacy","reqjson : "+req);
        Log.v("jacy","base64 : "+new String(Base64.encode(req.getBytes(),Base64.DEFAULT)));

        //设置request参数
        params.addParameter("request",new String(Base64.encode(req.getBytes(),Base64.DEFAULT)));
        //设置用户名参数
        params.addParameter("userNo", user.getUserNo());

        //发送post登录请求
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Type type = new TypeToken<Response<User>>(){}.getType();
                Response<User> rsp = gson.fromJson(result,type);
                int code = rsp.getCode();

                switch (code){
                    case Static.CODE_CLIENTNOTCFG :
                        ActivityUtils.toast(LaunchActivity.this,"客户端版本未配置");
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        break;
                    case Static.CODE_ERROR_HASH :
                        ActivityUtils.toast(LaunchActivity.this,"验证失败");
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        break;
                    case Static.CODE_ERROR_OTHER :
                        ActivityUtils.toast(LaunchActivity.this,"未知错误");
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        break;
                    case Static.CODE_ERROR_PARAM :
                        ActivityUtils.toast(LaunchActivity.this,"参数错误");
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        break;
                    case Static.CODE_ERROR_USERORPWD:
                        ActivityUtils.toast(LaunchActivity.this,"用户名或密码错误");
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        break;
                    case Static.CODE_SUCCESS :
                        ActivityUtils.toast(LaunchActivity.this,"登录成功");
                        break;
                    case Static.CODE_USERNOTUSE :
                        ActivityUtils.toast(LaunchActivity.this,"用户已被禁用");
                        DataUtils.writeBoolean(LaunchActivity.this,"islogin",false);
                        break;

                    default:break;
                }


                Log.v("jacy","launch response"+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ActivityUtils.toast(LaunchActivity.this,"服务器出错:"+ex.getMessage()+isOnCallback);
//                        ActivityUtils.dialog(getActivity());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.v("jacy",cex.getMessage());
            }

            @Override
            public void onFinished() {
              ActivityUtils.start(LaunchActivity.this, Static.ACTION_MAIN);
              finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("jacy","LaunchDestroy");
    }

}
