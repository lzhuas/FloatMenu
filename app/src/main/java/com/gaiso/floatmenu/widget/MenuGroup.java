package com.gaiso.floatmenu.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.gaiso.floatmenu.base.AbsMenu;
import com.gaiso.floatmenu.base.AbsViewGroup;
import com.gaiso.floatmenu.constants.MenuEnum;
import com.gaiso.floatmenu.constants.MenuOrder;
import com.gaiso.floatmenu.helper.DirectionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gaiso on 2016/11/3.
 */

public class MenuGroup extends AbsViewGroup {

    private LinearLayout mLLMenuGroup;

    private List<AbsMenu> mMenus = new ArrayList<>(4);

    public MenuGroup(Context context) {
        super(context);
        init();
    }

    private void init() {
        createMenus();
        initLayout();
        inflateItem();
        initListener();
    }

    public void showMenuBarge(int which) {
        mMenus.get(which).showBarge();
    }

    public void showAllBarge() {
        for (AbsMenu menu : mMenus) {
            menu.showBarge();
        }
    }

    public void reverseAllBarge() {
        for (AbsMenu menu : mMenus) {
            menu.reverseBarge();
        }
    }

    private void initListener() {
        mMenus.get(MenuOrder.NO_SOUND).getView().setOnClickListener(mNoSoundClick);
        mMenus.get(MenuOrder.VOLUME).getView().setOnClickListener(mVolumeClick);
        mMenus.get(MenuOrder.SETTING).getView().setOnClickListener(mSettingClick);
        mMenus.get(MenuOrder.WIFI).getView().setOnClickListener(mWifiClick);
    }

    private View.OnClickListener mNoSoundClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mMenus.get(MenuOrder.NO_SOUND).isBargeShow())
                mMenus.get(MenuOrder.NO_SOUND).hideBarge();
            Toast.makeText(getContext(), "no sound", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener mVolumeClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (mMenus.get(MenuOrder.VOLUME).isBargeShow())
                mMenus.get(MenuOrder.VOLUME).hideBarge();
            Toast.makeText(getContext(), "volume", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener mSettingClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (mMenus.get(MenuOrder.SETTING).isBargeShow())
                mMenus.get(MenuOrder.SETTING).hideBarge();
            Toast.makeText(getContext(), "setting", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener mWifiClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (mMenus.get(MenuOrder.WIFI).isBargeShow())
                mMenus.get(MenuOrder.WIFI).hideBarge();
            Toast.makeText(getContext(), "wifi", Toast.LENGTH_SHORT).show();
        }
    };

    private void inflateItem() {
        LayoutParams params;
        for (AbsMenu menu : mMenus) {
            View vMenu = menu.getView();
            params = new LayoutParams(mDimensionHolder.getMenuWidth(), mDimensionHolder.getMenuHeight());
            params.leftMargin = 9;
            vMenu.setLayoutParams(params);
            mLLMenuGroup.addView(vMenu);
        }
    }

    private void reverseLayout() {
        mLLMenuGroup.removeAllViews();
        LayoutParams params;
        int size = mMenus.size();
        boolean left = (mDirectionHandler.getCurrentSide() == DirectionHandler.LEFT_SIZE);
        int margin = mDimensionHolder.getMenuMargin();
        if (left) {
            for (AbsMenu menu : mMenus) {
                View vMenu = menu.getView();
                params = (LayoutParams) menu.getView().getLayoutParams();
                params.leftMargin = margin;
                params.rightMargin = 0;
                mLLMenuGroup.addView(vMenu, params);
            }
        } else {
            for (int i = size - 1; i > -1; i--) {
                View vMenu = mMenus.get(i).getView();
                params = (LayoutParams) vMenu.getLayoutParams();
                params.leftMargin = 0;
                params.rightMargin = margin;
                mLLMenuGroup.addView(vMenu, params);
            }
        }
        Log.i("floatMenu_Group", "reverse layout");
    }

    private void initLayout() {
        mLLMenuGroup = new LinearLayout(getContext());
        mLLMenuGroup.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams menuGroupParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLLMenuGroup.setLayoutParams(menuGroupParams);
        mLLMenuGroup.setOrientation(LinearLayout.HORIZONTAL);
    }

    private void createMenus() {
        for (MenuEnum value : MenuEnum.values()) {
            ItemMenu menu = new ItemMenu(getContext());
            menu.setIcon(value.getResId());
            menu.setName(value.getName());
            mMenus.add(/*value.getOrder(), */menu);
        }
    }

    @Override
    public void show() {
        super.show();
        mLLMenuGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void reverse() {
        reverseLayout();
    }

    @Override
    public View getView() {
        return mLLMenuGroup;
    }

    @Override
    public void hide() {
        super.hide();
        mLLMenuGroup.setVisibility(View.GONE);
    }

    @Override
    public boolean isShow() {
        return mLLMenuGroup.getVisibility() == View.VISIBLE;
    }

    public class MenuGroupParams extends WLayoutParams {

        public MenuGroupParams(WindowManager.LayoutParams params) {
            super(params);
        }

        public MenuGroupParams(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        @Override
        public int[] updateParams(int x, int y) {
            int[] xy = new int[2];
            if (mDirectionHandler.getCurrentSide() == DirectionHandler.LEFT_SIZE) {
                xy[0] = x + mDimensionHolder.getMenuWidth();
            } else {
                xy[0] = x - mDimensionHolder.getLogoAndGroupWidth();
            }
            xy[1] = y;
            return xy;
        }
    }
}
