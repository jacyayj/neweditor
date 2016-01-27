package com.weiyin.mobile.neweditor.View.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.weiyin.mobile.neweditor.Controller.FragmentHelper;
import com.weiyin.mobile.neweditor.R;
import com.weiyin.mobile.neweditor.Utils.ActivityUtils;
import com.weiyin.mobile.neweditor.Utils.ImageUtils;
import com.weiyin.mobile.neweditor.View.CostomView.SelectorPicPopupWindow;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * 会员支付界面
 * Created by jacyayj on 2016/1/11 0011.
 */
public class FragmentEditdata extends Fragment implements View.OnClickListener{

    private final String HEAD_NAME = "userhead.png";

    private final int CODE_TAKEPIC = 0x00123;
    private final int CODE_CHOOSEPIC = 0x00124;
    private final int CODE_CROPPIC = 0x00125;

    private View rootView = null;

    private SelectorPicPopupWindow popupWindow = null;

    //fragment页面跳转的辅助类
    private static FragmentHelper helper = null;

    @ViewInject(R.id.edit_name)
    private EditText name = null;
    @ViewInject(R.id.edit_head_img)
    private ImageView head = null;

    public static FragmentEditdata newInstacne(FragmentHelper helper1){
        helper = helper1;
        return new FragmentEditdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_editdata,null);

        x.view().inject(FragmentEditdata.this,rootView);

        initViews();

        return rootView;
    }

    private void initViews(){

        popupWindow = new SelectorPicPopupWindow(getActivity(),this);

    }

    /**
     * 页面中的点击事件
     * @param v 触发点击事件的控件
     */
    @Event(value = {R.id.edit_head,R.id.pay_weixin})
    private void onClicke(View v){

        switch (v.getId()){
            case R.id.edit_head : popupWindow.showAtLocation(rootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);break;
            case R.id.pay_alippay :
                ActivityUtils.toast(getActivity(),"支付宝支付");break;
            default:break;

        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_pic : takePic();popupWindow.dismiss();break;
            case R.id.choose_pic : choosePic();popupWindow.dismiss();break;
            default:break;
        }
    }

    /**
     * 拍设头像
     */
    private void takePic(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),HEAD_NAME)));
            startActivityForResult(intent,CODE_TAKEPIC);
        }else
            ActivityUtils.toast(getActivity(),"SD卡不可用");

    }

    /**
     * 选取头像
     */
    private void choosePic(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,CODE_CHOOSEPIC);
    }

    private void cropPic(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop",true);
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,CODE_CROPPIC);
    }

    private void setHeadImg(Intent headImg){
        Bundle bundle = headImg.getExtras();
        if (bundle !=null){
            Bitmap bitmap = bundle.getParcelable("data");
            head.setImageBitmap(ImageUtils.toRoundBitmap(bitmap));
            bitmap.recycle();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == getActivity().RESULT_CANCELED)
            return;

        switch (requestCode){

            case CODE_CHOOSEPIC :
                if (data != null)
                cropPic(data.getData());
                else
                    return;
                break;

            case CODE_TAKEPIC :
                    File file = new File(Environment.getExternalStorageDirectory(),HEAD_NAME);
                    cropPic(Uri.fromFile(file));
                break;

            case CODE_CROPPIC :
                if (data != null)
                    setHeadImg(data);
                break;

            default:break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
