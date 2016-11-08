package com.gaiso.floatmenu.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gaiso.floatmenu.R;

/**
 * Created by Gaiso on 2016/11/1.
 */

public abstract class AbsMenu implements MViewHolder, IBarge {

    private Context mContext;

    protected View itemView;

    private ImageView[] mBarges = new ImageView[2];

    protected int mActiveBarge = RIGHT;

    public interface OnMenuItemEventListener {

    }

    public AbsMenu(Context context) {
        mContext = context;
        getView();
    }

    @Override
    public void setDefaultBarge(int side) {
        mActiveBarge = side;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public View getView() {
        if (itemView == null) {
            itemView = View.inflate(mContext, getResId(), null);
            mBarges[LEFT] = (ImageView) itemView.findViewById(R.id.iv_left_barge);
            mBarges[RIGHT] = (ImageView) itemView.findViewById(R.id.iv_right_barge);
            findView();
        }
        return itemView;
    }

    @Override
    public abstract int getResId();

    @Override
    public abstract void findView();

    @Override
    public void showBarge() {
        mBarges[mActiveBarge].setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBarge() {
        mBarges[mActiveBarge].setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isBargeShow() {
        return mBarges[mActiveBarge].getVisibility() == View.VISIBLE;
    }

    @Override
    public void reverseBarge() {
        int before = mActiveBarge;
        mActiveBarge = (mActiveBarge == LEFT ? RIGHT : LEFT);
        if (mBarges[before].getVisibility() == View.VISIBLE) {
            mBarges[before].setVisibility(View.INVISIBLE);
            mBarges[mActiveBarge].setVisibility(View.VISIBLE);
        }
    }
}
