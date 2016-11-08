package com.gaiso.floatmenu.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.gaiso.floatmenu.base.ViewHolderGroup;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by Gaiso on 2016/11/1.
 */

public class Porter implements View.OnTouchListener {

    private static Porter INSTANCE = new Porter();
    private WindowManager mManager;
    private OnMoveListener mMoveListener;
    private boolean mIsAttach = true, mIsAClick = true;
    private ArrayMap<View, ViewHolderGroup.WLayoutParams> mViewData = new ArrayMap<>();

    private float mFirstRawX, mFirstRawY, mLastPositionX, mLastPositionY;
    private int mTouchSlop = 3;
    private int mStatusBarHeight;
    private DirectionHandler mDirectionHandler = DirectionHandler.getInstance();

    private int[] mTempLocation = new int[2];

    private Context mContext;

    private Porter() {
    }

    public void init(Context context, WindowManager manager) {
        mContext = context;
        mManager = manager;
    }

    public static Porter getPorter() {
        return INSTANCE;
    }

    public void setIsAttach(boolean attach) {
        if (mIsAttach == attach)
            return;
        if (attach) {
            addAllView2Handler();
        } else {
            mDirectionHandler.clear();
        }
        mIsAttach = attach;
    }

    private void addAllView2Handler() {
        Iterator<View> i = mViewData.keySet().iterator();
        while (i.hasNext()) {
            View key = i.next();
            ViewHolderGroup.WLayoutParams params = mViewData.get(key);
            addView2Handler(key, params);
        }
    }

    private void addView2Handler(View key, ViewHolderGroup.WLayoutParams params) {
        int side = params.getParams().x >= (mDirectionHandler.getScreenWidth() / 2) ? DirectionHandler.RIGHT_SIZE :
                DirectionHandler.LEFT_SIZE;
        mDirectionHandler.addView(key, side);
        Log.i("floatMenu_addView", key + ", " + side);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:

                view.getLocationOnScreen(mTempLocation);
                mTempLocation[1] = mTempLocation[1] - getStatusBarHeight();

                mFirstRawX = motionEvent.getRawX();
                mFirstRawY = motionEvent.getRawY();

                mLastPositionX = mTempLocation[0];
                mLastPositionY = mTempLocation[1];

                if (mMoveListener != null) {
                    mMoveListener.onMoveStart(view, (int) mLastPositionX, (int) mLastPositionY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = motionEvent.getRawX();
                float currentY = motionEvent.getRawY();
                if (Math.abs(currentX - mFirstRawX) > mTouchSlop || Math.abs(currentY -
                        mFirstRawY) > mTouchSlop) {
                    mIsAClick = false;
                    mLastPositionX = mTempLocation[0] + currentX - mFirstRawX;
                    mLastPositionY = mTempLocation[1] + currentY - mFirstRawY;
                    move(view, mLastPositionX, mLastPositionY);
                    if (mMoveListener != null) {
                        mMoveListener.onMoving(view, (int) mLastPositionX, (int) mLastPositionY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                boolean sideChange = false;
                if (mIsAttach) {
                    mDirectionHandler.updateSide(mLastPositionX);
                    int side = mDirectionHandler.getCurrentSide();
                    if (side == DirectionHandler.LEFT_SIZE) {
                        mLastPositionX = 0;
                    } else {
                        mLastPositionX = mDirectionHandler.getScreenWidth();
                    }
                    sideChange = mDirectionHandler.isSideChange(view);
                    move(view, mLastPositionX, mLastPositionY);
                }
                if (mMoveListener != null) {
                    mMoveListener.onMoveEnd(view, (int) mLastPositionX, (int) mLastPositionY, sideChange, mIsAClick);
                }
                resetClickStatus();
                break;
        }
        return true;
    }

    private void resetClickStatus() {
        mIsAClick = true;
    }

    public interface OnMoveListener {

        void onMoveStart(View view, int x, int y);

        void onMoving(View view, int x, int y);

        void onMoveEnd(View view, int x, int y, boolean sideChange, boolean justAClick);
    }

    public void setOnMoveListener(OnMoveListener listener) {
        mMoveListener = listener;
    }

    public void port(View view) {
        view.setOnTouchListener(this);
    }

    public void addTarget(View target, ViewHolderGroup.WLayoutParams params) {
        if (!mViewData.containsKey(target)) {
            mViewData.put(target, params);
            mManager.addView(target, params.getParams());
            if (mIsAttach) {
                addView2Handler(target, params);
            }
        }
    }

    private void move(View target, float x, float y) {
        ViewHolderGroup.WLayoutParams params = mViewData.get(target);
        if (params == null)
            return;
        mManager.updateViewLayout(target, params.getLatestParams((int) x, (int) y));
    }

    public void update(View target) {
        move(target, (int) mLastPositionX, (int) mLastPositionY);
    }

    public void clear() {
        // TODO: 2016/11/7 clear operation
        Iterator<View> viewIterator = mViewData.keySet().iterator();
        while (viewIterator.hasNext()) {
            mManager.removeView(viewIterator.next());
        }
        mViewData.clear();
    }

    private boolean isFullScreen() {
        int flag = ((Activity) mContext).getWindow().getAttributes().flags;
        return (flag & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    private int getStatusBarHeight() {
        if (isFullScreen())
            return 0;
        if (mStatusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                mStatusBarHeight = mContext.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mStatusBarHeight;
    }
}
