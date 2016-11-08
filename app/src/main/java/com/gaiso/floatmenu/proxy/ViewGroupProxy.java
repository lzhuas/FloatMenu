package com.gaiso.floatmenu.proxy;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.gaiso.floatmenu.widget.MenuGroup;
import com.gaiso.floatmenu.base.ViewHolderGroup;

import java.util.Map;

/**
 * Created by Gaiso on 2016/11/7.
 */

public class ViewGroupProxy implements ViewHolderGroup {

    public static final int MENU_GROUP = 0;

    private Context mContext;
    private Map<Integer, ViewHolderGroup> mGroupMap = new ArrayMap<>();
    private int mCurrentView = MENU_GROUP;

    public ViewGroupProxy(Context context) {
        mContext = context;
    }

    public void addGroup(int key, ViewHolderGroup group) {
        mGroupMap.put(key, group);
    }

    @Override
    public void show() {
        mGroupMap.get(mCurrentView).show();
    }

    @Override
    public void hide() {
        mGroupMap.get(mCurrentView).hide();
    }

    @Override
    public void reverse() {
        mGroupMap.get(mCurrentView).reverse();
    }

    @Override
    public View getView() {
        return mGroupMap.get(mCurrentView).getView();
    }

    @Override
    public boolean isShow() {
        return mGroupMap.get(mCurrentView).isShow();
    }

    public int getCurrentView() {
        return mCurrentView;
    }

    public void reverseBarge() {
        ((MenuGroup) mGroupMap.get(MENU_GROUP)).reverseAllBarge();
    }

    public void showMenuBarge(int which) {
        ((MenuGroup) mGroupMap.get(MENU_GROUP)).showMenuBarge(which);
    }

    public void showAllBarge() {
        ((MenuGroup) mGroupMap.get(MENU_GROUP)).showAllBarge();
    }
}
