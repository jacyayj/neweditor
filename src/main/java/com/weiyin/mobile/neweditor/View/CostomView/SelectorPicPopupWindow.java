package com.weiyin.mobile.neweditor.View.CostomView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.weiyin.mobile.neweditor.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by jacyayj on 2016/1/16 0016.
 */
public class SelectorPicPopupWindow extends PopupWindow{

    private View rootView = null;

    private TextView take_pic = null;
    private TextView choose_pic = null;
    private TextView cencle_seletor = null;
    private Display display = null;

    public SelectorPicPopupWindow(Context context, View.OnClickListener itemclicklistener) {
        super(context);

        display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        rootView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.selectorpic_layout,null);

        take_pic = (TextView) rootView.findViewById(R.id.take_pic);
        choose_pic = (TextView) rootView.findViewById(R.id.choose_pic);
        cencle_seletor = (TextView) rootView.findViewById(R.id.cencle_selector);

        cencle_seletor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        take_pic.setOnClickListener(itemclicklistener);
        choose_pic.setOnClickListener(itemclicklistener);
        setContentView(rootView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight((int)(display.getHeight()*0.3));
        ColorDrawable cw = new ColorDrawable(0x000000);
        setBackgroundDrawable(cw);
        setFocusable(true);
        setOutsideTouchable(true);
    }
}
