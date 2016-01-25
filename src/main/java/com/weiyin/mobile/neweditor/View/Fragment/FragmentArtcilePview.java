package com.weiyin.mobile.neweditor.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by jacyayj on 2016/1/13 0013.
 * 文章预览界面
 */
public class FragmentArtcilePview extends Fragment{

    private View rootView = null;

    //文章内容
    @ViewInject(R.id.artcile_pview)
    private TextView artcile = null;

    //头部提示条
    @ViewInject(R.id.article_remind_top)
    private View remind_top = null;

    //底部提示条
    @ViewInject(R.id.article_remind_bottom)
    private View remind_bottom = null;

    //文章标题
    @ViewInject(R.id.article_title)
    private TextView title = null;

    //fragment之间传递的数据
    private Bundle bundle = null;

    //操作fragment跳转的工具
    private static FragmentHelper helper = null;

    public static FragmentArtcilePview newInstance(FragmentHelper helper1) {
        helper = helper1;
        return new FragmentArtcilePview();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_artcilepview,null);

        x.view().inject(this,rootView);

        bundle = helper.getBundle();

        initViews();

        return rootView;
    }

    /**
     * 初始化页面数据
     */
    private void initViews(){
        title.setText(bundle.getString("title"));

    }

    /**
     * 使用xutils处理页面中的点击事件
     * @param v 触发事件的控件
     */
    @Event(value = {R.id.artcile_pview_back,R.id.artcile_remind_close_top,R.id.artcile_remind_close_bottom})
    private void onClicke(View v){

        switch (v.getId()){

            case R.id.artcile_pview_back : getActivity().onBackPressed();break;

            case R.id.artcile_remind_close_top : remind_top.setVisibility(View.INVISIBLE);break;

            case R.id.artcile_remind_close_bottom : remind_bottom.setVisibility(View.INVISIBLE);break;

            default:break;

        }
    }

}
