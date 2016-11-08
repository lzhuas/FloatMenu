package com.gaiso.floatmenu;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.gaiso.floatmenu.base.ViewHolderGroup;
import com.gaiso.floatmenu.proxy.ViewGroupProxy;
import com.gaiso.floatmenu.helper.DimensionHolder;
import com.gaiso.floatmenu.helper.DirectionHandler;
import com.gaiso.floatmenu.helper.Porter;
import com.gaiso.floatmenu.widget.LogoMenu;
import com.gaiso.floatmenu.widget.MenuGroup;

/**
 * Created by Gaiso on 2016/10/31.
 */

public class FloatMenu implements Porter.OnMoveListener {

    private Context mContext;
    private LogoMenu mLogo;
    private Porter mPorter;
    private ViewGroupProxy mGroupProxy;
    private DimensionHolder mDimensionHolder = DimensionHolder.getInstance();
    private DirectionHandler mDirectionHandler = DirectionHandler.getInstance();

    public FloatMenu(Context context) {
        mContext = context;
        init();
    }

    private void init() {

        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        mDirectionHandler.init(screenWidth);
        mDimensionHolder.init(mContext);

        mPorter = Porter.getPorter();
        mPorter.init(mContext, manager);

        mLogo = new LogoMenu(mContext);
        LogoMenu.LogoParams params = mLogo.new LogoParams(0, screenWidth / 10, mDimensionHolder.getMenuWidth(),
                mDimensionHolder.getMenuHeight());
        mLogo.showBarge();

        MenuGroup menuGroup = new MenuGroup(mContext);
        MenuGroup.MenuGroupParams menuParams = menuGroup.new MenuGroupParams(mDimensionHolder.getMenuWidth(),
                screenWidth / 10, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        menuGroup.setOnGroupEventListener(new ViewHolderGroup.OnGroupEventListener() {
            @Override
            public void onGroupClick(View view) {
            }

            @Override
            public void onGroupVisible(boolean visible) {
                Toast.makeText(mContext, "group show : " + visible, Toast.LENGTH_SHORT).show();
            }
        });

        mGroupProxy = new ViewGroupProxy(mContext);
        mGroupProxy.addGroup(ViewGroupProxy.MENU_GROUP, menuGroup);
        mGroupProxy.showAllBarge();

        Log.i("floatMenu_Init", 0 + "," + screenWidth / 10 + ", " + mLogo.getView().getMeasuredWidth());

        mPorter.addTarget(mLogo.getView(), params);
        mPorter.addTarget(menuGroup.getView(), menuParams);
        mPorter.setOnMoveListener(this);
        mPorter.port(mLogo.getView());
    }

    @Override
    public void onMoveStart(View view, int x, int y) {
        Log.i("floatMenu_Start", x + "," + y);
    }

    @Override
    public void onMoving(View view, int x, int y) {
        Log.i("floatMenu_Moving", x + "," + y);
        if (mGroupProxy.isShow())
            mGroupProxy.hide();
    }

    @Override
    public void onMoveEnd(View view, int x, int y, boolean sideChange, boolean justAClick) {
        Log.i("floatMenu_End", x + "," + y + ", sideChange:" + sideChange + ", click:" + justAClick);
        if (sideChange)
            mLogo.reverseBarge();

        if (justAClick) {//点击事件
            if (mLogo.isBargeShow())
                mLogo.hideBarge();

            if (mGroupProxy.isShow())
                mGroupProxy.hide();
            else {
                mGroupProxy.show();
            }

            if (mDirectionHandler.isSideChange(mGroupProxy.getView())) {
                mGroupProxy.reverse();
                mGroupProxy.reverseBarge();
            }

            mPorter.update(mGroupProxy.getView());
        }
    }
}
