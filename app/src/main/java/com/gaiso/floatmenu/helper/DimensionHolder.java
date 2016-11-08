package com.gaiso.floatmenu.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.gaiso.floatmenu.constants.MenuEnum;

/**
 * Created by Gaiso on 2016/11/3.
 */

public class DimensionHolder {

    private static final int MENU_ITEM_WIDTH_DP = 48;
    private static final int MENU_ITEM_HEIGHT_DP = 48;
    private static final int COMMON_MARGIN = 3;
    private static final int AD_WIDTH_DP = 200;
    private static final int AD_HEIGHT_DP = 48;

    private Context mContext;
    private DisplayMetrics mMetrics;
    private int mMenuWidth, mMenuHeight, mMenuMargin, mAdWidth, mAdHeight;
    private int mMenuGroupSize;
    private static DimensionHolder INSTANCE = new DimensionHolder();

    private DimensionHolder() {
    }

    public void init(Context context) {
        mContext = context;
        mMetrics = mContext.getResources().getDisplayMetrics();
        mMenuGroupSize = MenuEnum.values().length;
        initDimension();
    }

    private void initDimension() {
        mMenuWidth = dp2Px(MENU_ITEM_WIDTH_DP);
        mMenuHeight = dp2Px(MENU_ITEM_HEIGHT_DP);
        mMenuMargin = dp2Px(COMMON_MARGIN);
        mAdWidth = dp2Px(AD_WIDTH_DP);
        mAdHeight = dp2Px(AD_HEIGHT_DP);
    }

    public static DimensionHolder getInstance() {
        return INSTANCE;
    }

    private int dp2Px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mMetrics);
    }

    public int getMenuWidth() {
        return mMenuWidth;
    }

    public int getMenuHeight() {
        return mMenuHeight;
    }

    public int getMenuGroupWidth() {
        return mMenuGroupSize * ( mMenuWidth + mMenuMargin);
    }

    public int getMenuGroupHeight() {
        return mMenuHeight;
    }

    public int getMenuMargin() {
        return mMenuMargin;
    }

    public int getLogoAndGroupWidth() {
        return mMenuGroupSize * ( mMenuWidth + mMenuMargin) + mMenuWidth;
    }

    public int getAdWidth() {
        return mAdWidth;
    }

    public int getAdHeight() {
        return mAdHeight;
    }

    public int getLogoAndAdWidth() {
        return mAdWidth + mMenuWidth + mMenuMargin;
    }
}
