package com.gaiso.floatmenu.widget;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaiso.floatmenu.R;
import com.gaiso.floatmenu.base.AbsMenu;

/**
 * Created by Gaiso on 2016/11/3.
 */

public class ItemMenu extends AbsMenu {

    private ImageView mIvIcon;
    private TextView mTvName;

    public ItemMenu(Context context) {
        super(context);
    }

    @Override
    public int getResId() {
        return R.layout.menu_item_normal;
    }

    @Override
    public void findView() {
        mIvIcon = (ImageView) itemView.findViewById(R.id.iv);
        mTvName = (TextView) itemView.findViewById(R.id.title);
    }

    public void setIcon(int resId) {
        mIvIcon.setImageResource(resId);
    }

    public void setName(String name) {
        mTvName.setText(name);
    }
}
