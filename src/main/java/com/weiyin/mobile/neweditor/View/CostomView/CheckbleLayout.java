package com.weiyin.mobile.neweditor.View.CostomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/**
 * Created by jacyayj on 2016/1/13 0013.
 */
public class CheckbleLayout extends RelativeLayout implements Checkable{

    private boolean checked = false;

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    public CheckbleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean b) {
        checked = b;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        // 在原有状态中添加一个空间space用于保存checked状态
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            // 将checked状态合并到原有的状态数组中
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
