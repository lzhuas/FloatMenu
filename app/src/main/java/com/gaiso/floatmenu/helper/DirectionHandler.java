package com.gaiso.floatmenu.helper;

import android.support.v4.util.ArrayMap;
import android.view.View;

/**
 * Created by Gaiso on 2016/11/1.
 */

public class DirectionHandler {

    public static final int LEFT_SIZE = 0;

    public static final int RIGHT_SIZE = 1;

    private static DirectionHandler INSTANCE = new DirectionHandler();

    private int mScreenWidth;

    private int mCurrentSide = LEFT_SIZE;

    private ArrayMap<View, Integer> mViewsDirectionHolder = new ArrayMap<>();

    private DirectionHandler() {
    }

    public static DirectionHandler getInstance() {
        return INSTANCE;
    }

    public void init(int screenWidth) {
        mScreenWidth = screenWidth;
    }

    public void updateSide(float x) {
        int side = x >= (mScreenWidth / 2) ? RIGHT_SIZE : LEFT_SIZE;
        if (mCurrentSide != side) {
            mCurrentSide = side;
        }
    }

    public void addView(View view, int side) {
        if (!mViewsDirectionHolder.containsKey(view))
            mViewsDirectionHolder.put(view, side);
    }

    public void addView(View view) {
        addView(view, LEFT_SIZE);
    }

    public boolean isSideChange(View view) {
        Integer side = mViewsDirectionHolder.get(view);
        if (side == null) {
            return false;
        }
        if (side == mCurrentSide) {
            return false;
        } else {
            mViewsDirectionHolder.put(view, mCurrentSide);
            return true;
        }
    }

    public int getCurrentSide() {

        return mCurrentSide;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public void clear() {
        // TODO: 2016/11/7 clear ArrayMap
        mViewsDirectionHolder.clear();
    }
}
