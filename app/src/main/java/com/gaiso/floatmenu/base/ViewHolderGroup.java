package com.gaiso.floatmenu.base;

import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Gaiso on 2016/11/1.
 */

public interface ViewHolderGroup {

    void show();

    void hide();

    View getView();

    boolean isShow();

    void reverse();

    interface OnGroupEventListener {

        void onGroupClick(View view);

        void onGroupVisible(boolean visible);
    }

    abstract class WLayoutParams {

        private WindowManager.LayoutParams mParams;

        public WindowManager.LayoutParams getLatestParams(int x, int y) {

            int[] xy = updateParams(x, y);

            if (xy != null && xy.length == 2) {
                mParams.x = xy[0];
                mParams.y = xy[1];
            }

            return mParams;
        }

        public WindowManager.LayoutParams getParams() {
            return mParams;
        }

        public abstract int[] updateParams(int x, int y);

        public WLayoutParams(WindowManager.LayoutParams params) {
            mParams = params;
        }

        public WLayoutParams(int x, int y, int width, int height) {
            mParams = getDefaultParams(x, y, width, height);
        }

        private WindowManager.LayoutParams getDefaultParams(int x, int y, int width, int height) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            params.format = PixelFormat.RGBA_8888;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.x = x;
            params.y = y;
            params.width = width;
            params.height = height;
            return params;
        }
    }
}
