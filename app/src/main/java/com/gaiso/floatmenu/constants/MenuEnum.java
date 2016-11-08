package com.gaiso.floatmenu.constants;

import com.gaiso.floatmenu.R;

/**
 * Created by Gaiso on 2016/11/1.
 */

public enum MenuEnum {

    NO_SOUND(MenuOrder.NO_SOUND, "静音", R.mipmap.no_sound),
    VOLUME(MenuOrder.VOLUME, "音量", R.mipmap.volume),
    SETTING(MenuOrder.SETTING, "设置", R.mipmap.settings),
    WIFI(MenuOrder.WIFI, "WIFI", R.mipmap.wi_fi);

    private int mOrder;
    private String mName;
    private int mResId;

    MenuEnum(int order, String name, int resId) {
        mOrder = order;
        mName = name;
        mResId = resId;
    }

    public int getOrder() {
        return mOrder;
    }

    public String getName() {
        return mName;
    }

    public int getResId() {
        return mResId;
    }

}
