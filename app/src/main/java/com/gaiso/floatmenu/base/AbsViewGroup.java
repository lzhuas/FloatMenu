package com.gaiso.floatmenu.base;

import android.content.Context;
import android.view.View;

import com.gaiso.floatmenu.helper.DimensionHolder;
import com.gaiso.floatmenu.helper.DirectionHandler;

/**
 * Created by Gaiso on 2016/11/7.
 */

public abstract class AbsViewGroup implements ViewHolderGroup {

    private Context mContext;

    protected DimensionHolder mDimensionHolder = DimensionHolder.getInstance();

    protected DirectionHandler mDirectionHandler = DirectionHandler.getInstance();

    protected OnGroupEventListener mGroupEventListener;

    public AbsViewGroup(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    public void setOnGroupEventListener(OnGroupEventListener listener) {
        mGroupEventListener = listener;
    }

    @Override
    public void show() {
        if (mGroupEventListener != null)
            mGroupEventListener.onGroupVisible(true);
    }

    @Override
    public void hide() {
        if (mGroupEventListener != null)
            mGroupEventListener.onGroupVisible(false);
    }

    @Override
    public abstract View getView();

    @Override
    public abstract boolean isShow();

    @Override
    public abstract void reverse();
}
