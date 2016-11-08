package com.gaiso.floatmenu.widget;

import android.content.Context;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gaiso.floatmenu.R;
import com.gaiso.floatmenu.base.AbsMenu;
import com.gaiso.floatmenu.base.ILogo;
import com.gaiso.floatmenu.base.ViewHolderGroup;

/**
 * Created by Gaiso on 2016/11/1.
 */

public class LogoMenu extends AbsMenu implements ILogo {

    private ImageView mIvLogo;
    private RelativeLayout.LayoutParams mLayoutParams;

    public LogoMenu(Context context) {
        super(context);
    }

    @Override
    public int getResId() {
        return R.layout.menu_item_logo;
    }

    @Override
    public void findView() {
        mIvLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
        mLayoutParams = (RelativeLayout.LayoutParams) mIvLogo.getLayoutParams();
    }

    @Override
    public void hideHalf() {
        // TODO: 2016/11/8  隐藏图标一半
    }

    public class LogoParams extends ViewHolderGroup.WLayoutParams {

        public LogoParams(WindowManager.LayoutParams params) {
            super(params);
        }

        public LogoParams(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        @Override
        public int[] updateParams(int x, int y) {
            int[] xy = {x, y};
            return xy;
        }
    }

}
