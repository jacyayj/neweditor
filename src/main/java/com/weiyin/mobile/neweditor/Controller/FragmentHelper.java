package com.weiyin.mobile.neweditor.Controller;

import android.os.Bundle;

/**
 * Created by jacyayj on 2016/1/11 0011.
 */
public interface FragmentHelper {

    void show(String arg);

    void goBack();

    void jumpTo(String arg0);

    void setBundle(Bundle bundle);

    Bundle getBundle();

}
