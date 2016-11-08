package com.gaiso.floatmenu.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Gaiso on 2016/11/1.
 */

public interface MViewHolder {

    Context getContext();

    View getView();

    int getResId();

    void findView();
}
